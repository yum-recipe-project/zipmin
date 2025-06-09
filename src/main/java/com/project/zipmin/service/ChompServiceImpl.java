package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompResponseDTO;
import com.project.zipmin.dto.EventResponseDTO;
import com.project.zipmin.dto.MegazineResponseDTO;
import com.project.zipmin.dto.VoteResponseDTO;
import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.ChompMegazine;
import com.project.zipmin.mapper.ChompEventMapper;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.mapper.ChompMegazineMapper;
import com.project.zipmin.mapper.ChompVoteMapper;
import com.project.zipmin.repository.ChompEventRepository;
import com.project.zipmin.repository.ChompMegazineRepository;
import com.project.zipmin.repository.ChompRepository;
import com.project.zipmin.repository.ChompVoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChompServiceImpl implements ChompService {
	
	@Autowired
	private ChompRepository chompRepository;
	@Autowired
	private ChompVoteRepository chompVoteRepository;
	@Autowired
	private ChompMegazineRepository chompMegazineRepository;
	@Autowired
	private ChompEventRepository chompEventRepository;
	
	
	private final ChompMapper chompMapper;
	private final ChompVoteMapper chompVoteMapper;
	private final ChompMegazineMapper chompMegazineMapper;
	private final ChompEventMapper chompEventMapper;
	
	
	
	@Override
	public Page<ChompResponseDTO> getChompList(String category, Pageable pageable) {
		
		Page<Chomp> chompPage;
		
		if ("all".equals(category)) {
			chompPage = chompRepository.findAll(pageable);
		}
		else {
			chompPage = chompRepository.findByCategory(category, pageable);
		}
		
		Date today = new Date();
		List<ChompResponseDTO> chompDtoList = new ArrayList<ChompResponseDTO>();
		for (Chomp chomp : chompPage) {
			ChompResponseDTO chompDTO = chompMapper.toResponseDto(chomp);
			// 투표
			VoteResponseDTO voteDto = chompVoteMapper.chompVoteToChompVoteDTO(chomp.getChompVote());
			if (voteDto != null) {
				if (today.after(voteDto.getOpendate()) && today.before(voteDto.getClosedate())) {
					voteDto.setStatus("open");
				}
				else {
					voteDto.setStatus("close");
				}	
			}
			// 매거진
			MegazineResponseDTO megazineDto = chompMegazineMapper.chompMegazineToChompMegazineDTO(chomp.getChompMegazine());
			// 이벤트
			EventResponseDTO eventDto = chompEventMapper.chompEventToChompEventDTO(chomp.getChompEvent());
			if (eventDto != null) {
				if (today.after(eventDto.getOpendate()) && today.before(eventDto.getClosedate())) {
					eventDto.setStatus("open");
				}
				else {
					eventDto.setStatus("close");
				}
			}
			
			chompDTO.setChompVoteDTO(voteDto);
			chompDTO.setChompMegazineDTO(megazineDto);
			chompDTO.setChompEventDTO(eventDto);
			chompDtoList.add(chompDTO);
		}
		
		return new PageImpl<>(chompDtoList, pageable, chompPage.getTotalElements());
	}



	@Override
	public VoteResponseDTO getVoteById(int id) {
//		ChompVote chompVote = chompVoteRepository.findById(id).orElseThrow();
//		System.err.println(chompVote);
//		if (chompVote.isPresent()) {
//	        return chompVoteMapper.chompVoteToChompVoteDTO(chompVote.get());
//	    }
//		else {
//	        return null;
//	    }
		return null;
	}

	@Override
	public VoteResponseDTO getVoteByChompId(int chompId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public MegazineResponseDTO getMegazineById(int id) {
		ChompMegazine chompMegazine = chompMegazineRepository.findById(id).orElseThrow();
		MegazineResponseDTO chompMegazineDTO = chompMegazineMapper.chompMegazineToChompMegazineDTO(chompMegazine);
		ChompResponseDTO chompDTO = chompMapper.toResponseDto(chompMegazine.getChomp());
		chompMegazineDTO.setChompDTO(chompDTO);
		return chompMegazineDTO;
	}
}
