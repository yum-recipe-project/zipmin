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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.ChompReadResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.ChompSuccessCode;
import com.project.zipmin.api.EventSuccessCode;
import com.project.zipmin.api.MegazineSuccessCode;
import com.project.zipmin.api.VoteSuccessCode;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.dto.VoteRecordCreateRequestDto;
import com.project.zipmin.dto.VoteRecordCreateResponseDto;
import com.project.zipmin.service.ChompService;
import com.project.zipmin.service.CommentService;
import com.project.zipmin.swagger.ChompReadListSuccessResponse;
import com.project.zipmin.swagger.EventNotFoundResponse;
import com.project.zipmin.swagger.EventReadSuccessResponse;
import com.project.zipmin.swagger.InternalServerErrorResponse;
import com.project.zipmin.swagger.MegazineNotFoundResponse;
import com.project.zipmin.swagger.MegazineReadSuccessResponse;
import com.project.zipmin.swagger.VoteNotFoundResponse;
import com.project.zipmin.swagger.VoteReadSuccessResponse;
import com.project.zipmin.swagger.VoteRecordCreateFailResponse;
import com.project.zipmin.swagger.VoteRecordCreateSuccessResponse;
import com.project.zipmin.swagger.VoteRecordDuplicateResponse;
import com.project.zipmin.swagger.VoteRecordInvalidInputResponse;

@Tag(name = "Chompessor API", description = "쩝쩝박사 관련 API")
@RestController
public class ChompessorController {
	
	@Autowired
	ChompService chompService;
	@Autowired
	CommentService commentService;

	
	
	// 쩝쩝박사 목록 조회
	@Operation(
	    summary = "쩝쩝박사 목록 조회",
	    description = "카테고리 및 페이지 정보를 기반으로 쩝쩝박사 목록을 조회합니다."
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "쩝쩝박사 목록 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = ChompReadListSuccessResponse.class))),
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "500",
				description = "서버 내부 오류",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = InternalServerErrorResponse.class)))
	})
	@GetMapping("/chomp")
	public ResponseEntity<?> listChomp(
			@Parameter(description = "카테고리", example = "all") @RequestParam String category,
		    @Parameter(description = "페이지 번호", example = "0") @RequestParam int page,
		    @Parameter(description = "페이지 크기", example = "10") @RequestParam int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		Page<ChompReadResponseDto> chompPage = chompService.readChompPage(category, pageable);

		return ResponseEntity.status(ChompSuccessCode.CHOMP_READ_LIST_SUCCESS.getStatus())
				.body(ApiResponse.success(ChompSuccessCode.CHOMP_READ_LIST_SUCCESS, chompPage));
	}
	
	
	
	// 투표 상세 조회
	@Operation(
	    summary = "투표 상세 조회",
	    description = "ID를 기준으로 투표의 상세 정보를 조회합니다."
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
				description = "투표 조회 성공",
				content = @Content(
						mediaType = "application/json",
						schema = @Schema(implementation = VoteReadSuccessResponse.class))),
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
	@GetMapping("/votes/{id}")
	public ResponseEntity<?> readVote(@Parameter(description = "조회할 투표의 ID", required = true, example = "1") @PathVariable int id) {
		VoteReadResponseDto voteDto = chompService.readVoteById(id);
		
		return ResponseEntity.status(VoteSuccessCode.VOTE_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(VoteSuccessCode.VOTE_READ_SUCCESS, voteDto));
	}
	
	

	// 새 투표 등록 (관리자)
	@PostMapping("/votes")
	public int writeVote() {
		return 0;
	}

	// 특정 투표 수정 (관리자)
	@PutMapping("/votes/{voteId}")
	public int editVote(
			@PathVariable("voteId") int voteId) {
		return 0;
	}

	// 특정 투표 삭제 (관리자)
	@DeleteMapping("/votes/{voteId}")
	public int deleteVote(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	
	
	
	
	// 투표 참여
	@Operation(
	    summary = "투표 참여",
	    description = "로그인 한 사용자가 투표에 참여합니다."
	)
	@ApiResponses(value = {
		@io.swagger.v3.oas.annotations.responses.ApiResponse(
				responseCode = "200",
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
	@PostMapping("/votes/{voteId}/records")
	public ResponseEntity<?> castVote(
			@Parameter(description = "참여할 투표의 ID", required = true, example = "1") @PathVariable int voteId,
			@Parameter(description = "투표 참여 요청 정보", required = true) @RequestBody VoteRecordCreateRequestDto recordRequestDto) {
		
		// 여기에 인증 여부랑 로그인 사용자와 투표자의 일치 여부 검사 추가할 것
		
		VoteRecordCreateResponseDto recordResponseDto = chompService.createVoteRecord(recordRequestDto);
		
		return ResponseEntity.status(VoteSuccessCode.VOTE_RECORD_SUCCESS.getStatus())
				.body(ApiResponse.success(VoteSuccessCode.VOTE_RECORD_SUCCESS, recordResponseDto));
	}	
	
	
	
	// 사용자의 특정 투표 취소
	@DeleteMapping("/votes/{voteId}/records")
	public int cancelVote(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	
	// 사용자의 특정 투표 여부 확인
	@GetMapping("/votes/{voteId}/status")
	public int checkCastVoteStatus(
			@PathVariable("voteId") int voteId) {
		return 0;
	}
	


	
	
	
	// 특정 매거진 조회
	@Operation(
	    summary = "매거진 상세 조회",
	    description = "ID를 기준으로 매거진의 상세 정보를 조회합니다."
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
	@GetMapping("/megazines/{id}")
	public ResponseEntity<?> readMegazine(@Parameter(description = "조회할 매거진의 ID", required = true, example = "1") @PathVariable int id) {
		MegazineReadResponseDto megazineDto = chompService.readMegazineById(id);
		
		return ResponseEntity.status(MegazineSuccessCode.MEGAZINE_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(MegazineSuccessCode.MEGAZINE_READ_SUCCESS, megazineDto));	
	}
	
	

	// 새 매거진 등록 (관리자)
	@PostMapping("/megazines")
	public ResponseEntity<?> writeMegazines() {
		return null;
	}

	
	
	// 특정 매거진 수정 (관리자)
	@PutMapping("/megazines/{id}")
	public ResponseEntity<?> editMegazine(@PathVariable int id) {
		return null;
	}

	
	
	// 특정 매거진 삭제 (관리자)
	@DeleteMapping("/megazines/{id}")
	public ResponseEntity<?> deleteMegazine(@PathVariable int id) {
		return null;
	}
	
	
	
	
	// 특정 이벤트 조회
	@Operation(
	    summary = "이벤트 상세 조회",
	    description = "ID를 기준으로 이벤트의 상세 정보를 조회합니다."
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
	@GetMapping("/events/{id}")
	public ResponseEntity<?> readEvent(@Parameter(description = "조회할 이벤트의 ID", required = true, example = "1") @PathVariable int id) {
		EventReadResponseDto eventDto = chompService.readEventById(id);
		
		return ResponseEntity.status(EventSuccessCode.EVENT_READ_SUCCESS.getStatus())
				.body(ApiResponse.success(EventSuccessCode.EVENT_READ_SUCCESS, eventDto));
	}

}
