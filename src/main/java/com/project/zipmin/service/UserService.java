package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.LikeCreateRequestDto;
import com.project.zipmin.dto.LikeCreateResponseDto;
import com.project.zipmin.dto.LikeDeleteRequestDto;
import com.project.zipmin.dto.LikeReadResponseDto;
import com.project.zipmin.dto.MailDto;
import com.project.zipmin.dto.PasswordTokenDto;
import com.project.zipmin.dto.UserCreateRequestDto;
import com.project.zipmin.dto.UserCreateResponseDto;
import com.project.zipmin.dto.UserPasswordCheckRequestDto;
import com.project.zipmin.dto.UserPasswordUpdateRequestDto;
import com.project.zipmin.dto.UserPointReadResponseDto;
import com.project.zipmin.dto.UserProfileReadResponseDto;
import com.project.zipmin.dto.UserReadPasswordRequestDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.UserReadUsernameRequestDto;
import com.project.zipmin.dto.UserUpdateRequestDto;
import com.project.zipmin.dto.UserUpdateResponseDto;
import com.project.zipmin.entity.PasswordToken;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.User;
import com.project.zipmin.mapper.PasswordTokenMapper;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.repository.PasswordTokenRepository;
import com.project.zipmin.repository.UserRepository;
import com.project.zipmin.util.PasswordTokenUtil;

