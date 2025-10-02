package com.project.zipmin.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class PasswordTokenUtil {
	
	private static final SecureRandom RANDOM = new SecureRandom();
	
	public static String createRawToken() {
		byte[] bytes = new byte[32];
		RANDOM.nextBytes(bytes);
		return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
	}
	
	public static String createHashToken(String raw) {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest(raw.getBytes(StandardCharsets.UTF_8));
		StringBuilder sb = new StringBuilder();
		for (byte b : digest) sb.append(String.format("%02x", b));
		return sb.toString();
	}

}