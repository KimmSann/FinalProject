package com.example.demo.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.comment.entity.Comment;
import com.example.demo.post.entity.Post;
import com.example.demo.user.entity.User;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	// 게시물 조회
	List<Comment> findByPost(Post post);
	
	List<Comment> findByUser(User user);
	
}