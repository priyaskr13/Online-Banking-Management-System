package com.mcaproject.onlinebankingmanagement.Entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
public class TransactionEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_name_transaction")
	@SequenceGenerator(name = "seq_name_transaction", sequenceName = "seq_name_transaction", allocationSize = 1)
	int TransactionID;
	
	@ManyToOne
	@JoinColumn(name = "account_number", nullable=false) 
	private CustomerEntity customer;
	

	@Column
	private Long fromAccountNumber;
	
	@Column(name="transaction_type", nullable=false)
	@Enumerated(EnumType.STRING)
	private TransactionType transactionType;
	
	@Column(nullable=false)
	private LocalDateTime timestamp;
	
	@Column(nullable=false, precision = 10, scale = 2)
	private BigDecimal amount;
	
	
	@Column
	private String Description;
	
	public int getTransactionID() {
		return TransactionID;
	}


	public void setTransactionID(int transactionID) {
		TransactionID = transactionID;
	}


	public CustomerEntity getCustomer() {
		return customer;
	}

	

	public void setCustomer(CustomerEntity customer) {
		this.customer = customer;
	}


	public TransactionType getTransactionType() {
		return transactionType;
	}


	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}


	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	
	
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}


	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}


	public String getDescription() {
		return Description;
	}



	public void setDescription(String description) {
		Description = description;
	}


	public Long getFromAccountNumber() {
		return fromAccountNumber;
	}


	public void setFromAccountNumber(Long fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}


	public enum TransactionType {
		DEPOSIT,
		WITHDRAWAL,
		TRANSFER
	}
	

}

