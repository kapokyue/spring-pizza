package com.kapokyue.pizza.repository;

import com.kapokyue.pizza.model.Category;
import com.kapokyue.pizza.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>
{
  List<Product> findByNameLike( String name, Sort sort );

  List<Product> findByNameLikeAndCategory( String name, Category category, Sort sort );
}
