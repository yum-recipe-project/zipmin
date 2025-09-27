package com.project.zipmin.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.project.zipmin.api.ApiException;
import com.project.zipmin.api.UserErrorCode;
import com.project.zipmin.dto.MailDto;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MailService {
	
	private final JavaMailSender mailSender;
	
	@Value("${spring.mail.username}")
	private String from;
	
	public void sendEmail(MailDto mailDto) {
		
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
			mimeMessageHelper.setFrom(from);
			mimeMessageHelper.setTo(mailDto.getTo());
			mimeMessageHelper.setSubject(mailDto.getSubject());
			mimeMessageHelper.setText(mailDto.getContent(), true);
			
			mailSender.send(mimeMessage);
		}
		catch (Exception e) {
			throw new ApiException(UserErrorCode.USER_SEND_EMAIL_FAIL);
		}
		
	}

}
