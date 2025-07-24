package com.example.demo.post.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.board.entity.Board;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.entity.Post;
import com.example.demo.post.repository.PostRepository;
import com.example.demo.postLike.entity.PostLike;
import com.example.demo.postLike.repository.PostLikeRepository;
import com.example.demo.postimg.repository.PostimgRepository;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;
import com.example.demo.user.service.UserService;
import com.example.demo.util.S3FileUtil;


@Service
public class PostServiceImpl implements PostService {


	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	PostRepository repository;

	@Autowired
	UserService userService;
	
	@Autowired
	PostLikeRepository postLikeRepository;
	
	@Autowired
	PostimgRepository postimgRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	
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

	@Transactional
	@Override
	public boolean modify(PostDto dto, String email) {
		try {
		    Optional<Post> optional = repository.findById(dto.getPostid());
		    Post post = optional.get();

		    if (optional.isEmpty()) {
		        return false;
		    }
		    // 이메일을 서로 비교한다
		    String writerEmail = post.getUserid().getEmail();
		    
		    if(!writerEmail.equals(email)) {
		    	return false;
		    }
		    if(optional.isPresent()) {
		    	post.setContent(dto.getContent());
		    	post.setTitle(dto.getTitle());
		    	repository.save(post);
		    	return true;
		    }
		    return false;
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			return false;
		}

	}
	
	@Transactional
	@Override
	public boolean remove(int postId, String email) {
	    try {
	        Optional<Post> result = repository.findById(postId);

	        if (result.isEmpty()) return false;

	        Post post = result.get();

	        String writeEmail = post.getUserid().getEmail();
	        if (!writeEmail.equals(email)) {
	        	return false;
	        }
	        
	        Post entity = post.builder()
	        		.postid(postId)
	        		.build();
	        
	        // 댓글 삭제
	        commentRepository.deleteByPost(post);
	        // 이미지 삭제
	        postimgRepository.deleteByPostid(entity);
	        // 좋아요 삭제
	        postLikeRepository.deleteByPost(post);

	        // 그 후 게시글 삭제
	        repository.delete(post);

	        return true;
	    } catch (Exception e) {
	        System.out.println("ERROR : " + e);
	        return false;
	    }
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
	public boolean likePost(int postId, String email) {
		try {
		    Optional<User> userOpt = userRepository.findByEmail(email);
		    Optional<Post> postOpt = repository.findById(postId);

		    if (userOpt.isEmpty() || postOpt.isEmpty()) return false;

		    User user = userOpt.get();
		    Post post = postOpt.get();

		    boolean alreadyClicked = postLikeRepository.existsByUserAndPost(user, post);
		    if (alreadyClicked) return false;

		    // 좋아요 처리	
		    int updatedCount = post.getLikecount() + 1;
		    post.setLikecount(updatedCount);
		    repository.save(post);

		    // 기록 저장
		    PostLike like = new PostLike();
		    like.setUser(user);
		    like.setPost(post);
		    like.setLike(true);  // 좋아요 true
		    postLikeRepository.save(like);

		    return true;
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			return false;
		}

	}

	@Override
	public boolean unlikePost(int postId, String email) {
		// 나중에 값이 들엉가있으면 처리 해보기
		try {
		    Optional<User> userOpt = userRepository.findByEmail(email);
		    Optional<Post> postOpt = repository.findById(postId);

		    if (userOpt.isEmpty() || postOpt.isEmpty()) return false;

		    User user = userOpt.get();
		    Post post = postOpt.get();

		    boolean alreadyClicked = postLikeRepository.existsByUserAndPost(user, post);
		    if (alreadyClicked) return false;
		    	
		    // 싫어요 처리
		    int updatedCount = post.getUnlikecount() + 1;
		    post.setUnlikecount(updatedCount);
		    repository.save(post);

		    PostLike like = new PostLike();
		    like.setUser(user);
		    like.setPost(post);
		    like.setLike(true);  // 좋아요 true
		    postLikeRepository.save(like);

		    return true;
		} catch (Exception e) {
			System.out.println("ERROR : " + e);
			return false;
		}

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
	public List<PostDto> getListUserEmail(String nickname) {
		
		UserDto dto = userService.readByEmail(nickname);
		 
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
	public List<PostDto> getTop3Posts() {
	    List<Post> posts = repository.findTop3PostsNative();
	    return posts.stream().map(this::entityToDto).toList();
	}
	


	@Override
	public List<PostDto> getManySeePosts() {
		List<Post> posts = repository.findTop3PostsSee();
		return posts.stream().map(this::entityToDto).toList();
	}
}
