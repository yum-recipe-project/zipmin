package com.project.zipmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompDTO;
import com.project.zipmin.entity.Chomp;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.repository.ChompRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChompServiceImpl implements ChompService {
	
	@Autowired
	private ChompRepository chompRepository;
	
	private final ChompMapper chompMapper;

	@Override
	public List<ChompDTO> getChompList() {
		List<Chomp> chompList = chompRepository.findAll();
		return chompMapper.chompListToChompDTOList(chompList);
	}
	
	
}
