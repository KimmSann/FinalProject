package com.example.demo.home;

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
import com.example.demo.post.dto.PostDto;
import com.example.demo.post.service.PostService;

@Controller
public class HomeController {
	
	@Autowired
	BoardService boardService;
	
	@Autowired
	PostService postService;
	
	
	@GetMapping("/")
	public String home(Model model) {
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

	    model.addAttribute("boardlist", boardlist);
	    model.addAttribute("boardPostMap", boardPostMap);
	    return "home/main";
	}


}
