package com.o2r.service;
/**
 * @author Kapil Kumar
 *
 */
import java.util.List;

import com.o2r.model.Plan;

public interface PlanService {

	 public void addPlan(Plan plan);
	 public List<Plan> listPlans();
	 public Plan getPlan(int pid);	 
	 public void deletePlan(Plan plan);
}
