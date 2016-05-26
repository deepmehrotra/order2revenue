package com.o2r.dao;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

@Repository("mailDao")
public class MailDaoImpl implements MailDao{
	
	/*@Autowired
	private JavaMailSender javaMailSender;*/
	
	@Override
	public void sendMail(Properties props, String email) {
		
		
		
	}
}
