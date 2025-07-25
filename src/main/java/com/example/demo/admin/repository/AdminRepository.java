package com.example.demo.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.admin.entity.Admin;
import com.example.demo.user.entity.User;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	boolean existsByUser(User user);
	
	

    @Modifying 
    @Transactional  
	void deleteByUserUserid(int userId);

}
