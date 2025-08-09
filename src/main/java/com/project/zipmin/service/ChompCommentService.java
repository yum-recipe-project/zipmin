package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.method.P;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.CommentErrorCode;
import com.project.zipmin.api.LikeErrorCode;
import com.project.zipmin.dto.CommentCreateRequestDto;
import com.project.zipmin.dto.CommentCreateResponseDto;
import com.project.zipmin.dto.CommentDeleteRequestDto;
import com.project.zipmin.dto.CommentReadMyResponseDto;
import com.project.zipmin.dto.CommentReadResponseDto;
import com.project.zipmin.dto.CommentUpdateRequestDto;
import com.project.zipmin.dto.CommentUpdateResponseDto;
import com.project.zipmin.dto.EventReadResponseDto;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.dto.MegazineReadResponseDto;
import com.project.zipmin.dto.ReportCreateRequestDto;
import com.project.zipmin.dto.ReportCreateResponseDto;
import com.project.zipmin.dto.ReportDeleteRequestDto;
import com.project.zipmin.dto.VoteChoiceReadResponseDto;
import com.project.zipmin.dto.VoteReadResponseDto;
import com.project.zipmin.entity.Comment;
import com.project.zipmin.entity.Event;
import com.project.zipmin.entity.Megazine;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.Vote;
import com.project.zipmin.entity.VoteChoice;
import com.project.zipmin.mapper.ChompMapper;
import com.project.zipmin.mapper.CommentMapper;
import com.project.zipmin.mapper.EventMapper;
import com.project.zipmin.mapper.MegazineMapper;
import com.project.zipmin.mapper.VoteChoiceMapper;
import com.project.zipmin.mapper.VoteMapper;
import com.project.zipmin.mapper.VoteRecordMapper;
import com.project.zipmin.repository.ChompRepository;
import com.project.zipmin.repository.CommentRepository;
import com.project.zipmin.repository.EventRepository;
import com.project.zipmin.repository.MegazineRepository;
import com.project.zipmin.repository.VoteChoiceRepository;
import com.project.zipmin.repository.VoteRecordRepository;
import com.project.zipmin.repository.VoteRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ChompCommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private VoteChoiceRepository choiceRepository;
	@Autowired
	private VoteRecordRepository recordRepository;
	@Autowired
	private MegazineRepository megazineRepository;
	@Autowired
	private EventRepository eventRepository;
	
	private final ChompMapper chompMapper;
	private final VoteMapper voteMapper;
	private final VoteChoiceMapper choiceMapper;
	private final VoteRecordMapper recordMapper;
	private final MegazineMapper megazineMapper;
	private final EventMapper eventMapper;
	
	
	
	// 투표의 목록을 조회하는 함수
	public Page<VoteReadResponseDto> readVotePage(Pageable pageable) {
		
		Page<Vote> votePage = voteRepository.findAll(pageable);
		
		List<VoteReadResponseDto> voteDtoList = new ArrayList<>();
		Date today = new Date();
		for (Vote vote : votePage) {
			VoteReadResponseDto voteDto = voteMapper.toReadResponseDto(vote);
			
			// 투표수
			long total = recordRepository.countByVoteId(vote.getId());
			voteDto.setTotal(total);
			
			// 댓글수
			int commentCount = readCommentCount("vote", vote.getId());
			voteDto.setCommentcount(commentCount);
			
			// 투표 상태
			String status = (today.after(voteDto.getOpendate()) && today.before(voteDto.getClosedate())) ? "open" : "close";
			voteDto.setStatus(status);
			
			// 투표 옵션
			List<VoteChoice> choiceList = choiceRepository.findByVoteId(vote.getId());
			List<VoteChoiceReadResponseDto> choiceDtoList = new ArrayList<>();
			for (VoteChoice choice : choiceList) {
				VoteChoiceReadResponseDto choiceDto = choiceMapper.toReadResponseDto(choice);
				
				long count = recordRepository.countByChoiceId(choice.getId());
				double rate = (total == 0) ? 0.0 : Math.round(((double) count / total) * 100) / 1.0;
				
				choiceDto.setCount((int) count);
				choiceDto.setRate(rate);
				
				choiceDtoList.add(choiceDto);
			}
			voteDto.setChoiceList(choiceDtoList);
			
			voteDtoList.add(voteDto);
		}
		
		return new PageImpl<>(voteDtoList, pageable, votePage.getTotalElements());
	}
	
	
	
	
	// 매거진의 목록을 조회하는 함수
	public Page<MegazineReadResponseDto> readMegazinePage(String keyword, Pageable pageable) {
		
		Page<Megazine> megazinePage = (keyword == null || keyword.isBlank())
				? megazineRepository.findAll(pageable)
				: megazineRepository.findByTitleContainingIgnoreCase(keyword, pageable);
		
		List<MegazineReadResponseDto> megazineDtoList = new ArrayList<>();
		for (Megazine megazine : megazinePage) {
			MegazineReadResponseDto megazineDto = megazineMapper.toReadResponseDto(megazine);
			
			// 댓글수
			int count = readCommentCount("megazine", megazine.getId());
			megazineDto.setCommentcount(count);
			
			megazineDtoList.add(megazineDto);
		}
		
		return new PageImpl<>(megazineDtoList, pageable, megazinePage.getTotalElements());
	}
	
	
	
	
	// 투표의 목록을 조회하는 함수
	public Page<EventReadResponseDto> readEventPage(String keyword, Pageable pageable) {
		
		Page<Event> eventPage = (keyword == null || keyword.isBlank())
				? eventRepository.findAll(pageable)
				: eventRepository.findByTitleContainingIgnoreCase(keyword, pageable);
		
		List<EventReadResponseDto> eventDtoList = new ArrayList<>();
		for (Event event : eventPage) {
			EventReadResponseDto eventDto = eventMapper.toReadResponseDto(event);
			
			// 댓글수
			int count = readCommentCount("event", event.getId());
			eventDto.setCommentcount(count);
			
			eventDtoList.add(eventDto);
		}
		
		return new PageImpl<>(eventDtoList, pageable, eventPage.getTotalElements());
	}
	
	
	
	
	
	
	// **** 수정 보완 필요 *** 댓글 수
	public int readCommentCount(String tablename, Integer recodenum) {
		
		// 입력값 검증
		if (tablename == null || recodenum == null) {
			throw new ApiException(CommentErrorCode.COMMENT_INVALID_INPUT);
		}
		
		try {
			return commentRepository.countByTablenameAndRecodenum(tablename, recodenum);
		}
		catch (Exception e) {
			throw new ApiException(CommentErrorCode.COMMENT_READ_COUNT_FAIL);
		}
		
	}
	
	
	



	
	
	
	
	

}
