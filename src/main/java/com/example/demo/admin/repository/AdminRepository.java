package com.example.demo.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.admin.entity.Admin;
import com.example.demo.user.entity.User;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	boolean existsByUser(User user);

}
