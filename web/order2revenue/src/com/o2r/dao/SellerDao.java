package com.o2r.dao;

import java.util.List;

import com.o2r.model.Seller;

/**
 * @author Deep Mehrotra
 *
 */
public interface SellerDao {
 
 public void addSeller(Seller seller);

 public List<Seller> listSeller();
 
 public Seller getSeller(int sellerid);
 
 public void deleteSeller(Seller seller);
 
 public Seller getSeller(String email);
 public void planUpgrade(int pid, int sellerid);
}