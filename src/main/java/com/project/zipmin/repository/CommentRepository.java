package com.project.zipmin.repository;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.zipmin.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	@Query("SELECT c FROM Comment c " +
				"LEFT JOIN FETCH c.user " +
				"LEFT JOIN FETCH c.comment " +
				"WHERE c.tablename = :tablename AND c.recodenum = :recodenum " +
				"ORDER BY c.id ASC")
    List<Comment> findAllByTablenameAndRecodenumOrderByIdAsc(
        @Param("tablename") String tablename,
        @Param("recodenum") int recodenum
    );
	
	@Query("SELECT c FROM Comment c " +
				"LEFT JOIN FETCH c.user " +
				"LEFT JOIN FETCH c.comment " +
				"WHERE c.tablename = :tablename AND c.recodenum = :recodenum " +
				"ORDER BY c.comment.id DESC, c.id ASC")
	List<Comment> findAllByTablenameAndRecodenumOrderByIdDesc(
		@Param("tablename") String tablename,
		@Param("recodenum") int recodenum
	);


	@Query("SELECT c FROM Comment c " +
		       "LEFT JOIN FETCH c.user u " +
		       "LEFT JOIN FETCH c.comment " +
		       "WHERE c.tablename = :tablename AND c.recodenum = :recodenum " +
		       "ORDER BY c.likecount DESC, c.id ASC")
	List<Comment> findAllByTablenameAndRecodenumOrderByLikecount(
	    @Param("tablename") String tablename,
	    @Param("recodenum") int recodenum);
}
