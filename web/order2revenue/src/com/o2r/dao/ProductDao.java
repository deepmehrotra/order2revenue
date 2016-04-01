package com.o2r.dao;

import java.util.Date;
import java.util.List;

import com.o2r.model.Product;

/**
 * @author Deep Mehrotra
 *
 */
public interface ProductDao {

	 public void addProduct(Product product,int sellerId);

	 public List<Product> listProducts(int sellerId,int pageNo);

	 public List<Product> listProducts(int sellerId);

	 public Product getProduct(int orderId);

	 public void deleteProduct(Product product,int sellerId);

	 public void updateInventory(String sku , int currentInventory , int quantoAdd , int quantoSub,boolean status,int sellerId);

	public Product getProduct(String skuCode, int sellerId);

	public List<Product> getProductwithCreatedDate(Date startDate, Date endDate,
			int sellerId);

}