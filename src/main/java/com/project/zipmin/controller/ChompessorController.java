package com.project.zipmin.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.project.zipmin.dto.ChompReadResponseDto;
import com.project.zipmin.dto.EventCreateRequestDto;
import com.project.zipmin.dto.EventCreateResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.dto.EventUpdateRequestDto;
import com.project.zipmin.dto.EventUpdateResponseDto;
import com.project.zipmin.dto.MegazineCreateRequestDto;
import com.project.zipmin.dto.MegazineCreateResponseDto;
import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.ChompSuccessCode;
import com.project.zipmin.api.EventErrorCode;
import com.project.zipmin.api.EventSuccessCode;
import com.project.zipmin.api.MegazineErrorCode;
import com.project.zipmin.api.MegazineSuccessCode;
import com.project.zipmin.api.VoteErrorCode;
import com.project.zipmin.api.VoteSuccessCode;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.MegazineUpdateRequestDto;
import com.project.zipmin.dto.MegazineUpdateResponseDto;
import com.project.zipmin.dto.VoteCreateRequestDto;
import com.project.zipmin.dto.VoteCreateResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.dto.VoteRecordCreateRequestDto;
import com.project.zipmin.dto.VoteRecordCreateResponseDto;
import com.project.zipmin.dto.VoteUpdateRequestDto;
import com.project.zipmin.dto.VoteUpdateResponseDto;
import com.project.zipmin.service.ChompService;
import com.project.zipmin.service.CommentService;
import com.project.zipmin.service.UserService;
import com.project.zipmin.swagger.ChompReadListFailResponse;
import com.project.zipmin.swagger.ChompReadListSuccessResponse;
import com.project.zipmin.swagger.EventCreateFailResponse;
import com.project.zipmin.swagger.EventCreateSuccessResponse;
import com.project.zipmin.swagger.EventDeleteFailResponse;
import com.project.zipmin.swagger.EventDeleteSuccessResponse;
import com.project.zipmin.swagger.EventFileUploadFailResponse;
import com.project.zipmin.swagger.EventForbiddenResponse;
import com.project.zipmin.swagger.EventInvalidFileResponse;
import com.project.zipmin.swagger.EventInvalidInputResponse;
import com.project.zipmin.swagger.EventInvalidPeriodResponse;
import com.project.zipmin.swagger.EventNotFoundResponse;
import com.project.zipmin.swagger.EventReadSuccessResponse;
import com.project.zipmin.swagger.EventUnauthorizedAccessResponse;
import com.project.zipmin.swagger.EventUpdateFailResponse;
import com.project.zipmin.swagger.EventUpdateSuccessResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.MegazineCreateFailResponse;
import com.project.zipmin.swagger.MegazineCreateSuccessResponse;
import com.project.zipmin.swagger.MegazineDeleteFailResponse;
import com.project.zipmin.swagger.MegazineDeleteSuccessResponse;
import com.project.zipmin.swagger.MegazineForbiddenResponse;
import com.project.zipmin.swagger.MegazineInvalidInputResponse;
import com.project.zipmin.swagger.MegazineNotFoundResponse;
import com.project.zipmin.swagger.MegazineReadSuccessResponse;
import com.project.zipmin.swagger.MegazineUnauthorizedAccessResponse;
import com.project.zipmin.swagger.MegazineUpdateFailResponse;
import com.project.zipmin.swagger.MegazineUpdateSuccessResponse;
import com.project.zipmin.swagger.UserNotFoundResponse;
import com.project.zipmin.swagger.VoteChoiceInvalidInputResponse;
import com.project.zipmin.swagger.VoteCreateFailResponse;
import com.project.zipmin.swagger.VoteCreateSuccessResponse;
import com.project.zipmin.swagger.VoteDeleteFailResponse;
import com.project.zipmin.swagger.VoteDeleteSuccessResponse;
import com.project.zipmin.swagger.VoteForbiddenResponse;
import com.project.zipmin.swagger.VoteInvalidInputResponse;
import com.project.zipmin.swagger.VoteInvalidPeriodResponse;
import com.project.zipmin.swagger.VoteNotFoundResponse;
import com.project.zipmin.swagger.VoteNotOpenedResponse;
import com.project.zipmin.swagger.VoteReadFailResponse;
import com.project.zipmin.swagger.VoteReadSuccessResponse;
import com.project.zipmin.swagger.VoteRecordCreateFailResponse;
import com.project.zipmin.swagger.VoteRecordCreateSuccessResponse;
import com.project.zipmin.swagger.VoteRecordDeleteFailResponse;
import com.project.zipmin.swagger.VoteRecordDeleteSuccessResponse;
import com.project.zipmin.swagger.VoteRecordDuplicateResponse;
import com.project.zipmin.swagger.VoteRecordInvalidInputResponse;
import com.project.zipmin.swagger.VoteRecordNotFoundResponse;
import com.project.zipmin.swagger.VoteRecordReadFailResponse;
import com.project.zipmin.swagger.VoteUnauthorizedAccessResponse;
import com.project.zipmin.swagger.VoteUpdateFailResponse;
import com.project.zipmin.swagger.VoteUpdateSuccessResponse;

