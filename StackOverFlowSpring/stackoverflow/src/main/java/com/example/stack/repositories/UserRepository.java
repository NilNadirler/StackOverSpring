package com.example.stack.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.stack.entity.User;


public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findFirstByEmail(String email);
	
	

}
