package com.example.demo.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository repository;


	@Override
	public boolean register(UserDto dto) {
		
		try {
			int id = dto.getUserid();
			UserDto getDto = read(id);
			
			if(getDto != null) {
				System.out.println("사용중인 아이디 입니다.");
				return false;
			}
			else {
				User entity = dtoToEntity(getDto);
				
				// 여기에 인코딩 코드 넣기
				
				repository.save(entity);
				System.out.println("저장 완료");
				return true;
			}
		}
		catch (Exception e) {
			System.out.println("ERROR : " + e);
			return false;
		}
	}

	@Override
	public UserDto read(int id) {
		Optional<User> result = repository.findById(id);
		
		if(result.isPresent()) {
			User user = result.get();
			return entityToDto(user);
		}
		else {
			return null;			
		}
	}
	
}
