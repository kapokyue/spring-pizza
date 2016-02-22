package com.kapokyue.pizza.form;

import com.kapokyue.pizza.model.Customer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.Errors;

import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode( callSuper = true )
@ToString( callSuper = true )
public class RegisterForm extends ProfileForm
{
  @NotBlank
  String account;

  @NotNull
  @Size( min = 6, message = "Size of password must greater then 6" )
  String password;

  @NotNull
  String password_confirmation;

  public Customer makeCustomer() {
    Customer c = new Customer();
    c.setName( name );
    c.setAccount( account );
    c.setPassword( password );
    c.setPhone( phone );
    return c;
  }
}
