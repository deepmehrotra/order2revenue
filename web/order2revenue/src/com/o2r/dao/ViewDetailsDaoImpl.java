package com.o2r.dao;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
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
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.CustomerDBaseBean;
import com.o2r.bean.DashboardBean;
import com.o2r.bean.ViewDetailsBean;
import com.o2r.helper.ConverterClass;
import com.o2r.helper.CustomException;
import com.o2r.model.Customer;
import com.o2r.model.Expenses;
import com.o2r.model.Order;
import com.o2r.model.Seller;
import com.o2r.model.TaxDetail;
import com.o2r.service.ViewDetailsService;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Deep Mehrotra
 *
 *
 */
@Repository("ViewDetailsDao")
public class ViewDetailsDaoImpl implements ViewDetailsDao {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ViewDetailsService viewDetailsService;
	String pattern = "yyyy-mm-dd";
	SimpleDateFormat format = new SimpleDateFormat(pattern);
	static Logger log = Logger.getLogger(ViewDetailsDaoImpl.class.getName());

	private static final String topSellingSKUQuery = "Select ot.productskucode,sum(ot.quantity) as quantity,"
			+ " sum(ort.returnorrtoqty) as retnqty, sum(ot.quantity-ort.returnorrtoqty) as netsale"
			+ " from Order_Table ot, OrderReturn  ort"
			+ " where ort.returnId=ot.orderReturnOrRTO_returnId"
			+ " and ot.shippedDate between :startDate  AND :endDate and ot.seller_Id= :sellerId"
			+ " GROUP BY ot.productskucode order by quantity";

	private static final String topSellingSKUCityQuery = "select state.state_name, sum(ot.netSaleQuantity) as b"
			+ " from Order_Table ot, customer ct, tbl_area area, tbl_city city, tbl_states state"
			+ " where ot.netSaleQuantity >0 and  ot.customer_customerId=ct.customerId"
			+ " and ot.shippedDate between :startDate  AND :endDate and ot.seller_Id= :sellerId"
			+ " and ct.zipcode = area.zipcode and area.city_id=city.id and state.id= city.state_id "
			+ " group by state.id  order by b desc";

