package com.example.demo.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.service.BoardService;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.service.PostService;

@Controller
public class BoardController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PostService postService;
	
	
	@GetMapping("/")
	public String home(Model model) {
		
		List<BoardDto> boardlist = boardService.getList();
		List<PostDto> postlist = postService.getList();
		
		model.addAttribute("boardlist", boardlist);
		model.addAttribute("postlist", postlist);
		
		return "home/main";
	}
	
	
}
