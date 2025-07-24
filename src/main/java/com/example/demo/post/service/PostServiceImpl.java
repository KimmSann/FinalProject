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
import com.example.demo.postLike.entity.PostLike;
import com.example.demo.postLike.repository.PostLikeRepository;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.service.UserService;
import com.example.demo.util.S3FileUtil;


@Service
public class PostServiceImpl implements PostService {
	
	
	@Autowired 
	PostRepository repository;

	@Autowired
	UserService userService;
	
	@Autowired
	PostLikeRepository postLikeRepository;
	
	
	// aws s3에 사진 저장
	@Autowired
	S3FileUtil fileUtil;


	@Override
	public int register(PostDto dto) {
		try {
			Post entity = dtoToEntity(dto);
			
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
		
		// 나중에 로그인 한 유저 이름도 같이 받아서 비교 후 삭제처리하기
		Optional<Post> optional = repository.findById(dto.getPostid());
		
		if(optional.isPresent()) {
			Post entity = optional.get();
			entity.setTitle(dto.getTitle());
			entity.setContent(dto.getContent());
			return true;
		}
		return false;
	}
//	public boolean modifter(PostDto dto, String loginNickname) {
//	    Optional<Post> optional = repository.findById(dto.getPostid());
//
//	    if (optional.isEmpty()) {
//	        return false;
//	    }
//	    Post post = optional.get();
//	    String writerName = post.getUserid().getName();
//	    
//	    if(!writerName.equals(loginNickname)) {
//	    	return false;
//	    }
//	    if(optional.isPresent()) {
//	    	post.setContent(dto.getContent());
//	    	post.setTitle(dto.getContent());
//	    	return true;
//	    }
//	    return false;
//	}
	

	@Override
	public void remove(int postId) {
		Optional<Post> result = repository.findById(postId);
		
		if(result.isPresent()) {
			//Post post = result.get();
			// 나중에 로그인 한 유저 이름도 같이 받아서 비교 후 삭제처리하기
			//post.getUserid();
			
			repository.deleteById(postId);
		}
		
	}
//	@Override
//	public boolean remove(int postId, String loginNickname) {
//	    Optional<Post> result = repository.findById(postId);
//
//	    if (result.isEmpty()) {
//	        return false;
//	    }
//
//	    Post post = result.get();
//
//	    // 게시글 작성자의 닉네임
//	    String writerNickname = post.getUser().getNickname();
//	    // 닉네임이 다르면 삭제 거부
//	    if (!writerNickname.equals(loginNickname)) {
//	        return false;
//	    }
//
//	    repository.deleteById(postId);
//	    return true;
//	}
	

	
	
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
	
//	@Override
//	public int likePost(int postId, String nickname) {
//	    Optional<User> userOpt = userRepository.findByNickname(nickname);
//	    Optional<Post> postOpt = repository.findById(postId);
//
//	    if (userOpt.isEmpty() || postOpt.isEmpty()) return -1;
//
//	    User user = userOpt.get();
//	    Post post = postOpt.get();
//
//	    boolean alreadyClicked = postLikeRepository.existsByUserAndPost(user, post);
//	    if (alreadyClicked) return -1;
//
//	    // 좋아요 처리
//	    int updatedCount = post.getLikecount() + 1;
//	    post.setLikecount(updatedCount);
//	    repository.save(post);
//
//	    // 기록 저장
//	    PostLike like = new PostLike();
//	    like.setUser(user);
//	    like.setPost(post);
//	    like.setLike(true);  // 좋아요 true
//	    postLikeRepository.save(like);
//
//	    return updatedCount;
//	}

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
	
	
	
	@Override
	public List<PostDto> getListUserName(String nickname) {
		
		UserDto dto = userService.readByUserName(nickname);
		 
		User user = User.builder()
				.userid(dto.getUserid())
				.build();
		 
		List<Post> result = repository.findByUserid(user);
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


	@Override
	public Page<PostDto> searchByKeyword(String keyword, Pageable pageable) {
	    Page<Post> posts = repository.searchByKeyword(keyword, pageable);
	    
	    Page<PostDto> postdto = posts.map(entity -> {
	    	
	    	PostDto dto = entityToDto(entity);
	    	
	    	int userId = dto.getUserid();
	    	UserDto userDto = userService.read(userId);
	    	dto.setNickname(userDto.getNickname());
	    	return dto;
	    });
	    
	    return postdto;
	    
	}


	@Override
	public List<PostDto> findAll() {
		return getList();
	}
	
	 @Override
	    public void deleteById(int postId) {
	        if (repository.existsById(postId)) {
	            repository.deleteById(postId);
	        }
	    }
}
