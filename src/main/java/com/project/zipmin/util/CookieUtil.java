package com.project.zipmin.util;

import jakarta.servlet.http.Cookie;

public class CookieUtil {
	
	public static Cookie createCookie(String key, String token, int expiredS) {
		Cookie cookie = new Cookie(key, token);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(expiredS);
		return cookie;
	}
	
	public static Cookie deleteCookie(String key) {
		Cookie cookie = new Cookie(key, null);
		cookie.setHttpOnly(true);
		cookie.setPath("/");
		cookie.setMaxAge(0);
		return cookie;
	}
	
}