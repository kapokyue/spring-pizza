package com.kapokyue.pizza.annotation.impl;

import com.kapokyue.pizza.annotation.FieldMatch;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object>
{
  private String firstFieldName;
  private String secondFieldName;

  @Override
  public void initialize( final FieldMatch constraintAnnotation )
  {
    firstFieldName = constraintAnnotation.first();
    secondFieldName = constraintAnnotation.second();
  }

  @Override
  public boolean isValid( final Object value, final ConstraintValidatorContext context )
  {
    BeanWrapper v = new BeanWrapperImpl( value );
    Object firstObj = v.getPropertyValue( firstFieldName );
    Object secondObj = v.getPropertyValue( secondFieldName );

    return firstObj == null && secondObj == null ||
           firstObj != null && firstObj.equals( secondObj );
  }
}