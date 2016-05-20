package com.o2r.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import com.o2r.dao.SellerDao;
import com.o2r.helper.CustomException;
import com.o2r.model.Seller;
import com.o2r.model.State;
import com.o2r.model.StateDeliveryTime;
import com.sun.mail.smtp.SMTPTransport;

/**
 * @author Deep Mehrotra
 *
 */
//GIT Test
@Service("sellerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SellerServiceImpl implements SellerService,ServletContextAware {

    private ServletContext context;
    private ServletConfig config;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.context = servletContext;
    }

    public ServletContext getServletContext(){
        return context;
    } 

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
		try {
			 Properties smtp=new Properties();
			 Properties mail=new Properties();
			 
				org.springframework.core.io.Resource resource1 = new ClassPathResource("smtp.properties");
				org.springframework.core.io.Resource resource2 = new ClassPathResource("mail.properties");
				//Properties props = PropertiesLoaderUtils.loadProperties(resource);
		    
			 
			// InputStream configStream1 =context.getResourceAsStream("/WEB-INF/smtp.properties");
			// InputStream configStream2 = context.getResourceAsStream("/WEB-INF/mail.properties");
			 
			 smtp=PropertiesLoaderUtils.loadProperties(resource1);
			 mail=PropertiesLoaderUtils.loadProperties(resource2);

			// FileInputStream smtpProps=new FileInputStream("d:/mails/gmail/smtp.properties");
		    // FileInputStream mailProps=new FileInputStream("d:/mails/gmail/mail.properties");
		    
		    //smtp.load(configStream1);
		   //mail.load(configStream2);

		    Session session = Session.getInstance(smtp, null);

			session.setDebug(true);

		    Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(mail.getProperty("from")));


		    msg.setRecipients(Message.RecipientType.TO,	InternetAddress.parse(email, false));
		    if (mail.getProperty("cc") != null)
			msg.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail.getProperty("to"), false));

		    msg.setSubject(mail.getProperty("subject"));

		    String text = mail.getProperty("message");
			msg.setText(text);

		    msg.setHeader("X-Mailer", mail.getProperty("mailer"));
		    msg.setSentDate(new Date());

		    SMTPTransport t = (SMTPTransport)session.getTransport(mail.getProperty("protocol"));
		    try {
			    t.connect(smtp.getProperty("mail.smtp.user"),smtp.getProperty("mail.smtp.password"));
			    t.sendMessage(msg, msg.getAllRecipients());
		    } finally {
			t.close();
		    }
		    System.out.println("\nMail was sent successfully.");
		} catch (Exception e) {}
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

	public void planUpgrade(int pid, double totalAmount, long orderCount, int sellerid) throws CustomException {
		sellerDao.planUpgrade(pid, totalAmount,orderCount, sellerid);
	}
	
	public void updateProcessedOrdersCount(int sellerId, int processedOrderCount) throws CustomException {
		sellerDao.updateProcessedOrdersCount(sellerId, processedOrderCount);
	}
	
	@Override
	public List<State> listStates() {
		return sellerDao.listStates();
	}
	
	@Override
	 public State getStateByName(String statename) throws CustomException
	 {
		return sellerDao.getStateByName(statename);
	 }
	
	 public void addToProductStockList(){
		 	sellerDao.addToProductStockList();
	 }
	
	@Override
	public void addStateDeliveryTime(List<StateDeliveryTime> stateDelTimeList, int sellerId)
			throws CustomException
			{
		sellerDao.addStateDeliveryTime(stateDelTimeList, sellerId);
			}

	

}
