package com.bharath.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Country {

	@GeneratedValue
	@Id
	private Integer countryId;
	private String countryName;
}
