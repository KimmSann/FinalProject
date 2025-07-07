package com.example.demo.board.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
	
	int boardid;
	
	String boardname;
	
	String description;
	
	// 나중에 관리자 추가하기
	
}
