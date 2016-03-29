/**
 * @Auther Kapil Halewale
 */
package com.o2r.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.o2r.model.SellerAccount;

@Repository("sellerAccountDao")
public class SellerAccountDaoImpl implements SellerAccountDao {
	 private SessionFactory sessionFactory;

// Method to display all SellerAccounts
	@SuppressWarnings("unchecked")
	@Override
	public List<SellerAccount> listSellerAccounts() {
		  return (List<SellerAccount>) sessionFactory.getCurrentSession().createCriteria(SellerAccount.class).list();
	}

// Method to display  SellerAccounts By Id

	@Override
	public SellerAccount getSellerAccount(int selaccId) {
		  return (SellerAccount) sessionFactory.getCurrentSession().get(SellerAccount.class, selaccId);
	}

// Method to delete  SellerAccounts
	
	@Override
	public void deleteSellerAccount(SellerAccount sellerAccount) {
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         SellerAccount p = (SellerAccount)session.get(SellerAccount.class, sellerAccount.getSelaccId()); 
	         session.delete(p); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	   }					
	}

