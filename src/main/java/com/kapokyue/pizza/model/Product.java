package com.kapokyue.pizza.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Data
public class Product implements Serializable
{
  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  long id;

  long price;

  String name, description, img;

  @ManyToOne( fetch = FetchType.LAZY, optional = false )
  @JoinColumn( name = "category_id" )
  Category category;
}
