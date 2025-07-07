package com.example.demo.postimg.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PostimgDto {
	
	int fileid;
	
	int postid;
	
	String storedFileName;
}
