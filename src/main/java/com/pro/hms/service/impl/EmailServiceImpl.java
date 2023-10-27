package com.pro.hms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pro.hms.service.EmailService;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
@Service
public class EmailServiceImpl  implements EmailService{
	
	@Autowired
	private JavaMailSender mailSender;
	public void sendEmail(String To,String Subject,String Msg)
	{
		
		SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("fromemail@gmail.com");
        message.setTo(To);
        message.setText(Msg);
        message.setSubject(Subject);
        mailSender.send(message);
        System.out.println("Mail Send...");
	}
}
