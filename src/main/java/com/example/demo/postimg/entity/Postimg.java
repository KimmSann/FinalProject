package com.example.demo.postimg.entity;

import com.example.demo.post.entity.Post;

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
@Table(name = "tbl_postimg")
public class Postimg {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int fileid;
	
	@Column(columnDefinition = "TEXT")
	String storedFileName;
	
	@ManyToOne
	@JoinColumn(name = "postid")
	Post postid;
	
	
}
