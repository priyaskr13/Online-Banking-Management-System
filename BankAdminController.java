package com.mcaproject.onlinebankingmanagement.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mcaproject.onlinebankingmanagement.Entity.CustomerEntity;
import com.mcaproject.onlinebankingmanagement.Entity.TransactionEntity;
import com.mcaproject.onlinebankingmanagement.Entity.BranchEntity;
import com.mcaproject.onlinebankingmanagement.Repository.BranchRepository;
import com.mcaproject.onlinebankingmanagement.Repository.CustomerRegistrationRepository;
import com.mcaproject.onlinebankingmanagement.Repository.TransactionRepository;
import com.mcaproject.onlinebankingmanagement.Service.BankAdminService;
import com.mcaproject.onlinebankingmanagement.Service.customerService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
public class BankAdminController {
	
	@Autowired
	private BranchRepository branchRepository;
	
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired BankAdminService bankAdminService;
	
	@Autowired
	private CustomerRegistrationRepository customerRepository;
	
	@Autowired
	private customerService customerService;
	
	
	
	@PostMapping("/post-admin-login")
	public String viewAdminPage(@RequestParam("username") String username, @RequestParam("password") String password, 
			HttpServletRequest request, RedirectAttributes redirAttri, HttpSession adminsession) {
		
		if(username.equals("SYS-ADMIN") && password.equals("password@123")) {
			adminsession.setAttribute("username", username);
			return "redirect:/bankadmin";
		}
		else {
			redirAttri.addFlashAttribute("loginfailedmsg", "Login failed. Incorrect Username or Password");
			return "redirect:/bankadmin/login";
		}
		
		
	}
	
	
	@GetMapping("/adminlogout")
	public String logout(HttpSession adminsession) {
	    adminsession.invalidate();
	    return "/homepage/index";
	}
	
	
	
	
	@GetMapping("/branch/add")
	public String addBranch(Model model) {
		model.addAttribute("branch", new BranchEntity());
		return "branch/addBranch";
	}
	
	
	@PostMapping("/branch/add")
	public String saveBranchToDB(@ModelAttribute("branch") BranchEntity branchob, Model model, RedirectAttributes reAttri) {
		try {
			branchRepository.save(branchob);
			reAttri.addFlashAttribute("message", "Branch Successfully saved");
			return "redirect:/branch/add";
		} catch (Exception e){
			reAttri.addFlashAttribute("errorMessage", "Error : " + e.getMessage());
			return "redirect:/branch/add";
		}
		
	}
	
	/* addbranch ends, 
	 * registration code starts */
	
	@GetMapping("/registration")
	public String customerRegistration(Model model) {
		model.addAttribute("customer", new CustomerEntity());
		model.addAttribute("allbranches", branchRepository.findAll());
		return "/customer/Registration";
	}
	
	@PostMapping("/registration")
	public String saveCustomerToDB(@ModelAttribute("customer") CustomerEntity customerob, Model model, RedirectAttributes reAttri, BindingResult result) {
		
		 if (customerob.getAge() < 18) {
		        reAttri.addFlashAttribute("ageerrorMessagecust", "Age should not be less than 18");
		        return "redirect:/registration";
		    }
		 
		 CustomerEntity emailexists = customerRepository.findByEmail(customerob.getEmail());
		 if(emailexists!=null) {
			 reAttri.addFlashAttribute("emailMessagecust", "Email Already Exists!!!");
			 return "redirect:/registration";
		 }
		 
		try {
			customerRepository.save(customerob);
			reAttri.addFlashAttribute("messagecust", "customer Successfully saved");
			return "redirect:/registration";
		} catch (Exception e){
			reAttri.addFlashAttribute("ageerrorMessagecust", "Error : " + e.getMessage());
			return "redirect:/registration";
		}
		
	}
	
	@GetMapping("/getallcustomers")
	public String getAllCustomers(Model model) {

		List<CustomerEntity> allcustomers = customerService.getAllCustomers();
		model.addAttribute("allcustomers", allcustomers);
		return "customer/ViewAllCustomers";
		
	}

	@GetMapping("/customer/transactionOfParticularCustomer")
	public String getCustomerTransactionDetails(@RequestParam("accnum") Long accnum, Model model) {
		List<TransactionEntity> transactions = transactionRepository.findAllByFromAccountNumberOrderByTimestampDesc(accnum);

		model.addAttribute("customerTransactions", transactions);
		return "/customer/ParticularCustomerTransactions";	
	}
	
	@GetMapping("/customer/delete/{accnum}")
	public String deleteCustomer(@PathVariable("accnum") Long accnum, Model model) {
		customerService.deleteCustomer(accnum);
		model.addAttribute("CustomerDeletedMessage", "Customer with Account Number " + accnum + " has been sucessfully deleted");
		
		List<CustomerEntity> allcustomers = customerService.getAllCustomers();
		model.addAttribute("allcustomers", allcustomers);
		
		return "customer/ViewAllCustomers";
	}
	
	@GetMapping("/customer/getalltransactions")
	public String getAllTrasactions(Model model) {
		List<TransactionEntity> getalltransactions = bankAdminService.getAllTransactionsDesc();
		model.addAttribute("getalltransactions", getalltransactions);
		return "/bankadmin/getAllTransactions";
	}
	
	
}
