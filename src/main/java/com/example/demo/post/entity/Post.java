package com.example.demo.post.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.demo.board.entity.Board;
import com.example.demo.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EntityListeners(value = { AuditingEntityListener.class }) 
@Table(name = "tbl_post")
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int postid;
	
	@Column(length = 100)
	String title;
	
	@Column(length = 200)
	String content;
	
	@Column
	int viewcount;
	
	@Column
	int likecount;
	
	@Column
	int unlikecount;

    @CreatedDate
    LocalDateTime creatdate;

    @LastModifiedDate
    LocalDateTime updatdate;
    
    @ManyToOne
    @JoinColumn(name = "boardid")
    Board boardid;
    
    @ManyToOne
    @JoinColumn(name = "userid")
    User userid;
    
    
}
