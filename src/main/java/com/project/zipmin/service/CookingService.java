package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.project.zipmin.api.ClassErrorCode;
import com.project.zipmin.api.VoteErrorCode;
import com.project.zipmin.dto.ClassApplyCreateRequestDto;
import com.project.zipmin.dto.ClassApplyCreateResponseDto;
import com.project.zipmin.dto.ClassApplyDeleteRequestDto;
import com.project.zipmin.dto.ClassApplyReadResponseDto;
import com.project.zipmin.dto.ClassApplyUpdateRequestDto;
import com.project.zipmin.dto.ClassApplyUpdateResponseDto;
import com.project.zipmin.dto.ClassApprovalUpdateRequestDto;
import com.project.zipmin.dto.ClassMyApplyReadResponseDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.ClassScheduleReadResponseDto;
import com.project.zipmin.dto.ClassTargetReadResponseDto;
import com.project.zipmin.dto.ClassTutorReadResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Approval;
import com.project.zipmin.entity.Class;
import com.project.zipmin.entity.ClassApply;
import com.project.zipmin.entity.ClassSchedule;
import com.project.zipmin.entity.ClassTarget;
import com.project.zipmin.entity.ClassTutor;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.ClassApplyMapper;
import com.project.zipmin.mapper.ClassMapper;
import com.project.zipmin.mapper.ClassScheduleMapper;
import com.project.zipmin.mapper.ClassTargetMapper;
import com.project.zipmin.mapper.ClassTutorMapper;
import com.project.zipmin.repository.ClassApplyRepository;
import com.project.zipmin.repository.ClassRepository;
import com.project.zipmin.repository.ClassScheduleRepository;
import com.project.zipmin.repository.ClassTargetRepository;
import com.project.zipmin.repository.ClassTutorRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CookingService {
	
	private final ClassRepository classRepository;
	private final  ClassTargetRepository targetRepository;
	private final ClassScheduleRepository scheduleRepository;
	private final ClassTutorRepository tutorRepository;
	private final ClassApplyRepository applyRepository;
	
	private final UserService userService;
	
	private final ClassMapper classMapper;
	private final ClassTargetMapper targetMapper;
	private final ClassScheduleMapper scheduleMapper;
	private final ClassTutorMapper tutorMapper;
	private final ClassApplyMapper applyMapper;
	
	@Value("${app.upload.public-path:/files}")
	private String publicPath;
	
	
	
	
	
	// 클래스 목록 조회
	public Page<ClassReadResponseDto> readClassPage(String category, String keyword, String approval, String status, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
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
				case "postdate-desc":
					sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id"));
					break;
				case "postdate-asc":
					sortSpec = Sort.by(Sort.Order.asc("postdate"), Sort.Order.asc("id"));
					break;
				case "eventdate-desc":
					sortSpec = Sort.by(Sort.Order.desc("eventdate"), Sort.Order.desc("id"));
					break;
				case "eventdate-asc":
					sortSpec = Sort.by(Sort.Order.asc("eventdate"), Sort.Order.desc("id"));
					break;
				case "title-desc":
					sortSpec = Sort.by(Sort.Order.desc("title"), Sort.Order.desc("id"));
					break;
				case "title-asc":
					sortSpec = Sort.by(Sort.Order.asc("title"), Sort.Order.desc("id"));
					break;
				case "applycount-desc":
					sortSpec = Sort.by(Sort.Order.desc("applycount"), Sort.Order.desc("id"));
					break;
				case "applycount-asc":
					sortSpec = Sort.by(Sort.Order.asc("applycount"), Sort.Order.desc("id"));
					break;
				default:
					break;
		    }
		}
		
		// 기존 페이지 객체에 정렬 주입
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);
		
		// 쿠킹클래스 목록 조회
		Page<Class> classPage = null;
		
		try {
			boolean hasCategory = category != null && !category.isBlank();
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			boolean hasApproval = approval != null && !approval.isBlank();
			boolean hasStatus = status != null && !status.isBlank();
			Date today = new Date();
			
			// 승인 상태
			Integer approvalCode = null;
			if (hasApproval) {
				try {
					approvalCode = Approval.valueOf(approval).getCode();
				}
				catch (Exception e) {
					throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
				}
			}
			
			if (!hasCategory) {
				// 전체 카테고리
				if (!hasStatus) {
					// 진행 상태 없음
					if (!hasApproval) {
						// 승인 상태 없음
						classPage = hasKeyword
								? classRepository.findAllByTitleContainingIgnoreCase(keyword, sortedPageable)
								: classRepository.findAll(sortedPageable);
					}
					else {
						// 승인 상태 있음
						classPage = hasKeyword
								? classRepository.findAllByApprovalAndTitleContainingIgnoreCase(approvalCode, keyword, sortedPageable)
								: classRepository.findAllByApproval(approvalCode, sortedPageable);
	                }
	            }
				else {
					// 진행 상태 있음
					if ("open".equals(status)) {
						if (!hasApproval) {
							classPage = hasKeyword
									? classRepository.findAllByTitleContainingIgnoreCaseAndNoticedateAfter(keyword, today, sortedPageable)
									: classRepository.findAllByNoticedateAfter(today, sortedPageable);
							}
						else {
							classPage = hasKeyword
									? classRepository.findAllByApprovalAndTitleContainingIgnoreCaseAndNoticedateAfter(approvalCode, keyword, today, sortedPageable)
									: classRepository.findAllByApprovalAndNoticedateAfter(approvalCode, today, sortedPageable);
						}
					}
					else if ("close".equals(status)) {
						if (!hasApproval) {
							classPage = hasKeyword
									? classRepository.findAllByTitleContainingIgnoreCaseAndNoticedateBefore(keyword, today, sortedPageable)
									: classRepository.findAllByNoticedateBefore(today, sortedPageable);
						}
						else {
							classPage = hasKeyword
									? classRepository.findAllByApprovalAndTitleContainingIgnoreCaseAndNoticedateBefore(approvalCode, keyword, today, sortedPageable)
									: classRepository.findAllByApprovalAndNoticedateBefore(approvalCode, today, sortedPageable);
						}
					}
					else {
						throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
					}
	            }
			}
			else {
				// 카테고리 지정
				if (!hasStatus) {
					// 진행 상태 없음
					if (!hasApproval) {
						// 승인 상태 없음
						classPage = hasKeyword
								? classRepository.findAllByCategoryAndTitleContainingIgnoreCase(category, keyword, sortedPageable)
								: classRepository.findAllByCategory(category, sortedPageable);
					}
					else {
						// 승인 상태 있음
						classPage = hasKeyword
								? classRepository.findAllByCategoryAndApprovalAndTitleContainingIgnoreCase(category, approvalCode, keyword, sortedPageable)
								: classRepository.findAllByCategoryAndApproval(category, approvalCode, sortedPageable);
					}
	            }
				else {
					// 진행 상태 있음
					if ("open".equals(status)) {
						if (!hasApproval) {
							classPage = hasKeyword
									? classRepository.findAllByCategoryAndTitleContainingIgnoreCaseAndNoticedateAfter(category, keyword, today, sortedPageable)
									: classRepository.findAllByCategoryAndNoticedateAfter(category, today, sortedPageable);
						}
						else {
							classPage = hasKeyword
									? classRepository.findAllByCategoryAndApprovalAndTitleContainingIgnoreCaseAndNoticedateAfter(category, approvalCode, keyword, today, sortedPageable)
									: classRepository.findAllByCategoryAndApprovalAndNoticedateAfter(category, approvalCode, today, sortedPageable);
						}
					}
					else if ("close".equals(status)) {
						if (!hasApproval) {
							classPage = hasKeyword
									? classRepository.findAllByCategoryAndTitleContainingIgnoreCaseAndNoticedateBefore(category, keyword, today, sortedPageable)
									: classRepository.findAllByCategoryAndNoticedateBefore(category, today, sortedPageable);
						}
						else {
							classPage = hasKeyword
									? classRepository.findAllByCategoryAndApprovalAndTitleContainingIgnoreCaseAndNoticedateBefore(category, approvalCode, keyword, today, sortedPageable)
									: classRepository.findAllByCategoryAndApprovalAndNoticedateBefore(category, approvalCode, today, sortedPageable);
						}
					}
					else {
						throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
					}
				}
			}
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_READ_LIST_FAIL);
		}
		
		// 쿠킹클래스 목록 응답 구성
		Date today = new Date();
		List<ClassReadResponseDto> classDtoList = new ArrayList<>();
		for (Class classs : classPage) {
			ClassReadResponseDto classDto = classMapper.toReadResponseDto(classs);
			
			// 신청자
			classDto.setUsername(classs.getUser().getUsername());			
			classDto.setNickname(classs.getUser().getNickname());			
			// classDto.setUsername(classs.getUser().getUsername());	
			
			// 이미지
			if (classs.getImage() != null) {
				classDto.setImage(publicPath + "/" + classDto.getImage());
			}
			
			// 상태
			Boolean isOpened = today.before(classDto.getNoticedate());
			classDto.setOpened(isOpened);
			
			classDtoList.add(classDto);
		}
		
		return new PageImpl<>(classDtoList, pageable, classPage.getTotalElements());
	}
	
	
	
	
	
	// 클래스 상세 조회
	public ClassReadResponseDto readClassById(int id) {
		
		Class classs = classRepository.findById(id)
				.orElseThrow(() -> new ApiException(ClassErrorCode.CLASS_NOT_FOUND));
		
		// 클래스 조회
		ClassReadResponseDto classDto = classMapper.toReadResponseDto(classs);
		
		// 클래스 대상 조회
		try {
			List<ClassTarget> targetList = targetRepository.findByClasssId(id);
			List<ClassTargetReadResponseDto> targetDtoList = new ArrayList<>();
			for (ClassTarget target : targetList) {
				ClassTargetReadResponseDto targetDto = targetMapper.toReadResponseDto(target);
				targetDtoList.add(targetDto);
			}
			classDto.setTargetList(targetDtoList);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_TARGET_READ_LIST_FAIL);
		}
		
		// 클래스 커리큘럼 조회
		try {
			List<ClassSchedule> scheduleList = scheduleRepository.findByClasssId(id);
			List<ClassScheduleReadResponseDto> scheduleDtoList = new ArrayList<>();
			for (ClassSchedule schedule : scheduleList) {
				ClassScheduleReadResponseDto scheduleDto = scheduleMapper.toReadResponseDto(schedule);
				scheduleDtoList.add(scheduleDto);
			}
			classDto.setScheduleList(scheduleDtoList);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_SCHEDULE_READ_LIST_FAIL);
		}
		
		// 클래스 강사 조회
		try {
			List<ClassTutor> tutorList = tutorRepository.findByClasssId(id);
			List<ClassTutorReadResponseDto> tutorDtoList = new ArrayList<>();
			for (ClassTutor tutor : tutorList) {
				ClassTutorReadResponseDto tutorDto = tutorMapper.toReadResponseDto(tutor);
				tutorDtoList.add(tutorDto);
			}
			classDto.setTutorList(tutorDtoList);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_TUTOR_READ_FAIL);
		}
		
		// 쿠킹클래스 신청 여부 조회
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		classDto.setApplystatus(false);

		if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")) {
		    return classDto;
		}

		String username = authentication.getName();
		int userId = userService.readUserByUsername(username).getId();
		classDto.setApplystatus(applyRepository.existsByClasssIdAndUserId(id, userId));
		
		return classDto;
	}
	
	
	
	
	
	// 클래스 삭제
	public void deleteClass(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 클래스 존재 여부 확인
		Class classs = classRepository.findById(id)
				.orElseThrow(() -> new ApiException(ClassErrorCode.CLASS_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (classs.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
				}
				if (classs.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != classs.getUser().getId()) {
						throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (user.getId() != classs.getUser().getId()) {
					throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
				}
			}
		}
		
		// 클래스 삭제
		try {
			classRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_DELETE_FAIL);
		}
		
	}

	
	
	
	
	// 클래스 승인 수정
	public void updateClassApproval(ClassApprovalUpdateRequestDto classDto) {
		
		// 입력값 검증
		if (classDto == null || classDto.getId() == null || classDto.getApproval() == null) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 클래스 존재 여부 확인
		Class classs = classRepository.findById(classDto.getId())
				.orElseThrow(() -> new ApiException(ClassErrorCode.CLASS_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!user.getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
			}
		}
		
		// 승인 상태
		Integer approvalCode = null;
		try {
			approvalCode = Approval.valueOf(classDto.getApproval()).getCode();
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 변경 값 설정
		classs.setApproval(approvalCode);
		
		// 클래스 수정
		try {
			classs = classRepository.save(classs);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_UPDATE_APPROVAL_FAIL);
		}
	}
	
	
	
	
	
	
	
	
	// 사용자가 개설한 클래스 목록 조회
	public Page<ClassReadResponseDto> readClassPageByUserId(Integer userId, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (userId == null || pageable == null) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 클래스 목록 조회
		Page<Class> classPage;
		Date now = new Date();
		
		try {
			switch (sort) {
				case "end" -> classPage = classRepository.findByUserIdAndNoticedateBefore(userId, now, pageable);
				case "progress" -> classPage = classRepository.findByUserIdAndNoticedateAfter(userId, now, pageable);
				default -> classPage = classRepository.findByUserId(userId, pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_READ_LIST_FAIL);
		}
		
		List<ClassReadResponseDto> classDtoList = new ArrayList<ClassReadResponseDto>();
		for (Class classs : classPage) {
			ClassReadResponseDto classDto = classMapper.toReadResponseDto(classs);
			// 여기에 내용 추가
			classDtoList.add(classDto);
		}
		
		return new PageImpl<>(classDtoList, pageable, classPage.getTotalElements());
	}
	
	
	
	// 사용자가 신청한 클래스 목록 조회
	public Page<ClassMyApplyReadResponseDto> readApplyClassPageByUserId(Integer userId, String sort, Pageable pageable) {

	    // 입력값 검증

		
		// 사용자 신청 목록 조회
	    Page<ClassApply> applyPage;
	    Date now = new Date();

	    try {
	        switch (sort) {
	            case "end" -> applyPage = applyRepository.findByUserIdAndClasss_EventdateBefore(userId, now, pageable);
	            case "progress" -> applyPage = applyRepository.findByUserIdAndClasss_EventdateAfter(userId, now, pageable);
	            default -> applyPage = applyRepository.findByUserId(userId, pageable);
	        }
	    }
	    catch (Exception e) {
	        throw new ApiException(ClassErrorCode.CLASS_APPLY_READ_LIST_FAIL);
	    }

	    List<ClassMyApplyReadResponseDto> classDtoList = new ArrayList<>();
	    for (ClassApply apply : applyPage.getContent()) {
	        ClassMyApplyReadResponseDto classDto = classMapper.toReadMyApplyResponseDto(apply.getClasss());
	        classDto.setApplyId(apply.getId());
	        classDtoList.add(classDto);
	    }

	    return new PageImpl<>(classDtoList, pageable, applyPage.getTotalElements());
	}

	
	
	
	
	
	
	// 클래스 신청을 작성하는 함수
	public ClassApplyCreateResponseDto createApply(ClassApplyCreateRequestDto applyDto) {
		
		// 입력값 검증
		if (applyDto == null || applyDto.getClassId() == null || applyDto.getUserId() == null || applyDto.getReason() == null) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_INVALID_INPUT);
		}
		
		// 중복 신청 검사
		if (applyRepository.existsByClasssIdAndUserId(applyDto.getClassId(), applyDto.getUserId())) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_DUPLICATE);
		}
		
		// 클래스 지원 생성
		ClassApply apply = applyMapper.toEntity(applyDto);
		try {
			apply = applyRepository.save(apply);
			return applyMapper.toCreateResponseDto(apply);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_CREATE_FAIL);
		}
		
	}
	
	
	
	
	// 클래스 신청 목록 조회
	public Page<ClassApplyReadResponseDto> readApplyPageById(Integer id, Integer sort, Pageable pageable) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_INVALID_INPUT);
		}
		
		// 클래스 신청 목록 조회
		Page<ClassApply> applyPage;
		try {
			applyPage = (sort == -1)
					? applyRepository.findByClasssId(id, pageable)
					: applyRepository.findByClasssIdAndSelected(id, sort, pageable);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_READ_LIST_FAIL);
		}
		
		List<ClassApplyReadResponseDto> applyDtoList = new ArrayList<ClassApplyReadResponseDto>();
		for (ClassApply apply : applyPage) {
			ClassApplyReadResponseDto applyDto = applyMapper.toReadResponseDto(apply);
			applyDto.setName(apply.getUser().getName());
			applyDtoList.add(applyDto);
		}
		
		return new PageImpl<>(applyDtoList, pageable, applyPage.getTotalElements());
	}
	
	
	
	
	// 클래스 신청 수정
	public ClassApplyUpdateResponseDto updateApply(ClassApplyUpdateRequestDto applyDto) {
		
		// 입력값 검증
		if (applyDto == null || applyDto.getId() == null) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_INVALID_INPUT);
		}
		
		// 클래스 여부 판단
		Class classs = classRepository.findById(applyDto.getClassId())
				.orElseThrow(() -> new ApiException(ClassErrorCode.CLASS_NOT_FOUND));
 		
		// 클래스 지원 여부 판단
		ClassApply apply = applyRepository.findById(applyDto.getId())
				.orElseThrow(() -> new ApiException(ClassErrorCode.CLASS_APPLY_NOT_FOUND));
		
		// 필요한 필드만 수정
		if (applyDto.getReason() != null) {
			apply.setReason(applyDto.getReason());
		}
		if (applyDto.getQuestion() != null) {
			apply.setQuestion(applyDto.getQuestion());
		}
		if (applyDto.getSelected() != null) {
			apply.setSelected(applyDto.getSelected());
		}
		if (applyDto.getAttend() != null) {
			apply.setAttend(applyDto.getAttend());
		}
		
		// 클래스 지원 수정
		try {
			apply = applyRepository.save(apply);
			return applyMapper.toUpdateResponseDto(apply);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_CREATE_FAIL);
		}
	}
	
	
	
	// 클래스 신청 삭제
	public void deleteApply(ClassApplyDeleteRequestDto applyDto) {
		
		// 입력값 검증
		if (applyDto == null || applyDto.getId() == null || applyDto.getUserId() == null || applyDto.getClassId() == null) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_INVALID_INPUT);
		}
		
		// 클래스 신청 존재 여부 판단
		ClassApply apply = applyRepository.findById(applyDto.getId())
				.orElseThrow(() -> new ApiException(ClassErrorCode.CLASS_APPLY_NOT_FOUND));
		
		// 소유자 검증
		if (!userService.readUserById(applyDto.getUserId()).getRole().equals(Role.ROLE_ADMIN)) {
			if (apply.getUser().getId() != applyDto.getUserId()) {
				throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
			}
		}
		
	    // 삭제 가능 여부 판단
		Class classs = classRepository.findById(applyDto.getClassId())
				.orElseThrow(() -> new ApiException(ClassErrorCode.CLASS_NOT_FOUND));
	    Date now = new Date();
	    if (classs.getNoticedate().before(now) && apply.getSelected() == 1) {
	        throw new ApiException(ClassErrorCode.CLASS_ALREADY_ENDED);
	    }
		
		// 클래스 신청 삭제
		try {
			applyRepository.deleteById(applyDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_DELETE_FAIL);
		}
		
	}
	
	
	
	
	
	
	
	
	
}
