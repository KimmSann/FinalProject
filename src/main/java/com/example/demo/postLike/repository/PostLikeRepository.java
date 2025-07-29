package com.example.demo.postLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.post.entity.Post;
import com.example.demo.postLike.entity.PostLike;
import com.example.demo.user.entity.User;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer>{
	
    boolean existsByUserAndPost(User user, Post post);
    void deleteByPost(Post post);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM PostLike pl WHERE pl.post.postid = :postId")
    void deleteByPostPostid(@Param("postId") int postId);
    
}
