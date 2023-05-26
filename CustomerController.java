package com.mcaproject.onlinebankingmanagement.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mcaproject.onlinebankingmanagement.Entity.CustomerEntity;
import com.mcaproject.onlinebankingmanagement.Entity.TransactionEntity;
import com.mcaproject.onlinebankingmanagement.Repository.CustomerRegistrationRepository;
import com.mcaproject.onlinebankingmanagement.Repository.TransactionRepository;
import com.mcaproject.onlinebankingmanagement.Service.customerService;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/transaction")
public class CustomerController {
	
	

	@Autowired
	private CustomerRegistrationRepository customerRepository;
	
	@Autowired
	private customerService customerService;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@GetMapping("/deposit-form")
	public String showDepositForm(Model model) {
		model.addAttribute("transaction", new TransactionEntity());
		return "transaction/depositForm";
	}
	
	@PostMapping("/deposit")
	public String deposit(HttpSession session, @RequestParam("accnum") Long accnum, @RequestParam("depositamount") BigDecimal amount, @RequestParam("descrip") String description, RedirectAttributes reAttri){
		CustomerEntity customer = customerService.getCustomerByAccountNumber(accnum);
		
		if(customer == null) {
			reAttri.addFlashAttribute("depositerror", "Wrong Account Number please check..");
			return "redirect:/transaction/deposit-form";
		}
		
		TransactionEntity transaction = new TransactionEntity();
		
		Long fromAccountNumber = (Long)session.getAttribute("customerAccnum");
		
		transaction.setCustomer(customer);
		transaction.setAmount(amount);
		transaction.setDescription(description);
		transaction.setTimestamp(LocalDateTime.now());
		transaction.setTransactionType(TransactionEntity.TransactionType.DEPOSIT);
		transaction.setFromAccountNumber(fromAccountNumber);
		
		transactionRepository.save(transaction);
		
		BigDecimal newAccountBalance = customer.getBalance().add(amount);
		customer.setBalance(newAccountBalance);
		
		customerRepository.save(customer);
		
		reAttri.addFlashAttribute("depositsuccess", "Amount deposit successful..");
		return "redirect:/personalBanking";	
	}


	@GetMapping("/withdraw-form")
	public String showWithdrawForm(Model model) {
		model.addAttribute("transaction", new TransactionEntity());
		return "transaction/withdrawalForm";
	}
	
	@PostMapping("/withdrawal")
	public String withdraw(HttpServletRequest request, @ModelAttribute("transaction") TransactionEntity transaction, 
			HttpSession session, @RequestParam("withdrawamount") BigDecimal amount, @RequestParam("reason") String Reason, 
			RedirectAttributes redirAttri) {
		
		Long currentUser = (Long)session.getAttribute("customerAccnum");
		CustomerEntity customer = entityManager.find(CustomerEntity.class, currentUser);
		
		BigDecimal customerCurrentBalance = customer.getBalance();
		
		if(customerCurrentBalance.compareTo(BigDecimal.ZERO)<0) {
			redirAttri.addFlashAttribute("Withdrawalfailed", "Failed, due to Insufficient Balance..");
			return "redirect:/transaction/withdraw-form";
		}
		
		BigDecimal checkamount = new BigDecimal(request.getParameter("withdrawamount"));
		
		
		if(customerCurrentBalance.compareTo(checkamount) < 0) {
			redirAttri.addFlashAttribute("WithdrawalfailedDuetoLessAmount", "Failed, due to Insufficient Balance..");
			return "redirect:/transaction/withdraw-form";
		}
		
		transaction.setAmount(amount);
		transaction.setCustomer(customer);
		transaction.setDescription(Reason);
		transaction.setFromAccountNumber(currentUser);
		transaction.setTimestamp(LocalDateTime.now());
		transaction.setTransactionType(TransactionEntity.TransactionType.WITHDRAWAL);
		
		BigDecimal accountBalance = customer.getBalance().subtract(amount);
		
		customer.setBalance(accountBalance);
		transactionRepository.save(transaction);
		customerRepository.save(customer);
		redirAttri.addFlashAttribute("WithdrawalSuccess", "Amount Rs. " + amount + " withdrawal successful.." );
		return "redirect:/personalBanking";	
	}
	
	@GetMapping("/transfer-form")
	public String showTransferForm(Model model) {
		model.addAttribute("transfer", new TransactionEntity());
		return "/transaction/transferForm";
	}
	
	@PostMapping("/transfer")
	public String Transfer(@ModelAttribute("transfer") TransactionEntity transaction, HttpSession session,RedirectAttributes redirAttri, 
			Model model, @RequestParam("accnum") Long toaccnum, @RequestParam("transferamount") BigDecimal amount, 
			@RequestParam("reason") String reason, HttpServletRequest request) {
		
		CustomerEntity customer = customerService.getCustomerByAccountNumber((Long)session.getAttribute("customerAccnum"));
		CustomerEntity tocustomer = customerService.getCustomerByAccountNumber(toaccnum);
		
		BigDecimal customeraccbal = customer.getBalance();
		BigDecimal checkamount = new BigDecimal(request.getParameter("transferamount"));
		if(customeraccbal.compareTo(checkamount)<0 || customeraccbal.compareTo(BigDecimal.ZERO)<0 ) {
			redirAttri.addAttribute("transfererror", "Transaction failed due to insufficient Balance. Current Balance is : " + customeraccbal);
			return "redirect:/transaction/transfer-form";
		}
		
		if(tocustomer == null) {
			redirAttri.addFlashAttribute("transfererroracc", "Wrong Account Number please check..");
			return "redirect:/transaction/transfer-form";
		}
		
		
		BigDecimal accountBalance = customer.getBalance().subtract(amount);
		BigDecimal accountBalancetoCustomer = tocustomer.getBalance().add(amount);
		
		customer.setBalance(accountBalance);
		customerRepository.save(customer);
		
		tocustomer.setBalance(accountBalancetoCustomer);
		customerRepository.save(tocustomer);

		
		
		transaction.setAmount(amount);
		transaction.setCustomer(tocustomer);
		transaction.setDescription(reason);
		transaction.setFromAccountNumber(customer.getAccountNumber());
		transaction.setTimestamp(LocalDateTime.now());
		transaction.setTransactionType(TransactionEntity.TransactionType.TRANSFER);
		transactionRepository.save(transaction);

	
		redirAttri.addFlashAttribute("transferSuccess", "Rs. " + amount+ " successfully transfered from " + customer.getName() + " to " + tocustomer.getName() );
		return "redirect:/personalBanking";
		
	}

	@GetMapping("/last10")
	public String last10transacation(Model model, HttpSession session) {
		Long accnum = (Long)session.getAttribute("customerAccnum");
		List<TransactionEntity> Last10Transactions = transactionRepository.findTop10ByFromAccountNumberOrderByTimestampDesc(accnum);
		model.addAttribute("last10transactions", Last10Transactions);
		return "/transaction/last10transaction";
	}
	
}



