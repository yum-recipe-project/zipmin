package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompVoteChoiceDTO;
import com.project.zipmin.entity.ChompVoteChoice;

@Mapper(componentModel = "spring")
public interface ChompVoteChoiceMapper {
	ChompVoteChoiceDTO chompVoteChoiceToChompVoteChoiceDTO(ChompVoteChoice chompVoteChoice);
	ChompVoteChoice chompVoteChoiceDTOToChompVoteChoice(ChompVoteChoiceDTO chompVoteChoiceDTO);
	List<ChompVoteChoiceDTO> chompVoteChoiceListTChompVoteChoiceDTOList(List<ChompVoteChoice> chompVoteChoiceList);
}
