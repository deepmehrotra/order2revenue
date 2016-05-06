package com.o2r.dao;

import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.AccountTransaction;
import com.o2r.model.Plan;
import com.o2r.model.Product;
import com.o2r.model.ProductStockList;
import com.o2r.model.Seller;
import com.o2r.model.State;

/**
 * @author Deep Mehrotra
 *
 */
//GIT Test
@Repository("sellerDao")
public class SellerDaoImpl implements SellerDao {

	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(SellerDaoImpl.class.getName());

	public void addSeller(Seller seller)throws CustomException {

		// sessionFactory.getCurrentSession().saveOrUpdate(seller);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			session.saveOrUpdate(seller);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			
			log.error(e);
			throw new CustomException(GlobalConstant.addSellerError, new Date(), 1, GlobalConstant.addSellerErrorCode, e);
			//System.out.println(" Seller DAO IMPL :" + e.getLocalizedMessage());
		}
	}

	@SuppressWarnings("unchecked")
	public List<Seller> listSeller()throws CustomException {
		try{
		return (List<Seller>) sessionFactory.getCurrentSession()
				.createCriteria(Seller.class).list();
		}catch(Exception e){
			log.error(e);
			throw new CustomException(GlobalConstant.listSellerError, new Date(), 3, GlobalConstant.listSellerErrorCode, e);
			
		}
	}

	public Seller getSeller(int sellerid)throws CustomException {
		Seller seller=null;
		try{
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			seller=(Seller) session.get(Seller.class,
					sellerid);
			Hibernate.initialize(seller.getStateDeliveryTime());
			session.getTransaction().commit();
			session.close();
		
		}catch(Exception e){
			log.error(e);
			throw new CustomException(GlobalConstant.getSellerByIdError, new Date(), 3, GlobalConstant.getSellerByIdErrorCode, e);
			
		}
		return seller;
	}

	public Seller getSeller(String email)throws CustomException {
		// return (Seller) sessionFactory.getCurrentSession().get(Seller.class,
		// sellerid);
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("email", email));
			if (criteria.list() != null && criteria.list().size() != 0)
				seller = (Seller) criteria.list().get(0);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			
			log.error(e);
			throw new CustomException(GlobalConstant.getSellerByEmailError, new Date(), 3, GlobalConstant.getSellerByEmailErrorCode, e);
			
