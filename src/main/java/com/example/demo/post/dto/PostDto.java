package com.example.demo.post.dto;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PostDto {
	
	int postid;
	
	String title;
	
	String content;
	
	int viewcount;
	
	int likecount;
	
	int unlikecount;
	
    LocalDateTime creatdate;

    LocalDateTime updatdate;
    
    int boardid;
    
    int userid;
    
    String nickname;
	
}
