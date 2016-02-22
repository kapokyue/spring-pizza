package com.kapokyue.pizza.controller;

import com.kapokyue.pizza.model.*;
import com.kapokyue.pizza.repository.CartRepository;
import com.kapokyue.pizza.repository.OrderItemRepository;
import com.kapokyue.pizza.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/order" )
public class OrderController
{
  @Autowired
  OrderRepository orderRepo;

  @Autowired
  OrderItemRepository orderItemRepo;

  @Autowired
  CartRepository cartRepo;

  @PreAuthorize( "hasRole('ROLE_CUSTOMER')" )
  @RequestMapping( value = "/history", method = RequestMethod.GET )
  public String show( Model model,
                      @AuthenticationPrincipal Customer customer )
  {
    List<Order> orders = orderRepo.findByCustomer( customer );
    model.addAttribute( "orders", orders.parallelStream()
                                        .map( this::encapOrder )
                                        .toArray() );
    return "order-history";
  }

  @PreAuthorize( "hasRole('ROLE_MANAGER')" )
  @RequestMapping( value = "/all", method = RequestMethod.GET )
  public String all( Model model )
  {
    List<Order> orders = orderRepo.findAll();
    model.addAttribute( "orders", orders.parallelStream()
                                        .map( this::encapOrder )
                                        .toArray() );
    return "order-history";
  }

  @PreAuthorize( "hasRole('ROLE_MANAGER')" )
  @RequestMapping( value = "", method = RequestMethod.GET )
  public String processingOrder( Model model ) {
    Function<Order, Map<String, Object>> f = o -> {
      Map<String, Object> m = encapOrder( o );
      m.put( "username", o.getCustomer().getName() );
      return m;
    };
    model.addAttribute( "cookingOrders", orderRepo.findByStatus( "Cooking" )
                                                  .parallelStream()
                                                  .map( f )
                                                  .toArray() );
    model.addAttribute( "deliveringOrder", orderRepo.findByStatus( "Delivering" )
                                                    .parallelStream()
                                                    .map( f )
                                                    .toArray() );
    return "orders";
  }

  @PreAuthorize( "hasRole('ROLE_CUSTOMER')" )
  @RequestMapping( value = "", method = RequestMethod.POST )
  public String add( @RequestParam Map<String, String> params,
                     @RequestParam String address,
                     @RequestParam String note,
                     @AuthenticationPrincipal Customer customer )
  {
    List<Cart> carts = cartRepo.findByCustomer( customer );
    List<Product> products = carts.stream()
                                  .map( Cart::getProduct )
                                  .collect( Collectors.toList() );
    Order o = new Order();
    o.setAddress( address );
    o.setNote( note );
    o.setCustomer( customer );
    o.setStatus( "Cooking" );
    o.setCharge( products.stream()
                         .mapToLong( p ->
                             p.getPrice() * Long.parseLong( params.get( "qty[" + p.getId() + "]" ) ) )
                         .sum() );
    orderRepo.save( o );

    products.stream()
            .map( p -> {
              OrderItem item = new OrderItem();
              item.setProduct( p );
              item.setOrder( o );
              item.setQty( Long.parseLong( params.get( "qty[" + p.getId() + "]" ) ) );
              return item;
            } )
            .forEach( orderItemRepo::save );

    cartRepo.delete( carts );

    return "redirect:/order/history";
  }

  @PreAuthorize( "hasRole('ROLE_MANAGER')" )
  @RequestMapping( value = "/{id}/status", method = RequestMethod.PUT )
  public String updateOrderStatus( @PathVariable long id,
                                   @RequestParam String status )
  {
    Order o = orderRepo.findOne( id );
    o.setStatus( status );
    orderRepo.save( o );
    return "redirect:/order";
  }

  protected Map<String, Object> encapOrder( Order o ) {
    Map<String, Object> m = new HashMap<>( 6 );
    m.put( "id", o.getId() );
    m.put( "address", o.getAddress() );
    m.put( "charge", o.getCharge() );
    m.put( "orderAt", o.getOrderAt() );
    m.put( "status", o.getStatus() );
    m.put( "items", String.join( "<br>",
        o.getItems()
         .parallelStream()
         .map( i -> i.getProduct().getName() + " * " + i.getQty() )
         .collect( Collectors.toList() ) ) );
    return m;
  }
}
