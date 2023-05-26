package com.mcaproject.onlinebankingmanagement.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcaproject.onlinebankingmanagement.Repository.TransactionRepository;
import com.mcaproject.onlinebankingmanagement.Entity.TransactionEntity;

@Service
public class BankAdminService {
	
	@Autowired TransactionRepository transactionRepository;
	
	public List<TransactionEntity> getAllTransactionsDesc() {
		return transactionRepository.findAllByOrderByTimestampDesc();
	}
	
	
	
	
}
