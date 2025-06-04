package com.project.zipmin.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.zipmin.dto.FindUsernameRequestDto;
import com.project.zipmin.dto.UserDto;
import com.project.zipmin.dto.UserJoinDto;
import com.project.zipmin.entity.User;

@Service
public interface UserService {

    // 모든 회원 조회
    public List<UserDto> getUserList();
    

    // 특정 회원의 팔로워 목록 조회
    // List<UserDTO> getFollowerList(String userId);

    // 특정 회원의 팔로잉 목록 조회
    // List<UserDTO> getFollowingList(String userId);

    // 아이디를 이용해 특정 회원 조회
    // UserDTO getUserById(String userId);

    // 회원가입 (새로운 회원 추가)
    public User joinUser(UserJoinDto userDTO);
    
    // 사용자 아이디 중복 확인
    public boolean isUsernameDuplicated(String username);

    // 회원 정보 업데이트
    // UserDTO updateUserAccount(UserDTO userDTO);

    // 비밀번호 변경
    // boolean changePassword(String userId, String newPassword);

    // 회원 탈퇴 (삭제)
    // boolean deleteUserById(String userId);
    
    // 아이디 찾기
    public String findUsername(FindUsernameRequestDto findUsernameRequestDto);
}
