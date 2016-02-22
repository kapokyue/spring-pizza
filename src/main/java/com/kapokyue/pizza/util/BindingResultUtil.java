package com.kapokyue.pizza.util;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.BindingResult;

import java.util.LinkedList;
import java.util.List;

public class BindingResultUtil
{
  public static List<String> toErrorMessages( BindingResult bindingResult ) {
    List<String> errors = new LinkedList<>();
    bindingResult.getGlobalErrors().stream()
                 .map( DefaultMessageSourceResolvable::getDefaultMessage )
                 .forEach( errors::add );
    bindingResult.getFieldErrors().stream()
                 .map( err -> String.format( "Field[%s]: %s", err.getField(), err.getDefaultMessage() ) )
                 .forEach( errors::add );
    return errors;
  }
}