	@SuppressWarnings("deprecation")
	@Override
	public List<ViewDetailsBean> getTopSKUDetailsToday(Date startDate,
			Date endDate, int sellerId) {

		log.info("***top selling SKU details  starts***");
		List<Object[]> results = null;
		Map<String, Double> gpMonthly = new LinkedHashMap<>();

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String startDate1 = "31-101-2016";
			String endDate1 = "31-10-2016";
			startDate = sdf.parse(startDate1);
			endDate = sdf.parse(endDate1);
			sellerId = 1;

			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query mpquery = session.createSQLQuery(topSellingSKUQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", 1);
			results = mpquery.list();

			Iterator mpiterator1 = results.iterator();
			if (mpquery != null) {
				while (mpiterator1.hasNext()) {
					Object[] recordsRow = (Object[]) mpiterator1.next();

					ViewDetailsBean returnviewDetailsBean = new ViewDetailsBean();

					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {

						returnviewDetailsBean.setSkucode(recordsRow[0]
								.toString());
						returnviewDetailsBean.setNetSaleQty(Integer
								.parseInt(recordsRow[1].toString()));
						returnviewDetailsBean.setReturnSaleQty(Integer
								.parseInt(recordsRow[2].toString()));
						returnviewDetailsBean.setGrossSaleQty(Integer
								.parseInt(recordsRow[3].toString()));
						viewDetailsDbeanList.add(returnviewDetailsBean);
					}
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

	@SuppressWarnings("deprecation")
	@Override
	public List<ViewDetailsBean> getTopSKUDetailsQuarterly(Date startDate,
			Date endDate, int sellerId, String selectedQuarter) {

		log.info("***TopSKUDetailsQuarterly starts***");
		List<Object[]> results = null;
		Map<String, Double> gpMonthly = new LinkedHashMap<>();

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();
		String startDate1, endDate1;

		try {

			Calendar cl = Calendar.getInstance();
			if (selectedQuarter.equals("Fisrst")) {

				startDate1 = "01-Jan-" + cl.get(Calendar.YEAR);
				endDate1 = "31-Mar-" + cl.get(Calendar.YEAR);

			} else if (selectedQuarter.equals("Second")) {

				startDate1 = "01-Apr-" + cl.get(Calendar.YEAR);
				endDate1 = "30-Jun-" + cl.get(Calendar.YEAR);

			} else if (selectedQuarter.equals("Third")) {

				startDate1 = "01-Jul-" + cl.get(Calendar.YEAR);
				endDate1 = "30-Sep-" + cl.get(Calendar.YEAR);

			} else {

				startDate1 = "01-Oct-" + cl.get(Calendar.YEAR);
				endDate1 = "31-Dec-" + cl.get(Calendar.YEAR);
			}

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

			startDate = sdf.parse(startDate1);
			endDate = sdf.parse(endDate1);
			sellerId = 1;

			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query mpquery = session.createSQLQuery(topSellingSKUQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);

			results = mpquery.list();

			Iterator mpiterator1 = results.iterator();
			if (mpquery != null) {
				while (mpiterator1.hasNext()) {
					Object[] recordsRow = (Object[]) mpiterator1.next();

					ViewDetailsBean returnviewDetailsBean = new ViewDetailsBean();

					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {

						returnviewDetailsBean.setSkucode(recordsRow[0]
								.toString());
						returnviewDetailsBean.setNetSaleQty(Integer
								.parseInt(recordsRow[1].toString()));
						returnviewDetailsBean.setReturnSaleQty(Integer
								.parseInt(recordsRow[2].toString()));
						returnviewDetailsBean.setGrossSaleQty(Integer
								.parseInt(recordsRow[3].toString()));

						viewDetailsDbeanList.add(returnviewDetailsBean);
					}
				}
			}

		} catch (Exception e) {
			log.error("Failed! by sellerId : ", e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		long gpqmonthlyGPendtime = System.currentTimeMillis();
		log.info(" TopSKUDetailsQuarterly time  : " + (gpqmonthlyGPendtime));

		log.info("***TopSKUDetailsQuarterly ends***");

		return viewDetailsDbeanList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ViewDetailsBean> getTopSKUDetailsAnnualy(Date startDate,
			Date endDate, int sellerId, String selectedYear) {

		log.info("***getTopSKUDetailsAnnualy starts***");
		List<Object[]> results = null;
		Map<String, Double> gpMonthly = new LinkedHashMap<>();

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();
		String startDate1, endDate1;

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			Calendar cl = Calendar.getInstance();

			startDate1 = "01-Jan-" + selectedYear;
			endDate1 = "31-Dec-" + selectedYear;

			startDate = sdf.parse(startDate1);
			endDate = sdf.parse(endDate1);
			sellerId = 1;

			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query mpquery = session.createSQLQuery(topSellingSKUQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);

			results = mpquery.list();

			Iterator mpiterator1 = results.iterator();
			if (mpquery != null) {
				while (mpiterator1.hasNext()) {
					Object[] recordsRow = (Object[]) mpiterator1.next();

					ViewDetailsBean returnviewDetailsBean = new ViewDetailsBean();

					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {

						returnviewDetailsBean.setSkucode(recordsRow[0]
								.toString());
						returnviewDetailsBean.setNetSaleQty(Integer
								.parseInt(recordsRow[1].toString()));
						returnviewDetailsBean.setReturnSaleQty(Integer
								.parseInt(recordsRow[2].toString()));
						returnviewDetailsBean.setGrossSaleQty(Integer
								.parseInt(recordsRow[3].toString()));

						viewDetailsDbeanList.add(returnviewDetailsBean);
					}
				}
			}

		} catch (Exception e) {
			log.error("Failed! by sellerId : ", e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		long gpqmonthlyGPendtime = System.currentTimeMillis();
		log.info(" getTopSKUDetailsAnnualy time  : " + (gpqmonthlyGPendtime));

		log.info("***getTopSKUDetailsAnnualy ends***");

		return viewDetailsDbeanList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ViewDetailsBean> getTopSKUDetailsMonthly(Date startDate,
			Date endDate, int sellerId, String selectedmonth) {

		log.info("***grossProfitMonthly starts***");

		List<Object[]> results = null;
		Map<String, Double> gpMonthly = new LinkedHashMap<>();

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");

			Calendar cl = Calendar.getInstance();

			String startDate1 = "01-" + selectedmonth + "-"
					+ cl.get(Calendar.YEAR);
			String endDate1 = "31-" + selectedmonth + "-"
					+ cl.get(Calendar.YEAR);
			startDate = sdf.parse(startDate1);
			endDate = sdf.parse(endDate1);
			sellerId = 1;

			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query mpquery = session.createSQLQuery(topSellingSKUQuery)
					.setParameter("startDate", startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);

			results = mpquery.list();

			Iterator mpiterator1 = results.iterator();
			if (mpquery != null) {
				while (mpiterator1.hasNext()) {
					Object[] recordsRow = (Object[]) mpiterator1.next();

					ViewDetailsBean returnviewDetailsBean = new ViewDetailsBean();

					if (recordsRow[0] != null && recordsRow[1] != null
							&& recordsRow[2] != null) {

						returnviewDetailsBean.setSkucode(recordsRow[0]
								.toString());
						returnviewDetailsBean.setNetSaleQty(Integer
								.parseInt(recordsRow[1].toString()));
						returnviewDetailsBean.setReturnSaleQty(Integer
								.parseInt(recordsRow[2].toString()));
						returnviewDetailsBean.setGrossSaleQty(Integer
								.parseInt(recordsRow[3].toString()));
						viewDetailsDbeanList.add(returnviewDetailsBean);
					}
				}
			}

		} catch (Exception e) {
			log.error("Failed! by sellerId : ", e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		long gpqmonthlyGPendtime = System.currentTimeMillis();
		log.info(" getTopSKUDetailsMonthly time  : " + (gpqmonthlyGPendtime));

		log.info("***getTopSKUDetailsMonthly ends***");

		return viewDetailsDbeanList;
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ViewDetailsBean> getTopSKUCityDetails(Date startDate,
			Date endDate, int sellerId) {

		log.info("***getTopSKUCityDetails starts***");
		List<Object[]> results = null;
		Map<String, Double> gpMonthly = new LinkedHashMap<>();

		List<ViewDetailsBean> viewDetailsDbeanList = new ArrayList<ViewDetailsBean>();

		try {

			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
					
			Calendar cal = Calendar.getInstance();
			startDate = cal.getTime();
			cal.add(Calendar.MONTH, -6);
			endDate = cal.getTime();		
			
			sellerId = 1;
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query mpquery = session.createSQLQuery(topSellingSKUCityQuery)
					.setParameter("startDate",startDate)
					.setParameter("endDate", endDate)
					.setParameter("sellerId", sellerId);
			
			results = mpquery.list();

			Iterator mpiterator1 = results.iterator();
			if (mpquery != null) {
				while (mpiterator1.hasNext()) {
					Object[] recordsRow = (Object[]) mpiterator1.next();

					ViewDetailsBean returnviewDetailsBean = new ViewDetailsBean();

					if (recordsRow[0] != null && recordsRow[1] != null) {

						returnviewDetailsBean.setSkucode(recordsRow[0]
								.toString());
						returnviewDetailsBean.setNetSaleQty(Integer
								.parseInt(recordsRow[1].toString()));
						viewDetailsDbeanList.add(returnviewDetailsBean);
					}
				}
			}

		} catch (Exception e) {
			log.error("Failed! by sellerId : ", e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		long gpqmonthlyGPendtime = System.currentTimeMillis();
		log.info(" getTopSKUCityDetails time  : " + (gpqmonthlyGPendtime));

		log.info("***getTopSKUCityDetails ends***");

		return viewDetailsDbeanList;
	}

}
