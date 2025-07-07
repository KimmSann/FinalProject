package com.example.demo.postimg.service;

import com.example.demo.post.entity.Post;
import com.example.demo.postimg.dto.PostimgDto;
import com.example.demo.postimg.entity.Postimg;

public interface PostimgService {
	
	
	default PostimgDto entityToDto(Postimg postimg) {
		
		int postId = postimg.getPostid().getPostid();
		
		PostimgDto dto = PostimgDto.builder()
				.fileid(postimg.getFileid())
				.storedFileName(postimg.getStoredFileName())
				.postid(postId)
				.build();
		
		return dto;
	}
	
	
	default Postimg dtoToEntity(PostimgDto dto) {
		
		Post postId = Post.builder()
				.postid(dto.getPostid())
				.build();
		
		Postimg postimg = Postimg.builder()
				.fileid(dto.getFileid())
				.storedFileName(dto.getStoredFileName())
				.postid(postId)
				.build();
		
		return postimg;
	}
	
}
