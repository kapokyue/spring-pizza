package com.kapokyue.pizza.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity @Data @ToString( exclude = { "products" } )
public class Category implements Serializable
{
  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  long id;

  String name;

  @OneToMany( mappedBy = "category" )
  List<Product> products;
}
