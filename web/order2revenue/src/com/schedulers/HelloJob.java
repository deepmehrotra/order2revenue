package com.schedulers;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.annotations.Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.model.Product;
import com.o2r.model.ProductStockList;
public class HelloJob implements Job{
	
	 @Autowired
	 private SessionFactory sessionFactory;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		System.out.println("THIS IS JUST A QUARTZ TEST");
	}
	
	public void initIt() throws Exception {
		  for(int i=0;i<10000;i++)
		  System.out.println("Init method after properties are set : MAHESH");
		}
		
		public void cleanUp() throws Exception {
		  System.out.println("Spring Container is destroy! Customer clean up");
		}
	
	 public void addToProductStockList(){
		Session session=sessionFactory.getCurrentSession();
		session.beginTransaction();
		   Criteria criteria=session.createCriteria(Product.class);
		   java.util.List<Product> list=criteria.list();
		   Product stockProduct=null;
		   ProductStockList stockList=new ProductStockList();
			for (Product product : list ) {
				stockList.setCreatedDate(new Date());
				stockList.setStockAvailable(product.getQuantity());
				stockList.setUpdatedate(product.getProductDate().getDate());
				stockList.setMonth(product.getProductDate().getMonth());
				stockList.setYear(product.getProductDate().getYear());
				stockList.setPrice(product.getProductPrice());
				product.getClosingStocks().add(stockList);
			}
			session.getTransaction().commit();
	 }

}
