package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.VoteResponseDTO;
import com.project.zipmin.entity.ChompVote;

@Mapper(componentModel = "spring")
public interface ChompVoteMapper {
	VoteResponseDTO chompVoteToChompVoteDTO(ChompVote chompVote);
	ChompVote chompVoteDTOToChompVote(VoteResponseDTO chompVoteDTO);
	List<VoteResponseDTO> chompVoteListToChompVoteDTOList(List<ChompVote> chompVoteList);
}
