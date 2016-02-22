package com.kapokyue.pizza.controller;

import com.kapokyue.pizza.form.CategoryForm;
import com.kapokyue.pizza.model.Category;
import com.kapokyue.pizza.repository.CategoryRepository;
import com.kapokyue.pizza.util.BindingResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

@Controller
@RequestMapping( "/category" )
@PreAuthorize( "hasRole('ROLE_MANAGER')" )
public class CategoryController
{
  @Autowired
  CategoryRepository categoryRepo;

  @RequestMapping( value = "/create", method = RequestMethod.GET )
  public String create( Model model ) {
    model.addAttribute( "category", new Category() );
    return "form/category";
  }

  @RequestMapping( value = "/add", method = RequestMethod.POST )
  public String add( @Valid CategoryForm form,
                     BindingResult bindingResult,
                     RedirectAttributes redirectAttributes,
                     HttpServletRequest request )
  {
    if ( bindingResult.hasErrors() ) {
      redirectAttributes.addFlashAttribute( "errors", BindingResultUtil.toErrorMessages( bindingResult ) );
      return "redirect:" + request.getHeader( "referer" );
    }
    long id = categoryRepo.save( form.makeCategory() ).getId();
    redirectAttributes.addFlashAttribute( "succs", Collections.singletonList( "Create successful" ) );
    return "redirect:" + id;
  }

  @RequestMapping( value = "/{id}/edit", method = RequestMethod.GET )
  public String edit( @PathVariable long id, Model model ) {
    model.addAttribute( "category", categoryRepo.findOne( id ) );
    return "form/category";
  }

  @RequestMapping( value = "/{id}", method = RequestMethod.PUT )
  public String update( @PathVariable long id,
                        @Valid CategoryForm categoryForm,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        HttpServletRequest request )
  {
    if ( bindingResult.hasErrors() ) {
      redirectAttributes.addFlashAttribute( "errors", BindingResultUtil.toErrorMessages( bindingResult ) );
      return "redirect:" + request.getHeader( "referer" );
    }
    Category category = categoryForm.makeCategory();
    category.setId( id );
    categoryRepo.save( category );
    redirectAttributes.addFlashAttribute( "succs", Collections.singletonList( "Update successful" ) );
    return "redirect:" + id + "/edit";
  }

  @RequestMapping( value = "/{id}", method = RequestMethod.DELETE )
  public String drop( @PathVariable long id,
                      RedirectAttributes redirectAttributes )
  {
    categoryRepo.delete( id );
    redirectAttributes.addFlashAttribute( "succs", Collections.singletonList( "Delete successful" ) );
    return "redirect:/";
  }
}
