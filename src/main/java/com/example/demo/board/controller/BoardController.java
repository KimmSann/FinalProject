package com.example.demo.board.controller;


import java.security.Principal;

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
		
		// 그냥 클릭 시 viewcount 증가
		
		// 게시판 카테고리종류 가져옴
		BoardDto boardDto = boardService.getBoardInfo(boardId);
		
		// 카테고리에 따른 게시물 가져
		
		// 모델의 포스트 값만 가져옴 이걸 출력하기
		model.addAttribute("boardDto", boardDto);
		model.addAttribute("postDto", postDto);
		return "board/category";
	}
	
}
