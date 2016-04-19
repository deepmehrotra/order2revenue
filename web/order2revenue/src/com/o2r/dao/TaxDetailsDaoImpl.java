package com.o2r.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Seller;
import com.o2r.model.TaxCategory;
import com.o2r.model.TaxDetail;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("taxDetailDao")
public class TaxDetailsDaoImpl implements TaxDetailsDao {

	private static final String taxTdRetriveQuery = "Select td.taxId from TaxDetail td,Seller s, Seller_TaxDetail st where s.id=:sellerId and s.id=st.seller_id and st.taxDetails_taxId=td.taxId and MONTH(td.uploadDate)=:month and td.particular=:particular";

	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(TaxDetailsDaoImpl.class.getName());
	private final SimpleDateFormat formatter = new SimpleDateFormat("MMMM,yy");

	@Override
	public TaxDetail addTaxDetail(TaxDetail taxDetail, int sellerId) {
		log.info("***addTaxDetail Start****");
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
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addTaxDetailErrorCode));
			log.error(e);

		}
		log.info("***addTaxDetail Exit****");
		return taxDetail;

	}

	/*
	 * @Override public TaxDetail addMonthlyTaxDetail(Session session,TaxDetail
	 * taxDetail , int sellerId) { log.info("***addMonthlyTaxDetail Start****");
	 * log
	 * .debug("***Add Monthly TDS : cat : "+taxDetail.getParticular()+" Amoun :"
	 * +taxDetail.getBalanceRemaining());
	 * 
	 * // sellerId=4; Seller seller=null; TaxDetail existingObj=null; Date
	 * todaysDate=new Date(); double amount=taxDetail.getBalanceRemaining();
	 * boolean status=false; //Session session=null; try { // Session
	 * session=sessionFactory.openSession(); if(session!=null) {
	 * session.beginTransaction(); status=true; } else {
	 * 
	 * session=sessionFactory.getCurrentSession(); session.beginTransaction(); }
	 * 
	 * 
	 * DetachedCriteria maxQuery = DetachedCriteria.forClass( Seller.class );
	 * maxQuery.createAlias("taxDetails", "taxDetail",
	 * CriteriaSpecification.LEFT_JOIN)
	 * .add(Restrictions.eq("taxDetail.particular", taxDetail.getParticular()))
	 * .setProjection(Projections.max("taxDetail.uploadDate"))
	 * .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	 * 
	 * 
	 * Criteria
	 * criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id",
	 * sellerId)); criteria.createAlias("taxDetails", "taxDetail",
	 * CriteriaSpecification.LEFT_JOIN)
	 * .add(Restrictions.eq("taxDetail.particular", taxDetail.getParticular()))
	 * .add( Property.forName( "taxDetail.uploadDate" ).eq( maxQuery ))
	 * .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	 * 
	 * if(criteria.list()!=null&&criteria.list().size()!=0) {
	 * seller=(Seller)criteria.list().get(0);
	 * if(seller.getTaxDetails()!=null&&seller.getTaxDetails().size()!=0) {
	 * existingObj=seller.getTaxDetails().get(0);
	 * 
	 * if(existingObj.getUploadDate().getMonth()!=taxDetail.getUploadDate().getMonth
	 * ()) { taxDetail.setStatus("Due");
	 * taxDetail.setDescription("Tax Amount for "
	 * +formatter.format(taxDetail.getUploadDate()));
	 * taxDetail.setTaxortds("Tax");
	 * taxDetail.setTaxortdsCycle(formatter.format(taxDetail.getUploadDate()));
	 * //taxDetail.setUploadDate(todaysDate);
	 * seller.getTaxDetails().add(taxDetail); session.saveOrUpdate(seller); }
	 * else
	 * if(existingObj.getUploadDate().getMonth()==taxDetail.getUploadDate().
	 * getMonth()) {
	 * existingObj.setBalanceRemaining(existingObj.getBalanceRemaining
	 * ()+amount); existingObj.setUploadDate(taxDetail.getUploadDate());
	 * session.saveOrUpdate(existingObj); } } } else {
	 * seller=(Seller)session.get(Seller.class, sellerId);
	 * log.debug("Saving new tax object"); taxDetail.setStatus("Due");
	 * taxDetail.
	 * setDescription("Tax Amount for "+formatter.format(taxDetail.getUploadDate
	 * ())); taxDetail.setTaxortds("Tax");
	 * taxDetail.setTaxortdsCycle(formatter.format(taxDetail.getUploadDate()));
	 * seller.getTaxDetails().add(taxDetail); session.saveOrUpdate(seller); }
	 * 
	 * if(!status) session.getTransaction().commit(); // session.close(); }
	 * catch (Exception e) {
	 * log.equals("Inside exception in add monthly taxdetails  "); log.error(e);
	 * throw e; } log.info("***addMonthlyTaxDetail Exit****"); return taxDetail;
	 * 
	 * 
	 * }
	 */

	@Override
	public TaxDetail addMonthlyTaxDetail(Session session, TaxDetail taxDetail,
			int sellerId) {
		log.info("***addMonthlyTaxDetail Start****");
		log.debug("***Add Monthly TDS : cat : " + taxDetail.getParticular()
				+ " Amoun :" + taxDetail.getBalanceRemaining());

		// sellerId=4;
		List<Integer> taxIds = null;

		Seller seller = null;
		TaxDetail existingObj = null;
		Date todaysDate = new Date();
		double amount = taxDetail.getBalanceRemaining();
		boolean status = false;
		// Session session=null;
		try {
			// Session session=sessionFactory.openSession();
			if (session != null) {
				session.beginTransaction();
				status = true;
			} else {

				session = sessionFactory.getCurrentSession();
				session.beginTransaction();
			}
			Query gettingTaxId = session
					.createSQLQuery(taxTdRetriveQuery)
					.setParameter("sellerId", sellerId)
					.setParameter("month",
							taxDetail.getUploadDate().getMonth() + 1)
					.setParameter("particular", taxDetail.getParticular());

			System.out
					.println("**************************************************************** Month : "
							+ taxDetail.getUploadDate().getMonth() + 1);
			System.out
					.println("**************************************************************** Seller Id : "
							+ sellerId);
			System.out
					.println("**************************************************************** Particulars : "
							+ taxDetail.getParticular());

			taxIds = gettingTaxId.list();

			/*
			 * DetachedCriteria maxQuery = DetachedCriteria.forClass(
			 * Seller.class ); maxQuery.createAlias("taxDetails", "taxDetail",
			 * CriteriaSpecification.LEFT_JOIN)
			 * .add(Restrictions.eq("taxDetail.particular",
			 * taxDetail.getParticular()))
			 * .setProjection(Projections.max("taxDetail.uploadDate"))
			 * .setResultTransformer
			 * (CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			 * 
			 * //.add(Restrictions.le("taxDetail.uploadDate",
			 * taxDetail.getUploadDate()))
			 * 
			 * Criteria
			 * criteria=session.createCriteria(Seller.class).add(Restrictions
			 * .eq("id", sellerId)); criteria.createAlias("taxDetails",
			 * "taxDetail", CriteriaSpecification.LEFT_JOIN)
			 * .add(Restrictions.eq("taxDetail.particular",
			 * taxDetail.getParticular())) .add( Property.forName(
			 * "taxDetail.uploadDate" ).le( maxQuery ))
			 * .setResultTransformer(CriteriaSpecification
			 * .DISTINCT_ROOT_ENTITY);
			 */

			if (taxIds != null && taxIds.size() != 0 && taxIds.get(0) != null) {

				Integer taxId = taxIds.get(0);
				System.out
						.println("**************************************************************** Tax Id : "
								+ taxId);
				existingObj = (TaxDetail) session.get(TaxDetail.class, taxId);

				existingObj.setBalanceRemaining(existingObj
						.getBalanceRemaining() + amount);
				existingObj.setUploadDate(taxDetail.getUploadDate());
				session.saveOrUpdate(existingObj);

			} else {
				seller = (Seller) session.get(Seller.class, sellerId);
				log.debug("Saving new tax object");
				taxDetail.setStatus("Due");
				taxDetail.setDescription("Tax Amount for "
						+ formatter.format(taxDetail.getUploadDate()));
				taxDetail.setTaxortds("Tax");
				taxDetail.setTaxortdsCycle(formatter.format(taxDetail
						.getUploadDate()));
				seller.getTaxDetails().add(taxDetail);
				session.saveOrUpdate(seller);
			}

			if (!status)
				session.getTransaction().commit();
			// session.close();
		} catch (Exception e) {
			log.equals("Inside exception in add monthly taxdetails  ");
			log.error(e.getStackTrace());
			throw e;
		}
		log.info("***addMonthlyTaxDetail Exit****");
		return taxDetail;

	}

	@Override
	public TaxDetail addMonthlyTDSDetail(Session session, TaxDetail taxDetail,
			int sellerId) {
		log.info("***addMonthlyTDSDetail Start****");
		log.debug("Add Monthly TDS : cat : " + taxDetail.getParticular()
				+ " Amoun :" + taxDetail.getBalanceRemaining());
		Seller seller = null;
		TaxDetail existingObj = null;
		double amount = taxDetail.getBalanceRemaining();
		boolean sessionState = false;
		try {
			// Session session=sessionFactory.openSession();
			if (session != null) {
				sessionState = true;
			} else
				session = sessionFactory.getCurrentSession();

			session.beginTransaction();

			DetachedCriteria maxQuery = DetachedCriteria.forClass(Seller.class);
			maxQuery.createAlias("taxDetails", "taxDetail",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("taxDetail.particular", "TDS"))
					.add(Restrictions.eq("taxDetail.taxortds", "TDS"))
					.setProjection(Projections.max("taxDetail.uploadDate"))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("taxDetails", "taxDetail",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("taxDetail.particular", "TDS"))
					.add(Restrictions.eq("taxDetail.taxortds", "TDS"))
					.add(Property.forName("taxDetail.uploadDate").eq(maxQuery))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list() != null && criteria.list().size() != 0) {
				seller = (Seller) criteria.list().get(0);
				if (seller.getTaxDetails() != null
						&& seller.getTaxDetails().size() != 0) {
					existingObj = seller.getTaxDetails().get(0);

					if (existingObj.getUploadDate().getMonth() != taxDetail
							.getUploadDate().getMonth()) {
						taxDetail.setStatus("Due");
						taxDetail.setDescription("TDS for "
								+ formatter.format(taxDetail.getUploadDate()));
						taxDetail.setTaxortds("TDS");
						taxDetail.setTaxortdsCycle(formatter.format(taxDetail
								.getUploadDate()));
						taxDetail.setParticular("TDS");
						// taxDetail.setUploadDate(todaysDate);
						seller.getTaxDetails().add(taxDetail);
						session.saveOrUpdate(seller);
					} else if (existingObj.getUploadDate().getMonth() == taxDetail
							.getUploadDate().getMonth()) {
						existingObj.setBalanceRemaining(existingObj
								.getBalanceRemaining() + amount);
						existingObj.setUploadDate(taxDetail.getUploadDate());
						session.saveOrUpdate(existingObj);
					}
				}
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
				// taxDetail.setUploadDate(todaysDate);
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
			log.error(e);
			throw e;
		}
		log.info("***addMonthlyTDSDetail Exit****");
		return taxDetail;

	}

	@Override
	public void addPaymentTaxDetail(TaxDetail taxDetail, int sellerId)
			throws CustomException {
		log.info("***addPaymentTaxDetail Start****");
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
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addTaxPaymentDetailErrorCode));
			log.error(e);
			throw new CustomException(GlobalConstant.addTaxPaymentDetailError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addTaxPaymentDetailErrorCode, e);

		}

		log.info("***addPaymentTaxDetail Exit****");
	}

	@Override
	public void addStatusTDSDetail(TaxDetail taxDetail, int sellerId)
			throws CustomException {

		log.info("***addStatusTDSDetail Start****");
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
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addTDSPaymentDetailErrorCode));
			log.error(e);
			throw new CustomException(GlobalConstant.addTDSPaymentDetailError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addTDSPaymentDetailErrorCode, e);

		}

		log.info("***addStatusTDSDetail Exit****");

	}

	// Tax Categoyr Implementation

	@Override
	public TaxCategory addTaxCategory(TaxCategory taxCategory, int sellerId)
			throws CustomException {

		log.info("***addTaxCategory Start****");
		log.debug("addTaxCategory : cat : " + taxCategory.getTaxCatName()
				+ " Amount :" + taxCategory.getTaxPercent());
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			if (taxCategory.getTaxCatId() == 0) {
				Criteria criteria = session.createCriteria(Seller.class).add(
						Restrictions.eq("id", sellerId));
				seller = (Seller) criteria.list().get(0);
				taxCategory.setUploadDate(new Date());
				if (seller.getTaxCategories() != null)
					seller.getTaxCategories().add(taxCategory);
				session.saveOrUpdate(seller);
			} else {
				taxCategory.setUploadDate(new Date());
				session.saveOrUpdate(taxCategory);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxDetailListErrorCode));
			log.error(e);
			throw new CustomException(GlobalConstant.getTaxDetailListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxDetailListErrorCode, e);

		}

		log.info("***addTaxCategory Exit****");
		return taxCategory;

	}

	@Override
	public List<TaxDetail> listTaxDetails(int sellerId) throws CustomException {
		// sellerId=4;
		log.info("***listTaxDetails Start****");
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
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxDetailListErrorCode2));
			log.error(e);
			throw new CustomException(GlobalConstant.getTaxDetailListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxDetailListErrorCode2, e);

		}

		log.info("***listTaxDetails Exit****");
		return returnlist;
	}

	@Override
	public List<TaxDetail> listTaxDetails(int sellerId, String taxortds)
			throws CustomException {
		log.info("***listTaxDetails Start****");
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

			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxDetailListErrorCode));
			log.error(e);
			throw new CustomException(GlobalConstant.getTaxDetailListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxDetailListErrorCode, e);

		}

		log.info("***listTaxDetails with Status Exit****");
		return returnlist;
	}

	@Override
	public List<TaxCategory> listTaxCategories(int sellerId)
			throws CustomException {
		log.info("***listTaxDetails Start****");
		List<TaxCategory> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getTaxCategories() != null
					&& seller.getTaxCategories().size() != 0)
				returnlist = seller.getTaxCategories();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxCatListErrorCode));
			log.error(e);
			throw new CustomException(GlobalConstant.getTaxCatListError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxCatListErrorCode, e);

		}

		log.info("***listTaxCategories Exit****");
		return returnlist;
	}

	@Override
	public TaxDetail getTaxDetail(int mcId) {
		log.info("***getTaxDetails Start****");
		return (TaxDetail) sessionFactory.getCurrentSession().get(
				TaxDetail.class, mcId);
	}

	@Override
	public TaxCategory getTaxCategory(int tcId) {
		log.info("***getTaxCategory Start****");
		return (TaxCategory) sessionFactory.getCurrentSession().get(
				TaxCategory.class, tcId);
	}

	@Override
	public TaxCategory getTaxCategory(String catName, int sellerId)
			throws CustomException {
		TaxCategory returnObject = null;
		Seller seller = null;
		log.info("***getTaxCategory Start*****" + catName);

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
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getTaxCategoryErrorCode));
			log.error(e);
			throw new CustomException(GlobalConstant.getTaxCategoryError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.getTaxCategoryErrorCode, e);

		}

		log.info("***listTaxCategories Exit****");

		return returnObject;

	}

	@Override
	public void deleteTaxDetail(TaxDetail taxDetail, int sellerId) {
		System.out.println(" In Tax Detail delete cid " + taxDetail.getTaxId());
		// sellerId=4;
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

			System.out.println("  Deleteing manual charges updated:" + updated
					+ " catdelete :" + catdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			System.out.println(" Inside delleting taxdetail"
					+ e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void deleteTaxCategory(TaxCategory taxCategory, int sellerId)
			throws CustomException {
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

			System.out.println("  Deleteing manual charges updated:" + updated
					+ " catdelete :" + catdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.deleteTaxCategoryErrorCode));
			log.error(e);
			throw new CustomException(GlobalConstant.deleteTaxCategoryError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.deleteTaxCategoryErrorCode, e);

		}
		log.info("***deleteTaxCategory End*****");
	}

}