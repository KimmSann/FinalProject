package com.example.demo.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.entity.Comment;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.post.entity.Post;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentRepository repository;
	
	@Autowired
	UserService userService;

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
		
		for(CommentDto entity : dtoList) {
			int userId = entity.getUserid();
			UserDto userDto = userService.read(userId);
			entity.setNickname(userDto.getNickname());
		}
		
		return dtoList;
	}
	
	// 마이페이지에 출력
	@Override
	public List<CommentDto> getListByEmail(String email) {
		
		UserDto userDto = userService.readByEmail(email);
		
		User user = User.builder()
				.userid(userDto.getUserid())
				.build();
		
		List<Comment> result = repository.findByUser(user);
		List<CommentDto> list = new ArrayList<>();
		
		list = result.stream()
				.map(entity -> entityToDto(entity))
				.collect(Collectors.toList());
		
		return list;
	}


	@Override
	public boolean remove(int commentId, String email) {
		Optional<Comment> optional = repository.findById(commentId);
		
		if(optional.isEmpty()) {
			return false;
		}
		Comment comment = optional.get();
		String writeEmail = comment.getUser().getEmail();
		if(!writeEmail.equals(email)) {
			return false;
		}
		repository.deleteById(commentId);
		return true;
		
	}


}
