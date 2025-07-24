package com.example.demo.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.post.dto.PostDto;
import com.example.demo.post.repository.PostRepository;
import com.example.demo.post.service.PostService;

@SpringBootTest
public class PostTest {
	
	@Autowired
	PostService service;
	
	@Autowired
	PostRepository repository;
	
	
	@Test
	void 게시물등록() {
		PostDto dto = PostDto.builder()
				.title("진짜 큐엔에이")
				.content("ㅎㅇㅎㅇ 아니? 안 ㅎㅇㅎㅇ")
				.boardid(1)
				.userid(1)
				.build();
		
		
		int num = service.register(dto);
		
		System.out.println(num);
	}
	
//	@Test
//	void 게시물읽기() {
//		PostDto dto = service.read(2);
//		System.out.println(dto);
//	}
	
	@Test
	void 게시물리스트출력() {
		List<PostDto> list = service.getList();
	    for (PostDto dto : list) {
	        System.out.println(dto);
	    }
	}
	
	@Test
	void 게시물삭제() {
		service.remove(2);

	}
	
	
	// 좋아요와 조회수는 컨트롤러 만들 때 하기
	
	
}
