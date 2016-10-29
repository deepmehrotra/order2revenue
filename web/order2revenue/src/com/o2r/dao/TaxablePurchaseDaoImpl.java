package com.o2r.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.o2r.bean.ExpenseCategoryBean;
import com.o2r.bean.ExpensesDetails;
import com.o2r.bean.TaxablePurchaseBean;
import com.o2r.bean.ViewDetailsBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.helper.HelperClass;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;
import com.o2r.model.Product;
import com.o2r.model.Seller;
import com.o2r.model.TaxablePurchases;
import com.o2r.service.ExpenseService;
import com.o2r.service.TaxablePurchaseService;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("TaxablePurchaseDao")
public class TaxablePurchaseDaoImpl implements TaxablePurchaseDao {

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private HelperClass helperClass;
	@Autowired
	private TaxablePurchaseService taxablePurchaseService;

	static Logger log = Logger
			.getLogger(TaxablePurchaseDaoImpl.class.getName());

	private static final String queryoftaxable = "select taxPurchaseId,taxCategory,taxRate,purchaseDate,particular,basicPrice,taxAmount"
			+ " from TaxablePurchases Where seller_id=:sellerId order by createdDate desc";
	
	private static final String queryoftaxable1 = "select taxPurchaseId,taxCategory,taxRate,purchaseDate,particular,basicPrice,taxAmount"
			+ " from TaxablePurchases Where taxPurchaseId=:taxPurchaseId";

