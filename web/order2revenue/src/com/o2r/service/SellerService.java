package com.o2r.service;

import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.Seller;

/**
 * @author Deep Mehrotra
 *
 */
public interface SellerService {
 
 public void addSeller(Seller seller)throws CustomException;

 public List<Seller> listSellers()throws CustomException;
 
 public Seller getSeller(int sellerid)throws CustomException;
 
 public void deleteSeller(Seller seller)throws CustomException;
 
 public Seller getSeller(String email)throws CustomException;
 
 public void planUpgrade(int pid, int sellerid)throws CustomException;
}