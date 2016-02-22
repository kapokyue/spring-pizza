package com.kapokyue.pizza.repository;

import com.kapokyue.pizza.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItem.Id>
{
}
