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

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.service.BoardService;
import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.service.CommentService;
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.service.PostService;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.service.UserService;

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
	
	
	@GetMapping("/")
	public String home(Model model) {
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
		/* 
		 * 출력해야하는 데이터들 :
		 * user 정보들   클리어
		 * 내가 작성한 게시물들  클리어
		 * 내가 작성한 댓글들   
		*/
		// 시큐리티 적용하면 principal을 사용해서 넣기
		// 나중에 이름이 아니더라도 이메일같은걸로 찾기
		// 닉네임이 중복될 수 있기 때문 아니면 아이디로 받아올 수 있을지도
		String nickname = "길동이";
		
		UserDto userDto = userService.readByUserName(nickname);
	    List<PostDto> postDto = postService.getListUserName(nickname)
				                .stream()
				                .limit(5)
				                .collect(Collectors.toList());

		List<CommentDto> commentDto = commentService.getListByNickname(nickname)
		                         .stream()
		                         .limit(5)
		                         .collect(Collectors.toList());
		
		
		model.addAttribute("userDto", userDto);
		model.addAttribute("postDto", postDto);
		model.addAttribute("commentDto", commentDto);
	}


}
