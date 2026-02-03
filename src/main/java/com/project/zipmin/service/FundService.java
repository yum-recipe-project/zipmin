package com.project.zipmin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.FundErrorCode;
import com.project.zipmin.api.PaymentErrorCode;
import com.project.zipmin.api.UserAccountErrorCode;
import com.project.zipmin.api.WithdrawErrorCode;
import com.project.zipmin.dto.fund.FundCreateRequestDto;
import com.project.zipmin.dto.fund.FundCreateResponseDto;
import com.project.zipmin.dto.fund.FundReadResponseDto;
import com.project.zipmin.dto.fund.PaymentCreateRequestDto;
import com.project.zipmin.dto.fund.PaymentCreateResponseDto;
import com.project.zipmin.dto.fund.AccountCreateRequestDto;
import com.project.zipmin.dto.fund.AccountCreateResponseDto;
import com.project.zipmin.dto.fund.AccountReadResponseDto;
import com.project.zipmin.dto.fund.AccountUpdateRequestDto;
import com.project.zipmin.dto.fund.AccountUpdateResponseDto;
import com.project.zipmin.dto.fund.WithdrawCreateRequestDto;
import com.project.zipmin.dto.fund.WithdrawReadResponseDto;
import com.project.zipmin.dto.fund.WithdrawUpdateRequestDto;
import com.project.zipmin.dto.fund.WithdrawUpdateResponseDto;
import com.project.zipmin.dto.user.UserReadResponseDto;
import com.project.zipmin.dto.user.UserUpdateRequestDto;
import com.project.zipmin.dto.user.UserUpdateResponseDto;
import com.project.zipmin.entity.Fund;
import com.project.zipmin.entity.Role;
import com.project.zipmin.entity.User;
import com.project.zipmin.entity.Account;
import com.project.zipmin.entity.Withdraw;
import com.project.zipmin.mapper.FundMapper;
import com.project.zipmin.mapper.AccountMapper;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.mapper.WithdrawMapper;
import com.project.zipmin.repository.FundRepository;
import com.project.zipmin.repository.AccountRepository;
import com.project.zipmin.repository.WithdrawRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class FundService {
	
	private final AccountRepository accountRepository;
	private final FundRepository fundRepository;
	private final WithdrawRepository withdrawRepository;
	private final AccountMapper accountMapper;
	private final FundMapper fundMapper;
	private final WithdrawMapper withdrawMapper;
	private final UserMapper userMapper;
	private final UserService userService;
	
	private final IamportClient iamportClient;
	
	@Value("${app.upload.public-path:/files}")
	private String publicPath;
	
	
	
	
	
	// 계좌 목록 조회 (관리자)
	public Page<AccountReadResponseDto> readAccountPage(String field, String keyword, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
			}
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String sortField = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, sortField), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 계좌 목록 조회
		Page<Account> accountPage = null;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			
			if (hasKeyword) {
				switch (field.toLowerCase()) {
				    case "name":
				        accountPage = accountRepository.findAllByUserNameContainingIgnoreCase(keyword, pageable);
				        break;
				    case "username":
				        accountPage = accountRepository.findAllByUserUsernameContainingIgnoreCase(keyword, pageable);
				        break;
				}
			}
			else {
				accountPage = accountRepository.findAll(pageable);
			}
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_READ_FAIL);
		}
		
		// 계좌 목록 응답 구성
		List<AccountReadResponseDto> accountDtoList = new ArrayList<AccountReadResponseDto>();
		for (Account account : accountPage) {
			AccountReadResponseDto accountDto = accountMapper.toReadResponseDto(account);
			accountDtoList.add(accountDto);
		}
		
		return new PageImpl<>(accountDtoList, pageable, accountPage.getTotalElements());
	}
	
	
	
	
	
	// 계좌 상세 조회
	public AccountReadResponseDto readAccountById(int id) {
		
		// 입력값 검증
		if (id < 0) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 계좌 상세 조회
		Account account;
		try {
			account = accountRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (account.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
				if (account.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != account.getUser().getId()) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != account.getUser().getId()) {
				throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
			}
		}
		
		return accountMapper.toReadResponseDto(account);
	}
	
	
	
	
	
	// 사용자의 계좌 상세 조회
	public AccountReadResponseDto readAccountByUserId(int userId) {

		// 입력값 검증
		if (userId < 0) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != userId) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
		}
		
		// 계좌 조회
		Account account;
		try {
			account = accountRepository.findByUserId(userId);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND);
		}
		
		// 권한 확인
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (account.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
				if (account.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != account.getUser().getId()) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != account.getUser().getId()) {
				throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
			}
		}
		
		return accountMapper.toReadResponseDto(account);
	}
	
	
	
	
	
	// 계좌 작성
	public AccountCreateResponseDto createAccount(AccountCreateRequestDto accountRequestDto) {
		
		// 입력값 검증
		if (accountRequestDto == null 
				|| accountRequestDto.getBank() == null 
				|| accountRequestDto.getAccountnum() == null
				|| accountRequestDto.getName() == null
				|| accountRequestDto.getUserId() < 0) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != accountRequestDto.getUserId()) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
		}
		
		// 계좌 저장
		Account userAccount = accountMapper.toEntity(accountRequestDto);
		try {
			userAccount = accountRepository.save(userAccount);
			return accountMapper.toCreateResponseDto(userAccount);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_CREATE_FAIL);
		}
	}
	
	
	
	
	
	// 계좌 수정
	public AccountUpdateResponseDto updateAccount(AccountUpdateRequestDto accountRequestDto) {

		// 입력값 검증
		if (accountRequestDto == null
				|| accountRequestDto.getUserId() < 0
				|| accountRequestDto.getBank() == null
				|| accountRequestDto.getAccountnum() == null
				|| accountRequestDto.getName() == null) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_INVALID_INPUT);
		}
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != accountRequestDto.getUserId()) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
		}
		
		// 계좌 조회
		Account account;
		try {
			account = accountRepository.findByUserId(accountRequestDto.getUserId());
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_NOT_FOUND);
		}
		
		// 권한 확인
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (account.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
				if (account.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != account.getUser().getId()) {
					throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != account.getUser().getId()) {
				throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_FORBIDDEN);
			}
		}
		
		// 값 설정
		account.setBank(accountRequestDto.getBank());
		account.setAccountnum(accountRequestDto.getAccountnum());
		account.setName(accountRequestDto.getName());

		// 계좌 수정
		try {
			account = accountRepository.save(account);
			return accountMapper.toUpdateResponseDto(account);
		}
		catch (Exception e) {
			throw new ApiException(UserAccountErrorCode.USER_ACCOUNT_UPDATE_FAIL);
		}
	}
	
	
	
	
	
	// TODO : 계좌 삭제
	
	
	
	
	
	// TODO : 결제 목록 조회
	
	
	
	
	
	// 결제 작성 (포인트 충전)
	public PaymentCreateResponseDto createPayment(PaymentCreateRequestDto paymentRequestDto) {
		
		// 입력값 검증
		if (paymentRequestDto == null
				|| paymentRequestDto.getImpUid() == null
				|| paymentRequestDto.getMerchantUid() == null
				|| paymentRequestDto.getAmount() < 0
				|| paymentRequestDto.getName() == null
				|| paymentRequestDto.getTel() == null
				|| paymentRequestDto.getUserId() < 0) {
			throw new ApiException(PaymentErrorCode.PAYMENT_INVALID_INPUT);
		}
		
		// 포트원 결제 조회
		IamportResponse<Payment> iamportResponse;
		try {
			iamportResponse =  iamportClient.paymentByImpUid(paymentRequestDto.getImpUid());
		}
		catch (Exception e) {
			throw new ApiException(PaymentErrorCode.PAYMENT_LOOKUP_FAIL);
		}
		Payment payment = iamportResponse.getResponse();
		
		// 포인트 검증
		if (iamportResponse.getResponse().getAmount().compareTo(BigDecimal.valueOf(paymentRequestDto.getAmount())) != 0) {
			throw new ApiException(PaymentErrorCode.PAYMENT_INVALID_POINT);
		}
		
		// 결제 상태 검증 
        if (!"paid".equals(payment.getStatus())) {
            throw new ApiException(PaymentErrorCode.PAYMENT_INVALID_STATUS);
        }
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != paymentRequestDto.getUserId()) {
					throw new ApiException(PaymentErrorCode.PAYMENT_FORBIDDEN);
				}
			}
		}
		
		// 포인트 충전
		User user = userMapper.toEntity(userService.readUserById(paymentRequestDto.getUserId()));
		user.setPoint(user.getPoint() + paymentRequestDto.getAmount());
		UserUpdateRequestDto userRequestDto = userMapper.toUpdateRequestDto(user);
		UserUpdateResponseDto userResponseDto = userService.updateUser(userRequestDto);

		// TODO : 결제 응답 구성
		PaymentCreateResponseDto paymentResponseDto = new PaymentCreateResponseDto();
		paymentResponseDto.setUsername(userResponseDto.getUsername());
		
		return paymentResponseDto;
	}
	
	
	
	
	
	// 후원 목록 조회 (관리자)
	public Page<FundReadResponseDto> readFundPage(String keyword, String sort, Pageable pageable) {
		
		// 입력값 검증
		if (pageable == null) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
			}
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String field = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, field), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
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
	public Page<FundReadResponseDto> readFundPageByUserId(int userId, Pageable pageable) {

		// 입력값 검증
		if (userId < 0 || pageable == null) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != userId) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
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
	// TODO : 수정 (제거) userDto.revenue
	public int sumFundByUserId(int userId) {
		
		// 입력값 검증
		if (userId < 0) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}

		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
				if (userService.readUserById(userId).getRole().equals(Role.ROLE_ADMIN.name()) && currentUser.getId() != userId) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != userId) {
				throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
			}
		}
		
		// 후원 총합 조회
		try {
			 return fundRepository.sumPointByFundeeId(userId);
		}
		catch (Exception e) {
			throw new ApiException(FundErrorCode.FUND_SUM_FAIL);
		}
	}
	
	
	
	
	
	// 후원 작성
	public FundCreateResponseDto createFund(FundCreateRequestDto fundRequestDto) {

		// 입력값 검증
		if (fundRequestDto == null
				|| fundRequestDto.getFunderId() < 0
				|| fundRequestDto.getFundeeId() < 0
				|| fundRequestDto.getRecipeId() < 0
				|| fundRequestDto.getPoint() < 0) {
			throw new ApiException(FundErrorCode.FUND_INVALID_INPUT);
		}
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != fundRequestDto.getFunderId()) {
					throw new ApiException(FundErrorCode.FUND_FORBIDDEN);
				}
			}
		}
		
		// 사용자 조회
		UserReadResponseDto funderDto = userService.readUserById(fundRequestDto.getFunderId());
		UserReadResponseDto fundeeDto = userService.readUserById(fundRequestDto.getFundeeId());
		
		// 입력값 검증
		if (funderDto.getPoint() < fundRequestDto.getPoint()) {
			throw new ApiException(FundErrorCode.FUND_INVALID_POINT);
		}
		
		// 후원 저장
		try {
			Fund fund = fundMapper.toEntity(fundRequestDto);
			fund = fundRepository.save(fund);
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
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
			}
		}
		
		// 정렬
		Sort order = Sort.by(Sort.Order.desc("id"));
		if (sort != null && !sort.isBlank()) {
			String sortField = sort.split("-")[0];
			Direction direction = "asc".equals(sort.split("-")[1]) ? Direction.ASC : Direction.DESC;
			order = Sort.by(new Order(direction, sortField), Order.desc("id"));
		}
		pageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), order);
		
		// 출금 목록 조회
		Page<Withdraw> withdrawPage = null;
		try {
			boolean hasKeyword = keyword != null && !keyword.isBlank();
			boolean hasStatus = status != null && status != 0;
			
			if (hasStatus) {
				if (hasKeyword) {
					switch (field.toLowerCase()) {
					    case "name":
					    	withdrawPage = withdrawRepository.findAllByStatusAndUserNameContainingIgnoreCase(status, keyword, pageable);
					        break;
					    case "username":
					    	withdrawPage = withdrawRepository.findAllByStatusAndUserUsernameContainingIgnoreCase(status, keyword, pageable);
					        break;
					}
				}
				else {
					withdrawPage = withdrawRepository.findAllByStatus(status, pageable);
				}
			}
			else {
				if (hasKeyword) {
					switch (field.toLowerCase()) {
					    case "name":
					    	withdrawPage = withdrawRepository.findAllByUserNameContainingIgnoreCase(keyword, pageable);
					        break;
					    case "username":
					    	withdrawPage = withdrawRepository.findAllByUserUsernameContainingIgnoreCase(keyword, pageable);
					        break;
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
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != userId) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
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
	// TODO : 반환 변경
	public WithdrawReadResponseDto createWithdraw(WithdrawCreateRequestDto withdrawRequestDto) {

		// 입력값 검증
		if (withdrawRequestDto == null
				|| withdrawRequestDto.getUserId() == 0
				|| withdrawRequestDto.getPoint() == 0) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_INPUT);
		}
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != withdrawRequestDto.getUserId()) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
			}
		}
		
		// 사용자 조회
		UserReadResponseDto userDto = userService.readUserById(withdrawRequestDto.getUserId());
		
		// 입력값 검증
		if (userDto.getPoint() < withdrawRequestDto.getPoint()) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_INVALID_POINT);
		}
		
		// 계좌 설정
		AccountReadResponseDto accountDto = readAccountByUserId(withdrawRequestDto.getUserId());
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
		
		// 본인 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (!userService.readUserByUsername(username).getRole().equals(Role.ROLE_ADMIN.name())) {
				if (userService.readUserByUsername(username).getId() != withdrawDto.getUserId()) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
			}
		}

		// 출금 조회
		Withdraw withdraw;
		try {
			withdraw = withdrawRepository.findById(withdrawDto.getId());
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_NOT_FOUND);
		}
		
		// 권한 확인
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (withdraw.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
				if (withdraw.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != withdraw.getUser().getId()) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
			}
			else if (currentUser.getRole().equals(Role.ROLE_USER.name()) && currentUser.getId() != withdraw.getUser().getId()) {
				throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
			}
		}
		
		// 값 설정
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
			AccountReadResponseDto accountDto = readAccountById(withdrawDto.getId());
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
		
		// 출금 조회
		Withdraw withdraw;
		try {
			withdraw = withdrawRepository.findById(id);
		}
		catch (Exception e) {
			throw new ApiException(WithdrawErrorCode.WITHDRAW_NOT_FOUND);
		}
		
		// 권한 확인
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		UserReadResponseDto currentUser = userService.readUserByUsername(username);
		if (!currentUser.getRole().equals(Role.ROLE_SUPER_ADMIN.name())) {
			if (currentUser.getRole().equals(Role.ROLE_ADMIN.name())) {
				if (withdraw.getUser().getRole().equals(Role.ROLE_SUPER_ADMIN)) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
				if (withdraw.getUser().getRole().equals(Role.ROLE_ADMIN) && currentUser.getId() != withdraw.getUser().getId()) {
					throw new ApiException(WithdrawErrorCode.WITHDRAW_FORBIDDEN);
				}
			}
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
