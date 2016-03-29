/**
 * @Auther Kapil Halewale
 */
package com.o2r.dao;

import java.util.List;

import com.o2r.model.SellerAccount;


public interface SellerAccountDao {
	 public List<SellerAccount> listSellerAccounts();
	 public SellerAccount getSellerAccount(int cid);	 
	 public void deleteSellerAccount(SellerAccount sellerAccount);
}
