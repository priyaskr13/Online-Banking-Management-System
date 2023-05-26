package com.mcaproject.onlinebankingmanagement.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mcaproject.onlinebankingmanagement.Entity.TransactionEntity;


@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {

	
	List<TransactionEntity> findTop10ByFromAccountNumberOrderByTimestampDesc(Long fromAccountNumber);
	
	List<TransactionEntity> findAllByFromAccountNumberOrderByTimestampDesc(Long fromAccountNumber);
	
	List<TransactionEntity> findByFromAccountNumber(Long fromAccountNumber);
	
	List<TransactionEntity> findAllByOrderByTimestampDesc();
	
}
