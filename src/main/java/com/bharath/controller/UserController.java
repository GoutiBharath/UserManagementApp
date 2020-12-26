package com.bharath.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bharath.entity.User;
import com.bharath.service.IUserService;

@RestController
public class UserController {

	@Autowired
	private IUserService userService;
	
	@GetMapping("/getCountries")
	public Map<Integer, String> findCountries(){
		return userService.findCountries();
	}
	
	@GetMapping("/getStates/{countryId}")
	public Map<Integer, String> findStates(@PathVariable Integer countryId){
		return userService.findStates(countryId);
	}
	
	@GetMapping("/getCities/{stateId}")
	public Map<Integer, String> findCities(@PathVariable Integer stateId){
		return userService.findCities(stateId);
	}
	
	@PostMapping("/registration")
	public boolean userRegistration(@RequestBody User user) {
		if(userService.isEmailUnique(user.getEmailId())) {
			return userService.saveUser(user);
		}
		return false;
	}
	
	@PostMapping("/unlockUserAccount")
	public String unlockUserAccount(@RequestParam String emailId, @RequestParam String tempPwd, 
			@RequestParam String newPwd) {
		if(userService.isTempPwdValid(emailId, tempPwd)) {
			userService.unlockAccount(emailId, newPwd);
			return "Account Unlocked. Please proceed with Login";
		}
		return "Please enter valid Temporary password";
	}
	
	@PostMapping("/login")
	public String userLogin(@RequestParam String emailId, @RequestParam String pwd) {
		return userService.loginCheck(emailId, pwd);
	}
	
	@GetMapping("/forgotPassword")
	public String getUserPassword(@RequestParam String emailId) {
		if(userService.forgotPassword(emailId) != null) {
			return userService.forgotPassword(emailId);
		}
		return "The User doesn't exists";
	}
	
	
}
