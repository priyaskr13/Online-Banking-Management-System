package com.mcaproject.onlinebankingmanagement.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.mcaproject.onlinebankingmanagement.Entity.CustomerEntity;
import com.mcaproject.onlinebankingmanagement.Repository.CustomerRegistrationRepository;
import com.mcaproject.onlinebankingmanagement.Service.customerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class IndexPageController {
	
	
	@Autowired
	private customerService customerService;

	
	
    @GetMapping("/")
    public String home() {
        return "/homepage/index";
    }
    
    @GetMapping("/bankadmin/login")
	public String getAdminLoginPage() {
		return "bankadmin/AdminLogin";
	}
    
    @GetMapping("/bankadmin")
    public String bankadmin() {
        return "/homepage/bankadmin";
    }
    
    @GetMapping("/customer")
    public String customer() {
        return "/customer/CustomerLogin";
    }
    
    @GetMapping("/aboutus")
    public String aboutus() {
        return "/homepage/aboutus";
    }
    
    @GetMapping("/personalBanking")
    public String personalBanking(HttpSession session, Model model) {
    	
    	
    	String Name = (String)session.getAttribute("customerName");
    	Long AccountNumber = (Long)session.getAttribute("customerAccnum");
    	
    	
    	CustomerEntity customer = customerService.getCustomerByAccountNumber(AccountNumber);
    	 BigDecimal balance = customer.getBalance();

        	
    	model.addAttribute("CustomerName", Name);
    	model.addAttribute("CustomerAccountNumber", AccountNumber);
    	model.addAttribute("CustomerBalance", balance);
    	
    	
    	return "/homepage/customer";
    }
  
    @Autowired
    public void setCustomerService(customerService customerService) {
        this.customerService = customerService;
    }
}
