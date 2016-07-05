package com.o2r.utility;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.Seller;

@Repository("dataRestoreUtility")
public class DataRestore {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private SessionFactory sessionFactoryforBackup;
	static Logger log = Logger.getLogger(DataRestore.class.getName());
	
	public Seller getSellerFromDB(int sellerid, String dbtype)
	{
		log.info("*** getSeller from sellerId Starts : getSellerFromDB ****");
		Seller seller=null;
		Session session = null;
		try{
			if(dbtype!=null&&dbtype.equals("primary"))
			{
				session=sessionFactory.openSession();
			}
			else
			{
				session=sessionFactoryforBackup.openSession();
			}
				
			session.beginTransaction();
			Object obj=session.get(Seller.class,
					sellerid);
			if(obj!=null)
			seller=(Seller)obj;
			else
				System.out.println(" No selled in db with that id");
			//Hibernate.initialize(seller.getStateDeliveryTime());
			session.getTransaction().commit();
			session.close();
		System.out.println(" Seller Nname and id from "+dbtype+" name :"+seller.getName());
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed!",e);
					
		}
		log.info("*** getSeller from sellerId Ends : getSellerFromDB ****");
		return seller;
	}
	
	public void moveSeller(int sellerid)
	{
		log.info("*** getSeller from sellerId Starts : getSellerFromDB ****");
		Seller seller=null;
		Session session = null;
		try{
			
			session=sessionFactory.openSession();
			session.beginTransaction();
			Object obj=session.get(Seller.class,
					sellerid);
			if(obj!=null)
			seller=(Seller)obj;
			else
				System.out.println(" No selled in db with that id");
			if(seller!=null)
			{
			/*Hibernate.initialize(seller.getStateDeliveryTime());
			Hibernate.initialize(seller.getPartners());
			Hibernate.initialize(seller.getProducts());
			Hibernate.initialize(seller.getExpensecategories());
			Hibernate.initialize(seller.getPaymentUploads());*/
			}
			session.getTransaction().commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed! in fetching seller from db1",e);
					
		}
		System.out.println(" Successfully fetc seller data");
		/*seller.setId(0);
		seller.getSellerAccount().setSelaccId(0);*/
		System.out.println(" Seller Accoount details : "+seller.getSellerAccount().getSelaccId());
		try{
			
			session=sessionFactoryforBackup.openSession();
			
		session.beginTransaction();
		if(seller!=null)
			
			session.saveOrUpdate(seller);
		session.getTransaction().commit();
		session.close();
	}catch(Exception e){
		e.printStackTrace();
		log.error("Failed! in saving seller from db1",e);
				
	}
		log.info("*** getSeller from sellerId Ends : getSellerFromDB ****");
		
	}

}
