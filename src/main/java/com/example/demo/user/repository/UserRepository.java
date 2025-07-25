package com.example.demo.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.entity.User;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer>{
	 boolean existsByName(String name);
	 
	 boolean existsByEmail(String email);
	 Optional<User> findByEmail(String email);
	 Optional<User> findByNickname(String nickname);
	 Optional<User> findByEmailAndLoginType(String email, String loginType);
	 
}
