/**
 * @author Kapil Kumar
 *
 */
package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.PlanDao;
import com.o2r.model.Plan;

@Service("planService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class PlanServiceImpl implements PlanService {
	@Autowired
	private PlanDao planDao;
	@Override
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addPlan(Plan plan) {
		planDao.addPlan(plan);
	}

	@Override
	public List<Plan> listPlans() {
		return planDao.listPlans();
	}

	@Override
	public Plan getPlan(int pid) {
		return planDao.getPlan(pid);
	}

	@Override
	public void deletePlan(Plan plan) {
		planDao.deletePlan(plan);
	}

}
