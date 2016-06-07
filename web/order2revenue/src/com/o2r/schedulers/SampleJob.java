package com.o2r.schedulers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.controller.AdminController;
import com.o2r.helper.HelperClass;
import com.o2r.model.Order;
import com.o2r.service.OrderService;

@Repository
public class SampleJob implements Job{
	
	@Autowired
	private HelperClass helperClass;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private OrderService orderService;
	
	
	static Logger log = Logger.getLogger(AdminController.class.getName());
	
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {

		System.out.println("Declare Your Job Here...");
		log.info("Inside Job Assign....");
		log.debug("Yes, I am executing.....");
		List<Order> orders=new ArrayList<Order>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date currentDate= new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -2);
			log.info(cal.getTime());
			log.info(fmt.format(currentDate));
			log.info(fmt.format(cal.getTime()));
			Session session=sessionFactory.openSession();			
			if(session != null)
				session.beginTransaction();
			else
				log.info(session);
			Criteria criteria = session.createCriteria(Order.class);
			Criterion rest1 = Restrictions.eq("poOrder", false);
            Criterion rest2 = Restrictions.and(    
                    Restrictions.eq("poOrder", true),
                    Restrictions.isNull("consolidatedOrder"));
            criteria.add(Restrictions.or(rest1, rest2));
            criteria.add(Restrictions.lt("paymentDueDate", "2016-05-18"))
            		.add(Restrictions.gt("paymentDueDate", "2016-05-16"));
            if(criteria != null){
            	orders=criteria.list();            
    			log.info(orders.size());
            }else{
            	log.info("No orders....!!!!");
            }
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		
	}
}
