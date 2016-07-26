package com.o2r.utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.Seller;

@Repository("dataRestoreUtility")
public class DataRestore {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Autowired
	private SessionFactory sessionFactoryforBackup;
	static Logger log = Logger.getLogger(DataRestore.class.getName());
	
	private static final String listPartnersQuery = "select partners_pcId from seller_partner "
			+ "where seller_id=:sellerId";
	private static final String listtaxDetailsQuery = "select taxDetails_taxId from seller_taxdetail"
			+ " where seller_id=:sellerId";
	private static final String listtaxCategoryQuery = "select taxCategories_taxCatId from "
			+ "seller_taxcategory where seller_id=:sellerId";
	private static final String listmanualChargesQuery = "select manualCharges_mcId from "
			+ "seller_manualCharges where seller_id=:sellerId";
	private static final String listexpenseCatQuery = "select expcatid from seller_expcat "
			+ "where id =:sellerId";
	private static final String listaccountTransactionsQuery = "select accountTransactions_accTransId "
			+ "from selleraccount_accounttransaction sa,seller s where "
			+ "sa.sellerAccount_selaccId =  s.sellerAccount_selaccId and s.id=:sellerId";
	private static final String listpartnerNRConfigQuery = "select nrnReturnConfig_configid from "
			+ "partner where pcId in ("
			+ "select partners_pcId from "
			+ "seller_partner where seller_id=:sellerId)";
		private static final String listeventConfigQuery = "select nrnReturnConfig_configid from "
			+ "events where partner_pcId in ("
			+ "select partners_pcId from "
			+ "seller_partner where seller_id=:sellerId)";
	private static final String listorderTimelineQuery = "select orderTimeline_timelineId from "
			+ "order_table_ordertimeline ott,order_table ot where ot.seller_id=:sellerId "
			+ "and ot.orderId=ott.order_table_orderId";
	private static final String listordermappedIDQuery = "select customer_customerId, "
			+ "orderpayment_paymentId,orderreturnorrto_returnid,"
			+ "ordertax_taxid,paymentUpload_uploadid,productConfig_productConfigId "
			+ "from order_table where seller_Id=:sellerId";
	private static final String listproductConfigQuery = "select productConfig_productConfigId"
			+ " from product_productconfig pp , product pr "
			+ "where pp.Product_productId=pr.productId and pr.seller_id=:sellerId";
	private static final String listproductStocklistQuery = "select closingStocks_stockId from "
			+ "product_productstocklist pp , product pr "
			+ "where pp.Product_productId=pr.productId and pr.seller_id=:sellerId";
	private static final String listCategoryQuery = "select categories_categoryId from "
			+ "seller_category where seller_id =:sellerId";
	private static final String listProductsQuery = "select categories_categoryId from "
			+ "seller_category where seller_id =:sellerId";
	private static final String listOrdersQuery = "select categories_categoryId from "
			+ "seller_category where seller_id =:sellerId";
	
	/*
	 * 
	 * Delete Queries
	 */
	private static final String deleteuploadLogQuery = "delete from upload_log where seller_id=:sellerId";
	private static final String deletesellerTaxDetailQuery = "delete from seller_taxdetail where seller_id=:sellerId";
	private static final String deletetaxDetailQuery = "delete from taxdetail where taxId in (:listtaxDetailsQuery)";
	private static final String deletesellerTaxCatQuery = "delete from seller_taxcategory where seller_id=:sellerId";
	private static final String deletetaxCatQuery = "delete from taxcategory where taxCatId in (:listtaxCategoryQuery)";
	private static final String deleteSellerRoleQuery = "delete from seller_roles where seller_id=:sellerId";
	private static final String deleteStateDeliveryQuery = "delete from seller_state_deliverytime where seller_id=:sellerId";
	private static final String deleteSellerManualCharQuery = "delete from seller_manualcharges where seller_id =:sellerId";
	private static final String deleteManualCharQuery = "delete from manualcharges where mcId in (:listmanualChargesQuery)";
	private static final String deleteSellerExpcatQuery = "delete from seller_expcat where id =:sellerId";
	private static final String deleteExpensesQuery = "delete from expenses where expesecat_id in (:listexpenseCatQuery)";
	private static final String deleteExpenseCatQuery = "delete from expcat where expcatid in (:listexpenseCatQuery)";
	private static final String deleteSellAccTranQuery = "delete from selleraccount_accounttransaction where sellerAccount_selaccId in"
			+ " (select sellerAccount_selaccId from seller where id =:sellerId)";
	private static final String deleteAccountTransactionQuery = "delete from accounttransaction where accTransId in (:listaccountTransactionsQuery)";
	private static final String deleteSellerPartnerQuery = "delete from seller_partner where seller_id =:sellerId";
	private static final String deletePartnerOrderQuery = "delete from partner_order_table where "
			+ "partner_pcId in (:listPartnersQuery)";
	private static final String deleteEventQuery = "delete from event where partner_pcId in (listPartnersQuery)";
	private static final String deletePartnerQuery = "delete from partner where pcId in (listPartnersQuery)";
	
