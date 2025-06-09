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
	public List<ChompResponseDTO> getChompList() {
//		Date today = new Date();
//		
//		List<Chomp> chompList = chompRepository.findAll();
//		List<ChompResponseDTO> chompDTOList = new ArrayList<ChompResponseDTO>();
//		for (Chomp chomp : chompList) {
//			ChompResponseDTO chompDTO = chompMapper.chompToChompDTO(chomp);
//			ChompVoteDTO chompVoteDTO = chompVoteMapper.chompVoteToChompVoteDTO(chomp.getChompVote());
//			if (chompVoteDTO != null) {
//				if (today.after(chompVoteDTO.getOpendate()) && today.before(chompVoteDTO.getClosedate())) {
//					chompVoteDTO.setStatus("open");
//				}
//				else {
//					chompVoteDTO.setStatus("close");
//				}	
//			}
//			ChompMegazineDTO chompMegazineDTO = chompMegazineMapper.chompMegazineToChompMegazineDTO(chomp.getChompMegazine());
//			ChompEventDTO chompEventDTO = chompEventMapper.chompEventToChompEventDTO(chomp.getChompEvent());
//			if (chompEventDTO != null) {
//				if (today.after(chompEventDTO.getOpendate()) && today.before(chompEventDTO.getClosedate())) {
//					chompEventDTO.setStatus("open");
//				}
//				else {
//					chompEventDTO.setStatus("close");
//				}
//			}
//			chompDTO.setChompVoteDTO(chompVoteDTO);
//			chompDTO.setChompMegazineDTO(chompMegazineDTO);
//			chompDTO.setChompEventDTO(chompEventDTO);
//			chompDTOList.add(chompDTO);
//		}
//		return chompDTOList;
		
		return null;
	}
	
	
	

	@Override
	public Page<ChompResponseDTO> getChompListByCategoryAndStatus(String category, String status, Pageable pageable) {
		
		Page<Chomp> chompPage = chompRepository.findByCategoryAndStatus(category, status, pageable);
		
		List<ChompResponseDTO> chompDtoList = new ArrayList<ChompResponseDTO>();
		for (Chomp chomp : chompPage) {
			chompDtoList.add(chompMapper.toResponseDto(chomp));
		}
		
		return new PageImpl<>(chompDtoList, pageable, chompPage.getTotalElements());
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
		ChompResponseDTO chompDTO = chompMapper.toResponseDto(chompMegazine.getChomp());
		chompMegazineDTO.setChompDTO(chompDTO);
		return chompMegazineDTO;
	}
}
