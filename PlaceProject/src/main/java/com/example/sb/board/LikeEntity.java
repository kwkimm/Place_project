package com.example.sb.board;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.example.sb.user.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Getter
@Setter
public class LikeEntity {
   
   @Id
   @GeneratedValue(strategy = GenerationType.SEQUENCE)
   private Long lCode;
   
   @ManyToOne
   @JoinColumn(nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   private BoardEntity board;
   
   @ManyToOne
   @JoinColumn(nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
   private UserEntity user;
   
   @Column(nullable = false)
   private boolean state; // true = 좋아요, false = 좋아요 취소
   

   public LikeEntity(BoardEntity board, UserEntity user) {
      this.board = board;
      this.user = user;
      this.state = true;
   }
   
    public void unLikeEntity(BoardEntity board) {
        this.state = false;
        board.setLiked(board.getLiked() - 1);
    }
   
   

}