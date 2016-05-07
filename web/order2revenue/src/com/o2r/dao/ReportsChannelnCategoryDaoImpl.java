package com.o2r.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SQLProjection;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.bean.ChannelSalesDetails;
import com.o2r.bean.TotalShippedOrder;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Order;
import com.o2r.model.Product;
import com.o2r.model.TaxCategory;
import com.sun.xml.bind.v2.runtime.Name;


@Repository("reportGeneratorDao2")
public class ReportsChannelnCategoryDaoImpl implements ReportsChannelnCategoryDao {
	
	 @Autowired
	 private SessionFactory sessionFactory;
	 
	 static Logger log = Logger.getLogger(ReportsGeneratorDaoImpl.class.getName());
	
	@Override
	public List<ChannelSalesDetails> getChannelSalesDetails(Date startDate,Date endDate, int sellerId) throws CustomException{
		// TODO Auto-generated method stub
		System.out.println("GET CHNNEL SALES DETAILS");
		 log.info("*** getAllPartnerTSOdetails start ***");
		 List<ChannelSalesDetails> ttso=new ArrayList<ChannelSalesDetails>();
		 
		 try
		 {
			 Session session=sessionFactory.openSession();	
			 
			 Criteria criteriaTax=session.createCriteria(TaxCategory.class);
			 ProjectionList tList = Projections.projectionList();
			 HashMap<String,Float> taxMap=new HashMap<String,Float>();
			 tList.add(Projections.property("taxCatId"));
			 tList.add(Projections.property("taxCatName"));
			 tList.add(Projections.property("taxPercent"));
			 criteriaTax.setProjection(tList);			 
			 List taxList=criteriaTax.list();
			 Iterator txItr=taxList.iterator();
			 while(txItr.hasNext()){
				 Object[] recordsRow = (Object[])txItr.next();
				 taxMap.put(recordsRow[1].toString(),Float.parseFloat(recordsRow[2].toString()));
			 }
			 
			 ChannelSalesDetails temp=null;
	 
			 Criteria ctr=session.createCriteria(Order.class);
			 ProjectionList p=Projections.projectionList()	;
			 p.add(Projections.groupProperty("pcName"));
			 ctr.setProjection(p);
			 List lst=ctr.list();
			 String arr[]=new String[lst.size()];
			 Iterator itrx=lst.iterator();int a=0;
			 
								 while(itrx.hasNext())
								 {
									 arr[a]=itrx.next().toString();a++;
								 }
								 
			 Criteria carr[]=new Criteria[lst.size()];
			 Criteria criteria;
			 session.beginTransaction();
			 
			 Disjunction or = Restrictions.disjunction();

			 or.add(Restrictions.between("orderDate",startDate, endDate));
			 or.add(Restrictions.between("orderReturnOrRTO.returnDate",startDate, endDate));

			 
			 
			 for(int x=0;x<carr.length;x++){
				 criteria=null;
				 criteria=session.createCriteria(Order.class);
				 criteria.createAlias("seller", "seller", CriteriaSpecification.LEFT_JOIN);
				 criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
				 criteria.createAlias("orderPayment", "orderPayment", CriteriaSpecification.INNER_JOIN);
				 criteria.createAlias("orderTax", "orderTax", CriteriaSpecification.INNER_JOIN);
				 criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO", CriteriaSpecification.INNER_JOIN);
				// criteria.createAlias("orderTimeline", "orderTimeline", CriteriaSpecification.LEFT_JOIN)
				 criteria.add(Restrictions.eq("seller.id", sellerId)).add(Restrictions.eq("pcName", arr[x]));
				 criteria.add(or);
				 //criteria.add(Restrictions.between("orderDate",startDate, endDate));
				 //criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				 System.out.println("CRITERIA LISR SIZE IS "+criteria.list().size()+"  "+  arr[x]);
				 carr[x]=criteria;
				 }
			 			 
			 for(int y=0;y<carr.length;y++){
			 carr[y].setProjection(getPL("orderTax"));
			 System.out.println("CRITERIA LISR SIZE IS "+carr[y].list().size()+"  "+  arr[y]);
			 }
			 
			 		int i=0;
						 for(int m=0;m<carr.length;m++){
							 List<Object[]> results = carr[m].list();
							               
							 Iterator channelItr = results.iterator();
							 while(channelItr.hasNext()){
								 Object[] recordsRow = (Object[])channelItr.next();
								 temp=new ChannelSalesDetails();
								 populateChannelSalesDetails(temp,recordsRow,startDate,endDate,"pc");
								 temp.setGroupByName("orderTax");
								 temp.setPcName(arr[i]);
								 temp.setTaxPercent(taxMap.get(temp.getTaxCategtory()));
								 ttso.add(temp);
						 }i++;}
		

	//		ttsoReturn(startDate,endDate,sellerId,ttso);			

						 Criteria criteria1=getCriteriaPL(startDate,endDate,sellerId);
						 List pcNameList=criteria1.list();

						 
						 System.out.println("SIZE OF THE LIST IS "+pcNameList.size());
							Iterator pcNameItr=pcNameList.iterator();
							
							while(pcNameItr.hasNext()){
								 Object[] recordsRow = (Object[])pcNameItr.next();
								 temp=new ChannelSalesDetails();
								 populateChannelSalesDetails(temp,recordsRow,startDate,endDate,"pc");
								 temp.setGroupByName("channels");
								 ttso.add(temp);
							}
			 session.getTransaction().commit();
		     session.close();
		   }
		   catch (Exception e) {			   
			   log.error(e);
			   log.info("Error :",e);
			   throw new CustomException(GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3, GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);
		}
		 log.info("*** getAllPartnerTSOdetails exit ***");
	 
		 return ttso;
	}


	@Override
	public List<TotalShippedOrder> getProductSalesDetails(Date startDate,Date endDate, int sellerId) throws CustomException {
		// TODO Auto-generated method stub
		System.out.println("GET PRODUCTS SALES DETAILS");
		return null;
	}
	
