package com.project.zipmin.dto.fund;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountUpdateResponseDto {
	
	private int id;
    private String bank;
    private String accountnum;
    private String name;
    private int userId;
    
}