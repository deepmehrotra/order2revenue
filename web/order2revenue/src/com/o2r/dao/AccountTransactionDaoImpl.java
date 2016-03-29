/**
 * @Auther Kapil Halewale
 */
package com.o2r.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.AccountTransaction;


@Repository("accountTransactionDao")
public class AccountTransactionDaoImpl implements AccountTransactionDao {
	 @Autowired
	 private SessionFactory sessionFactory;
	 
	@SuppressWarnings("unchecked")
	@Override
	public List<AccountTransaction> listAccountTransactions() {
		  return (List<AccountTransaction>) sessionFactory.getCurrentSession().createCriteria(AccountTransaction.class).list();

	}
	@Override
	public AccountTransaction getAccountTransaction(int accTransId) {
		  return (AccountTransaction) sessionFactory.getCurrentSession().get(AccountTransaction.class, accTransId);
	}

	@Override
	public void deleteAccountTransaction(AccountTransaction accountTransaction) {
	      Session session = sessionFactory.openSession();
	      Transaction tx = null;
	      try{
	         tx = session.beginTransaction();
	         AccountTransaction at = (AccountTransaction)session.get(AccountTransaction.class, accountTransaction.getAccTransId()); 
	         session.delete(at); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }		
	}
}