	@Override
	@SuppressWarnings("unchecked")
	public List<TaxablePurchases> listTaxablePurchase(int sellerId)
			throws CustomException {

		log.info("*** listExpenses start ***");
		List<TaxablePurchases> viewDetailsDbeanList = new ArrayList<TaxablePurchases>();
		List<Object[]> results = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query mpquery = session.createSQLQuery(queryoftaxable)
					.setParameter("sellerId", sellerId);

			results = mpquery.list();
			
			Iterator mpiterator1 = results.iterator();
			if (mpquery != null) {
				while (mpiterator1.hasNext()) {
					Object[] recordsRow = (Object[]) mpiterator1.next();
					TaxablePurchases returnviewDetailsBean = new TaxablePurchases();

					if (recordsRow[0] != null && recordsRow[2] != null) {

						returnviewDetailsBean.setTaxPurchaseId(Integer
								.parseInt(recordsRow[0] + ""));
						returnviewDetailsBean
								.setTaxCategory(recordsRow[1] + "");
						returnviewDetailsBean.setTaxRate(Float
								.parseFloat(recordsRow[2].toString()));						
						
						
						returnviewDetailsBean.setPurchaseDate((Date)recordsRow[3]);
						
						returnviewDetailsBean.setParticular(recordsRow[4]
								.toString());
						returnviewDetailsBean.setBasicPrice(Float
								.parseFloat(recordsRow[5].toString()));
						returnviewDetailsBean.setTaxAmount(Float
								.parseFloat(recordsRow[5].toString()));
						returnviewDetailsBean.setTotalAmount(Float
								.parseFloat(recordsRow[5].toString()));
						viewDetailsDbeanList.add(returnviewDetailsBean);
					}
				}
			}

		} catch (Exception e) {
			log.error("Failed! by sellerId : " + sellerId, e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.listExpensesError,
					new Date(), 3, GlobalConstant.listExpensesErrorCode, e);
		}
		log.info("*** listPurchases exit ***");
		return viewDetailsDbeanList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public int deleteTaxablePurchase(int taxCategory,  int sellerId)
			throws CustomException {

		log.info("*** deletePurchases start ***");
		Session session = null;
		
		int purchasedelete = 0;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			purchasedelete = session.createQuery("DELETE FROM TaxablePurchases WHERE taxPurchaseId = "+ taxCategory).executeUpdate();

			log.debug("Deleteing taxablepurchase purchasedelete :"
					+ purchasedelete);

		} catch (Exception e) {
			session.getTransaction().rollback();
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.deleteExpenseError,
					new Date(), 3, GlobalConstant.deleteExpenseErrorCode, e);
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		log.info("*** deleteExpense exit ***");
		return purchasedelete;
	}

	@Override
	public void addTaxablePurchase(TaxablePurchases taxablePurchases, int sellerId)
			throws CustomException {

		log.info("*** addExpense start ***");
		Session session = null;
		Seller seller = null;
		System.out.println(" i am in DAO save method");
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			
			taxablePurchases.setCreatedDate(new Date());
			//System.out.println(" The taxablePurchases"+taxablePurchases.getTaxPurchaseId());
			//System.out.println(" The taxablePurchases"+taxablePurchases.getSeller().getId());
			//System.out.println(" The taxablePurchases"+taxablePurchases.getSeller().getId());
			//System.out.println(" The taxablePurchases"+taxablePurchases.getBasicPrice());
			//System.out.println(" The taxablePurchases"+taxablePurchases.getParticular());
			
			//System.out.println(" The taxablePurchases"+taxablePurchases.getTaxAmount());
			//System.out.println(" The taxablePurchases"+taxablePurchases.getTaxCategory());
			//System.out.println(" The taxablePurchases"+taxablePurchases.getTaxRate());
			//System.out.println(" The taxablePurchases"+taxablePurchases.getTotalAmount());
			//System.out.println(" The taxablePurchases"+taxablePurchases.getPurchaseDate());	
			
			taxablePurchases.setSeller(seller);
			session.saveOrUpdate(taxablePurchases);
			session.getTransaction().commit();
			
		} catch (Exception e) {
			e.printStackTrace();
			session.getTransaction().rollback();
			log.error("Failed! by sellerId : " + sellerId, e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.addExpenseError,
					new Date(), 1, GlobalConstant.addExpenseErrorCode, e);
		} finally {
			if (session != null)
				session.close();
		}
		log.info("*** addExpense exit ***");
	}
	
	
	public TaxablePurchaseBean getTaxProduct(int taxCategoryID)
			throws CustomException{
		
		log.info("*** listExpenses start ***");
		TaxablePurchaseBean returnviewDetailsBean = new TaxablePurchaseBean();
		List<Object[]> results = null;
		int sellerId=0;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query mpquery = session.createSQLQuery(queryoftaxable1)
					.setParameter("taxPurchaseId", taxCategoryID);
			results = mpquery.list();
			Calendar cal = Calendar.getInstance();
			Date PDate = cal.getTime();
			Iterator mpiterator1 = results.iterator();
			if (mpquery != null) {
				while (mpiterator1.hasNext()) {
					Object[] recordsRow = (Object[]) mpiterator1.next();					

					if (recordsRow[0] != null && recordsRow[2] != null) {

						returnviewDetailsBean.setTaxPurchaseId(Integer.parseInt(recordsRow[0].toString()));
						returnviewDetailsBean
								.setTaxCategory(recordsRow[1] + "");
						returnviewDetailsBean.setTaxRate(Float
								.parseFloat(recordsRow[2].toString()));
						
						returnviewDetailsBean.setPurchaseDate((Date)recordsRow[3]);
						/*if (recordsRow[3] != null) {
							SimpleDateFormat format = new SimpleDateFormat(
									"MM-dd-yyyy");
							Date purchaseDate = format.parse(recordsRow[3]
									.toString());
							returnviewDetailsBean.setPurchaseDate(purchaseDate);
						}*/
						returnviewDetailsBean.setParticular(recordsRow[4]
								.toString());
						returnviewDetailsBean.setBasicPrice(Float
								.parseFloat(recordsRow[5].toString()));
						returnviewDetailsBean.setTaxAmount(Float
								.parseFloat(recordsRow[5].toString()));
						returnviewDetailsBean.setTotalAmount(Float
								.parseFloat(recordsRow[5].toString()));
						
					}
				}
			}

		} catch (Exception e) {
			log.error("Failed! by sellerId : " + sellerId, e);
			e.printStackTrace();
			throw new CustomException(GlobalConstant.listExpensesError,
					new Date(), 3, GlobalConstant.listExpensesErrorCode, e);
		}
		log.info("*** listPurchases exit ***");
		return returnviewDetailsBean;
	}
	
	

}
