package com.example.demo.comment.service;

import java.util.List;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.entity.Post;
import com.example.demo.user.entity.User;

public interface CommentService {
	
	int register (CommentDto dto);
	
	List<CommentDto> getList(int postId);
	
	List<CommentDto> getListByNickname(String nickname);
	
	boolean remove(int commentId);
	
	void deleteById(int commentId);
	
	
	default CommentDto entityToDto(Comment comment) {
		
		int userId = comment.getUser().getUserid();
		int postId = comment.getPost().getPostid();
		
		CommentDto dto = CommentDto.builder()
				.commentid(comment.getCommentid())
				.content(comment.getContent())
				.createdate(comment.getCreatedate())
				.userid(userId)
				.postid(postId)
				.build();
		
		return dto;
	}
	
	default Comment dtoToEntity(CommentDto dto) {
		
		User userid = User.builder()
				.userid(dto.getUserid())
				.build();
		
		Post postid = Post.builder()
				.postid(dto.getPostid())
				.build();
		
		Comment comment = Comment.builder()
				.commentid(dto.getCommentid())
				.content(dto.getContent())
				.createdate(dto.getCreatedate())
				.user(userid)
				.post(postid)
				.build();
		
		return comment;
	}
	List<CommentDto> findAll();

	
}
