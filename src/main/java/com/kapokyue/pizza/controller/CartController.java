package com.kapokyue.pizza.controller;

import com.kapokyue.pizza.model.Cart;
import com.kapokyue.pizza.model.Customer;
import com.kapokyue.pizza.model.Product;
import com.kapokyue.pizza.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping( "/cart" )
@PreAuthorize( "hasRole('ROLE_CUSTOMER')" )
public class CartController
{
  @Autowired
  CartRepository cartRepo;

  @RequestMapping( value = "", method = RequestMethod.GET )
  public String show( Model model, @AuthenticationPrincipal Customer customer )
  {
    model.addAttribute( "products", cartRepo.findByCustomer( customer )
                                            .stream()
                                            .map( Cart::getProduct )
                                            .toArray() );
    return "cart";
  }

  @RequestMapping( value = "", method = RequestMethod.POST )
  public String add( @RequestParam long productId,
                     @AuthenticationPrincipal Customer customer,
                     HttpServletRequest request )
  {
    Cart c = new Cart();
    c.setCustomer( customer );

    // no need to find the product actually
    Product p = new Product();
    p.setId( productId );
    c.setProduct( p );

    cartRepo.save( c );

    return "redirect:" + request.getHeader( "referer" );
  }

  @RequestMapping( value = "/{productId}", method = RequestMethod.DELETE )
  public String drop( @PathVariable Long productId,
                      @AuthenticationPrincipal Customer customer,
                      HttpServletRequest request )
  {
    cartRepo.delete( new Cart.Id( productId, customer.getId() ) );
    return "redirect:" + request.getHeader( "referer" );
  }
}
