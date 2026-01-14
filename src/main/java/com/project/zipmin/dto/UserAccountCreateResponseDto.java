package com.project.zipmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountCreateResponseDto {
	
    private String bank;
    private String accountnum;
    private String name;
    
}