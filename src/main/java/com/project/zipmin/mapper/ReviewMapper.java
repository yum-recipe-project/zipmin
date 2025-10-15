package com.project.zipmin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.project.zipmin.dto.ReviewCreateRequestDto;
import com.project.zipmin.dto.ReviewCreateResponseDto;
import com.project.zipmin.dto.ReviewReadMyResponseDto;
import com.project.zipmin.dto.ReviewReadResponseDto;
import com.project.zipmin.dto.ReviewUpdateRequestDto;
import com.project.zipmin.dto.ReviewUpdateResponseDto;
import com.project.zipmin.entity.Review;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
	
    // Read
	@Mapping(target = "userId", source = "user.id")
    ReviewReadResponseDto toReadResponseDto(Review review);
	
	@Mapping(target = "user.id", source = "userId")
    Review toEntity(ReviewReadResponseDto reviewDto);

    // Create
    @Mapping(target = "recipe.id", source = "recipeId")
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "postdate", ignore = true)
    Review toEntity(ReviewCreateRequestDto reviewDto);

    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "userId", source = "user.id")
    ReviewCreateRequestDto toCreateRequestDto(Review review);

    @Mapping(target = "recipeId", source = "recipe.id")
    @Mapping(target = "userId", source = "user.id")
    ReviewCreateResponseDto toCreateResponseDto(Review review);

    @Mapping(target = "recipe.id", source = "recipeId")
    @Mapping(target = "user.id", source = "userId")
    Review toEntity(ReviewCreateResponseDto reviewDto);
    
    // Update
    Review toEntity(ReviewUpdateRequestDto reviewDto);
    ReviewUpdateRequestDto toUpdateRequestDto(Review review);

    Review toEntity(ReviewUpdateResponseDto reviewDto);
    ReviewUpdateResponseDto toUpdateResponseDto(Review review);

//    
//    
//    @Mapping(target = "commId", source = "comment.id")
//	@Mapping(target = "userId", source = "user.id")
//	CommentReadMyResponseDto toReadMyResponseDto(Comment comment);
    
    @Mapping(source = "recipe.id", target = "recipeId")
    @Mapping(source = "user.id", target = "userId")
    ReviewReadMyResponseDto toReadMyResponseDto(Review review);
}
