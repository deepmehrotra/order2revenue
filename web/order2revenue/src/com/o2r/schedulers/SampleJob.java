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
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o2r.controller.AdminController;
import com.o2r.model.Order;


public class SampleJob {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	static Logger log = Logger.getLogger(AdminController.class.getName());
	
	public void executeJobPaymentDueDate() {

		System.out.println("Declare Your Job Here...");
		log.info("Inside Job Assign....");
		log.debug("Yes, I am executing.....");
		List<Order> orders=new ArrayList<Order>();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date currentDate= new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -2);
			Date backDate=cal.getTime();
			log.info(cal.getTime());
			log.info(fmt.format(currentDate));
			log.info(fmt.format(cal.getTime()));
			Session session=sessionFactory.openSession();			
			if(session != null)
				session.beginTransaction();
			else
				log.info(session);
			Criteria criteria = session.createCriteria(Order.class);
			 criteria.createAlias("orderPayment", "payment",CriteriaSpecification.LEFT_JOIN);
			 criteria.createAlias("orderReturnOrRTO", "return",CriteriaSpecification.LEFT_JOIN);
			 
			Criterion rest1 = Restrictions.eq("poOrder", false);
            Criterion rest2 = Restrictions.and(    
                    Restrictions.eq("poOrder", true),
                    Restrictions.isNull("consolidatedOrder"));
            criteria.add(Restrictions.or(rest1, rest2));
            criteria.add(Restrictions.lt("paymentDueDate", currentDate))
            		.add(Restrictions.gt("paymentDueDate", backDate))
            		.add(Restrictions.isNull("payment.dateofPayment"))
            		.add(Restrictions.isNull("return.returnDate"));
            List temp=criteria.list();
           
            if(temp != null && temp.size() != 0){
            	orders=temp;            
    			log.info(orders.size());    			
    			for(Order order : orders){
    				if(order.getFinalStatus().equals("Actionable")){
    					log.info("Already Setted......");
    				}else{
	    				order.setStatus("Payment Disputed");
	    				order.setFinalStatus("Actionable");
	    				order.getOrderPayment().setPaymentDifference(-(order.getNetRate()));
	    				try{
	    					log.info("Order Setted.....");
	        				session.merge(order);
	        				session.getTransaction().commit();
	        				log.info("Order Merged Successfully....");
	        			}catch(Exception e){
	        				log.error("Failed! inside Schedular", e);    				
	        			}
    				}
    			}   			
            }else{
            	log.info("No orders....!!!!");
            }
            
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void executeJobProductStockList(){
		
	}
	
}
