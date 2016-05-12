package com.o2r.dao;

import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.Seller;
import com.o2r.model.State;

/**
 * @author Deep Mehrotra
 *
 */
//GIT Test
public interface SellerDao {
 
 public void addSeller(Seller seller)throws CustomException;

 public List<Seller> listSeller()throws CustomException;
 
 public Seller getSeller(int sellerid)throws CustomException;
 
 public void deleteSeller(Seller seller)throws CustomException;
 
 public Seller getSeller(String email)throws CustomException;
 
 public void planUpgrade(int pid, double totalAmount, long orderCount, int sellerid)throws CustomException;

 public List<State> listStates();

 public void addToProductStockList();

public int getStateDeliveryTime(int sellerId, String statename) throws CustomException;

public State getStateByName(String statename) throws CustomException;

public void updateProcessedOrdersCount(int pid, int processedOrderCount) throws CustomException;

}
