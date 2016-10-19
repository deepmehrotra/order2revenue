package com.o2r.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("DetailedDashboardService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class DetailedDashboardServiceImpl implements DetailedDashboardService{

}
