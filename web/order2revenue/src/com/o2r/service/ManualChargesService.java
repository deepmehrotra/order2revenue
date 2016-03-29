package com.o2r.service;

import java.util.List;

import com.o2r.model.ManualCharges;

/**
 * @author Deep Mehrotra
 *
 */
public interface ManualChargesService {
 
	public void addManualCharges(ManualCharges manualCharges , int sellerId);
	 
	 public List<ManualCharges> listManualCharges(int sellerId);

	 public ManualCharges getManualCharges(int mcId);
	 
	 public void deleteManualCharges(ManualCharges manualCharges,int sellerId);
	 
	 public Double getMCforPaymentID(String paymentId, int sellerId);
}