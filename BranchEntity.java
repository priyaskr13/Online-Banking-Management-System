package com.mcaproject.onlinebankingmanagement.Entity;

import jakarta.persistence.*;

@Entity
public class BranchEntity {

	
	public BranchEntity() {
		super();
	}

	public BranchEntity(Long branchCode, String branchName, String managerName, int employeeCount) {
		super();
		this.branchCode = branchCode;
		this.branchName = branchName;
		this.managerName = managerName;
		this.employeeCount = employeeCount;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_name")
	@SequenceGenerator(name = "seq_name", sequenceName = "seq_name", allocationSize = 1)
	private Long branchCode;
	
	@Column(nullable = false)
	private String branchName;

	@Column(nullable = false)
	private String managerName;
	
	@Column(nullable = false)
	private int employeeCount;
	
	public Long getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(Long branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public int getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(int employeeCount) {
		this.employeeCount = employeeCount;
	}
}
