package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompVoteDTO;
import com.project.zipmin.entity.ChompVote;

@Mapper(componentModel = "spring")
public interface ChompVoteMapper {
	ChompVoteDTO chompVoteToChompVoteDTO(ChompVote chompVote);
	ChompVote chompVoteDTOToChompVote(ChompVoteDTO chompVoteDTO);
	List<ChompVoteDTO> chompVoteListToChompVoteDTOList(List<ChompVote> chompVoteList);
}
