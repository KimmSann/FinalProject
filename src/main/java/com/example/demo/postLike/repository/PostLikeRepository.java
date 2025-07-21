package com.example.demo.postLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.post.entity.Post;
import com.example.demo.postLike.entity.PostLike;
import com.example.demo.user.entity.User;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer>{
	
    boolean existsByUserAndPost(User user, Post post);
}
