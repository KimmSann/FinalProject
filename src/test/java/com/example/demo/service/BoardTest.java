package com.example.demo.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.repository.BoardRepository;
import com.example.demo.board.service.BoardService;

@SpringBootTest
public class BoardTest {
	@Autowired
	BoardRepository repository;
	
	@Autowired
	BoardService service;
	
	@Test
	void 보드추가() {
		
//		// 일부로 에러냄
//		BoardDto dto = BoardDto.builder()
//				.boardname("Q&A")
//				.description("질의응답을 해야해요")
//				.adminid(2)   // 관리자가 없으니 에러남
//				.build();

		
		BoardDto dto = BoardDto.builder()
				.boardname("Q&A")
				.description("질의응답을 해야해요")
				.adminid(1)
				.build();
		
		int no = service.register(dto);
		
		System.out.println(no);
	}

}
