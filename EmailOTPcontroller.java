package com.mcaproject.onlinebankingmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.mcaproject.onlinebankingmanagement.Entity.CustomerEntity;
import com.mcaproject.onlinebankingmanagement.Repository.CustomerRegistrationRepository;
import com.mcaproject.onlinebankingmanagement.Service.EmailService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class EmailOTPcontroller {
	

	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private CustomerRegistrationRepository customerRegistrationRepository;
	
	
	@GetMapping("/customer-login")
	public String GetCustomerLoginPage() {
		return "/customer/CustomerLogin";
	}
	
	
	@PostMapping("/customer-login")
	public String sendOTP(@RequestParam String email, 
			@RequestParam String password, Model model, HttpSession session,  
			HttpServletRequest request, RedirectAttributes redirectAttrs){
		
			CustomerEntity customer = customerRegistrationRepository.findByEmailAndPassword(email, password);
			if(customer!=null) {
				session.setAttribute("logincust", customer);
				model.addAttribute("email", email);
				model.addAttribute("customerAccountNumber", customer.getAccountNumber());
				model.addAttribute("customerName", customer.getName());
				 
				return "/customer/SendOtpPage";
			}
			
			
			else {
				redirectAttrs.addFlashAttribute("loginfailedmsg", "Invalid Email or Password");
				return "redirect:/customer-login";
			}	
	
	}
	
	
	@PostMapping("/send-otp")
	public String sendOtp(@RequestParam String email, Model model, HttpSession session) {
		CustomerEntity customer = customerRegistrationRepository.findByEmail(email);
		
		String otp = emailService.generateOTP();
		
		String subject = "OTP for Online Banking Management";
		String body = "Your OTP is : " + otp;
		
		try {
			emailService.sendOTP(email, subject, body);
			
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		session.setAttribute("otp", otp);
		model.addAttribute("email", email);
		
		session.setAttribute("customerName", customer.getName());
		session.setAttribute("customerAccnum", customer.getAccountNumber());
		
		return "/customer/verifyOtpPage";
	}
	
	@GetMapping("/verifyotp")
	public String verifyotp(){
		return "/customer/VerifyOtpPage";
	}
	
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam String mailOTP, HttpSession session, RedirectAttributes redirAttri) {
		String otp = (String) session.getAttribute("otp");
		if(mailOTP.equals(otp)) {
			return "redirect:/personalBanking";
		}else {
	        redirAttri.addFlashAttribute("wrongotp", "OTP is incorrect. Please check again.");
	        return "redirect:/verifyotp";
		}
	}

	
	@GetMapping("/logout")
	public String logout(HttpSession session, HttpSession adminsession) {
	    session.invalidate();
	    return "/homepage/index";
	}

	
}











