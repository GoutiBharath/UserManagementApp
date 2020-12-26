package com.bharath.service;

import java.util.Map;

import com.bharath.entity.User;

public interface IUserService {

	public Map<Integer, String> findCountries();
	public Map<Integer, String> findStates(Integer countryId);
	public Map<Integer, String> findCities(Integer stateId);
	public boolean isEmailUnique(String emailId);
	public boolean saveUser(User user);
	
	public String loginCheck(String email, String pwd);
	
	public boolean isTempPwdValid(String email, String tempPwd);
	public boolean unlockAccount(String email, String newPwd);
	
	public String forgotPassword(String email);
}
