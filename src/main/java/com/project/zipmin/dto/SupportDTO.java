package com.project.zipmin.dto;

import lombok.Data;

@Data
public class SupportDTO {
	private int support_idx;
	private String sponsor_ref; // 누가
	private String recipe_ref; // 어떤 레시피에
	private int point; // 몇 포인트를
	private String receiver_ref; // 누구한테
	// private boolean settle; // 정산여부
	// 후원 받은 내역에서 이미 정산 받은 내역을 보여줄지 안보여줄지에 따라 정산 여부 컬럼이 추가될 수도 있고 아닐 수도 있음
	
}
