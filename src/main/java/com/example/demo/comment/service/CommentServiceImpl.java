package com.example.demo.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.post.entity.Post;

@Service
public class CommentServiceImpl implements CommentService{


	@Autowired
	CommentRepository repository;


	@Override
	public int register(CommentDto dto) {
		try {
			Comment entity = dtoToEntity(dto); 	
			repository.save(entity);
			
			return entity.getCommentid();			
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			return 0;
		}
	}

	@Override
	public List<CommentDto> getList(int postId) {
		
		// 유저 이름은 prical로 추가 가능
		Post post = Post.builder().postid(postId).build();
		List<Comment> entityList = repository.findByPost(post);
		List<CommentDto> dtoList = new ArrayList<>();
		for (Comment entity : entityList) {
			CommentDto dto = entityToDto(entity);
			dtoList.add(dto);
		}
		
		return dtoList;
	}

	@Override
	public boolean remove(int commentId) {
		Optional<Comment> comment = repository.findById(commentId);
		
		
		// 나중에 이름이 같은지 다른지 확인하는 로직 추가
		
		if(comment.isEmpty()) {
			return false;
		}
		repository.deleteById(commentId);
		return true;
		
	}

}
