package com.kapokyue.pizza;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class SpringPizzaApplication extends SpringBootServletInitializer
{
  public static void main( String[] args ) {
    SpringApplication.run( SpringPizzaApplication.class, args );
  }

  @Override
  protected SpringApplicationBuilder configure( SpringApplicationBuilder application ) {
    return application.sources( this.getClass() );
  }
}
