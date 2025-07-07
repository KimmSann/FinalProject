package com.example.demo.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.admin.entity.Admin;

public interface AdminRepository extends JpaRepository<Admin, Integer>{

}
