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

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.controller.UploadController;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.AccountTransaction;
import com.o2r.model.Plan;
import com.o2r.model.Seller;
import com.o2r.model.SellerAccount;

@Repository("planDao")
public class PlanDaoImpl implements PlanDao {
	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(PlanDaoImpl.class.getName());

	@Override
	public void addPlan(Plan plan)throws CustomException {
		
		log.info("*** addPlan Start ***");
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(plan);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.addPlanError, new Date(), 1, GlobalConstant.addPlanErrorCode, e);			
		}
		log.info("*** addPlan exit ***");
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Plan> listPlans()throws CustomException {
		log.info("*** listPlans Start ***");
		List<Plan> plans=new ArrayList<Plan>();
		try{
		plans=(List<Plan>) sessionFactory.getCurrentSession().createCriteria(Plan.class).list();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.listPlansError, new Date(), 3, GlobalConstant.listPlansErrorCode, e);
		}
		log.info("*** listPlans exit ***");
		return plans;
	}

	// Method to get plan by id
	@Override
	public Plan getPlan(int pid)throws CustomException {
		log.info("*** getplan start ***");
		Plan plan=new Plan();
		try{
		plan=(Plan) sessionFactory.getCurrentSession().get(Plan.class, pid);
		}catch (Exception e){
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.getPlanError, new Date(), 3, GlobalConstant.getPlanErrorCode, e);
		}
		log.info("*** getPlan exit ***");
		return plan;
	}

	// Method to delete plan by pid
	@Override
	public void deletePlan(Plan plan)throws CustomException {
		
		log.info("*** deletePlan start ***");

		Session session = sessionFactory.openSession();
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			Plan p = (Plan) session.get(Plan.class, plan.getPid());
			session.delete(p);
			tx.commit();
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			log.error("Failed!",e);
			throw new CustomException(GlobalConstant.deletePlanError, new Date(), 3, GlobalConstant.deletePlanErrorCode, e);
			
		} finally {
			session.close();
		}
		log.info("*** deletePlan exit ***");
	}

}
