package com.project.zipmin.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminViewController {
	
	@GetMapping("/admin/home.do")
	public String adminHome() {
		return "admin/home";
	}
	
	@GetMapping("/admin/login.do")
	public String adminLogin() {
		return "admin/login";
	}
	
	@GetMapping("/admin/register.do")
	public String adminRegister() {
		return "admin/register";
	}
	
	@GetMapping("/admin/button.do")
	public String adminButtons() {
		return "admin/ui-buttons";
	}
	
	@GetMapping("/admin/alert.do")
	public String adminAlerts() {
		return "admin/ui-alerts";
	}
	
	@GetMapping("/admin/card.do")
	public String adminCard() {
		return "admin/ui-card";
	}
	
	@GetMapping("/admin/form.do")
	public String adminForms() {
		return "admin/ui-forms";
	}
	
	@GetMapping("/admin/typography.do")
	public String adminTypography() {
		return "admin/ui-typography";
	}
	
	@GetMapping("/admin/icon.do")
	public String adminIcons() {
		return "admin/ui-icons";
	}
	
	@GetMapping("/admin/sample.do")
	public String adminSample() {
		return "admin/sample";
	}
}
