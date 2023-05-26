package com.mcaproject.onlinebankingmanagement.Service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mcaproject.onlinebankingmanagement.Entity.CustomerEntity;
import com.mcaproject.onlinebankingmanagement.Entity.TransactionEntity;
import com.mcaproject.onlinebankingmanagement.Repository.CustomerRegistrationRepository;

@Service
public class customerService   {
	
	
	@Autowired
	private CustomerRegistrationRepository customerRegistrationRepository;
	
	
	
	public List<CustomerEntity> getAllCustomers(){
		return customerRegistrationRepository.findAll();
	}
	
	public void deleteCustomer(Long accnum) {
		customerRegistrationRepository.deleteById(accnum);
	}
	
	//to display customer name and account number in Customer homepage
	public CustomerEntity getCustomerByAccountNumber(Long accnum) {
		return customerRegistrationRepository.findById(accnum).orElse(null);
	}

} 