import io.jsonwebtoken.lang.Collections;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
	
	private final UserMapper userMapper;
	private final PasswordTokenMapper tokenMapper;
	
	private final UserRepository userRepository;
	private final PasswordTokenRepository tokenRepository;
	
	private final LikeService likeService;
	private final MailService mailService;
	
	private final PasswordEncoder passwordEncoder;
	
	

	
	
	// 사용자 목록 조회 (관리자)
	public Page<UserReadResponseDto> readUserPage(String category, String field, String keyword, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 정렬 문자열을 객체로 변환
		Sort sortSpec = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			switch (sort) {
				case "id-desc":
					sortSpec = Sort.by(Sort.Order.desc("id"));
					break;
				case "id-asc":
					sortSpec = Sort.by(Sort.Order.asc("id"));
					break;
				case "username-desc":
					sortSpec = Sort.by(Sort.Order.desc("username"));
					break;
				case "username-asc":
					sortSpec = Sort.by(Sort.Order.asc("username"));
					break;
				case "name-desc":
					sortSpec = Sort.by(Sort.Order.desc("name"));
					break;
				case "name-asc":
					sortSpec = Sort.by(Sort.Order.asc("name"));
					break;
				case "nickname-desc":
					sortSpec = Sort.by(Sort.Order.desc("nickname"));
					break;
				case "nickname-asc":
					sortSpec = Sort.by(Sort.Order.asc("nickname"));
					break;
				case "role-desc":
					sortSpec = Sort.by(Sort.Order.desc("role"));
					break;
				case "role-asc":
					sortSpec = Sort.by(Sort.Order.asc("role"));
					break;
				default:
					break;
			}
		}
		
		// 기존 페이지 객체에 정렬 주입
		Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sortSpec);
		
		// 사용자 목록 조회
		Page<User> userPage = null;
		try {
			boolean hasCategory = category != null && !category.isBlank();
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			
			if (!hasCategory) {
				// 전체
				if (!hasKeyword) {
					userPage = userRepository.findAll(sortedPageable);
				}
				else {
					if (field.equalsIgnoreCase("username")) {
						userPage = userRepository.findAllByUsernameContainingIgnoreCase(keyword, sortedPageable);
					}
					else if (field.equalsIgnoreCase("name")) {
						userPage = userRepository.findAllByNameContainingIgnoreCase(keyword, sortedPageable);
		            }
					else if (field.equalsIgnoreCase("nickname")) {
		                userPage = userRepository.findAllByNicknameContainingIgnoreCase(keyword, sortedPageable);
		            }
				}
			}
			else {
				// 카테고리
				List<Role> roles = null;
				if (category.equalsIgnoreCase("admin")) {
					roles = List.of(Role.ROLE_ADMIN, Role.ROLE_SUPER_ADMIN);
				}
				else if (category.equalsIgnoreCase("user")) {
					roles = List.of(Role.ROLE_USER);
				}
				
				if (!hasKeyword) {
					userPage = userRepository.findAllByRoleIn(roles, sortedPageable);
				}
				else {
					System.err.println("출력 = "  + field + " " + keyword);
					if (field.equalsIgnoreCase("username")) {
						userPage = userRepository.findAllByRoleInAndUsernameContainingIgnoreCase(roles, keyword, sortedPageable);
					}
					else if (field.equalsIgnoreCase("name")) {
						userPage = userRepository.findAllByRoleInAndNameContainingIgnoreCase(roles, keyword, sortedPageable);
		            }
					else if (field.equalsIgnoreCase("nickname")) {
		                userPage = userRepository.findAllByRoleInAndNicknameContainingIgnoreCase(roles, keyword, sortedPageable);
		            }
				}
			}
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_READ_LIST_FAIL);
		}
		
		// 사용자 목록 응답 구성
		List<UserReadResponseDto> userDtoList = new ArrayList<UserReadResponseDto>();
		for (User user : userPage) {
			UserReadResponseDto userDto = userMapper.toReadResponseDto(user);
			userDtoList.add(userDto);
		}
		
		return new PageImpl<>(userDtoList, pageable, userPage.getTotalElements());
	}
	
	
	
	
	
	// 사용자가 좋아요한 사용자 목록 조회
	public List<UserProfileReadResponseDto> readLikeUserList(Integer userId) {
		
		// 입력값 검증
		if (userId == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 좋아요 일련번호 목록 조회
		List<LikeReadResponseDto> likeDtoList = likeService.readLikeListByTablenameAndUserId("users", userId);
		List<Integer> idList = likeDtoList.stream()
				.map(LikeReadResponseDto::getRecodenum)
				.collect(Collectors.toCollection(LinkedHashSet::new))
				.stream().toList();
		
		if (idList.isEmpty()) {
			return Collections.emptyList();
		}
		
		// 사용자 목록 조회
		List<User> userList = null;
		try {
			userList = userRepository.findAllByIdIn(idList);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_READ_LIST_FAIL);
		}
		
		// 사용자 목록 응답 구성
		List<UserProfileReadResponseDto> userDtoList = new ArrayList<>();	
		for (User user : userList) {
			UserProfileReadResponseDto userDto = userMapper.toReadProfileResponseDto(user);
			
			// 좋아요 여부
			userDto.setLiked(likeService.existsUserLike("users", userDto.getId(), userId));
			
			userDtoList.add(userDto);
		}
		
		return userDtoList;
	}
	
	
	
	// 사용자를 좋아하는 사용자 목록 조회
	public List<UserProfileReadResponseDto> readLikedUserList(Integer userId) {
		
		// 입력값 검증
		if (userId == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 좋아요 일련번호 목록 조회
		List<LikeReadResponseDto> likeDtoList = likeService.readLikeListByTablenameAndRecodenum("users", userId);
		List<Integer> idList = likeDtoList.stream()
				.map(LikeReadResponseDto::getUserId)
				.collect(Collectors.toCollection(LinkedHashSet::new))
				.stream().toList();
		
		if (idList.isEmpty()) {
			return Collections.emptyList();
		}
		
		// 사용자 목록 조회
		List<User> userList = null;
		try {
			userList = userRepository.findAllByIdIn(idList);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_READ_LIST_FAIL);
		}
		
		// 사용자 목록 응답 구성
		List<UserProfileReadResponseDto> userDtoList = new ArrayList<>();	
		for (User user : userList) {
			UserProfileReadResponseDto userDto = userMapper.toReadProfileResponseDto(user);
			
			// 좋아요 여부
			userDto.setLiked(likeService.existsUserLike("users", userDto.getId(), userId));
			
			userDtoList.add(userDto);
		}
		
		return userDtoList;
	}
	
	
	// 아이디로 사용자 조회
	public UserReadResponseDto readUserById(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		return userMapper.toReadResponseDto(user);
	}
	
	

	// 아이디로 사용자 프로필 조회
	public UserProfileReadResponseDto readUserProfileById(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findById(id)
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		// 사용자 응답 구성
		UserProfileReadResponseDto userDto = userMapper.toReadProfileResponseDto(user);
		userDto.setLikecount(likeService.countLike("users", user.getId()));
		
		// 좋아요 여부
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal())) {
			String username = authentication.getName();
			int userId = readUserByUsername(username).getId();
			userDto.setLiked(likeService.existsUserLike("users", user.getId(), userId));
		}
		
		return userDto;
	}
	
	
	
	
	
	// 사용자명으로 사용자 조회
	public UserReadResponseDto readUserByUsername(String username) {
		
		// 입력값 검증
		if (username == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

		return userMapper.toReadResponseDto(user);
	}
	
	
	
	
	
	// 이름과 전화번호로 사용자 조회
	public UserReadResponseDto readUserByNameAndTel(UserReadUsernameRequestDto userDto) {
		
		// 입력값 검증
		if (userDto == null || userDto.getName() == null || userDto.getTel() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}

		// 사용자 조회
		User user = userRepository.findByNameAndTel(userDto.getName(), userDto.getTel())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		return userMapper.toReadResponseDto(user);
	}
	
	
	
	
	
	// 사용자명과 이메일로 사용자 조회
	public UserReadResponseDto readUserByUsernameAndEmail(UserReadPasswordRequestDto userDto) {
		
		// 입력값 검증
		if (userDto == null || userDto.getUsername() == null || userDto.getEmail() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findByUsernameAndEmail(userDto.getUsername(), userDto.getEmail())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		return userMapper.toReadResponseDto(user);
	}

	
	
	
	// 아이디 찾기
	public UserReadResponseDto findUsername(UserReadUsernameRequestDto userDto) {
		
		System.err.println("실행" + userDto);
		
		// 입력값 검증
		if (userDto == null || userDto.getName() == null || userDto.getTel() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findByNameAndTel(userDto.getName(), userDto.getTel())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		return userMapper.toReadResponseDto(user);
	}
	
	
	
	
	
	// 비밀번호 찾기
	public void findPassword(UserReadPasswordRequestDto userDto) {
		
		// 입력값 검증
		if (userDto == null || userDto.getUsername() == null || userDto.getEmail() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 조회
		User user = userRepository.findByUsernameAndEmail(userDto.getUsername(), userDto.getEmail())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		// 토큰 만료 시간 설정
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 30);
		Date expiresAt = cal.getTime();
		
		// 토큰 설정
		String rawToken = PasswordTokenUtil.createRawToken();
		String hashToken = PasswordTokenUtil.createHashToken(rawToken);
		PasswordTokenDto tokenDto = new PasswordTokenDto();
		tokenDto.setUserId(user.getId());
		tokenDto.setToken(hashToken);
		tokenDto.setExpiresAt(expiresAt);
		PasswordToken passwordToken = tokenMapper.toEntity(tokenDto);
		tokenRepository.save(passwordToken);
		
		String domain = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		String link = domain + "/user/resetPassword.do?key=" + rawToken;

		// 메일 전송
		MailDto mailDto = new MailDto();
		mailDto.setTo(user.getEmail());
		mailDto.setSubject("집밥의민족 계정 암호 재설정");
		mailDto.setContent("안녕하세요. 아잠만님.<br>"
				+ "아래 링크를 클릭하여 새 비밀번호를 설정해주세요.<br><br>"
				+ "<a href=\"" + link + "\">" + link + "</a><br><br>"
				+ "위의 비밀번호 변경 링크는 발송 후 30분 동안 유효합니다.<br>"
				+ "30분이 지난 후에는 비밀번호 변경 링크 요청을 다시 진행해주세요.<br><br>"
				+ "집밥의민족 드림");
		
		mailService.sendEmail(mailDto);
	}
	
	
	
	
	
	// 비밀번호 검증
	public void checkPassword(UserPasswordCheckRequestDto userDto) {
		
		// 입력값 검증
		if (userDto == null || userDto.getId() == null || userDto.getPassword() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 판단
		User user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
		
		// 비밀번호 검증
		if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
			throw new ApiException(UserErrorCode.USER_INCORRECT_PASSWORD);
		}
		
	}
	

	
	
	
	// 토큰 검증
	public void checkToken(String rawToken) {
		
		// 토큰 조회
		String hashToken = PasswordTokenUtil.createHashToken(rawToken);
		PasswordToken passwordToken = tokenRepository.findByToken(hashToken)
				.orElseThrow(() ->  new ApiException(UserErrorCode.USER_TOKEN_INVALID));
		
		// 만료 여부 확인
		if (!passwordToken.getExpiresAt().after(new Date())) {
			throw new ApiException(UserErrorCode.USER_TOKEN_EXPIRED);
		}
		
	}
	
	
	

	
	// 사용자 작성
	public UserCreateResponseDto createUser(UserCreateRequestDto userRequestDto) {
		
		// 입력값 검증
		if (userRequestDto == null || userRequestDto.getUsername() == null || userRequestDto.getPassword() == null || userRequestDto.getNickname() == null || userRequestDto.getName() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		User user = userMapper.toEntity(userRequestDto);
		user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
		user.setRole(userRequestDto.getRole());
		
		// 중복 아이디 검사
		if (userRepository.existsByUsername(userRequestDto.getUsername())) {
			throw new ApiException(UserErrorCode.USER_USERNAME_DUPLICATED);
		}
		
		// 중복 전화번호 검사
		if (userRequestDto.getTel() != null && userRepository.existsByTel(userRequestDto.getTel())) {
			throw new ApiException(UserErrorCode.USER_TEL_DUPLICATED);
		}
		
		// 중복 이메일 검사
		if (userRequestDto.getEmail() != null && userRepository.existsByEmail(userRequestDto.getEmail())) {
			throw new ApiException(UserErrorCode.USER_EMAIL_DUPLICATED);
		}
		
		// 사용자 저장
		try {
			user = userRepository.save(user);
			return userMapper.toCreateResponseDto(user);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_CREATE_FAIL);
		}
		
	}

	

	
	
	// 사용자 수정
	public UserUpdateResponseDto updateUser(UserUpdateRequestDto userDto) {
		
		// 입력값 검증
		if (userDto == null || userDto.getId() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 판단
		User user = userRepository.findById(userDto.getId())
				.orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

		// 변경 값 설정
		if (userDto.getUsername() != null) {
			if (!userDto.getUsername().equals(user.getUsername()) && userRepository.existsByUsername(userDto.getUsername())) {
				throw new ApiException(UserErrorCode.USER_USERNAME_DUPLICATED);
			}
			user.setUsername(userDto.getUsername());
		}
		if (userDto.getPassword() != null) {
			user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		}
		if (userDto.getEmail() != null) {
			if (!userDto.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(userDto.getEmail())) {
				throw new ApiException(UserErrorCode.USER_EMAIL_DUPLICATED);
			}
			user.setEmail(userDto.getEmail());
		}
		if (userDto.getName() != null) {
			user.setName(userDto.getName());
		}
		if (userDto.getNickname() != null) {
			user.setNickname(userDto.getNickname());
		}
		if (userDto.getTel() != null) {
			if (!userDto.getTel().equals(user.getTel()) && userRepository.existsByTel(userDto.getTel())) {
				throw new ApiException(UserErrorCode.USER_TEL_DUPLICATED);
			}
			user.setTel(userDto.getTel());
		}
		if (userDto.getAvatar() != null) {
			user.setAvatar(userDto.getAvatar());
		}
		if (userDto.getIntroduce() != null) {
			user.setIntroduce(userDto.getIntroduce());
		}
		if (userDto.getLink() != null) {
			user.setLink(userDto.getLink());
		}
		
		// 사용자 수정
		try {
			user = userRepository.save(user);
			return userMapper.toUpdateResponseDto(user);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_UPDATE_FAIL);
		}
		
	}
	
	
	

	
	// 사용자 비밀번호 수정
	public void updatePassword(UserPasswordUpdateRequestDto userDto) {
		
		// 토큰 조회
		PasswordToken token = tokenRepository.findByToken(PasswordTokenUtil.createHashToken(userDto.getToken()))
				.orElseThrow(() ->  new ApiException(UserErrorCode.USER_TOKEN_INVALID));
		
		// 만료 여부 확인
		if (!token.getExpiresAt().after(new Date())) {
			throw new ApiException(UserErrorCode.USER_TOKEN_EXPIRED);
		}
		
		// 비밀번호 수정
		User user = token.getUser();
		user.setPassword(passwordEncoder.encode(userDto.getPassword()));
		try {
			userRepository.save(user);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_UPDATE_FAIL);
		}
		
		// 토큰 만료
		token.setExpiresAt(new Date());
		try {
			tokenRepository.save(token);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_TOKEN_UPDATE_FAIL);
		}
		
	}

	


	
	// 사용자 삭제
	public void deleteUser(Integer id) {
		
		// 입력값 검증
		if (id == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 판단
		if (!userRepository.existsById(id)) {
			throw new ApiException(UserErrorCode.USER_NOT_FOUND);
		}
		
		// 사용자 삭제
		try {
			userRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_DELETE_FAIL);
		}
		
	}
	
	

	
	
	// 아이디 중복확인
	public boolean existsUsername(String username) {
		
		// 입력값 검증
		if (username == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}

		return userRepository.existsByUsername(username);
	}

	
	

	
	// 사용자 좋아요
	public LikeCreateResponseDto likeUser(LikeCreateRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 확인
		if (userRepository.existsById(likeDto.getRecodenum())) {
			new ApiException(UserErrorCode.USER_NOT_FOUND);
		}
		
		// 좋아요 저장
		try {
			return likeService.createLike(likeDto);
		}
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_LIKE_FAIL);
		}
		
	}
	
	
	
	
	
	// 사용자 좋아요 취소
	public void unlikeUser(LikeDeleteRequestDto likeDto) {
		
		// 입력값 검증
		if (likeDto == null || likeDto.getTablename() == null
				|| likeDto.getRecodenum() == null || likeDto.getUserId() == null) {
			throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
		}
		
		// 사용자 존재 여부 확인
		if (userRepository.existsById(likeDto.getRecodenum())) {
			new ApiException(UserErrorCode.USER_NOT_FOUND);
		}
		
		// 좋아요 취소
		try {
			likeService.deleteLike(likeDto);
		}
		catch (ApiException e) {
			throw e;
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_UNLIKE_FAIL);
		}
		
	}
	
	
	
	
	
	// 아이디로 사용자 포인트 조회
	public UserPointReadResponseDto readUserPointById(Integer id) {

	    // 입력값 검증
	    if (id == null) {
	        throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
	    }

	    // 사용자 조회
	    User user = userRepository.findById(id)
	            .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

	    return userMapper.toReadPointResponseDto(user);
	}
	
	
	

	
	// 사용자 포인트 충전
	public UserPointReadResponseDto addPointToUser(Integer id, int addPoint) {
	    // 입력값 검증
	    if (id == null) {
	        throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
	    }

	    // 사용자 조회
	    User user = userRepository.findById(id)
	            .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));

	    // 포인트 충전
	    int newPoint = user.getPoint() + addPoint;
	    user.setPoint(newPoint);
	    
	    userRepository.save(user);
	    
	    return userMapper.toReadPointResponseDto(user);
	}
	
	
	

	
	// 사용자 포인트 정보 업데이트
	public User saveUser(User user) {
        if (user == null) {
        	throw new ApiException(UserErrorCode.USER_INVALID_INPUT);
        }
        return userRepository.save(user);
    }
		
	public User getUserEntityById(Integer id) {
	    return userRepository.findById(id)
	            .orElseThrow(() -> new ApiException(UserErrorCode.USER_NOT_FOUND));
	}




	


}
