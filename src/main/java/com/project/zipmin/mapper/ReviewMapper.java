package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ReviewReadResponseDto;
import com.project.zipmin.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	
    // Read
    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "userId", source = "user.id")
    ReviewReadResponseDto toReadResponseDto(Review review);
	
    @Mapping(target = "recipe.id", source = "recipeId")
    @Mapping(target = "user.id", source = "userId")
    Review toEntity(ReviewReadResponseDto reviewDto);

    
	
//	// Create
//    @Mapping(target = "recipe.id", source = "recipeId")
//    @Mapping(target = "user.id", source = "userId")
//    Review toEntity(ReviewCreateRequestDto reviewDto);
//
//    @Mapping(target = "reviewId", source = "id")
//    @Mapping(target = "userId", source = "user.id")
//    ReviewCreateRequestDto toCreateRequestDto(Review review);
//
//    @Mapping(target = "reviewId", source = "id")
//    @Mapping(target = "userId", source = "user.id")
//    ReviewCreateResponseDto toCreateResponseDto(Review review);
//
//    @Mapping(target = "recipe.id", source = "recipeId")
//    @Mapping(target = "user.id", source = "userId")
//    Review toEntity(ReviewCreateResponseDto reviewDto);
//
//
//
//    // Update
//    Review toEntity(ReviewUpdateRequestDto reviewDto);
//    ReviewUpdateRequestDto toUpdateRequestDto(Review review);
//
//    Review toEntity(ReviewUpdateResponseDto reviewDto);
//    ReviewUpdateResponseDto toUpdateResponseDto(Review review);
	
}
