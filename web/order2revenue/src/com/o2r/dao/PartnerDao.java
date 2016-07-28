package com.o2r.dao;

import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.MetaPartner;
import com.o2r.model.Partner;

/**
 * @author Deep Mehrotra
 *
 */
public interface PartnerDao {

	public void addPartner(Partner partner, int sellerId)
			throws CustomException;

	public List<Partner> listPartner(int sellerId) throws CustomException;

	public Partner getPartner(long partnerid) throws CustomException;

	public void deletePartner(Partner partner, int sellerId)
			throws CustomException;

	public Partner getPartner(String name, int sellerId) throws CustomException;

	void addMetaPartner(MetaPartner partner) throws CustomException;

	public MetaPartner getMetaPartner(String partnerName) throws CustomException;
}