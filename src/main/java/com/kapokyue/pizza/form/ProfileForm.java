package com.kapokyue.pizza.form;

import com.kapokyue.pizza.annotation.FieldMatch;
import com.kapokyue.pizza.model.User;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
@FieldMatch.List( {
    @FieldMatch(
        first = "password", second = "password_confirmation",
        message = "Confirmation field of password must equal to field of password" )
} )
public class ProfileForm
{
  @NotBlank
  String name;

  @NotBlank
  String phone;

  String password;

  String password_confirmation;

  public void updateUser( User u ) {
    u.setName( name );
    if ( !password.isEmpty() ) {
      u.setPassword( password );
    }
    u.setPhone( phone );
  }
}
