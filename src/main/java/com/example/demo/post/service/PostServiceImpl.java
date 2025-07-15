package com.example.demo.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.demo.board.entity.Board;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.entity.Post;
import com.example.demo.post.repository.PostRepository;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.service.UserService;
import com.example.demo.util.S3FileUtil;


@Service
public class PostServiceImpl implements PostService {
	@Autowired 
	PostRepository repository;

	@Autowired
	UserService userService;
	
	// aws s3에 사진 저장
	@Autowired
	S3FileUtil fileUtil;


	@Override
	public int register(PostDto dto) {
		try {
//			System.out.println(dto);
			
			// boardid는 어떻게?
			// boardId랑 어떻게 연결해야할듯 
			Post entity = dtoToEntity(dto);
			
			//String imgPath = fileUtil.fileUpload(dto.get)
			
			repository.save(entity);
			
			return entity.getPostid();
			
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

	

	@Override
	public boolean modify(PostDto dto) {
		
		Optional<Post> optional = repository.findById(dto.getPostid());
		
		if(optional.isPresent()) {
			Post entity = optional.get();
			entity.setTitle(dto.getTitle());
			entity.setContent(dto.getContent());
			return true;
		}
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


	@Override
	public List<PostDto> getList() {
		List<Post> result = repository.findAll();
		List<PostDto> list = new ArrayList<>();
		
		list = result.stream()
				.map(entity -> entityToDto(entity))
				.collect(Collectors.toList());
		
		return list;
	}
	
	// 인기글 3개 가져옴
	@Override
	public List<PostDto> getTop3Posts() {
	    List<Post> posts = repository.findTop3PostsNative();
	    return posts.stream().map(this::entityToDto).toList();
	}
	
	@Override
	public Page<PostDto> category(int boardId, Pageable pageable) {
		
		Board board = Board.builder().boardid(boardId).build();
		Page<Post> entityPage  = repository.findByBoardid(board, pageable);
		
		Page<PostDto> dtopage = entityPage.map(entity -> {
			
			PostDto dto = entityToDto(entity);
			
			int userId = dto.getUserid();
			UserDto userDto = userService.read(userId);
			dto.setNickname(userDto.getNickname());
			
			return dto;
			
		});
		
		
		return dtopage;
	}
	


//    PostDto dto = entityToDto(entity);
//
//    // 작성자 닉네임 추가
//    int userId = dto.getUserid();
//    UserDto userDto = userService.read(userId);
//    dto.setNickname(userDto.getNickname());


}
