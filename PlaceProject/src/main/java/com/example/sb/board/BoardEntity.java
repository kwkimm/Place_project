package com.example.sb.board;

import java.time.LocalDateTime;
import java.util.List;

import com.example.sb.comm.CommEntity;
import com.example.sb.user.UserEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BoardEntity {
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long bCode;
   
   private String title;
   
   @Column(length = 2000)
   private String content;
   private LocalDateTime createDate;
   
   private LocalDateTime modifyDate;
   
   // many to one, 외래키 한유저에 여러 게시글
   @ManyToOne
   private UserEntity author;
   
   @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.REMOVE) // 글 하나에 여러 댓글
   private List<CommEntity> commList;
   
   private Integer visitCount = 0;
   
   // 코스장소 pCode
   private Integer pCode1;
   private Integer pCode2;
   private Integer pCode3;
   
   public Integer getPcode(int i) {
      if(i == 0) 
         return pCode1;
      else if(i == 1) 
         return pCode2;
      else
         return pCode3;
   }
   
   
   // 좋아요
   private Integer liked=0;

}