package com.example.demo.comment.controller;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    
    // 로그인이 필요하다하기
    @PostMapping("/register")
    public boolean register(@RequestBody CommentDto commentDto) {
    	// 나중에 dto.serwriter을 사용해서 이름을 미리 정해둠 뒤 출력
        int commentId = commentService.register(commentDto);
        System.out.println("테스트" + commentId);
        return true;
    }

    /** 댓글 목록 조회 */
    @GetMapping("/list")
    public List<CommentDto> getList(@RequestParam(name = "postId") int postId) {
        return commentService.getList(postId);
    }

    
    /** 댓글 삭제 */
    @DeleteMapping("/delete")
    public boolean delete(@RequestParam(name = "commentId") int commentId) {
        return commentService.remove(commentId);
    }
}
