package com.o2r.dao;

import java.util.Date;
import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;

/**
 * @author Deep Mehrotra
 *
 */
public interface ProductDao {

	 public void addProduct(Product product,int sellerId)throws CustomException;
	 
	 public void addProductConfig(ProductConfig productConfig,int sellerId);

	 public List<Product> listProducts(int sellerId,int pageNo)throws CustomException;

	 public List<Product> listProducts(int sellerId)throws CustomException;

	 public Product getProduct(int orderId)throws CustomException;

	 public void deleteProduct(Product product,int sellerId)throws CustomException;

	 public void updateInventory(String sku , int currentInventory , int quantoAdd , int quantoSub,boolean status,int sellerId)throws CustomException;

	public Product getProduct(String skuCode, int sellerId)throws CustomException;

	public List<Product> getProductwithCreatedDate(Date startDate, Date endDate,int sellerId)throws CustomException;

}