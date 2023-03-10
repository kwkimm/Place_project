package com.example.sb.comm;

import java.time.LocalDateTime;

import com.example.sb.board.BoardEntity;
import com.example.sb.user.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CommEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long commCode;
	
	private String content;
	
	// 작성일
	private LocalDateTime createDate;
	
	@ManyToOne // 한 게시글에 여러 댓글 
	private BoardEntity boardEntity;
	
	@ManyToOne // 한 유저에 여러 댓글
	private UserEntity author;
	
	private LocalDateTime modifyDate;
	
}
