package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompVoteResponseDTO;
import com.project.zipmin.entity.ChompVote;

@Mapper(componentModel = "spring")
public interface ChompVoteMapper {
	ChompVoteResponseDTO chompVoteToChompVoteResponseDTO(ChompVote chompVote);
	ChompVote chompVoteResponseDTOToChompVote(ChompVoteResponseDTO chompVoteDTO);
	List<ChompVoteResponseDTO> chompVoteListToChompVoteResponseDTOList(List<ChompVote> chompVoteList);
}
