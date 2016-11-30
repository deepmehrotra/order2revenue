package com.o2r.schedulers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
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

import com.o2r.controller.AdminController;
import com.o2r.helper.HelperClass;
import com.o2r.model.Order;
import com.o2r.model.OrderTimeline;
import com.o2r.model.Product;
import com.o2r.model.ProductStockList;


public class SampleJob {
	
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HelperClass helperClass;
	
	static Logger log = Logger.getLogger(SampleJob.class.getName());
	
	public void executeJobPaymentDueDate() {

		log.info("$$$ executeJobPaymentDueDate() Starts : SampleJob $$$");
		Session session=null;
		List<Order> orders=new ArrayList<Order>();
		try {
			Date currentDate= new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -2);
			Date backDate=cal.getTime();
			session=sessionFactory.openSession();			
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
	    					session.merge(order);	    					
	        				log.info("Order Merged Successfully....");
	        			}catch(Exception e){
	        				log.error("Failed! During Merging !", e);    				
	        			}
    				}
    			}
    			log.info("************************ Schedular Executed At : "+ new Date());
            }else{
            	log.info("No orders....!!!!");
            }
            session.getTransaction().commit();            
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		} finally{
			if(session != null)
            	session.close();
		}
		log.info("$$$ executeJobPaymentDueDate() Exits : SampleJob $$$");
	}
	
	public void executeJobProductStockList(){
		
		log.info("$$$ executeJobProductStockList() Starts : SampleJob $$$");
		Date currentDate=new Date();
		Session session=null;
		try {
			session = sessionFactory.openSession();
			if(session != null)
				session.beginTransaction(); 
			Criteria criteria = session.createCriteria(Product.class);
			java.util.List<Product> list = criteria.list();
			ProductStockList stockList = null;
			int count=0;
			if(list != null && list.size() != 0){
				for (Product product : list) {
					List<ProductStockList> stocklist = product.getClosingStocks();
	                if (stocklist != null)
	                    Collections.sort(stocklist);
	                if (stocklist != null && stocklist.size() != 0
	                        && stocklist.get(0).getMonth() == (currentDate.getMonth()+1)
	                        && stocklist.get(0).getYear() == currentDate.getYear()) {
	                    log.debug("No need to Update The stockList...");
	                } else {
						stockList = new ProductStockList();
						stockList.setCreatedDate(currentDate);
						stockList.setStockAvailable(product.getQuantity());
						stockList.setUpdatedate(currentDate.getDate());
						stockList.setMonth((currentDate.getMonth())+1);
						stockList.setYear(currentDate.getYear());
						stockList.setPrice(product.getProductPrice());
						product.getClosingStocks().add(stockList);
						session.saveOrUpdate(product);
						count++;
						//session.saveOrUpdate(stockList);
						log.info("PRODUCT ID : " + product.getProductId());
	                }
				}
			}
			session.getTransaction().commit();
			log.debug("ProductStockList Updated !!!! No of new Entries : "+count);
		} catch (Exception e) {
			log.error("Failed!",e);
		} finally {
			if(session != null)
            	session.close();
		}
		log.info("$$$ executeJobProductStockList() Ends : SampleJob $$$");
	}
	
	
	public void executeJobSettleOrder() {
		
		log.info("$$$ executeJobSettleOrder() Starts : SampleJob $$$");
		log.info("Fired At : "+ new Date());
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -90);
		Date backDate=cal.getTime(); 
		Session session=null;
		List<Order> orderList = new ArrayList<Order>();
		int count = 0;		
		try {
			session = sessionFactory.openSession();
			if(session != null)
				session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Order.class);
				criteria.createAlias("orderPayment", "orderPayment", CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.lt("orderPayment.dateofPayment", backDate))
					.add(Restrictions.gt("orderPayment.paymentDifference", 0.0))
					.add(Restrictions.ne("finalStatus", "Settled"));
					
			orderList = criteria.list();
			if(orderList != null){
				log.info("No of Orders to be Settle : "+orderList.size());
			}
			if(orderList != null && orderList.size() != 0){
				for(Order order : orderList){					
					order.getOrderPayment().setPaymentDifference(0);
					order.setFinalStatus("Settled");
					OrderTimeline timeline = new OrderTimeline();
					timeline.setEventDate(new Date());
					timeline.setEvent("Settled");
					order.getOrderTimeline().add(timeline);
					order.setLastActivityOnOrder(new Date());
					try {
						session.merge(order);						
						count++;
					} catch (Exception e) {
						e.printStackTrace();
						log.error("Error in executeJobSettleOrder in SampleJob Schedular !");
					}					
				}
			}
			session.getTransaction().commit();
			log.info("Order Settled !!!! No of rows Updated : "+count);
		} catch (Exception e) {
			log.error("Failed!",e);
			log.info("Rows Updated : "+count);
		} finally {
			if(session != null)
            	session.close();
		}
		log.info("$$$ executeJobSettleOrder() Ends : SampleJob $$$");
	}
	
}
