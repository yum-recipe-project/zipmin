package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.GuideDTO;
import com.project.zipmin.entity.Guide;

@Mapper(componentModel = "spring")
public interface GuideMapper {
	GuideDTO guideToGuideDTO(Guide guide);
	Guide guideDTOToGuide(GuideDTO guideDTO);
	List<GuideDTO> guideListToGuideDTOList(List<Guide> guideList);
}
