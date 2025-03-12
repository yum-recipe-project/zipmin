package com.project.zipmin.service;

import java.util.List;

import com.project.zipmin.dto.UserDTO;

public interface UserService {

    // 모든 회원 조회
    public List<UserDTO> getUserList();

    // 특정 회원의 팔로워 목록 조회
    // List<UserDTO> getFollowerList(String userId);

    // 특정 회원의 팔로잉 목록 조회
    // List<UserDTO> getFollowingList(String userId);

    // 아이디를 이용해 특정 회원 조회
    // UserDTO getUserById(String userId);

    // 회원가입 (새로운 회원 추가)
    // UserDTO registerUser(UserDTO userDTO);

    // 회원 정보 업데이트
    // UserDTO updateUserAccount(UserDTO userDTO);

    // 비밀번호 변경
    // boolean changePassword(String userId, String newPassword);

    // 회원 탈퇴 (삭제)
    // boolean deleteUserById(String userId);
}
