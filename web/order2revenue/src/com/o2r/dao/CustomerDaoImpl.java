package com.o2r.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.CustomerDBaseBean;
import com.o2r.controller.AdminController;
import com.o2r.model.Category;
import com.o2r.model.Customer;
import com.o2r.model.Order;
import com.o2r.model.Seller;


/**
 * @author Deep Mehrotra
 *
 */
@Repository("customerDao")
public class CustomerDaoImpl implements CustomerDao {

 @Autowired
 private SessionFactory sessionFactory;
 
 private final int pageSize = 500;
 
 static Logger log = Logger.getLogger(CustomerDaoImpl.class.getName());
 /*@Override
public void addCustomer(Customer customer,int sellerId) {
	sellerId=4;
	Seller seller=null;
	   try
	   {
	   Session session=sessionFactory.openSession();
	   session.beginTransaction();
	   Criteria criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
	   seller=(Seller)criteria.list().get(0);
	   if(seller.getCategories()!=null)
	   seller.getCategories().add(category);
	   session.saveOrUpdate(seller);
	   session.getTransaction().commit();
	   session.close();
	   }
	   catch (Exception e) {
		   System.out.println("Inside exception  "+e.getLocalizedMessage());
	}
	
	
}
*/
 
 
 @Override
	public List<CustomerDBaseBean> listCustomerDB(int sellerId, int pageNo) {
	 
	 log.info("$$$ listCustomer Starts : CustomerDaoImpl $$$");
	 List projObj=null;
	 CustomerDBaseBean custmerDBean=null;
	 List<CustomerDBaseBean> customerDbeanList=new ArrayList<CustomerDBaseBean>();
	 Session session = null;
	 try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Order.class);
			criteria.createAlias("customer", "customer",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO",CriteriaSpecification.LEFT_JOIN);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.add(Restrictions.isNotNull("customer.customerPhnNo"))
					.add(Restrictions.eq("poOrder", false));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();			
			projList.add(Projections.property("customer.customerId"));
			projList.add(Projections.property("customer.customerCity"));
			projList.add(Projections.property("customer.customerEmail"));
			projList.add(Projections.property("customer.customerName"));
			projList.add(Projections.property("customer.customerPhnNo"));
			projList.add(Projections.property("customer.zipcode"));
			projList.add(Projections.sum("quantity"));
			projList.add(Projections.sum("orderReturnOrRTO.returnorrtoQty"));
			projList.add(Projections.sqlProjection("(sum( grossNetRate * (quantity-returnorrtoQty) )) as netNR",new String[] { "netNR" },
					new Type[] { Hibernate.DOUBLE }));
			projList.add(Projections.groupProperty("customer.customerPhnNo"));
			criteria.setProjection(projList);
			criteria.setFirstResult(pageNo * pageSize);
			criteria.setMaxResults(pageSize);
			if(criteria != null && criteria.list().size() != 0){
				projObj=criteria.list();
				if(projObj != null && projObj.size() != 0){
					Iterator itr=projObj.iterator();
					while(itr.hasNext()){
						Object[] eachObj=(Object[]) itr.next();						
						custmerDBean=new CustomerDBaseBean();
						custmerDBean.setCustomerId((int)eachObj[0]);
						custmerDBean.setCustomerCity((String)eachObj[1]);
						custmerDBean.setCustomerEmail((String)eachObj[2]);
						custmerDBean.setCustomerName((String)eachObj[3]);
						custmerDBean.setCustomerPhnNo((String)eachObj[4]);
						custmerDBean.setZipcode((String)eachObj[5]);
						custmerDBean.setProductPurchased((int)eachObj[6]);
						custmerDBean.setProductReturned((int)eachObj[7]);
						custmerDBean.setNetRevenue((double)eachObj[8]);
						customerDbeanList.add(custmerDBean);
					}
				}
			}
			session.getTransaction().commit();		
		} catch (Exception e) {
			log.error("Error in listCustomer !",e);
			e.printStackTrace();
		} finally {
			if(session != null)
				session.close();
		}
	 	log.info("$$$ listCustomer Ends : CustomerDaoImpl $$$");
		return customerDbeanList;
	} 
 
 
