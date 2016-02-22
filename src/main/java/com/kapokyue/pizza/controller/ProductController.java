package com.kapokyue.pizza.controller;

import com.kapokyue.pizza.form.ProductForm;
import com.kapokyue.pizza.model.*;
import com.kapokyue.pizza.repository.CartRepository;
import com.kapokyue.pizza.repository.CategoryRepository;
import com.kapokyue.pizza.repository.ProductRepository;
import com.kapokyue.pizza.util.BindingResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping( "/product" )
public class ProductController
{
  @Autowired
  ProductRepository productRepo;

  @Autowired
  CategoryRepository categoryRepo;

  @Autowired
  CartRepository cartRepo;

  @Autowired
  ServletContext context;

  @RequestMapping( value = "", method = RequestMethod.GET )
  public String showAll( Model model,
                         @RequestParam( required = false ) Long cid,
                         @AuthenticationPrincipal User user )
  {
    model.addAttribute( "title", "Products" );
    model.addAttribute( "categoryBaseUrl", "/product" );
    model.addAttribute( "products", cid == null
                                    ? productRepo.findAll()
                                    : categoryRepo.findOne( cid ).getProducts() );
    model.addAttribute( "categorys", categoryRepo.findAll() );
    model.addAttribute( "categoryActiveId", cid == null ? 0 : cid );

    if ( user instanceof Customer ) {
      Map<Long, Object> cs = cartRepo.findByCustomer( (Customer) user ).stream()
                                     .map( Cart::getProduct )
                                     .collect( Collectors.toMap( Product::getId, Function.identity() ) );
      model.addAttribute( "cartProducts", cs );
    }
    return "product";
  }

  @RequestMapping( value = "/search", method = RequestMethod.GET )
  public String search( Model model,
                        @RequestParam String name,
                        @RequestParam( required = false ) Long cid,
                        @RequestParam( required = false ) String sorter,
                        UriComponentsBuilder uriBuilder )
  {
    model.addAttribute( "title", "Search" );
    model.addAttribute( "categoryBaseUrl", uriBuilder
        .queryParam( "name", name ).queryParam( "sorter", sorter ).build() );
    model.addAttribute( "categorys", categoryRepo.findAll() );
    model.addAttribute( "categoryActiveId", cid == null ? 0 : cid );

    String likeName = "%" + name + "%";
    List<Product> products;
    Sort sort = null;
    if ( !StringUtils.isEmpty( sorter ) ) {
      String[] orderBy = sorter.split( "-" );
      sort = new Sort( new Sort.Order( Sort.Direction.fromString( orderBy[1] ), orderBy[0] ) );
    }
    if ( cid == null ) {
      products = productRepo.findByNameLike( likeName, sort );
    } else {
      products = productRepo.findByNameLikeAndCategory( likeName, categoryRepo.findOne( cid ), sort );
    }
    model.addAttribute( "products", products );

    return "product";
  }

  @RequestMapping( value = "/create", method = RequestMethod.GET )
  @PreAuthorize( "hasRole('ROLE_MANAGER')" )
  public String create( Model model ) {
    model.addAttribute( "product", new Product() );
    model.addAttribute( "categorys", categoryRepo.findAll()
                                                 .stream()
                                                 .collect(
                                                     Collectors.toMap(
                                                         Category::getId, Category::getName ) ) );
    return "form/product";
  }

  @RequestMapping( value = "/add", method = RequestMethod.POST )
  @PreAuthorize( "hasRole('ROLE_MANAGER')" )
  public String add( @Valid ProductForm productForm,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes,
                     HttpServletRequest request ) throws IOException
  {
    if ( bindingResult.hasErrors() ) {
      redirectAttributes.addFlashAttribute( "errors", BindingResultUtil.toErrorMessages( bindingResult ) );
      return "redirect:" + request.getHeader( "referer" );
    }

    long id = productRepo.save( productForm.makeProduct( context ) ).getId();
    redirectAttributes.addFlashAttribute( "succs", Collections.singletonList( "Create successful" ) );
    return "redirect:" + id + "/edit";
  }

  @RequestMapping( value = "/{id}/edit", method = RequestMethod.GET )
  @PreAuthorize( "hasRole('ROLE_MANAGER')" )
  public String edit( @PathVariable long id, Model model ) {
    model.addAttribute( "categorys", categoryRepo.findAll()
                                                 .stream()
                                                 .collect(
                                                     Collectors.toMap(
                                                         Category::getId, Category::getName ) ) );
    model.addAttribute( "product", productRepo.findOne( id ) );
    return "form/product";
  }

  @RequestMapping( value = "/{id}/update", method = RequestMethod.POST )
  @PreAuthorize( "hasRole('ROLE_MANAGER')" )
  public String update( @PathVariable long id,
                        @Valid ProductForm productForm,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        HttpServletRequest request ) throws IOException
  {
    if ( bindingResult.hasErrors() ) {
      redirectAttributes.addFlashAttribute( "errors", BindingResultUtil.toErrorMessages( bindingResult ) );
      return "redirect:" + request.getHeader( "referer" );
    }

    Product p;
    p = productForm.makeProduct( context );
    p.setId( id );
    productRepo.save( p );
    redirectAttributes.addFlashAttribute( "succs", Collections.singletonList( "Update successful" ) );
    return "redirect:" + "edit";
  }

  @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
  @PreAuthorize( "hasRole('ROLE_MANAGER')" )
  public String drop( @PathVariable long id,
                      RedirectAttributes redirectAttributes )
  {
    Product p = productRepo.findOne( id );
    if (p.getImg().startsWith( "/" )) {
      File f = new File( context.getRealPath( "/" ) + p.getImg() );
      f.delete();
    }
    productRepo.delete( id );
    redirectAttributes.addFlashAttribute( "succs", Collections.singletonList( "Delete successful" ) );
    return "redirect:/";
  }
}
