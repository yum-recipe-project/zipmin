package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.CookingErrorCode;
import com.project.zipmin.dto.ClassApplyCreateRequestDto;
import com.project.zipmin.dto.ClassApplyCreateResponseDto;
import com.project.zipmin.dto.ClassReadResponseDto;
import com.project.zipmin.dto.ClassScheduleReadResponseDto;
import com.project.zipmin.dto.ClassTargetReadResponseDto;
import com.project.zipmin.dto.ClassTutorReadResponseDto;
import com.project.zipmin.entity.Class;
import com.project.zipmin.entity.ClassApply;
import com.project.zipmin.entity.ClassSchedule;
import com.project.zipmin.entity.ClassTarget;
import com.project.zipmin.entity.ClassTutor;
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
	
	@Autowired
	private ClassRepository classRepository;
	@Autowired
	private ClassTargetRepository targetRepository;
	@Autowired
	private ClassScheduleRepository scheduleRepository;
	@Autowired
	private ClassTutorRepository tutorRepository;
	@Autowired
	private ClassApplyRepository applyRepository;
	
	@Autowired
	private UserService userService;
	
	private final ClassMapper classMapper;
	private final ClassTargetMapper targetMapper;
	private final ClassScheduleMapper scheduleMapper;
	private final ClassTutorMapper tutorMapper;
	private final ClassApplyMapper applyMapper;
	
	
	
	// 클래스 목록 조회
	public Page<ClassReadResponseDto> readClassPage() {
		return null;
	}
	
	
	
	// 클래스 상세 조회
	public ClassReadResponseDto readClassById(int id) {
		
		Class classs = classRepository.findById(id)
				.orElseThrow(() -> new ApiException(CookingErrorCode.COOKING_NOT_FOUND));
		
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
			throw new ApiException(CookingErrorCode.COOKING_TARGET_READ_LIST_FAIL);
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
			throw new ApiException(CookingErrorCode.COOKING_SCHEDULE_READ_LIST_FAIL);
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
			throw new ApiException(CookingErrorCode.COOKING_TUTOR_READ_FAIL);
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
		System.err.println(id + " + " + userId);
		
		return classDto;
	}
	
	
	
	// 클래스 지원을 작성하는 함수
	public ClassApplyCreateResponseDto createApply(ClassApplyCreateRequestDto applyDto) {
		
		// 입력값 검증
		if (applyDto == null || applyDto.getClassId() == null || applyDto.getUserId() == null || applyDto.getReason() == null) {
			throw new ApiException(CookingErrorCode.COOKING_APPLY_INVALID_INPUT);
		}
		
		// 중복 신청 검사
		if (applyRepository.existsByClasssIdAndUserId(applyDto.getClassId(), applyDto.getUserId())) {
			throw new ApiException(CookingErrorCode.COOKING_APPLY_DUPLICATE);
		}
		
		// 클래스 지원 생성
		ClassApply apply = applyMapper.toEntity(applyDto);
		try {
			apply = applyRepository.save(apply);
			return applyMapper.toCreateResponseDto(apply);
		}
		catch (Exception e) {
			throw new ApiException(CookingErrorCode.COOKING_APPLY_CREATE_FAIL);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
