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
import com.o2r.model.Product;
import com.o2r.model.ProductStockList;


public class SampleJob {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	static Logger log = Logger.getLogger(AdminController.class.getName());
	
	public void executeJobPaymentDueDate() {

		log.info("$$$ executeJobPaymentDueDate() Starts : SampleJob $$$");
		
		List<Order> orders=new ArrayList<Order>();
		try {
			Date currentDate= new Date();
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DATE, -2);
			Date backDate=cal.getTime();
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
	    					session.merge(order);
	        				session.getTransaction().commit();
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
            
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}	
		log.info("$$$ executeJobPaymentDueDate() Exits : SampleJob $$$");
	}
	
	public void executeJobProductStockList(){
		try {
			Session session = sessionFactory.openSession();
			if(session != null)
				session.beginTransaction(); 
			Criteria criteria = session.createCriteria(Product.class);
			java.util.List<Product> list = criteria.list();
			ProductStockList stockList = null;
			if(list != null && list.size() != 0){
				for (Product product : list) {
					stockList = new ProductStockList();
					stockList.setCreatedDate(new Date());
					stockList.setStockAvailable(product.getQuantity());
					stockList.setUpdatedate(product.getProductDate().getDate());
					stockList.setMonth(product.getProductDate().getMonth());
					stockList.setYear(product.getProductDate().getYear());
					stockList.setPrice(product.getProductPrice());
					product.getClosingStocks().add(stockList);
					session.saveOrUpdate(product);
					session.saveOrUpdate(stockList);
					log.info("PRODUCT ID : " + product.getProductId());
				}
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			log.error("Failed!",e);
		}
	}
	
}
