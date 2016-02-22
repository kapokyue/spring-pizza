package com.kapokyue.pizza.controller;

import com.kapokyue.pizza.util.BindingResultUtil;
import com.kapokyue.pizza.form.ProfileForm;
import com.kapokyue.pizza.form.RegisterForm;
import com.kapokyue.pizza.model.User;
import com.kapokyue.pizza.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;

@Controller
public class UserController
{
  @Autowired
  UserRepository userRepo;

  @RequestMapping( value = "/register", method = RequestMethod.GET )
  @PreAuthorize( "isAnonymous()" )
  public String registerForm() {
    return "form/register";
  }

  @RequestMapping( value = "/register", method = RequestMethod.POST )
  @PreAuthorize( "isAnonymous()" )
  public String register( RedirectAttributes redirectAttributes,
                          @Valid RegisterForm form,
                          BindingResult bindingResult,
                          HttpServletRequest request ) throws ServletException
  {
    if ( bindingResult.hasErrors() ) {
      redirectAttributes.addFlashAttribute( "errors", BindingResultUtil.toErrorMessages( bindingResult ) );
      return "redirect:/register";
    } else {
      User u = userRepo.save( form.makeCustomer() );
      request.login( u.getAccount(), u.getPassword() );
      return "redirect:/";
    }
  }

  @RequestMapping( value = "/profile", method = RequestMethod.GET )
  @PreAuthorize( "isAuthenticated()" )
  public String edit( Model model,
                      @AuthenticationPrincipal User user )
  {
    model.addAttribute( "user", user );
    return "form/profile";
  }

  @RequestMapping( value = "/profile", method = RequestMethod.PUT )
  @PreAuthorize( "isAuthenticated()" )
  public String update( RedirectAttributes redirectAttributes,
                        @Valid ProfileForm form,
                        BindingResult bindingResult,
                        @AuthenticationPrincipal User user )
  {
    if ( bindingResult.hasErrors() ) {
      redirectAttributes.addFlashAttribute( "errors", BindingResultUtil.toErrorMessages( bindingResult ) );
    } else {
      form.updateUser( user );
      userRepo.save( user );
      redirectAttributes.addFlashAttribute( "succs", Collections.singletonList( "Update successful" ) );
    }
    return "redirect:/profile";
  }

  @RequestMapping( value = "/login", method = RequestMethod.GET )
  public String loginForm( HttpServletRequest request, Model model ) {
    if ( request.getParameter( "error" ) != null ) {
      model.addAttribute( "errors", Collections.singletonList( "No this account or wrong password" ) );
    }
    return "form/login";
  }

  @RequestMapping( value = "/login", method = RequestMethod.POST )
  public String login( HttpServletRequest request,
                       RedirectAttributes redirectAttributes,
                       @RequestParam String account,
                       @RequestParam String password )
  {
    try {
      request.login( account, password );
      redirectAttributes.addFlashAttribute( "succs",
          Collections.singletonList( "Login successful" ) );

      if ( request.getRequestURI().startsWith( "/login" ) ) {
        return "redirect:/";
      }
    } catch ( ServletException e ) {
      redirectAttributes.addFlashAttribute( "errors",
          Collections.singletonList( "Fail to login: No this account or wrong password" ) );
    }
    return "redirect:" + request.getHeader( "referer" );
  }

  @RequestMapping( value = "/logout", method = RequestMethod.POST )
  public String logout( HttpServletRequest request ) throws ServletException
  {
    request.logout();
    return "/";
  }
}
