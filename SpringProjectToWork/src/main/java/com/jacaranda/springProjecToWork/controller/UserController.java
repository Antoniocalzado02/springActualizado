package com.jacaranda.springProjecToWork.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.jacaranda.springProjecToWork.model.Users;
import com.jacaranda.springProjecToWork.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UserController {
	
	@Autowired
	UserService service;
	
	@GetMapping("/sign_up")
	public String signUp(Model model) {
		model.addAttribute("user",new Users());
		return "signup";
	}
	
	@GetMapping("/sign_up/submit")
	public String signUpSubmit(@ModelAttribute("user") Users user, HttpServletRequest request) {
		String siteUrl=request.getRequestURL().toString();
		siteUrl=siteUrl.replace(request.getServletPath(), "");
		user.setRole("USER");
		
		if(service.checkExist(user)) {
			try {
				service.add(user, siteUrl);
			}catch (UnsupportedEncodingException e) {
				return "error";
			}catch (MessagingException e) {
				return "error";
			}
			return "register_succes";
		}
		else {
			return "error";
		}
		
	}
	
	@GetMapping("/verify")
	public String verificar(@RequestParam(name="code") String code,
	@RequestParam(name="username") String username) {

	if(service.enableUser(code, username)) {
		return "verificado";
	}else {
	// System.out.println("error");
		return "error";
	}
	
	}

}
