package com.mcaproject.onlinebankingmanagement.Service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	public void sendOTP(String toemail, String subject, String text) throws MessagingException {
		SimpleMailMessage message=new SimpleMailMessage(); 
		message.setFrom("priyabarani14@gmail.com");
		message.setTo(toemail);
		message.setSubject(subject);
		message.setText(text);
		javaMailSender.send(message);
		
		System.out.println("mail send successfully");
	}
	
	
	public String generateOTP() {
		Random random = new Random();
		int otp = 100000 + random.nextInt(900000);
		return String.valueOf(otp);
	}
}
