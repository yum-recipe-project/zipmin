package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompDTO;
import com.project.zipmin.dto.ChompMegazineDTO;
import com.project.zipmin.dto.ChompVoteDTO;
import com.project.zipmin.entity.Chomp;
import com.project.zipmin.entity.ChompMegazine;
import com.project.zipmin.entity.ChompVote;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.mapper.ChompMegazineMapper;
import com.project.zipmin.mapper.ChompVoteMapper;
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
	
	
	private final ChompMapper chompMapper;
	private final ChompVoteMapper chompVoteMapper;
	private final ChompMegazineMapper chompMegazineMapper;

	@Override
	public List<ChompDTO> getChompList() {
		List<Chomp> chompList = chompRepository.findAll();
		List<ChompDTO> chompDTOList = new ArrayList<ChompDTO>();
		for (Chomp chomp : chompList) {
			ChompDTO chompDTO = chompMapper.chompToChompDTO(chomp);
			ChompMegazineDTO chompMegazineDTO = chompMegazineMapper.chompMegazineToChompMegazineDTO(chomp.getChompMegazine());
			chompDTO.setChompMegazineDTO(chompMegazineDTO);
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
