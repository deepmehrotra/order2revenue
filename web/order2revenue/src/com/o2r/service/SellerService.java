package com.o2r.service;

import java.util.List;
import java.util.Properties;

import com.o2r.helper.CustomException;
import com.o2r.model.Seller;
import com.o2r.model.State;
import com.o2r.model.StateDeliveryTime;

/**
 * @author Deep Mehrotra
 *
 */
//GIT Test
public interface SellerService {
 
 public void addSeller(Seller seller)throws CustomException;

 public List<Seller> listSellers()throws CustomException;
 
 public Seller getSeller(int sellerid)throws CustomException;
 
 public void deleteSeller(Seller seller)throws CustomException;
 
 public Seller getSeller(String email)throws CustomException;
 
 public void planUpgrade(int pid, double totalAmount, long orderCount, int sellerid)throws CustomException;
 
 public List<State> listStates();
 public State getStateByName(String statename) throws CustomException;
 public void addToProductStockList();
 public void updateProcessedOrdersCount(int sellerId, int processedOrderCount) throws CustomException;
 public void addStateDeliveryTime(List<StateDeliveryTime> stateDelTimeList, int sellerId)throws CustomException;
 
 public Seller getSellerVerCode(String verCode);
 
 public boolean sendMail(String to, String subject, String body);
}
