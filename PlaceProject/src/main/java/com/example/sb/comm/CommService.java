package com.example.sb.comm;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.sb.board.BoardEntity;
import com.example.sb.user.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommService {
	
	private final CommRepository commRepository;
	
	// 댓글 작성후 DB에 저장
	public void create(BoardEntity boardEntity, String content, UserEntity userEntity) {
		CommEntity commEntity = new CommEntity();
		
		commEntity.setContent(content);
		commEntity.setCreateDate(LocalDateTime.now());
		commEntity.setAuthor(userEntity);
		commEntity.setBoardEntity(boardEntity);
		
		commRepository.save(commEntity);
		
	}
	
	public CommEntity getComm(Long commCode) {
		
		Optional<CommEntity> comm = commRepository.findById(commCode);
		
		return comm.get();
	}

	public Long modify(Long commCode, String content) {
		
		CommEntity commEntity = getComm(commCode);
		
		commEntity.setContent(content);
		commEntity.setModifyDate(LocalDateTime.now());
		
		commRepository.save(commEntity);
		
		return commEntity.getBoardEntity().getBCode();
	}

	public Long delete(Long commCode) {
		
		CommEntity commEntity = getComm(commCode);
		
		Long bCode = commEntity.getBoardEntity().getBCode();
		
		commRepository.deleteById(commCode);
		
		
		return bCode;
	}

}