	public static ProjectionList getPL(String name){
		 ProjectionList projList = Projections.projectionList();
		 if(name.equals("pcName"))
			 projList.add(Projections.groupProperty("pcName"));
		 else if(name.equals("orderTax"))
		 projList.add(Projections.groupProperty("orderTax.taxCategtory"));
		 else if(name.equals("paymentType"))
		 projList.add(Projections.groupProperty("paymentType"));
		 
		 projList.add(Projections.sum("quantity"));
		 projList.add(Projections.sum("discount"));
		 projList.add(Projections.sum("orderMRP"));
		 projList.add(Projections.sum("orderSP"));
		 projList.add(Projections.sum("shippingCharges"));
		 projList.add(Projections.sum("netSaleQuantity"));
		 projList.add(Projections.sum("netRate"));
		 projList.add(Projections.sum("grossNetRate"));
		 projList.add(Projections.sum("partnerCommission"));
		 projList.add(Projections.sum("pr"));
		 projList.add(Projections.sum("totalAmountRecieved"));
		 projList.add(Projections.sum("poPrice"));
		 projList.add(Projections.sum("grossProfit"));
		 projList.add(Projections.sum("serviceTax"));
		 projList.add(Projections.sum("fixedfee"));
		 projList.add(Projections.sum("pccAmount"));		 
		 projList.add(Projections.sum("orderPayment.negativeAmount"));
		 projList.add(Projections.sum("orderPayment.positiveAmount"));
		 projList.add(Projections.sum("orderPayment.actualrecived2"));
		 projList.add(Projections.sum("orderPayment.netPaymentResult"));
		 projList.add(Projections.sum("orderPayment.paymentDifference"));			 
		 projList.add(Projections.sum("orderReturnOrRTO.returnOrRTOCharges"));
		 projList.add(Projections.sum("orderReturnOrRTO.returnorrtoQty"));
		 projList.add(Projections.sum("orderReturnOrRTO.returnOrRTOChargestoBeDeducted"));
		 projList.add(Projections.sum("orderTax.tax"));
		 projList.add(Projections.sqlProjection("(sum( (tax/quantity) * returnorrtoQty )) as test", new String[]{"test"},new Type[]{Hibernate.DOUBLE}));
		 projList.add(Projections.sqlProjection("(sum( grossNetRate * returnorrtoQty )) as testq", new String[]{"testq"},new Type[]{Hibernate.DOUBLE}));
		 projList.add(Projections.sqlProjection("(sum( (orderSP/quantity) * returnorrtoQty )) as testR", new String[]{"testR"},new Type[]{Hibernate.DOUBLE}));

		 
		 return projList;
	}
	
	
	public static void populateChannelSalesDetails(ChannelSalesDetails temp,Object[] recordsRow,Date startDate,Date endDate,String nme){
		
		 temp.setPcName(recordsRow[0].toString());
		 temp.setTaxCategtory(recordsRow[0].toString());
		 temp.setPaymentType(recordsRow[0].toString());
		 temp.setQuantity(Integer.parseInt(recordsRow[1].toString()));
		 temp.setDiscount(Double.parseDouble(recordsRow[2].toString()));
		 temp.setOrderMRP(Double.parseDouble(recordsRow[3].toString()));
		 temp.setOrderSP(Double.parseDouble(recordsRow[4].toString()));
		 temp.setShippingCharges(Double.parseDouble(recordsRow[5].toString()));
		 temp.setNetSaleQuantity(Integer.parseInt(recordsRow[6].toString()));
		 temp.setNetRate(Double.parseDouble(recordsRow[7].toString()));
		 temp.setGrossNetRate(Double.parseDouble(recordsRow[8].toString()));
		 temp.setPartnerCommission(Double.parseDouble(recordsRow[9].toString()));
		 temp.setPr(Double.parseDouble(recordsRow[10].toString()));
		 temp.setTotalAmountRecieved(Double.parseDouble(recordsRow[11].toString()));
		 temp.setPoPrice(Double.parseDouble(recordsRow[12].toString()));
		 temp.setGrossProfit(Double.parseDouble(recordsRow[13].toString()));
		 temp.setServiceTax(Float.parseFloat(recordsRow[14].toString()));
		 temp.setFixedfee(Double.parseDouble(recordsRow[15].toString()));
		 temp.setPccAmount(Double.parseDouble(recordsRow[16].toString()));
		 temp.setNegativeAmount(Double.parseDouble(recordsRow[17].toString()));
		 temp.setPositiveAmount(Double.parseDouble(recordsRow[18].toString()));
		 temp.setActualrecived2(Double.parseDouble(recordsRow[19].toString()));
		 temp.setNetPaymentResult(Double.parseDouble(recordsRow[20].toString()));
		 temp.setPaymentDifference(Double.parseDouble(recordsRow[21].toString()));
		 temp.setReturnOrRTOCharges(Double.parseDouble(recordsRow[22].toString()));
		 temp.setReturnorrtoQty(Integer.parseInt(recordsRow[23].toString()));
		 temp.setStartDate(startDate.getDate()+"|"+startDate.getMonth()+"|"+startDate.getYear());
		 temp.setEndDate(endDate.getDate()+"|"+endDate.getMonth()+"|"+endDate.getYear());
		 temp.setRowCount(Integer.parseInt(recordsRow[23].toString()));
		 temp.setReturnOrRTOChargestoBeDeducted(Double.parseDouble(recordsRow[24].toString()));
		 temp.setTax(Float.parseFloat(recordsRow[25].toString()));
		// temp.setTax(Float.parseFloat(recordsRow[26].toString()));
		 if(!nme.equals("categoryName") && !nme.equals("getPayments")){
		 temp.setNrTax(Double.parseDouble(recordsRow[26].toString()));
		 temp.setNrReturn(Double.parseDouble(recordsRow[27].toString()));
		 temp.setReturnSP(Double.parseDouble(recordsRow[28].toString()));

		 }
		 if(nme.equals("categoryName") || nme.equals("getPayments")){
		 temp.setOrderId(Integer.parseInt(recordsRow[25].toString()));
		 temp.setInvoiceID(recordsRow[26].toString());
		 temp.setProductSkuCode(recordsRow[27].toString());;
		 temp.setGroupByName("categoryDetails");
		 temp.setTaxCategtory(recordsRow[28].toString());
		 temp.setPaymentType(recordsRow[29].toString());
		 if(recordsRow[30]!=null)
		 temp.setPaymentCycle(recordsRow[30].toString());
		 temp.setUploadDate((Date)recordsRow[31]);
		 temp.setDateofPayment((Date)recordsRow[32]);
		 temp.setTax(Float.parseFloat(recordsRow[33].toString()));
		 }
		 
//		 temp.setNetPaymentResult(Double.parseDouble(recordsRow[20].toString()));
		
	}


