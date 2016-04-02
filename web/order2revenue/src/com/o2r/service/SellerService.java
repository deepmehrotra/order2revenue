package com.o2r.service;

import java.util.List;

import com.o2r.model.Seller;
import com.o2r.model.State;

/**
 * @author Deep Mehrotra
 *
 */
//GIT Test
public interface SellerService {

	public void addSeller(Seller seller);

	public List<Seller> listSellers();

	public Seller getSeller(int sellerid);

	public void deleteSeller(Seller seller);

	public Seller getSeller(String email);

	public void planUpgrade(int pid, int sellerid);

	public List<State> listStates();
}