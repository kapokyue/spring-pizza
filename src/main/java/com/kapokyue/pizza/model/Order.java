package com.kapokyue.pizza.model;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

@Entity @Table( name = "customer_order" ) @Data
@ToString( exclude = { "items" } )
public class Order implements Serializable
{
  @Id
  @GeneratedValue( strategy = GenerationType.AUTO )
  long id;

  @ManyToOne( fetch = FetchType.LAZY, optional = false )
  @JoinColumn( name = "user_id" )
  Customer customer;

  long charge;

  String status, address;

  String note;

  Timestamp orderAt;

  @OneToMany( mappedBy = "order", fetch = FetchType.EAGER )
  List<OrderItem> items;
}
