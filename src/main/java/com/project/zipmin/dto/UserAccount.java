package com.project.zipmin.dto;

import lombok.Data;

@Data
public class UserAccount {
	private int id;
	private String bank; // 은행명
	private String accountnum; // 계좌번호
	private String name; // 예금주
	private String user_id;
}
