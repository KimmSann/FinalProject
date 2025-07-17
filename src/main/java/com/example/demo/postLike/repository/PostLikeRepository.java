package com.example.demo.postLike.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.postLike.entity.PostLike;

public interface PostLikeRepository extends JpaRepository<PostLike, Integer>{

}
