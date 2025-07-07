package com.example.demo.comment.dto;

import java.time.LocalDateTime;


import jakarta.persistence.Entity;
import lombok.*;

@Entity
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
}
