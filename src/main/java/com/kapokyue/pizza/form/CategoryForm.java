package com.kapokyue.pizza.form;

import com.kapokyue.pizza.model.Category;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

@Data
public class CategoryForm
{
  @NotBlank
  String name;

  public Category makeCategory() {
    Category c = new Category();
    c.setName( name );
    return c;
  }
}
