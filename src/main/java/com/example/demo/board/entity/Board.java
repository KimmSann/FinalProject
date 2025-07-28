package com.example.demo.board.entity;


import com.example.demo.admin.entity.Admin;

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
@Table(name = "tbl_board")
public class Board {
	
	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	int boardid;
	
	@Column(length = 100)
	String boardname;
	
	@Column(length = 100)
	String description;

	
	
}
