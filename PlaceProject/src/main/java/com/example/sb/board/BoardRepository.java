package com.example.sb.board;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	
	Page<BoardEntity> findByTitleLike(Pageable pageable, String title);
	Page<BoardEntity> findByContentLike(Pageable pageable, String content);
	List<BoardEntity> findTop5ByOrderByVisitCountDesc(); 
	
	@Query("select "
			+"distinct b "
			+"from BoardEntity b "
			+"left outer join UserEntity u1 on b.author = u1 "
			+	"where "
			+     "u1.nickName like %:kw%")
	Page<BoardEntity> findAllByKeyword(Pageable pageable, @Param("kw") String kw);
	
	@Modifying
	@Query("update BoardEntity b set b.visitCount = b.visitCount + 1 where b.bCode = :bCode")
	int visitCount(@Param("bCode")  Long bCode);
	
}
