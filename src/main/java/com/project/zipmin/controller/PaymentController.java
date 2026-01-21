package com.project.zipmin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.project.zipmin.api.ApiResponse;
import com.project.zipmin.api.PaymentSuccessCode;
import com.project.zipmin.dto.PointCreateRequestDto;
import com.project.zipmin.dto.UserPointReadResponseDto;
import com.project.zipmin.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;

import io.jsonwebtoken.io.IOException;

@RestController
public class PaymentController {
	
	@Autowired
    private PaymentService paymentService;

    //토큰 발급용
    private final IamportClient iamportClientApi;
    
    public PaymentController() {
        this.iamportClientApi = new IamportClient("0422126237208654",
                "fAHT2Uhecoy92LTrXa10VVCbNJTSPkkQFiDR83iheJpIay45ahGWX8OhfQ1gDAf1lJkJHNiHq6BMXM7U");
    }
	
    public IamportResponse<Payment> paymentLookup(String impUid) throws IamportResponseException, IOException, java.io.IOException {
        return iamportClientApi.paymentByImpUid(impUid);
    }
    
    


    // 포인트 결제/충전
    @PostMapping("/users/{id}/point")
    public ResponseEntity<?> postUserPoint(
    		@PathVariable int id,
    		@RequestBody PointCreateRequestDto pointCreateRequestDto) throws IamportResponseException, IOException, java.io.IOException{
    	
    	// TODO : 로그인 여부 확인
    	
    	// 포트원 실제 결제내역 조회
        IamportResponse<Payment> iamportResponse = paymentLookup(pointCreateRequestDto.getImpUid());
        
        // 내역 검증 및 포인트 충전
        UserPointReadResponseDto pointCreateResponseDto = paymentService.verifyIamport(iamportResponse, pointCreateRequestDto);
        System.err.println("포인트충전완료:" + pointCreateResponseDto);
        
        return ResponseEntity.status(PaymentSuccessCode.PAYMENT_COMPLETE_SUCCESS.getStatus())
				.body(ApiResponse.success(PaymentSuccessCode.PAYMENT_COMPLETE_SUCCESS, pointCreateResponseDto));
    }
    

	
	
	
}
