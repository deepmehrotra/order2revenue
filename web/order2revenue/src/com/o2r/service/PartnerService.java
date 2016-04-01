package com.o2r.service;

import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.Partner;

/**
 * @author Deep Mehrotra
 *
 */
public interface PartnerService {
 
 public void addPartner(Partner partner,int sellerId)throws CustomException;

 public List<Partner> listPartners(int sellerId)throws CustomException;
 
 public Partner getPartner(int partnerid)throws CustomException;
 
 public Partner getPartner(String name ,int sellerId)throws CustomException;
 
 public void deletePartner(Partner partner,int sellerId)throws CustomException;
}