//			System.out.println(" Seller  DAO IMPL :" + e.getLocalizedMessage());
//			e.printStackTrace();
		}
		return seller;
	}

	public void deleteSeller(Seller seller)throws CustomException {
		// sessionFactory.getCurrentSession().createQuery("DELETE FROM Seller WHERE id = "+seller.getId()).executeUpdate();

		System.out.println(" In seller  delete sellerid " + seller.getId());
		Seller seller1 = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", seller.getId()));

			seller1 = (Seller) criteria.list().get(0);
			seller1.getPartners().clear();
			seller1.getOrders().clear();
			System.out.println(" List size of partner after deletion :"
					+ seller1.getPartners().size());
			System.out.println(" List size of order after deletion :"
					+ seller1.getOrders().size());
			session.delete(seller1);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			
			log.error(e);
			throw new CustomException(GlobalConstant.deleteSellerError, new Date(), 3, GlobalConstant.deleteSellerErrorCode, e);
			
//			System.out.println(" Inside delleting partner"+ e.getLocalizedMessage());
//			e.printStackTrace();
		}
	}

	@Override
	public void planUpgrade(int pid, int sellerid)throws CustomException {
		System.out.println("Inside Plan Upgrade");
		System.out.println("Dao Pid " + pid);
		/* System.out.println("Dao price "+plan.getPlanPrice()); */
		AccountTransaction at = null;
		Plan plan = null;
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			plan = (Plan) session.get(Plan.class, pid);
			seller = (Seller) session.get(Seller.class, sellerid);
			System.out.println(" Getting ACCOUNT TRANSacttion object");
			AccountTransaction acct = payementStatus(plan);
			System.out.println(" AFTER getting  ACCOUNT TRANSacttion object "
					+ acct.getStatus() + " plan order count : "
					+ plan.getOrderCount());
			System.out.println(" Current ORDER BUCKET : "
					+ seller.getSellerAccount());
			if (seller.getSellerAccount() == null) {
				throw new Exception();
			}
			System.out.println(" Current ORDER BUCKET : "
					+ seller.getSellerAccount().getOrderBucket());
			if (acct.getStatus().equals("Success")) {
				seller.getSellerAccount().setOrderBucket(
						seller.getSellerAccount().getOrderBucket()
								+ plan.getOrderCount());
				seller.getSellerAccount().setTotalAmountPaidToO2r(
						plan.getPlanPrice());
				seller.setPlan(plan);

			}
			if (seller.getSellerAccount().getLastLogin() == null
					|| seller.getSellerAccount().isAccountStatus() == false) {
				seller.getSellerAccount().setAccountStatus(true);
				seller.getSellerAccount().setLastLogin(new Date());
				seller.getSellerAccount().setTotalOrderProcessed(
						seller.getSellerAccount().getTotalOrderProcessed()
								+ plan.getOrderCount());
				seller.getSellerAccount().setAtivationDate(new Date());
			}
			seller.getSellerAccount().setLastTransaction(new Date());
			seller.getSellerAccount().getAccountTransactions().add(acct); //

			Random r = new Random();
			int randomId = r.nextInt(9999999);
			String invoiceId = "O2R" + randomId;

			at = new AccountTransaction();
			at.setTransactionDate(new Date());
			at.setTransactionAmount(plan.getPlanPrice());
			at.setCurrentOrderCount(plan.getOrderCount());
			at.setStatus("Done");
			at.setInvoiceId(invoiceId);
			at.setTransactionId("RecivedFrom Third Party");
			System.out.println(" Setting at object ionto seller");
			seller.getSellerAccount().getAccountTransactions().add(at);
			session.saveOrUpdate(seller);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			
			log.error(e);
			throw new CustomException(GlobalConstant.planUpgradeError, new Date(), 1, GlobalConstant.planUpgradeErrorCode, e);
			
			/*System.out.println(" Inside exception in plN Dao impl :  "+ e.getLocalizedMessage());
			e.printStackTrace();*/
		}

	}

	AccountTransaction payementStatus(Plan plan) {
		AccountTransaction at = new AccountTransaction();
		at.setTransactionDate(new Date());
		at.setTransactionAmount(plan.getPlanPrice());
		at.setStatus("Success");// It has to come from third party
		at.setInvoiceId("invoice123");
		at.setTransactionId("transac123"); // It has Recived From Third Party
		return at;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<State> listStates() {

		return (List<State>) sessionFactory.getCurrentSession()
				.createCriteria(State.class).list();
	}
	
	 public void addToProductStockList(){
		 System.out.println("THIS IS SPRING BEAN IMPLEMENTATION");
		Session session=sessionFactory.openSession();
		session.beginTransaction();
		   Criteria criteria=session.createCriteria(Product.class);
		   java.util.List<Product> list=criteria.list();
		   Product stockProduct=null;
		   ProductStockList stockList=null;
			for (Product product : list ) {
				stockList=new ProductStockList();
				stockList.setCreatedDate(new Date());
				stockList.setStockAvailable(product.getQuantity());
				stockList.setUpdatedate(product.getProductDate().getDate());
				stockList.setMonth(product.getProductDate().getMonth());
				stockList.setYear(product.getProductDate().getYear());
				stockList.setPrice(product.getProductPrice());
				product.getClosingStocks().add(stockList);
				session.saveOrUpdate(product);
				session.saveOrUpdate(stockList);
		System.out.println("PRODUCT ID IS hehehe "+product.getProductId());
			}
			session.getTransaction().commit();
	 }
}
