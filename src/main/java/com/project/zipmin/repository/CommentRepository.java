package com.project.zipmin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.zipmin.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	
	// 오래된순
	Page<Comment> findAllByOrderByPostdateAscIdAsc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByPostdateAscIdAsc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByPostdateAscIdAsc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByPostdateAscIdAsc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByComment_IdDescIdAsc(String tablename, int recodenum, Pageable pageable);

	

	// 최신순
	Page<Comment> findAllByOrderByPostdateDescIdDesc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByPostdateDescIdDesc(String keyword, Pageable pageable);

	Page<Comment> findByTablenameOrderByPostdateDescIdDesc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByPostdateDescIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByComment_IdDescIdDesc(String tablename, int recodenum, Pageable pageable);
	

	
	// 인기순
	Page<Comment> findAllByOrderByLikecountDescIdDesc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByLikecountDescIdDesc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByLikecountDescIdDesc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByLikecountDescIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByLikecountDescIdDesc(String tablename, int recodenum, Pageable pageable);
	
	

	// 비인기순
	Page<Comment> findAllByOrderByLikecountAscIdDesc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByLikecountAscIdDesc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByLikecountAscIdDesc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByLikecountAscIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByLikecountAscIdDesc(String tablename, int recodenum, Pageable pageable);
	
	
	
	// 신고순
	Page<Comment> findAllByOrderByReportcountDescIdDesc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByReportcountDescIdDesc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByReportcountDescIdDesc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByReportcountDescIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByReportcountDescIdDesc(String tablename, int recodenum, Pageable pageable);

	
	
	// 미신고순
	Page<Comment> findAllByOrderByReportcountAscIdDesc(Pageable pageable);
	Page<Comment> findByContentContainingIgnoreCaseOrderByReportcountAscIdDesc(String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameOrderByReportcountAscIdDesc(String tablename, Pageable pageable);
	Page<Comment> findByTablenameAndContentContainingIgnoreCaseOrderByReportcountAscIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findByTablenameAndRecodenumOrderByReportcountAscIdDesc(String tablename, int recodenum, Pageable pageable);
	
	
	
	
	Page<Comment> findByUserId(int userId, Pageable pageable);
	
    int countByTablenameAndRecodenum(@Param("tablename") String tablename, @Param("recodenum") Integer recodenum);
}
