/**
 * @Auther Kapil Halewale
 */
package com.o2r.service;

import java.util.List;

import com.o2r.model.AccountTransaction;
import com.o2r.model.SellerAccount;

public interface SellerAccountService {
	
	public void saveSellerAccount(SellerAccount sellerAccount); 
	
	public List<SellerAccount> listSellerAccounts();
	 
	 public SellerAccount getSellerAccount(int cid);	 
	 
	 public void deleteSellerAccount(SellerAccount sellerAccount);
	 
	 public AccountTransaction getLastTXN(int SellerId);	 
}