	private static final String deleteNRChargesQuery = "delete from nrnreturncharges where config_configId"
			+ " in (:nrnreturnconfiglist)";
	private static final String deleteNRConfigQuery = "delete from nrnreturnconfig where configId"
			+ " in (:nrnreturnconfiglist)";
	private static final String deleteOTOrderTimelineQuery = "delete from order_table_ordertimeline where orderTimeline_timelineId "
			+ " in (:listorderTimelineQuery)";
	private static final String deletePOOrdersQuery = "delete from order_table where seller_id=:sellerId and "
			+ "consolidatedOrder_orderId is not null";
	private static final String deleteNONPOOrdersQuery = "delete from order_table where seller_id=:sellerId ";
	private static final String deleteCustomerQuery = "delete from customer where sellerId =:sellerId ";
	private static final String deleteGatePassQuery = "delete from gatepass where gpId in (:gatePassList)";
	private static final String deleteOrderRetrunQuery = "delete from orderreturn where returnId in (:returnOrderList)";
	private static final String deleteOrderTaxQuery = "delete from ordertax where taxId in (:orderTaxList)";
	private static final String deleteOrderPayQuery = "delete from orderpay where paymentId in (:orderPaymentList)";
	
	
	public Seller getSellerFromDB(int sellerid, String dbtype)
	{
		log.info("*** getSeller from sellerId Starts : getSellerFromDB ****");
		Seller seller=null;
		Session session = null;
		try{
			if(dbtype!=null&&dbtype.equals("primary"))
			{
				session=sessionFactory.openSession();
			}
			else
			{
				session=sessionFactoryforBackup.openSession();
			}
				
			session.beginTransaction();
			Object obj=session.get(Seller.class,
					sellerid);
			if(obj!=null)
			seller=(Seller)obj;
			else
				System.out.println(" No selled in db with that id");
			//Hibernate.initialize(seller.getStateDeliveryTime());
			session.getTransaction().commit();
			session.close();
		System.out.println(" Seller Nname and id from "+dbtype+" name :"+seller.getName());
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed!",e);
					
		}
		log.info("*** getSeller from sellerId Ends : getSellerFromDB ****");
		return seller;
	}
	
	public void moveSeller(int sellerid)
	{
		log.info("*** getSeller from sellerId Starts : getSellerFromDB ****");
		Seller seller=null;
		Seller newSeller=new Seller();
		Session session = null;
		try{
			
			session=sessionFactory.openSession();
			session.beginTransaction();
			Object obj=session.get(Seller.class,
					sellerid);
			if(obj!=null)
			{
			seller=(Seller)obj;
			Hibernate.initialize(seller.getPartners());
			//HelperUtility.convertor(seller.getSellerAccount(),newSeller.getSellerAccount(), SellerAccount.class);
			}
			
			
			session.getTransaction().commit();
			session.close();
		}catch(Exception e){
			e.printStackTrace();
			log.error("Failed! in fetching seller from db1",e);
					
		}
		System.out.println(" Successfully fetc seller data "+newSeller.getSellerAccount().getSelaccId());
		
		/*newSeller.setId(0);
		newSeller.getSellerAccount().setSelaccId(0);*/
		/*System.out.println("Old Seller :->"+seller);
		System.out.println("New Seller :->"+newSeller);*/
			System.out.println(" newSeller Seller Accoount details : "+newSeller.getSellerAccount().getSelaccId());
		try{
			
			session=sessionFactoryforBackup.openSession();
			
		session.beginTransaction();
		if(newSeller!=null)
			newSeller=(Seller)session.get(Seller.class, 1);
			HelperUtility.convertor(seller,newSeller, Seller.class,
					new ArrayList<String>(
						    Arrays.asList("id", "sellerAccount","role","plan")));
			
			session.saveOrUpdate(newSeller);
		session.getTransaction().commit();
		session.close();
	}catch(Exception e){
		e.printStackTrace();
		log.error("Failed! in saving seller from db1",e);
				
	}
		log.info("*** getSeller from sellerId Ends : getSellerFromDB ****");
		
	}
	
