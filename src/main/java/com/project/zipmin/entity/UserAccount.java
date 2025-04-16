package com.project.zipmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
@Table(name = "USER_ACCOUNT")
public class UserAccount {
	@Id
	@GeneratedValue(generator = "seq_user_account_id")
	@SequenceGenerator(name = "seq_user_account_id", sequenceName = "SEQ_USER_ACCOUNT_ID", allocationSize = 1)
	private int id;
	private String bank;
	private String accountnum;
	private String name;
	
	// private String userId;
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
}
