package com.project.zipmin.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "PASSWORD_TOKEN")
public class PasswordToken {

	@Id
	@GeneratedValue(generator = "seq_password_token_id")
	@SequenceGenerator(name = "seq_password_token_id", sequenceName = "SEQ_PASSWORD_TOKEN_ID", allocationSize = 1)
	private int id;
	
	private String token;
	private Date expiresAt;
	
	// private int userId;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID")
	private User user;
	
	public boolean isUsable() {
        return new Date().before(expiresAt);
    }
	
}
