package com.example.sb.board;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.sb.user.UserEntity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardService {

	private final BoardRepository boardRepository;
	private final LikeRepository likeRepository;

	public void create(String title, String content, UserEntity userEntity, Integer pcode1, Integer pcode2,
			Integer pcode3) {
		BoardEntity boardEntity = new BoardEntity();

		boardEntity.setTitle(title);
		boardEntity.setContent(content);
		boardEntity.setCreateDate(LocalDateTime.now());
		boardEntity.setAuthor(userEntity);
		boardEntity.setPCode1(pcode1);
		boardEntity.setPCode2(pcode2);
		boardEntity.setPCode3(pcode3);

		boardRepository.save(boardEntity);

	}

	public List<BoardEntity> getList() {
		// TODO Auto-generated method stub
		return boardRepository.findAll();
	}

	public BoardEntity getBoard(Long bCode) {
		Optional<BoardEntity> be = boardRepository.findById(bCode);

		return be.get();
	}

	// visitCount update 해주는 코드
	@Transactional
	public int updateVisit(Long bCode) {
		return boardRepository.visitCount(bCode);
	}

	// 게시글 수정시 DB에 저장하는 코드
	public void boardModify(Long bCode, String title, String content, Integer pcode1, Integer pcode2, Integer pcode3) {

		BoardEntity boardEntity = getBoard(bCode);

		boardEntity.setTitle(title);
		boardEntity.setContent(content);
		boardEntity.setModifyDate(LocalDateTime.now());
		boardEntity.setPCode1(pcode1);
		boardEntity.setPCode2(pcode2);
		boardEntity.setPCode3(pcode3);

		boardRepository.save(boardEntity);

	}

	// 게시글 삭제버튼 클릭시 삭제하는 코드
	public void delete(BoardEntity boardEntity) {
		boardRepository.delete(boardEntity);
	}

	public Page<BoardEntity> getBoardList(int page, String kw, String searchType, String sortType) {
		List<Sort.Order> sort = new ArrayList<>();

		sort.add(Sort.Order.desc(sortType));
//		if(sortType.equals("visitCount")) {
//	         sort.add(Sort.Order.desc("visitCount"));
//	      } else if (sortType.equals("liked")) {
//	          sort.add(Sort.Order.desc("liked"));
//	      } else {
//	         sort.add(Sort.Order.desc("createDate"));
//	      }

		Pageable pageable = PageRequest.of(page, 10, Sort.by(sort));

		kw = "%" + kw + "%";

		if (searchType.equals("title")) {
			return boardRepository.findByTitleLike(pageable, kw);
		} else if (searchType.equals("content")) {
			return boardRepository.findByContentLike(pageable, kw);
		} else if (searchType.equals("nickName")) {
			return boardRepository.findAllByKeyword(pageable, kw);
		} else {
			return boardRepository.findAll(pageable);
		}
	}

	// MainCotroller와 index에 사용할 메서드
	public List<BoardEntity> getHiVcnList() {
		return boardRepository.findTop5ByOrderByVisitCountDesc();
	}

	// 좋아요
	public void likeCount(BoardEntity board, UserEntity user) {

		if (likeRepository.findByBoardAndUser(board, user) == null) {
			// 좋아요를 누른적 없다면 LikeBoard 생성 후, 좋아요 처리
			board.setLiked(board.getLiked() + 1);
			boardRepository.save(board);
			LikeEntity like = new LikeEntity(); // true 처리
			like.setUser(user);
			like.setBoard(board);
			likeRepository.save(like);
		} else {
			LikeEntity like = likeRepository.findByBoardAndUser(board, user);
			like.unLikeEntity(board);
			likeRepository.delete(like);
		}
	}

}
