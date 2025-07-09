package com.example.demo.comment.dto;

import java.time.LocalDateTime;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CommentDto {
	
	int commentid;
	
	String content;
	
	LocalDateTime createdate;
	
	int userid;
	
	int postid;
	
	String nickname;
}
