package com.o2r.dao;

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
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Category;
import com.o2r.model.NRnReturnCharges;
import com.o2r.model.Order;
import com.o2r.model.Partner;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.ProductStockList;
import com.o2r.model.Seller;
import com.o2r.model.TaxCategory;
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

	private final int pageSize = 500;

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
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.addProductError,
					new Date(), 1, GlobalConstant.addProductErrorCode, e);
		}
		log.info("*** addProduct Ends : ProductDaoImpl ****");
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
				if (partner.getNrnReturnConfig().getCommissionType()
						.equals("fixed")) {
					commision = chargesMap
							.get(GlobalConstant.fixedCommissionPercent);
				} else {
					commision = chargesMap.get(GlobalConstant.CommPOPrefix
							+ category);
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
			log.error("Failed!", e);
			e.printStackTrace();
		}
		log.info("*** addProductConfig Ends : ProductDaoImpl ****");
	}

	@Override
	public void addSKUMapping(ProductConfig productConfig, int sellerId) {

		log.info("*** addProductConfig Starts : ProductDaoImpl ****");
		Product product = null;
		List dbresult = null;
		/*
		 * boolean status=true; String
		 * prodSKU=productConfig.getProductSkuCode(); String
		 * channelREF=productConfig.getChannelSkuRef(); String
		 * channel=productConfig.getChannelName();
		 */
		// List<ProductConfig> productConfigs = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Product.class);
			/*
			 * criteria.createAlias("seller", "seller",
			 * CriteriaSpecification.LEFT_JOIN).add(
			 * Restrictions.eq("seller.id", sellerId));
			 * criteria.add(Restrictions.eq("productSkuCode", prodSKU)); product
			 * = (Product) criteria.list().get(0); if(product !=
			 * null&&product.getProductConfig()!=null) for(ProductConfig procon
			 * : product.getProductConfig()) {
			 * if(procon.getChannelName()!=null&&
			 * procon.getChannelName().equals(channel)) {
			 * procon.setChannelSkuRef(channelREF); status = false; } } if
			 * (product != null&&status) { productConfig.setProduct(product);
			 * product.getProductConfig().add(productConfig);
			 * 
			 * }
			 */
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
					product.getProductConfig().add(productConfig);
					// session.saveOrUpdate(product);
				}
				session.saveOrUpdate(product);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error("Failed!", e);
			e.printStackTrace();
		}
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
					List<ProductConfig> productConfigs = product.getProductConfig();
					
					if (productConfigs != null) {
						Iterator<ProductConfig> productConfigIterator = productConfigs.iterator();
						while (productConfigIterator.hasNext()) {
							ProductConfig tmpProductConfig = productConfigIterator.next();
							if (tmpProductConfig.getProductConfigId() == 
									productConfig.getProductConfigId()) {
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
			log.error("Failed!", e);
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
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.listProductsError,
					new Date(), 3, GlobalConstant.listProductsErrorCode, e);

		}
		log.info("*** listProducts Ends : ProductDaoImpl ****");
		return returnlist;
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
			log.error("Failed!", e);
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
			Criterion rest2 = Restrictions.eq("productConfig.channelSkuRef",
					skuCode);
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
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.getProductError,
					new Date(), 3, GlobalConstant.getProductErrorCode, e);

		}
		log.info("*** getProduct by skuCode Ends : ProductDaoImpl ****");
		return returnObject;

	}

	@Override
	public ProductConfig getProductConfig(String channelSKUCode,
			String channel, int sellerId) throws CustomException {

		log.info("*** getProductConfig Starts : ProductDaoImpl ****");
		ProductConfig returnObject = null;
		List returnlist = null;
		log.debug(" ***Insid get product config from sku and channel ***"
				+ channelSKUCode + " - " + channel);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(ProductConfig.class);
			criteria.createAlias("product", "product",
					CriteriaSpecification.LEFT_JOIN);
			criteria.add(Restrictions.eq("channelName", channel).ignoreCase())
					.add(Restrictions.eq("channelSkuRef", channelSKUCode)
							.ignoreCase())
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
				log.debug("Product sku " + channelSKUCode + " not found");
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
	public ProductConfig getProductConfig(int productConfigId)
			throws CustomException {

		log.info("*** getProductConfig Starts : ProductDaoImpl ****");
		ProductConfig returnObject = null;
		List returnlist = null;
		log.debug(" ***Inside get product config from ID ***"
				+ productConfigId);
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
			log.error("Failed!", e);
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
			log.error("Failed!", e);
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
			log.error("Failed!", e);
			throw new CustomException(
					GlobalConstant.getProductwithCreatedDateError, new Date(),
					3, GlobalConstant.getProductwithCreatedDateErrorCode, e);
		}
		log.info("*** getProductwithCreatedDate Ends : ProductDaoImpl ****");
		return result;
	}

	@Override
	public void updateInventory(String sku, int currentInventory,
			int quantoAdd, int quantoSub, boolean status, int sellerId)
			throws CustomException {

		log.info("*** updateInventory Starts : ProductDaoImpl ****");
		log.debug(" Inside inventory update method :" + sku + " quantoAdd "
				+ quantoAdd + "quantoSub " + quantoSub);
		Product product = null;
		Session session = null;
		Date todayDate = new Date();
		long currentValue = 0;

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
				log.debug(" Size of stocklist: " + stocklist.size());
				if (stocklist != null && stocklist.size() != 0
						&& stocklist.get(0).getMonth() == todayDate.getMonth()
						&& stocklist.get(0).getYear() == todayDate.getYear()) {
					log.debug("No need to Update The stockList...");
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

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.updateInventoryError,
					new Date(), 3, GlobalConstant.updateInventoryErrorCode, e);

		} finally {

			System.out.println(" Commiting inventory update");
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
								/*
								 * session.createSQLQuery(
								 * "delete from productstocklist where stockId=:ids"
								 * ) .setParameterList("ids",
								 * ids).executeUpdate();
								 */

								int countPro = session.createSQLQuery(
										"delete from product where productId="
												+ productId).executeUpdate();
								System.out.println(countPro);
								session.getTransaction().commit();
								return "Deleted";
							} catch (Exception e) {
								log.error("Failed!", e);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("Failed!", e);
		}
		log.info("$$$ deleteProduct Ends : ProductDaoImpl $$$");
		return "Server Issue";
	}

}
