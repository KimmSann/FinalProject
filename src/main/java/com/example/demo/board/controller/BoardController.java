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
		// 그냥 클릭 시 viewcount 증가
		
		BoardDto boardDto = boardService.getBoardInfo(boardId);
		
		Page<PostDto> postDto = postService.category(boardId, pageable);
		
		// 모델의 포스트 값만 가져옴 이걸 출력하기
		
		model.addAttribute("boardDto", boardDto);
		model.addAttribute("postDto", postDto);		
		return "board/category";
	}
	
	
}
