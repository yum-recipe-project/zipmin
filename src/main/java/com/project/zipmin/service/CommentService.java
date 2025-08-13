package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.LikeErrorCode;
import com.project.zipmin.dto.CommentCreateRequestDto;
import com.project.zipmin.dto.CommentCreateResponseDto;
import com.project.zipmin.dto.CommentDeleteRequestDto;
import com.project.zipmin.dto.CommentReadMyResponseDto;
import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentUpdateRequestDto;
import com.project.zipmin.dto.CommentUpdateResponseDto;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.dto.ReportCreateRequestDto;
import com.project.zipmin.dto.ReportCreateResponseDto;
import com.project.zipmin.dto.ReportDeleteRequestDto;
import com.project.zipmin.entity.Comment;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.CommentMapper;
import com.project.zipmin.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private ChompService chompService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private ReportService reportService;

	private final CommentMapper commentMapper;
	
	
	
	// 댓글 목록 조회 (오래된순)
	public Page<CommentReadResponseDto> readCommentPageOrderByIdAsc(String tablename, Integer recodenum, String keyword, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 목록 조회
		Page<Comment> commentPage = null;
		try {
			boolean hasTable = tablename != null && !tablename.isBlank();
	        boolean hasRecord = recodenum != null;
	        boolean hasKeyword = keyword != null && !keyword.isBlank();

	        if (!hasTable) {
	        	// 전체
	            commentPage = hasKeyword
	                    ? commentRepository.findByContentContainingIgnoreCaseOrderByPostdateAscIdAsc(keyword, pageable)
	                    : commentRepository.findAllByOrderByPostdateAscIdAsc(pageable);
	        }
	        else if (!hasRecord) {
	            // 테이블만
	            commentPage = hasKeyword
	                    ? commentRepository.findByTablenameAndContentContainingIgnoreCaseOrderByPostdateAscIdAsc(tablename, keyword, pageable)
	                    : commentRepository.findByTablenameOrderByPostdateAscIdAsc(tablename, pageable);
	        } else {
	        	// 테이블과 레코드
	            commentPage = commentRepository.findByTablenameAndRecodenumOrderByComment_IdDescIdAsc(tablename, recodenum, pageable);
	        }
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
		}

		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
			commentDto.setNickname(comment.getUser().getNickname());
			commentDto.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
			commentDto.setReportcount(reportService.countReportByTablenameAndRecodenum("comments", comment.getId()));
			
			// 좋아요 여부 조회
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			    String username = authentication.getName();
			    int userId = userService.readUserByUsername(username).getId();
			    commentDto.setLikestatus(likeService.existsUserLike("comments", comment.getId(), userId));
			}

			commentDtoList.add(commentDto);
		}
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	// 댓글 목록 조회 (최신순)
	public Page<CommentReadResponseDto> readCommentPageOrderByIdDesc(String tablename, Integer recodenum, String keyword, Pageable pageable) {

		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 목록 조회
		Page<Comment> commentPage;
		try {
			boolean hasTable = tablename != null && !tablename.isBlank();
	        boolean hasRecord = recodenum != null;
	        boolean hasKeyword = keyword != null && !keyword.isBlank();

	        if (!hasTable) {
	        	// 전체
	            commentPage = hasKeyword
	                    ? commentRepository.findByContentContainingIgnoreCaseOrderByPostdateDescIdDesc(keyword, pageable)
	                    : commentRepository.findAllByOrderByPostdateDescIdDesc(pageable);
	        }
	        else if (!hasRecord) {
	            // 테이블만
	            commentPage = hasKeyword
	                    ? commentRepository.findByTablenameAndContentContainingIgnoreCaseOrderByPostdateDescIdDesc(tablename, keyword, pageable)
	                    : commentRepository.findByTablenameOrderByPostdateDescIdDesc(tablename, pageable);
	        } else {
	        	// 테이블과 레코드
	            commentPage = commentRepository.findByTablenameAndRecodenumOrderByComment_IdDescIdDesc(tablename, recodenum, pageable);
	        }
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
		}
		
		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
			commentDto.setNickname(comment.getUser().getNickname());
			commentDto.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
			commentDto.setRole(comment.getUser().getRole().toString());
			commentDto.setReportcount(reportService.countReportByTablenameAndRecodenum("comments", comment.getId()));
			
			// 좋아요 여부 조회
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			    String username = authentication.getName();
			    int userId = userService.readUserByUsername(username).getId();
			    commentDto.setLikestatus(likeService.existsUserLike("comments", comment.getId(), userId));
			}
			
			commentDtoList.add(commentDto);
		}
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	// 댓글 목록 조회 (인기순)
	public Page<CommentReadResponseDto> readCommentPageOrderByLikecountDesc(String tablename, Integer recodenum, String keyword, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 목록 조회
		Page<Comment> commentPage;
		try {
			boolean hasTable = tablename != null && !tablename.isBlank();
	        boolean hasRecord = recodenum != null;
	        boolean hasKeyword = keyword != null && !keyword.isBlank();

	        if (!hasTable) {
	        	// 전체
	            commentPage = hasKeyword
	                    ? commentRepository.findByContentContainingIgnoreCaseOrderByLikecountDescIdDesc(keyword, pageable)
	                    : commentRepository.findAllByOrderByLikecountDescIdDesc(pageable);
	        }
	        else if (!hasRecord) {
	            // 테이블만
	            commentPage = hasKeyword
	                    ? commentRepository.findByTablenameAndContentContainingIgnoreCaseOrderByLikecountDescIdDesc(tablename, keyword, pageable)
	                    : commentRepository.findByTablenameOrderByLikecountDescIdDesc(tablename, pageable);
	        } else {
	        	// 테이블과 레코드
	            commentPage = commentRepository.findByTablenameAndRecodenumOrderByLikecountDescIdDesc(tablename, recodenum, pageable);
	        }
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
		}

		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
			commentDto.setNickname(comment.getUser().getNickname());
			commentDto.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
			commentDto.setRole(comment.getUser().getRole().toString());
			commentDto.setReportcount(reportService.countReportByTablenameAndRecodenum("comments", comment.getId()));
			
			// 좋아요 여부 조회
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			    String username = authentication.getName();
			    int userId = userService.readUserByUsername(username).getId();
			    commentDto.setLikestatus(likeService.existsUserLike("comments", comment.getId(), userId));
			}
			
			commentDtoList.add(commentDto);
		}
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}
	
	
	
	// 댓글 목록 조회 (비인기순)
	public Page<CommentReadResponseDto> readCommentPageOrderByLikecountAsc(
	        String tablename, Integer recodenum, String keyword, Pageable pageable) {

	    if (pageable == null) {
	        throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
	    }

	    Page<Comment> commentPage;
	    try {
	    	boolean hasTable = tablename != null && !tablename.isBlank();
	    	boolean hasRecord = recodenum != null;
	    	boolean hasKeyword = keyword != null && !keyword.isBlank();
	    	
	        if (!hasTable) {
	        	// 전체
	            commentPage = hasKeyword
	                    ? commentRepository.findByContentContainingIgnoreCaseOrderByLikecountAscIdDesc(keyword, pageable)
	                    : commentRepository.findAllByOrderByLikecountAscIdDesc(pageable);
	        }
	        else if (!hasRecord) {
	        	// 테이블만
	            commentPage = hasKeyword
	                    ? commentRepository.findByTablenameAndContentContainingIgnoreCaseOrderByLikecountAscIdDesc(tablename, keyword, pageable)
	                    : commentRepository.findByTablenameOrderByLikecountAscIdDesc(tablename, pageable);
	        }
	        else {
	        	// 테이블과 레코드
	            commentPage = commentRepository.findByTablenameAndRecodenumOrderByLikecountAscIdDesc(tablename, recodenum, pageable);
	        }
	    } catch (Exception e) {
	        throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
	    }

	    List<CommentReadResponseDto> commentDtoList = new ArrayList<>();
	    for (Comment comment : commentPage) {
	        CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
	        commentDto.setNickname(comment.getUser().getNickname());
	        commentDto.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
	        commentDto.setRole(comment.getUser().getRole().toString());
	        commentDto.setReportcount(reportService.countReportByTablenameAndRecodenum("comments", comment.getId()));
	        
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
	            String username = auth.getName();
	            int userId = userService.readUserByUsername(username).getId();
	            commentDto.setLikestatus(likeService.existsUserLike("comments", comment.getId(), userId));
	        }
	        commentDtoList.add(commentDto);
	    }
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	// 댓글 목록 조회 (신고순)
	public Page<CommentReadResponseDto> readCommentPageOrderByReportcountDesc(
	        String tablename, Integer recodenum, String keyword, Pageable pageable) {

	    if (pageable == null) {
	    	throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
	    }

	    Page<Comment> commentPage;
	    try {
	    	boolean hasTable = tablename != null && !tablename.isBlank();
	    	boolean hasRecord = recodenum != null;
	    	boolean hasKeyword = keyword != null && !keyword.isBlank();
	    	
	        if (!hasTable) {
	        	// 전체
	            commentPage = hasKeyword
	                    ? commentRepository.findByContentContainingIgnoreCaseOrderByReportcountDescIdDesc(keyword, pageable)
	                    : commentRepository.findAllByOrderByReportcountDescIdDesc(pageable);
	        }
	        else if (!hasRecord) {
	        	// 테이블만
	            commentPage = hasKeyword
	                    ? commentRepository.findByTablenameAndContentContainingIgnoreCaseOrderByReportcountDescIdDesc(tablename, keyword, pageable)
	                    : commentRepository.findByTablenameOrderByReportcountDescIdDesc(tablename, pageable);
	        }
	        else {
	        	// 테이블과 레코드
	            commentPage = commentRepository.findByTablenameAndRecodenumOrderByReportcountDescIdDesc(tablename, recodenum, pageable);
	        }
	    } catch (Exception e) {
	        throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
	    }

	    List<CommentReadResponseDto> commentDtoList = new ArrayList<>();
	    for (Comment comment : commentPage) {
	        CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
	        commentDto.setNickname(comment.getUser().getNickname());
	        commentDto.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
	        commentDto.setRole(comment.getUser().getRole().toString());
	        commentDto.setReportcount(reportService.countReportByTablenameAndRecodenum("comments", comment.getId()));
	        
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
	            String username = auth.getName();
	            int userId = userService.readUserByUsername(username).getId();
	            commentDto.setLikestatus(likeService.existsUserLike("comments", comment.getId(), userId));
	        }
	        commentDtoList.add(commentDto);
	    }
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}
	
	
	
	// 댓글 목록 조회 (미신고순)
	public Page<CommentReadResponseDto> readCommentPageOrderByReportcountAsc(
	        String tablename, Integer recodenum, String keyword, Pageable pageable) {

	    if (pageable == null) {
	    	throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
	    }

	    Page<Comment> commentPage;
	    try {
	    	boolean hasTable = tablename != null && !tablename.isBlank();
	    	boolean hasRecord = recodenum != null;
	    	boolean hasKeyword = keyword != null && !keyword.isBlank();
	    	
	        if (!hasTable) {
	        	// 전체
	            commentPage = hasKeyword
	                    ? commentRepository.findByContentContainingIgnoreCaseOrderByReportcountAscIdDesc(keyword, pageable)
	                    : commentRepository.findAllByOrderByReportcountAscIdDesc(pageable);
	        }
	        else if (!hasRecord) {
	        	// 테이블만
	            commentPage = hasKeyword
	                    ? commentRepository.findByTablenameAndContentContainingIgnoreCaseOrderByReportcountAscIdDesc(tablename, keyword, pageable)
	                    : commentRepository.findByTablenameOrderByReportcountAscIdDesc(tablename, pageable);
	        }
	        else {
	        	// 테이블과 레코드
	            commentPage = commentRepository.findByTablenameAndRecodenumOrderByReportcountAscIdDesc(tablename, recodenum, pageable);
	        }
	    } catch (Exception e) {
	        throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
	    }

	    List<CommentReadResponseDto> commentDtoList = new ArrayList<>();
	    for (Comment comment : commentPage) {
	        CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
	        commentDto.setNickname(comment.getUser().getNickname());
	        commentDto.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
	        commentDto.setRole(comment.getUser().getRole().toString());
	        commentDto.setReportcount(reportService.countReportByTablenameAndRecodenum("comments", comment.getId()));
	        
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
	            String username = auth.getName();
	            int userId = userService.readUserByUsername(username).getId();
	            commentDto.setLikestatus(likeService.existsUserLike("comments", comment.getId(), userId));
	        }
	        commentDtoList.add(commentDto);
	    }
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	
	
	// **** 수정 보완 필요 *** 댓글 수
	public int readCommentCount(String tablename, Integer recodenum) {
		
		// 입력값 검증
		if (tablename == null || recodenum == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		try {
			return commentRepository.countByTablenameAndRecodenum(tablename, recodenum);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_COUNT_FAIL);
		}
		
	}
	
	
	
	// 사용자가 작성한 댓글 목록 조회
	public Page<CommentReadMyResponseDto> readCommentPageByUserId(Integer userId, Pageable pageable) {
		
		if (userId == null || pageable == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 목록 조회
		Page<Comment> commentPage;
		try {
			commentPage = commentRepository.findByUserId(userId, pageable);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
		}
		
		List<CommentReadMyResponseDto> commentDtoList = new ArrayList<CommentReadMyResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadMyResponseDto commentDto = commentMapper.toReadMyResponseDto(comment);
			commentDto.setNickname(comment.getUser().getNickname());
			String title = null;
			if (comment.getTablename().equals("vote")) {
				title = chompService.readVoteById(comment.getRecodenum()).getTitle();
			}
			else if (comment.getTablename().equals("megazine")) {
				title = chompService.readMegazineById(comment.getRecodenum()).getTitle();
			}
			else if (comment.getTablename().equals("event")) {
				title = chompService.readEventById(comment.getRecodenum()).getTitle();
			}
			commentDto.setTitle(title);
			commentDtoList.add(commentDto);
		}
		
		return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}
	
	

	
	
	// 댓글 작성
	public CommentCreateResponseDto createComment(CommentCreateRequestDto commentRequestDto) {

		// 입력값 검증
		if (commentRequestDto == null || commentRequestDto.getContent() == null || commentRequestDto.getTablename() == null || commentRequestDto.getRecodenum() == null || commentRequestDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		Comment comment = commentMapper.toEntity(commentRequestDto);
		
	    // 대댓글이면 댓글 참조
	    if (commentRequestDto.getCommId() != null) {
	        Comment parent = commentRepository.findById(commentRequestDto.getCommId())
	            .orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
	        comment.setComment(parent);
	    }
	    
		// 댓글 저장
		try {
			comment = commentRepository.saveAndFlush(comment);
			CommentCreateResponseDto commentResponseDto = commentMapper.toCreateResponseDto(comment);
			commentResponseDto.setNickname(userService.readUserById(commentRequestDto.getUserId()).getNickname());
			commentResponseDto.setLikecount(likeService.countLikesByTablenameAndRecodenum("comments", comment.getId()));
			return commentResponseDto;
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_CREATE_FAIL);
		}
		
	}

	
	
	// 댓글 수정
	public CommentUpdateResponseDto updateComment(CommentUpdateRequestDto commentDto) {
		
		// 입력값 검증
		if (commentDto == null || commentDto.getId() == null || commentDto.getContent() == null || commentDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}

		// 댓글 존재 여부 판단
		Comment comment = commentRepository.findById(commentDto.getId())
				.orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (comment.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(CommentErrorCode.COMMENT_SUPER_ADMIN_FORBIDDEN);
				}
			}
			// 일반 회원
			else {
				if (userService.readUserByUsername(username).getId() != comment.getUser().getId()) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
			}
		}
		
		// 필요한 필드 수정
		comment.setContent(commentDto.getContent());
		
		// 댓글 수정
		try {
			comment = commentRepository.save(comment);
			return commentMapper.toUpdateResponseDto(comment);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_UPDATE_FAIL);
		}
		
	}
	
	
	
	// 댓글 삭제
	public void deleteComment(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 존재 여부 판단
		Comment comment = commentRepository.findById(id)
				.orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (comment.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(CommentErrorCode.COMMENT_SUPER_ADMIN_FORBIDDEN);
				}
			}
			// 일반 회원
			else {
				if (userService.readUserByUsername(username).getId() != comment.getUser().getId()) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
			}
		}

		// 댓글 삭제
		try {
			commentRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_DELETE_FAIL);
		}

	}



	// 댓글 좋아요
	public LikeCreateResponseDto likeComment(LikeCreateRequestDto likeDto) {
		
		// 댓글 존재 여부 판단
		Comment comment = commentRepository.findById(likeDto.getRecodenum())
			    .orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		// 좋아요 중복 작성 처리해야함 *****
		
		
		// 좋아요 작성
		try {
		    return likeService.createLike(likeDto);
		}
		catch (ApiException e) {
		    throw e;
		}
		catch (Exception e) {
		    throw new ApiException(CommentErrorCode.COMMENT_LIKE_FAIL);
		}
		
	}



	// 댓글 좋아요 취소
	public void unlikeComment(LikeDeleteRequestDto likeDto) {
		
		// 댓글 존재 여부 판단
		Comment comment = commentRepository.findById(likeDto.getRecodenum())
			    .orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		// 좋아요 취소
		try {
			likeService.deleteLike(likeDto);
		}
		catch(ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_UNLIKE_FAIL);
		}

	}
	
	// 댓글 신고
	public ReportCreateResponseDto reportComment(ReportCreateRequestDto reportDto) {
		
		// 댓글 존재 여부 판단
		Comment comment = commentRepository.findById(reportDto.getRecodenum())
			    .orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		// 신고 작성
		try {
			return reportService.createReport(reportDto);
		}
		catch (ApiException e) {
		    throw e;
		}
		catch (Exception e) {
		    throw new ApiException(CommentErrorCode.COMMENT_REPORT_FAIL);
		}
	}
	
	
	
	// 댓글 신고 취소
	public void unreportComment(ReportDeleteRequestDto reportDto) {
		
		// 댓글 존재 여부 판단
		Comment comment = commentRepository.findById(reportDto.getRecodenum())
			    .orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		// 신고 취소
		try {
			reportService.deleteReport(reportDto);
		}
		catch (ApiException e) {
		    throw e;
		}
		catch (Exception e) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNREPORT_FAIL);
		}
		
	}
	
	
	
	
	
	

}
