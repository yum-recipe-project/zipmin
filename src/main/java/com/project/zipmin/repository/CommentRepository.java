package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.zipmin.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	// 오래된순
	Page<Comment> findAllByOrderByPostdateAsc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByPostdateAsc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByPostdateAsc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByPostdateAsc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByComment_IdDescIdAsc(String tablename, int recodenum, Pageable pageable);

	

	// 최신순
	Page<Comment> findAllByOrderByPostdateDesc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByPostdateDesc(String keyword, Pageable pageable);

	Page<Comment> findByTablenameOrderByPostdateDesc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByPostdateDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByComment_IdDescIdDesc(String tablename, int recodenum, Pageable pageable);
	

	
	// 인기순
	Page<Comment> findAllByOrderByLikecountDescIdAsc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByLikecountDescIdAsc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByLikecountDescIdAsc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByLikecountDescIdAsc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByLikecountDescIdAsc(String tablename, int recodenum, Pageable pageable);
	
	

	// 비인기순
	Page<Comment> findAllByOrderByLikecountAscIdAsc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByLikecountAscIdAsc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByLikecountAscIdAsc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByLikecountAscIdAsc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByLikecountAscIdAsc(String tablename, int recodenum, Pageable pageable);
	
	
	
	// 신고순
	Page<Comment> findAllByOrderByReportcountDescIdAsc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByReportcountDescIdAsc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByReportcountDescIdAsc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByReportcountDescIdAsc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByReportcountDescIdAsc(String tablename, int recodenum, Pageable pageable);

	
	
	// 미신고순
	Page<Comment> findAllByOrderByReportcountAscIdAsc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByReportcountAscIdAsc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByReportcountAscIdAsc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByReportcountAscIdAsc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByReportcountAscIdAsc(String tablename, int recodenum, Pageable pageable);
	
	
	
	
	Page<Comment> findByUserId(int userId, Pageable pageable);
	
    int countByTablenameAndRecodenum(@Param("tablename") String tablename, @Param("recodenum") Integer recodenum);
}
