package com.kapokyue.pizza.repository;

import com.kapokyue.pizza.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>
{
  Optional<User> findByAccount( String account );
}
