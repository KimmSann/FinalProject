package com.example.demo.user.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.post.repository.PostRepository;
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
	        Optional<User> existingUser = repository.findByEmail(dto.getEmail());
	        if (existingUser.isPresent()) {
	            System.out.println("이미 사용 중인 이메일입니다.");
	            return false;
	        }

	        User entity = dtoToEntity(dto);


	        repository.save(entity);
	        System.out.println("저장 완료");
	        return true;
	    } catch (Exception e) {
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
