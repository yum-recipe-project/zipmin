package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.ChompVoteRecordDTO;
import com.project.zipmin.entity.ChompVoteRecord;

@Mapper(componentModel = "spring")
public interface ChompVoteRecordMapper {
	ChompVoteRecordDTO chompVoteRecordToChompVoteRecordDTO(ChompVoteRecord chompVoteRecord);
	ChompVoteRecord chompVoteRecordDTOToChompVoteRecord(ChompVoteRecordDTO chompVoteRecordDTO);
	List<ChompVoteRecordDTO> chompVoteRecordListTChompVoteRecordDTOList(List<ChompVoteRecord> chompVoteRecordList);
}
