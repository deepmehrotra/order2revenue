package com.o2r.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.ProductConfigBean;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.model.Category;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.Order;
import com.o2r.model.Partner;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.ProductStockList;
import com.o2r.model.Seller;
import com.o2r.model.SellerAlerts;
import com.o2r.model.TaxCategory;
import com.o2r.service.AlertsService;
import com.o2r.service.OrderService;
import com.o2r.service.PartnerService;
import com.o2r.service.TaxDetailService;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("productDao")
public class ProductDaoImpl implements ProductDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private PartnerService partnerService;
	@Autowired
	private TaxDetailService taxDetailService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private AlertsService alertService;
	
	private final int pageSize = 500;

	private final String listProductConfig = "select pc.productSkuCode,pc.channelName,pc.channelSkuRef,"
			+ "pc.vendorSkuRef,pc.discount,pc.mrp,pc.productPrice,pc.suggestedPOPrice,pc.eossDiscountValue,pc.grossNR,"
			+ "pc.productConfigId from productconfig pc,product p,product_productconfig pp where "
			+ "p.seller_id=? and pp.Product_productId=p.productId and "
			+ "pp.productConfig_productConfigId=pc.productConfigId and pc.mrp <> 0";

	private final String listProductMapping = "select pc.productSkuCode,pc.channelName,"
			+ "pc.channelSkuRef,pc.vendorSkuRef,pc.discount,pc.mrp,pc.productPrice,pc.suggestedPOPrice,"
			+ "pc.eossDiscountValue,pc.grossNR,pc.productConfigId from productconfig pc,"
			+ "product p,product_productconfig pp where p.seller_id=? and pp.Product_productId="
			+ "p.productId and pp.productConfig_productConfigId=pc.productConfigId and pc.mrp=0";
	
	private final String listProductMappingCount = "select count(*) from productconfig pc,"
			+ "product p,product_productconfig pp where p.seller_id=? and pp.Product_productId="
			+ "p.productId and pp.productConfig_productConfigId=pc.productConfigId and pc.mrp=0";

	private final String searchProductConfig = "select pc.productSkuCode,pc.channelName,pc.channelSkuRef,"
			+ "pc.vendorSkuRef,pc.discount,pc.mrp,pc.productPrice,pc.suggestedPOPrice,pc.eossDiscountValue,pc.grossNR,"
			+ "pc.productConfigId from productconfig pc,product p,product_productconfig pp where "
			+ "p.seller_id=? and pp.Product_productId=p.productId and pp.productConfig_productConfigId=pc.productConfigId "
			+ "and pc.mrp <> 0 and pc.";

	private final String searchProductMapping = "select pc.productSkuCode,"
			+ "pc.channelName,pc.channelSkuRef,pc.vendorSkuRef,pc.discount,pc.mrp,pc.productPrice,"
			+ "pc.suggestedPOPrice,pc.eossDiscountValue,pc.grossNR,pc.productConfigId "
			+ "from productconfig pc,product p,product_productconfig pp where p.seller_id=? "
			+ "and pp.Product_productId=p.productId and pp.productConfig_productConfigId=pc.productConfigId"
			+ " and pc.mrp=0 and pc.";

	static Logger log = Logger.getLogger(ProductDaoImpl.class.getName());

	@Override
	public void addProduct(Product product, int sellerId)
			throws CustomException {

		log.info("*** addProduct Starts : ProductDaoImpl ****");
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
		// Date todayDate = new Date();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
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
				productObj.setQuantity(quant);
				session.saveOrUpdate(productObj);

			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addProductError,
					new Date(), 1, GlobalConstant.addProductErrorCode, e);
		}
		log.info("*** addProduct Ends : ProductDaoImpl ****");
	}

	@Override
	public void addProduct(List<Product> productList, int sellerId)
			throws CustomException {

		log.info("*** addProductList Starts : ProductDaoImpl ****");
		Seller seller = null;
		StringBuffer errorSkus = null;
		boolean status = true;
		Session session = null;
		Map<String, Category> categoryMap = new HashMap<String, Category>();

		if (productList != null && productList.size() != 0) {
			errorSkus = new StringBuffer();
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			for (Category category : seller.getCategories()) {
				if (!categoryMap.containsKey(category.getCatName())) {
					categoryMap.put(category.getCatName(), category);
				}
			}
			for (Product product : productList) {
				Category productcat = null;
				int productId = product.getProductId();
				String sku = product.getProductSkuCode();
				String productName = product.getProductName();
				String catName = product.getCategoryName();
				String skuChannel = product.getChannelSKU();
				long quant = product.getQuantity();
				float price = product.getProductPrice();
				Date proddate = product.getProductDate();

				// Date todayDate = new Date();
				try {

					if (productId == 0) {
						productcat = categoryMap.containsKey(catName) ? categoryMap
								.get(catName) : null;
						if (productcat != null) {
							productcat = (Category) session.get(Category.class,
									productcat.getCategoryId());
							// Hibernate.initialize(productcat.getProducts());

							product.setCategory(productcat);
							productcat.getProducts().add(product);
							productcat.setProductCount(productcat
									.getProductCount() + product.getQuantity());
							productcat
									.setSkuCount(productcat.getSkuCount() + 1);

							session.saveOrUpdate(productcat);
						}
						if (seller.getProducts() != null)
							seller.getProducts().add(product);
						product.setSeller(seller);
						session.saveOrUpdate(seller);
					} else {
						Product productObj = (Product) session.get(
								Product.class, productId);
						productObj.setProductName(productName);
						productObj.setProductDate(proddate);
						productObj.setProductPrice(price);
						productObj.setProductSkuCode(sku);
						productObj.setChannelSKU(skuChannel);
						productObj.setQuantity(quant);
						session.saveOrUpdate(productObj);

					}

				} catch (Exception e) {
					status = false;
					errorSkus.append(product.getProductSkuCode() + ",");
					log.error("Failed! by sellerId : " + sellerId, e);
					/*
					 * throw new CustomException(GlobalConstant.addProductError,
					 * new Date(), 1, GlobalConstant.addProductErrorCode, e);
					 */
				}
			}
			session.getTransaction().commit();
			session.close();
		}

		if (!status)
			throw new CustomException(errorSkus.toString(), new Date(), 1,
					GlobalConstant.addProductErrorCode, new Exception());
		log.info("*** addProduct Ends : ProductDaoImpl ****");
	}

	@Override
	public Product getProductEdit(String sku, int sellerId)
			throws CustomException {
		Product product = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));
			criteria.add(Restrictions.eq("productSkuCode", sku));

			if (criteria != null && criteria.list().size() != 0) {
				product = (Product) criteria.list().get(0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return product;
	}

	@Override
	public int editProduct(int sellerId, List<Product> products)
			throws CustomException {

		Product newProduct = null;
		StringBuffer editError = null;
		boolean status = true;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			editError = new StringBuffer();
			for (Product product : products) {
				try {
					Criteria criteria = session.createCriteria(Product.class);
					criteria.createAlias("seller", "seller",
							CriteriaSpecification.LEFT_JOIN).add(
							Restrictions.eq("seller.id", sellerId));
					criteria.add(Restrictions.eq("productSkuCode",
							product.getProductSkuCode()));
					if (criteria != null && criteria.list().size() != 0) {
						newProduct = (Product) criteria.list().get(0);
						newProduct.setProductName(product.getProductName());
						newProduct.setProductPrice(product.getProductPrice());
						newProduct.setLength(product.getLength());
						newProduct.setBreadth(product.getBreadth());
						newProduct.setHeight(product.getHeight());
						newProduct.setThreholdLimit(product.getThreholdLimit());
						newProduct.setChannelSKU(product.getChannelSKU());
						newProduct.setDeadWeight(product.getDeadWeight());
						newProduct.setVolume(product.getVolume());
						newProduct.setVolWeight(product.getVolWeight());
						session.saveOrUpdate(newProduct);
					}
				} catch (Exception e) {
					status = false;
					editError.append(product.getProductSkuCode() + ",");
					log.error("Failed! by sellerId : " + sellerId, e);
				}
			}
			session.getTransaction().commit();
			session.close();
			if (!status) {
				throw new CustomException(editError.toString(), new Date(), 1,
						"Server Error", new Exception());
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
		}
		return 1;
	}

	@Override
	public void addProductConfig(ProductConfig productConfig, int sellerId) {

		log.info("*** addProductConfig Starts : ProductDaoImpl ****");
		Product product = null;
		List<ProductConfig> productConfigs = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));
			criteria.add(Restrictions.eq("productSkuCode",
					productConfig.getProductSkuCode()));

			if (criteria.list() != null && criteria.list().size() != 0) {
				product = (Product) criteria.list().get(0);
			}

			if (product != null) {
				productConfig.setProduct(product);

				productConfig.setSp(productConfig.getMrp()
						- ((productConfig.getMrp() * productConfig
								.getDiscount()) / 100));

				Partner partner = partnerService.getPartner(
						productConfig.getChannelName(), sellerId);
				String category = product.getCategoryName();

				List<NRnReturnCharges> chargesList = partner
						.getNrnReturnConfig().getCharges();
				Map<String, Float> chargesMap = new HashMap<String, Float>();

				for (NRnReturnCharges charge : chargesList) {
					System.out.println(" Charge : " + charge.getChargeName());
					chargesMap.put(charge.getChargeName(),
							charge.getChargeAmount());
				}

				float commision = 0;
				if (partner.getNrnReturnConfig().getCommissionType() != null
						&& partner.getNrnReturnConfig().getCommissionType()
								.equals("fixed")) {
					if (chargesMap
							.containsKey(GlobalConstant.fixedCommissionPercent)) {
						commision = chargesMap
								.get(GlobalConstant.fixedCommissionPercent);
					}
				} else {
					if (chargesMap.containsKey(GlobalConstant.CommPOPrefix
							+ category)) {
						commision = chargesMap.get(GlobalConstant.CommPOPrefix
								+ category);
					}
				}
				productConfig.setCommision(commision);
				productConfig
						.setCommisionAmt((productConfig.getSp() * commision) / 100);

				List<TaxCategory> taxCategoryList = taxDetailService
						.listTaxCategories(sellerId);
				for (TaxCategory taxCategory : taxCategoryList) {

					if (partner.getNrnReturnConfig().getTaxSpType() != null
							&& partner.getNrnReturnConfig().getTaxSpType()
									.equals("fixed")) {

						String mapKey = GlobalConstant.fixedtaxSPPercent
								+ GlobalConstant.TaxCategoryPrefix
								+ taxCategory.getTaxCatName();
						if (chargesMap.containsKey(mapKey)) {
							productConfig.setTaxSpCategory(taxCategory
									.getTaxCatName());
							productConfig.setTaxSp(taxCategory.getTaxPercent());
							productConfig
									.setTaxSpAmt(productConfig.getSp()
											- (productConfig.getSp() * 100 / (100 + productConfig
													.getTaxSp())));
						}
					} else {

						String mapKey = GlobalConstant.TaxSPPrefix + category
								+ GlobalConstant.TaxCategoryPrefix
								+ taxCategory.getTaxCatName();
						if (chargesMap.containsKey(mapKey)) {
							productConfig.setTaxSpCategory(taxCategory
									.getTaxCatName());
							productConfig.setTaxSp(taxCategory.getTaxPercent());
							productConfig
									.setTaxSpAmt(productConfig.getSp()
											- (productConfig.getSp() * 100 / (100 + productConfig
													.getTaxSp())));
						}
					}

					if (partner.getNrnReturnConfig().getTaxPoType() != null
							&& partner.getNrnReturnConfig().getTaxPoType()
									.equals("fixed")) {

						String mapKey = GlobalConstant.fixedtaxPOPercent
								+ GlobalConstant.TaxCategoryPrefix
								+ taxCategory.getTaxCatName();
						if (chargesMap.containsKey(mapKey)) {
							productConfig.setTaxPoCategory(taxCategory
									.getTaxCatName());
							productConfig.setTaxPo(taxCategory.getTaxPercent());
						}

					} else {

						String mapKey = GlobalConstant.TaxPOPrefix + category
								+ GlobalConstant.TaxCategoryPrefix
								+ taxCategory.getTaxCatName();
						if (chargesMap.containsKey(mapKey)) {
							productConfig.setTaxPoCategory(taxCategory
									.getTaxCatName());
							productConfig.setTaxPo(taxCategory.getTaxPercent());
						}
					}
				}

				productConfig.setPr(productConfig.getSp()
						- productConfig.getCommisionAmt()
						- productConfig.getTaxSpAmt());

				productConfig
						.setTaxPoAmt((productConfig.getPr() * productConfig
								.getTaxPo()) / 100);

				productConfig.setSuggestedPOPrice(productConfig.getPr()
						+ productConfig.getTaxPoAmt());

				if (productConfig.getChannelName().equalsIgnoreCase(
						GlobalConstant.PCMYNTRA)) {
					productConfig.setEossDiscountValue((productConfig
							.getSuggestedPOPrice() * productConfig
							.getDiscount()) / 100);
					productConfig.setGrossNR(productConfig
							.getSuggestedPOPrice()
							- productConfig.getEossDiscountValue());
				} else {
					productConfig.setGrossNR(productConfig
							.getSuggestedPOPrice());
				}

				productConfigs = product.getProductConfig();
				if (productConfigs != null && productConfigs.size() != 0) {
					product.getProductConfig().add(productConfig);
					session.saveOrUpdate(product);
				} else {

					productConfigs.add(productConfig);
					product.setProductConfig(productConfigs);
					session.saveOrUpdate(product);
				}
			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error("Failed! by sellerId : " + sellerId, e);
			e.printStackTrace();
		}
		log.info("*** addProductConfig Ends : ProductDaoImpl ****");
	}

	@Override
	public void addSKUMapping(List<ProductConfig> productConfigList,
			int sellerId) throws CustomException {

		log.info("*** addProductConfig List Starts : ProductDaoImpl ****");
		Product product = null;
		List dbresult = null;
		boolean status = true;
		StringBuffer errorSKUS = new StringBuffer("");
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		for (ProductConfig productConfig : productConfigList) {
			try {

				Criteria criteria = session.createCriteria(Product.class);

				criteria.createAlias("seller", "seller",
						CriteriaSpecification.LEFT_JOIN).add(
						Restrictions.eq("seller.id", sellerId));
				criteria.add(Restrictions.eq("productSkuCode",
						productConfig.getProductSkuCode()));
				dbresult = criteria.list();
				if (dbresult != null && dbresult.size() != 0) {
					product = (Product) dbresult.get(0);
					if (product != null && productConfig.getProduct() == null) {
						productConfig.setProduct(product);
						product.getProductConfig().add(productConfig);
						session.saveOrUpdate(product);
					} else {
						session.saveOrUpdate(productConfig);
					}
				}

			} catch (Exception e) {
				errorSKUS.append(productConfig.getChannelSkuRef() + ",");
				status = false;
				log.error("Failed! by sellerId : " + sellerId, e);
				e.printStackTrace();
			}
		}
		session.getTransaction().commit();
		session.close();
		if (!status)
			throw new CustomException(errorSKUS.toString(), new Date(), 1,
					GlobalConstant.addProductErrorCode, new Exception());
		log.info("*** addProductConfig Ends : ProductDaoImpl ****");
	}

	@Override
	public void removeSKUMapping(ProductConfig productConfig, int sellerId)
			throws CustomException {

		log.info("*** removeSKUMapping Starts : ProductDaoImpl ****");
		Product product = null;
		List dbresult = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);

			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));
			criteria.add(Restrictions.eq("productSkuCode",
					productConfig.getProductSkuCode()));
			dbresult = criteria.list();
			if (dbresult != null && dbresult.size() != 0) {
				product = (Product) criteria.list().get(0);
				if (product != null) {
					productConfig.setProduct(product);
					List<ProductConfig> productConfigs = product
							.getProductConfig();

					if (productConfigs != null) {
						Iterator<ProductConfig> productConfigIterator = productConfigs
								.iterator();
						while (productConfigIterator.hasNext()) {
							ProductConfig tmpProductConfig = productConfigIterator
									.next();
							if (tmpProductConfig.getProductConfigId() == productConfig
									.getProductConfigId()) {
								productConfigIterator.remove();
							}
						}
					}
					product.setProductConfig(productConfigs);
					session.saveOrUpdate(product);
				}
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listProductsError,
					new Date(), 3, GlobalConstant.listProductsErrorCode, e);
		}
		log.info("*** removeSKUMapping Ends : ProductDaoImpl ****");
	}

	@Override
	public List<Product> listProducts(int sellerId, int pageNo)
			throws CustomException {

		log.info("*** listProducts Starts : ProductDaoImpl ****");
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
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listProductsError,
					new Date(), 3, GlobalConstant.listProductsErrorCode, e);

		}
		log.info("*** listProducts Ends : ProductDaoImpl ****");
		return returnlist;
	}

	@Override
	public List<ProductConfig> listProductConfig(int sellerId, int pageNo,
			String condition) throws CustomException {

		log.info("*** listProductConfig by SellerId Starts : ProductDaoImpl ****");
		List<ProductConfig> productConfigList = new ArrayList<ProductConfig>();
		ProductConfig productConfig = null;
		String targetSQL = null;
		if (condition.equals("config")) {
			targetSQL = listProductConfig;
		} else {
			targetSQL = listProductMapping;
		}
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query listQuery = session.createSQLQuery(targetSQL);
			listQuery.setInteger(0, sellerId);
			listQuery.setFirstResult(pageNo * pageSize);
			listQuery.setMaxResults(pageSize);
			if (listQuery != null && listQuery.list().size() != 0) {
				List<Object> objects = listQuery.list();
				for (Object eachObject : objects) {
					Object[] object = (Object[]) eachObject;
					System.out.println(object.length);
					productConfig = new ProductConfig();
					productConfig.setProductSkuCode((String) object[0]);
					productConfig.setChannelName((String) object[1]);
					productConfig.setChannelSkuRef((String) object[2]);
					productConfig.setVendorSkuRef((String) object[3]);
					productConfig.setDiscount((float) object[4]);
					productConfig.setMrp((double) object[5]);
					productConfig.setProductPrice((double) object[6]);
					productConfig.setSuggestedPOPrice((double) object[7]);
					productConfig.setEossDiscountValue((double) object[8]);
					productConfig.setGrossNR((double) object[9]);
					productConfig.setProductConfigId((int) object[10]);
					productConfigList.add(productConfig);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listProductsError,
					new Date(), 3, GlobalConstant.listProductsErrorCode, e);
		}
		log.info("*** listProductConfig by SellerId Ends : ProductDaoImpl ****");
		return productConfigList;
	}

	@Override
	public List<ProductConfig> searchProductConfig(String field, String value,
			int sellerId, String condition) throws CustomException {
		List<ProductConfig> productConfigList = new ArrayList<ProductConfig>();
		ProductConfig productConfig = null;
		String targetSQL = null;
		if (condition.equals("config")) {
			targetSQL = searchProductConfig + field + "=?";
		} else {
			targetSQL = searchProductMapping + field + "=?";
		}
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query listQuery = session.createSQLQuery(targetSQL);
			listQuery.setInteger(0, sellerId);
			listQuery.setString(1, value);

			if (listQuery != null && listQuery.list().size() != 0) {
				List<Object> objects = listQuery.list();
				for (Object eachObject : objects) {
					Object[] object = (Object[]) eachObject;
					System.out.println(object.length);
					productConfig = new ProductConfig();
					productConfig.setProductSkuCode((String) object[0]);
					productConfig.setChannelName((String) object[1]);
					productConfig.setChannelSkuRef((String) object[2]);
					productConfig.setVendorSkuRef((String) object[3]);
					productConfig.setDiscount((float) object[4]);
					productConfig.setMrp((double) object[5]);
					productConfig.setProductPrice((double) object[6]);
					productConfig.setSuggestedPOPrice((double) object[7]);
					productConfig.setEossDiscountValue((double) object[8]);
					productConfig.setGrossNR((double) object[9]);
					productConfig.setProductConfigId((int) object[10]);
					productConfigList.add(productConfig);
				}
			}
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getProductError,
					new Date(), 3, GlobalConstant.getProductErrorCode, e);
		}
		return productConfigList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> listProducts(int sellerId) throws CustomException {

		log.info("*** listProducts by SellerId Starts : ProductDaoImpl ****");
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
			for (Product product : returnlist)
				Hibernate.initialize(product.getProductConfig());
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listProductsError,
					new Date(), 3, GlobalConstant.listProductsErrorCode, e);

		}
		log.info("*** listProducts by SellerId Ends : ProductDaoImpl ****");
		return returnlist;
	}

	@Override
	public Product getProduct(int productId) {

		log.info("*** getProduct by productId Starts : ProductDaoImpl ****");
		Product product = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			product = (Product) session.get(Product.class, productId);
			Hibernate.initialize(product.getProductConfig());
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
			// throw new CustomException(GlobalConstant.getSellerByIdError, new
			// Date(), 3, GlobalConstant.getSellerByIdErrorCode, e);

		}
		log.info("*** getProduct by productId Ends : ProductDaoImpl ****");
		return product;
	}

	@Override
	public Product getProduct(String skuCode, int sellerId)
			throws CustomException {

		log.info("*** getProduct by skuCode Starts : ProductDaoImpl ****");
		Product returnObject = null;
		List returnlist = null;
		log.debug(" ***Insid get product from sku ***" + skuCode);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("productConfig", "productConfig",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));
			Criterion rest1 = Restrictions.eq("productSkuCode", skuCode);
			Criterion rest2 = Restrictions.eq("productConfig.channelSkuRef", skuCode);					
			criteria.add(Restrictions.or(rest1, rest2)).setResultTransformer(
					CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			returnlist = criteria.list();

			if (returnlist != null && returnlist.size() != 0) {

				returnObject = (Product) returnlist.get(0);
			} else {
				log.debug("Product sku " + skuCode + " not found");
			}
			log.debug(" Return object :#### " + returnObject);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getProductError,
					new Date(), 3, GlobalConstant.getProductErrorCode, e);

		}
		log.info("*** getProduct by skuCode Ends : ProductDaoImpl ****");
		return returnObject;

	}

	@Override
	public ProductConfig getProductConfig(String SKUCode,
			String channel, int sellerId) throws CustomException {

		log.info("*** getProductConfig Starts : ProductDaoImpl ****");
		ProductConfig returnObject = null;
		List returnlist = null;
		log.debug(" ***Insid get product config from sku and channel ***"
				+ SKUCode + " - " + channel);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(ProductConfig.class);
			criteria.createAlias("product", "product",CriteriaSpecification.LEFT_JOIN);
			Criterion res1 = Restrictions.or(Restrictions.eq("channelSkuRef", SKUCode).ignoreCase(),Restrictions.eq("vendorSkuRef", SKUCode).ignoreCase());
			
			criteria.add(Restrictions.eq("channelName", channel).ignoreCase())
					.add(Restrictions.or(Restrictions.eq("productSkuCode", SKUCode).ignoreCase(),res1))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.createAlias("product.seller", "seller",CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			returnlist = criteria.list();

			if (returnlist != null && returnlist.size() != 0) {

				returnObject = (ProductConfig) returnlist.get(0);
			} else {
				log.debug("Product sku " + SKUCode + " not found");
			}
			log.debug(" Return object :#### " + returnObject);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getProductError,
					new Date(), 3, GlobalConstant.getProductErrorCode, e);

		}
		log.info("*** getProductConfig Ends : ProductDaoImpl ****");
		return returnObject;

	}
	
	@Override
	public ProductConfig getProductConfigByAnySKU(String childSKUCode,
			String channel, int sellerId) throws CustomException {

		log.info("*** getProductConfigByAnySKU Starts : ProductDaoImpl ****");
		ProductConfig returnObject = null;
		List returnlist = null;
		log.debug(" ***Insid get product config from any sku and channel ***"
				+ childSKUCode + " - " + channel);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(ProductConfig.class);
			criteria.createAlias("product", "product",
					CriteriaSpecification.LEFT_JOIN);
			Criterion rest1 = Restrictions.eq("channelSkuRef", childSKUCode).ignoreCase();
			Criterion rest2 = Restrictions.eq("vendorSkuRef", childSKUCode).ignoreCase();
			criteria.add(Restrictions.eq("channelName", channel).ignoreCase())
					.add(Restrictions.or(rest1, rest2))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.createAlias("product.seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			returnlist = criteria.list();

			if (returnlist != null && returnlist.size() != 0) {

				returnObject = (ProductConfig) returnlist.get(0);
			} else {
				log.debug("Product sku " + childSKUCode + " not found");
			}
			log.debug(" Return object :#### " + returnObject);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getProductError,
					new Date(), 3, GlobalConstant.getProductErrorCode, e);

		}
		log.info("*** getProductConfig Ends : ProductDaoImpl ****");
		return returnObject;

	}

	@Override
	public ProductConfig getProductConfig(int productConfigId)
			throws CustomException {

		log.info("*** getProductConfig Starts : ProductDaoImpl ****");
		ProductConfig returnObject = null;
		List returnlist = null;
		log.debug(" ***Inside get product config from ID ***" + productConfigId);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(ProductConfig.class);
			criteria.createAlias("product", "product",
					CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("productConfigId", productConfigId));

			returnlist = criteria.list();

			if (returnlist != null && returnlist.size() != 0) {

				returnObject = (ProductConfig) returnlist.get(0);
			} else {
				log.debug("Product sku " + productConfigId + " not found");
			}
			log.debug(" Return object :#### " + returnObject);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.getProductError,
					new Date(), 3, GlobalConstant.getProductErrorCode, e);

		}
		log.info("*** getProductConfig Ends : ProductDaoImpl ****");
		return returnObject;

	}

	@Override
	public void deleteProduct(Product product, int sellerId)
			throws CustomException {

		log.info("*** deleteProduct Starts : ProductDaoImpl ****");
		log.debug(" In Category delete sku id " + product.getProductSkuCode());
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

			log.debug("  Deleteing category updated:" + updated
					+ " catdelete :" + catdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.deleteProductError,
					new Date(), 3, GlobalConstant.deleteProductErrorCode, e);
		}
		log.info("*** deleteProduct Ends : ProductDaoImpl ****");
	}

	@Override
	public List<Product> getProductwithCreatedDate(Date startDate,
			Date endDate, int sellerId) throws CustomException {

		log.info("*** getProductwithCreatedDate Starts : ProductDaoImpl ****");
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
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(
					GlobalConstant.getProductwithCreatedDateError, new Date(),
					3, GlobalConstant.getProductwithCreatedDateErrorCode, e);
		}
		log.info("*** getProductwithCreatedDate Ends : ProductDaoImpl ****");
		return productlist;
	}

	@Override
	public boolean getProductwithProductConfig(int sellerId)
			throws CustomException {

		log.info("*** getProductwithProductConfig Starts : ProductDaoImpl ****");
		List<Product> productlist = null;
		boolean result = false;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.isNotNull("productConfig"))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			productlist = criteria.list();
			if (productlist != null)
				result = true;

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(
					GlobalConstant.getProductwithCreatedDateError, new Date(),
					3, GlobalConstant.getProductwithCreatedDateErrorCode, e);
		}
		log.info("*** getProductwithCreatedDate Ends : ProductDaoImpl ****");
		return result;
	}

	@Override
	public void updateInventory(String sku, int currentInventory,
			int quantoAdd, int quantoSub, boolean status, int sellerId,
			Date orderDates) throws CustomException {

		log.info("*** updateInventory Starts : ProductDaoImpl ****");
		log.debug(" Inside inventory update method :" + sku + " quantoAdd "
				+ quantoAdd + "quantoSub " + quantoSub);
		Product product = null;
		Session session = null;
		Date orderDate = orderDates;
		Date monthopeningdate = new Date();
		monthopeningdate.setDate(1);
		long currentValue = 0;
		long catproductcount = 0;

		try {
			if (sku != null)
				product = getProduct(sku, sellerId);

			log.debug(" sku after getting from : "
					+ product.getProductSkuCode());
			log.debug(" Cuurent inventory value : " + product.getQuantity());
			session = sessionFactory.openSession();
			session.getTransaction().begin();

			if (product != null) {
				log.debug(" Sku code from procut :"
						+ product.getProductSkuCode());
				currentValue = product.getQuantity();
				catproductcount = product.getCategory().getProductCount();
				if (currentInventory != 0) {
					product.setQuantity(currentInventory);
				} else if (quantoAdd != 0) {
					product.setQuantity(product.getQuantity() + quantoAdd);
					product.getCategory()
							.setProductCount(
									product.getCategory().getProductCount()
											+ quantoAdd);
				} else if (quantoSub != 0) {
					log.debug(" Subtracting quantity from product quantity : "
							+ quantoSub);
					product.setQuantity(product.getQuantity() - quantoSub);
					product.getCategory()
							.setProductCount(
									product.getCategory().getProductCount()
											- quantoSub);
					log.debug(" Quantity after sub :" + product.getQuantity());
				}

				// Updating Closing stock for a month for each SKU
				product = (Product) session.merge(product);
				log.debug(" Quantity after merging product object : "
						+ product.getQuantity());
				Hibernate.initialize(product.getClosingStocks());
				List<ProductStockList> stocklist = product.getClosingStocks();
				if (stocklist != null)
					Collections.sort(stocklist);
				log.debug("Incomming Date : " + orderDate);
				log.debug(" Size of stocklist: " + stocklist.size());
				// log.debug("Updated month of StockList : "+stocklist.get(0).getMonth()
				// +"-"+stocklist.get(0).getYear());
				// log.debug("Created Month : "+(orderDate.getMonth()+1)+"-"+orderDate.getYear());
				if (stocklist != null
						&& stocklist.size() != 0
						&& stocklist.get(0).getMonth() == (orderDate.getMonth() + 1)
						&& stocklist.get(0).getYear() == orderDate.getYear()) {
					log.debug("No need to Update The stockList...");
				} else {
					ProductStockList newObj = new ProductStockList();
					newObj.setStockAvailable(currentValue);
					newObj.setCreatedDate(monthopeningdate);
					newObj.setUpdatedate(monthopeningdate.getDate());
					newObj.setMonth(monthopeningdate.getMonth() + 1);
					log.debug("Newly Saved One : " + newObj.getMonth());
					newObj.setYear(monthopeningdate.getYear());
					newObj.setPrice(product.getProductPrice());
					product.getClosingStocks().add(newObj);
				}

				// Updating Closing stock for a month for each product Category
				Category productcat = product.getCategory();
				if (productcat != null) {
					if (productcat.getOsUpdate() != null) {
						if ((productcat.getOsUpdate().getMonth() <= orderDate
								.getMonth() && productcat.getOsUpdate()
								.getYear() <= orderDate.getYear())
								|| (productcat.getOsUpdate().getMonth() > orderDate
										.getMonth() && productcat.getOsUpdate()
										.getYear() < orderDate.getYear())) {
							productcat.setOpeningStock(catproductcount);
							productcat.setOsUpdate(orderDate);
							productcat.setProductCount(productcat
									.getProductCount() + quantoAdd - quantoSub);
						}
					} else {
						productcat
								.setOpeningStock(productcat.getProductCount());
						productcat.setOsUpdate(orderDate);
						productcat.setProductCount(productcat.getProductCount()
								+ quantoAdd - quantoSub);
					}
					session.saveOrUpdate(productcat);
				}
				session.saveOrUpdate(product);
				if(product != null){
					if(product.getThreholdLimit() > product.getQuantity()){
						SellerAlerts sellerAlert = new SellerAlerts();
						sellerAlert.setAlertDate(new Date());
						sellerAlert.setAlertType("Inventory");
						sellerAlert.setMessage(GlobalConstant.InventoryMsg+" : "+product.getCategoryName());
						sellerAlert.setStatus("unread");
						alertService.saveAlerts(sellerAlert, sellerId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.updateInventoryError,
					new Date(), 3, GlobalConstant.updateInventoryErrorCode, e);

		} finally {
			log.debug(" Commiting inventory update");
			session.getTransaction().commit();
			session.close();
		}
		log.info("*** updateInventory Ends : ProductDaoImpl ****");
	}

	@Override
	public String deleteProduct(int productId, int sellerId) throws Exception {
		log.info("$$$ deleteProduct Starts : ProductDaoImpl $$$");
		Product product = null;
		List<ProductConfig> productConfigs = null;
		List<Order> orders = null;
		Category category = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			product = (Product) session.get(Product.class, productId);
			if (product != null) {
				productConfigs = product.getProductConfig();
				if (productConfigs != null && productConfigs.size() != 0) {
					return "Please Delete Config Mapping Before Deleteing The Product !";
				} else {
					orders = orderService.findOrders("productSkuCode",
							product.getProductSkuCode(), sellerId, false, true);
					if (orders != null && orders.size() != 0) {
						return "Due To Order Existence !";
					} else {
						category = product.getCategory();
						if (category != null) {
							category.setProductCount(category.getProductCount()
									- product.getQuantity());
							category.setSkuCount(category.getSkuCount() - 1);
							session.merge(category);

							try {
								int countCat_Pro = session.createSQLQuery(
										"delete from category_product where products_productId="
												+ productId).executeUpdate();
								System.out.println(countCat_Pro);
								Query query = session
										.createSQLQuery("select closingStocks_stockId from product_productstocklist where Product_productId="
												+ productId);
								/* query.executeUpdate(); */
								List<Integer> ids = query.list();
								System.out.println(ids);

								int countPro_StockList = session
										.createSQLQuery(
												"delete from product_productstocklist where Product_productId="
														+ productId)
										.executeUpdate();
								System.out.println(countPro_StockList);
								int count = 0;
								for (int id : ids) {
									session.createSQLQuery(
											"delete from productstocklist where stockId="
													+ id).executeUpdate();
									count++;
								}
								System.out.println(count);

								int countPro = session.createSQLQuery(
										"delete from product where productId="
												+ productId).executeUpdate();
								System.out.println(countPro);
								session.getTransaction().commit();
								return "Deleted";
							} catch (Exception e) {
								log.error("Failed! by sellerId : " + sellerId,
										e);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed! by sellerId : " + sellerId, e);
		}
		log.info("$$$ deleteProduct Ends : ProductDaoImpl $$$");
		return "Server Issue";
	}

	@Override
	public List<String> listProductSKU(int sellerId) {
		List<String> SKUList = null;
		Session session = null;
		List<String> criterialist = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();

			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));
			/*criteria.createAlias("productConfig", "productConfig",
					CriteriaSpecification.LEFT_JOIN);*/
			criteria.setProjection(Projections.property("productSkuCode"));
			criterialist = criteria.list();
			if (criterialist != null && criteria.list().size() != 0) {
				SKUList = criterialist;
			}
			System.out.println("Product Lit : " + SKUList.size());
			/*criterialist = null;
			Criteria criteriaformappingsku = session
					.createCriteria(Product.class);
			criteriaformappingsku.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));
			criteriaformappingsku.createAlias("productConfig", "productConfig",
					CriteriaSpecification.LEFT_JOIN);
			criteriaformappingsku.setProjection(Projections
					.property("productConfig.channelSkuRef"));
			criterialist = criteriaformappingsku.list();
			if (criterialist != null && criteria.list().size() != 0) {
				SKUList.addAll(criterialist);
			}*/
			System.out.println(SKUList.size());
		} catch (Exception e) {
			log.error("Failed! by sellerId : " + sellerId, e);
		} finally {
			if (session != null)
				session.close();
		}
		return SKUList;
	}
	
	@Override
	public int productCount(int sellerId) {
		int noOfProduct = 0;
		Session session=null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("seller.id", sellerId));
			criteria.setProjection(Projections.rowCount());
			if (criteria != null)
				noOfProduct = (int) criteria.uniqueResult();
			session.getTransaction().commit();
			System.out.println(noOfProduct);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId + " In Product Count",e);
		} finally {
			if(session != null)
				session.close();
		}
		return noOfProduct;
	}

	@Override
	public long productMappingCount(int sellerId) {
		BigInteger noOfProductMappings = new BigInteger("0");
		Session session=null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query listQuery = session.createSQLQuery(listProductMappingCount);
			listQuery.setInteger(0, sellerId);				
			noOfProductMappings=(BigInteger) listQuery.uniqueResult();
			System.out.println(noOfProductMappings);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId + " In Product Count",e);
		} finally {
			if(session != null)
				session.close();
		}
		return noOfProductMappings.longValue();
	}
}
