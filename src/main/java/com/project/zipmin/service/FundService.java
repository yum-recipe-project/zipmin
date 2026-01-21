package com.project.zipmin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.api.UserAccountErrorCode;
import com.project.zipmin.api.WithdrawErrorCode;
import com.project.zipmin.dto.FundCreateRequestDto;
import com.project.zipmin.dto.FundCreateResponseDto;
import com.project.zipmin.dto.FundReadResponseDto;
import com.project.zipmin.dto.UserAccountCreateRequestDto;
import com.project.zipmin.dto.UserAccountCreateResponseDto;
import com.project.zipmin.dto.UserAccountReadResponseDto;
import com.project.zipmin.dto.UserAccountUpdateRequestDto;
import com.project.zipmin.dto.UserAccountUpdateResponseDto;
import com.project.zipmin.dto.UserReadResponseDto;
import com.project.zipmin.dto.WithdrawCreateRequestDto;
import com.project.zipmin.dto.WithdrawReadResponseDto;
import com.project.zipmin.dto.WithdrawUpdateRequestDto;
import com.project.zipmin.dto.WithdrawUpdateResponseDto;
import com.project.zipmin.entity.Fund;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.UserAccount;
import com.project.zipmin.entity.Withdraw;
import com.project.zipmin.mapper.FundMapper;
import com.project.zipmin.mapper.UserAccountMapper;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.mapper.WithdrawMapper;
import com.project.zipmin.repository.FundRepository;
import com.project.zipmin.repository.UserAccountRepository;
import com.project.zipmin.repository.WithdrawRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FundService {
	
	private final UserAccountRepository accountRepository;
	private final FundRepository fundRepository;
	private final WithdrawRepository withdrawRepository;
	
	private final UserAccountMapper accountMapper;
	private final FundMapper fundMapper;
	private final WithdrawMapper withdrawMapper;
	private final UserMapper userMapper;
	
	private final UserService userService;
	
	@Value("${app.upload.public-path:/files}")
	private String publicPath;
	
	
	
	
	
	// 계좌 상세 조회
	public UserAccountReadResponseDto readAccountById(int id) {
		
		// 입력값 검증
		if (id < 0) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 상세 조회
		UserAccount account = accountRepository.findById(id)
				.orElseThrow(() -> new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND));
		
		return accountMapper.toReadResponseDto(account);
	}
	
	
	
	
	
	// 사용자 계좌 상세 조회
	public UserAccountReadResponseDto readAccountByUserId(int userId) {

		// 입력값 검증
		if (userId < 0) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != userId) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
			// 일반회원
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userId) {
				throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
			}
		}
		
		// 계좌 조회
		UserAccount userAccount = accountRepository.findByUserId(userId)
				.orElseThrow(() -> new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND));

		return accountMapper.toReadResponseDto(userAccount);
	}
	
	
	
	
	
	// 계좌 작성
	public UserAccountCreateResponseDto createAccount(UserAccountCreateRequestDto accountRequestDto) {
		
		// 입력값 검증
		if (accountRequestDto == null 
				|| accountRequestDto.getBank() == null 
				|| accountRequestDto.getAccountnum() == null
				|| accountRequestDto.getName() == null
				|| accountRequestDto.getUserId() == 0) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 저장
		UserAccount userAccount = accountMapper.toEntity(accountRequestDto);
		try {
			userAccount = accountRepository.save(userAccount);
			return accountMapper.toCreateResponseDto(userAccount);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 계좌 수정
	public UserAccountUpdateResponseDto updateAccount(UserAccountUpdateRequestDto accountRequestDto) {

		// 입력값 검증
		if (accountRequestDto == null 
				|| accountRequestDto.getUserId() == 0
				|| accountRequestDto.getBank() == null
				|| accountRequestDto.getAccountnum() == null
				|| accountRequestDto.getName() == null) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 조회
		UserAccount account = accountRepository.findByUserId(accountRequestDto.getUserId())
				.orElseThrow(() -> new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND));

		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (account.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
				if (account.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != account.getUser().getId()) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
			// 일반회원
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != account.getUser().getId()) {
				throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
			}
		}
		
		// 변경 값 설정
		account.setBank(accountRequestDto.getBank());
		account.setAccountnum(accountRequestDto.getAccountnum());
		account.setName(accountRequestDto.getName());

		try {
			account = accountRepository.save(account);
			return accountMapper.toUpdateResponseDto(account);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UPDATE_FAIL);
		}
	}
	
	
	
	
	
	// 후원 목록 조회
	public Page<FundReadResponseDto> readFundPage(String keyword, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}
		
		// 정렬
		Sort orderBy = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			switch (sort) {
				case "id-desc":
					orderBy = Sort.by(Sort.Order.desc("id"));
					break;
				case "id-asc":
					orderBy = Sort.by(Sort.Order.asc("id"));
					break;
				default:
					break;
		    }
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), orderBy);
		
		// 후원 목록 조회
		Page<Fund> fundPage;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			
			fundPage = hasKeyword
					? fundRepository.findAllByFunderUsernameContainingIgnoreCase(keyword, pageable)
					: fundRepository.findAll(pageable);
		}
		catch (Exception e) {
			throw new ApiException(FundErrorCode.FUND_READ_LIST_FAIL);
		}
		
		// 후원 목록 응답 구성
		List<FundReadResponseDto> fundDtoList = new ArrayList<>();
		for (Fund fund : fundPage) {
			FundReadResponseDto fundDto = fundMapper.toReadResponseDto(fund);
			
			fundDtoList.add(fundDto);
		}
	
		return new PageImpl<>(fundDtoList, pageable, fundPage.getTotalElements());
	}
	
	
	
	
	
	// 사용자의 후원 목록 조회
	public Page<FundReadResponseDto> readFundPageByUserId(Integer userId, Pageable pageable) {

		// 입력값 검증
		if (userId == null || pageable == null) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
	
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != userId) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
			}
			// 일반회원
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userId) {
				throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
			}
		}
		
		// 후원 목록 조회
 		Page<Fund> fundPage;
 		try {
 			fundPage = fundRepository.findAllByFundeeId(userId, pageable);
 		}
 		catch (Exception e) {
 			throw new ApiException(FundErrorCode.FUND_READ_LIST_FAIL);
 		}

 		// 후원 목록 응답 구성
 		List<FundReadResponseDto> fundDtoList = new ArrayList<FundReadResponseDto>();
 		for (Fund fund : fundPage) {
 			FundReadResponseDto fundDto = fundMapper.toReadResponseDto(fund);
 			
 			fundDto.setNickname(fund.getFunder().getNickname());
			fundDto.setTitle(fund.getRecipe().getTitle());
			fundDto.setImage(fund.getRecipe().getImage());

			fundDtoList.add(fundDto);
		}
 		
		return new PageImpl<>(fundDtoList, pageable, fundPage.getTotalElements());
	}
	
	
	
	
	
	// 사용자의 후원 총합 조회
	public int sumFundByUserId(Integer userId) {
		
		// 입력값 검증
		if (userId == null) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}

		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != userId) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
			}
			// 일반회원
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userId) {
				throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
			}
		}
		
		// 후원 총합 조회
		try {
			 return fundRepository.sumPointByFundeeId(userId);
		}
		catch (Exception e) {
			throw new ApiException(FundErrorCode.FUND_READ_SUM_FAIL);
		}
	}
	
	
	
	
	
	// 후원 작성
	public FundCreateResponseDto createFund(FundCreateRequestDto fundRequestDto) {

		// 입력값 검증
		if (fundRequestDto == null
				|| fundRequestDto.getFundeeId() == 0
				|| fundRequestDto.getFundeeId() == 0
				|| fundRequestDto.getRecipeId() == 0
				|| fundRequestDto.getPoint() == 0) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}
		
		// 사용자 조회
		UserReadResponseDto funderDto = userService.readUserById(fundRequestDto.getFunderId());
		UserReadResponseDto fundeeDto = userService.readUserById(fundRequestDto.getFundeeId());
		
		// 입력값 검증
		if (funderDto.getPoint() < fundRequestDto.getPoint()) {
			throw new ApiException(FundErrorCode.FUND_INVALID_POINT);
		}
		
		// 후원 저장
		Fund fund = fundMapper.toEntity(fundRequestDto);
		try {
			fund = fundRepository.save(fund);
			
			// 사용자 갱신
			funderDto.setPoint(funderDto.getPoint() - fundRequestDto.getPoint());
			fundeeDto.setRevenue(fundeeDto.getRevenue() + fundRequestDto.getPoint());
			userService.updateUser(userMapper.toUpdateRequestDto(userMapper.toEntity(funderDto)));
			userService.updateUser(userMapper.toUpdateRequestDto(userMapper.toEntity(fundeeDto)));
			
			return fundMapper.toCreateResponseDto(fund);
		}
		catch (Exception e) {
			throw new ApiException(FundErrorCode.FUND_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 출금 목록 조회 (관리자)
	public Page<WithdrawReadResponseDto> readAdminWithdrawPage(String field, String keyword, Integer status, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_INPUT);
		}
		
		// 정렬
		Sort orderBy = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			switch (sort) {
				case "id-desc":
					orderBy = Sort.by(Sort.Order.desc("id"));
					break;
				case "id-asc":
					orderBy = Sort.by(Sort.Order.asc("id"));
					break;
				case "point-desc":
					orderBy = Sort.by(Sort.Order.desc("point"));
					break;
				case "point-asc":
					orderBy = Sort.by(Sort.Order.asc("point"));
					break;
				case "claimdate-desc":
					orderBy = Sort.by(Sort.Order.desc("claimdate"));
					break;
				case "claimdate-asc":
					orderBy = Sort.by(Sort.Order.asc("claimdate"));
					break;
				case "settledate-desc":
					orderBy = Sort.by(Sort.Order.desc("settledate"));
					break;
				case "settledate-asc":
					orderBy = Sort.by(Sort.Order.asc("settledate"));
					break;
				default:
					break;
			}
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), orderBy);
		
		// 출금 목록 조회
		Page<Withdraw> withdrawPage = null;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			boolean hasStatus = status != null && status != 0;
			
			if (hasStatus) {
				if (hasKeyword) {
					if (field.equalsIgnoreCase("username")) {
						withdrawPage = withdrawRepository.findAllByStatusAndUserUsernameContainingIgnoreCase(status, keyword, pageable);
					}
					else if (field.equalsIgnoreCase("name")) {
						withdrawPage = withdrawRepository.findAllByStatusAndUserNameContainingIgnoreCase(status, keyword, pageable);
					}
				}
				else {
					withdrawPage = withdrawRepository.findAllByStatus(status, pageable);
				}
			}
			else {
				if (hasKeyword) {
					if (field.equalsIgnoreCase("username")) {
						withdrawPage = withdrawRepository.findAllByUserUsernameContainingIgnoreCase(keyword, pageable);
					}
					else if (field.equalsIgnoreCase("name")) {
						withdrawPage = withdrawRepository.findAllByUserNameContainingIgnoreCase(keyword, pageable);
					}
				}
				else {
					withdrawPage = withdrawRepository.findAll(pageable);
				}
			}
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_READ_LIST_FAIL);
		}

		// 출금 목록 응답 구성
		List<WithdrawReadResponseDto> withdrawDtoList = new ArrayList<>();
		for (Withdraw withdraw : withdrawPage) {
			WithdrawReadResponseDto withdrawDto = withdrawMapper.toReadResponseDto(withdraw);
			
			withdrawDto.setName(withdraw.getUser().getName());
			withdrawDto.setUsername(withdraw.getUser().getUsername());
			withdrawDto.setAccountnum(withdraw.getAccount().getAccountnum());
			
			withdrawDtoList.add(withdrawDto);
		}

		return new PageImpl<>(withdrawDtoList, pageable, withdrawPage.getTotalElements());
	}
	
	
	
	
	
	// 사용자의 출금 목록 조회
	public Page<WithdrawReadResponseDto> readWithdrawPageByUserId(int userId, Pageable pageable) {

		// 입력값 검증
		if (userId == 0 || pageable == null) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != userId) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userId) {
				throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
			}
		}
		
		// 출금 목록 조회
		Page<Withdraw> withdrawPage;
		try {
			withdrawPage = withdrawRepository.findAllByUserId(userId, pageable);
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_READ_LIST_FAIL);
		}

		// 출금 목록 응답 구성
		List<WithdrawReadResponseDto> withdrawDtoList = new ArrayList<>();
		for (Withdraw withdraw : withdrawPage) {
			WithdrawReadResponseDto withdrawDto = withdrawMapper.toReadResponseDto(withdraw);
			withdrawDtoList.add(withdrawDto);
		}

		return new PageImpl<>(withdrawDtoList, pageable, withdrawPage.getTotalElements());
	}

	
	
	
	
	// 출금 작성 (포인트 출금 신청)
	public WithdrawReadResponseDto createWithdraw(WithdrawCreateRequestDto withdrawRequestDto) {

		// 입력값 검증
		if (withdrawRequestDto == null
				|| withdrawRequestDto.getUserId() == 0
				|| withdrawRequestDto.getPoint() == 0) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_INPUT);
		}
		
		// 사용자 조회
		UserReadResponseDto userDto = userService.readUserById(withdrawRequestDto.getUserId());
		
		// 입력값 검증
		if (userDto.getPoint() < withdrawRequestDto.getPoint()) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_POINT);
		}
		
		// 계좌 설정
		UserAccountReadResponseDto accountDto = readAccountByUserId(withdrawRequestDto.getUserId());
		withdrawRequestDto.setAccountId(accountDto.getId());
		
		// 출금 저장
		Withdraw withdraw = withdrawMapper.toEntity(withdrawRequestDto);
		try {
			withdraw = withdrawRepository.save(withdraw);
			
			// 사용자 갱신
			userDto.setRevenue(userDto.getRevenue() - withdrawRequestDto.getPoint());
			userService.updateUser(userMapper.toUpdateRequestDto(userMapper.toEntity(userDto)));

			return withdrawMapper.toReadResponseDto(withdraw);
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_CREATE_FAIL);
		}
		
	}
	
	
	
	
	
	// 출금 수정
	public WithdrawUpdateResponseDto updateWithdraw(WithdrawUpdateRequestDto withdrawDto) {
		
		// 입력값 검증
		if (withdrawDto == null || withdrawDto.getId() == 0) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_INPUT);
		}
		
		// 출금 존재 여부 확인
		Withdraw withdraw = withdrawRepository.findById(withdrawDto.getId())
				.orElseThrow(() -> new ApiException(WithdrawErrorCode.WITHDRAW_NOT_FOUND));

		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (withdraw.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
				if (withdraw.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != withdraw.getUser().getId()) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
			}
			// 일반회원
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != withdraw.getUser().getId()) {
				throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
			}
		}
		
		// 변경 값 설정
		if (withdrawDto.getPoint() != 0) {
			withdraw.setPoint(withdrawDto.getPoint());
		}
		if (withdrawDto.getStatus() == 0 || withdrawDto.getStatus() == 1) {
			withdraw.setStatus(withdrawDto.getStatus());
			withdraw.setSettledate(new Date());
		}
		if (withdrawDto.getClaimdate() != null) {
			withdraw.setClaimdate(withdrawDto.getClaimdate());
		}
		if (withdrawDto.getSettledate() != null) {
			withdraw.setSettledate(withdrawDto.getSettledate());
		}
		if (withdrawDto.getUserId() != 0) {
			UserReadResponseDto userDto = userService.readUserById(withdrawDto.getId());
			withdraw.setUser(userMapper.toEntity(userDto));
		}
		if (withdrawDto.getAccountId() != 0) {
			UserAccountReadResponseDto accountDto = readAccountById(withdrawDto.getId());
			withdraw.setAccount(accountMapper.toEntity(accountDto));
		}
		if (withdrawDto.getAdminId() != 0) {
			UserReadResponseDto userDto = userService.readUserById(withdrawDto.getId());
			withdraw.setAdmin(userMapper.toEntity(userDto));
		}
		
		// 출금 수정
		try {
			withdraw = withdrawRepository.save(withdraw);
			return withdrawMapper.toUpdateResponseDto(withdraw);
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_UPDATE_FAIL);
		}
		
	}
	
	
	
	
	
	// 출금 삭제
	public void deleteWithdraw(int id) {
		
		// 입력값 검증
		if (id < 0) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_INPUT);
		}
		
		// 출금 존재 여부 확인
		Withdraw withdraw = withdrawRepository.findById(id)
				.orElseThrow(() -> new ApiException(WithdrawErrorCode.WITHDRAW_NOT_FOUND));
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			// 관리자
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (withdraw.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
				if (withdraw.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != withdraw.getUser().getId()) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
			}
			// 일반회원
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != withdraw.getUser().getId()) {
				throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
			}
		}
		
		// 출금 삭제
		try {
			withdrawRepository.deleteById(id);
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_DELETE_FAIL);
		}
		
	}
}
