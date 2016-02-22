package com.kapokyue.pizza.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity @Data
public class OrderItem implements Serializable
{
  @Embeddable @EqualsAndHashCode
  @AllArgsConstructor @Getter @ToString
  @NoArgsConstructor( access = AccessLevel.PACKAGE )
  public static class Id implements Serializable
  {
    long productId, orderId;
  }

  @EmbeddedId
  Id id = new Id();

  @MapsId( value = "productId" )
  @ManyToOne( fetch = FetchType.LAZY, optional = false )
  @JoinColumn( name = "product_id" )
  Product product;

  @MapsId( value = "orderId" )
  @ManyToOne( fetch = FetchType.LAZY, optional = false )
  @JoinColumn( name = "order_id" )
  Order order;

  long qty;
}