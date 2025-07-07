package com.example.demo.postimg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.postimg.entity.Postimg;

public interface PostimgRepository extends JpaRepository<Postimg, Integer> {

}
