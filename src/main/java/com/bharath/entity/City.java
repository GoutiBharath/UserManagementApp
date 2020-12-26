package com.bharath.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class City {

	@GeneratedValue
	@Id
	private Integer cityId;
	private String cityName;
	private Integer stateId;
}
