package com.o2r.schedulers;

import java.util.Date;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.o2r.model.Product;
import com.o2r.model.ProductStockList;
@Service
public class StockUpdateServiceImpl implements StockUpdateService {
	 @Autowired
	 private SessionFactory sessionFactory;
	 
	 @Override
	 public void addToProductStockList(){
		 System.out.println("THIS IS SPRING BEAN IMPLEMENTATION");
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		   Criteria criteria=session.createCriteria(Product.class);
		   java.util.List<Product> list=criteria.list();
		   Product stockProduct=null;
		   ProductStockList stockList=null;
			for (Product product : list ) {
				stockList=new ProductStockList();
				stockList.setCreatedDate(new Date());
				stockList.setStockAvailable(product.getQuantity());
				stockList.setUpdatedate(product.getProductDate().getDate());
				stockList.setMonth(product.getProductDate().getMonth());
				stockList.setYear(product.getProductDate().getYear());
				stockList.setPrice(product.getProductPrice());
				product.getClosingStocks().add(stockList);
				session.saveOrUpdate(product);
				session.saveOrUpdate(stockList);
		System.out.println("PRODUCT ID IS hehehe "+product.getProductId());
			}
			session.getTransaction().commit();
	 }
}
