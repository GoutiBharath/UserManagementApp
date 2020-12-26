package com.bharath.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class User {

	@GeneratedValue
	@Id
	private Integer userId;
	private String firstName;
	private String lastName;
	private String emailId;
	private String phoneNo;
	private Date dateOfBirth;
	private Integer country;
	private Integer state;
	private Integer city;
	private String password;
	private String accountStatus;
}
