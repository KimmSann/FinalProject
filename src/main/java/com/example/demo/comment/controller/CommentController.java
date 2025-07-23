package com.example.demo.comment.controller;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.service.CommentService;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    CommentService commentService;
    
    @Autowired
    UserService userService;
    
    // 로그인이 필요하다하기
    // 댓글에 아무것도 없으면 등록 못하게 하기
    @PostMapping("/register")
    public boolean register(@RequestBody CommentDto commentDto
    		,Principal principal
    		,RedirectAttributes redirectAttributes) {
    	
    	if(principal.getName() == null) {
    		redirectAttributes.addFlashAttribute("message","로그인을 먼저 하세요.");
    		return false;
    	}
    	
    	UserDto dto = userService.readByEmail(principal.getName());
    	commentDto.setUserid(dto.getUserid());
    	
        int commentId = commentService.register(commentDto);
        System.out.println("테스트" + commentId);
        return true;
    }

    
    @GetMapping("/list")
    public List<CommentDto> getList(@RequestParam(name = "postId") int postId) {
        return commentService.getList(postId);
    }

    
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam(name = "commentId") int commentId, Principal principal) {
        return commentService.remove(commentId, principal.getName());
    }
}
