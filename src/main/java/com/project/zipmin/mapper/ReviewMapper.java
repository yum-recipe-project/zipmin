package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.review.ReviewCreateRequestDto;
import com.project.zipmin.dto.review.ReviewCreateResponseDto;
import com.project.zipmin.dto.review.ReviewReadResponseDto;
import com.project.zipmin.dto.review.ReviewUpdateRequestDto;
import com.project.zipmin.dto.review.ReviewUpdateResponseDto;
import com.project.zipmin.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

	// Read
	@Mapping(target = "user.id", source = "userId")
	Review toEntity(ReviewReadResponseDto reviewDto);

	@Mapping(target = "userId", source = "user.id")
	ReviewReadResponseDto toReadResponseDto(Review review);



	// Create
	@Mapping(target = "user.id", source = "userId")
	Review toEntity(ReviewCreateRequestDto reviewDto);

	@Mapping(target = "userId", source = "user.id")
	ReviewCreateRequestDto toCreateRequestDto(Review review);

	@Mapping(target = "userId", source = "user.id")
	ReviewCreateResponseDto toCreateResponseDto(Review review);

	@Mapping(target = "user.id", source = "userId")
	Review toEntity(ReviewCreateResponseDto reviewDto);



	// Update
	Review toEntity(ReviewUpdateRequestDto reviewDto);
	ReviewUpdateRequestDto toUpdateRequestDto(Review review);

	Review toEntity(ReviewUpdateResponseDto reviewDto);
	ReviewUpdateResponseDto toUpdateResponseDto(Review review);

}
