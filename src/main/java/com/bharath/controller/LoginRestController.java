package com.bharath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharath.service.IUserService;

@RestController
public class LoginRestController {

	@Autowired
	private IUserService userService;
	
	@PostMapping("/login")
	public String userLogin(@RequestParam String emailId, @RequestParam String pwd) {
		return userService.loginCheck(emailId, pwd);
	}
}
