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
	Page<Comment> findAllByContentContainingIgnoreCaseOrderByPostdateAscIdAsc(String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameOrderByPostdateAscIdAsc(String tablename, Pageable pageable);
	Page<Comment> findAllByTablenameAndContentContainingIgnoreCaseOrderByPostdateAscIdAsc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameAndRecodenumOrderByComment_IdDescIdAsc(String tablename, int recodenum, Pageable pageable);

	

	// 최신순
	Page<Comment> findAllByOrderByPostdateDescIdDesc(Pageable pageable);
	Page<Comment> findAllByContentContainingIgnoreCaseOrderByPostdateDescIdDesc(String keyword, Pageable pageable);

	Page<Comment> findAllByTablenameOrderByPostdateDescIdDesc(String tablename, Pageable pageable);
	Page<Comment> findAllByTablenameAndContentContainingIgnoreCaseOrderByPostdateDescIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameAndRecodenumOrderByComment_IdDescIdDesc(String tablename, int recodenum, Pageable pageable);
	

	
	// 인기순
	Page<Comment> findAllByOrderByLikecountDescIdDesc(Pageable pageable);
	Page<Comment> findAllByContentContainingIgnoreCaseOrderByLikecountDescIdDesc(String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameOrderByLikecountDescIdDesc(String tablename, Pageable pageable);
	Page<Comment> findAllByTablenameAndContentContainingIgnoreCaseOrderByLikecountDescIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameAndRecodenumOrderByLikecountDescIdDesc(String tablename, int recodenum, Pageable pageable);
	
	

	// 비인기순
	Page<Comment> findAllByOrderByLikecountAscIdDesc(Pageable pageable);
	Page<Comment> findAllByContentContainingIgnoreCaseOrderByLikecountAscIdDesc(String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameOrderByLikecountAscIdDesc(String tablename, Pageable pageable);
	Page<Comment> findAllByTablenameAndContentContainingIgnoreCaseOrderByLikecountAscIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameAndRecodenumOrderByLikecountAscIdDesc(String tablename, int recodenum, Pageable pageable);
	
	
	
	// 신고순
	Page<Comment> findAllByOrderByReportcountDescIdDesc(Pageable pageable);
	Page<Comment> findAllByContentContainingIgnoreCaseOrderByReportcountDescIdDesc(String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameOrderByReportcountDescIdDesc(String tablename, Pageable pageable);
	Page<Comment> findAllByTablenameAndContentContainingIgnoreCaseOrderByReportcountDescIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameAndRecodenumOrderByReportcountDescIdDesc(String tablename, int recodenum, Pageable pageable);

	
	
	// 미신고순
	Page<Comment> findAllByOrderByReportcountAscIdDesc(Pageable pageable);
	Page<Comment> findAllByContentContainingIgnoreCaseOrderByReportcountAscIdDesc(String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameOrderByReportcountAscIdDesc(String tablename, Pageable pageable);
	Page<Comment> findAllByTablenameAndContentContainingIgnoreCaseOrderByReportcountAscIdDesc(String tablename, String keyword, Pageable pageable);
	
	Page<Comment> findAllByTablenameAndRecodenumOrderByReportcountAscIdDesc(String tablename, int recodenum, Pageable pageable);
	
	
	
	
	Page<Comment> findByUserId(int userId, Pageable pageable);
	
    int countByTablenameAndRecodenum(@Param("tablename") String tablename, @Param("recodenum") Integer recodenum);
}
