package com.example.demo.user.controller;

import com.example.demo.user.dto.SignupDto;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupForm(@RequestParam(value = "error", required = false) String error,
                             Model model) {
        if ("duplicate".equals(error)) {
            model.addAttribute("errorMessage", "이미 등록된 이메일입니다.");
        }
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@ModelAttribute SignupDto signupDto,
                               @RequestParam(name = "files") MultipartFile files) {
        try {
            boolean success = userService.signup(signupDto, files);
            if (success) {
                return "redirect:/signin";
            } else {
                return "redirect:/signup?error=true";
            }
        } catch (RuntimeException e) {
            return "redirect:/signup?error=duplicate";  // 이메일 중복 등 예외 발생 시
        }
    }
    // 이메일 중복 확인용
    @GetMapping("/check-email")
    @ResponseBody
    public ResponseEntity<Boolean> checkEmailDuplicate(@RequestParam("email") String email) {
        boolean isDuplicate = userService.isEmailTaken(email);
        return ResponseEntity.ok(isDuplicate);
    }
}
