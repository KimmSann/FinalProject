package com.example.demo.post.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.board.entity.Board;
import com.example.demo.post.entity.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
	
	Page<Post> findByBoardid(Board boardid, Pageable pageable);
	
	@Query(value = "SELECT * FROM tbl_post ORDER BY likecount DESC LIMIT 3", nativeQuery = true)
	List<Post> findTop3PostsNative();
}
