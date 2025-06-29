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
import com.project.zipmin.entity.ChompEvent;
import com.project.zipmin.entity.ChompMegazine;
import com.project.zipmin.entity.ChompVote;
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
public class ChompServiceImpl implements ChompService {
	
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
	@Override
	public Page<ChompReadResponseDto> getChompList(String category, Pageable pageable) {
		
		Page<Chomp> chompPage = "all".equals(category) ? chompRepository.findAll(pageable) : chompRepository.findByCategory(category, pageable);
		List<ChompReadResponseDto> chompDtoList = new ArrayList<ChompReadResponseDto>();
		
		for (Chomp chomp : chompPage) {
			ChompReadResponseDto chompDto = chompMapper.toReadResponseDto(chomp);
			
			// 투표
			VoteReadResponseDto voteDto = voteMapper.toReadResponseDto(chomp.getChompVote());
			if (voteDto != null) {
				voteDto.setStatus(getStatus(voteDto.getOpendate(), voteDto.getClosedate()));
	            chompDto.setVoteDto(voteDto);
			}
			
			// 매거진
			MegazineReadResponseDto megazineDto = megazineMapper.toReadResponseDto(chomp.getChompMegazine());
			chompDto.setMegazineDto(megazineDto);
			
			// 이벤트
			EventReadResponseDto eventDto = eventMapper.toReadResponseDto(chomp.getChompEvent());
			if (eventDto != null) {
				eventDto.setStatus(getStatus(eventDto.getOpendate(), eventDto.getClosedate()));
				chompDto.setEventDto(eventDto);
			}
			
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
	@Override
	public VoteReadResponseDto getVoteById(int id) {
		
		ChompVote chompVote = voteRepository.findById(id)
				.orElseThrow(() -> new ApiException(VoteErrorCode.VOTE_NOT_FOUND));
		
		VoteReadResponseDto voteDto = voteMapper.toReadResponseDto(chompVote);
		
		ChompReadResponseDto chompDto = chompMapper.toReadResponseDto(chompVote.getChomp());
		voteDto.setChompDto(chompDto);
		
		// 여기에 투표 결과 등등 추가
		
		return voteDto;
	}

	
	
	// 매거진의 상세 내용을 조회하는 함수
	@Override
	public MegazineReadResponseDto getMegazineById(int id) {
		
		ChompMegazine chompMegazine = megazineRepository.findById(id)
				.orElseThrow(() -> new ApiException(MegazineErrorCode.MEGAZINE_NOT_FOUND));
		
		MegazineReadResponseDto megazineDto = megazineMapper.toReadResponseDto(chompMegazine);
		
		ChompReadResponseDto chompDto = chompMapper.toReadResponseDto(chompMegazine.getChomp());
		megazineDto.setChompDto(chompDto);
		
		return megazineDto;
	}


	
	// 이벤트의 상세 내용을 조회하는 함수
	@Override
	public EventReadResponseDto getEventById(int id) {
	
		ChompEvent chompEvent = eventRepository.findById(id)
				.orElseThrow(() -> new ApiException(EventErrorCode.EVENT_NOT_FOUND));
		
		EventReadResponseDto eventDto = eventMapper.toReadResponseDto(chompEvent);
		
		ChompReadResponseDto chompDto = chompMapper.toReadResponseDto(chompEvent.getChomp());
		eventDto.setChompDto(chompDto);
		
		return eventDto;
	}
}
