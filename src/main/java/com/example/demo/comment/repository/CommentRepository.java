package com.example.demo.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
}