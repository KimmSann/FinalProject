package com.example.demo.board.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.service.BoardService;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.service.PostService;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PostService postService;
	
	
	@GetMapping("/category")
	public String qna(@RequestParam("boardId") int boardId
			,@PageableDefault(size = 10) Pageable pageable
			,Model model) {
		// Q&A 메인화면
		// post, 유저이름, 조회수 등등 여러가지 출력해야함
		// 필요한 데이터 : board 이름, post 정보들 이정도
		// 모든 post를 가져올 순 없으니 qna만 가져오기
		// 나중에 qna 창에 들어갈 떄 번호 가져오기
		// 번호 말고 보트를 직접 가져오게 하기
		BoardDto boardDto = boardService.getBoardInfo(boardId);
		
		Page<PostDto> postDto = postService.category(boardId, pageable);
		
		// 모델의 포스트 값만 가져옴 이걸 출력하기
		
		model.addAttribute("boardDto", boardDto);
		model.addAttribute("postDto", postDto);		
		return "board/category";
	}
	
	
}


//@GetMapping("/read")
//public void read(@RequestParam(name = "no") int postid, Model model) {
//
//	// 댓글 목록 List로 받고 뿌리기 타임리프로 반복문 써야할듯
//	List<CommentDto> commentList = commentService.getList(postid);
//	// postid로 유저 아이디 찾기
//	PostDto postDto = postservice.read(postid);
//	int userId = postDto.getUserid();
//	
//	UserDto userDto = userservice.read(userId);
//	
//	model.addAttribute("postDto", postDto);
//	model.addAttribute("userDto", userDto);
//	model.addAttribute("commentList", commentList);
//	
//}
