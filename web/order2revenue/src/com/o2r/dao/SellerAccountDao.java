/**
 * @Auther Kapil Halewale
 */
package com.o2r.dao;

import java.util.List;

import com.o2r.model.AccountTransaction;
import com.o2r.model.SellerAccount;


public interface SellerAccountDao {
	 public List<SellerAccount> listSellerAccounts();
	 public SellerAccount getSellerAccount(int id);	 
	 public void deleteSellerAccount(SellerAccount sellerAccount);
	 public void saveSellerAccount(SellerAccount sellerAccount);
	 public AccountTransaction getLastTXN(int SellerId);
}
