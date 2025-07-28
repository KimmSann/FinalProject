package com.example.demo.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.admin.service.AdminService;
import com.example.demo.comment.service.CommentService;
import com.example.demo.post.service.PostService;
import com.example.demo.user.service.UserService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;
    private final AdminService adminService;

    // 관리자 대시보드
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("posts", postService.findAll());
        model.addAttribute("comments", commentService.findAll());
        return "admin/dashboard"; // templates/admin/dashboard.html
    }

    // 권한 변경
    @PostMapping("/grant-admin/{userId}")
    public String grantAdmin(@PathVariable("userId") int userId) {
        adminService.grantAdmin(userId);
        return "redirect:/admin/dashboard";
    }

 // 유저 강제 탈퇴
    @PostMapping("/delete-user/{userId}")
    public String deleteUser(@PathVariable("userId") int userId) {
        boolean success = adminService.forceDeleteUser(userId);  // adminService에서 강제 탈퇴 처리
        if (!success) {
            // 실패 처리 (필요하면)
            System.out.println("유저 삭제 실패");
            // 예: 에러 페이지로 리다이렉트하거나 메시지 전달 가능
        }
        return "redirect:/admin/dashboard";
    }
    // 게시물 삭제
    @PostMapping("/delete-post/{postId}")
    public String deletePost(@PathVariable("postId") int postId) {
        postService.deleteById(postId);
        return "redirect:/admin/dashboard";
    }

    // 댓글 삭제
    @PostMapping("/delete-comment/{commentId}")
    public String deleteComment(@PathVariable("commentId") int commentId) {
        commentService.deleteById(commentId);
        return "redirect:/admin/dashboard";
    }
}
