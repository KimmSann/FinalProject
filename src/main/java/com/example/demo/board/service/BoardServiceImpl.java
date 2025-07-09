package com.example.demo.board.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.example.demo.post.repository.PostRepository;
import com.example.demo.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.board.dto.BoardDto;
import com.example.demo.board.entity.Board;
import com.example.demo.board.repository.BoardRepository;

@Service
public class BoardServiceImpl implements BoardService{

    private final UserRepository userRepository;

    private final PostRepository postRepository;

	@Autowired
	BoardRepository repository;

    BoardServiceImpl(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
	
	// 외부에서 데이터 받음
	@Override
	public int register(BoardDto dto) {
		try {
			//System.out.println(dto);
			
			Board entity = dtoToEntity(dto);
			repository.save(entity);
			System.out.println("저장 성공");
			
			int newnum = entity.getBoardid();
			return newnum;
		}
		catch (Exception e) {
			System.out.println("ERROR : " + e);
			return 0;
		}

	}

	@Override
	public boolean remove(int no) {
		Optional<Board> result = repository.findById(no);
		
		if(result.isPresent()) {
			repository.deleteById(no);
			System.out.println("삭제가 되었습니다.");
			return true;
		}		
		return false;
	}

	@Override
	public List<BoardDto> getList() {
		// db에서 전부 찾음
		List<Board> result = repository.findAll();
		
		// dto(화면 출력용) 으로도 하나 만들기
		List<BoardDto> list = new ArrayList<>();
		
		// stream은 컬렉션의 객체를 하나씩 꺼내서 처리 가능
		list = result.stream()
				.map(entity -> entityToDto(entity))
				.collect(Collectors.toList());
		
		return list;
	}

	@Override
	public BoardDto getBoardInfo(int boardId) {
		Optional<Board> optional = repository.findById(boardId);
		
		if(optional.isPresent()) {
			Board entity = optional.get();
			BoardDto dto = entityToDto(entity);			
			return dto;
		}
		else {
			return null;			
		}
	}
	
}
