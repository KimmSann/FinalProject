package com.example.demo.board.service;

import java.util.List;

import com.example.demo.admin.entity.Admin;
import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.entity.Board;

public interface BoardService {
	
	int register (BoardDto dto);
	
	List<BoardDto> getList();  // 전체 게시판 목록
	
	boolean remove(int no);
	
	
	
	
	default BoardDto entityToDto(Board board) {
		
		int adminId = board.getAdminid().getAdminid();
		
		BoardDto dto = BoardDto.builder()
				.boardid(board.getBoardid())
				.boardname(board.getBoardname())
				.description(board.getDescription())
				.adminid(adminId)
				.build();
		
		return dto;
	}
	
	default Board dtoToEntity (BoardDto dto) {
		
		Admin admin = Admin.builder()
			    .adminid(dto.getAdminid())
			    .build();
		
		Board board = Board.builder()
				.boardid(dto.getBoardid())
				.boardname(dto.getBoardname())
				.description(dto.getDescription())
				.adminid(admin)
				.build();
		
		return board;
	}
	
}
