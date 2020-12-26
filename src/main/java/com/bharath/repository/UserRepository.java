package com.bharath.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bharath.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Serializable> {

	User findByEmailId(String emailId);
	User findByEmailIdAndPassword(String emailId, String password);

}
