package com.project.zipmin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.zipmin.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	@Query(value = "SELECT c FROM Comment c " +
				"LEFT JOIN FETCH c.user " +
				"LEFT JOIN FETCH c.comment " +
				"WHERE c.tablename = :tablename AND c.recodenum = :recodenum " +
				"ORDER BY c.id ASC",
		   countQuery = "SELECT COUNT(c) FROM Comment c WHERE c.tablename = :tablename AND c.recodenum = :recodenum")
    Page<Comment> findByTablenameAndRecodenumOrderByIdAsc(@Param("tablename") String tablename, @Param("recodenum") int recodenum, Pageable pageable);


	@Query(value = "SELECT c FROM Comment c " +
				"LEFT JOIN FETCH c.user " +
				"LEFT JOIN FETCH c.comment " +
				"WHERE c.tablename = :tablename AND c.recodenum = :recodenum " +
				"ORDER BY c.comment.id DESC, c.id ASC",
		   countQuery = "SELECT COUNT(c) FROM Comment c WHERE c.tablename = :tablename AND c.recodenum = :recodenum")
	Page<Comment> findByTablenameAndRecodenumOrderByIdDesc(@Param("tablename") String tablename, @Param("recodenum") int recodenum, Pageable pageable);

	@Query(value = "SELECT c FROM Comment c " +
		       "LEFT JOIN FETCH c.user u " +
		       "LEFT JOIN FETCH c.comment " +
		       "WHERE c.tablename = :tablename AND c.recodenum = :recodenum " +
		       "ORDER BY c.likecount DESC, c.id ASC",
		   countQuery = "SELECT COUNT(c) FROM Comment c WHERE c.tablename = :tablename AND c.recodenum = :recodenum")
	Page<Comment> findByTablenameAndRecodenumOrderByLikecount(@Param("tablename") String tablename, @Param("recodenum") int recodenum, Pageable pageable);
}
