package com.example.demo.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.entity.Post;
import com.example.demo.post.repository.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired 
	PostRepository repository;


	

	@Override
	public int register(PostDto dto) {
		try {
//			System.out.println(dto);
			
			// boardid는 어떻게?
			// boardId랑 어떻게 연결해야할듯 
			Post entity = dtoToEntity(dto);
			
			repository.save(entity);
			int newPost = entity.getPostid();
			
			System.out.println(newPost);
			
			return newPost;
			
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			return 0;
		}
	}

	
	@Override
	public PostDto read(int postId) {
		try {
			Optional<Post> optional = repository.findById(postId);
			
			if(optional.isPresent()) {
				Post post = optional.get();
				PostDto dto = entityToDto(post);
				
				return dto;
			}
			
			return null;	
			
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			return null;
		}
	}

	
	// 나중에 페이징처리 할 예정
	@Override
	public List<PostDto> getList() {
		
		List<Post> result = repository.findAll();
		List<PostDto> list = new ArrayList<>();
		
		list = result.stream()
				.map(entity -> entityToDto(entity))
				.collect(Collectors.toList());
		
		return list;
	}

	@Override
	public boolean modify(PostDto dto) {
		// postimg때문에 나중에 추가 예정
		return false;
	}

	@Override
	public boolean remove(int postId) {
		Optional<Post> result = repository.findById(postId);
		
		if(result.isPresent()) {
			repository.deleteById(postId);
			return true;
		}
		
		return false;
	}


	
	
	@Override
	public int viewcount(int postId) {
		
		int viewcounted = 0;
		// 번호의 값을 찾는다
		Optional<Post> list = repository.findById(postId);
		
		if(list.isPresent()) {
			Post entity = list.get();
			viewcounted = entity.getViewcount();
			entity.setViewcount(viewcounted + 1);
			repository.save(entity);
			
			return viewcounted;			
		}
		
		return 0;
	}
	

	@Override
	public int likePost(int postId) {
	    Optional<Post> optionalPost = repository.findById(postId);

	    if (optionalPost.isPresent()) {
	        Post entity = optionalPost.get();
	        int updatedCount = entity.getLikecount() + 1;
	        entity.setLikecount(updatedCount);
	        repository.save(entity);

	        return updatedCount;
	    }

	    return 0;
	}

	@Override
	public int unlikePost(int postId) {
	    Optional<Post> optionalPost = repository.findById(postId);

	    if (optionalPost.isPresent()) {
	        Post entity = optionalPost.get();
	        int updatedCount = entity.getUnlikecount() + 1;
	        entity.setUnlikecount(updatedCount);
	        repository.save(entity);

	        return updatedCount;
	    }

	    return 0;
	}


}
