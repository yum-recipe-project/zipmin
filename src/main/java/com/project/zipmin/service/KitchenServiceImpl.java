package com.project.zipmin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.GuideDTO;
import com.project.zipmin.entity.Guide;
import com.project.zipmin.mapper.GuideMapper;
import com.project.zipmin.repository.KitchenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KitchenServiceImpl implements KitchenService {
	
	@Autowired
	private final KitchenRepository guideRepository;
	@Autowired
	private final GuideMapper guideMapper;
	
	
	
//	 모든 키친가이드 게시물 조회	
	@Override
    public Page<GuideDTO> getGuideList(String category, Pageable pageable) {
        Page<Guide> guidePage;

        if ("all".equals(category)) {
            guidePage = guideRepository.findAll(pageable);
        } else {
            guidePage = guideRepository.findByCategory(category, pageable);
        }

        List<GuideDTO> guideDTOList = guideMapper.guideListToGuideDTOList(guidePage.getContent());

        return new PageImpl<>(guideDTOList, pageable, guidePage.getTotalElements());
    }
	
	
//	특정 가이드 상세 조회
	@Override
	public GuideDTO getGuideById(int guideId) {
	    Guide guide = guideRepository.findById(guideId)
	            .orElseThrow(() -> new IllegalArgumentException("해당 가이드를 찾을 수 없습니다. ID: " + guideId));

	    return guideMapper.guideToGuideDTO(guide);
	}

	
	

}
