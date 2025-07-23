package com.example.demo.comment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.demo.util.S3FileUtil;
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

    private final S3FileUtil s3FileUtil;


	@Autowired
	CommentRepository repository;
	
	@Autowired
	UserService userService;


    CommentServiceImpl(S3FileUtil s3FileUtil) {
        this.s3FileUtil = s3FileUtil;
    }


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
	
	@Override
	public List<CommentDto> getListByNickname(String nickname) {
		
		UserDto userDto = userService.readByUserName(nickname);
		
		//User user = User.builder().userid(userDto.getUserid()).build();
		
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
	public boolean remove(int commentId) {
		Optional<Comment> comment = repository.findById(commentId);

		
		// 나중에 이름이 같은지 다른지 확인하는 로직 추가
		
		if(comment.isEmpty()) {
			return false;
		}
		repository.deleteById(commentId);
		return true;
		
	}
	
//	나중에 이름 가져와서 지우기
//	@Override
//	public boolean remove(int commentId, String loginNickname) {
//	    Optional<Comment> commentOpt = repository.findById(commentId);
//
//	    if (commentOpt.isEmpty()) {
//	        return false;
//	    }
//
//	    Comment comment = commentOpt.get();
//
//	    // 댓글 작성자 닉네임을 가져오기 위해 User 엔티티에서 꺼냄
//	    String writerNickname = comment.getUser().getNickname();
//
//	    // 닉네임이 일치하지 않으면 삭제 금지
//	    if (!writerNickname.equals(loginNickname)) {
//	        return false;
//	    }
//
//	    repository.deleteById(commentId);
//	    return true;
//	}
//
	@Override
	public List<CommentDto> findAll() {
	    List<Comment> entityList = repository.findAll();  

	    List<CommentDto> dtoList = entityList.stream()
	        .map(entity -> {
	            CommentDto dto = entityToDto(entity);
	            int userId = dto.getUserid();
	            UserDto userDto = userService.read(userId);
	            dto.setNickname(userDto.getNickname());
	            return dto;
	        })
	        .collect(Collectors.toList());

	    return dtoList;
	}

}
