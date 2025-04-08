package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompDTO;
import com.project.zipmin.dto.ChompEventDTO;
import com.project.zipmin.dto.ChompMegazineDTO;
import com.project.zipmin.dto.ChompVoteDTO;
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
	public List<ChompDTO> getChompList() {
		Date today = new Date();
		
		List<Chomp> chompList = chompRepository.findAll();
		List<ChompDTO> chompDTOList = new ArrayList<ChompDTO>();
		for (Chomp chomp : chompList) {
			ChompDTO chompDTO = chompMapper.chompToChompDTO(chomp);
			ChompVoteDTO chompVoteDTO = chompVoteMapper.chompVoteToChompVoteDTO(chomp.getChompVote());
			if (chompVoteDTO != null) {
				if (today.after(chompVoteDTO.getOpendate()) && today.before(chompVoteDTO.getClosedate())) {
					chompVoteDTO.setStatus("open");
				}
				else {
					chompVoteDTO.setStatus("close");
				}	
			}
			ChompMegazineDTO chompMegazineDTO = chompMegazineMapper.chompMegazineToChompMegazineDTO(chomp.getChompMegazine());
			ChompEventDTO chompEventDTO = chompEventMapper.chompEventToChompEventDTO(chomp.getChompEvent());
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
	public ChompVoteDTO getVoteById(int id) {
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
	public ChompVoteDTO getVoteByChompId(int chompId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ChompMegazineDTO getMegazineById(int id) {
		ChompMegazine chompMegazine = chompMegazineRepository.findById(id).orElseThrow();
		ChompMegazineDTO chompMegazineDTO = chompMegazineMapper.chompMegazineToChompMegazineDTO(chompMegazine);
		ChompDTO chompDTO = chompMapper.chompToChompDTO(chompMegazine.getChomp());
		chompMegazineDTO.setChompDTO(chompDTO);
		return chompMegazineDTO;
	}
}
