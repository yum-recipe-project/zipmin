package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ClassErrorCode;
import com.project.zipmin.dto.cooking.ClassApplyCreateRequestDto;
import com.project.zipmin.dto.cooking.ClassApplyCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassApplyReadResponseDto;
import com.project.zipmin.dto.cooking.ClassApplyStatusUpdateRequestDto;
import com.project.zipmin.dto.cooking.ClassApplyUpdateResponseDto;
import com.project.zipmin.dto.cooking.ClassCreateRequestDto;
import com.project.zipmin.dto.cooking.ClassCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassReadResponseDto;
import com.project.zipmin.dto.cooking.ClassScheduleCreateRequestDto;
import com.project.zipmin.dto.cooking.ClassScheduleCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassScheduleReadResponseDto;
import com.project.zipmin.dto.cooking.ClassTargetCreateRequestDto;
import com.project.zipmin.dto.cooking.ClassTargetCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassTargetReadResponseDto;
import com.project.zipmin.dto.cooking.ClassTutorCreateReqeustDto;
import com.project.zipmin.dto.cooking.ClassTutorCreateResponseDto;
import com.project.zipmin.dto.cooking.ClassTutorReadResponseDto;
import com.project.zipmin.dto.cooking.ClassUpdateRequestDto;
import com.project.zipmin.dto.cooking.ClassUpdateResponseDto;
import com.project.zipmin.dto.user.UserReadResponseDto;
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
	private final ClassTargetRepository targetRepository;
	private final ClassScheduleRepository scheduleRepository;
	private final ClassTutorRepository tutorRepository;
	private final ClassApplyRepository applyRepository;
	private final ClassMapper classMapper;
	private final ClassTargetMapper targetMapper;
	private final ClassScheduleMapper scheduleMapper;
	private final ClassTutorMapper tutorMapper;
	private final ClassApplyMapper applyMapper;
	private final UserService userService;
	private final FileService fileService;
	
	@Value("${app.upload.public-path:/files}")
	private String publicPath;
	
	
	
	
	
	// 클래스 목록 조회
	public Page<ClassReadResponseDto> readClassPage(String keyword, String category, int approval, int status, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 쿠킹클래스 목록 조회
		Page<Class> classPage;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			boolean hasCategory = category != null && !category.isBlank();
			boolean hasApproval = approval == 0 || approval == 1 || approval == 2;
			boolean hasStatus = status == 0 || status == 1;
			
			if (hasCategory) {
				if (hasApproval) {
					if (hasStatus) {
						classPage = hasKeyword
								? classRepository.findAllByCategoryAndApprovalAndStatusAndTitleContainingIgnoreCase(category, approval, status, keyword, pageable)
								: classRepository.findAllByCategoryAndApprovalAndStatus(category, approval, status, pageable);
					}
					else {
						classPage = hasKeyword
								? classRepository.findAllByCategoryAndApprovalAndTitleContainingIgnoreCase(category, approval, keyword, pageable)
								: classRepository.findAllByCategoryAndApproval(category, approval, pageable);
					}
				}
				else {
					if (hasStatus) {
						classPage = hasKeyword
								? classRepository.findAllByCategoryAndStatusAndTitleContainingIgnoreCase(category, status, keyword, pageable)
								: classRepository.findAllByCategoryAndStatus(category, status, pageable);
					}
					else {
						classPage = hasKeyword
								? classRepository.findAllByCategoryAndTitleContainingIgnoreCase(category, keyword, pageable)
								: classRepository.findAllByCategory(category, pageable);
					}
				}
			}
			else {
				if (hasApproval) {
					if (hasStatus) {
						classPage = hasKeyword
								? classRepository.findAllByApprovalAndStatusAndTitleContainingIgnoreCase(approval, status, keyword, pageable)
								: classRepository.findAllByApprovalAndStatus(approval, status, pageable);
					}
					else {
						classPage = hasKeyword
								? classRepository.findAllByApprovalAndTitleContainingIgnoreCase(approval, keyword, pageable)
								: classRepository.findAllByApproval(approval, pageable);
					}
				}
				else {
					if (hasStatus) {
						classPage = hasKeyword
								? classRepository.findAllByStatusAndTitleContainingIgnoreCase(status, keyword, pageable)
								: classRepository.findAllByStatus(approval, pageable);
					}
					else {
						classPage = hasKeyword
								? classRepository.findAllByTitleContainingIgnoreCase(keyword, pageable)
								: classRepository.findAll(pageable);
					}
				}
			}
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_READ_LIST_FAIL);
		}
		
		// 쿠킹클래스 목록 응답 구성
		List<ClassReadResponseDto> classDtoList = new ArrayList<>();
		for (Class classs : classPage) {
			ClassReadResponseDto classDto = classMapper.toReadResponseDto(classs);
			classDto.setUsername(classs.getUser().getUsername());			
			classDto.setNickname(classs.getUser().getNickname());			
			classDto.setImage(publicPath + "/" + classDto.getImage());
			classDtoList.add(classDto);
		}
		
		return new PageImpl<>(classDtoList, pageable, classPage.getTotalElements());
	}
	
	
	
	
	
	// 사용자의 클래스 목록 조회
	public Page<ClassReadResponseDto> readClassPageByUserId(int userId,  int approval, int status, Pageable pageable) {
		
		// 입력값 검증
		if (userId < 0 || pageable == null) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != userId) {
					throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
				}
			}
		}
		
		// 클래스 목록 조회
		Page<Class> classPage = null;
		try {
			boolean hasApproval = approval == 0 || approval == 1 || approval == 2;
			boolean hasStatus = status == 0 || status == 1;
			
			if (hasStatus) {
				classPage = hasApproval
						? classRepository.findAllByUserIdAndApprovalAndStatus(userId, approval, status, pageable)
						: classRepository.findAllByUserIdAndStatus(userId, status, pageable);
			}
			else {
				classPage = hasApproval
						? classRepository.findAllByUserIdAndApproval(userId, approval, pageable)
						: classRepository.findAllByUserId(userId, pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_READ_LIST_FAIL);
		}
		
		// 클래스 목록 응답 구성
		List<ClassReadResponseDto> classDtoList = new ArrayList<ClassReadResponseDto>();
		for (Class classs : classPage) {
			ClassReadResponseDto classDto = classMapper.toReadResponseDto(classs);
			classDto.setImage(publicPath + "/" + classDto.getImage());
			classDtoList.add(classDto);
		}
		
		return new PageImpl<>(classDtoList, pageable, classPage.getTotalElements());
	}
	
	
	
	
	
	// 사용자가 신청한 클래스 목록 조회
	public Page<ClassReadResponseDto> readAppliedClassPageByUserId(int userId, String keyword, int status, Pageable pageable) {
		
		// 입력값 검증
		if (userId < 0 || pageable == null) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != userId) {
					throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
				}
			}
		}
		
		// 클래스 신청 목록 조회
		List<ClassApply> applyList;
		List<ClassApplyReadResponseDto> applyDtoList;
		try {
			applyList = applyRepository.findAllByUserId(userId);
			applyDtoList = applyMapper.toReadResponseDtoList(applyList);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_READ_LIST_FAIL);
		}
		
		// 신청한 클래스 일련번호 추출
		List<Integer> classIdList = applyDtoList.stream()
				.map(ClassApplyReadResponseDto::getClassId)
				.toList();
		
		// 신청한 클래스 목록 조회
		Page<Class> classPage = null;
		try {
			boolean hasKeyword = keyword != null && keyword.isBlank();
			boolean hasStatus = status == 0 || status == 1;
			
			if (hasStatus) {
				classPage = hasKeyword
						? classRepository.findAllByIdInAndStatusAndTitleContainingIgnoreCase(classIdList, status, keyword, pageable)
						: classRepository.findAllByIdInAndStatus(classIdList, status, pageable);
			}
			else {
				classPage = hasKeyword
						? classRepository.findAllByIdInAndStatusAndTitleContainingIgnoreCase(classIdList, status, keyword, pageable)
						: classRepository.findAllByIdIn(classIdList, pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_READ_LIST_FAIL);
		}
		
		// 신청한 클래스 목록 응답 구성
		List<ClassReadResponseDto> classDtoList = new ArrayList<>();
		for (Class classs : classPage) {
			ClassReadResponseDto classDto = classMapper.toReadResponseDto(classs);
			if (classs.getImage() != null) {
				classDto.setImage(publicPath + "/" + classDto.getImage());
			}
			classDtoList.add(classDto);
		}
		
		return new PageImpl<>(classDtoList, pageable, classPage.getTotalElements());
	}
	
	
	
	
	
	// 쿠킹클래스 상세 조회
	public ClassReadResponseDto readClassById(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 쿠킹클래스 조회
		ClassReadResponseDto classDto;
		try {
			Class classs = classRepository.findById(id);
			classDto = classMapper.toReadResponseDto(classs);
			classDto.setImage(publicPath + "/" + classDto.getImage());
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
				UserReadResponseDto userDto = userService.readUserByUsername(authentication.getName());
				classDto.setApplied(applyRepository.existsByClasssIdAndUserId(id, userDto.getId()));
				if (countClassAttend(userDto.getId()) < 3) {
					classDto.setAble(true);
				}
				
			}
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_NOT_FOUND);
		}
		
		// 쿠킹클래스 교육대상 목록 조회
		try {
			List<ClassTarget> targetList = targetRepository.findAllByClasssId(id);
			List<ClassTargetReadResponseDto> targetDtoList = targetMapper.toReadResponseDtoList(targetList);
			classDto.setTargetList(targetDtoList);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_TARGET_READ_LIST_FAIL);
		}
		
		// 쿠킹클래스 커리큘럼 목록 조회
		try {
			List<ClassSchedule> scheduleList = scheduleRepository.findByClasssId(id);
			List<ClassScheduleReadResponseDto> scheduleDtoList = scheduleMapper.toReadResponseDtoList(scheduleList);
			classDto.setScheduleList(scheduleDtoList);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_SCHEDULE_READ_LIST_FAIL);
		}
		
		// 쿠킹클래스 강사 목록 조회
		try {
			List<ClassTutor> tutorList = tutorRepository.findByClasssId(id);
			List<ClassTutorReadResponseDto> tutorDtoList = new ArrayList<>();
			for (ClassTutor tutor : tutorList) {
				ClassTutorReadResponseDto tutorDto = tutorMapper.toReadResponseDto(tutor);
				tutorDto.setImage(publicPath + "/" + tutorDto.getImage());
				tutorDtoList.add(tutorDto);
			}
			classDto.setTutorList(tutorDtoList);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_TUTOR_READ_LIST_FAIL);
		}
		
		return classDto;
	}
	
	
	
	
	
	// 클래스 작성
	// TODO : Noticedate
	public ClassCreateResponseDto createClass(ClassCreateRequestDto createRequestDto, MultipartFile classImage, List<MultipartFile> tutorImages)  {
		
		// 입력값 검증
		if (createRequestDto == null
				|| createRequestDto.getTitle() == null
				|| createRequestDto.getCategory() == null
				|| createRequestDto.getIntroduce() == null
				|| createRequestDto.getPlace() == null
				|| createRequestDto.getEventdate() == null
				|| createRequestDto.getStarttime() == null
				|| createRequestDto.getEndtime() == null
				|| createRequestDto.getHeadcount() == 0
				|| createRequestDto.getNeed() == null
				|| createRequestDto.getUserId() == 0) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		if (classImage == null) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 입력값 검증 (교육대상)
		if (createRequestDto.getTargetList() == null) {
			throw new ApiException(ClassErrorCode.CLASS_TARGET_INVALID_INPUT);
		}
		for (ClassTargetCreateRequestDto targetDto : createRequestDto.getTargetList()) {
			if (targetDto.getContent() == null) {
				throw new ApiException(ClassErrorCode.CLASS_TARGET_INVALID_INPUT);
			}
		}
		
		// 입력값 검증 (교육일정)
		if (createRequestDto.getScheduleList() == null || createRequestDto.getScheduleList().isEmpty()) {
			throw new ApiException(ClassErrorCode.CLASS_SCHEDULE_INVALID_INPUT);
		}
		for (ClassScheduleCreateRequestDto scheduleDto : createRequestDto.getScheduleList()) {
			if (scheduleDto.getStarttime() == null || scheduleDto.getEndtime() == null ||
				scheduleDto.getTitle() == null || scheduleDto.getTitle().trim().isEmpty()) {
				throw new ApiException(ClassErrorCode.CLASS_SCHEDULE_INVALID_INPUT);
			}
			if (scheduleDto.getEndtime().before(scheduleDto.getStarttime())) {
				throw new ApiException(ClassErrorCode.CLASS_SCHEDULE_INVALID_INPUT);
			}
		}

		// 입력값 검증 (강사)
		if (createRequestDto.getTutorList() == null || createRequestDto.getTutorList().isEmpty()) {
			throw new ApiException(ClassErrorCode.CLASS_TUTOR_INVALID_INPUT);
		}
		for (ClassTutorCreateReqeustDto tutorDto : createRequestDto.getTutorList()) {
			if (tutorDto.getName() == null || tutorDto.getName().trim().isEmpty() ||
				tutorDto.getCareer() == null || tutorDto.getCareer().trim().isEmpty()) {
				throw new ApiException(ClassErrorCode.CLASS_TUTOR_INVALID_INPUT);
			}
		}

		// 중복 검사 
		if (classRepository.existsByTitleAndUserId(createRequestDto.getTitle(), createRequestDto.getUserId())) {
			throw new ApiException(ClassErrorCode.CLASS_CREATE_DUPLICATE);
		}
		
		// 클래스 대표 이미지 저장
		try {
			String classImgUrl = fileService.store(classImage);
			createRequestDto.setImage(classImgUrl);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_FILE_UPLOAD_FAIL);
		}
		
		// 클래스 저장
		Class classEntity = classMapper.toEntity(createRequestDto);
		try {
			classEntity = classRepository.save(classEntity);
			ClassCreateResponseDto classResponseDto = classMapper.toCreateResponseDto(classEntity);

			// 교육대상 저장
			List<ClassTargetCreateResponseDto> targetResponseList = new ArrayList<>();
			for (ClassTargetCreateRequestDto targetDto : createRequestDto.getTargetList()) {
				targetDto.setClassId(classResponseDto.getId());
				ClassTarget targetEntity = targetMapper.toEntity(targetDto);

				try {
					targetEntity = targetRepository.save(targetEntity);
				} catch (Exception e) {
					throw new ApiException(ClassErrorCode.CLASS_TARGET_CREATE_FAIL);
				}

				targetResponseList.add(targetMapper.toCreateResponseDto(targetEntity));
			}

			classResponseDto.setTargetList(targetResponseList);

			List<ClassScheduleCreateResponseDto> scheduleResponseList = new ArrayList<>();
			for (ClassScheduleCreateRequestDto scheduleDto : createRequestDto.getScheduleList()) {
				scheduleDto.setClassId(classResponseDto.getId());

				ClassSchedule scheduleEntity = scheduleMapper.toEntity(scheduleDto);
				try {
					scheduleEntity = scheduleRepository.save(scheduleEntity);
				} catch (Exception e) {
					throw new ApiException(ClassErrorCode.CLASS_SCHEDULE_CREATE_FAIL);
				}
				scheduleResponseList.add(scheduleMapper.toCreateResponseDto(scheduleEntity));
			}
			classResponseDto.setScheduleList(scheduleResponseList);
			
			// 클래스 강사 저장
			List<ClassTutorCreateResponseDto> tutorResponseList = new ArrayList<>();
			for (int i = 0; i < createRequestDto.getTutorList().size(); i++) {
				ClassTutorCreateReqeustDto tutorDto = createRequestDto.getTutorList().get(i);
				tutorDto.setClassId(classResponseDto.getId());

				// 강사 이미지 저장
				try {
					if (tutorImages != null && tutorImages.size() > i && !tutorImages.get(i).isEmpty()) {
						String image = fileService.store(tutorImages.get(i));
						tutorDto.setImage(image);
					}
				}
				catch (Exception e) {
					throw new ApiException(ClassErrorCode.CLASS_TUTOR_FILE_UPLOAD_FAIL);
				}

				// DB 저장
				ClassTutor tutorEntity = tutorMapper.toEntity(tutorDto);
				try {
					tutorEntity = tutorRepository.save(tutorEntity);
				}
				catch (Exception e) {
					throw new ApiException(ClassErrorCode.CLASS_TUTOR_CREATE_FAIL);
				}

				tutorResponseList.add(tutorMapper.toCreateResponseDto(tutorEntity));
			}
			classResponseDto.setTutorList(tutorResponseList);
			
			return classResponseDto;
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// TODO : 더 수정
	// 클래스 수정
	public ClassUpdateResponseDto updateClass(ClassUpdateRequestDto classDto) {
		
		// 입력값 검증
		if (classDto == null || classDto.getId() == 0) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 쿠킹클래스 조회
		Class classs;
		try {
			classs = classRepository.findById(classDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_NOT_FOUND);
		}
		
		// TODO : 권한 확인
		
		// 클래스 기간 검증
		Date today = new Date();
		if (today.after(classs.getNoticedate())) {
			throw new ApiException(ClassErrorCode.CLASS_ALREADY_ENDED);
		}
		
		// 값 설정
		classs.setApproval(classDto.getApproval());
		
		// 클래스 수정
		try {
			classs = classRepository.save(classs);
			return classMapper.toUpdateResponseDto(classs);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_UPDATE_FAIL);
		}
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
			else {
				if (user.getId() != classs.getUser().getId()) {
					throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
				}
			}
		}
		
		// 클래스 삭제 기한 검사
		Date today = new Date();
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (today.after(classs.getNoticedate())) {
					throw new ApiException(ClassErrorCode.CLASS_ALREADY_ENDED);
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

	
	
	
	
	// 클래스 신청 목록 조회
	public Page<ClassApplyReadResponseDto> readApplyPageById(int id, int selected, Pageable pageable) {
		
		// 입력값 검증
		if (id < 0) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_INVALID_INPUT);
		}
		
		// 클래스 조회
		Class classs;
		try {
			classs = classRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto loginUser = userService.readUserByUsername(username);
		if (!loginUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!loginUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (loginUser.getId() != classs.getUser().getId()) {
					throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
				}
			}
		}
		
		// 클래스 신청 목록 조회
		Page<ClassApply> applyPage = null;
		try {
			boolean hasSelected = selected == 0 || selected == 1 || selected == 2;
			
			if (hasSelected) {
				applyPage = applyRepository.findByClasssIdAndSelected(id, selected, pageable);
			}
			else {
				applyPage = applyRepository.findByClasssId(id, pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_READ_LIST_FAIL);
		}
		
		// 클래스 신청 목록 응답 구성
		List<ClassApplyReadResponseDto> applyDtoList = new ArrayList<ClassApplyReadResponseDto>();
		for (ClassApply apply : applyPage) {
			ClassApplyReadResponseDto applyDto = applyMapper.toReadResponseDto(apply);
			applyDto.setName(apply.getUser().getName());
			applyDtoList.add(applyDto);
		}
		
		return new PageImpl<>(applyDtoList, pageable, applyPage.getTotalElements());
	}
	
	
	
	
	
	// 클래스 신청 작성
	public ClassApplyCreateResponseDto createApply(ClassApplyCreateRequestDto applyDto) {
		
		// 입력값 검증
		if (applyDto == null
				|| applyDto.getClassId() == 0
				|| applyDto.getReason() == null
				|| applyDto.getUserId() == 0) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_INVALID_INPUT);
		}
		
		// 클래스 조회
		Class classs;
		try {
			classs = classRepository.findById(applyDto.getClassId());
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_NOT_FOUND);
		}
		
		// 클래스 신청 기간 검사
		Date today = new Date();
		if (classs.getNoticedate().before(today)) {
			throw new ApiException(ClassErrorCode.CLASS_ALREADY_ENDED);
		}
		
		// 신청 가능 여부 조회
		if (countClassAttend(applyDto.getUserId()) > 3) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_UNABLE);
		}
		
		// 중복 신청 검사
		if (applyRepository.existsByClasssIdAndUserId(applyDto.getClassId(), applyDto.getUserId())) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_DUPLICATE);
		}
		
		// 클래스 신청 작성
		ClassApply apply = applyMapper.toEntity(applyDto);
		try {
			apply = applyRepository.save(apply);
			return applyMapper.toCreateResponseDto(apply);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 클래스 신청 상태 수정
	public ClassApplyUpdateResponseDto updateApplySelected(ClassApplyStatusUpdateRequestDto applyDto) {
		
		// 입력값 검증
		if (applyDto == null
				|| applyDto.getId() < 0
				|| applyDto.getClassId() < 0
				|| (applyDto.getSelected() < 0 && applyDto.getAttend() < 0)) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_INVALID_INPUT);
		}
		
		// 클래스 조회
		Class classs;
		try {
			classs = classRepository.findById(applyDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_NOT_FOUND);
		}
		
		// 클래스 지원 여부 판단
		ClassApply apply = applyRepository.findById(applyDto.getId())
				.orElseThrow(() -> new ApiException(ClassErrorCode.CLASS_APPLY_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto loginUser = userService.readUserByUsername(username);
		if (!loginUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!loginUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (loginUser.getId() != classs.getUser().getId()) {
					throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
				}
			}
		}
		
//		// 클래스 선정 기간 검증
//		Date today = new Date();
//		if (applyDto.getSelected() != null) {
//			if (classs.getApproval() != 1 || today.after(classs.getNoticedate())) {
//				throw new ApiException(ClassErrorCode.CLASS_ALREADY_ENDED);
//			}
//		}
//		
//		// 클래스 출석 기간 검증
//		if (applyDto.getAttend() != null) {
//			Calendar nextweek = Calendar.getInstance();
//			nextweek.setTime(classs.getEventdate());
//			nextweek.add(Calendar.DAY_OF_YEAR, 7);
//			if (classs.getApproval() != 1 || today.before(classs.getEventdate()) || today.after(nextweek.getTime())) {
//				throw new ApiException(ClassErrorCode.CLASS_ALREADY_ENDED);
//			}
//		}
		
		// 값 설정
		if (applyDto.getSelected() == 0 || applyDto.getSelected() == 1) {
			apply.setSelected(applyDto.getSelected());
		}
		if (applyDto.getAttend() == 0 || applyDto.getAttend() == 1) {
			apply.setAttend(applyDto.getAttend());
		}
		
		// 클래스 지원 수정
		try {
			apply = applyRepository.save(apply);
			return applyMapper.toUpdateResponseDto(apply);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_UPDATE_FAIL);
		}
	}
	
	
	
	
	
	// 클래스 신청 삭제
	public void deleteApply(int classId, int applyId) {
		
		// 입력값 검증
		if (classId < 0 || applyId < 0) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_INVALID_INPUT);
		}
		
		// 클래스 신청 존재 여부 판단
		ClassApply apply = applyRepository.findById(applyId)
				.orElseThrow(() -> new ApiException(ClassErrorCode.CLASS_APPLY_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
		}
		if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
			if (apply.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
				throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
			}
			else if (apply.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != apply.getUser().getId()) {
				throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
			}
		}
		if (currentUser.getRole().equals(Role.ROLE_USER.name())) {
			if (currentUser.getId() != apply.getUser().getId()) {
				throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
			}
		}
		
		// 클래스 조회
		Class classs;
		try {
			classs = classRepository.findById(classId);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_NOT_FOUND);
		}
		
		if (classs.getId() != classId) {
			throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
		}
		
		// 클래스 기간 검증
		Date today = new Date();
		if (today.after(classs.getNoticedate())) {
			throw new ApiException(ClassErrorCode.CLASS_ALREADY_ENDED);
		}
		
		// 클래스 신청 삭제
		try {
			applyRepository.deleteById(apply.getId());
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_DELETE_FAIL);
		}
		
	}
	
	
	
	
	// 사용자의 클래스 결석 수 조회
	public int countClassAttend(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(ClassErrorCode.CLASS_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto loginUser = userService.readUserByUsername(username);
		if (!loginUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!loginUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (loginUser.getId() != id) {
					throw new ApiException(ClassErrorCode.CLASS_FORBIDDEN);
				}
			}
		}
		
		// 결석한 클래스 신청 목록 조회
		List<ClassApply> applyList = null;
		try {
			applyList = applyRepository.findAllByUserIdAndSelectedAndAttend(id, 1, 0);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_APPLY_READ_LIST_FAIL);
		}
		
		// 결석한 클래스 일련번호 추출
		List<Integer> classIds = applyList.stream()
				.map(apply -> apply.getClasss().getId())
				.toList();
		
		if (classIds.isEmpty()) {
			return 0; 
		}
		
		// 결석한 클래스 목록 조회
		List<Class> classList = new ArrayList<Class>();
		try {
			classList = classRepository.findAllByIdIn(classIds);
		}
		catch (Exception e) {
			throw new ApiException(ClassErrorCode.CLASS_READ_LIST_FAIL);
		}
		
		// 기간 이내 결석 클래스 목록
		Date today = new Date();
		Calendar past = Calendar.getInstance();
		past.add(Calendar.DAY_OF_YEAR, -60);
		Date batchdate = past.getTime();
		int count = (int) classList.stream()
				.filter(classs -> classs.getApproval() == 1)
				.filter(classs -> {
					Calendar tmp = Calendar.getInstance();
					tmp.setTime(classs.getEventdate());
					tmp.add(Calendar.DAY_OF_YEAR, 7);
					return tmp.getTime().before(today);
			})
			.filter(classs -> classs.getEventdate().after(batchdate))
			.count();
		
		return count;
	}
	
}