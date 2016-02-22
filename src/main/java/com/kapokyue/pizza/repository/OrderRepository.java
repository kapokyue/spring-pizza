package com.kapokyue.pizza.repository;

import com.kapokyue.pizza.model.Customer;
import com.kapokyue.pizza.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>
{
  List<Order> findByCustomer( Customer customer );

  List<Order> findByStatus( String status );
}
