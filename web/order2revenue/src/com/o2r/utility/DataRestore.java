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

}
