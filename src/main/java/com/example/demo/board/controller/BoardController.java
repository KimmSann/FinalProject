package com.example.demo.board.controller;


import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	// navbar 오른쪽 구석에 조그만하게 로그인된 이름 보여주기
	@GetMapping("/category")
	public String qna(@RequestParam(value = "boardId", required = false) int boardId
			,@PageableDefault(size = 10) Pageable pageable
			,@RequestParam(value = "keyword", required = false) String keyword
			,Model model, Principal principal) {
		
		Page<PostDto> postDto;
		if(keyword != null && !keyword.trim().isEmpty()) {
			postDto = postService.searchByKeyword(keyword, pageable);
		}
		else {
			postDto = postService.category(boardId, pageable);
		}
		// 게시판 카테고리종류 가져옴
		BoardDto boardDto = boardService.getBoardInfo(boardId);
		
		// 카테고리에 따른 게시물 가져오기
		
		// 모델의 포스트 값만 가져옴 이걸 출력하기
		model.addAttribute("boardDto", boardDto);
		model.addAttribute("postDto", postDto);
		return "board/category";
	}
	
	@PostMapping("/register")
	public String register() {
		
	    BoardDto dto1 = BoardDto.builder()
	            .boardname("게임 평가")
	            .description("유저들과 게임을 평가합니다.")
	            .build();

	    BoardDto dto2 = BoardDto.builder()
	            .boardname("자유게시판")
	            .description("자유롭게 대화하세요")
	            .build();

	    BoardDto dto3 = BoardDto.builder()
	            .boardname("Q&A")
	            .description("궁금한 것을 질문해 보세요.")
	            .build();

	    int no1 = boardService.register(dto1);
	    int no2 = boardService.register(dto2);
	    int no3 = boardService.register(dto3);

	    System.out.println("등록된 게시판 번호: " + no1 + ", " + no2 + ", " + no3);
	    
	    return "redirect:/";
	}


}
