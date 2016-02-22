package com.kapokyue.pizza;

import com.kapokyue.pizza.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith( SpringJUnit4ClassRunner.class )
@SpringApplicationConfiguration( classes = SpringPizzaApplication.class )
@Transactional
public class SpringPizzaApplicationTests
{
  @Autowired CartRepository cartRepo;
  @Autowired CategoryRepository categoryRepo;
  @Autowired OrderItemRepository orderItemRepo;
  @Autowired OrderRepository orderRepo;
  @Autowired ProductRepository productRepo;
  @Autowired UserRepository userRepo;

  @Autowired CustomerRepository customerRepo;
  @Autowired ManagerRepository managerRepo;

  @Before
  public void init() {
    System.out.println("#### Start Testing ####");
  }

  @Test
  public void testCartRepo() {
    System.out.println( "-->> Print all Cart " );
    cartRepo.findAll().forEach( System.out::println );
  }

  @Test
  public void testCategoryRepo() {
    System.out.println( "-->> Print all Category " );
    categoryRepo.findAll().forEach( System.out::println );
  }

  @Test
  public void testOrderItemRepo() {
    System.out.println( "-->> Print all OrderItem " );
    orderItemRepo.findAll().forEach( System.out::println );
  }

  @Test
  public void testOrderRepo() {
    System.out.println( "-->> Print all Order " );
    orderRepo.findAll().forEach( System.out::println );
  }

  @Test
  public void testProductRepo() {
    System.out.println( "-->> Print all Product " );
    productRepo.findAll().forEach( System.out::println );
  }

  @Test
  public void testUserRepo() {
    System.out.println( "-->> Print all User " );
    userRepo.findAll().forEach( System.out::println );
  }
}
