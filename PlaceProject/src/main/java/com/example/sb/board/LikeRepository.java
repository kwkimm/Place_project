package com.example.sb.board;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.sb.user.UserEntity;

public interface LikeRepository extends JpaRepository<LikeEntity, Integer>{
   
   LikeEntity findByBoardAndUser(BoardEntity board, UserEntity user);
   

}