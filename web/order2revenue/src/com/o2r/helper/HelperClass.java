package com.o2r.helper;

import javax.servlet.http.HttpServletRequest;

import com.o2r.dao.SellerDao;
import com.o2r.dao.SellerDaoImpl;
import com.o2r.model.Seller;


public class HelperClass {
	
	
	public static int getSellerIdfromSession(HttpServletRequest request) throws NullPointerException
	{
		int sellerId=0;
		System.out.println(" Inside seller id from session :"+request.getUserPrincipal().getName());
		if(request.getSession().getAttribute("sellerId")!=null)
		{
			sellerId=Integer.parseInt(request.getSession().getAttribute("sellerId").toString());
		}
		else
		{
		SellerDao sellerdao=new SellerDaoImpl();
		Seller seller=sellerdao.getSeller(request.getUserPrincipal().getName());
		if(seller!=null)
		{
		request.getSession().setAttribute("sellerId", seller.getId());
		sellerId=seller.getId();
		}
		}
		if(sellerId!=0)
		return sellerId;
		else
			throw new NullPointerException("User not available");
	}

}
