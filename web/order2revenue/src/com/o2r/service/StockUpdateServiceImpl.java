package com.o2r.service;

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
@Entity
@Service("stockUpdateService")
public class StockUpdateServiceImpl implements StockUpdateService {
	 @Autowired
	 private SessionFactory sessionFactory;
	 
	 @Override
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
				session.saveOrUpdate(product);
			}
			session.getTransaction().commit();
	 }
}
