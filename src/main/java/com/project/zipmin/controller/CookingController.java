package com.project.zipmin.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.ClassApplyDTO;
import com.project.zipmin.dto.ClassDTO;
import com.project.zipmin.dto.ClassScheduleDTO;
import com.project.zipmin.dto.ClassTeacherDTO;
import com.project.zipmin.dto.CommentDTO;
import com.project.zipmin.dto.GuideDTO;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/classes")
public class CookingController {

	// 클래스 목록 조회
	@GetMapping("")
	public List<ClassDTO> listClass() {
		return null;
	}
	
	
	
	// 특정 클래스 조회
	@GetMapping("/{classId}")
	public ClassDTO viewClass(
			@PathVariable("classId") int classId) {
		return null;
	}
	
	
	
	// 새 클래스 신청
	@PostMapping("")
	public int writeClass() {
		return 0;
	}
	
	
	
	// 클래스 승인 및 거절 (관리자)
	@PatchMapping("/{classId}")
	public int updateClassStatus(
			@PathVariable("classId") int classId) {
		return 0;
	}
	
	
	
	// 특정 클래스 수정 (관리자)
	@PutMapping("/{classId}")
	public int editClass(
			@PathVariable("classId") int classId) {
		// 구현 안할듯
		return 0;
	}
	
	
	
	// 특정 클래스 삭제 (관리자)
	@DeleteMapping("/{classId}")
	public int deleteGuide(
			@PathVariable("classId") int classId) {
		return 0;
	}
	
	
	
	// 특정 클래스의 일정 목록 조회
	@GetMapping("/{classId}/schedules")
	public List<ClassScheduleDTO> viewSchedule(
			@PathVariable("classId") int classId) {
		// 필요에 따라선 클래스 조회에서 일정 목록을 한번에 조회하게 될 수도 있음
		return null;
	}
	
	
	
	// 특정 클래스의 일정 추가
	@PostMapping("/{classId}/schedules")
	public int writeSchedule(
			@PathVariable("classId") int classId) {
		return 0;
	}
	
	
	
	// 특정 클래스의 일정 수정
	@PutMapping("/{classId}/schedules")
	public int editSchedule(
			@PathVariable("classId") int classId) {
		// 필요 없을수도있음
		return 0;
	}
	
	
	
	// 특정 클래스의 일정 삭제
	@DeleteMapping("/{classId}/schedules")
	public int deleteSchedule(
			@PathVariable("classId") int classId) {
		// 필요 없을 수도 있음
		return 0;
	}
	
	
	
	// 특정 클래스의 강사 목록 조회
	@GetMapping("/{classId}/teacher")
	public List<ClassTeacherDTO> viewTeacher(
			@PathVariable("classId") int classId) {
		// 필요에 따라선 클래스 조회에서 일정 목록을 한번에 조회하게 될 수도 있음
		return null;
	}
	
	
	
	// 특정 클래스의 강사 추가
	@PostMapping("/{classId}/teacher")
	public int writeTeacher(
			@PathVariable("classId") int classId) {
		return 0;
	}
	
	
	
	// 특정 클래스의 강사 수정
	@PutMapping("/{classId}/teacher")
	public int editTeacher(
			@PathVariable("classId") int classId) {
		// 필요 없을수도있음
		return 0;
	}
	
	
	
	// 특정 클래스의 강사 삭제
	@DeleteMapping("/{classId}/teacher")
	public int deleteTeacher(
			@PathVariable("classId") int classId) {
		// 필요 없을 수도 있음
		return 0;
	}
	
	
	
	// 특정 클래스의 신청서 목록 조회
	@GetMapping("/{classId}/applications")
	public List<ClassApplyDTO> listClassApply(
			@PathVariable("classId") int classId) {
		return null;
	}
	
	
	
	// 특정 클래스의 특정 신청서 승인
	@PatchMapping("/{classId}/applications/{applyId}/approve")
	public int approveApplication(
			@PathVariable("classId") int classId,
			@PathVariable("applyId") int applyId) {
		return 0;
	}
	
	
	
	// 특정 클래스의 특정 신청서 거절
	@PatchMapping("/{classId}/applications/{applyId}/reject")
	public int rejectApplication(
			@PathVariable("classId") int classId,
			@PathVariable("applyId") int applyId) {
		return 0;
	}

}
