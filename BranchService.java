package com.mcaproject.onlinebankingmanagement.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mcaproject.onlinebankingmanagement.Entity.BranchEntity;
import com.mcaproject.onlinebankingmanagement.Repository.BranchRepository;

@Service
public class BranchService {

	@Autowired
	private BranchRepository branchRepository;
	
	
	public List<BranchEntity> getAllBranches(){
		return branchRepository.findAll();
	}

	
	public void deleteBranchByID(Long branchCode) {
		branchRepository.deleteById(branchCode);
	}
	

	public BranchEntity findBranchByID(Long branchCode) {
		return branchRepository.findById(branchCode).orElse(null);
	}
	
	public BranchEntity updateBranch(Long branchCode, BranchEntity updatedBranch) {
		BranchEntity branch = branchRepository.findById(branchCode).orElse(null);
		branch.setManagerName(updatedBranch.getManagerName());
		branch.setEmployeeCount(updatedBranch.getEmployeeCount());
		branchRepository.save(branch);
		return branch;
	}
}
