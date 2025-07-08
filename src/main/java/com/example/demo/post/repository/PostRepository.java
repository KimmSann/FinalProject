package com.example.demo.post.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.board.entity.Board;
import com.example.demo.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	List<Post> findByBoardid(Board board);
	
}
