package com.o2r.service;

import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import com.o2r.dao.SellerDao;
import com.o2r.helper.CustomException;
import com.o2r.model.AccountTransaction;
import com.o2r.model.Seller;
import com.o2r.model.State;
import com.o2r.model.StateDeliveryTime;

/**
 * @author Deep Mehrotra
 *
 */
//GIT Test
@Service("sellerService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class SellerServiceImpl implements SellerService,ServletContextAware {

    private ServletContext context;
    private ServletConfig config;

    @Override
    public void setServletContext(final ServletContext servletContext) {
        this.context = servletContext;
    }

    public ServletContext getServletContext(){
        return context;
    } 

	@Autowired
	private SellerDao sellerDao;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addSeller(Seller seller) throws CustomException {
		sellerDao.addSeller(seller);					
	}	
	
	public List<Seller> listSellers() throws CustomException {
		return sellerDao.listSeller();
	}

	public Seller getSeller(int sellerid) throws CustomException {
		return sellerDao.getSeller(sellerid);
	}

	public void deleteSeller(Seller seller) throws CustomException {
		sellerDao.deleteSeller(seller);
	}

	public Seller getSeller(String email) throws CustomException {
		return sellerDao.getSeller(email);
	}

	public AccountTransaction planUpgrade(String txnStat,String txnid,int pid, double totalAmount, long orderCount, int sellerid) throws CustomException {
		return sellerDao.planUpgrade(txnStat,txnid,pid, totalAmount,orderCount, sellerid);
	}
	
	public void updateProcessedOrdersCount(int sellerId, int processedOrderCount) throws CustomException {
		sellerDao.updateProcessedOrdersCount(sellerId, processedOrderCount);
	}
	
	@Override
	public List<State> listStates() {
		return sellerDao.listStates();
	}
	
	@Override
	 public State getStateByName(String statename) throws CustomException
	 {
		return sellerDao.getStateByName(statename);
	 }
	
	 public void addToProductStockList(){
		 	sellerDao.addToProductStockList();
	 }
	
	@Override
	public void addStateDeliveryTime(List<StateDeliveryTime> stateDelTimeList, int sellerId)
			throws CustomException
	{
		sellerDao.addStateDeliveryTime(stateDelTimeList, sellerId);
	}
	
	@Override
	public Seller getSellerVerCode(String verCode) {		
		return sellerDao.getSellerVerCode(verCode);
	}
	
	@Override
	public boolean sendMail(String to, String subject, String body) {
		return sellerDao.sendMail(to, subject, body);
	}

	@Override
	public List<AccountTransaction> getAccountTransactions(int sellerId) {
		return sellerDao.getAccountTransactions(sellerId);
	}
	
	@Override
	public AccountTransaction upgradeAccountTransaction(String txnStat, String txnid,
			int sellerid) throws CustomException
			{
		return sellerDao.upgradeAccountTransaction(txnStat, txnid, sellerid);
			}
}
