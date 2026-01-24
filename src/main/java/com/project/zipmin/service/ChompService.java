package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.project.zipmin.api.ChompErrorCode;
import com.project.zipmin.api.EventErrorCode;
import com.project.zipmin.api.MegazineErrorCode;
import com.project.zipmin.api.VoteErrorCode;
import com.project.zipmin.dto.EventCreateRequestDto;
import com.project.zipmin.dto.EventCreateResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.dto.EventUpdateRequestDto;
import com.project.zipmin.dto.EventUpdateResponseDto;
import com.project.zipmin.dto.MegazineCreateRequestDto;
import com.project.zipmin.dto.MegazineCreateResponseDto;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.MegazineUpdateRequestDto;
import com.project.zipmin.dto.MegazineUpdateResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.VoteChoiceUpdateRequestDto;
import com.project.zipmin.dto.VoteRecordCreateRequestDto;
import com.project.zipmin.dto.VoteRecordCreateResponseDto;
import com.project.zipmin.dto.VoteUpdateRequestDto;
import com.project.zipmin.dto.VoteUpdateResponseDto;
import com.project.zipmin.dto.chomp.ChompReadResponseDto;
import com.project.zipmin.dto.chomp.VoteChoiceCreateRequestDto;
import com.project.zipmin.dto.chomp.VoteChoiceReadResponseDto;
import com.project.zipmin.dto.chomp.VoteCreateRequestDto;
import com.project.zipmin.dto.chomp.VoteCreateResponseDto;
import com.project.zipmin.dto.chomp.VoteReadResponseDto;
import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.VoteChoice;
import com.project.zipmin.entity.VoteRecord;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.mapper.VoteChoiceMapper;
import com.project.zipmin.mapper.VoteRecordMapper;
import com.project.zipmin.repository.VoteChoiceRepository;
import com.project.zipmin.repository.VoteRecordRepository;
import com.project.zipmin.repository.ChompRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChompService {
	
	private final ChompRepository chompRepository;
	private final VoteChoiceRepository choiceRepository;
	private final VoteRecordRepository recordRepository;
	
	private final UserService userService;
	private final FileService fileService;
    
	private final ChompMapper chompMapper;
	private final VoteChoiceMapper choiceMapper;
	private final VoteRecordMapper recordMapper;
	
	@Value("${app.upload.public-path:/files}")
	private String publicPath;
	
	
	
	
	
	// 쩝쩝박사 목록 조회
	public Page<ChompReadResponseDto> readChompPage(String keyword, String category, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(ChompErrorCode.CHOMP_INVALID_INPUT);
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 쩝쩝박사 목록 조회
		Page<Chomp> chompPage;
		try {
			boolean hasCategory = (category != null) && (!category.isBlank());
			boolean hasKeyword = (keyword != null) && (!keyword.isBlank());
			
			if (hasCategory) {
				chompPage = hasKeyword
						? chompRepository.findAllByCategoryAndTitleContainingIgnoreCase(category, keyword, pageable)
						: chompRepository.findAllByCategory(category, pageable);
	        }
			else {
				chompPage = hasKeyword
						? chompRepository.findAllByTitleContainingIgnoreCase(keyword, pageable)
						: chompRepository.findAll(pageable);
	        }
		}
		catch (Exception e) {
			throw new ApiException(ChompErrorCode.CHOMP_READ_LIST_FAIL);
		}
		
		// 쩝쩝박사 목록 응답 구성
		Date today = new Date();
		List<ChompReadResponseDto> chompDtoList = new ArrayList<>();
		for (Chomp chomp : chompPage) {
			ChompReadResponseDto chompDto = chompMapper.toReadResponseDto(chomp);
			chompDto.setImage(publicPath + "/" + chompDto.getImage());
			chompDto.setOpened(today.after(chompDto.getOpendate()) && today.before(chompDto.getClosedate()));
			chompDtoList.add(chompDto);
		}
		
		return new PageImpl<>(chompDtoList, pageable, chompPage.getTotalElements());
	}
	
	
	
	
	
	// 투표 상세 조회
	public VoteReadResponseDto readVoteById(int id) {
		
		// 입력값 검증
		if (id == 0) {
			throw new ApiException(VoteErrorCode.VOTE_INVALID_INPUT);
		}
		
		// 투표 조회
		Chomp vote;
		VoteReadResponseDto voteDto;
		try {
			vote = chompRepository.findById(id);
			voteDto = chompMapper.toVoteReadResponseDto(vote);
			voteDto.setImage(publicPath + "/" + voteDto.getImage());
			voteDto.setRecordcount(recordRepository.countByChompId(id));
		}
		catch (Exception e) {
			throw new ApiException(VoteErrorCode.VOTE_NOT_FOUND);
		}
		
		// 투표 옵션 목록 조회
		List<VoteChoice> choiceList;
		try {
			choiceList = choiceRepository.findAllByChompId(id);
			List<VoteChoiceReadResponseDto> choiceDtoList = new ArrayList<>();
			for (VoteChoice choice : choiceList) {
				VoteChoiceReadResponseDto choiceDto = choiceMapper.toReadResponseDto(choice);
				int recordcount = recordRepository.countByChompIdAndChoiceId(id, choiceDto.getId());
				choiceDto.setRecordcount(recordcount);
				double recordrate = (vote.getRecordcount() == 0) ? 0.0 : Math.round(((double) recordcount / voteDto.getRecordcount()) * 100) / 1.0;
				choiceDto.setRecordrate(recordrate);
				choiceDtoList.add(choiceDto);
			}
			voteDto.setChoiceList(choiceDtoList);
		}
		catch (Exception e) {
			throw new ApiException(VoteErrorCode.VOTE_CHOICE_READ_LIST_FAIL);
		}
		
		// 투표 기록 조회
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			if (authentication != null && authentication.isAuthenticated() && !authentication.getPrincipal().equals("anonymousUser")) {
				UserReadResponseDto userDto = userService.readUserByUsername(authentication.getName());
				VoteRecord record = recordRepository.findByUserIdAndChompId(userDto.getId(), voteDto.getId());
				if (record != null) {
					voteDto.setVoted(true);
					voteDto.setChoiceId(record.getChoice().getId());
				}
			}
		}
		catch (Exception e) {
			throw new ApiException(VoteErrorCode.VOTE_RECORD_READ_FAIL);
		}
		
		return voteDto;
	}
	
	
	
	
	
	// 투표 작성
	public VoteCreateResponseDto createVote(VoteCreateRequestDto voteRequestDto, MultipartFile file) {
		
		// 입력값 검증
	    if (voteRequestDto == null
	    		|| voteRequestDto.getTitle() == null
	    		|| voteRequestDto.getOpendate() == null
	    		|| voteRequestDto.getClosedate() == null
	    		|| voteRequestDto.getCategory() == null
	    		|| voteRequestDto.getChoiceList() == null) {
	    	throw new ApiException(VoteErrorCode.VOTE_INVALID_INPUT);
	    }
	    if (voteRequestDto.getOpendate().after(voteRequestDto.getClosedate())) {
	    	throw new ApiException(VoteErrorCode.VOTE_INVALID_PERIOD);
	    }
	    if (file == null) {
	    	throw new ApiException(VoteErrorCode.VOTE_INVALID_FILE);
	    }
	    for (VoteChoiceCreateRequestDto choiceDto : voteRequestDto.getChoiceList()) {
	    	if (choiceDto.getChoice() == null) {
	    		throw new ApiException(VoteErrorCode.VOTE_CHOICE_INVALID_INPUT);
	    	}
	    }
	    
        // 파일 저장
        try {
        	String image = fileService.store(file);
        	voteRequestDto.setImage(image);
        }
        catch (Exception e) {
            throw new ApiException(VoteErrorCode.VOTE_FILE_UPLOAD_FAIL);
        }
		
	    // 투표 작성
	    Chomp vote = chompMapper.toEntity(voteRequestDto);
	    try {
	    	vote = chompRepository.save(vote);
	    	// 투표 옵션 작성
			try {
				for (VoteChoiceCreateRequestDto choiceDto : voteRequestDto.getChoiceList()) {
					choiceDto.setChompId(vote.getId());
					VoteChoice choice = choiceMapper.toEntity(choiceDto);
					choiceRepository.save(choice);
				}
	    	}
	    	catch (Exception e) {
				throw new ApiException(VoteErrorCode.VOTE_CHOICE_CREATE_FAIL);
	    	}
	    	return chompMapper.toVoteCreateResponseDto(vote);
	    }
	    catch (Exception e) {
	    	throw new ApiException(VoteErrorCode.VOTE_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 투표를 수정하는 함수
	public VoteUpdateResponseDto updateVote(VoteUpdateRequestDto voteRequestDto, MultipartFile file) {
		
		// 입력값 검증
		if (voteRequestDto == null || voteRequestDto.getId() == null
				|| voteRequestDto.getTitle() == null || voteRequestDto.getOpendate() == null
				|| voteRequestDto.getClosedate() == null || voteRequestDto.getChoiceList() == null) {
			throw new ApiException(VoteErrorCode.VOTE_INVALID_INPUT);
		}
		if (voteRequestDto.getOpendate().after(voteRequestDto.getClosedate())) {
			throw new ApiException(VoteErrorCode.VOTE_INVALID_PERIOD);
		}
	    for (VoteChoiceUpdateRequestDto choiceDto : voteRequestDto.getChoiceList()) {
	    	if (choiceDto.getChoice() == null) {
	    		throw new ApiException(VoteErrorCode.VOTE_CHOICE_INVALID_INPUT);
	    	}
	    }
		
		// 투표 존재 여부 확인
		Chomp vote = chompRepository.findById(voteRequestDto.getId())
				.orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (vote.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(VoteErrorCode.VOTE_FORBIDDEN);
				}
				if (vote.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != vote.getUser().getId()) {
						throw new ApiException(VoteErrorCode.VOTE_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				throw new ApiException(VoteErrorCode.VOTE_FORBIDDEN);
			}
		}
		
		// 변경 값 설정
		vote.setTitle(voteRequestDto.getTitle());
		vote.setOpendate(voteRequestDto.getOpendate());
		vote.setClosedate(voteRequestDto.getClosedate());
		
		// 파일 저장
        try {
        	if (file != null && !file.isEmpty()) {
        		String image = fileService.store(file);
        		vote.setImage(image);
        	}
        }
        catch (Exception e) {
            throw new ApiException(EventErrorCode.EVENT_FILE_UPLOAD_FAIL);
        }
		
		// 투표 수정
		try {
			vote = chompRepository.save(vote);
			
			// 투표 선택지 수정
			Set<Integer> choiceIdSet = new HashSet<>();
			for (VoteChoiceUpdateRequestDto choiceDto : voteRequestDto.getChoiceList()) {
				choiceDto.setChompId(vote.getId());
				VoteChoice choice = choiceMapper.toEntity(choiceDto);
				try {
					choice = choiceRepository.save(choice);
				}
				catch (Exception e) {
					throw new ApiException(VoteErrorCode.VOTE_CHOICE_UPDATE_FAIL);
				}
	            choiceIdSet.add(choice.getId());
	        }

	        // 선택지 삭제
	        if (choiceIdSet.isEmpty()) {
	            choiceRepository.deleteAllByChompId(voteRequestDto.getId());
	        }
	        else {
	            choiceRepository.deleteAllByChompIdAndIdNotIn(voteRequestDto.getId(), choiceIdSet);
	        }
    		
	    	return chompMapper.toVoteUpdateResponseDto(vote);
	    }
	    catch (Exception e) {
	    	throw new ApiException(VoteErrorCode.VOTE_UPDATE_FAIL);
		}
		
	}
	
	
	
	
	
	// 투표를 삭제하는 함수
	public void deleteVote(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(VoteErrorCode.VOTE_INVALID_INPUT);
		}
		
		// 투표 존재 여부 확인
		Chomp vote = chompRepository.findById(id)
				.orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (vote.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(VoteErrorCode.VOTE_FORBIDDEN);
				}
				if (vote.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != vote.getUser().getId()) {
						throw new ApiException(VoteErrorCode.VOTE_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				throw new ApiException(VoteErrorCode.VOTE_FORBIDDEN);
			}
		}
		
		// 투표 삭제
		try {
			chompRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(VoteErrorCode.VOTE_DELETE_FAIL);
		}
		
	}
	
	
	
	
	
	// 투표 기록을 작성하는 함수
	public VoteRecordCreateResponseDto createVoteRecord(VoteRecordCreateRequestDto recordDto) {
		
		// 입력값 검증
	    if (recordDto == null || recordDto.getUserId() == 0 || recordDto.getChompId() == 0 || recordDto.getChoiceId() == 0) {
	    	throw new ApiException(VoteErrorCode.VOTE_RECORD_INVALID_INPUT);
	    }
	    
	    // *** 투표 존재 여부 확인 추가 ***
	    
	    // 중복 투표 검사
	    if (recordRepository.existsByUserIdAndChompId(recordDto.getUserId(), recordDto.getChompId())) {
	    	throw new ApiException(VoteErrorCode.VOTE_RECORD_DUPLICATE);
	    }
	    
	    // 투표 기간 검사
	    Chomp vote;
	    try {
	    	vote = chompRepository.findById(recordDto.getChompId());
	    }
	    catch (Exception e) {
			throw new ApiException(VoteErrorCode.VOTE_NOT_FOUND);
		}
	    Date now = new Date();
	    if (now.before(vote.getOpendate()) || now.after(vote.getClosedate())) {
	        throw new ApiException(VoteErrorCode.VOTE_NOT_OPENED);
	    }
	    
	    // 투표 기록 저장
	    VoteRecord record = recordMapper.toEntity(recordDto);
	    try {
	    	record = recordRepository.save(record);
	    	return recordMapper.toCreateResponseDto(record);
	    }
	    catch (Exception e) {
	    	throw new ApiException(VoteErrorCode.VOTE_RECORD_CREATE_FAIL);
	    }
	}
	
	
	

	
	// 투표 기록을 삭제하는 함수
	public void deleteVoteRecord(Integer id) {
		
		// 입력값 검증
	    if (id == null) {
	    	throw new ApiException(VoteErrorCode.VOTE_RECORD_INVALID_INPUT);
	    }
	    
	    // 투표 기록 존재 여부 판단
	    String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		
		VoteRecord record;
		try {
			record = recordRepository.findByUserIdAndChompId(user.getId(), id);
		}
		catch (Exception e) {
			throw new ApiException(VoteErrorCode.VOTE_RECORD_NOT_FOUND);
		}
		
	    // 투표 기간 검사
	    Chomp vote = chompRepository.findById(id)
	    	    .orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
	    Date now = new Date();
	    if (now.before(vote.getOpendate()) || now.after(vote.getClosedate())) {
	        throw new ApiException(VoteErrorCode.VOTE_NOT_OPENED);
	    }
		
		// 투표 기록 삭제
		try {
	        recordRepository.delete(record);
		}
		catch (Exception e) {
			throw new ApiException(VoteErrorCode.VOTE_RECORD_DELETE_FAIL);
	    }
	}
	
	
	
	
	
	// 매거진의 상세 내용을 조회하는 함수
	public MegazineReadResponseDto readMegazineById(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_INVALID_INPUT);
		}
		
		Chomp megazine = chompRepository.findById(id)
				.orElseThrow(() -> new ApiException(MegazineErrorCode.MEGAZINE_NOT_FOUND));
		
		MegazineReadResponseDto megazineDto = chompMapper.toMegazineReadResponseDto(megazine);
		megazineDto.setImage(publicPath + "/" + megazineDto.getImage());
		
		return megazineDto;
		
	}
	
	
	
	
	
	// 매거진을 작성하는 함수
	public MegazineCreateResponseDto createMegazine(MegazineCreateRequestDto megazineRequestDto, MultipartFile file) {
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!user.getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(MegazineErrorCode.MEGAZINE_FORBIDDEN);
			}
		}
		megazineRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		// 입력값 검증
	    if (megazineRequestDto == null || megazineRequestDto.getTitle() == null
	    		|| megazineRequestDto.getContent() == null || megazineRequestDto.getCategory() == null) {
	    	throw new ApiException(MegazineErrorCode.MEGAZINE_INVALID_INPUT);
	    }
	    if (file == null) {
	    	throw new ApiException(EventErrorCode.EVENT_INVALID_FILE);
	    }
	    
        // 파일 저장
        try {
        	String image = fileService.store(file);
        	megazineRequestDto.setImage(image);
        }
        catch (Exception e) {
            throw new ApiException(EventErrorCode.EVENT_FILE_UPLOAD_FAIL);
        }
	    
	    // 매거진 저장
	    Chomp megazine = chompMapper.toEntity(megazineRequestDto);
	    try {
	    	megazine = chompRepository.save(megazine);
	    	return chompMapper.toMegazineCreateResponseDto(megazine);
	    }
		catch (Exception e) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 매거진 수정
	public MegazineUpdateResponseDto updateMegazine(MegazineUpdateRequestDto megazineRequestDto, MultipartFile file) {
		
		// 입력값 검증
		if (megazineRequestDto == null || megazineRequestDto.getId() == null
				|| megazineRequestDto.getContent() == null || megazineRequestDto.getTitle() == null) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_INVALID_INPUT);
		}
		// ***** TODO : 파일 수정 추가 *****
		
		// 매거진 존재 여부 확인
		Chomp megazine = chompRepository.findById(megazineRequestDto.getId())
				.orElseThrow(() -> new ApiException(MegazineErrorCode.MEGAZINE_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (megazine.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(MegazineErrorCode.MEGAZINE_FORBIDDEN);
				}
				if (megazine.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != megazine.getUser().getId()) {
						throw new ApiException(MegazineErrorCode.MEGAZINE_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				throw new ApiException(MegazineErrorCode.MEGAZINE_FORBIDDEN);
			}
		}
		
		// 변경 값 설정
		megazine.setTitle(megazineRequestDto.getTitle());
		megazine.setContent(megazineRequestDto.getContent());
		
		// 파일 저장
        try {
        	if (file != null && !file.isEmpty()) {
        		String image = fileService.store(file);
        		megazine.setImage(image);
        	}
        }
        catch (Exception e) {
            throw new ApiException(EventErrorCode.EVENT_FILE_UPLOAD_FAIL);
        }
		
		// 매거진 수정
		try {
			megazine = chompRepository.save(megazine);
			return chompMapper.toMegazineUpdateResponseDto(megazine);
		}
		catch (Exception e) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_UPDATE_FAIL);
		}
	}
	
	

	
	
	// 매거진을 삭제하는 함수
	public void deleteMegazine(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_INVALID_INPUT);
		}
		
		// 매거진 존재 여부 판단
		Chomp megazine = chompRepository.findById(id)
				.orElseThrow(() -> new ApiException(MegazineErrorCode.MEGAZINE_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (megazine.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(MegazineErrorCode.MEGAZINE_FORBIDDEN);
				}
				if (megazine.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != megazine.getUser().getId()) {
						throw new ApiException(MegazineErrorCode.MEGAZINE_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				throw new ApiException(MegazineErrorCode.MEGAZINE_FORBIDDEN);
			}
		}
		
		// 매거진 삭제
		try {
			chompRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_DELETE_FAIL);
		}
	}
	
	
	
	
	
	// 이벤트의 상세 내용을 조회하는 함수
	public EventReadResponseDto readEventById(Integer id) {
		
		if (id == null) {
			throw new ApiException(EventErrorCode.EVENT_INVALID_INPUT);
		}
		
		Chomp event = chompRepository.findById(id)
				.orElseThrow(() -> new ApiException(EventErrorCode.EVENT_NOT_FOUND));
		
		EventReadResponseDto eventDto = chompMapper.toEventReadResponseDto(event);
		eventDto.setImage(publicPath + "/" + eventDto.getImage());
		
		return eventDto;
		
	}
	
	
	
	
	
	// 이벤트를 작성하는 함수
	public EventCreateResponseDto createEvent(EventCreateRequestDto eventRequestDto, MultipartFile file) {
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!user.getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(MegazineErrorCode.MEGAZINE_FORBIDDEN);
			}
		}
		eventRequestDto.setUserId(userService.readUserByUsername(username).getId());
		
		// 입력값 검증
		if (eventRequestDto == null || eventRequestDto.getTitle() == null
				|| eventRequestDto.getContent() == null || eventRequestDto.getOpendate() == null
				|| eventRequestDto.getClosedate() == null || eventRequestDto.getCategory() == null) {
			throw new ApiException(EventErrorCode.EVENT_INVALID_INPUT);
		}
	    if (eventRequestDto.getOpendate().after(eventRequestDto.getClosedate())) {
	    	throw new ApiException(EventErrorCode.EVENT_INVALID_PERIOD);
	    }
	    if (file == null) {
	    	throw new ApiException(EventErrorCode.EVENT_INVALID_FILE);
	    }
	    
        // 파일 저장
        try {
        	String image = fileService.store(file);
        	eventRequestDto.setImage(image);
        }
        catch (Exception e) {
            throw new ApiException(EventErrorCode.EVENT_FILE_UPLOAD_FAIL);
        }
        
	    // 이벤트 생성
	    Chomp event = chompMapper.toEntity(eventRequestDto);
	    try {
	    	event = chompRepository.save(event);
	    	return chompMapper.toEventCreateResponseDto(event);
	    }
	    catch (Exception e) {
			throw new ApiException(EventErrorCode.EVENT_CREATE_FAIL);
		}
	    
	}
	
	
	
	
	
	// 이벤트를 수정하는 함수
	public EventUpdateResponseDto updateEvent(EventUpdateRequestDto eventRequestDto, MultipartFile file) {
		
		// 입력값 검증
		if (eventRequestDto == null || eventRequestDto.getId() == 0
				|| eventRequestDto.getTitle() == null || eventRequestDto.getContent() == null
				|| eventRequestDto.getOpendate() == null || eventRequestDto.getClosedate() == null) {
			throw new ApiException(EventErrorCode.EVENT_INVALID_INPUT);
		}
	    if (eventRequestDto.getOpendate().after(eventRequestDto.getClosedate())) {
	    	throw new ApiException(EventErrorCode.EVENT_INVALID_PERIOD);
	    }
		
		// 이벤트 존재 여부 확인
		Chomp event;
	    try {
	    	event = chompRepository.findById(eventRequestDto.getId());
	    }
	    catch (Exception e) {
			throw new ApiException(EventErrorCode.EVENT_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (event.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(EventErrorCode.EVENT_FORBIDDEN);
				}
				if (event.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (userService.readUserByUsername(username).getId() != event.getUser().getId()) {
						throw new ApiException(EventErrorCode.EVENT_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				if (userService.readUserByUsername(username).getId() != event.getUser().getId()) {
					throw new ApiException(EventErrorCode.EVENT_FORBIDDEN);
				}
			}
		}
		
		// 변경 값 설정
		event.setTitle(eventRequestDto.getTitle());
		event.setContent(eventRequestDto.getContent());
		event.setOpendate(eventRequestDto.getOpendate());
		event.setClosedate(eventRequestDto.getClosedate());
		
		// 파일 저장
        try {
        	if (file != null && !file.isEmpty()) {
        		String image = fileService.store(file);
        		event.setImage(image);
        	}
        }
        catch (Exception e) {
            throw new ApiException(EventErrorCode.EVENT_FILE_UPLOAD_FAIL);
        }
		
		// 이벤트 수정
		try {
			event = chompRepository.save(event);
			return chompMapper.toEventUpdateResponseDto(event);
		}
		catch (Exception e) {
			throw new ApiException(EventErrorCode.EVENT_UPDATE_FAIL);
		}
	}
	
	
	
	
	
	// 이벤트를 삭제하는 함수
	public void deleteEvent(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(EventErrorCode.EVENT_INVALID_INPUT);
		}
		
		// 이벤트 존재 여부 확인
		Chomp event = chompRepository.findById(id)
				.orElseThrow(() -> new ApiException(EventErrorCode.EVENT_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (event.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(EventErrorCode.EVENT_FORBIDDEN);
				}
				if (event.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (userService.readUserByUsername(username).getId() != event.getUser().getId()) {
						throw new ApiException(EventErrorCode.EVENT_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				throw new ApiException(EventErrorCode.EVENT_FORBIDDEN);
			}
		}
		
		// 이벤트 삭제
		try {
			chompRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(EventErrorCode.EVENT_DELETE_FAIL);
		}
	}
}
