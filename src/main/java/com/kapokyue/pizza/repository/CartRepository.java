package com.kapokyue.pizza.repository;

import com.kapokyue.pizza.model.Cart;
import com.kapokyue.pizza.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartRepository extends JpaRepository<Cart, Cart.Id>
{
  List<Cart> findByCustomer( Customer customer );
}
