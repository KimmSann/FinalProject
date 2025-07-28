package com.example.demo.post.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.board.entity.Board;
import com.example.demo.post.entity.Post;
import com.example.demo.user.entity.User;


public interface PostRepository extends JpaRepository<Post, Integer>{
	
	Page<Post> findByBoardid(Board boardid, Pageable pageable);
	
	@Query(value = "SELECT * FROM tbl_post ORDER BY likecount DESC LIMIT 3", nativeQuery = true)
	List<Post> findTop3PostsNative();
	
	List<Post> findByUserid(User userid);
	
	@Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
	Page<Post> searchByKeyword(@Param("keyword") String keyword, Pageable pageable);
	
	@Query(value = "SELECT * FROM tbl_post ORDER BY viewcount DESC LIMIT 3", nativeQuery = true)
	List<Post> findTop3PostsSee();
	
	
}