@RestController
@Tag(name = "Chompessor API", description = "쩝쩝박사 관련 API")
public class ChompessorController {
	
	@Autowired
	ChompService chompService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CommentService commentService;
	
	
	
	
	
	@Operation(
	    summary = "쩝쩝박사 목록 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "쩝쩝박사 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ChompReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "쩝쩝박사 목록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ChompReadListFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 쩝쩝박사 목록 조회
	@GetMapping("/chomp")
	public ResponseEntity<?> listChomp(
			@Parameter(description = "카테고리", required = false) @RequestParam(required = false) String category,
			@Parameter(description = "검색어", required = false) @RequestParam(required = false) String keyword,
			@Parameter(description = "정렬", required = false) @RequestParam(required = false) String sort,
		    @Parameter(description = "페이지 번호") @RequestParam int page,
		    @Parameter(description = "페이지 크기") @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ChompReadResponseDto> chompPage = chompService.readChompPage(category, keyword, sort, pageable);

		return ResponseEntity.status(ChompSuccessCode.CHOMP_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ChompSuccessCode.CHOMP_READ_LIST_SUCCESS, chompPage));
	}
	
	
	
	
	
	@Operation(
	    summary = "투표 상세 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "투표 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteReadSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "투표 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteReadFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "투표 기록 조회 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteRecordReadFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 투표를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 투표 상세 조회
	@GetMapping("/votes/{id}")
	public ResponseEntity<?> readVote(@Parameter(description = "투표의 일련번호") @PathVariable int id) {
		
		VoteReadResponseDto voteDto = chompService.readVoteById(id);
		
		return ResponseEntity.status(VoteSuccessCode.VOTE_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(VoteSuccessCode.VOTE_READ_SUCCESS, voteDto));
	}
	
	

	
	
	@Operation(
	    summary = "투표 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "투표 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "투표 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "투표 기간 설정이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteInvalidPeriodResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteChoiceInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 투표 작성 (관리자)
	@PostMapping("/votes")
	public ResponseEntity<?> writeVote(
			@Parameter(description = "투표 작성 요청 정보") @RequestPart VoteCreateRequestDto voteRequestDto,
			@Parameter(description = "이미지 파일", required = false) @RequestPart(value = "file", required = false) MultipartFile file) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(VoteErrorCode.VOTE_UNAUTHORIZED_ACCESS);
		}
		
		VoteCreateResponseDto voteResponseDto = chompService.createVote(voteRequestDto, file);
		
		return ResponseEntity.status(VoteSuccessCode.VOTE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(VoteSuccessCode.VOTE_CREATE_SUCCESS, voteResponseDto));
	}

	
	
	
	
	@Operation(
	    summary = "투표 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "투표 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "투표 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "투표 기간 설정이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteInvalidPeriodResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteChoiceInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 투표를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 투표 수정 (관리자)
	@PatchMapping("/votes/{id}")
	public ResponseEntity<?> editVote(
			@Parameter(description = "투표의 일련번호") @PathVariable int id,
			@Parameter(description = "투표 수정 요청 정보") @RequestPart VoteUpdateRequestDto voteRequestDto,
			@Parameter(description = "이미지 파일", required = false) @RequestPart(value = "file", required = false) MultipartFile file) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(VoteErrorCode.VOTE_UNAUTHORIZED_ACCESS);
		}
		
		VoteUpdateResponseDto voteResponseDto = chompService.updateVote(voteRequestDto, file);
		
