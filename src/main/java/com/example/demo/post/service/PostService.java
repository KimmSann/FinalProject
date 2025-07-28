package com.example.demo.post.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.demo.board.entity.Board;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.entity.Post;
import com.example.demo.user.entity.User;

public interface PostService {
	
	// 기본 CRUD
	
	void deleteById(int postId); // 게시물 삭제
	
	int register(PostDto dto);
	
	PostDto read(int postId);
	
	// 게시물 목록 조회
	List<PostDto> getList();
	
	// 카테고리별 게시물 조회
	Page<PostDto> category(int boardId, Pageable pageable);
	
	boolean modify(PostDto dto, String email);
	
	boolean remove(int postId, String email);
	
	boolean removeAsAdmin(int postId);

	
	
	
	// 조회수 싫어요, 좋아요 수 관리
	
	int viewcount(int postId);
	
	boolean likePost(int postId, String email);
	
	boolean unlikePost(int postId, String email);
	
	List<PostDto> getTop3Posts();
	
	List<PostDto> getManySeePosts();
	
	List<PostDto> getListUserEmail(String email);
	
	Page<PostDto> searchByKeyword(String keyword, Pageable pageable);

	
	default PostDto entityToDto(Post post) {
		
		int boardId = post.getBoardid().getBoardid();
		int userId = post.getUserid().getUserid();
		 String nickname = (post.getUserid() != null && post.getUserid().getNickname() != null)
		            ? post.getUserid().getNickname()
		            : "알 수 없음";
		
		PostDto dto = PostDto.builder()
				.postid(post.getPostid())
				.title(post.getTitle())
				.content(post.getContent())
				.viewcount(post.getViewcount())
				.likecount(post.getLikecount())
				.unlikecount(post.getUnlikecount())
				.creatdate(post.getCreatdate())
				.updatdate(post.getUpdatdate())
				.boardid(boardId)
				.userid(userId)
				.nickname(nickname)
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
				.unlikecount(dto.getUnlikecount())
				.creatdate(dto.getCreatdate())
				.updatdate(dto.getUpdatdate())
				.boardid(boardId)
				.userid(userId)
				.build();
		
		return post;
	}
	
	
	List<PostDto> findAll();

	
	
}
