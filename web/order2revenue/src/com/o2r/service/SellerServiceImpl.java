package com.o2r.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.SellerDao;
import com.o2r.helper.CustomException;
import com.o2r.model.Seller;
import com.o2r.model.State;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Deep Mehrotra
 *
 */
//GIT Test
@Service("sellerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SellerServiceImpl implements SellerService {

	@Autowired
	private SellerDao sellerDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSeller(Seller seller,Properties p) throws CustomException {
		sellerDao.addSeller(seller);
		if(p!=null){
			sendMail(p,seller.getEmail());
		}		
	}
	
	public void sendMail(final Properties props,final String email){
		
		Session session = Session.getInstance(props,
				  new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(props.getProperty("mail.username"),props.getProperty("mail.password"));
					}
				  });

				try {
						Message message = new MimeMessage(session);
						message.setFrom(new InternetAddress(props.getProperty("mail.username")));
						message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(email));
						String subject= props.getProperty("mail.subject");
						message.setContent(subject, "text/html; charset=utf-8");
						message.setSubject(subject);
						message.setText(props.getProperty("mail.message"));
						message.setHeader("X-Mailer", "DBSync-Email");
						message.setSentDate(new Date());
						Transport.send(message);
						
				} catch (MessagingException e) {
						System.out.println(e);
				}		
	}

	public List<Seller> listSellers() throws CustomException {
		return sellerDao.listSeller();
	}

	public Seller getSeller(int sellerid) throws CustomException {
		return sellerDao.getSeller(sellerid);
	}

	public void deleteSeller(Seller seller) throws CustomException {
		sellerDao.deleteSeller(seller);
	}

	public Seller getSeller(String email) throws CustomException {
		return sellerDao.getSeller(email);
	}

	public void planUpgrade(int pid, int sellerid) throws CustomException {
		sellerDao.planUpgrade(pid, sellerid);
	}
	
	public List<State> listStates() {
		return sellerDao.listStates();
	}
}
