package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.ChompErrorCode;
import com.project.zipmin.api.EventErrorCode;
import com.project.zipmin.api.MegazineErrorCode;
import com.project.zipmin.api.VoteErrorCode;
import com.project.zipmin.dto.ChompCreateRequestDto;
import com.project.zipmin.dto.ChompCreateResponseDto;
import com.project.zipmin.dto.ChompReadResponseDto;
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
import com.project.zipmin.dto.VoteChoiceCreateRequestDto;
import com.project.zipmin.dto.VoteChoiceReadResponseDto;
import com.project.zipmin.dto.VoteCreateRequestDto;
import com.project.zipmin.dto.VoteCreateResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.dto.VoteRecordCreateRequestDto;
import com.project.zipmin.dto.VoteRecordCreateResponseDto;
import com.project.zipmin.dto.VoteRecordDeleteRequestDto;
import com.project.zipmin.dto.VoteUpdateRequestDto;
import com.project.zipmin.dto.VoteUpdateResponseDto;
import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.Event;
import com.project.zipmin.entity.Megazine;
import com.project.zipmin.entity.Vote;
import com.project.zipmin.entity.VoteChoice;
import com.project.zipmin.entity.VoteRecord;
import com.project.zipmin.mapper.EventMapper;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.mapper.MegazineMapper;
import com.project.zipmin.mapper.VoteChoiceMapper;
import com.project.zipmin.mapper.VoteMapper;
import com.project.zipmin.mapper.VoteRecordMapper;
import com.project.zipmin.repository.EventRepository;
import com.project.zipmin.repository.MegazineRepository;
import com.project.zipmin.repository.VoteChoiceRepository;
import com.project.zipmin.repository.VoteRecordRepository;
import com.project.zipmin.repository.ChompRepository;
import com.project.zipmin.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChompService {
	
	@Autowired
	private ChompRepository chompRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private VoteChoiceRepository choiceRepository;
	@Autowired
	private VoteRecordRepository recordRepository;
	@Autowired
	private MegazineRepository megazineRepository;
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserService userService;
	
	
	private final ChompMapper chompMapper;
	private final VoteMapper voteMapper;
	private final VoteChoiceMapper choiceMapper;
	private final VoteRecordMapper recordMapper;
	private final MegazineMapper megazineMapper;
	private final EventMapper eventMapper;
	
	
	
	// 쩝쩝박사의 게시물 목록을 조회하는 함수
	public Page<ChompReadResponseDto> readChompPage(String category, Pageable pageable) {
		
		Page<Chomp> chompPage = "all".equals(category)
			? chompRepository.findAll(pageable)
			: chompRepository.findByCategory(category, pageable);
		
		List<ChompReadResponseDto> chompDtoList = new ArrayList<>();
		
		Date today = new Date();
		for (Chomp chomp : chompPage) {
			ChompReadResponseDto chompDto = chompMapper.toReadResponseDto(chomp);
			
			// 투표
			voteRepository.findByChompId(chompDto.getId()).ifPresent(vote -> {
				VoteReadResponseDto voteDto = voteMapper.toReadResponseDto(vote);
				String status = (today.after(voteDto.getOpendate()) && today.before(voteDto.getClosedate())) ? "open" : "close";
				voteDto.setStatus(status);
				chompDto.setVoteDto(voteDto);
			});
			
			// 매거진
			megazineRepository.findByChompId(chompDto.getId()).ifPresent(megazine -> {
				MegazineReadResponseDto megazineDto = megazineMapper.toReadResponseDto(megazine);
				chompDto.setMegazineDto(megazineDto);
			});
			
			// 이벤트
			eventRepository.findByChompId(chomp.getId()).ifPresent(event -> {
				EventReadResponseDto eventDto = eventMapper.toReadResponseDto(event);
				String status = (today.after(eventDto.getOpendate()) && today.before(eventDto.getClosedate())) ? "open" : "close";
				eventDto.setStatus(status);
				chompDto.setEventDto(eventDto);
			});
			
			chompDtoList.add(chompDto);
		}
		
		return new PageImpl<>(chompDtoList, pageable, chompPage.getTotalElements());
	}

	
	
	// 투표의 상세 내용을 조회하는 함수
	public VoteReadResponseDto readVoteById(int id) {
		
		Vote vote = voteRepository.findById(id)
				.orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
		
		VoteReadResponseDto voteDto = voteMapper.toReadResponseDto(vote);
		long total = recordRepository.countByVoteId(id);
		voteDto.setTotal(total);
		
		List<VoteChoice> choiceList = choiceRepository.findByVoteId(id);
		List<VoteChoiceReadResponseDto> choiceDtoList = new ArrayList<>();
		for (VoteChoice choice : choiceList) {
			VoteChoiceReadResponseDto choiceDto = choiceMapper.toReadResponseDto(choice);
			
			long count = recordRepository.countByChoiceId(choice.getId());
			double rate = (total == 0) ? 0.0 : Math.round(((double) count / total) * 100) / 1.0;
			
			choiceDto.setCount((int) count);
			choiceDto.setRate(rate);
			
			choiceDtoList.add(choiceDto);
		}
		voteDto.setChoiceList(choiceDtoList);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
			String username = auth.getName();
			UserReadResponseDto userDto = userService.readUserByUsername(username);
			
			Optional<VoteRecord> record = recordRepository.findByUserIdAndVoteId(userDto.getId(), voteDto.getId());
			
			if (record.isPresent()) {
				voteDto.setVoted(true);
				voteDto.setChoiceId(record.get().getChoice().getId());
			}
			else {
				voteDto.setVoted(false);
			}
		}
		
		return voteDto;
	}
	
	
	
	// 투표를 작성하는 함수
	public VoteCreateResponseDto createVote(VoteCreateRequestDto voteRequestDto) {
		
		ChompCreateRequestDto chompRequestDto = new ChompCreateRequestDto();
		chompRequestDto.setCategory("vote");
		
		// 쩝쩝박사 생성
		Chomp chomp = chompMapper.toEntity(chompRequestDto);
		ChompCreateResponseDto chompResponseDto = new ChompCreateResponseDto();
		try {
			chomp = chompRepository.save(chomp);
			chompResponseDto = chompMapper.toCreateResponseDto(chomp);
		}
		catch (Exception e) {
			throw new ApiException(ChompErrorCode.CHOMP_CREATE_FAIL);
		}

		// 입력값 검증
	    if (voteRequestDto == null || voteRequestDto.getTitle() == null || voteRequestDto.getOpendate() == null || voteRequestDto.getClosedate() == null || voteRequestDto.getChoices() == null) {
	    	throw new ApiException(VoteErrorCode.VOTE_INVALID_INPUT);
	    }
	    for (VoteChoiceCreateRequestDto choiceDto : voteRequestDto.getChoices()) {
	    	if (choiceDto.getChoice() == null) {
	    		throw new ApiException(VoteErrorCode.VOTE_CHOICE_INVALID_INPUT);
	    	}
	    }
	    
	    // 기간 검증
	    if (voteRequestDto.getOpendate().after(voteRequestDto.getClosedate())) {
	    	throw new ApiException(VoteErrorCode.VOTE_INVALID_PERIOD);
	    }
		
	    // 투표 생성
	    voteRequestDto.setChompId(chompResponseDto.getId());
	    Vote vote = voteMapper.toEntity(voteRequestDto);
	    try {
	    	vote = voteRepository.save(vote);
    		for (VoteChoiceCreateRequestDto choiceDto : voteRequestDto.getChoices()) {
    			choiceDto.setVoteId(vote.getId());
    			VoteChoice choice = choiceMapper.toEntity(choiceDto);
    			
    			try {
    				choiceRepository.save(choice);
		    	}
		    	catch (Exception e) {
					throw new ApiException(VoteErrorCode.VOTE_CHOICE_CREATE_FAIL);
		    	}
    		}
	    	return voteMapper.toCreateResponseDto(vote);
	    }
	    catch (Exception e) {
	    	throw new ApiException(VoteErrorCode.VOTE_CREATE_FAIL);
		}
	}
	
	
	
	// 투표를 수정하는 함수 (*********** 수정 필요 ************)
	public VoteUpdateResponseDto updateVote(VoteUpdateRequestDto voteRequestDto) {
		
		// 입력값 검증
		
		
		
		// 투표 존재 여부 판단
		Vote vote = voteRepository.findById(voteRequestDto.getId())
				.orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
		
		// 필요한 필드만 수정
		
		
		
		// 투표 수정
		
		
		
		return null;
	}
	
	
	
	// 투표를 삭제하는 함수
	public void deleteVote(int id) {
		
		if (id < 0) {
			throw new ApiException(VoteErrorCode.VOTE_INVALID_INPUT);
		}
		
		// 투표 존재 여부 판단
		Vote vote = voteRepository.findById(id)
				.orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
		
		// 투표 옵션 삭제
		try {
			choiceRepository.deleteAllByVoteId(id);
		}
		catch (Exception e) {
			throw new ApiException(VoteErrorCode.VOTE_CHOICE_DELETE_FAIL);
		}
		
		// 투표 삭제
		try {
			voteRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(VoteErrorCode.VOTE_DELETE_FAIL);
		}
		
		// 쩝쩝박사 게시물 삭제
		try {
			chompRepository.deleteById(vote.getChomp().getId());
		}
		catch (Exception e) {
			throw new ApiException(ChompErrorCode.CHOMP_DELETE_FAIL);
		}
	}
	
	
	
	// 투표하는 함수
	public VoteRecordCreateResponseDto createVoteRecord(VoteRecordCreateRequestDto recordDto) {
		
		// 입력값 검증
	    if (recordDto == null || recordDto.getUserId() == 0 || recordDto.getVoteId() == 0 || recordDto.getChoiceId() == 0) {
	    	throw new ApiException(VoteErrorCode.VOTE_RECORD_INVALID_INPUT);
	    }
	    
	    // 중복 투표 검사
	    if (recordRepository.existsByUserIdAndVoteId(recordDto.getUserId(), recordDto.getVoteId())) {
	    	throw new ApiException(VoteErrorCode.VOTE_RECORD_DUPLICATE);
	    }
	    
	    // 투표 기간 검사
	    Vote vote = voteRepository.findById(recordDto.getVoteId())
	    	    .orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
	    Date now = new Date();
	    if (now.before(vote.getOpendate())) {
	        throw new ApiException(VoteErrorCode.VOTE_NOT_STARTED);
	    }
	    if (now.after(vote.getClosedate())) {
	        throw new ApiException(VoteErrorCode.VOTE_ALREADY_ENDED);
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
	
	
	
	// 투표를 취소하는 함수
	public void deleteVoteRecord(VoteRecordDeleteRequestDto recordDto) {
		
		// 입력값 검증
	    if (recordDto.getUserId() == 0 || recordDto.getVoteId() == 0) {
	    	throw new ApiException(VoteErrorCode.VOTE_RECORD_INVALID_INPUT);
	    }
		
	    // 투표 기록 존재 여부 판단
		if (!recordRepository.existsByUserIdAndVoteId(recordDto.getUserId(), recordDto.getVoteId())) {
	        throw new ApiException(VoteErrorCode.VOTE_RECORD_NOT_FOUND);
	    }
		
	    // 투표 기간 검사
	    Vote vote = voteRepository.findById(recordDto.getVoteId())
	    	    .orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
	    Date now = new Date();
	    if (now.before(vote.getOpendate())) {
	        throw new ApiException(VoteErrorCode.VOTE_NOT_STARTED);
	    }
	    if (now.after(vote.getClosedate())) {
	        throw new ApiException(VoteErrorCode.VOTE_ALREADY_ENDED);
	    }
		
		// 투표 기록 삭제
		try {
	        recordRepository.deleteByUserIdAndVoteId(recordDto.getUserId(), recordDto.getVoteId());
	    }
		catch (Exception e) {
	        throw new ApiException(VoteErrorCode.VOTE_RECORD_DELETE_FAIL);
	    }
	}
	
	
	
	// 매거진의 상세 내용을 조회하는 함수
	public MegazineReadResponseDto readMegazineById(int id) {
		
		Megazine chompMegazine = megazineRepository.findById(id)
				.orElseThrow(() -> new ApiException(MegazineErrorCode.MEGAZINE_NOT_FOUND));
		
		return megazineMapper.toReadResponseDto(chompMegazine);
		
	}
	
	
	
	// 매거진을 작성하는 함수
	public MegazineCreateResponseDto createMegazine(MegazineCreateRequestDto megazineRequestDto) {
		
		ChompCreateRequestDto chompRequestDto = new ChompCreateRequestDto();
		chompRequestDto.setCategory("megazine");
		
		// 쩝쩝박사 생성
		Chomp chomp = chompMapper.toEntity(chompRequestDto);
		ChompCreateResponseDto chompResponseDto = new ChompCreateResponseDto();
		try {
			chomp = chompRepository.save(chomp);
			chompResponseDto = chompMapper.toCreateResponseDto(chomp);
		}
		catch (Exception e) {
			throw new ApiException(ChompErrorCode.CHOMP_CREATE_FAIL);
		}
		
		// 입력값 검증
	    if (megazineRequestDto == null || megazineRequestDto.getTitle() == null || megazineRequestDto.getContent() == null) {
	    	throw new ApiException(MegazineErrorCode.MEGAZINE_INVALID_INPUT);
	    }
		
	    // 매거진 생성
		megazineRequestDto.setChompId(chompResponseDto.getId());
		Megazine megazine = megazineMapper.toEntity(megazineRequestDto);
		try {
			megazine = megazineRepository.save(megazine);
			return megazineMapper.toCreateResponseDto(megazine);
		}
		catch (Exception e) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_CREATE_FAIL);
		}
	}
	
	
	
	// 매거진을 수정하는 함수
	public MegazineUpdateResponseDto updateMegazine(MegazineUpdateRequestDto megazineRequestDto) {
		
		// 입력값 검증
		if (megazineRequestDto == null || megazineRequestDto.getId() == 0 || megazineRequestDto.getContent() == null || megazineRequestDto.getTitle() == null) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_INVALID_INPUT);
		}
		
		// 매거진 존재 여부 판단
		Megazine megazine = megazineRepository.findById(megazineRequestDto.getId())
				.orElseThrow(() -> new ApiException(MegazineErrorCode.MEGAZINE_NOT_FOUND));
		
		// 필요한 필드만 수정
		megazine.setTitle(megazineRequestDto.getTitle());
		megazine.setContent(megazineRequestDto.getContent());
		
		// 매거진 수정
		try {
			megazine = megazineRepository.save(megazine);
			return megazineMapper.toUpdateResponseDto(megazine);
		}
		catch (Exception e) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_UPDATE_FAIL);
		}
	}
	
	
	
	// 매거진을 삭제하는 함수
	public void deleteMegazine(int id) {
		
		// 입력값 검증
		if (id < 0) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_INVALID_INPUT);
		}
		
		// 매거진 존재 여부 판단
		Megazine megazine = megazineRepository.findById(id)
				.orElseThrow(() -> new ApiException(MegazineErrorCode.MEGAZINE_NOT_FOUND));
		
		// 매거진 삭제
		try {
			megazineRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(MegazineErrorCode.MEGAZINE_DELETE_FAIL);
		}
		
		// 쩝쩝박사 게시물 삭제
		try {
			chompRepository.deleteById(megazine.getChomp().getId());
		}
		catch (Exception e) {
			throw new ApiException(ChompErrorCode.CHOMP_DELETE_FAIL);
		}
	}
	
	
	
	// 이벤트의 상세 내용을 조회하는 함수
	public EventReadResponseDto readEventById(int id) {
		
		Event chompEvent = eventRepository.findById(id)
				.orElseThrow(() -> new ApiException(EventErrorCode.EVENT_NOT_FOUND));
		
		return eventMapper.toReadResponseDto(chompEvent);
		
	}
	
	
	
	// 이벤트를 작성하는 함수
	public EventCreateResponseDto createEvent(EventCreateRequestDto eventRequestDto) {
		
		ChompCreateRequestDto chompRequestDto = new ChompCreateRequestDto();
		chompRequestDto.setCategory("event");
		
		// 쩝쩝박사 생성
		Chomp chomp = chompMapper.toEntity(chompRequestDto);
		ChompCreateResponseDto chompResponseDto = new ChompCreateResponseDto();
		try {
			chomp = chompRepository.save(chomp);
			chompResponseDto = chompMapper.toCreateResponseDto(chomp);
		}
		catch (Exception e) {
			throw new ApiException(ChompErrorCode.CHOMP_CREATE_FAIL);
		}
		
		// 입력값 검증
		if (eventRequestDto == null || eventRequestDto.getTitle() == null || eventRequestDto.getContent() == null || eventRequestDto.getOpendate() == null || eventRequestDto.getClosedate() == null) {
			throw new ApiException(EventErrorCode.EVENT_INVALID_INPUT);
		}
		
	    // 기간 검증
	    if (eventRequestDto.getOpendate().after(eventRequestDto.getClosedate())) {
	    	throw new ApiException(EventErrorCode.EVENT_INVALID_PERIOD);
	    }
	    
	    // 이벤트 생성
	    eventRequestDto.setChompId(chompResponseDto.getId());
	    Event event = eventMapper.toEntity(eventRequestDto);
	    try {
	    	event = eventRepository.save(event);
	    	return eventMapper.toCreateResponseDto(event);
	    }
	    catch (Exception e) {
			throw new ApiException(EventErrorCode.EVENT_CREATE_FAIL);
		}
	}
	
	
	
	// 이벤트를 수정하는 함수
	public EventUpdateResponseDto updateEvent(EventUpdateRequestDto eventRequestDto) {
		
		// 입력값 검증
		if (eventRequestDto == null || eventRequestDto.getId() == 0 || eventRequestDto.getTitle() == null || eventRequestDto.getContent() == null || eventRequestDto.getOpendate() == null || eventRequestDto.getClosedate() == null) {
			throw new ApiException(EventErrorCode.EVENT_INVALID_INPUT);
		}
		
		// 이벤트 존재 여부 판단
		Event event = eventRepository.findById(eventRequestDto.getId())
				.orElseThrow(() -> new ApiException(EventErrorCode.EVENT_NOT_FOUND));
		
	    // 기간 검증
	    if (eventRequestDto.getOpendate().after(eventRequestDto.getClosedate())) {
	    	throw new ApiException(EventErrorCode.EVENT_INVALID_PERIOD);
	    }
		
		// 필요한 필드만 수정
		event.setTitle(eventRequestDto.getTitle());
		event.setContent(eventRequestDto.getContent());
		event.setOpendate(eventRequestDto.getOpendate());
		event.setClosedate(eventRequestDto.getClosedate());
		
		// 이벤트 수정
		try {
			event = eventRepository.save(event);
			return eventMapper.toUpdateResponseDto(event);
		}
		catch (Exception e) {
			throw new ApiException(EventErrorCode.EVENT_UPDATE_FAIL);
		}
	}
	
	
	
	// 이벤트를 삭제하는 함수
	public void deleteEvent(int id) {
		
		// 입력값 검증
		if (id < 0) {
			throw new ApiException(EventErrorCode.EVENT_INVALID_INPUT);
		}
		
		// 이벤트 존재 여부 판단
		Event event = eventRepository.findById(id)
				.orElseThrow(() -> new ApiException(EventErrorCode.EVENT_NOT_FOUND));
		
		// 이벤트 삭제
		try {
			megazineRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(EventErrorCode.EVENT_DELETE_FAIL);
		}
		
		// 쩝쩝박사 게시물 삭제
		try {
			chompRepository.deleteById(event.getChomp().getId());
		}
		catch (Exception e) {
			throw new ApiException(ChompErrorCode.CHOMP_DELETE_FAIL);
		}
	}
}
