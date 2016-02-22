package com.kapokyue.pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController
{
  @RequestMapping( "/" )
  public String index() {
    return "forward:/product";
  }

  @RequestMapping( "/about-us" )
  public String aboutUs( Model model ) {
    return "about-us";
  }
}
