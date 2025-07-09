package com.example.demo.post.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.board.entity.Board;
import com.example.demo.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	Page<Post> findByBoardid(Board boardid, Pageable pageable);

	
}
