package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.KitchenErrorCode;
import com.project.zipmin.dto.UserCommentReadesponseDto;
import com.project.zipmin.dto.GuideCreateRequestDto;
import com.project.zipmin.dto.GuideCreateResponseDto;
import com.project.zipmin.dto.GuideReadMySavedResponseDto;
import com.project.zipmin.dto.GuideReadResponseDto;
import com.project.zipmin.dto.GuideUpdateRequestDto;
import com.project.zipmin.dto.GuideUpdateResponseDto;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.dto.LikeReadResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.entity.Comment;
import com.project.zipmin.entity.Guide;
import com.project.zipmin.entity.Like;
import com.project.zipmin.entity.Role;
import com.project.zipmin.mapper.GuideMapper;
import com.project.zipmin.repository.KitchenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KitchenService {
	
	@Autowired
	private final KitchenRepository kitchenRepository;
	
	@Autowired
	private UserService userService;
	@Autowired
	private LikeService likeService;
	
	@Autowired
	private final GuideMapper guideMapper;
	
	// 가이드 목록 조회
	public Page<GuideReadResponseDto> readGuidePage(String category, String keyword, String sort, Pageable pageable) {
		
		System.err.println("sort: " + sort);
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 정렬기준 문자열 객체로 변환
		Sort sortSpec = Sort.by(Sort.Order.desc("id"));
		
		if(sort != null && !sort.isBlank()) {
			switch (sort) {
			case "id-desc": {
				sortSpec = Sort.by(Sort.Order.desc("id"));
				break;
			}
			case "likecount-desc": {
				sortSpec = Sort.by(Sort.Order.desc("likecount"), Sort.Order.desc("id"));
				break;
			}
			case "postdate-desc": {  
	            sortSpec = Sort.by(Sort.Order.desc("postdate"), Sort.Order.desc("id"));
	            break;
	        }
	        case "postdate-asc": {  
	            sortSpec = Sort.by(Sort.Order.asc("postdate"), Sort.Order.desc("id"));
	            break;
	        }
			default:
				sortSpec = Sort.by(Sort.Order.desc("id"));
			}
		}
		
		// 기존 페이지 객체에 정렬 주입
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);
		
		// 가이드 목록 조회
		Page<Guide> guidePage;
		try {
			guidePage = (keyword == null || keyword.isBlank())
					? kitchenRepository.findAll(sortedPageable)
					: kitchenRepository.findAllByCategory(keyword, sortedPageable);
			
			boolean hasCategory = category != null && !category.isBlank();
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			
			if (!hasCategory) {
				// 전체
				guidePage = hasKeyword
	                    ? kitchenRepository.findAllByTitleContainingIgnoreCase(keyword, sortedPageable)
	                    : kitchenRepository.findAll(sortedPageable);
	        }
			else {
				// 카테고리만
				guidePage = hasKeyword
	                    ? kitchenRepository.findAllByCategoryAndTitleContainingIgnoreCase(category, keyword, sortedPageable)
	                    : kitchenRepository.findAllByCategory(category, sortedPageable);
	        }
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_READ_LIST_FAIL);
		}
		
		
		// dto 변경
		List<GuideReadResponseDto> guideDtoList = new ArrayList<GuideReadResponseDto>();
		

		
		for (Guide guide : guidePage) {
			GuideReadResponseDto guideDto = guideMapper.toReadResponseDto(guide);
			guideDto.setLikecount(likeService.countLike("guide", guide.getId()));
			
			// 좋아요 여부 조회
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			    String username = authentication.getName();
			    int userId = userService.readUserByUsername(username).getId();
			    
			    guideDto.setLikestatus(likeService.existsUserLike("guide", guideDto.getId(), userId));
			}
			
			UserReadResponseDto userInfo = userService.readUserById(guideDto.getUserId());
			guideDto.setUsername(userInfo.getUsername());
			guideDtoList.add(guideDto);
		}
		
		
		return new PageImpl<>(guideDtoList, sortedPageable, guidePage.getTotalElements());
	}
	
	
	// 특정 가이드 상세 조회 (좋아요 상태 포함)
	public GuideReadResponseDto readGuideById(int id) {
	    Guide guide = kitchenRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("해당 가이드를 찾을 수 없습니다. ID: " + id));
	    
	    GuideReadResponseDto guideDto = guideMapper.toReadResponseDto(guide);

	    long likeCount = likeService.countLike("guide", guide.getId());
	    guideDto.setLikecount(likeCount);

	    boolean likestatus = false;
	    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	    if (authentication != null && authentication.isAuthenticated()
	            && !"anonymousUser".equals(authentication.getPrincipal())) {
	        String username = authentication.getName();
	        int userId = userService.readUserByUsername(username).getId();
	        likestatus = likeService.existsUserLike("guide", guideDto.getId(), userId);
	    }
	    guideDto.setLikestatus(likestatus);

	    return guideDto;
	}

	
	// 가이드 좋아요
	public LikeCreateResponseDto likeGuide(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 게시글 존재 여부 확인
		if (!kitchenRepository.existsById(likeDto.getRecodenum())) {
		    throw new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND);
		}
		
		// 좋아요 저장
		try {
		    return likeService.createLike(likeDto);
		}
		catch (ApiException e) {
		    throw e;
		}
		catch (Exception e) {
		    throw new ApiException(KitchenErrorCode.KITCHEN_LIKE_FAIL);
		}
		
	}

	
	// 가이드 좋아요 취소
	public void unlikeGuide(LikeDeleteRequestDto likeDto) {
	    // 입력값 검증
	    if (likeDto == null || likeDto.getTablename() == null
	            || likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
	    }

	    // 게시글 존재 여부 확인
	    if (!kitchenRepository.existsById(likeDto.getRecodenum())) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND);
	    }

	    // 좋아요 삭제
	    try {
	        likeService.deleteLike(likeDto);
	    } catch (ApiException e) {
	        throw e;
	    } catch (Exception e) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_UNLIKE_FAIL);
	    }
	}
	

	// 가이드 작성
	public GuideCreateResponseDto createGuide(GuideCreateRequestDto guideRequestDto) {
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
			}
		}
		guideRequestDto.setUserId(userService.readUserByUsername(username).getId());
	
		// 입력값 검증
	    if (guideRequestDto == null 
	            || guideRequestDto.getTitle() == null 
	            || guideRequestDto.getSubtitle() == null
	            || guideRequestDto.getCategory() == null 
	            || guideRequestDto.getContent() == null) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
	    }
	    
	    // 엔티티 변환 및 저장
	    Guide guide = guideMapper.toEntity(guideRequestDto);
	    guide.setPostdate(new Date());
	    
	    try {
	        guide = kitchenRepository.save(guide);
	        return guideMapper.toCreateResponseDto(guide);
	    } catch (Exception e) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_CREATE_FAIL);
	    }
	}
	
	
	// 가이드 삭제
	public void deleteGuide(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
		}
		
		// 가이드 게시글 여부 확인
		Guide guide = kitchenRepository.findById(id)
				.orElseThrow(() -> new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (guide.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
				}
				if (guide.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != guide.getUser().getId()) {
						throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
			}
		}
		
		// 가이드 삭제
		try {
			kitchenRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(KitchenErrorCode.KITCHEN_DELETE_FAIL);
		}
		
	}
	
	
	
	// 가이드 수정
	public GuideUpdateResponseDto updateGuide(GuideUpdateRequestDto guideRequestDto) {
		
		// 입력값 검증
	    if (guideRequestDto == null || guideRequestDto.getId() == 0
	            || guideRequestDto.getTitle() == null || guideRequestDto.getSubtitle() == null
	            || guideRequestDto.getCategory() == null || guideRequestDto.getContent() == null) {
	        throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
	    }
		
		// 가이드 존재 여부 확인
		Guide guide = kitchenRepository.findById(guideRequestDto.getId())
				.orElseThrow(() -> new ApiException(KitchenErrorCode.KITCHEN_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto user = userService.readUserByUsername(username);
		if (!user.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (user.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (guide.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
				}
				if (guide.getUser().getRole().equals(Role.ROLE_ADMIN)) {
					if (user.getId() != guide.getUser().getId()) {
						throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
					}
				}
			}
			// 일반 회원
			else {
				throw new ApiException(KitchenErrorCode.KITCHEN_FORBIDDEN);
			}
		}
		
		// 변경 값 설정
	    guide.setTitle(guideRequestDto.getTitle());
	    guide.setSubtitle(guideRequestDto.getSubtitle());
	    guide.setCategory(guideRequestDto.getCategory());
	    guide.setContent(guideRequestDto.getContent());
	    
	    
	    
	    // 이벤트 수정
 		try {
 			guide = kitchenRepository.save(guide);
 			return guideMapper.toUpdateResponseDto(guide);
 		}
 		catch (Exception e) {
 			throw new ApiException(KitchenErrorCode.KITCHEN_UPDATE_FAIL);
 		}
		
	}


	
	// 사용자가 저장한 가이드 목록 조회
    public Page<GuideReadMySavedResponseDto> readSavedGuidePageByUserId(Integer userId, Pageable pageable) {

        if (userId == null || pageable == null) {
            throw new ApiException(KitchenErrorCode.KITCHEN_INVALID_INPUT);
        }

        List<LikeReadResponseDto> likeList = likeService.readLikeListByTablenameAndUserId("guide", userId);

        // 좋아요한 가이드 ID만 추출
        List<Integer> guideIds = likeList.stream()
                .map(LikeReadResponseDto::getRecodenum)
                .toList();

        if (guideIds.isEmpty()) {
            return Page.empty(pageable); 
        }

        Page<Guide> guidePage = kitchenRepository.findByIdIn(guideIds, pageable);

        List<GuideReadMySavedResponseDto> dtoList = new ArrayList<>();
        for (Guide guide : guidePage) {
            GuideReadMySavedResponseDto dto = guideMapper.toReadMySavedResponseDto(guide);
            dto.setLikecount(likeService.countLike("guide", guide.getId())); // 좋아요 수 세팅
            dtoList.add(dto);
        }

        return new PageImpl<>(dtoList, pageable, guidePage.getTotalElements());
    }
	
	
	
	
	
	
	
	
	
	
}
