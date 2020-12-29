package com.bharath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharath.service.IUserService;

@RestController
public class ForgotPasswordRestController {

	@Autowired
	private IUserService userService;
	
	@GetMapping("/forgotPassword")
	public String getUserPassword(@RequestParam String emailId) {
		if(userService.forgotPassword(emailId) != null) {
			return userService.forgotPassword(emailId);
		}
		return "The User doesn't exists";
	}
}
