package com.example.demo.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.comment.entity.Comment;
import com.example.demo.post.entity.Post;
import com.example.demo.user.entity.User;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	// 게시물 조회
	List<Comment> findByPost(Post post);
	
	List<Comment> findByUser(User user);
	
	void deleteByPost(Post post);

	 @Modifying
	    @Transactional
	    @Query("DELETE FROM Comment c WHERE c.post.postid = :postId")
	    void deleteByPostPostid(@org.springframework.data.repository.query.Param("postId") int postId);
	}
	  
	  
	
