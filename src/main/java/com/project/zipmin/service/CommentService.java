package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.dto.UserCommentReadesponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.comment.CommentCreateRequestDto;
import com.project.zipmin.dto.comment.CommentCreateResponseDto;
import com.project.zipmin.dto.comment.CommentReadResponseDto;
import com.project.zipmin.dto.comment.CommentUpdateRequestDto;
import com.project.zipmin.dto.comment.CommentUpdateResponseDto;
import com.project.zipmin.dto.like.LikeCreateRequestDto;
import com.project.zipmin.dto.like.LikeCreateResponseDto;
import com.project.zipmin.dto.like.LikeDeleteRequestDto;
import com.project.zipmin.dto.report.ReportCreateRequestDto;
import com.project.zipmin.dto.report.ReportCreateResponseDto;
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
	private final CommentMapper commentMapper;
	private final UserService userService;
	private final ChompService chompService;
	private final RecipeService recipeService;
	private final KitchenService kitchenService;
	private final LikeService likeService;
	private final ReportService reportService;
	
	

	
	
	// 댓글 목록 조회 (관리자)
	public Page<CommentReadResponseDto> readCommentPage(String keyword, String tablename, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 댓글 목록 조회
		Page<Comment> commentPage;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			boolean hasTablename = tablename != null && !tablename.isBlank();
			
			if (!hasTablename) {
				commentPage = hasKeyword
						? commentRepository.findAllByTablenameAndContentContainingIgnoreCase(tablename, keyword, pageable)
						: commentRepository.findAllByTablename(tablename, pageable);
				}
			else {
				commentPage = hasKeyword
						? commentRepository.findAllByContentContainingIgnoreCase(keyword, pageable)
						: commentRepository.findAll(pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
		}

		// 댓글 목록 응답 구성
		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
			commentDto.setUsername(comment.getUser().getUsername());
			commentDto.setNickname(comment.getUser().getNickname());
			commentDto.setLikecount(comment.getLikecount());
			commentDto.setReportcount(comment.getReportcount());
			commentDtoList.add(commentDto);
		}
		
		return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	
	
	// 댓글 목록 조회 (레코드번호 기반)
	public Page<CommentReadResponseDto> readCommentPageByTablenameAndRecodenum(String tablename, int recodenum, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (tablename == null || recodenum == 0 || pageable == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 댓글 목록 조회
		Page<Comment> commentPage;
		try {
			commentPage = commentRepository.findAllByTablenameAndRecodenumAndCommentIsNull(tablename, recodenum, pageable);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
		}
		
		// 댓글 목록 응답 구성
		List<CommentReadResponseDto> commentDtoList = new ArrayList<CommentReadResponseDto>();
		for (Comment comment : commentPage) {
			CommentReadResponseDto commentDto = commentMapper.toReadResponseDto(comment);
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
				UserReadResponseDto userDto = userService.readUserByUsername(authentication.getName());
				commentDto.setLiked(likeService.existLike("comments", comment.getId(), userDto.getId()));
			}
			commentDto.setNickname(comment.getUser().getNickname());
			commentDto.setAvatar(comment.getUser().getAvatar());
			commentDto.setLikecount(comment.getLikecount());
			commentDto.setReportcount(comment.getReportcount());
			
			// 대댓글 목록 응답 구성
			List<Comment> subcommentList = comment.getSubcomment();
			List<CommentReadResponseDto> subcommentDtoList = new ArrayList<CommentReadResponseDto>();
			for (Comment subcomment : subcommentList) {
				CommentReadResponseDto subcommentDto = commentMapper.toReadResponseDto(subcomment);
				if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
					UserReadResponseDto userDto = userService.readUserByUsername(authentication.getName());
					subcommentDto.setLiked(likeService.existLike("comments", subcomment.getId(), userDto.getId()));
				}
				subcommentDto.setNickname(subcomment.getUser().getNickname());
				subcommentDto.setAvatar(subcomment.getUser().getAvatar());
				subcommentDto.setLikecount(subcomment.getLikecount());
				subcommentDto.setReportcount(subcomment.getReportcount());
				subcommentDtoList.add(subcommentDto);
			}
			commentDto.setSubcommentList(subcommentDtoList);
			commentDtoList.add(commentDto);
		}
		
		return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}

	
	
	
	
	// 사용자의 댓글 목록 조회
	public Page<UserCommentReadesponseDto> readCommentPageByUserId(int userId, Pageable pageable) {
		
		// 입력값 검증
		if (userId == 0 || pageable == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != userId) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userId) {
				throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
			}
		}
		
		// 댓글 목록 조회
		Page<Comment> commentPage;
		try {
			commentPage = commentRepository.findAllByUserId(userId, pageable);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_LIST_FAIL);
		}
		
		// 댓글 목록 응답 구성
		List<UserCommentReadesponseDto> commentDtoList = new ArrayList<>();
		for (Comment comment : commentPage) {
			UserCommentReadesponseDto commentDto = commentMapper.toReadMyResponseDto(comment);
			commentDto.setNickname(comment.getUser().getNickname());
			commentDto.setAvatar(comment.getUser().getAvatar());
			String title = switch (comment.getTablename()) {
				case "vote" -> chompService.readVoteById(comment.getRecodenum()).getTitle();
				case "megazine" -> chompService.readMegazineById(comment.getRecodenum()).getTitle();
				case "event" -> chompService.readEventById(comment.getRecodenum()).getTitle();
				case "recipe" -> recipeService.readRecipeById(comment.getRecodenum()).getTitle();
				case "guide" -> kitchenService.readGuideById(comment.getRecodenum()).getTitle();
				default -> null;
			};
			commentDto.setTitle(title);
			commentDtoList.add(commentDto);
		}
		
		return new PageImpl<>(commentDtoList, pageable, commentPage.getTotalElements());
	}
	
	

	
	
	// 댓글 작성
	public CommentCreateResponseDto createComment(CommentCreateRequestDto commentRequestDto) {

		// 입력값 검증
		if (commentRequestDto == null
				|| commentRequestDto.getContent() == null
				|| commentRequestDto.getTablename() == null
				|| commentRequestDto.getRecodenum() == 0
				|| commentRequestDto.getUserId() == 0) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		Comment comment = commentMapper.toEntity(commentRequestDto);
		
		// 댓글 참조
//		if (commentRequestDto.getCommId() != 0) {
//			Comment parent = commentRepository.findById(commentRequestDto.getCommId())
//				.orElseThrow(() -> new ApiException(CommentErrorCode.COMMENT_NOT_FOUND));
//			comment.setComment(parent);
//		}
		
		// 댓글 존재 여부 확인
		if (commentRequestDto.getCommId() != 0 && !commentRepository.existsById(commentRequestDto.getCommId())) {
			throw new ApiException(CommentErrorCode.COMMENT_NOT_FOUND);
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

	
	
	
	
	// 댓글 수정
	public CommentUpdateResponseDto updateComment(CommentUpdateRequestDto commentDto) {
		
		// 입력값 검증
		if (commentDto == null
				|| commentDto.getId() == 0
				|| commentDto.getContent() == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}

		// 댓글 존재 여부 판단
		Comment comment;
		try {
			comment = commentRepository.findById(commentDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (comment.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
				if (comment.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != comment.getUser().getId()) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != comment.getUser().getId()) {
				throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
			}
		}
		
		// 값 설정
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
	public void deleteComment(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		// 댓글 조회
		Comment comment;
		try {
			comment = commentRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (comment.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
				if (comment.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != comment.getUser().getId()) {
					throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != comment.getUser().getId()) {
				throw new ApiException(CommentErrorCode.COMMENT_FORBIDDEN);
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
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
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
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_LIKE_FAIL);
		}
		
	}

	
	


	// 댓글 좋아요 취소
	public void unlikeComment(LikeDeleteRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null
				|| likeDto.getTablename() == null
				|| likeDto.getRecodenum() == 0
				|| likeDto.getUserId() == 0) {
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
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_UNLIKE_FAIL);
		}

	}
	
	
	
	
	
	// 댓글 신고
	public ReportCreateResponseDto reportComment(ReportCreateRequestDto reportDto) {
		
		// 입력값 검증
		if (reportDto == null || reportDto.getTablename() == null
				|| reportDto.getRecodenum() == 0 || reportDto.getReason() == null
				|| reportDto.getUserId() == 0) {
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
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_REPORT_FAIL);
		}
	}
	
}
