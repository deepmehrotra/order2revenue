/**
 * @Auther Kapil Halewale
 */
package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.SellerAccountDao;
import com.o2r.model.SellerAccount;

@Service("sellerAccountService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SellerAccountServiceImpl implements SellerAccountService {

	@Autowired
	 private SellerAccountDao sellerAccountDao;
	@Override
	public List<SellerAccount> listSellerAccounts() {
		return sellerAccountDao.listSellerAccounts();
	}

	@Override
	public SellerAccount getSellerAccount(int cid) {
		return sellerAccountDao.getSellerAccount(cid);
	}

	@Override
	public void deleteSellerAccount(SellerAccount sellerAccount) {
		sellerAccountDao.deleteSellerAccount(sellerAccount);		
	}

}
