package com.o2r.helper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.o2r.model.Seller;
import com.o2r.service.SellerService;

@Service("helperClass")
public class HelperClass {

	@Autowired
	private SellerService sellerService;
	Seller seller =null;
	public int getSellerIdfromSession(HttpServletRequest request)
			throws Exception {
		
		System.out.println("&&&&&&& Sessoin ID : " + request.getSession().getId());
		int sellerId = 0;
		System.out.println(" Inside seller id from session :"
				+ request.getUserPrincipal().getName());
		if (request.getSession().getAttribute("sellerId") != null) {
			System.out.println(" Getting id from session");
			sellerId = Integer.parseInt(request.getSession()
					.getAttribute("sellerId").toString());
		/*	if(request.getSession()
					.getAttribute("logoUrl")==null)
			{
				seller = sellerService.getSeller(sellerId);
				request.getSession().setAttribute("logoUrl", seller.getLogoUrl());
			}*/
		} else {
			// SellerDao sellerdao=new SellerDaoImpl();

			seller = sellerService.getSeller(request.getUserPrincipal()
					.getName());

			Seller seller = sellerService.getSeller(request.getUserPrincipal().getName());

			if (seller != null) {
				System.out.println(" Getting id from db");
				
				request.getSession().setAttribute("sellerId", seller.getId());
				//request.getSession().setAttribute("logoUrl", seller.getLogoUrl());
				sellerId = seller.getId();
			}
		}
		if (sellerId != 0)
			return sellerId;
		else
			throw new NullPointerException("User not available");
	}

}
