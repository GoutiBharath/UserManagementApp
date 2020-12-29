package com.bharath.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bharath.entity.City;
import com.bharath.entity.Country;
import com.bharath.entity.State;
import com.bharath.entity.User;
import com.bharath.repository.CityRepository;
import com.bharath.repository.CountryRepository;
import com.bharath.repository.StateRepository;
import com.bharath.repository.UserRepository;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private CountryRepository countryRepo;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private CityRepository cityRepo;

	@Autowired
	private IEmailService emailService;

	@Override
	public Map<Integer, String> findCountries() {
		List<Country> countriesList = countryRepo.findAll();
		Map<Integer, String> countries = new HashMap<>();

		countriesList.forEach(country -> {
			countries.put(country.getCountryId(), country.getCountryName());
		});
		return countries;
	}

	@Override
	public Map<Integer, String> findStates(Integer countryId) {
		Map<Integer, String> states = new HashMap<>();
		List<State> statesList = stateRepo.findByCountryId(countryId);
		statesList.forEach(state -> {
			states.put(state.getStateId(), state.getStateName());
		});
		return states;
	}

	@Override
	public Map<Integer, String> findCities(Integer stateId) {
		Map<Integer, String> cities = new HashMap<>();
		List<City> citiesList = cityRepo.findByStateId(stateId);
		citiesList.forEach(city -> {
			cities.put(city.getCityId(), city.getCityName());
		});
		return cities;
	}

	@Override
	public boolean isEmailUnique(String emailId) {
		User userDetails = userRepo.findByEmailId(emailId);
		if (userDetails == null) {
			return true;
		}
		return false;
	}

	@Override
	public boolean saveUser(User user) {
		user.setPassword(passwordGenerator());
		user.setAccountStatus("LOCKED");
		User userObj = userRepo.save(user);

		String emailBody = getUnlockAccEmailBody(user);
		String subject = "UNLOCK Your Account | IES ";

		boolean isSent = emailService.sendAccountUnlockEmail(subject, emailBody, user.getEmailId());

		return userObj.getUserId() != null && isSent;
	}

	private String passwordGenerator() {
		String randomPassword = "";
		String alphaNumeric = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		Random randomPwd = new Random();
		for (int i = 0; i < 8; i++) {
			randomPassword = randomPassword + alphaNumeric.charAt(randomPwd.nextInt(alphaNumeric.length()));
		}
		return randomPassword;
	}

	// test case-1 : invalid email & pwd ==> INVALID_CREDENTIALs
	// test case-2 : valid email and pwd & acc is LOCKED ==> ACCOUNT_LOCKED
	// test case-3 : valid email, pwd & acc UNLOCKED ==> LOGIN_SUCCESS
	@Override
	public String loginCheck(String email, String pwd) {
		User userDetails = userRepo.findByEmailIdAndPassword(email, pwd);
		if (userDetails != null) {
			if (userDetails.getAccountStatus().equals("LOCKED")) {
				return "ACCOUNT_LOCKED";
			} else {
				return "LOGIN_SUCCESS";
			}
		}
		return "INVALID_CREDENTIALS";
	}

	// test case 1: User has given invalid temp-pwd ==> false
	// test case 2 : User has given valid temp-pwd ==> true
	@Override
	public boolean isTempPwdValid(String email, String tempPwd) {
		User userDetails = userRepo.findByEmailIdAndPassword(email, tempPwd);
		return userDetails.getUserId() != null;
	}

	@Override
	public boolean unlockAccount(String email, String newPwd) {
		User userDetails = userRepo.findByEmailId(email);
		userDetails.setPassword(newPwd);
		userDetails.setAccountStatus("UNLOCKED");
		try {
			userRepo.save(userDetails);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	// test case : 1 => User entered valid email -> return existing pwd
	// test case : 2 => User entered invalid email -> return null
	@Override
	public String forgotPassword(String email) {
		User userDetails = userRepo.findByEmailId(email);
		if (userDetails != null) {
			return userDetails.getPassword();
		}
		return null;
	}

	private String getUnlockAccEmailBody(User user) {

		StringBuffer sb = new StringBuffer("");

		try {

			File f = new File("unlock-acc-email-body.txt");

			FileReader fr = new FileReader(f);

			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();

			while (line != null) {
				if (line.contains("{FNAME}")) {
					line = line.replace("{FNAME}", user.getFirstName());
				}
				if (line.contains("{LNAME}")) {
					line = line.replace("{LNAME}", user.getLastName());
				}
				if (line.contains("${TEMP-PWD}")) {
					line = line.replace("{TEMP-PWD}", user.getPassword());
				}
				if (line.contains("{EMAIL}")) {
					line = line.replace("{EMAIL}", user.getEmailId());
				}
				sb.append(line);
				line = br.readLine();
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

}
