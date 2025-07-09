package com.example.demo.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.service.CommentService;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.service.PostService;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.service.UserService;

@Controller
@RequestMapping("post")
public class PostController {
	
	@Autowired
	PostService postservice;
	
	@Autowired
	UserService userservice;
	
	@Autowired
	CommentService commentService;
	
	
	@GetMapping("/read")
	public void read(@RequestParam(name = "no") int postid, Model model) {

		// 댓글 목록 List로 받고 뿌리기 타임리프로 반복문 써야할듯
		List<CommentDto> commentList = commentService.getList(postid);
		// postid로 유저 아이디 찾기
		PostDto postDto = postservice.read(postid);
		int userId = postDto.getUserid();
		
		UserDto userDto = userservice.read(userId);
		
		model.addAttribute("postDto", postDto);
		model.addAttribute("userDto", userDto);
		model.addAttribute("commentList", commentList);
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "post/register";
	}
	
	@PostMapping("/register")
	public String registerHandler(
			@RequestParam(name = "title") String title,
			@RequestParam(name = "content") String content,
			@RequestParam(name = "boardId") int boardId,
			RedirectAttributes redirectAttributes) {
		
		// 일단 임의로 유저아이디를 가져오고 나중에 과정을 걸쳐서 추가하기
		PostDto dto = PostDto.builder()
				.title(title)
				.content(content)
				.boardid(boardId)
				.userid(1)
				.build();
		
		int registerNo = postservice.register(dto);
		redirectAttributes.addFlashAttribute("msg",registerNo);
		
		System.out.println(registerNo);
		
		return "redirect:/";
	}
	
}
