package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.VoteRecordReadResponseDto;
import com.project.zipmin.entity.VoteRecord;

@Mapper(componentModel = "spring")
public interface VoteRecordMapper {
	VoteRecordReadResponseDto chompVoteRecordToChompVoteRecordDTO(VoteRecord chompVoteRecord);
	VoteRecord chompVoteRecordDTOToChompVoteRecord(VoteRecordReadResponseDto chompVoteRecordDTO);
	List<VoteRecordReadResponseDto> chompVoteRecordListTChompVoteRecordDTOList(List<VoteRecord> chompVoteRecordList);
}
