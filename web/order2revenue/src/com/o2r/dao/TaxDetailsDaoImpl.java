package com.o2r.dao;



import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.TaxDetailBean;
import com.o2r.bean.ViewDetailsBean;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Category;
import com.o2r.model.Product;
import com.o2r.model.Seller;
import com.o2r.model.TaxCategory;
import com.o2r.model.TaxDetail;
import com.o2r.model.TaxablePurchases;
import com.o2r.service.CategoryService;
import com.o2r.service.SellerService;

/**
 * @author Deep Mehrotra
 *
 */ 
@Repository("taxDetailDao")
public class TaxDetailsDaoImpl implements TaxDetailsDao {

	private static final String taxTdRetriveQuery = "Select td.taxId from TaxDetail td,Seller s, "
			+ "Seller_TaxDetail st where s.id=:sellerId and s.id=st.seller_id and "
			+ "st.taxDetails_taxId=td.taxId and MONTH(td.uploadDate)=:month and td.particular=:particular";

	

	private static final String taxDetailsQuery = "select taxdetail.taxortdsCycle,taxcategory.taxCatType, sum(taxdetail.balanceRemaining)"
			+ " from taxdetail, taxcategory,seller_taxcategory  "
			+ " where  taxdetail.particular!='TDS' "
			+ " and taxdetail.uploadDate>=:sDate  and taxdetail.uploadDate<=:eDate "
			+ " and seller_taxcategory.taxCategories_taxCatId= taxcategory.taxCatId "
			+ " and taxdetail.particular=taxcategory.taxCatName "
			+ " and seller_taxcategory.seller_id=:sellerId  "
			+ " and taxdetail.taxId in (select taxDetails_taxId from seller_taxdetail where seller_id=:sellerId )"
			+ " group by taxdetail.taxortdsCycle,taxcategory.taxCatType order by taxdetail.taxortdsCycle";

	private static final String taxablePurchasesQuery = " select tp.taxcategory,sum(tp.taxAmount),tp.purchaseDate,"
			+ " Monthname(tp.purchaseDate) as month ,YEAR(tp.purchaseDate) "
			+ " from taxablepurchases tp "
			+ " where tp.seller_Id=:sellerId "			
			+ " and tp.purchaseDate>=:sDate  and tp.purchaseDate<=:eDate "
			+ " GROUP BY YEAR(tp.purchaseDate), MONTH(tp.purchaseDate),tp.taxcategory "
			+ " order by YEAR(tp.purchaseDate), MONTH(tp.purchaseDate)";

	private static final String prodCattaxDetailsQuery = "select productCategoryCode, Vat, Cst, Excise,CustomDuty, AntDumpingAgent "
			+ " from prodtaxcategory where sellerId=:sellerId";

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private SellerService sellerService;
	@Autowired
	private AreaConfigDao areaConfigDao;

	static Logger log = Logger.getLogger(TaxDetailsDaoImpl.class.getName());
	private final SimpleDateFormat formatter = new SimpleDateFormat("MMMM,yy");

