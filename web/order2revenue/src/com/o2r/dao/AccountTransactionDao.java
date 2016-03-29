/**
 * @Auther Kapil Halewale
 */
package com.o2r.dao;

import java.util.List;

import com.o2r.model.AccountTransaction;


public interface AccountTransactionDao {
	
	 public List<AccountTransaction> listAccountTransactions();
	 public AccountTransaction getAccountTransaction(int accTransId);	 
	 public void deleteAccountTransaction(AccountTransaction accountTransaction);
}
