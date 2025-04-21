package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompResponseDTO;
import com.project.zipmin.dto.ChompEventResponseDTO;
import com.project.zipmin.dto.ChompMegazineResponseDTO;
import com.project.zipmin.dto.ChompVoteResponseDTO;
import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.ChompEvent;
import com.project.zipmin.entity.ChompMegazine;
import com.project.zipmin.entity.ChompVote;
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
	public List<ChompResponseDTO> getChompList() {
		Date today = new Date();
		
		List<Chomp> chompList = chompRepository.findAll();
		List<ChompResponseDTO> chompDTOList = new ArrayList<ChompResponseDTO>();
		for (Chomp chomp : chompList) {
			ChompResponseDTO chompDTO = chompMapper.chompToChompResponseDTO(chomp);
			ChompVoteResponseDTO chompVoteDTO = chompVoteMapper.chompVoteToChompVoteResponseDTO(chomp.getChompVote());
			if (chompVoteDTO != null) {
				if (today.after(chompVoteDTO.getOpendate()) && today.before(chompVoteDTO.getClosedate())) {
					chompVoteDTO.setStatus("open");
				}
				else {
					chompVoteDTO.setStatus("close");
				}	
			}
			ChompMegazineResponseDTO chompMegazineDTO = chompMegazineMapper.chompMegazineToChompMegazineResponseDTO(chomp.getChompMegazine());
			ChompEventResponseDTO chompEventDTO = chompEventMapper.chompEventToChompEventResponseDTO(chomp.getChompEvent());
			if (chompEventDTO != null) {
				if (today.after(chompEventDTO.getOpendate()) && today.before(chompEventDTO.getClosedate())) {
					chompEventDTO.setStatus("open");
				}
				else {
					chompEventDTO.setStatus("close");
				}
			}
			chompDTO.setChompVoteDTO(chompVoteDTO);
			chompDTO.setChompMegazineDTO(chompMegazineDTO);
			chompDTO.setChompEventDTO(chompEventDTO);
			chompDTOList.add(chompDTO);
		}
		return chompDTOList;
	}

	@Override
	public ChompVoteResponseDTO getVoteById(int id) {
		ChompVote chompVote = chompVoteRepository.findById(id).orElseThrow();
		ChompVoteResponseDTO chompVoteDTO = chompVoteMapper.chompVoteToChompVoteResponseDTO(chompVote);
		ChompResponseDTO chompDTO = chompMapper.chompToChompResponseDTO(chompVote.getChomp());
		chompVoteDTO.setChompDTO(chompDTO);
		return chompVoteDTO;
	}
	
	@Override
	public ChompMegazineResponseDTO getMegazineById(int id) {
		ChompMegazine chompMegazine = chompMegazineRepository.findById(id).orElseThrow();
		ChompMegazineResponseDTO chompMegazineDTO = chompMegazineMapper.chompMegazineToChompMegazineResponseDTO(chompMegazine);
		ChompResponseDTO chompDTO = chompMapper.chompToChompResponseDTO(chompMegazine.getChomp());
		chompMegazineDTO.setChompDTO(chompDTO);
		return chompMegazineDTO;
	}

	@Override
	public ChompEventResponseDTO getEventById(int id) {
		ChompEvent chompEvent = chompEventRepository.findById(id).orElseThrow();
		ChompEventResponseDTO chompEventDTO = chompEventMapper.chompEventToChompEventResponseDTO(chompEvent);
		ChompResponseDTO chompDTO = chompMapper.chompToChompResponseDTO(chompEvent.getChomp());
		chompEventDTO.setChompDTO(chompDTO);
		return chompEventDTO;
	}
	
	
}
