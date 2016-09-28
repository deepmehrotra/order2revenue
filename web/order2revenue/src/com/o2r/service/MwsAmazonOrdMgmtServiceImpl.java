package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.MwsAmazonOrdMgmtDao;
import com.o2r.model.Order;

@Service("mwsAmazonOrdMgmtService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MwsAmazonOrdMgmtServiceImpl implements MwsAmazonOrdMgmtService {

	@Autowired
	private MwsAmazonOrdMgmtDao amazonOrdMgmtDao;
	
	@Override
	public List<Order> getOrderList() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
