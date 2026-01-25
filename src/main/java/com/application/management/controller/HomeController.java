package com.application.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class HomeController {

	@GetMapping("/")
	public String getLandingPage() {
		return "index";
	}
	
	@GetMapping("/dashboard")
	public String getDashBoard() {
		return "dashboard";
	}
	
	@GetMapping("/login")
	public String getLogin() {
		return "login";
	}
	
}
