package com.example.demo.user.service;

import com.example.demo.user.dto.UserDto;
import com.example.demo.user.entity.User;

public interface UserService {
	
	boolean register(UserDto dto);
	
	UserDto read(int id);
	
	UserDto readByUserName(String nickname);
	
	default UserDto entityToDto (User user) {
		
		UserDto dto = UserDto.builder()
				.userid(user.getUserid())
				.name(user.getName())
				.email(user.getEmail())
				.password(user.getPassword())
				.nickname(user.getNickname())
				.profileimg(user.getProfileimg())
				.role(user.getRole())
				.createdate(user.getCreatedate())
				.build();
		
		return dto;
	}
	
	default User dtoToEntity(UserDto dto) {
		
		User user = User.builder()
				.userid(dto.getUserid())
				.name(dto.getName())
				.email(dto.getEmail())
				.password(dto.getPassword())
				.nickname(dto.getNickname())
				.profileimg(dto.getProfileimg())
				.role(dto.getRole())
				.createdate(dto.getCreatedate())
				.build();
		
		return user;
		
	}
	
	
	
}
