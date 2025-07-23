package com.example.demo.user.dto;

import java.time.LocalDateTime;

import com.example.demo.user.entity.User;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class UserDto {
	
	int userid;
	
	String name;
	
	String email;
	
	String password;
	
	String nickname;
	
	String profileimg;
	
	String role;
	
	String loginType;
	
	LocalDateTime createdate;
	
	
	public UserDto(User user) {
        this.userid = user.getUserid();
        this.name = user.getName();
        this.email = user.getEmail();
        this.nickname = user.getNickname();
        this.profileimg = user.getProfileimg();
        this.role = user.getRole();
        this.loginType = user.getLoginType();
     }
	
	
}