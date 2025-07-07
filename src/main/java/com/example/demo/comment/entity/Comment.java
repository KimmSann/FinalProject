package com.example.demo.comment.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.board.entity.Board;
import com.example.demo.post.entity.Post;
import com.example.demo.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Table(name = "tbl_comment")
public class Comment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int commentid;
	
	@Column(length = 100)
	String content;
	
	@CreatedDate
	LocalDateTime createdate;	
	
	@ManyToOne
	@JoinColumn(name = "userid")
	User userid;
	
	@ManyToOne
	@JoinColumn(name = "postid")
	Post postid;	
}