		return ResponseEntity.status(VoteSuccessCode.VOTE_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(VoteSuccessCode.VOTE_UPDATE_SUCCESS, voteResponseDto));
	}

	
	
	
	
	@Operation(
	    summary = "투표 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "투표 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "투표 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 투표를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 투표 삭제 (관리자)
	@DeleteMapping("/votes/{id}")
	public ResponseEntity<?> deleteVote(@Parameter(description = "투표의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(VoteErrorCode.VOTE_UNAUTHORIZED_ACCESS);
		}
		
		chompService.deleteVote(id);
		
		return ResponseEntity.status(VoteSuccessCode.VOTE_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(VoteSuccessCode.VOTE_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	@Operation(
	    summary = "투표 참여"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "201",
				description = "투표 기록 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteRecordCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "투표 기록 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteRecordCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteRecordInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "투표 기간 외 접근 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteNotOpenedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 투표를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "409",
				description = "투표 중복 참여 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteRecordDuplicateResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 투표 참여
	@PostMapping("/votes/{id}/records")
	public ResponseEntity<?> castVote(
			@Parameter(description = "투표의 일련번호") @PathVariable int id,
			@Parameter(description = "투표 참여 요청 정보") @RequestBody VoteRecordCreateRequestDto recordRequestDto) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(VoteErrorCode.VOTE_UNAUTHORIZED_ACCESS);
		}
		recordRequestDto.setUserId(userService.readUserByUsername(authentication.getName()).getId());
		
		VoteRecordCreateResponseDto recordResponseDto = chompService.createVoteRecord(recordRequestDto);
		
		return ResponseEntity.status(VoteSuccessCode.VOTE_RECORD_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(VoteSuccessCode.VOTE_RECORD_CREATE_SUCCESS, recordResponseDto));
	}	
	
	
	

	
	@Operation(
	    summary = "투표 참여 취소"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "투표 기록 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteRecordDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "투표 기록 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteRecordDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteRecordInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "투표 기간 외 접근 시도",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteNotOpenedResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 투표 기록을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteRecordNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 투표 참여 취소
	@DeleteMapping("/votes/{id}/records")
	public ResponseEntity<?> cancelVote(@Parameter(description = "투표의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(VoteErrorCode.VOTE_UNAUTHORIZED_ACCESS);
		}
		
		chompService.deleteVoteRecord(id);
		
		return ResponseEntity.status(VoteSuccessCode.VOTE_RECORD_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(VoteSuccessCode.VOTE_RECORD_DELETE_SUCCESS, null));
	}
	

	
	
	
	@Operation(
	    summary = "매거진 상세 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "매거진 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineReadSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 매거진을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 매거진 상세 조회
	@GetMapping("/megazines/{id}")
	public ResponseEntity<?> readMegazine(@Parameter(description = "매거진의 일련번호", required = true, example = "1") @PathVariable int id) {
		
		MegazineReadResponseDto megazineDto = chompService.readMegazineById(id);
		
		return ResponseEntity.status(MegazineSuccessCode.MEGAZINE_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(MegazineSuccessCode.MEGAZINE_READ_SUCCESS, megazineDto));	
	}
	
	
	
	
	
	@Operation(
	    summary = "매거진 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "매거진 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "매거진 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 매거진 작성 (관리자)
	@PostMapping("/megazines")
	public ResponseEntity<?> writeMegazines(
			@Parameter(description = "매거진 작성 요청 정보") @RequestPart MegazineCreateRequestDto megazineRequestDto,
			@Parameter(description = "이미지 파일", required = false) @RequestPart(value = "file", required = false) MultipartFile file) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(MegazineErrorCode.MEGAZINE_UNAUTHORIZED_ACCESS);
		}
		
		MegazineCreateResponseDto megazineResponseDto = chompService.createMegazine(megazineRequestDto, file);
		
		return ResponseEntity.status(MegazineSuccessCode.MEGAZINE_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(MegazineSuccessCode.MEGAZINE_CREATE_SUCCESS, megazineResponseDto));
	}
	
	
	
	
	
	@Operation(
	    summary = "매거진 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "매거진 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "매거진 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 매거진을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 매거진 수정 (관리자)
	@PatchMapping("/megazines/{id}")
	public ResponseEntity<?> editMegazine(
			@Parameter(description = "매거진의 일련번호") @PathVariable int id,
			@Parameter(description = "매거진 수정 요청 정보") @RequestPart MegazineUpdateRequestDto megazineRequestDto,
			@Parameter(description = "이미지 파일", required = false) @RequestPart(value = "file", required = false) MultipartFile file) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(MegazineErrorCode.MEGAZINE_UNAUTHORIZED_ACCESS);
		}
		
		MegazineUpdateResponseDto megazineResponseDto = chompService.updateMegazine(megazineRequestDto, file);
		
		return ResponseEntity.status(MegazineSuccessCode.MEGAZINE_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(MegazineSuccessCode.MEGAZINE_UPDATE_SUCCESS, megazineResponseDto));
	}

	
	
	
	
	@Operation(
	    summary = "매거진 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "매거진 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "매거진 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 매거진을 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = MegazineNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 매거진 삭제 (관리자)
	@DeleteMapping("/megazines/{id}")
	public ResponseEntity<?> deleteMegazine(@Parameter(description = "매거진의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(MegazineErrorCode.MEGAZINE_UNAUTHORIZED_ACCESS);
		}
		
		chompService.deleteMegazine(id);
		
		return ResponseEntity.status(MegazineSuccessCode.MEGAZINE_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(MegazineSuccessCode.MEGAZINE_DELETE_SUCCESS, null));
	}
	
	
	
	
	
	@Operation(
	    summary = "이벤트 상세 조회"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "이벤트 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventReadSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 이벤트를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 이벤트 상세 조회
	@GetMapping("/events/{id}")
	public ResponseEntity<?> readEvent(@Parameter(description = "이벤트의 일련번호") @PathVariable int id) {
		
		EventReadResponseDto eventDto = chompService.readEventById(id);
		
		return ResponseEntity.status(EventSuccessCode.EVENT_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(EventSuccessCode.EVENT_READ_SUCCESS, eventDto));
	}
	
	
	
	
	
	@Operation(
	    summary = "이벤트 작성"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "이벤트 작성 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventCreateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "이벤트 작성 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventCreateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "이벤트 기간 설정이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventInvalidPeriodResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "파일이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventInvalidFileResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "이벤트 파일 업로드 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventFileUploadFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 이벤트 작성 (관리자)
	@PostMapping("/events")
	public ResponseEntity<?> writeEvent(
			@Parameter(description = "이벤트 작성 요청 정보") @RequestPart EventCreateRequestDto eventRequestDto,
			@Parameter(description = "이미지 파일", required = false) @RequestPart(required = false) MultipartFile file) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(EventErrorCode.EVENT_UNAUTHORIZED_ACCESS);
		}
	
		EventCreateResponseDto eventResponseDto = chompService.createEvent(eventRequestDto, file);
		
		return ResponseEntity.status(EventSuccessCode.EVENT_CREATE_SUCCESS.getStatus())
				.body(ApiResponse.success(EventSuccessCode.EVENT_CREATE_SUCCESS, eventResponseDto));
	}
	
	
	

	
	@Operation(
	    summary = "이벤트 수정"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "이벤트 수정 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventUpdateSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "이벤트 수정 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventUpdateFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "이벤트 기간 설정이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventInvalidPeriodResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 이벤트를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "이벤트 파일 업로드 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventFileUploadFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 이벤트 수정 (관리자)
	@PatchMapping("/events/{id}")
	public ResponseEntity<?> editEvent(
			@Parameter(description = "이벤트의 일련번호") @PathVariable int id,
			@Parameter(description = "이벤트 수정 요청 정보") @RequestPart EventUpdateRequestDto eventRequestDto,
			@Parameter(description = "이미지 파일", required = false) @RequestPart(required = false) MultipartFile file) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(EventErrorCode.EVENT_UNAUTHORIZED_ACCESS);
		}
		
		EventUpdateResponseDto eventResponseDto = chompService.updateEvent(eventRequestDto, file);
		
		return ResponseEntity.status(EventSuccessCode.EVENT_UPDATE_SUCCESS.getStatus())
				.body(ApiResponse.success(EventSuccessCode.EVENT_UPDATE_SUCCESS, eventResponseDto));
	}
	
	
	
	
	
	@Operation(
	    summary = "이벤트 삭제"
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "이벤트 삭제 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventDeleteSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "이벤트 삭제 실패",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventDeleteFailResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "400",
				description = "입력값이 유효하지 않음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventInvalidInputResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "401",
				description = "로그인 되지 않은 사용자",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventUnauthorizedAccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "403",
				description = "권한 없는 사용자의 접근",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventForbiddenResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 이벤트를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = EventNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "404",
				description = "해당 사용자를 찾을 수 없음",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = UserNotFoundResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	// 이벤트 삭제 (관리자)
	@DeleteMapping("/events/{id}")
	public ResponseEntity<?> deleteEvent(
			@Parameter(description = "이벤트의 일련번호") @PathVariable int id) {
		
		// 로그인 여부 확인
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    throw new ApiException(EventErrorCode.EVENT_UNAUTHORIZED_ACCESS);
		}
		
		chompService.deleteEvent(id);
		
		return ResponseEntity.status(EventSuccessCode.EVENT_DELETE_SUCCESS.getStatus())
				.body(ApiResponse.success(EventSuccessCode.EVENT_DELETE_SUCCESS, null));
	}
}