	public void deleteSeller(int sellerId)
	{
		log.info("***deleteSeller   starts***");
		List<String> listPartnersQuery = null;
		List<String> listtaxDetailsQuery = null;
		List<String> listtaxCategoryQuery = null;
		List<String> listmanualChargesQuery = null;
		List<String> listexpenseCatQuery = null;
		List<String> listaccountTransactionsQuery = null;
		List<String> listpartnerNRConfigQuery = null;
		List<String> listeventConfigQuery = null;
		List<String> listorderTimelineQuery = null;
		List<Object[]> listordermappedIDQuery = null;
		List<String> listproductConfigQuery = null;
		List<String> listproductStocklistQuery = null;
		List<String> listCategoryQuery = null;
		List<String> listorderreturnIDQuery = new ArrayList<String>();
		List<String> listorderpaymentIDQuery = new ArrayList<String>();
		List<String> listordertaxIDQuery = new ArrayList<String>();
		List<String> listordercustomerIDQuery = new ArrayList<String>();
		Map<Date, Long> orderCount = new LinkedHashMap<>();
		Date orderDate = null;
		Session session = null;
		try {
			session=sessionFactory.openSession();
			session.beginTransaction();
			Query query = session.createSQLQuery(DataRestore.listproductConfigQuery)
					.setParameter("sellerId", sellerId);
			listproductConfigQuery = query.list();
			query = session.createSQLQuery(DataRestore.listPartnersQuery)
					.setParameter("sellerId", sellerId);
			listPartnersQuery = query.list();
			query = session.createSQLQuery(DataRestore.listtaxDetailsQuery)
					.setParameter("sellerId", sellerId);
			listtaxDetailsQuery = query.list();
			query = session.createSQLQuery(DataRestore.listtaxCategoryQuery)
					.setParameter("sellerId", sellerId);
			listtaxCategoryQuery = query.list();
			query = session.createSQLQuery(DataRestore.listmanualChargesQuery)
					.setParameter("sellerId", sellerId);
			listmanualChargesQuery = query.list();
			query = session.createSQLQuery(DataRestore.listexpenseCatQuery)
					.setParameter("sellerId", sellerId);
			listexpenseCatQuery = query.list();
			query = session.createSQLQuery(DataRestore.listaccountTransactionsQuery)
					.setParameter("sellerId", sellerId);
			listaccountTransactionsQuery = query.list();
			query = session.createSQLQuery(DataRestore.listpartnerNRConfigQuery)
					.setParameter("sellerId", sellerId);
			listpartnerNRConfigQuery = query.list();
			query = session.createSQLQuery(DataRestore.listeventConfigQuery)
					.setParameter("sellerId", sellerId);
			listeventConfigQuery = query.list();
			query = session.createSQLQuery(DataRestore.listorderTimelineQuery)
					.setParameter("sellerId", sellerId);
			listorderTimelineQuery = query.list();
			query = session.createSQLQuery(DataRestore.listordermappedIDQuery)
					.setParameter("sellerId", sellerId);
			listordermappedIDQuery = query.list();
			query = session.createSQLQuery(DataRestore.listproductStocklistQuery)
					.setParameter("sellerId", sellerId);
			listproductStocklistQuery = query.list();
			query = session.createSQLQuery(DataRestore.listCategoryQuery)
					.setParameter("sellerId", sellerId);
			listCategoryQuery = query.list();
			
			System.out.println(" listCategoryQuery "+listCategoryQuery);
			System.out.println(" listproductConfigQuery "+listproductConfigQuery);
			System.out.println(" listPartnersQuery "+listPartnersQuery);
			System.out.println(" listtaxDetailsQuery "+listtaxDetailsQuery);
			System.out.println(" listtaxCategoryQuery "+listtaxCategoryQuery);
			System.out.println(" listmanualChargesQuery "+listmanualChargesQuery);
			System.out.println(" listtaxDetailsQuery "+listtaxDetailsQuery);
			System.out.println(" listtaxCategoryQuery "+listtaxCategoryQuery);
			System.out.println(" listmanualChargesQuery "+listmanualChargesQuery);
			
			Iterator iterator1 = listordermappedIDQuery.iterator();
			if (listordermappedIDQuery != null) {
				while (iterator1.hasNext()) {
					Object[] recordsRow = (Object[]) iterator1.next();					
					if(recordsRow[0]!=null)
						listordercustomerIDQuery.add(recordsRow[0].toString());
					if(recordsRow[1]!=null)
						listorderpaymentIDQuery.add(recordsRow[1].toString());
					if(recordsRow[2]!=null)
						listorderreturnIDQuery.add(recordsRow[2].toString());
					if(recordsRow[3]!=null)
						listordertaxIDQuery.add(recordsRow[3].toString());
						
				}
			}
		} catch (Exception e) {
			log.error("Failed!",e);
			log.debug("Inside exception  " + e.getLocalizedMessage());
			e.printStackTrace();
		}
		log.info("*** getSeller from sellerId Ends : getSellerFromDB ****");
		
	}

}
