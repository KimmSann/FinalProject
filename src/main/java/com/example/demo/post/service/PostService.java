package com.example.demo.post.service;

import com.example.demo.board.entity.Board;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.entity.Post;
import com.example.demo.user.entity.User;

public interface PostService {
	
	default PostDto entityToDto(Post post) {
		
		int boardId = post.getBoardid().getBoardid();
		int userId = post.getUserid().getUserid();
		
		PostDto dto = PostDto.builder()
				.postid(post.getPostid())
				.title(post.getTitle())
				.content(post.getContent())
				.viewcount(post.getViewcount())
				.likecount(post.getLikecount())
				.creatdate(post.getCreatdate())
				.updatdate(post.getUpdatdate())
				.boardid(boardId)
				.userid(userId)
				.build();
		
		return dto;
	}
	
	
	default Post dtoToEntity(PostDto dto) {
		
		Board boardId = Board.builder()
				.boardid(dto.getBoardid())
				.build();
		
		User userId = User.builder()
				.userid(dto.getUserid())
				.build();
		
		Post post = Post.builder()
				.postid(dto.getPostid())
				.title(dto.getTitle())
				.content(dto.getContent())
				.viewcount(dto.getViewcount())
				.likecount(dto.getLikecount())
				.creatdate(dto.getCreatdate())
				.updatdate(dto.getUpdatdate())
				.boardid(boardId)
				.userid(userId)
				.build();
		
		return post;
	}
	
}
