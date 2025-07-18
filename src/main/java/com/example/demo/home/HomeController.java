package com.example.demo.home;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.service.BoardService;
import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.service.CommentService;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.service.PostService;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.service.UserService;
import com.example.demo.util.S3FileUtil;

@Controller
public class HomeController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentService commentService;
	
	@Autowired
	S3FileUtil fileUtil;
	
	
	@GetMapping("/")
	public String home(Model model, Principal principal) {
		List<PostDto> bestPost = postService.getTop3Posts();
	    List<BoardDto> boardlist = boardService.getList();
	    Map<Integer, List<PostDto>> boardPostMap = new HashMap<>();
	    
	    // 값 각 board당 개시물 최대 3개만 출력할 수 있게 함
	    for (BoardDto board : boardlist) {
	        List<PostDto> posts = postService.getList().stream()
	            .filter(p -> p.getBoardid() == board.getBoardid())
	            .limit(3)
	            .collect(Collectors.toList());
	        boardPostMap.put(board.getBoardid(), posts);
	    }
	    
	    
	    // 위와 비슷하게 좋아요순으로 정렬한 값 추출하기
	    
	    model.addAttribute("bestPost", bestPost);
	    model.addAttribute("boardlist", boardlist);
	    model.addAttribute("boardPostMap", boardPostMap);
	    return "home/main";
	}
	
	@GetMapping("/home/mypage")
	public void mypage(Model model, Principal principal) {
		String email = principal.getName();
		
		// 출력 5개로 제한
		UserDto userDto = userService.readByEmail(email);
	    List<PostDto> postDto = postService.getListUserEmail(email)
				                .stream()
				                .limit(5)
				                .collect(Collectors.toList());

		List<CommentDto> commentDto = commentService.getListByEmail(email)
		                         .stream()
		                         .limit(5)
		                         .collect(Collectors.toList());
		
		model.addAttribute("userDto", userDto);
		model.addAttribute("postDto", postDto);
		model.addAttribute("commentDto", commentDto);
	}
	
	@GetMapping("/home/modify")
	public void mypageModfy(Model model, Principal principal) {
		
		//String name = principal.getName();
		
		String email = principal.getName();
		UserDto userDto = userService.readByEmail(email);
		
		model.addAttribute("userDto", userDto);
		
	}
	
	@PostMapping("/home/modify")
	public String changePage(
			@RequestParam(name = "nickname") String nickname
			,@RequestParam(name = "files") MultipartFile files){
		
		String filename = fileUtil.fileUpload(files);
		
		UserDto dto = UserDto.builder()
				.profileimg(filename)
				.nickname(nickname)
				.build();	
		boolean modifyCheck = userService.modify(dto);
		
		if(modifyCheck) {
			return "home/modify";
		}
		
		// 나중에 리다이렉트
		return "home/mypage";
	}


}
