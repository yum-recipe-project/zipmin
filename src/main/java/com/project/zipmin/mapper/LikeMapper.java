package com.project.zipmin.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.project.zipmin.dto.LikeDTO;
import com.project.zipmin.entity.Like;

@Mapper(componentModel = "spring")
public interface LikeMapper {
	LikeDTO likeToLikeDTO(Like like);
	Like likeDTOToLike(LikeDTO likeDTO);
	List<LikeDTO> likeListToLikeDTOList(List<Like> likeList);
}