@SuppressWarnings("unchecked")
@Override
	public List<Customer> listCustomers(int sellerId) {
		//sellerId = 4;
		List<Customer> customerList = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria=session.createCriteria(Customer.class).add(Restrictions.eq("sellerId", sellerId));
			if(criteria != null){
				customerList=criteria.list();
			}			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			System.out.println(" Exception in getting Customer List :"
					+ e.getLocalizedMessage());
		}
		return customerList;
	}


/*
 * @Override
 * public List<Category> listParentCategories(int sellerId) {
	sellerId=4;
	List<Category> returnlist=null;
	Seller seller=null;
	try
	{
	Session session=sessionFactory.openSession();
	   session.beginTransaction();
	   Criteria criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
	   criteria.createAlias("categories", "category",CriteriaSpecification.LEFT_JOIN)
	   .add(Restrictions.eq("category.isSubCategory",false))
	   .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	   seller=(Seller)criteria.list().get(0);
	   System.out.println(" Size of parent category list :"+seller.getCategories().size());
	   if(seller.getCategories()!=null&&seller.getCategories().size()!=0)
		   returnlist=seller.getCategories();
	   session.getTransaction().commit();
	   session.close();
	}
	catch(Exception e)
	{
		System.out.println(" Exception in getting order list :"+e.getLocalizedMessage());
	}
	return returnlist;
}*/

@Override
public Customer getCustomer(int customerId) {
	return (Customer) sessionFactory.getCurrentSession().get(Category.class, customerId);
}

@SuppressWarnings("unchecked")
@Override
public Customer getCustomer(String customerEmail,int sellerId,Session session) {
	
	log.info("*** getCustomer starts ***");
	
	Customer returncustomer=null;		
	try
	{
			if (!session.getTransaction().isActive())
				session.beginTransaction();

			Criteria criteria = session.createCriteria(Customer.class)
					.add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions.eq("customerEmail", customerEmail));

			if (criteria.list() != null && criteria.list().size() != 0) {
				List<Customer> customerlist = (List<Customer>) criteria.list();
				log.debug(" Getting customer list :"+ customerlist.size());
				if (customerlist != null && customerlist.size() != 0)
					returncustomer = customerlist.get(0);

			}
		}
	catch(Exception e)
	{
		log.error("Failed! by sellerId : "+sellerId,e);
		System.out.println(" Exception in getting cutomer list :"+e.getLocalizedMessage());
		e.printStackTrace();
	}
	finally
	{
		//if(!session.getTransaction().wasCommitted())
		/* session.getTransaction().commit();
		   session.close();*/
	}
	log.info("*** getCustomer ends ***");
	return returncustomer;
}

/*@Override
public void deleteCategory(Category category,int sellerId) {
	 System.out.println(" In Category delete cid "+category.getCatName());
	 sellerId=4;
	 try
	 {
		 Session session=sessionFactory.openSession();
		  session.beginTransaction();
		  Query deleteQuery = session.createSQLQuery(
				    "delete from Seller_Category where seller_Id=? and category_categoryId=?");
		  		deleteQuery.setInteger(0,sellerId);
				deleteQuery.setInteger(1, category.getCategoryId());
				
				int updated = deleteQuery.executeUpdate();
				int catdelete=session.createQuery("DELETE FROM Category WHERE categoryId = "+category.getCategoryId()).executeUpdate();
		
				System.out.println("  Deleteing category updated:"+updated+" catdelete :"+catdelete);
		  session.getTransaction().commit();
		  session.close();

	 }
	 catch(Exception e)
	 {
		 System.out.println(" Inside delleting order"+e.getLocalizedMessage());
		 e.printStackTrace();
	 }
	
}
*/

}