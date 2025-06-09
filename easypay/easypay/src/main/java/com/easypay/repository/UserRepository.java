package com.easypay.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.easypay.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	@Query("select u from User u where u.username=?1")
	User getByUsername(String username);
	
	User findByUsername(String username);

}
