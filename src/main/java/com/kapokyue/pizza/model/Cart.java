package com.kapokyue.pizza.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Data
public class Cart implements Serializable
{
  @Embeddable @EqualsAndHashCode
  @AllArgsConstructor @Getter @ToString
  @NoArgsConstructor( access = AccessLevel.PACKAGE )
  public static class Id implements Serializable
  {
    long productId, customerId;
  }

  @EmbeddedId
  Id id = new Id();

  @MapsId( value = "customerId" )
  @ManyToOne( fetch = FetchType.LAZY, optional = false )
  @JoinColumn( name = "user_id" )
  Customer customer;

  @MapsId( value = "productId" )
  @JoinColumn( name = "product_id" )
  @ManyToOne( fetch = FetchType.EAGER, optional = false )
  Product product;
}
