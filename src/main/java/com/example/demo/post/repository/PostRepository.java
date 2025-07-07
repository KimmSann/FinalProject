package com.example.demo.post.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{

}
