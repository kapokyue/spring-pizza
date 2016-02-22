package com.kapokyue.pizza.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Entity @Data
@DiscriminatorValue( value = "C" )
@EqualsAndHashCode( callSuper = true )
@ToString( exclude = { "carts", "orders" }, callSuper = true )
public class Customer extends User
{
  @OneToMany( mappedBy = "customer" )
  List<Cart> carts;

  @OneToMany( mappedBy = "customer" )
  List<Order> orders;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList( new SimpleGrantedAuthority( "ROLE_CUSTOMER" ) );
  }
}
