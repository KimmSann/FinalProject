package com.example.demo.user.dto;

import java.time.LocalDateTime;

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
	
	LocalDateTime createdate;
	
}