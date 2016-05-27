package com.o2r.dao;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.AccountTransaction;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Plan;
import com.o2r.model.Product;
import com.o2r.model.ProductStockList;
import com.o2r.model.Seller;
import com.o2r.model.State;
import com.o2r.model.StateDeliveryTime;
import com.o2r.service.ExpenseService;

/**
 * @author Deep Mehrotra
 *
 */
//GIT Test
@Repository("sellerDao")
public class SellerDaoImpl implements SellerDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private ExpenseService expenseService;

	static Logger log = Logger.getLogger(SellerDaoImpl.class.getName());
	
	private static final String deliveryTimeDeleteQuery = "delete from seller_state_deliverytime  where seller_id=:sellerId";


	public void addSeller(Seller seller)throws CustomException {
		
		
		log.info("*** addSeller Starts : SellerDaoImpl ****");
		boolean firsttimeflag=true;
		Seller sellerNew=null;
		int id =0;
		String name = null;
		String address =  null;
		String companyName = null; 
		String contactNo = null;
		String password = null;
		String email=null;
		String tinNumber = null;
		String tanNumber = null;
		String brandName = null;
		String logoUrl = null;
		if (seller != null && seller.getId() != 0) {
             id = seller.getId();
             name = seller.getName();
             address = seller.getAddress();
             email = seller.getEmail();
             companyName = seller.getCompanyName();
             contactNo = seller.getContactNo();
             password = seller.getPassword();
             tinNumber = seller.getTinNumber();
             tanNumber = seller.getTanNumber();
             brandName = seller.getBrandName();
             logoUrl = seller.getLogoUrl();
		}		
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			if(seller!=null&&seller.getId()!=0)
			{
				firsttimeflag=false;
				sellerNew = (Seller) session.get(Seller.class, id);
					  if(sellerNew != null){
		                    sellerNew.setId(id);
		                    sellerNew.setName(name);
		                    sellerNew.setAddress(address);
		                    sellerNew.setEmail(email);
		                    sellerNew.setCompanyName(companyName);
		                    sellerNew.setContactNo(contactNo);
		                    sellerNew.setPassword(password);
		                    sellerNew.setTinNumber(tinNumber);
		                    sellerNew.setTanNumber(tanNumber);
		                    sellerNew.setBrandName(brandName);
		                    if(logoUrl != null){
		                    	sellerNew.setLogoUrl(logoUrl); 
		                    }
		                }
					session.saveOrUpdate(sellerNew);
					
				
			}
			else
			{
			session.saveOrUpdate(seller);
			}
			session.getTransaction().commit();
			session.close();
			if(firsttimeflag)
			setExpenseGroupsForSeller(seller.getId());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addSellerError, new Date(), 1, GlobalConstant.addSellerErrorCode, e);
		}
		log.info("*** addSeller Ends : SellerDaoImpl ****");
	}

	@SuppressWarnings("unchecked")
	public List<Seller> listSeller()throws CustomException {
		
		log.info("*** listSeller Starts : SellerDaoImpl ****");
		List<Seller> sellers=null;
		try{				
			sellers=(List<Seller>) sessionFactory.getCurrentSession().createCriteria(Seller.class).list();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.listSellerError, new Date(), 3, GlobalConstant.listSellerErrorCode, e);
			
		}
		log.info("*** listSeller Ends : SellerDaoImpl ****");
		return sellers;
	}

	public Seller getSeller(int sellerid)throws CustomException {
		
		log.info("*** getSeller from sellerId Starts : SellerDaoImpl ****");
		Seller seller=null;
		try{
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			seller=(Seller) session.get(Seller.class,
					sellerid);
			Hibernate.initialize(seller.getStateDeliveryTime());
			session.getTransaction().commit();
			session.close();
		
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.getSellerByIdError, new Date(), 3, GlobalConstant.getSellerByIdErrorCode, e);
			
		}
		log.info("*** getSeller from sellerId Ends : SellerDaoImpl ****");
		return seller;
	}

	public Seller getSeller(String email)throws CustomException {
		
		log.info("*** getSeller from email Starts : SellerDaoImpl ****");
		System.out.println(" Getting seelrthrough email : "+email);
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("email", email));
			if (criteria.list() != null && criteria.list().size() != 0)
				seller = (Seller) criteria.list().get(0);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.getSellerByEmailError, new Date(), 3, GlobalConstant.getSellerByEmailErrorCode, e);			
		}
		log.info("*** getSeller from email Ends : SellerDaoImpl ****");
		return seller;
	}

	public void deleteSeller(Seller seller)throws CustomException {
		
		log.info("*** deleteSeller Starts : SellerDaoImpl ****");
		Seller seller1 = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", seller.getId()));

			seller1 = (Seller) criteria.list().get(0);
			seller1.getPartners().clear();
			seller1.getOrders().clear();
			System.out.println(" List size of partner after deletion :"
					+ seller1.getPartners().size());
			System.out.println(" List size of order after deletion :"
					+ seller1.getOrders().size());
			session.delete(seller1);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.deleteSellerError, new Date(), 3, GlobalConstant.deleteSellerErrorCode, e);
		}
		log.info("*** deleteSeller Ends : SellerDaoImpl ****");
	}

	@Override
	public void planUpgrade(int pid, double totalAmount, long orderCount, int sellerid)throws CustomException {
		

		log.info("*** planUpgrade Starts : SellerDaoImpl ****");
		AccountTransaction at = null;
		Plan plan = null;
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			plan = (Plan) session.get(Plan.class, pid);
			seller = (Seller) session.get(Seller.class, sellerid);			
			AccountTransaction acct = payementStatus(plan);
			log.debug(" AFTER getting  ACCOUNT TRANSacttion object "
					+ acct.getStatus() + " plan order count : "
					+ plan.getOrderCount());
			log.debug(" Current ORDER BUCKET : "
					+ seller.getSellerAccount());
			if (seller.getSellerAccount() == null) {
				throw new Exception();
			}
			log.debug(" Current ORDER BUCKET : "+ seller.getSellerAccount().getOrderBucket());
			if (acct.getStatus().equals("Success")) {
				seller.getSellerAccount().setOrderBucket(
						seller.getSellerAccount().getOrderBucket()
								+ orderCount);
				seller.getSellerAccount().setTotalAmountPaidToO2r(
						seller.getSellerAccount().getTotalAmountPaidToO2r() + 
						totalAmount);
				seller.getSellerAccount().setPlanId(
						plan.getPid());
				seller.setPlan(plan);

			}
			if (seller.getSellerAccount().getLastLogin() == null
					|| seller.getSellerAccount().isAccountStatus() == false) {
				seller.getSellerAccount().setAccountStatus(true);
				seller.getSellerAccount().setLastLogin(new Date());
				seller.getSellerAccount().setTotalOrderProcessed(
						seller.getSellerAccount().getTotalOrderProcessed()
								+ plan.getOrderCount());
				seller.getSellerAccount().setAtivationDate(new Date());
			}
			seller.getSellerAccount().setLastTransaction(new Date());
			seller.getSellerAccount().getAccountTransactions().add(acct); //

			Random r = new Random();
			int randomId = r.nextInt(9999999);
			String invoiceId = "O2R" + randomId;

			at = new AccountTransaction();
			at.setTransactionDate(new Date());
			at.setTransactionAmount(plan.getPlanPrice());
			at.setCurrentOrderCount(plan.getOrderCount());
			at.setStatus("Done");
			at.setInvoiceId(invoiceId);
			at.setTransactionId("RecivedFrom Third Party");
			seller.getSellerAccount().getAccountTransactions().add(at);
			session.saveOrUpdate(seller);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.planUpgradeError, new Date(), 1, GlobalConstant.planUpgradeErrorCode, e);
		}
		log.info("*** planUpgrade Ends : SellerDaoImpl ****");
	}

	public AccountTransaction payementStatus(Plan plan) {
		
		log.info("*** payementStatus Starts : SellerDaoImpl ****");
		AccountTransaction at = new AccountTransaction();
		at.setTransactionDate(new Date());
		at.setTransactionAmount(plan.getPlanPrice());
		at.setStatus("Success");// It has to come from third party
		at.setInvoiceId("invoice123");
		at.setTransactionId("transac123"); // It has Recived From Third Party
		log.info("*** payementStatus Ends : SellerDaoImpl ****");
		return at;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<State> listStates() {

		return (List<State>) sessionFactory.getCurrentSession()
				.createCriteria(State.class).list();
	}

	 public void addToProductStockList(){
		
		log.info("*** addToProductStockList Starts : SellerDaoImpl ****");
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		Criteria criteria=session.createCriteria(Product.class);
		java.util.List<Product> list=criteria.list();
		Product stockProduct=null;
		ProductStockList stockList=null;
		for (Product product : list ) {
			stockList=new ProductStockList();
			stockList.setCreatedDate(new Date());
			stockList.setStockAvailable(product.getQuantity());
			stockList.setUpdatedate(product.getProductDate().getDate());
			stockList.setMonth(product.getProductDate().getMonth());
			stockList.setYear(product.getProductDate().getYear());
			stockList.setPrice(product.getProductPrice());
			product.getClosingStocks().add(stockList);
			session.saveOrUpdate(product);
			session.saveOrUpdate(stockList);
		log.debug("PRODUCT ID IS hehehe "+product.getProductId());
		}
		session.getTransaction().commit();
		log.info("*** addToProductStockList Ends : SellerDaoImpl ****");
	}

	@Override
	public int getStateDeliveryTime(int sellerId,String statename)throws CustomException {
		
		log.info("*** getStateDeliveryTime starts : SellerDaoImpl ****");
		List<StateDeliveryTime> listofstates=null;
		int returntime=0;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(StateDeliveryTime.class).add(
					Restrictions.eq("seller.id", sellerId));
			criteria.createAlias("state", "state",
					CriteriaSpecification.LEFT_JOIN).add(Restrictions.eq("state.stateName", statename))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			listofstates=criteria.list();

			if ( listofstates!= null && listofstates.size() != 0) 
				returntime=listofstates.get(0).getDeliveryTime();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(" Error getting state elivery time  ");
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.getSellerByEmailError, new Date(), 3, GlobalConstant.getSellerByEmailErrorCode, e);
		}
		log.info("*** getStateDeliveryTime Ends : SellerDaoImpl ****");
		return returntime;
	}
	
	/*
	 * Method to add Default Categories in new Seller
	 */
	private void setExpenseGroupsForSeller(int sellerId) throws CustomException {
		
		log.info("*** setExpenseGroupsForSeller starts : SellerDaoImpl ****");
		try{
		 for(Entry<String, String> e : GlobalConstant.preDefinedExpenseCategoryMap.entrySet()) {
			 expenseService.addExpenseCategory(new ExpenseCategory(e.getKey(), e.getValue(),new Date()), sellerId);
		 }
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
		}
		log.info("*** setExpenseGroupsForSeller Ends : SellerDaoImpl ****");
	}
	
	@Override
	public void addStateDeliveryTime(List<StateDeliveryTime> stateDelTimeList, int sellerId)throws CustomException {
		

		log.info("*** addStateDeliveryTime starts : SellerDaoImpl ****");
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query gettingTaxId = session
					.createSQLQuery(deliveryTimeDeleteQuery)
					.setParameter("sellerId",sellerId);
			gettingTaxId.executeUpdate();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			
			for(StateDeliveryTime sdt:stateDelTimeList)
			{
				sdt.setSeller(seller);
			seller.getStateDeliveryTime().add(sdt);
			}
			session.saveOrUpdate(seller);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addStateDeliveryTimeError,
					new Date(), 1, GlobalConstant.addStateDeliveryTimeErrorCode,
					e);			
		}
		log.info("*** addStateDeliveryTime Ends : SellerDaoImpl ****");
	}
	
	@Override
	public State getStateByName(String statename) throws CustomException
	{
		log.info("*** getStateByName starts : SellerDaoImpl ****");
		List listofstates=null;
		State returnstate=null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(State.class).add(
					Restrictions.eq("stateName", statename));
			
			listofstates=criteria.list();

			if ( listofstates!= null && listofstates.size() != 0) 
				returnstate=(State)listofstates.get(0);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.getSellerByEmailError, new Date(), 3, GlobalConstant.getSellerByEmailErrorCode, e);
		}
		log.info("*** getStateByName Ends : SellerDaoImpl ****");
		return returnstate;
	}


	@Override
	public void updateProcessedOrdersCount(int sellerId, int processedOrderCount) throws CustomException {
		
		log.info("**** updateProcessedOrdersCount starts : SellerDaoImpl ****");
		try{
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			long totalOrderProcessed = seller.getSellerAccount().getTotalOrderProcessed();
			totalOrderProcessed += processedOrderCount;
			seller.getSellerAccount().setTotalOrderProcessed(totalOrderProcessed);
			session.saveOrUpdate(seller);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.updateProcessedOrdersCountError, new Date(), 1, GlobalConstant.updateProcessedOrdersCountErrorCode, e);
		}
		log.info("*** updateProcessedOrdersCount Ends : SellerDaoImpl ****");
	}
	
	@Override
	public void sendMail(String email)throws CustomException{
		
		String code=null;
		final String from="2mailbishnu@gmail.com";
		if(code != null){
			Seller seller=getSeller(2);
			/*if(seller != null){
				if(seller.getVerCode().equals(code)){
					seller.setVerCode("Verified");
				}
			}*/
		} else {
			String varificationCode = "saveSeller.html?code=O2RUSER10" + "";
			Properties prop = new Properties();
			prop.put("mail.smtp.starttls.enable", "true");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.port", "587");

			javax.mail.Session session = javax.mail.Session.getInstance(prop,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(from, "DeadmaN^^");
						}
					});

			try {
				MimeMessage message = new MimeMessage(session);
				message.setFrom(new InternetAddress(from));
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(email));
				message.setSubject("Registration Conformation");
				// message.setText("click on the below link for complete Registration ");
				message.setText(varificationCode);
				Transport.send(message);
				System.out.println("Mail send Successfully....");
			} catch (AddressException e) {
				e.printStackTrace();
			} catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	    
	  }
}
