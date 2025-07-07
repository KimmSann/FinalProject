package com.example.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
