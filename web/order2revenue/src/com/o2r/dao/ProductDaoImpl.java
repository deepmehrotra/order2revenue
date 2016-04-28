package com.o2r.dao;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Category;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.ProductStockList;
import com.o2r.model.Seller;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	private final int pageSize = 500;

	static Logger log = Logger.getLogger(ProductDaoImpl.class.getName());

	@Override
	public void addProduct(Product product, int sellerId)throws CustomException {
		// sellerId=4;
		Seller seller = null;
		Category productcat = null;
		int productId = product.getProductId();
		String sku = product.getProductSkuCode();
		String productName = product.getProductName();
		String catName = product.getCategoryName();
		String skuChannel = product.getChannelSKU();
		long quant = product.getQuantity();
		float price = product.getProductPrice();
		Date proddate = product.getProductDate();
		Date todayDate = new Date();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			for (Category category : seller.getCategories()) {
				if (category.getCatName().equalsIgnoreCase(catName)) {
					productcat = category;
				}
			}
			if (productId == 0) {

				if (productcat != null) {
					product.setCategory(productcat);
					productcat.getProducts().add(product);
					productcat.setProductCount(productcat.getProductCount()
							+ product.getQuantity());
					productcat.setSkuCount(productcat.getSkuCount() + 1);

					session.saveOrUpdate(productcat);
				}
				if (seller.getProducts() != null)
					seller.getProducts().add(product);
				product.setSeller(seller);
				session.saveOrUpdate(seller);
			} else {
				Product productObj = (Product) session.get(Product.class,
						productId);
				productObj.setProductName(productName);
				productObj.setProductDate(proddate);
				productObj.setProductPrice(price);
				productObj.setProductSkuCode(sku);
				productObj.setChannelSKU(skuChannel);
				// productObj.getCategory().setProductCount(productObj.getCategory().getProductCount()-productObj.getQuantity()+quant);
				productObj.setQuantity(quant);
				session.saveOrUpdate(productObj);

			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.addProductError, new Date(), 1, GlobalConstant.addProductErrorCode, e);
			
			//System.out.println("Inside exception  " + e.getLocalizedMessage());
		}

	}
	
	@Override
	public void addProductConfig(ProductConfig productConfig, int sellerId) {
		
		Product product=null;
		List<ProductConfig> productConfigs=null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(Restrictions.eq("seller.id", sellerId));
					 criteria.add(Restrictions.eq("productName", productConfig.getProductName()));
			product=(Product)criteria.list().get(0);
			if(product != null){
				productConfig.setProduct(product);
				productConfigs=product.getProductConfig();
				if(productConfigs != null && productConfigs.size() != 0){
					product.getProductConfig().add(productConfig);
					session.saveOrUpdate(product);
				}else{
					
					productConfigs.add(productConfig);
					product.setProductConfig(productConfigs);
					session.saveOrUpdate(product);
				}
			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Product> listProducts(int sellerId, int pageNo)throws CustomException {
		// sellerId=4;
		List<Product> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order.desc("productDate"));
			criteria.setFirstResult(pageNo * pageSize);
			criteria.setMaxResults(pageSize);
			returnlist = criteria.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			
			log.error(e);
			throw new CustomException(GlobalConstant.listProductsError, new Date(), 3, GlobalConstant.listProductsErrorCode, e);
			/*System.out.println(" Exception in getting order list :"
					+ e.getLocalizedMessage());*/
		}
		return returnlist;
	}

	@Override
	public List<Product> listProducts(int sellerId)throws CustomException {
		// sellerId=4;
		List<Product> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order.desc("productDate"));
			returnlist = criteria.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			
			log.error(e);
			throw new CustomException(GlobalConstant.listProductsError, new Date(), 3, GlobalConstant.listProductsErrorCode, e);
			/*System.out.println(" Exception in getting order list :"
					+ e.getLocalizedMessage());*/
		}
		return returnlist;
	}

	@Override
	public Product getProduct(int productId) {
		return (Product) sessionFactory.getCurrentSession().get(Product.class,
				productId);
	}

	@Override
	public Product getProduct(String skuCode, int sellerId)throws CustomException {
		Product returnObject = null;
		List returnlist = null;
		System.out.println(" ***Insid get product from sku ***" + skuCode);

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.eq("productSkuCode", skuCode)
							.ignoreCase())
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			returnlist = criteria.list();

			if (returnlist != null && returnlist.size() != 0) {

				returnObject = (Product) returnlist.get(0);
			} else {
				System.out.println("Product sku " + skuCode + " not found");
			}
			System.out.println(" Return object :#### " + returnObject);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.getProductError, new Date(), 3, GlobalConstant.getProductErrorCode, e);
			
			/*System.out.println(" Product   DAO IMPL :"
					+ e.getLocalizedMessage());*/
			
		}

		return returnObject;

	}

	@Override
	public void deleteProduct(Product product, int sellerId)throws CustomException {
		System.out.println(" In Category delete sku id "
				+ product.getProductSkuCode());
		// sellerId=4;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query deleteQuery = session
					.createSQLQuery("delete from Seller_Product where seller_Id=? and product_productId=?");
			deleteQuery.setInteger(0, sellerId);
			deleteQuery.setInteger(1, product.getProductId());

			int updated = deleteQuery.executeUpdate();
			int catdelete = session.createQuery(
					"DELETE FROM Product WHERE productId = "
							+ product.getProductId()).executeUpdate();

			System.out.println("  Deleteing category updated:" + updated
					+ " catdelete :" + catdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.deleteProductError, new Date(), 3, GlobalConstant.deleteProductErrorCode, e);
			/*System.out.println(" Inside delleting order"
					+ e.getLocalizedMessage());*/
			
		}

	}

	@Override
	public List<Product> getProductwithCreatedDate(Date startDate,
			Date endDate, int sellerId)throws CustomException {

		List<Product> productlist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions
							.between("productDate", startDate, endDate))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			productlist = criteria.list();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			
			log.error(e);
			throw new CustomException(GlobalConstant.getProductwithCreatedDateError, new Date(), 3, GlobalConstant.getProductwithCreatedDateErrorCode, e);
			/*System.out.println(" Inside find  product by date  exception :"
					+ e.getMessage());*/
			
		}
		return productlist;
	}

	@Override
	public void updateInventory(String sku, int currentInventory,
			int quantoAdd, int quantoSub, boolean status, int sellerId)throws CustomException {
		System.out.println(" Inside inventory update method :" + sku
				+ " quantoAdd " + quantoAdd + "quantoSub " + quantoSub);
		Product product = null;
		Session session = null;
		Date todayDate = new Date();
		long currentValue = 0;

		try {
			if (sku != null)
				product = getProduct(sku, sellerId);

			System.out.println(" sku after getting from : "
					+ product.getProductSkuCode());
			System.out.println(" Cuurent inventory value : "
					+ product.getQuantity());

			/*
			 * if(status) { session=sessionFactory.openSession(); } else {
			 * session=sessionFactory.getCurrentSession(); } if(session==null)
			 */
			session = sessionFactory.openSession();

			session.getTransaction().begin();

			if (product != null) {
				System.out.println(" Sku code from procut :"
						+ product.getProductSkuCode());
				currentValue = product.getQuantity();
				if (currentInventory != 0) {
					product.setQuantity(currentInventory);
				} else if (quantoAdd != 0) {
					product.setQuantity(product.getQuantity() + quantoAdd);
					product.getCategory()
							.setProductCount(
									product.getCategory().getProductCount()
											+ quantoAdd);
				} else if (quantoSub != 0) {
					System.out
							.println(" Subtracting quantity from product quantity : "
									+ quantoSub);
					product.setQuantity(product.getQuantity() - quantoSub);
					product.getCategory()
							.setProductCount(
									product.getCategory().getProductCount()
											- quantoSub);
					System.out.println(" Quantity after sub :"
							+ product.getQuantity());
				}

				// Updating Closing stock for a month for each SKU
				product = (Product) session.merge(product);
				System.out.println(" Quantity after merging product object : "
						+ product.getQuantity());
				Hibernate.initialize(product.getClosingStocks());
				List<ProductStockList> stocklist = product.getClosingStocks();
				if (stocklist != null)
					Collections.sort(stocklist);
				System.out.println(" Size of stocklist: " + stocklist.size());
				if (stocklist != null && stocklist.size() != 0
						&& stocklist.get(0).getMonth() == todayDate.getMonth()
						&& stocklist.get(0).getYear() == todayDate.getYear()) {
					System.out.println(" No need to update stock list");
				} else {
					ProductStockList newObj = new ProductStockList();
					newObj.setStockAvailable(currentValue);
					newObj.setCreatedDate(todayDate);
					newObj.setUpdatedate(todayDate.getDate());
					newObj.setMonth(todayDate.getMonth());
					newObj.setYear(todayDate.getYear());
					newObj.setPrice(product.getProductPrice());
					product.getClosingStocks().add(newObj);
				}

				// Updating Closing stock for a month for each product Category
				Category productcat = product.getCategory();
				if (productcat != null) {
					if (productcat.getOsUpdate() != null) {
						if (productcat.getOsUpdate().getMonth() < todayDate
								.getMonth()) {
							productcat.setOpeningStock(productcat
									.getProductCount());
							productcat.setOsUpdate(todayDate);
						} else if (productcat.getOsUpdate().getMonth() == 12) {
							if (todayDate.getMonth() == 1) {
								productcat.setOpeningStock(productcat
										.getProductCount());
								productcat.setOsUpdate(todayDate);

							}
						}
					} else {
						productcat
								.setOpeningStock(productcat.getProductCount());
						productcat.setOsUpdate(todayDate);
					}
					session.saveOrUpdate(productcat);
				}
				session.saveOrUpdate(product);
			}

			/*
			 * session.getTransaction().commit(); session.close();
			 */
		} catch (Exception e) {
			
			log.error(e);
			throw new CustomException(GlobalConstant.updateInventoryError, new Date(), 3, GlobalConstant.updateInventoryErrorCode, e);
			/*System.out.println(" Exception in updating inventories  :"+ e.getMessage());*/
		} finally {

			System.out.println(" Commiting inventory update");
			session.getTransaction().commit();
			session.close();

		}

	}

}