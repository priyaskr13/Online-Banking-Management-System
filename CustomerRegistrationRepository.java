package com.mcaproject.onlinebankingmanagement.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcaproject.onlinebankingmanagement.Entity.CustomerEntity;

import com.mcaproject.onlinebankingmanagement.Service.customerService;

@Repository
public interface CustomerRegistrationRepository extends JpaRepository <CustomerEntity, Long>{

	CustomerEntity findByEmail(String email); 
	
	CustomerEntity findByEmailAndPassword(String email, String password);
	
	
	                                                                                                          
}