/*
 * @Author Kapil Kumar
 */
package com.o2r.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.AccountTransaction;
import com.o2r.model.Plan;
import com.o2r.model.Seller;
import com.o2r.model.SellerAccount;
@Repository("planDao")
public class PlanDaoImpl implements PlanDao{
	 @Autowired
	 private SessionFactory sessionFactory;

	 @Override
	public void addPlan(Plan plan) {
		 try{
			   Session session=sessionFactory.openSession();
			   session.beginTransaction();
			   session.saveOrUpdate(plan);
			   session.getTransaction().commit();
			   session.close();		
			 }catch(Exception e){
				 System.out.println(" Plan Module Dao IMPL :"+e.getLocalizedMessage());
			 }
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Plan> listPlans() {
		  return (List<Plan>) sessionFactory.getCurrentSession().createCriteria(Plan.class).list();
	}

// Method to get plan by id
	@Override
	public Plan getPlan(int pid) {
	    return (Plan) sessionFactory.getCurrentSession().get(Plan.class, pid);
	}

// Method to delete plan by pid
	@Override
	public void deletePlan(Plan plan) {
		      Session session = sessionFactory.openSession();
		      Transaction tx = null;
		      try{
		         tx = session.beginTransaction();
		         Plan p = (Plan)session.get(Plan.class, plan.getPid()); 
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
