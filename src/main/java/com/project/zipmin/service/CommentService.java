package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Comment;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.CommentMapper;
import com.project.zipmin.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	
	private final UserService userService;
	private final ChompService chompService;
	private final LikeService likeService;
	private final ReportService reportService;

	private final CommentMapper commentMapper;
	
	
	
	
	
	// 댓글 목록 조회
	public Page<CommentReadResponseDto> readCommentPage(String tablename, Integer recodenum, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (tablename == null || recodenum == null || pageable == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 정렬 문자열을 객체로 변환
		Sort sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			switch (sort) {
			case "postdate-desc":
				sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id"));
				break;
			case "postdate-asc":
				sortSpec = Sort.by(Sort.Order.asc("postdate"), Sort.Order.asc("id"));
				break;
			case "likecount-desc":
				sortSpec = Sort.by(Sort.Order.desc("likecount"), Sort.Order.desc("id"));
				break;
			default:
				break;
			}
		}
		
		// 기존 페이지 객체에 정렬 주입
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);
		
		// 댓글 목록 조회
		Page<Comment> commentPage = null;
		try {
			commentPage = commentRepository.findAllByTablenameAndRecodenumAndCommentIsNull(tablename, recodenum, sortedPageable);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
		}
		
		// 댓글 목록 응답 구성
		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
			
			// 닉네임 좋아요수 신고수
			commentDto.setNickname(comment.getUser().getNickname());
			commentDto.setLikecount(likeService.countLike("comments", comment.getId()));
			commentDto.setReportcount(reportService.countReport("comments", comment.getId()));
			
			// 좋아요 여부
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
				String username = authentication.getName();
				int userId = userService.readUserByUsername(username).getId();
				commentDto.setLiked(likeService.existsUserLike("comments", comment.getId(), userId));
			}
			
			// 대댓글
			List<Comment> subcommentList = comment.getSubcomment();
			
			List<CommentReadResponseDto> subcommentDtoList = new ArrayList<CommentReadResponseDto>();
			for (Comment subcomment : subcommentList) {
				CommentReadResponseDto subcommentDto = commentMapper.toReadResponseDto(subcomment);
				
				// 닉네임 좋아요수 신고수
				subcommentDto.setNickname(subcomment.getUser().getNickname());
				subcommentDto.setLikecount(likeService.countLike("comments", subcomment.getId()));
				subcommentDto.setReportcount(reportService.countReport("comments", subcomment.getId()));
				
				// 좋아요 여부
				if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
					String username = authentication.getName();
					int userId = userService.readUserByUsername(username).getId();
					subcommentDto.setLiked(likeService.existsUserLike("comments", subcomment.getId(), userId));
				}
				
				subcommentDtoList.add(subcommentDto);
			}
			commentDto.setSubcommentList(subcommentDtoList);
			commentDtoList.add(commentDto);
		}
		
		return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}
	
	
	
	
	
	// 댓글 목록 조회 (관리자)
	public Page<CommentReadResponseDto> readAdminCommentPage(String tablename, String keyword, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 정렬 문자열을 객체로 변환
		Sort sortSpec = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			switch (sort) {
				case "id-desc":
					sortSpec = Sort.by(Sort.Order.desc("id"));
					break;
				case "id-asc":
					sortSpec = Sort.by(Sort.Order.asc("id"));
					break;
				case "content-desc":
					sortSpec = Sort.by(Sort.Order.desc("content"), Sort.Order.desc("id"));
					break;
				case "content-asc":
					sortSpec = Sort.by(Sort.Order.asc("content"), Sort.Order.desc("id"));
					break;
				case "postdate-desc":
					sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id"));
					break;
				case "postdate-asc":
					sortSpec = Sort.by(Sort.Order.asc("postdate"), Sort.Order.asc("id"));
					break;
				case "likecount-desc":
					sortSpec = Sort.by(Sort.Order.desc("likecount"), Sort.Order.desc("id"));
					break;
				case "likecount-asc":
					sortSpec = Sort.by(Sort.Order.asc("likecount"), Sort.Order.desc("id"));
					break;
				case "reportcount-desc":
					sortSpec = Sort.by(Sort.Order.desc("reportcount"), Sort.Order.desc("id"));
					break;
				case "reportcount-asc":
					sortSpec = Sort.by(Sort.Order.asc("reportcount"), Sort.Order.desc("id"));
					break;
				default:
					break;
		    }
		}
		
		// 기존 페이지 객체에 정렬 주입
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);
		
		// 댓글 목록 조회
		Page<Comment> commentPage = null;
		try {
			boolean hasTable = tablename != null && !tablename.isBlank();
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			
			if (!hasTable) {
				// 전체
				commentPage = hasKeyword
						? commentRepository.findAllByContentContainingIgnoreCase(keyword, sortedPageable)
						: commentRepository.findAll(sortedPageable);
				}
			else {
				// 테이블
				commentPage = hasKeyword
						? commentRepository.findAllByTablenameAndContentContainingIgnoreCase(tablename, keyword, sortedPageable)
						: commentRepository.findAllByTablename(tablename, sortedPageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
		}

		// 댓글 목록 응답 구성
		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
			
			// 닉네임 좋아요수 신고수
			commentDto.setNickname(comment.getUser().getNickname());
			commentDto.setLikecount(likeService.countLike("comments", comment.getId()));
			commentDto.setReportcount(reportService.countReport("comments", comment.getId()));
			
			commentDtoList.add(commentDto);
		}
		
	    return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	
	
	// 사용자가 작성한 댓글 목록을 조회하는 함수
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
	
	

	
	
	// 댓글을 작성하는 함수
	public CommentCreateResponseDto createComment(CommentCreateRequestDto commentRequestDto) {

		// 입력값 검증
		if (commentRequestDto == null || commentRequestDto.getContent() == null
				|| commentRequestDto.getTablename() == null || commentRequestDto.getRecodenum() == null
				|| commentRequestDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		Comment comment = commentMapper.toEntity(commentRequestDto);
		
	    // 댓글 참조
	    if (commentRequestDto.getCommId() != null) {
	        Comment parent = commentRepository.findById(commentRequestDto.getCommId())
	            .orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
	        comment.setComment(parent);
	    }
	    
		// 댓글 저장
		try {
			comment = commentRepository.saveAndFlush(comment);
			
			// 댓글 응답 구성
			CommentCreateResponseDto commentResponseDto = commentMapper.toCreateResponseDto(comment);
			commentResponseDto.setNickname(userService.readUserById(commentRequestDto.getUserId()).getNickname());
			commentResponseDto.setLikecount(likeService.countLike("comments", comment.getId()));
			return commentResponseDto;
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_CREATE_FAIL);
		}
		
	}

	
	
	
	
	// 댓글을 수정하는 함수
	public CommentUpdateResponseDto updateComment(CommentUpdateRequestDto commentDto) {
		
		// 입력값 검증
		if (commentDto == null || commentDto.getId() == null || commentDto.getContent() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}

		// 댓글 존재 여부 판단
		Comment comment = commentRepository.findById(commentDto.getId())
				.orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (comment.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
				else if (comment.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != comment.getUser().getId()) {
						throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (user.getId() != comment.getUser().getId()) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
			}
		}
		
		// 변경 값 설정
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
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (comment.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
				else if (comment.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != comment.getUser().getId()) {
						throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (user.getId() != comment.getUser().getId()) {
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
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 존재 여부 확인
		if (commentRepository.existsById(likeDto.getRecodenum())) {
			new ApiException(CommentErrorCode.COMMENT_NOT_FOUND);
		}
		
		// 좋아요 저장
		try {
		    return likeService.createLike(likeDto);
		}
		catch (Exception e) {
		    throw new ApiException(CommentErrorCode.COMMENT_LIKE_FAIL);
		}
		
	}

	
	


	// 댓글 좋아요 취소
	public void unlikeComment(LikeDeleteRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 존재 여부 확인
		if (commentRepository.existsById(likeDto.getRecodenum())) {
			new ApiException(CommentErrorCode.COMMENT_NOT_FOUND);
		}
		
		// 좋아요 취소
		try {
			likeService.deleteLike(likeDto);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_UNLIKE_FAIL);
		}

	}
	
	
	
	
	
	// 댓글 신고
	public ReportCreateResponseDto reportComment(ReportCreateRequestDto reportDto) {
		
		// 입력값 검증
		if (reportDto == null || reportDto.getTablename() == null
				|| reportDto.getRecodenum() == null || reportDto.getReason() == null
				|| reportDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 존재 여부 확인
		if (commentRepository.existsById(reportDto.getRecodenum())) {
			new ApiException(CommentErrorCode.COMMENT_NOT_FOUND);
		}
		
		// 신고 작성
		try {
			return reportService.createReport(reportDto);
		}
		catch (Exception e) {
		    throw new ApiException(CommentErrorCode.COMMENT_REPORT_FAIL);
		}
	}
	
	

	
	
	// 댓글 신고 취소
	public void unreportComment(ReportDeleteRequestDto reportDto) {
		
		// 입력값 검증
		if (reportDto == null || reportDto.getTablename() == null
				|| reportDto.getRecodenum() == null || reportDto.getUserId() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 존재 여부 확인
		if (commentRepository.existsById(reportDto.getRecodenum())) {
			new ApiException(CommentErrorCode.COMMENT_NOT_FOUND);
		}
		
		// 신고 취소
		try {
			reportService.deleteReport(reportDto);
		}
		catch (Exception e) {
		    throw new ApiException(CommentErrorCode.COMMENT_UNREPORT_FAIL);
		}
		
	}
}
