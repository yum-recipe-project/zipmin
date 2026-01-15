package com.project.zipmin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAccountCreateResponseDto {
	
	private int id;
    private String bank;
    private String accountnum;
    private String name;
    private int userId;
    
}