package com.o2r.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.amazonservices.mws.orders.model.ListOrderItemsResult;
import com.o2r.amazonservices.mws.orders.model.ListOrdersResult;
import com.o2r.amazonservices.mws.orders.model.OrderItem;
import com.o2r.bean.AuthInfoBean;
import com.o2r.dao.MwsAmazonOrdMgmtDao;
import com.o2r.model.AmazonOrderInfo;
import com.o2r.model.Order;
import com.o2r.model.PartnerSellerAuthInfo;
import com.o2r.model.Seller;

@Service("mwsAmazonOrdMgmtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MwsAmazonOrdMgmtServiceImpl implements MwsAmazonOrdMgmtService {

	@Autowired
	private MwsAmazonOrdMgmtDao amazonOrdMgmtDao;
	
	@Override
	public List<Order> getOrderList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> getSellers() throws Exception {
		return amazonOrdMgmtDao.getSeller();
	}

	@Override
	public AuthInfoBean getAuthInfoBeanObj(PartnerSellerAuthInfo sellerAuthInfo) throws Exception {
		AuthInfoBean authInfoBean = null;
		try {
			authInfoBean = new AuthInfoBean();
			authInfoBean.setSellerid(sellerAuthInfo.getSellerid());
			authInfoBean.setAccesskey(sellerAuthInfo.getAccesskey());
			authInfoBean.setMwsauthtoken(sellerAuthInfo.getMwsauthtoken());
			authInfoBean.setSecretkey(sellerAuthInfo.getSecretkey());
			authInfoBean.setMarketplaceid(sellerAuthInfo.getMarketplaceid());
			authInfoBean.setServiceurl(sellerAuthInfo.getServiceurl());
			authInfoBean.setStatus(sellerAuthInfo.getStatus());
		} catch (Exception expOj) {
			expOj.printStackTrace();
		}
		return authInfoBean;
	}

	@Override
	public XMLGregorianCalendar getCreatedAfter(Date createdAfter) throws Exception {
		try {
			Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 2);
            calendar.set(Calendar.MONTH, 9);
            calendar.set(Calendar.YEAR, 2016);
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(calendar.getTime());
            XMLGregorianCalendar dateCreatedAfter = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            dateCreatedAfter = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            return dateCreatedAfter;
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return null;
	}

	@Override
	public XMLGregorianCalendar getCreatedBefore(Date createdBefore) throws Exception {
		try {
			Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DATE, 3);
            calendar.set(Calendar.MONTH, 9);
            calendar.set(Calendar.YEAR, 2016);
            calendar.set(Calendar.MINUTE, Calendar.MINUTE - 2);
            System.out.println("date:"+calendar.getTime());
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(calendar.getTime());
            XMLGregorianCalendar dateCreatedBefore = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            dateCreatedBefore = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
            return dateCreatedBefore;
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return null;
	}

	@Override
	public List<String> getConfiguredoOrderStatus() throws Exception {
		List<String> orderStatus = null;
		try {
			orderStatus = new ArrayList<String>();
			orderStatus.add("");
		} catch (Exception expObj) {
			expObj.printStackTrace();
		}
		return orderStatus;
	}

	@Override
	public ListOrdersResult getListOrders(XMLGregorianCalendar createdAfter,
			XMLGregorianCalendar createdBefore, List<String> orderStatus,
			AuthInfoBean authInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListOrderItemsResult getListOrderItems(AuthInfoBean authInfo,
			String amazonOrderId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AmazonOrderInfo getAmazonOrderInfoObj(AuthInfoBean authInfo,
			String amazonOrderId) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveOrderInfo(
			com.o2r.amazonservices.mws.orders.model.Order order,
			List<OrderItem> orderItems) throws Exception {
		//amazonOrdMgmtDao.saveAmazonOrderInfo(orderInfo);
		
	}

}
