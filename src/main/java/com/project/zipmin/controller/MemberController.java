package com.project.zipmin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.dto.MemberDTO;
import com.project.zipmin.service.IMemberService;

@RestController
@RequestMapping("/members")
public class MemberController {
	
	@Autowired
	IMemberService memberDAO;

	// 사용자 목록 조회
	@GetMapping("")
	public List<MemberDTO> listMember() {
		
		
		
		return null;
	}
	
	// 특정 사용자 조회
	@GetMapping("/{id}")
	public MemberDTO viewMember(@PathVariable("id") String id) {
		return null;
	}
	
	// 사용자 생성
	@PostMapping("")
	public int addMember() {
		return 0;
	}
	
	// 사용자 정보 수정
	@PutMapping("/{id}")
	public int editMember(@PathVariable("id") String id) {
		return 0;
	}
	
	// 사용자 삭제
	@DeleteMapping("/{id}")
	public int deleteMember(@PathVariable("id") String id) {
		return 0;
	}
	
	
	
	
	
	

}