	@Override
	public TaxDetail addTaxDetail(TaxDetail taxDetail, int sellerId) {

		log.info("*** addTaxDetail starts : TaxDetailsDaoImpl ****");
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			if (taxDetail.getTaxId() == 0) {
				Criteria criteria = session.createCriteria(Seller.class).add(
						Restrictions.eq("id", sellerId));
				seller = (Seller) criteria.list().get(0);
				taxDetail.setUploadDate(new Date());
				if (seller.getTaxDetails() != null)
					seller.getTaxDetails().add(taxDetail);
				session.saveOrUpdate(seller);
			} else {
				taxDetail.setUploadDate(new Date());
				session.saveOrUpdate(taxDetail);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addTaxDetailErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);

		}
		log.info("*** addTaxDetail Ends : TaxDetailsDaoImpl ****");
		return taxDetail;

	}

	@Override
	public void removeProductMapping(int tcId, int sellerId)
			throws CustomException {
		log.info("*** removeProductMapping start ***");
		try {
			System.out.println(tcId);
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Query updateQuery = session
					.createSQLQuery("UPDATE category set LST_taxCatId = null where LST_taxCatId=?");
			updateQuery.setInteger(0, tcId);

			Query updateQuery1 = session
					.createSQLQuery("UPDATE category set CST_taxCatId = null where CST_taxCatId=?");
			updateQuery1.setInteger(0, tcId);

			int updated = updateQuery.executeUpdate();
			int updated1 = updateQuery1.executeUpdate();

			log.debug("removeProductMapping updated:" + updated + " & "
					+ +updated);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			log.error("Failed! by sellerId : " + sellerId, e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.deleteManualChargesError,
					new Date(), 3, GlobalConstant.deleteManualChargesErrorCode,
					e);
		}
		log.info("*** deleteManualCharges exit ***");
	}

	@Override
	public TaxDetail addMonthlyTaxDetail(Session session, TaxDetail taxDetail,
			int sellerId) {

		log.info("*** addMonthlyTaxDetail Starts : TaxDetailsDaoImpl ****");
		log.info("***Add Monthly Tax : cat : " + taxDetail.getParticular()
				+ " Amoun :" + taxDetail.getBalanceRemaining());

		List<Integer> taxIds = null;

		Seller seller = null;
		TaxDetail existingObj = null;
		double amount = taxDetail.getBalanceRemaining();
		Date uploadDate = (Date) taxDetail.getUploadDate().clone();
		boolean status = false;
		try {
			if (session != null) {
				session.beginTransaction();
				status = true;
			} else {
				session = sessionFactory.getCurrentSession();
				session.beginTransaction();
			}
			Query gettingTaxId = session.createSQLQuery(taxTdRetriveQuery)
					.setParameter("sellerId", sellerId)
					.setParameter("month", uploadDate.getMonth() + 1)
					.setParameter("particular", taxDetail.getParticular());

			taxIds = gettingTaxId.list();

			if (taxIds != null && taxIds.size() != 0 && taxIds.get(0) != null) {

				Integer taxId = taxIds.get(0);
				existingObj = (TaxDetail) session.get(TaxDetail.class, taxId);

				existingObj.setBalanceRemaining(existingObj
						.getBalanceRemaining() + amount);
				existingObj.setUploadDate(uploadDate);
				session.saveOrUpdate(existingObj);

			} else {
				seller = (Seller) session.get(Seller.class, sellerId);
				log.info("Saving new tax object");
				taxDetail.setStatus("Due");
				taxDetail.setDescription("Tax Amount for "
						+ formatter.format(taxDetail.getUploadDate()));
				taxDetail.setTaxortds("Tax");
				taxDetail.setTaxortdsCycle(formatter.format(taxDetail
						.getUploadDate()));
				seller.getTaxDetails().add(taxDetail);
				session.saveOrUpdate(seller);
			}

			if (!status) {
				session.getTransaction().commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw e;
		}
		log.info("*** addMonthlyTaxDetail Ends : TaxDetailsDaoImpl ****");
		return taxDetail;

	}

	@Override
	public TaxDetail addMonthlyTDSDetail(Session session, TaxDetail taxDetail,
			int sellerId) {

		log.info("*** addMonthlyTDSDetail Starts : TaxDetailsDaoImpl ****");
		log.debug("Add Monthly TDS : cat : " + taxDetail.getParticular()
				+ " Amoun :" + taxDetail.getBalanceRemaining());
		Seller seller = null;
		List<Integer> taxIds = null;
		TaxDetail existingObj = null;
		double amount = taxDetail.getBalanceRemaining();
		Date tempDate = (Date) taxDetail.getUploadDate().clone();
		String particular = taxDetail.getParticular();
		boolean sessionState = false;
		try {
			if (session != null) {
				sessionState = true;
			} else {
				session = sessionFactory.getCurrentSession();

			}
			session.beginTransaction();
			Query gettingTaxId = session.createSQLQuery(taxTdRetriveQuery)
					.setParameter("sellerId", sellerId)
					.setParameter("month", tempDate.getMonth() + 1)
					.setParameter("particular", particular);

			taxIds = gettingTaxId.list();

			if (taxIds != null && taxIds.size() != 0 && taxIds.get(0) != null) {
				Integer taxId = taxIds.get(0);
				existingObj = (TaxDetail) session.get(TaxDetail.class, taxId);

				existingObj.setBalanceRemaining(existingObj
						.getBalanceRemaining() + amount);
				existingObj.setUploadDate(tempDate);
				session.saveOrUpdate(existingObj);
			} else {
				seller = (Seller) session.get(Seller.class, sellerId);
				System.out.println(" saving new TDS object ");

				taxDetail.setStatus("Due");
				taxDetail.setDescription("TDS for "
						+ formatter.format(taxDetail.getUploadDate()));
				taxDetail.setTaxortds("TDS");
				taxDetail.setParticular("TDS");
				taxDetail.setTaxortdsCycle(formatter.format(taxDetail
						.getUploadDate()));
				seller.getTaxDetails().add(taxDetail);

				session.saveOrUpdate(seller);
			}

			if (!sessionState) {
				session.getTransaction().commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("Inside exception in add monthly tdsdetails  ");
			log.error("Failed! by sellerId : " + sellerId, e);
			throw e;
		}
		log.info("*** addMonthlyTDSDetail Ends : TaxDetailsDaoImpl ****");
		return taxDetail;

	}

	@Override
	public void addPaymentTaxDetail(TaxDetail taxDetail, int sellerId)
			throws CustomException {

		log.info("*** addPaymentTaxDetail Starts : TaxDetailsDaoImpl ****");
		log.debug("addPaymentTaxDetail : cat : " + taxDetail.getParticular()
				+ " Amoun :" + taxDetail.getBalanceRemaining());
		TaxDetail existingObj = null;
		Date todaysDate = new Date();
		double amount = taxDetail.getPaidAmount();
		int taxDetailid = taxDetail.getTaxId();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			existingObj = (TaxDetail) session.get(TaxDetail.class, taxDetailid);
			existingObj.setStatus(taxDetail.getStatus());
			existingObj.setPaidAmount(amount);
			existingObj.setDateOfPayment(todaysDate);
			session.saveOrUpdate(existingObj);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addTaxPaymentDetailErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addTaxPaymentDetailError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addTaxPaymentDetailErrorCode, e);

		}
		log.info("*** addPaymentTaxDetail Ends : TaxDetailsDaoImpl ****");
	}

	@Override
	public void addStatusTDSDetail(TaxDetail taxDetail, int sellerId)
			throws CustomException {

		log.info("*** addStatusTDSDetail Starts : TaxDetailsDaoImpl ****");
		log.debug("addStatusTDSDetail : cat : " + taxDetail.getParticular()
				+ " Amount :" + taxDetail.getBalanceRemaining());
		TaxDetail existingObj = null;
		Date todaysDate = new Date();
		String status = taxDetail.getStatus();
		int taxDetailid = taxDetail.getTaxId();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			existingObj = (TaxDetail) session.get(TaxDetail.class, taxDetailid);
			existingObj.setStatus(status);
			existingObj.setDateOfPayment(todaysDate);
			session.saveOrUpdate(existingObj);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addTDSPaymentDetailErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addTDSPaymentDetailError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addTDSPaymentDetailErrorCode, e);

		}
		log.info("*** addStatusTDSDetail Ends : TaxDetailsDaoImpl ****");

	}

	// Tax Categoyr Implementation

	@Override
	public TaxCategory addTaxCategory(TaxCategory taxCategory, int sellerId)
			throws CustomException {

		log.info("*** addTaxCategory Starts : TaxDetailsDaoImpl ****");
		log.debug("addTaxCategory : cat : " + taxCategory.getTaxCatName()
				+ " Amount :" + taxCategory.getTaxPercent());
		Seller seller = null;
		Session session = null;
		try {
			System.out.println(taxCategory.getProductCategoryCST());
			System.err.println(taxCategory.getProductCategoryLST());
			session = sessionFactory.openSession();
			session.beginTransaction();
			if (taxCategory.getTaxCatId() == 0) {
				Criteria criteria = session.createCriteria(Seller.class).add(
						Restrictions.eq("id", sellerId));
				seller = (Seller) criteria.list().get(0);
				taxCategory.setUploadDate(new Date());
				if (seller.getTaxCategories() != null)
					seller.getTaxCategories().add(taxCategory);
				session.merge(seller);
			} else {
				/*
				 * Criteria criteria = session.createCriteria(Seller.class).add(
				 * Restrictions.eq("id", sellerId)); seller = (Seller)
				 * criteria.list().get(0);
				 * seller.getTaxCategories().add(taxCategory);
				 */
				taxCategory.setUploadDate(new Date());
				session.merge(taxCategory);
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxDetailListErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getTaxDetailListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxDetailListErrorCode, e);

		} finally {
			if (session != null)
				session.close();
		}

		log.info("*** addTaxCategory Ends : TaxDetailsDaoImpl ****");
		return taxCategory;

	}

	@Override
	public List<TaxDetail> listTaxDetails(int sellerId) throws CustomException {

		log.info("*** listTaxDetails Starts : TaxDetailsDaoImpl ****");
		List<TaxDetail> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getTaxDetails() != null
					&& seller.getTaxDetails().size() != 0)
				returnlist = seller.getTaxDetails();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxDetailListErrorCode2));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getTaxDetailListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxDetailListErrorCode2, e);

		}
		log.info("*** listTaxDetails Ends : TaxDetailsDaoImpl ****");
		return returnlist;
	}

	@Override
	public List<TaxDetail> listTaxDetails(int sellerId, String taxortds)
			throws CustomException {

		log.info("*** listTaxDetails by TaxorTds Starts : TaxDetailsDaoImpl ****");
		log.debug("***listTaxDetails taxortds****:" + taxortds);
		List<TaxDetail> returnlist = null;
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("taxDetails", "taxDetail",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("taxDetail.taxortds", taxortds))
					.addOrder(
							org.hibernate.criterion.Order
									.desc("taxDetail.uploadDate"))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list() != null && criteria.list().size() != 0) {

				seller = (Seller) criteria.list().get(0);
				returnlist = seller.getTaxDetails();
				log.debug(" Getting return list : " + returnlist);

			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxDetailListErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getTaxDetailListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxDetailListErrorCode, e);

		}

		log.info("*** listTaxDetails by TaxorTds Ends : TaxDetailsDaoImpl ****");		
		return returnlist;
	}

	@Override
	public List<TaxCategory> listTaxCategories(int sellerId)
			throws CustomException {

		log.info("*** listTaxCategories Starts : TaxDetailsDaoImpl ****");
		List<TaxCategory> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getTaxCategories() != null
					&& seller.getTaxCategories().size() != 0)
				returnlist = seller.getTaxCategories();

			if (returnlist != null && returnlist.size() != 0) {

				for (TaxCategory returnObj : returnlist) {
					Hibernate.initialize(returnObj.getProductCategoryCST());
					Hibernate.initialize(returnObj.getProductCategoryLST());
				}
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxCatListErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getTaxCatListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxCatListErrorCode, e);

		}
		log.info("*** listTaxCategories Ends : TaxDetailsDaoImpl ****");
		return returnlist;
	}

	@Override
	public Map<String, Float> getTaxCategoryMap(int sellerId)
			throws CustomException {

		log.info("*** getTaxCategoryMap Starts : TaxDetailsDaoImpl ****");
		Map<String, Float> returnMap = new HashMap<String, Float>();
		List<TaxCategory> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getTaxCategories() != null
					&& seller.getTaxCategories().size() != 0)
				returnlist = seller.getTaxCategories();

			if (returnlist != null && returnlist.size() != 0) {

				for (TaxCategory returnObj : returnlist) {
					if (!returnMap.containsKey(returnObj.getTaxCatName()))
						returnMap.put(returnObj.getTaxCatName(),
								returnObj.getTaxPercent());
				}
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxCatListErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getTaxCatListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxCatListErrorCode, e);

		}
		log.info("*** getTaxCategoryMap Ends : TaxDetailsDaoImpl ****");
		return returnMap;
	}

	@Override
	public TaxDetail getTaxDetail(int mcId) {

		log.info("*** getTaxDetail Starts : TaxDetailsDaoImpl ****");
		TaxDetail taxDetail = null;
		try {
			taxDetail = (TaxDetail) sessionFactory.getCurrentSession().get(
					TaxDetail.class, mcId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.info("*** getTaxDetail Ends : TaxDetailsDaoImpl ****");
		return taxDetail;
	}

	@Override
	public TaxCategory getTaxCategory(int tcId) {
		log.info("*** getTaxCategory Starts : TaxDetailsDaoImpl ****");
		TaxCategory taxCategory = null;
		try {
			taxCategory = (TaxCategory) sessionFactory.getCurrentSession().get(
					TaxCategory.class, tcId);
			Hibernate.initialize(taxCategory.getProductCategoryCST());
			Hibernate.initialize(taxCategory.getProductCategoryLST());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.info("*** getTaxCategory Ends : TaxDetailsDaoImpl ****");
		return taxCategory;
	}

	@Override

	public TaxCategory getTaxCategory(Product product, double sp, int sellerId, String zipcode) throws CustomException {

		log.info("*** getTaxCategory Starts : TaxDetailsDaoImpl ****");
		TaxCategory taxCategory = null;
		String sellerState = null;
		try {
			Category category = categoryService.getCategory(product.getCategoryName(), sellerId);
			if(category != null){
				if(category.getTaxFreePriceLimit() >= sp){
					taxCategory = getTaxCategory("Tax@0", sellerId);
				} else {
					Seller seller = sellerService.getSeller(sellerId);
					if(seller != null)
						sellerState = areaConfigDao.getStateFromZipCode(seller.getZipcode());
					String customerState = areaConfigDao.getStateFromZipCode(zipcode);
					System.out.println(" Customer state : "+customerState);
					System.out.println(" sellerState state : "+sellerState);
					if (sellerState != null && sellerState.equalsIgnoreCase(customerState) && category != null) {
						taxCategory = category.getLST();
					} else {
						taxCategory = category.getCST();
					}
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxCatListErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getTaxCatListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxCatListErrorCode, e);
		}
		log.info("*** getTaxCategory Ends : TaxDetailsDaoImpl ****");
		return taxCategory;
	}

	@Override
	public TaxCategory getTaxCategory(String catName, int sellerId)
			throws CustomException {

		log.info("*** getTaxCategory by Category NAme Starts : TaxDetailsDaoImpl ****");
		TaxCategory returnObject = null;
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("taxCategories", "taxCategory",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("taxCategory.taxCatName", catName))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			if (criteria.list() != null && criteria.list().size() != 0) {
				seller = (Seller) criteria.list().get(0);
				returnObject = seller.getTaxCategories().get(0);
				if (returnObject != null) {
					Hibernate.initialize(returnObject.getProductCategoryCST());
					Hibernate.initialize(returnObject.getProductCategoryLST());
				}
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxCategoryErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getTaxCategoryError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxCategoryErrorCode, e);

		}
		log.info("*** getTaxCategory by Category NAme Ends : TaxDetailsDaoImpl ****");
		return returnObject;
	}

	@Override
	public void deleteTaxDetail(TaxDetail taxDetail, int sellerId) {

		log.info("*** deleteTaxDetail Starts : TaxDetailsDaoImpl ****");
		log.debug("In Tax Detail delete cid " + taxDetail.getTaxId());
		try {

			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Query deleteQuery = session
					.createSQLQuery("delete from Seller_TaxDetail where seller_Id=? and TaxDetails_taxId=?");
			deleteQuery.setInteger(0, sellerId);
			deleteQuery.setInteger(1, taxDetail.getTaxId());

			int updated = deleteQuery.executeUpdate();
			int catdelete = session.createQuery(
					"DELETE FROM TaxDetail WHERE taxId = "
							+ taxDetail.getTaxId()).executeUpdate();

			log.debug("Deleteing manual charges updated:" + updated
					+ " catdelete :" + catdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			log.debug("Inside delleting taxdetail" + e.getLocalizedMessage());
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
		}
		log.info("*** deleteTaxDetail Ends : TaxDetailsDaoImpl ****");
	}

	@Override
	public void deleteTaxCategory(TaxCategory taxCategory, int sellerId)
			throws CustomException {

		log.info("*** deleteTaxCategory Starts : TaxDetailsDaoImpl ****");
		log.info("***deleteTaxCategory Start*****" + taxCategory.getTaxCatId());
		try {

			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Query deleteQuery = session
					.createSQLQuery("delete from Seller_TaxCategory where seller_Id=? and TaxCategory_taxCatId=?");
			deleteQuery.setInteger(0, sellerId);
			deleteQuery.setInteger(1, taxCategory.getTaxCatId());

			int updated = deleteQuery.executeUpdate();
			int catdelete = session.createQuery(
					"DELETE FROM TaxCategory WHERE taxCatId = "
							+ taxCategory.getTaxCatId()).executeUpdate();

			log.debug("Deleteing manual charges updated:" + updated
					+ " catdelete :" + catdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.deleteTaxCategoryErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.deleteTaxCategoryError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.deleteTaxCategoryErrorCode, e);

		}
		log.info("*** deleteTaxCategory Ends : TaxDetailsDaoImpl ****");
	}

	
	
	@Override
	public List<TaxDetailBean> listtaxDetailsOnMonth(int sellerId, Date sDate,
			Date eDate) throws CustomException {

		log.info("*** listTaxDetails by TaxorTds Starts : TaxDetailsDaoImpl ****");
		List<TaxDetail> returnlist = null;
		Seller seller = null;

		List<TaxDetailBean> viewDetailsDbeanList = new ArrayList<TaxDetailBean>();
		Session session = null;
		try {
			

			List<Object[]> results = null;

			session = sessionFactory.openSession();
			session.beginTransaction();
			Query mpquery1 = session.createSQLQuery(taxablePurchasesQuery)
					.setParameter("sellerId", sellerId)
					.setParameter("sDate", sDate)
					.setParameter("eDate", eDate);
			results = mpquery1.list();
				
			Iterator mpiterator1 = results.iterator();
			HashMap<String, Float> taxablePurchasesQueryMap = new HashMap<String, Float>();
			while (mpiterator1.hasNext()) {
				Object[] recordsRow = (Object[]) mpiterator1.next();
				String mapkey = recordsRow[0].toString() + "," + recordsRow[3]
						+ "," + recordsRow[4].toString().substring(2);
				taxablePurchasesQueryMap.put(mapkey, Float.parseFloat(recordsRow[1].toString()));				
			}
			
			Calendar startCalendar = new GregorianCalendar();
			startCalendar.setTime(sDate);
			Calendar endCalendar = new GregorianCalendar();
			endCalendar.setTime(eDate);

			int diffYear = endCalendar.get(Calendar.YEAR) - startCalendar.get(Calendar.YEAR);
			int diffMonth = 1+diffYear * 12 + endCalendar.get(Calendar.MONTH) - startCalendar.get(Calendar.MONTH);	
			Query mpquery = session.createSQLQuery(taxDetailsQuery)
					.setParameter("sellerId", sellerId)
					.setParameter("sDate", sDate)
					.setParameter("eDate", eDate);			
			results = mpquery.list();
			String oldCatType = "";
			double totalTax = 0.0d;
			Iterator mpiterator = results.iterator();		
						
			HashMap<String, Float> taxDetailsQueryMap = new HashMap<String, Float>();
			while (mpiterator.hasNext()) {
				Object[] recordsRow = (Object[]) mpiterator.next();
				String mapkey = recordsRow[1].toString() + "," + recordsRow[0].toString();
				taxDetailsQueryMap.put(mapkey, Float.parseFloat(recordsRow[2].toString()));				
			}			
		
			int itr=1;			
			do{				
					
					TaxDetailBean returnTaxDetailBean = new TaxDetailBean();				
					String currMonth=new SimpleDateFormat("MMMM").format(startCalendar.getTime());				
					String currYear=new SimpleDateFormat("yy").format(startCalendar.getTime());				
					returnTaxDetailBean.setTaxortdsCycle(currMonth+","+currYear);				
					double valTaxablePurchase  = 0.0d;
					double valTaxDetails =0.0d;				
				
					String fetchString = "LST"+ ","+ currMonth+","+currYear;					
					if(taxablePurchasesQueryMap.get(fetchString)!=null)
					valTaxablePurchase = Float.parseFloat(taxablePurchasesQueryMap.get(fetchString).toString());
					if(taxDetailsQueryMap.get(fetchString)!=null)
					valTaxDetails = Float.parseFloat(taxDetailsQueryMap.get(fetchString).toString());
					returnTaxDetailBean.setVat(valTaxDetails-valTaxablePurchase);					
				
					valTaxablePurchase  = 0.0d;
					valTaxDetails =0.0d;
					fetchString = "CST"+ ","+ currMonth+","+currYear;					
					if(taxablePurchasesQueryMap.get(fetchString)!=null)
					valTaxablePurchase = Float.parseFloat(taxablePurchasesQueryMap.get(fetchString).toString());
					if(taxDetailsQueryMap.get(fetchString)!=null)
					valTaxDetails = Float.parseFloat(taxDetailsQueryMap.get(fetchString).toString());
					returnTaxDetailBean.setCst(valTaxDetails-valTaxablePurchase);	
					
					valTaxablePurchase  = 0.0d;
					valTaxDetails =0.0d;
					fetchString = "Excise"+ ","+ currMonth+","+currYear;					
					if(taxablePurchasesQueryMap.get(fetchString)!=null)
					valTaxablePurchase = Float.parseFloat(taxablePurchasesQueryMap.get(fetchString).toString());
					if(taxDetailsQueryMap.get(fetchString)!=null)
					valTaxDetails = Float.parseFloat(taxDetailsQueryMap.get(fetchString).toString());					
					returnTaxDetailBean.setExcise(valTaxDetails-valTaxablePurchase);					
					
					valTaxablePurchase  = 0.0d;
					valTaxDetails =0.0d;
					fetchString = "CustomDuty"+ ","+ currMonth+","+currYear;					
					if(taxablePurchasesQueryMap.get(fetchString)!=null)
					valTaxablePurchase = Float.parseFloat(taxablePurchasesQueryMap.get(fetchString).toString());
					if(taxDetailsQueryMap.get(fetchString)!=null)
					valTaxDetails = Float.parseFloat(taxDetailsQueryMap.get(fetchString).toString());					
					returnTaxDetailBean.
					setCustomDuty(valTaxDetails-valTaxablePurchase);					
					
					valTaxablePurchase  = 0.0d;
					valTaxDetails =0.0d;
					fetchString = "AntiDumpityDuty"+ ","+ currMonth+","+currYear;					
					if(taxablePurchasesQueryMap.get(fetchString)!=null)
					valTaxablePurchase = Float.parseFloat(taxablePurchasesQueryMap.get(fetchString).toString());
					if(taxDetailsQueryMap.get(fetchString)!=null)
					valTaxDetails = Float.parseFloat(taxDetailsQueryMap.get(fetchString).toString());				
					returnTaxDetailBean.setAntiDumpingDuty(valTaxDetails-valTaxablePurchase);
					double totalamt = returnTaxDetailBean.getCst()+returnTaxDetailBean.getVat()+returnTaxDetailBean.getExcise()+returnTaxDetailBean.getCustomDuty()+returnTaxDetailBean.getAntiDumpingDuty();
					returnTaxDetailBean.setTotalTax(totalamt);
					viewDetailsDbeanList.add(returnTaxDetailBean);					
					startCalendar.add(Calendar.MONTH, 1);
					
			itr =itr+1;	
			}while(itr<=diffMonth);								
		} catch (Exception e) {
			log.error("Failed! by sellerId : ", e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}finally{
			session.getTransaction().commit();
			session.close();
		}
		long topsellingGPendtime = System.currentTimeMillis();
		log.info(" topsellingGPendtime time  : " + (topsellingGPendtime));
		log.info("***topsellingGPendtime ends***");		
		return viewDetailsDbeanList;
	}
	

	@Override
	public List<TaxDetailBean> getProdCategoryTaxDetails(int sellerId)
			throws CustomException {

		log.info("*** listTaxDetails by TaxorTds Starts : TaxDetailsDaoImpl ****");

		List<TaxDetailBean> viewDetailsDbeanList = new ArrayList<TaxDetailBean>();

		try {

			List<Object[]> results = null;
			sellerId = 1;

			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query mpquery = session.createSQLQuery(prodCattaxDetailsQuery)
					.setParameter("sellerId", 0);
			results = mpquery.list();
			Iterator mpiterator1 = results.iterator();

			//System.out.println(" The size" + results.size());
			if (mpquery != null) {
				while (mpiterator1.hasNext()) {
					Object[] recordsRow = (Object[]) mpiterator1.next();
					TaxDetailBean returnTaxDetailBean = new TaxDetailBean();
					returnTaxDetailBean
							.setTaxCategory(recordsRow[0].toString());
					returnTaxDetailBean.setVat(Double.parseDouble(recordsRow[1]
							.toString()));
					returnTaxDetailBean.setCst(Double.parseDouble(recordsRow[2]
							.toString()));
					returnTaxDetailBean.setExcise(Double
							.parseDouble(recordsRow[3].toString()));

					returnTaxDetailBean.setCustomDuty(Double
							.parseDouble(recordsRow[4].toString()));
					returnTaxDetailBean.setAntiDumpingDuty(Double
							.parseDouble(recordsRow[5].toString()));
					if (returnTaxDetailBean != null)
						viewDetailsDbeanList.add(returnTaxDetailBean);

				}
			}

		} catch (Exception e) {
			log.error("Failed! by sellerId : ", e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		long topsellingGPendtime = System.currentTimeMillis();
		log.info(" topsellingGPendtime time  : " + (topsellingGPendtime));

		log.info("***topsellingGPendtime ends***");

		return viewDetailsDbeanList;
	}

	@Override
	public List<TaxDetailBean> getVatTaxDetails(int sellerId)
			throws CustomException {

		log.info("*** listTaxDetails by TaxorTds Starts : TaxDetailsDaoImpl ****");

		List<TaxDetailBean> viewDetailsDbeanList = new ArrayList<TaxDetailBean>();

		try {
			
			System.out.println(" HII I AM IN VAT DETAILS METHOD");

			List<Object[]> results = null;
			// sellerId = 1;

			Session session = sessionFactory.openSession();
			session.beginTransaction();
			String taxcat = "VAT";
			Query mpquery = session.createSQLQuery(taxablePurchasesQuery)
					.setParameter("taxCategtory", taxcat);
			results = mpquery.list();
			Iterator mpiterator1 = results.iterator();

			System.out.println(" The size" + results.size());
			if (mpquery != null) {
				while (mpiterator1.hasNext()) {
					Object[] recordsRow = (Object[]) mpiterator1.next();
					TaxDetailBean returnTaxDetailBean = new TaxDetailBean();
					returnTaxDetailBean.setVat(Double.parseDouble(recordsRow[0]
							.toString()));
					viewDetailsDbeanList.add(returnTaxDetailBean);
				}
			}
			System.out.println("viewDetailsDbeanList viewDetailsDbeanList"
					+ viewDetailsDbeanList.size());

		} catch (Exception e) {
			log.error("Failed! by sellerId : ", e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		long topsellingGPendtime = System.currentTimeMillis();
		log.info(" topsellingGPendtime time  : " + (topsellingGPendtime));

		log.info("***topsellingGPendtime ends***");

		return viewDetailsDbeanList;
	}

}
