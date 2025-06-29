package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.EventErrorCode;
import com.project.zipmin.api.MegazineErrorCode;
import com.project.zipmin.api.VoteErrorCode;
import com.project.zipmin.dto.ChompReadResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.Event;
import com.project.zipmin.entity.Megazine;
import com.project.zipmin.entity.Vote;
import com.project.zipmin.mapper.EventMapper;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.mapper.MegazineMapper;
import com.project.zipmin.mapper.VoteMapper;
import com.project.zipmin.repository.EventRepository;
import com.project.zipmin.repository.MegazineRepository;
import com.project.zipmin.repository.ChompRepository;
import com.project.zipmin.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChompService {
	
	@Autowired
	private ChompRepository chompRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private MegazineRepository megazineRepository;
	@Autowired
	private EventRepository eventRepository;
	
	
	private final ChompMapper chompMapper;
	private final VoteMapper voteMapper;
	private final MegazineMapper megazineMapper;
	private final EventMapper eventMapper;
	
	
	
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
		
		Vote chompVote = voteRepository.findById(id)
				.orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
		
		VoteReadResponseDto voteDto = voteMapper.toReadResponseDto(chompVote);
		
		// 여기에 투표 결과 등등 추가
		
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
}
