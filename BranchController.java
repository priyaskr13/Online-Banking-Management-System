package com.mcaproject.onlinebankingmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mcaproject.onlinebankingmanagement.Entity.BranchEntity;
import com.mcaproject.onlinebankingmanagement.Service.BranchService;

@Controller
@RequestMapping("/branch")
public class BranchController {
	
	@Autowired
	private BranchService branchService;
	
	
	@GetMapping("/getallbranches")
	public String getAllBranches(Model model) {
		List<BranchEntity> allBranches = branchService.getAllBranches();
		model.addAttribute("allBranches", allBranches);
		return "/branch/allBranchesList";
	}
	
	
	@GetMapping("/update/{branchCode}")
	public String UpdateBranch(@PathVariable("branchCode") Long branchCode, Model model) {
		BranchEntity branchupdate=branchService.findBranchByID(branchCode);
		model.addAttribute("branchupdate", branchupdate);
		return "/branch/updateBranch";
	}
	
	
	@PostMapping("/update/{branchCode}")
	public String SaveUpdateBranch(@PathVariable("branchCode") Long branchCode, @ModelAttribute("branchupdate") BranchEntity updatedBranch, Model model) {
		BranchEntity branch = branchService.updateBranch(branchCode, updatedBranch);
		model.addAttribute("BranchUpdateMessage", "Branch with code : " +branchCode + " has been updated Successfully");
		model.addAttribute("branch", branch);
		
		List<BranchEntity> allBranches = branchService.getAllBranches();
		model.addAttribute("allBranches", allBranches);
		
		return "/branch/allBranchesList";
	}
	
}
