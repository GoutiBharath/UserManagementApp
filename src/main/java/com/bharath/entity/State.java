package com.bharath.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class State {

	@GeneratedValue
	@Id
	private Integer stateId;
	private String stateName;
	private Integer countryId;
}
