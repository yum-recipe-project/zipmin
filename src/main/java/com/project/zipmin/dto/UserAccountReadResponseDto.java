package com.project.zipmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountReadResponseDto {
    private String bank;        // 은행명
    private String accountnum;  // 계좌번호
    private String name;        // 예금주명
}