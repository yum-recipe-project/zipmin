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
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.dto.MegazineCreateRequestDto;
import com.project.zipmin.dto.MegazineCreateResponseDto;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.MegazineUpdateRequestDto;
import com.project.zipmin.dto.MegazineUpdateResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.VoteChoiceReadResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.dto.VoteRecordCreateRequestDto;
import com.project.zipmin.dto.VoteRecordCreateResponseDto;
import com.project.zipmin.dto.VoteRecordDeleteRequestDto;
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
	    if (megazineRequestDto == null || megazineRequestDto.getChompId() == 0 || megazineRequestDto.getContent() == null || megazineRequestDto.getTitle() == null || megazineRequestDto.getUserId() == 0) {
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
	
	
	
	// 쩝쩝박사의 게시물 목록을 조회하는 함수
	public Page<ChompReadResponseDto> readChompPage(String category, Pageable pageable) {
		
		Page<Chomp> chompPage = "all".equals(category)
			? chompRepository.findAll(pageable)
			: chompRepository.findByCategory(category, pageable);
		
		List<ChompReadResponseDto> chompDtoList = new ArrayList<>();
		
		for (Chomp chomp : chompPage) {
			ChompReadResponseDto chompDto = chompMapper.toReadResponseDto(chomp);
			
			// 투표
			voteRepository.findByChompId(chompDto.getId()).ifPresent(vote -> {
				VoteReadResponseDto voteDto = voteMapper.toReadResponseDto(vote);
				voteDto.setStatus(getStatus(voteDto.getOpendate(), voteDto.getClosedate()));
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
				eventDto.setStatus(getStatus(eventDto.getOpendate(), eventDto.getClosedate()));
				chompDto.setEventDto(eventDto);
			});
			
			chompDtoList.add(chompDto);
		}
		
		return new PageImpl<>(chompDtoList, pageable, chompPage.getTotalElements());
	}
	
	
	
	// 현재 날짜를 기준으로 진행중/진행종료 여부를 판별하는 함수
	private String getStatus(Date opendate, Date closedate) {
		Date today = new Date();
	    return (today.after(opendate) && today.before(closedate)) ? "open" : "close";
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

	
	
	// 매거진의 상세 내용을 조회하는 함수
	public MegazineReadResponseDto readMegazineById(int id) {
		
		Megazine chompMegazine = megazineRepository.findById(id)
				.orElseThrow(() -> new ApiException(MegazineErrorCode.MEGAZINE_NOT_FOUND));
		
		MegazineReadResponseDto megazineDto = megazineMapper.toReadResponseDto(chompMegazine);
		
		return megazineDto;
	}


	
	// 이벤트의 상세 내용을 조회하는 함수
	public EventReadResponseDto readEventById(int id) {
		
		Event chompEvent = eventRepository.findById(id)
				.orElseThrow(() -> new ApiException(EventErrorCode.EVENT_NOT_FOUND));
		
		EventReadResponseDto eventDto = eventMapper.toReadResponseDto(chompEvent);
		
		return eventDto;
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
	
	
	
	
	
	// 투표를 취소하는 함수
	public void deleteVoteRecord(VoteRecordDeleteRequestDto recordDto) {
		
		// 입력값 검증
	    if (recordDto.getUserId() == 0 || recordDto.getVoteId() == 0) {
	    	throw new ApiException(VoteErrorCode.VOTE_RECORD_INVALID_INPUT);
	    }
		
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
	
}
