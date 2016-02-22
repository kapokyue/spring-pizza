package com.kapokyue.pizza.model;

import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;

@Entity @Table( name = "system_user" ) @Data
@Inheritance( strategy = InheritanceType.SINGLE_TABLE )
@DiscriminatorColumn( name = "type" )
public abstract class User implements UserDetails
{
  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  long id;

  String name, account, password, phone;

  @Override public String getUsername() {
    return getAccount();
  }

  @Override public boolean isAccountNonExpired() {
    return true;
  }

  @Override public boolean isAccountNonLocked() {
    return true;
  }

  @Override public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override public boolean isEnabled() {
    return true;
  }
}
