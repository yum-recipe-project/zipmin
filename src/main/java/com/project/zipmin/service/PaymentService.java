package com.project.zipmin.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.PaymentErrorCode;
import com.project.zipmin.dto.PointCreateRequestDto;
import com.project.zipmin.dto.PointCreateResponseDto;
import com.project.zipmin.dto.UserPointReadResponseDto;
import com.project.zipmin.mapper.UserMapper;
import com.project.zipmin.repository.UserRepository;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	
	@Autowired
	private UserService userService; 
	
	// 결제금액 확인 및 포인트 충전
	public UserPointReadResponseDto verifyIamport(IamportResponse<Payment> iamportResponse, PointCreateRequestDto dto) {
		
		// 실제 결제 금액
        BigDecimal actualAmount = iamportResponse.getResponse().getAmount();
        
        // 결제 선택한 금액
        int requestedAmount = dto.getAmount();
        
        if (actualAmount.compareTo(BigDecimal.valueOf(requestedAmount)) != 0) {
        	throw new ApiException(PaymentErrorCode.PAYMENT_AMOUNT_MISMATCH);
        }
        
        // 포인트 충전
        UserPointReadResponseDto responsedto = userService.addPointToUser(dto.getUserId(), requestedAmount);
		
		return responsedto;
	}
	
}
	


	
	