	@Override
	public List<ChannelSalesDetails> getCategorySalesDetails(Date startDate,Date endDate, int sellerIdfromSession) throws CustomException {
		// TODO Auto-generated method stub
		 List<ChannelSalesDetails> ttso=new ArrayList<ChannelSalesDetails>();
		 ArrayList<ChannelSalesDetails> ttsonew=new ArrayList<ChannelSalesDetails>();
		 
		 try
		 {
			 Session session=sessionFactory.openSession();
			 session.getTransaction().begin();
			 
			 
			 Criteria criteriaTax=session.createCriteria(TaxCategory.class);
			 ProjectionList tList = Projections.projectionList();
			 HashMap<String,Float> taxMap=new HashMap<String,Float>();
			 tList.add(Projections.property("taxCatId"));
			 tList.add(Projections.property("taxCatName"));
			 tList.add(Projections.property("taxPercent"));
			 criteriaTax.setProjection(tList);			 
			 List taxList=criteriaTax.list();
			 Iterator txItr=taxList.iterator();
			 while(txItr.hasNext()){
				 Object[] recordsRow = (Object[])txItr.next();
				 taxMap.put(recordsRow[1].toString(),Float.parseFloat(recordsRow[2].toString()));
			 }
			 
			 
			 
			 
			 
			 Criteria criteria1=getCriteria(startDate,endDate,sellerIdfromSession);
			 
			 List pcNameList=criteria1.list();
			 
			 System.out.println("SIZE OF THE LIST IS "+pcNameList.size());
			 
			 Criteria criteria2=session.createCriteria(Product.class);
			 		  criteria2.setProjection(geProductPL("product"));
			 		  
 		  
			 List productList = criteria2.list();
			 
				Iterator prNameItr=productList.iterator();
				HashMap<String,String> prMap=new HashMap<String,String>();
				HashMap<String,String> prPriceMap=new HashMap<String,String>();
				
				while(prNameItr.hasNext()){
					 Object[] recordsRow = (Object[])prNameItr.next();
					 prMap.put(recordsRow[0].toString(), recordsRow[1].toString());
					 prPriceMap.put(recordsRow[0].toString(), recordsRow[2].toString());
					 
					 System.out.println("Product Price Is " +recordsRow[2].toString());
				}

			 		
				Iterator pcNameItr=pcNameList.iterator();
				ChannelSalesDetails temp=null;
				ArrayList<Integer> orderId=new ArrayList<Integer>();
				
				while(pcNameItr.hasNext()){
					 Object[] recordsRow = (Object[])pcNameItr.next();
					 temp=new ChannelSalesDetails();
					
					 populateChannelSalesDetails(temp,recordsRow,startDate,endDate,"categoryName");
					 
					 temp.setGroupByName(null);
					 temp.setProductCategory(prMap.get(temp.getProductSkuCode()));
					 temp.setPcName(prMap.get(temp.getProductSkuCode()));
					 temp.setTaxPercent(taxMap.get(temp.getTaxCategtory()));
					 temp.setProductPrice(Float.parseFloat(prPriceMap.get(temp.getProductSkuCode())));
					 if(!orderId.contains(new Integer(temp.getOrderId())))
					 ttso.add(temp);
					 orderId.add(new Integer(temp.getOrderId()));
				}
		Collections.sort(ttso,new Comparator(){
			    public int compare(Object o1,Object o2){
					return	((ChannelSalesDetails) o1).getProductCategory().compareTo(((ChannelSalesDetails) o2).getProductCategory());
					}
				});
	    
		    	int i=0;
		    	ChannelSalesDetails temp1=null;
		    	Iterator ttsoItr= ttso.iterator();
		    	Iterator cate= ttso.iterator();
		    	
		    	//temp = (ChannelSalesDetails)ttsoItr.next();
		    	
		    	HashSet st=new HashSet();
		    	HashSet<Integer> oid=new HashSet<Integer>();
		    	String name=null;
		    	while(cate.hasNext()){
		    		name=((ChannelSalesDetails)cate.next()).getProductCategory();
		    		if(!st.contains(name))
		    			st.add(name);
		    	}
		    	System.out.print("SIZE IS CATEGOY SIZE IS "+st.size());
		    	
		    	Iterator stItr=st.iterator();
		    	
		    	while(stItr.hasNext()){
		    		temp = new ChannelSalesDetails();
		    		String cates=stItr.next().toString();
		    		ttsoItr=null;
		    		ttsoItr= ttso.iterator();
		    		
		         while(ttsoItr.hasNext()){
		        	 
		        	 temp1=(ChannelSalesDetails)ttsoItr.next();
		        	 if(temp1.getProductCategory().equals(cates)){
		        		 System.out.println(temp1.getQuantity());
		        		 temp.setPcName(temp1.getPcName());
		        		 temp.setTaxCategtory(temp1.getTaxCategtory());
		        		 temp.setTaxPercent(temp1.getTaxPercent());
		        		 temp.setProductCategory(temp1.getProductCategory());
		        		 temp.setProductSkuCode(temp1.getProductSkuCode());
		        		 temp.setDiscount(temp.getDiscount()+temp1.getDiscount());
		        		 temp.setFixedfee(temp.getFixedfee()+temp1.getFixedfee());
		        		 temp.setGrossNetRate(temp.getGrossNetRate()+temp1.getGrossNetRate());
		        		 temp.setGrossProfit(temp.getGrossProfit()+temp1.getGrossProfit());
		        		 temp.setNegativeAmount(temp.getNegativeAmount()+temp1.getNegativeAmount());
		        		 temp.setPositiveAmount(temp.getPositiveAmount()+temp1.getPositiveAmount());
		        		 temp.setNetPaymentResult(temp.getNetPaymentResult()+temp1.getNetPaymentResult());
		        		 temp.setNetRate(temp.getNetRate()+temp1.getNetRate());
		        		 temp.setNetSaleQuantity(temp.getNetSaleQuantity()+temp1.getNetSaleQuantity());
		        		 temp.setOrderMRP(temp.getOrderMRP()+temp1.getOrderMRP());
		        		 temp.setOrderSP(temp.getOrderSP()+temp1.getOrderSP());
		        		 temp.setPartnerCommission(temp.getPartnerCommission()+temp1.getPartnerCommission());
		        		 temp.setPaymentDifference(temp.getPaymentDifference()+temp1.getPaymentDifference());
		        		 temp.setPccAmount(temp.getPccAmount()+temp1.getPccAmount());
		        		 temp.setPoPrice(temp.getPoPrice()+temp1.getPoPrice());
		        		 temp.setPr(temp.getPr()+temp1.getPr());
		        		 temp.setQuantity(temp.getQuantity()+temp1.getQuantity());
		        		 temp.setReturnOrRTOCharges(temp.getReturnOrRTOCharges()+temp1.getReturnOrRTOCharges());
		        		 temp.setTotalAmountRecieved(temp.getTotalAmountRecieved()+temp1.getTotalAmountRecieved());
		        		 temp.setShippingCharges(temp.getShippingCharges()+temp1.getShippingCharges());
		        		 temp.setServiceTax(temp.getServiceTax()+temp1.getServiceTax());
		        		 temp.setReturnorrtoQty(temp.getReturnorrtoQty()+temp1.getReturnorrtoQty());
		        		 temp.setReturnOrRTOChargestoBeDeducted(temp.getReturnOrRTOChargestoBeDeducted()+temp1.getReturnOrRTOChargestoBeDeducted());
		        		 temp.setReturnOrRTOCharges(temp.getReturnOrRTOCharges()+temp1.getReturnOrRTOCharges());
		        		 temp.setGroupByName("categoryName");
		        		 temp.setOrderId(temp1.getOrderId());
		        		 temp.setDateofPayment(temp1.getDateofPayment());
		        		 temp.setUploadDate(temp1.getUploadDate());
		        		 temp.setProductPrice(temp1.getProductPrice());
		        		 temp.setTax(temp.getTax()+temp1.getTax());
		        		 temp.setStartDate(startDate.getDate()+"|"+startDate.getMonth()+"|"+startDate.getYear());
		        		 temp.setEndDate(endDate.getDate()+"|"+endDate.getMonth()+"|"+endDate.getYear());
		        		 //temp.setTaxPercent(temp1.getTaxPercent());
		        		 
	        			 temp.setNrTax(temp.getNrTax()+ ((temp1.getTax()/temp1.getQuantity())* temp1.getReturnorrtoQty()));
	        			 temp.setNrReturn(temp.getNrReturn()+(temp1.getGrossNetRate()*temp1.getReturnorrtoQty()));
	        			 temp.setReturnSP(temp.getReturnSP()+((temp1.getOrderSP()/temp1.getQuantity())*temp1.getReturnorrtoQty()));
	        			 
		        	 }
	       	 
		         }  ttsonew.add(temp);    }
		         
		         
		         System.out.println("TTSO NEW SIZE IS "+ttsonew.size());
			 session.getTransaction().commit();
		     session.close();
		   }
		   catch (Exception e) {			   
			   log.error(e);
			   log.info("Error :",e);
			   throw new CustomException(GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3, GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);
		}
		 log.info("*** getAllPartnerTSOdetails exit ***");
	 
		 return ttsonew;
	}
	
