package com.o2r.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.PaymentUpload;
import com.o2r.model.Seller;


/**
 * @author Deep Mehrotra
 *
 */
@Repository("paymentDao")
public class PaymentUploadDaoImpl implements PaymentUploadDao{

 @Autowired
 private SessionFactory sessionFactory;
 
 static Logger log = Logger.getLogger(PaymentUploadDaoImpl.class.getName());
 @Override
 public void addPaymentUpload(PaymentUpload upload , int sellerId) throws CustomException
 {
	
	// sellerId=4;
		Seller seller=null;
		
		   try
		   {
			   Session session=sessionFactory.openSession();
			   session.beginTransaction();
			   Criteria criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
			   seller=(Seller)criteria.list().get(0);
			   if(upload.getUploadId()==0)
			   {
				   upload.setUploadDate(new Date());
			 seller.getPaymentUploads().add(upload);
			   System.out.println(" Inside upload add");
			   session.saveOrUpdate(seller);
			   }
			   else
			   {
				   session.saveOrUpdate(upload);
			   }
		    session.getTransaction().commit();
		   session.close();
		   }
		   catch (Exception e) {
			   log.error(e);
			   e.printStackTrace();
			   throw new CustomException(GlobalConstant.addPaymentUploadError, new Date(), 1, GlobalConstant.addPaymentUploadErrorCode, e);
			   //System.out.println("Inside exception  "+e.getLocalizedMessage());
		}
		
	 
 }
 
 

public List<PaymentUpload> listPaymentUploads(int sellerId)throws CustomException
 {
	// sellerId=4;
		List<PaymentUpload> returnlist=null;
		Seller seller=null;
		try
		{
		Session session=sessionFactory.openSession();
		   session.beginTransaction();
		   Criteria criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
		   seller=(Seller)criteria.list().get(0);
		   if(seller.getPaymentUploads()!=null&&seller.getPaymentUploads().size()!=0)
		   returnlist=seller.getPaymentUploads();
		   session.getTransaction().commit();
		   session.close();
		}
		catch(Exception e)
		{
			log.error(e);
			throw new CustomException(GlobalConstant.listPaymentUploadError, new Date(), 1, GlobalConstant.listPaymentUploadErrorCode, e);
			//System.out.println(" Exception in getting Payment list :"+e.getLocalizedMessage());
		}
		return returnlist;
 }
 
 public PaymentUpload getPaymentUpload(int paymentUploadId)throws CustomException
 {
	 PaymentUpload  returnObject=null;
		try
		{
		Session session=sessionFactory.openSession();
		   session.beginTransaction();
		   Criteria criteria=session.createCriteria(PaymentUpload.class).add(Restrictions.eq("uploadId", paymentUploadId));
		   returnObject=(PaymentUpload)criteria.list().get(0);
		   Hibernate.initialize(returnObject.getOrders());
		   session.getTransaction().commit();
		   session.close();
		}
		catch(Exception e)
		{
			log.error(e);
			throw new CustomException(GlobalConstant.getPaymentUploadError, new Date(), 3, GlobalConstant.getPaymentUploadErrorCode, e);
			
			//System.out.println(" Exception in getting Expenses list :"+e.getLocalizedMessage());
		}
		return returnObject;
	
 }
 
 public PaymentUpload getManualPayment(int sellerId)throws CustomException
 {
	 PaymentUpload  returnObject=null;
	 Seller seller=null;
	 //sellerId=4;
		try
		{
			 Session session=sessionFactory.openSession();
			   session.beginTransaction();
			   Criteria criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
			   criteria.createAlias("paymentUploads", "paymentUpload", CriteriaSpecification.LEFT_JOIN)
			   .add(Restrictions.eq("paymentUpload.uploadDesc", "Manual Upload"))
			   .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			   
			   if(criteria.list()!=null&&criteria.list().size()!=0)
			   {
			   seller=(Seller)criteria.list().get(0);
			   if(seller.getPaymentUploads()!=null&&seller.getPaymentUploads().size()!=0)
			   {
				   returnObject=seller.getPaymentUploads().get(0);
				   Hibernate.initialize(returnObject.getOrders());
			   }
		  
		   session.getTransaction().commit();
		   session.close();
			   }
		}
		catch(Exception e)
		{
			log.error(e);
			throw new CustomException(GlobalConstant.getManualPaymentError, new Date(), 3, GlobalConstant.getManualPaymentErrorCode, e);
			
			//System.out.println(" Exception in getting Payment Upload list :"+e.getLocalizedMessage());
		}
		return returnObject;
	
 }
 
 public void deletePaymentUpload(PaymentUpload payupload,int sellerId)
 {
	// System.out.println(" In Category delete cid "+expense.getExpenseName());
	// sellerId=4;
	 try
	 {
		/* Session session=sessionFactory.openSession();
		  session.beginTransaction();
		  Query deleteQuery = session.createSQLQuery(
				    "delete from ExpenseCategory_Expenses where expcategoryId=? and expenses_expenseId=?");
		  		deleteQuery.setInteger(0,expense.getExpenseCategory().getExpcategoryId());
				deleteQuery.setInteger(1, expense.getExpenseId());
				
				int updated = deleteQuery.executeUpdate();
				int catdelete=session.createQuery("DELETE FROM Expenses WHERE expenseId = "+expense.getExpenseId()).executeUpdate();
		
				System.out.println("  Deleteing category updated:"+updated+" catdelete :"+catdelete);
		  session.getTransaction().commit();
		  session.close();
*/
	 }
	 catch(Exception e)
	 {
		 
		 System.out.println(" Inside delleting order"+e.getLocalizedMessage());
		 e.printStackTrace();
	 }
 }




 

}