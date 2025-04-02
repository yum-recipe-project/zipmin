package com.project.zipmin.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.ChompVoteDTO;
import com.project.zipmin.entity.ChompVote;
import com.project.zipmin.mapper.ChompVoteMapper;
import com.project.zipmin.repository.ChompVoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChompVoteServiceImpl implements ChompVoteService {
	
	@Autowired
	private ChompVoteRepository chompVoteRepository;
	
	private final ChompVoteMapper chompVoteMapper;

	@Override
	public ChompVoteDTO getVoteById(int id) {
		Optional<ChompVote> chompVote = chompVoteRepository.findById(id);
		System.err.println(chompVote);
		if (chompVote.isPresent()) {
	        return chompVoteMapper.chompVoteToChompVoteDTO(chompVote.get());
	    }
		else {
	        return null;
	    }
	}

	@Override
	public ChompVoteDTO getVoteByChompId(int chompId) {
		// TODO Auto-generated method stub
		return null;
	}
}
