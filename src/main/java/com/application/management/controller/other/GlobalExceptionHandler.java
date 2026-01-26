package com.application.management.controller.other;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.annotation.PostConstruct;

@ControllerAdvice
public class GlobalExceptionHandler {

	@PostConstruct
    public void init() {
        System.out.println("GlobalExceptionHandler loaded!");
    }
	
    @ExceptionHandler(Exception.class)
    public String handleAllExceptions(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "errorPage";
    }
}
