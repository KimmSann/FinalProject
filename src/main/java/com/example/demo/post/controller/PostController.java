package com.example.demo.post.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
		
		// 일단 클릭하면 조회수 증가
		postservice.viewcount(postid);
		// postid로 유저 아이디 찾기
		PostDto postDto = postservice.read(postid);
		int userId = postDto.getUserid();
		
		UserDto userDto = userservice.read(userId);
		List<PostimgDto> postimgDto = postimgService.getPostImages(postid);		
		
		model.addAttribute("postDto", postDto);
		model.addAttribute("userDto", userDto);
		model.addAttribute("postimgDto", postimgDto);
	}
	
	@GetMapping("/register")
	public void register() {
		
	}
	
	// 이미지 추가
	@PostMapping("/register")
	public String registerHandler(
	    @RequestParam(name = "title") String title,
	    @RequestParam(name = "content") String content,
	    @RequestParam(name = "boardId") int boardId,
	    @RequestParam(name = "files", required = false) MultipartFile[] files,
	    RedirectAttributes redirectAttributes,
	    Principal principal) {
		
		UserDto userDto = userservice.readByEmail(principal.getName());

	    PostDto dto = PostDto.builder()
	            .title(title)
	            .content(content)
	            .boardid(boardId)
	            .userid(userDto.getUserid())	// 시큐리티에서 받은 아이디로 저장
	            .build();

	    int registerNo = postservice.register(dto);

	    boolean noFiles = false;
	    
	    // 파일에 아무것도 없으면 넘어가기
	    if (files != null) {
	        for (MultipartFile f : files) {
	            if (f != null && !f.isEmpty()) {
	            	noFiles = true;
	                break;
	            }
	        }
	    }
	    
	    // 사진이 있다면 등록 시작
	    if (noFiles) {
	        postimgService.savePostImage(registerNo, files);
	    }

	    return "redirect:/";
	}
	
	@PostMapping("/modify")
	public String modifySave(
			@RequestParam(name = "postId") int postId,
			@RequestParam(name = "content") String content,
			@RequestParam(name = "title") String title,
			@RequestParam(name = "newImage", required = false) MultipartFile[] file,
			Principal principal) {
		
		PostDto postdto = PostDto.builder()
				.postid(postId)	
				.content(content)
				.title(title)
				.build();
		
		boolean modiftState = postservice.modify(postdto, principal.getName());
		
		if(!modiftState) {
			// 나중에 메시지 보내기
			System.out.println("수정 실패");
			return null;
		}

		boolean noFiles = false;
	    // 파일에 아무것도 없으면 넘어가기
	    if (file != null) {
	        for (MultipartFile f : file) {
	            if (f != null && !f.isEmpty()) {
	            	noFiles = true;
	                break;
	            }
	        }
	    }
	    if (noFiles) {
	        postimgService.modify(postId, file);
	    }
		

		
		return "redirect:/";
	}
	
	
	
	
	@GetMapping("/modify")
	public void modify(@RequestParam(name = "postId") int postId, Model model) {
		// 닉네임을 가져옴
		PostDto postDto = postservice.read(postId);
		UserDto userDto = userservice.read(postDto.getUserid());
		postDto.setNickname(userDto.getNickname());
		List<PostimgDto> postimgDto = postimgService.getPostImages(postId);
		model.addAttribute("postDto",postDto);
		model.addAttribute("postimgDto", postimgDto);
	}
	
	
	@PostMapping("/like")
	public String likePost(@RequestParam("postId") int postId, RedirectAttributes redirectAttributes) {
	    int newCount = postservice.likePost(postId);
	    System.out.println(newCount);
	    redirectAttributes.addFlashAttribute("message", "좋아요가 눌렸습니다! 👍");
	    return "redirect:/post/read?no=" + postId;
	}

	
	@PostMapping("unlike")
	public String unlikePost(@RequestParam("postId") int postId, RedirectAttributes redirectAttributes) {
	    int newCount = postservice.unlikePost(postId);
	    System.out.println(newCount);
	    redirectAttributes.addFlashAttribute("message", "싫어요가 눌렸습니다! 👎");
	    return "redirect:/post/read?no=" + postId;
	}

	@PostMapping("/remove")
	public String remove(@RequestParam("postId") int postId,
			RedirectAttributes redirectAttributes,
			Principal principal) {
		
	    boolean removeState = postservice.remove(postId, principal.getName());
	    if(removeState) {
	    	redirectAttributes.addFlashAttribute("message", "게시글이 삭제되었습니다.");
	    	return "redirect:/";	    	
	    }
	    else {
			return "board/category";
		}
	}

	
	
	
}
