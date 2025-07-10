package com.example.demo.post.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.service.CommentService;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.service.PostService;
import com.example.demo.postimg.dto.PostimgDto;
import com.example.demo.postimg.service.PostimgService;
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
	
	@Autowired
	PostimgService postimgService;
	

	
	
	@GetMapping("/read")
	public void read(@RequestParam(name = "no") int postid, Model model) {
		
		postservice.viewcount(postid);
		// 댓글 목록 List로 받고 뿌리기 타임리프로 반복문 써야할듯
		List<CommentDto> commentList = commentService.getList(postid);
		// postid로 유저 아이디 찾기
		PostDto postDto = postservice.read(postid);
		int userId = postDto.getUserid();
		
		UserDto userDto = userservice.read(userId);
		List<PostimgDto> postimgDto = postimgService.getPostImages(postid);		
		
		model.addAttribute("postDto", postDto);
		model.addAttribute("userDto", userDto);
		model.addAttribute("commentList", commentList);
		model.addAttribute("postimgDto", postimgDto);
	}
	
	@GetMapping("/register")
	public String register() {
		
		return "post/register";
	}
	
	// 이미지 추가
	@PostMapping("/register")
	public String registerHandler(
	    @RequestParam(name = "title") String title,
	    @RequestParam(name = "content") String content,
	    @RequestParam(name = "boardId") int boardId,
	    @RequestParam(name = "files", required = false) MultipartFile[] files,
	    RedirectAttributes redirectAttributes) {

	    PostDto dto = PostDto.builder()
	            .title(title)
	            .content(content)
	            .boardid(boardId)
	            .userid(1)
	            .build();

	    int registerNo = postservice.register(dto);

	    boolean hasNonEmpty = false;
	    if (files != null) {
	        for (MultipartFile f : files) {
	            if (f != null && !f.isEmpty()) {
	                hasNonEmpty = true;
	                break;
	            }
	        }
	    }

	    if (hasNonEmpty) {
	        postimgService.savePostImage(registerNo, files);
	    }

	    return "redirect:/";
	}
	
	
	@PostMapping("/like")
	public String likePost(@RequestParam("postId") int postId, RedirectAttributes redirectAttributes) {
	    int newCount = postservice.likePost(postId);
	    redirectAttributes.addFlashAttribute("message", "좋아요가 눌렸습니다! 👍");
	    return "redirect:/post/read?no=" + postId;
	}

	@PostMapping("unlike")
	public String unlikePost(@RequestParam("postId") int postId, RedirectAttributes redirectAttributes) {
	    int newCount = postservice.unlikePost(postId);
	    redirectAttributes.addFlashAttribute("message", "싫어요가 눌렸습니다! 👎");
	    return "redirect:/post/read?no=" + postId;
	}


	
}
