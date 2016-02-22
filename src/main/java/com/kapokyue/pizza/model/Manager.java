package com.kapokyue.pizza.model;

import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Collection;
import java.util.Collections;

@Entity @DiscriminatorValue( value = "M" )
@ToString( callSuper = true )
public class Manager extends User
{
  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList( new SimpleGrantedAuthority( "ROLE_MANAGER" ) );
  }
}
