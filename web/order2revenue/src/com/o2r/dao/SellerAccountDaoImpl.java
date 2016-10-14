package com.o2r.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.AccountTransaction;
import com.o2r.model.SellerAccount;

@Repository("sellerAccountDao")
public class SellerAccountDaoImpl implements SellerAccountDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	 
	 static Logger log = Logger.getLogger(SellerAccountDaoImpl.class.getName());

// Method to display all SellerAccounts
	@SuppressWarnings("unchecked")
	@Override
	public List<SellerAccount> listSellerAccounts() {
		  return (List<SellerAccount>) sessionFactory.getCurrentSession().createCriteria(SellerAccount.class).list();
	}
	
	@Override
	public void saveSellerAccount(SellerAccount sellerAccount) {
		try {
			Session session=sessionFactory.openSession();
			session.beginTransaction();
			if(sellerAccount != null){
				session.saveOrUpdate(sellerAccount);
			}			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error("Failed to save SellerAccount : ",e);
		}
		
	}

// Method to display  SellerAccounts By Id

	@Override
	public SellerAccount getSellerAccount(int selaccId) {
		
			SellerAccount sellerAccount=null;
			try {
				Session session=sessionFactory.openSession();
				session.beginTransaction();
				sellerAccount=(SellerAccount)session.get(SellerAccount.class, selaccId);
				session.close();
			} catch (Exception e) {
				log.error("Failed To getting SellerAccount : SellerAccountDaoImpl ",e);
			}
			return sellerAccount;
	}

// Method to delete  SellerAccounts
	
	@Override
	public void deleteSellerAccount(SellerAccount sellerAccount) {

		log.info("*** deleteSellerAccount Starts : SellerAccountDaoImpl ****");
		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			SellerAccount p = (SellerAccount) session.get(SellerAccount.class,
					sellerAccount.getSelaccId());
			session.delete(p);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			log.error("Failed!", e);
			e.printStackTrace();
		} finally {
			session.close();
		}
		log.info("*** deleteSellerAccount Ends : SellerAccountDaoImpl ****");
	}
	
	@Override
	public AccountTransaction getLastTXN(int SellerId) {
		AccountTransaction accTxn = null;
		SellerAccount sellerAcc = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(SellerAccount.class);
					criteria.createAlias("accountTransactions", "accountTransactions")
					.add(Restrictions.eq("sellerId", SellerId))
					.add(Restrictions.eqProperty("lastTransaction", "accountTransactions.transactionDate"));
			if(criteria != null && criteria.list().size() != 0)
				sellerAcc = (SellerAccount) criteria.list().get(0);
			if(sellerAcc != null){
				accTxn = sellerAcc.getAccountTransactions().get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed By Seller ID : "+SellerId, e);
		} finally {
			if(session != null)
				session.close();
		}		
		return accTxn;
	}
	
}

