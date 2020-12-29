package com.bharath.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharath.service.IUserService;

@RestController
public class UnlockAccountRestController {

	@Autowired
	private IUserService userService;
	
	@PostMapping("/unlockUserAccount")
	public String unlockUserAccount(@RequestParam String emailId, 
			@RequestParam String tempPwd, @RequestParam String newPwd) {
		if(userService.isTempPwdValid(emailId, tempPwd)) {
			userService.unlockAccount(emailId, newPwd);
			return "Account Unlocked. Please proceed with Login";
		}
		return "Please enter valid Temporary password";
	}

}
