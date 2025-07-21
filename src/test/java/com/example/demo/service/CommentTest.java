package com.example.demo.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.comment.dto.CommentDto;
import com.example.demo.comment.repository.CommentRepository;
import com.example.demo.comment.service.CommentService;

@SpringBootTest
public class CommentTest {
	
	@Autowired
	CommentRepository repository;
	
	@Autowired
	CommentService service;
	
	@Test
	void 댓글달리나_테스트() {
		CommentDto dto = CommentDto.builder()
				.content("안녕하세요....")
				.userid(2)
				.postid(1)
				.build();
		
		int no = service.register(dto);
		System.out.println(no);
	}
	
	@Test
	void 댓글조회되나_테스트() {
		List<CommentDto> list = service.getList(3);
		
	    for (CommentDto dto : list) {
	        System.out.println(dto);
	    }
	}
	
//	@Test
//	void 댓글을_삭제한다() {
//		boolean check = service.remove(3);
//		
//		if(check) {
//			System.out.println("삭제 완료");
//		}
//		else {
//			System.out.println("삭제 안됨");
//		}
//	}
	
	
	
}
