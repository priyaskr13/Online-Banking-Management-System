package com.mcaproject.onlinebankingmanagement.Entity;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.annotation.Transient;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;


@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class CustomerEntity {

	
	public CustomerEntity() {
		super();
	}

	public CustomerEntity(Long accountNumber, String name,
			@Min(value = 18, message = "Age must be at least 18 years") int age, Long aadharNumber, String email,
			String password, String mobile, String address, BranchEntity branch, String oTP,
			List<TransactionEntity> transactionAccount, BigDecimal balance) {
		super();
		this.accountNumber = accountNumber;
		this.name = name;
		this.age = age;
		this.aadharNumber = aadharNumber;
		this.email = email;
		this.password = password;
		this.mobile = mobile;
		this.address = address;
		this.branch = branch;
		OTP = oTP;
		this.transactionAccount = transactionAccount;
		this.balance = balance;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_name_account_number")
	@SequenceGenerator(name = "seq_name_account_number", sequenceName = "seq_name_account_number", allocationSize = 1)
	private Long accountNumber;
	
	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	@Min(value = 18, message = "Age must be at least 18 years")
	private int age;
	
	@Column(nullable = false)
	private Long aadharNumber;
	
	@Column(nullable = false, unique=true)
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String mobile;
	
	@Column(nullable = false)
	private String address;
	
	@ManyToOne
	@JoinColumn(name = "branch_code")
	private BranchEntity branch;
	
	@Transient
	private String OTP;
	
	@OneToMany(mappedBy = "customer")
	private List<TransactionEntity> transactionAccount;

	@Column(nullable=false, precision = 10, scale = 2)
	private BigDecimal balance = BigDecimal.ZERO;
	
	
	
	public List<TransactionEntity> getTransactionAccount() {
		return transactionAccount;
	}

	public void setTransactionAccount(List<TransactionEntity> transactionAccount) {
		this.transactionAccount = transactionAccount;
	}

	public Long getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Long accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAge() {
		return age;
	}


	public void setAge(int age) {
		this.age = age;
	}

	public Long getAadharNumber() {
		return aadharNumber;
	}

	public void setAadharNumber(Long aadharNumber) {
		this.aadharNumber = aadharNumber;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public BranchEntity getBranch() {
		return branch;
	}

	public void setBranch(BranchEntity branch) {
		this.branch = branch;
	}

	public String getOTP() {
		return OTP;
	}

	public void setOTP(String oTP) {
		OTP = oTP;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	
}