	public static ProjectionList getAllPL(String name){
		 ProjectionList projList = Projections.projectionList();
	 
		 projList.add(Projections.property("pcName"));
		 projList.add(Projections.property("quantity"));
		 projList.add(Projections.property("discount"));
		 projList.add(Projections.property("orderMRP"));
		 projList.add(Projections.property("orderSP"));
		 projList.add(Projections.property("shippingCharges"));
		 projList.add(Projections.property("netSaleQuantity"));
		 projList.add(Projections.property("netRate"));
		 projList.add(Projections.property("grossNetRate"));
		 projList.add(Projections.property("partnerCommission"));
		 projList.add(Projections.property("pr"));
		 projList.add(Projections.property("totalAmountRecieved"));
		 projList.add(Projections.property("poPrice"));
		 projList.add(Projections.property("grossProfit"));
		 projList.add(Projections.property("serviceTax"));
		 projList.add(Projections.property("fixedfee"));
		 projList.add(Projections.property("pccAmount"));		 
		 projList.add(Projections.property("orderPayment.negativeAmount"));
		 projList.add(Projections.property("orderPayment.positiveAmount"));
		 projList.add(Projections.property("orderPayment.actualrecived2"));
		 projList.add(Projections.property("orderPayment.netPaymentResult"));
		 projList.add(Projections.property("orderPayment.paymentDifference"));			 
		 projList.add(Projections.property("orderReturnOrRTO.returnOrRTOCharges"));
		 projList.add(Projections.property("orderReturnOrRTO.returnorrtoQty"));
		 projList.add(Projections.property("orderReturnOrRTO.returnOrRTOChargestoBeDeducted"));
		 projList.add(Projections.property("orderId"));
		 projList.add(Projections.property("invoiceID"));
		 projList.add(Projections.property("productSkuCode"));
		 projList.add(Projections.property("orderTax.taxCategtory"));
		 projList.add(Projections.property("paymentType"));
		 projList.add(Projections.property("orderPayment.paymentCycle"));
		 projList.add(Projections.property("orderPayment.uploadDate"));
		 projList.add(Projections.property("orderPayment.dateofPayment"));
		 projList.add(Projections.property("orderTax.tax"));
		 
		 return projList;
	}
	public static ProjectionList geProductPL(String name){
		 ProjectionList projList = Projections.projectionList();	 
		 projList.add(Projections.property("productSkuCode"));
		 projList.add(Projections.property("categoryName"));
		 projList.add(Projections.property("productPrice"));
		 return projList;
	}
	
	
	public Criteria getCriteriaOR(Date startDate,Date endDate,int sellerId){
		 Session session=sessionFactory.openSession();			 
		 Criteria criteria1=session.createCriteria(Order.class);
		 criteria1.createAlias("seller", "seller", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("orderPayment", "orderPayment", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("orderTax", "orderTax", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("orderReturnOrRTO", "orderReturnOrRTO", CriteriaSpecification.LEFT_JOIN);
		// criteria1.createAlias("orderTimeline", "orderTimeline", CriteriaSpecification.LEFT_JOIN);
		 criteria1.add(Restrictions.eq("seller.id", sellerId));
		 criteria1.add(Restrictions.isNotNull("orderReturnOrRTO.returnDate"));
		 criteria1.add(Restrictions.isNotNull("orderReturnOrRTO.returnOrRTOId"));
		 criteria1.add(Restrictions.eq("seller.id", sellerId));
		 criteria1.add(Restrictions.between("orderReturnOrRTO.returnDate",startDate, endDate));
		 //criteria1.setProjection(Projections.distinct(Projections.property("orderId")));
		 criteria1.setProjection(getPL("pcName"));
		 //criteria1.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		 return criteria1;
	}
	
	public Criteria getCriteriaPL(Date startDate,Date endDate,int sellerIdfromSession) {
		 Session session=sessionFactory.openSession();
		 Criteria criteria1=session.createCriteria(Order.class);
		 criteria1.createAlias("seller", "seller", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("orderPayment", "orderPayment", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("orderTax", "orderTax", CriteriaSpecification.INNER_JOIN);
		 criteria1.createAlias("paymentUpload", "paymentUpload", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("orderReturnOrRTO", "orderReturnOrRTO", CriteriaSpecification.INNER_JOIN);
		 criteria1.add(Restrictions.eq("seller.id", sellerIdfromSession));
		 criteria1.add(Restrictions.between("orderDate",startDate, endDate));
		 //criteria1.setProjection(Projections.distinct(Projections.property("orderId")));
		 criteria1.setProjection(getPL("pcName"));
		 return criteria1;
	}
	
	public Criteria getCriteria(Date startDate,Date endDate,int sellerIdfromSession) {
		 Session session=sessionFactory.openSession();
		 Criteria criteria1=session.createCriteria(Order.class);
		 criteria1.createAlias("seller", "seller", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("orderPayment", "orderPayment", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("orderTax", "orderTax", CriteriaSpecification.INNER_JOIN);
		 criteria1.createAlias("paymentUpload", "paymentUpload", CriteriaSpecification.LEFT_JOIN);
		 criteria1.createAlias("orderReturnOrRTO", "orderReturnOrRTO", CriteriaSpecification.INNER_JOIN);
		 criteria1.add(Restrictions.eq("seller.id", sellerIdfromSession));
		 criteria1.add(Restrictions.between("orderDate",startDate, endDate));
		 //criteria1.setProjection(Projections.distinct(Projections.property("orderId")));
		 criteria1.setProjection(getAllPL("product"));
		 return criteria1;
	}
	
	@Override
	public List<ChannelSalesDetails> getPaymentsReceievedDetails(Date startDate, Date endDate, int sellerIdfromSession)
			throws CustomException {
		 List<ChannelSalesDetails> ttso=new ArrayList<ChannelSalesDetails>();
		 ArrayList<ChannelSalesDetails> ttsonew=new ArrayList<ChannelSalesDetails>();
		 
		 try
		 {
			 Session session=sessionFactory.openSession();
			 session.getTransaction().begin();
			 Criteria criteria1=getCriteria(startDate,endDate,sellerIdfromSession);
			 
			 List pcNameList=criteria1.list();
			 
			 System.out.println("SIZE OF THE LIST IS "+pcNameList.size());

				Iterator pcNameItr=pcNameList.iterator();
				ChannelSalesDetails temp=null;
				ArrayList<Integer> orderId=new ArrayList<Integer>();
				
				while(pcNameItr.hasNext()){
					 Object[] recordsRow = (Object[])pcNameItr.next();
					 temp=new ChannelSalesDetails();
					
					 populateChannelSalesDetails(temp,recordsRow,startDate,endDate,"getPayments");
					 
					 temp.setGroupByName("getPayments");
					 if(!orderId.contains(new Integer(temp.getOrderId())))
					 ttso.add(temp);
					 orderId.add(new Integer(temp.getOrderId()));
				}
				
		Collections.sort(ttso,new Comparator(){
			    public int compare(Object o1,Object o2){
					return	((ChannelSalesDetails) o1).getPcName().compareTo(((ChannelSalesDetails) o2).getPcName());
					}
				});
		         
		         System.out.println("TTSO NEW SIZE IS "+ttso.size());
			 session.getTransaction().commit();
		     session.close();
		   }
		   catch (Exception e) {			   
			   log.error(e);
			   log.info("Error :",e);
			   throw new CustomException(GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3, GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);
		}
		 log.info("*** getAllPartnerTSOdetails exit ***");
		 List aList=getConsolidatedDetails(startDate,endDate,sellerIdfromSession);
		  ttso.addAll(aList);
		  return ttso;
	}

	@Override
	public List<ChannelSalesDetails> getOrderwiseGPDetails(Date startDate,
			Date endDate, int sellerIdfromSession) throws CustomException {
		 List<ChannelSalesDetails> ttso=new ArrayList<ChannelSalesDetails>();
		 ArrayList<ChannelSalesDetails> ttsonew=new ArrayList<ChannelSalesDetails>();
		 
		 try
		 {
			 Session session=sessionFactory.openSession();
			 session.getTransaction().begin();
			 Criteria criteria1=getCriteria(startDate,endDate,sellerIdfromSession);
			 
			 List pcNameList=criteria1.list();
			 
			 System.out.println("SIZE OF THE LIST IS "+pcNameList.size());
			 
			 Criteria criteria2=session.createCriteria(Product.class);
			 		  criteria2.setProjection(geProductPL("product"));
			 		  
		  
			 List productList = criteria2.list();
			 
				Iterator prNameItr=productList.iterator();
				HashMap<String,String> prMap=new HashMap<String,String>();
				HashMap<String,String> prPriceMap=new HashMap<String,String>();
				
				while(prNameItr.hasNext()){
					 Object[] recordsRow = (Object[])prNameItr.next();
					 prMap.put(recordsRow[0].toString(), recordsRow[1].toString());
					 prPriceMap.put(recordsRow[0].toString(), recordsRow[2].toString());
					 
					 System.out.println("Product Price Is " +recordsRow[2].toString());
				}

			 		
				Iterator pcNameItr=pcNameList.iterator();
				ChannelSalesDetails temp=null;
				ArrayList<Integer> orderId=new ArrayList<Integer>();
				
				while(pcNameItr.hasNext()){
					 Object[] recordsRow = (Object[])pcNameItr.next();
					 temp=new ChannelSalesDetails();
					
					 populateChannelSalesDetails(temp,recordsRow,startDate,endDate,"categoryName");
					 
					 temp.setGroupByName(null);
					 temp.setProductCategory(prMap.get(temp.getProductSkuCode()));
					 //temp.setPcName(prMap.get(temp.getProductSkuCode()));
					 temp.setProductPrice(Float.parseFloat(prPriceMap.get(temp.getProductSkuCode())));
					 if(!orderId.contains(new Integer(temp.getOrderId())))
					 ttso.add(temp);
					 orderId.add(new Integer(temp.getOrderId()));
				}
				
				
				System.out.println("SIZE OF THE TTSO IS "+ttso.size());
				
				
		Collections.sort(ttso,new Comparator(){
			    public int compare(Object o1,Object o2){
					return	((ChannelSalesDetails) o1).getPcName().compareTo(((ChannelSalesDetails) o2).getPcName());
					}
				});
	    
		    	int i=0;
		    	ChannelSalesDetails temp1=null;
		    	Iterator ttsoItr= ttso.iterator();
		    	Iterator cate= ttso.iterator();
		    	
		    	HashSet st=new HashSet();
		    	HashSet<Integer> oid=new HashSet<Integer>();
		    	String name=null;
		    	while(cate.hasNext()){
		    		name=((ChannelSalesDetails)cate.next()).getPcName();
		    		if(!st.contains(name))
		    			st.add(name);
		    	}
		    	System.out.print("SIZE IS CATEGOY SIZE IS "+st.size());
		    	
		    	Iterator stItr=st.iterator();
		    	
		    	while(stItr.hasNext()){
		    		temp = new ChannelSalesDetails();
		    		String cates=stItr.next().toString();
		    		ttsoItr=null;
		    		ttsoItr= ttso.iterator();
		    		
		         while(ttsoItr.hasNext()){
		        	 
		        	 temp1=(ChannelSalesDetails)ttsoItr.next();
		        	 if(temp1.getPcName().equals(cates)){
		        		 System.out.println(temp1.getQuantity());
		        		 temp.setPcName(temp1.getPcName());
		        		 temp.setTaxCategtory(temp1.getTaxCategtory());
		        		 temp.setProductCategory(temp1.getProductCategory());
		        		 temp.setProductSkuCode(temp1.getProductSkuCode());
		        		 temp.setDiscount(temp.getDiscount()+temp1.getDiscount());
		        		 temp.setFixedfee(temp.getFixedfee()+temp1.getFixedfee());
		        		 temp.setGrossNetRate(temp.getGrossNetRate()+temp1.getGrossNetRate());
		        		 temp.setGrossProfit(temp.getGrossProfit()+temp1.getGrossProfit());
		        		 temp.setNegativeAmount(temp.getNegativeAmount()+temp1.getNegativeAmount());
		        		 temp.setPositiveAmount(temp.getPositiveAmount()+temp1.getPositiveAmount());
		        		 temp.setNetPaymentResult(temp.getNetPaymentResult()+temp1.getNetPaymentResult());
		        		 temp.setNetRate(temp.getNetRate()+temp1.getNetRate());
		        		 temp.setNetSaleQuantity(temp.getNetSaleQuantity()+temp1.getNetSaleQuantity());
		        		 temp.setOrderMRP(temp.getOrderMRP()+temp1.getOrderMRP());
		        		 temp.setOrderSP(temp.getOrderSP()+temp1.getOrderSP());
		        		 temp.setPartnerCommission(temp.getPartnerCommission()+temp1.getPartnerCommission());
		        		 temp.setPaymentDifference(temp.getPaymentDifference()+temp1.getPaymentDifference());
		        		 temp.setPccAmount(temp.getPccAmount()+temp1.getPccAmount());
		        		 temp.setPoPrice(temp.getPoPrice()+temp1.getPoPrice());
		        		 temp.setPr(temp.getPr()+temp1.getPr());
		        		 temp.setQuantity(temp.getQuantity()+temp1.getQuantity());
		        		 temp.setReturnOrRTOCharges(temp.getReturnOrRTOCharges()+temp1.getReturnOrRTOCharges());
		        		 temp.setTotalAmountRecieved(temp.getTotalAmountRecieved()+temp1.getTotalAmountRecieved());
		        		 temp.setShippingCharges(temp.getShippingCharges()+temp1.getShippingCharges());
		        		 temp.setServiceTax(temp.getServiceTax()+temp1.getServiceTax());
		        		 temp.setReturnorrtoQty(temp.getReturnorrtoQty()+temp1.getReturnorrtoQty());
		        		 temp.setReturnOrRTOChargestoBeDeducted(temp.getReturnOrRTOChargestoBeDeducted()+temp1.getReturnOrRTOChargestoBeDeducted());
		        		 temp.setReturnOrRTOCharges(temp.getReturnOrRTOCharges()+temp1.getReturnOrRTOCharges());
		        		 temp.setGroupByName("categoryName");
		        		 temp.setOrderId(temp1.getOrderId());
		        		 temp.setDateofPayment(temp1.getDateofPayment());
		        		 temp.setUploadDate(temp1.getUploadDate());
		        		 temp.setProductPrice(temp1.getProductPrice());
		        		 //temp.setGroupByName("pcName");
		        		 
		        	 }
	       	 
		         }  
		         temp.setStartDate(startDate.toString());
		         temp.setEndDate(endDate.toString());
		         ttsonew.add(temp);    }
		         
		         
		         System.out.println("TTSO NEW SIZE IS "+ttsonew.size());
			 session.getTransaction().commit();
		     session.close();
		   }
		   catch (Exception e) {			   
			   log.error(e);
			   log.info("Error :",e);
			   throw new CustomException(GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3, GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);
		}
		 log.info("*** getAllPartnerTSOdetails exit ***");
	 
		 return ttso;

	}

	public List<ChannelSalesDetails> getConsolidatedDetails(Date startDate,Date endDate, int sellerId) throws CustomException{
		// TODO Auto-generated method stub
		System.out.println("GET CHNNEL SALES DETAILS");
		 log.info("*** getAllPartnerTSOdetails start ***");
		 Session session=sessionFactory.openSession();	
		 List<ChannelSalesDetails> ttso=new ArrayList<ChannelSalesDetails>();
		 
		 		Criteria criteria2=session.createCriteria(Product.class);
		 		criteria2.setProjection(geProductPL("product"));
		  

		  		List productList = criteria2.list();

				Iterator prNameItr=productList.iterator();
				HashMap<String,String> prMap=new HashMap<String,String>();
				HashMap<String,String> prPriceMap=new HashMap<String,String>();
	
	while(prNameItr.hasNext()){
		 Object[] recordsRow = (Object[])prNameItr.next();
		 prMap.put(recordsRow[0].toString(), recordsRow[1].toString());
		 prPriceMap.put(recordsRow[0].toString(), recordsRow[2].toString());
		 
		 System.out.println("Product Price Is " +recordsRow[2].toString());
	}
		 
		 try
		 {
			 		 
			 Criteria criteria1=session.createCriteria(Order.class);
			 criteria1.createAlias("seller", "seller", CriteriaSpecification.LEFT_JOIN);
			 criteria1.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
			 criteria1.createAlias("orderPayment", "orderPayment", CriteriaSpecification.LEFT_JOIN);
			 criteria1.createAlias("orderTax", "orderTax", CriteriaSpecification.LEFT_JOIN);
			 criteria1.createAlias("paymentUpload", "paymentUpload", CriteriaSpecification.LEFT_JOIN);
			 criteria1.createAlias("orderReturnOrRTO", "orderReturnOrRTO", CriteriaSpecification.LEFT_JOIN);
			// criteria1.createAlias("orderTimeline", "orderTimeline", CriteriaSpecification.LEFT_JOIN);
			 criteria1.add(Restrictions.eq("seller.id", sellerId));
			 criteria1.add(Restrictions.between("orderDate",startDate, endDate));
			 //criteria1.setProjection(Projections.distinct(Projections.property("orderId")));
			 criteria1.setProjection(getPL("pcName"));
			 //criteria1.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			 
			 List pcNameList=criteria1.list();
		 
			 System.out.println("SIZE OF THE LIST IS "+pcNameList.size());
			 
			 Criteria ctr=session.createCriteria(Order.class);
			 ProjectionList p=Projections.projectionList()	;
			 p.add(Projections.groupProperty("pcName"));
			 ctr.setProjection(p);
			 List lst=ctr.list();
			 String arr[]=new String[lst.size()];
			 Iterator itrx=lst.iterator();int a=0;
			 
								 while(itrx.hasNext())
								 {
									 arr[a]=itrx.next().toString();a++;
								 }
								 
			 Criteria carr[]=new Criteria[lst.size()];
			 Criteria criteria;
			 session.beginTransaction();
			 for(int x=0;x<carr.length;x++){
				 criteria=null;
				 criteria=session.createCriteria(Order.class);
				 criteria.createAlias("seller", "seller", CriteriaSpecification.LEFT_JOIN);
				 criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
				 criteria.createAlias("orderPayment", "orderPayment", CriteriaSpecification.INNER_JOIN);
				 criteria.createAlias("orderTax", "orderTax", CriteriaSpecification.INNER_JOIN);
				 criteria1.createAlias("paymentUpload", "paymentUpload", CriteriaSpecification.LEFT_JOIN);
				 criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO", CriteriaSpecification.INNER_JOIN);
				// criteria.createAlias("orderTimeline", "orderTimeline", CriteriaSpecification.LEFT_JOIN)
				 criteria.add(Restrictions.eq("seller.id", sellerId)).add(Restrictions.eq("pcName", arr[x]));
				 criteria.add(Restrictions.between("orderDate",startDate, endDate));
				 //criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
				 System.out.println("CRITERIA LISR SIZE IS "+criteria.list().size()+"  "+  arr[x]);
				 carr[x]=criteria;
				 }
			 			 
			 for(int y=0;y<carr.length;y++){
			 carr[y].setProjection(getPL("paymentType"));
			 System.out.println("CRITERIA LISR SIZE IS "+carr[y].list().size()+"  "+  arr[y]);
			 }
			 
			 		ChannelSalesDetails temp=null;int i=0000;
						 for(int m=0;m<carr.length;m++){
							 List<Object[]> results = carr[m].list();
							               
							 Iterator channelItr = results.iterator();
							 while(channelItr.hasNext()){
								 Object[] recordsRow = (Object[])channelItr.next();
								 temp=new ChannelSalesDetails();
								 populateChannelSalesDetails(temp,recordsRow,startDate,endDate,"pc");
								 temp.setGroupByName("paymentTypeOne");
								 temp.setPcName(arr[i]);
								 ttso.add(temp);
						 }i++;}
		
				Iterator pcNameItr=pcNameList.iterator();
				
				while(pcNameItr.hasNext()){
					 Object[] recordsRow = (Object[])pcNameItr.next();
					 temp=new ChannelSalesDetails();
					 populateChannelSalesDetails(temp,recordsRow,startDate,endDate,"pc");
					 temp.setGroupByName(null);
					 ttso.add(temp);
			}

			 session.getTransaction().commit();
		     session.close();
		   }
		   catch (Exception e) {			   
			   log.error(e);
			   log.info("Error :",e);
			   throw new CustomException(GlobalConstant.getAllPartnerTSOdetailsError, new Date(), 3, GlobalConstant.getAllPartnerTSOdetailsErrorCode, e);
		}
		 log.info("*** getAllPartnerTSOdetails exit ***");
	 
		 return ttso;
	}

	public void ttsoReturn(Date startDate,Date endDate,int sellerId,List<ChannelSalesDetails> ttso){
		
		 Session session=sessionFactory.openSession();	
		 
		 Criteria criteriaTax=session.createCriteria(TaxCategory.class);
		 ProjectionList tList = Projections.projectionList();
		 HashMap<String,Float> taxMap=new HashMap<String,Float>();
		 tList.add(Projections.property("taxCatId"));
		 tList.add(Projections.property("taxCatName"));
		 tList.add(Projections.property("taxPercent"));
		 criteriaTax.setProjection(tList);			 
		 List taxList=criteriaTax.list();
		 Iterator txItr=taxList.iterator();
		 while(txItr.hasNext()){
			 Object[] recordsRow = (Object[])txItr.next();
			 taxMap.put(recordsRow[1].toString(),Float.parseFloat(recordsRow[2].toString()));
		 }
				
		ChannelSalesDetails temp=null;
		 Criteria ctr=session.createCriteria(Order.class);
		 ProjectionList p=Projections.projectionList()	;
		 p.add(Projections.groupProperty("pcName"));
		 ctr.setProjection(p);
		 List lst=ctr.list();
		 String arr[]=new String[lst.size()];
		 Iterator itrx=lst.iterator();int a=0;
		 
							 while(itrx.hasNext())
							 {
								 arr[a]=itrx.next().toString();a++;
							 }
							 
		 Criteria carr[]=new Criteria[lst.size()];
		 Criteria criteria;
		 session.beginTransaction();
		 for(int x=0;x<carr.length;x++){
			 criteria=null;
			 criteria=session.createCriteria(Order.class);
			 criteria.createAlias("seller", "seller", CriteriaSpecification.LEFT_JOIN);
			 criteria.createAlias("customer", "customer", CriteriaSpecification.LEFT_JOIN);
			 criteria.createAlias("orderPayment", "orderPayment", CriteriaSpecification.INNER_JOIN);
			 criteria.createAlias("orderTax", "orderTax", CriteriaSpecification.INNER_JOIN);
			 criteria.createAlias("orderReturnOrRTO", "orderReturnOrRTO", CriteriaSpecification.INNER_JOIN);
			// criteria.createAlias("orderTimeline", "orderTimeline", CriteriaSpecification.LEFT_JOIN)
			 criteria.add(Restrictions.eq("seller.id", sellerId)).add(Restrictions.eq("pcName", arr[x]));
			 criteria.add(Restrictions.between("orderReturnOrRTO.returnDate",startDate, endDate));
			 //criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			 System.out.println("CRITERIA LISR SIZE IS "+criteria.list().size()+"  "+  arr[x]);
			 carr[x]=criteria;
			 }
			 
		 for(int y=0;y<carr.length;y++){
		 carr[y].setProjection(getPL("orderTax"));
		 System.out.println("CRITERIA LISR SIZE IS "+carr[y].list().size()+"  "+  arr[y]);
		 }
		 
		 		int i=0;
					 for(int m=0;m<carr.length;m++){
						 List<Object[]> results = carr[m].list();
						               
						 Iterator channelItr = results.iterator();
						 while(channelItr.hasNext()){
							 Object[] recordsRow = (Object[])channelItr.next();
							 temp=new ChannelSalesDetails();
							 populateChannelSalesDetails(temp,recordsRow,startDate,endDate,"pc");
							 temp.setGroupByName("returnGroup");
							 temp.setPcName(arr[i]);
							 temp.setTaxPercent(taxMap.get(temp.getTaxCategtory()));
							 ttso.add(temp);
					 }i++;}

	

	}

}

