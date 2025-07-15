package com.example.demo.postimg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.post.entity.Post;
import com.example.demo.postimg.entity.Postimg;

public interface PostimgRepository extends JpaRepository<Postimg, Integer> {
	List<Postimg> findByPostid(Post postid);
	void deleteByPostid(Post post);
}
