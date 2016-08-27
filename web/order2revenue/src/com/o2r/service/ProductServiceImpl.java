package com.o2r.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.ProductDao;
import com.o2r.helper.CustomException;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;

/**
 * @author Deep Mehortra
 *
 */
@Service("productService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductDao productDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addProduct(Product product, int sellerId)
			throws CustomException {
		System.out.println("Inside add order OrderServiceImpl awb :"
				+ product.getProductName());
		productDao.addProduct(product, sellerId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addProductConfig(ProductConfig productConfig, int sellerId)throws CustomException {
		productDao.addProductConfig(productConfig, sellerId);
	}
	
	@Override
	public List<ProductConfig> listProductConfig(int sellerId, int pageNo, String condition)throws CustomException {
		return productDao.listProductConfig(sellerId, pageNo, condition);
	}
	
	@Override
	public List<ProductConfig> searchProductConfig(String field, String value,
			int sellerId, String condition) throws CustomException {		
		return productDao.searchProductConfig(field, value, sellerId, condition);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void removeSKUMapping(ProductConfig productConfig, int sellerId)
			throws CustomException {
		productDao.removeSKUMapping(productConfig, sellerId);
	}

	@Override
	public int editProduct(int sellerId, List<Product> products)throws CustomException {
		return productDao.editProduct(sellerId, products);
	}
	
	
	@Override
	public List<Product> listProducts(int sellerId, int pageNo)
			throws CustomException {
		return productDao.listProducts(sellerId, pageNo);
	}

	@Override
	public List<Product> listProducts(int sellerId) throws CustomException {
		return productDao.listProducts(sellerId);
	}

	@Override
	public Product getProduct(int orderId) throws CustomException {
		return productDao.getProduct(orderId);
	}

	@Override
	public void deleteProduct(Product product, int sellerId)
			throws CustomException {

		productDao.deleteProduct(product, sellerId);
	}

	@Override
	public void updateInventory(String sku, int currentInventory,
			int quantoAdd, int quantoSub, boolean status, int sellerId, Date orderDate)
			throws CustomException {
		productDao.updateInventory(sku, currentInventory, quantoAdd, quantoSub,
				status, sellerId, orderDate);
	}

	@Override
	public Product getProduct(String skuCode, int sellerId)
			throws CustomException {
		return productDao.getProduct(skuCode, sellerId);
	}
	
	@Override
	public Product getProductEdit(String sku, int sellerId)
			throws CustomException {
		
		return productDao.getProductEdit(sku, sellerId);
	}

	@Override
	public ProductConfig getProductConfig(String channelSKUCode,
			String channel, int sellerId) throws CustomException {
		return productDao.getProductConfig(channelSKUCode, channel, sellerId);
	}
	
	@Override
	public ProductConfig getProductConfigByAnySKU(String childSKUCode, String channel,
			int sellerId) throws CustomException {
		return productDao.getProductConfigByAnySKU(childSKUCode, channel, sellerId);
	}

	@Override
	public List<Product> getProductwithCreatedDate(Date startDate,
			Date endDate, int sellerId) throws CustomException {
		return productDao.getProductwithCreatedDate(startDate, endDate,
				sellerId);
	}

	/*@Override
	public void addSKUMapping(ProductConfig productConfig, int sellerId) {
		productDao.addSKUMapping(productConfig, sellerId);
	}*/

	@Override
	public boolean getProductwithProductConfig(int sellerId)
			throws CustomException {
		return productDao.getProductwithProductConfig(sellerId);
	}

	@Override
	public String deleteProduct(int productId, int sellerId) throws Exception {
		return productDao.deleteProduct(productId, sellerId);
	}

	@Override
	public ProductConfig getProductConfig(int productConfigId)
			throws CustomException {
		return productDao.getProductConfig(productConfigId);
	}

	@Override
	public void addProduct(List<Product> productList, int sellerId)
			throws CustomException {
		productDao.addProduct(productList, sellerId);
		
	}

	@Override
	public void addSKUMapping(List<ProductConfig> productConfigList,
			int sellerId) throws CustomException {
		productDao.addSKUMapping(productConfigList, sellerId);
		
	}
	
	@Override
	public List<String> listProductSKU(int sellerId) {
		return productDao.listProductSKU(sellerId);
	}
}