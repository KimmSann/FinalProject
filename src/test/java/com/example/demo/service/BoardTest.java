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
		
		// 보드는 qna 자유게시판 등 약 4가지 보드를 수정하지않고 고정할거기 떄문에 테스트할 떄 고정으로 생성
		
		BoardDto dto = BoardDto.builder()
				.boardname("Q&A")
				.description("질의응답을 해야해요")
				.adminid(3)   // 관리자가 없으니 에러남
				.build();

		
//		BoardDto dto = BoardDto.builder()
//				.boardname("자유게시판")
//				.description("자유롭게 대화하세요")
//				.adminid(1)
//				.build();
		
		

//		BoardDto dto = BoardDto.builder()
//				.boardname("게임 평가")
//				.description("유저들과 게임을 평가합니다.")
//				.adminid(1)   // 관리자가 없으니 에러남
//				.build();
		int no = service.register(dto);
		
		System.out.println(no);
	}

}
