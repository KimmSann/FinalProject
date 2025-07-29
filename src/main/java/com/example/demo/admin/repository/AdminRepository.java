package com.example.demo.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.admin.entity.Admin;
import com.example.demo.user.entity.User;

public interface AdminRepository extends JpaRepository<Admin, Integer>{
	
	boolean existsByUser(User user);
	
	

	@Modifying
	@Transactional
	@Query("DELETE FROM Admin a WHERE a.user.userid = :userId")
	void deleteByUserUserid(@Param("userId") int userId);

}
