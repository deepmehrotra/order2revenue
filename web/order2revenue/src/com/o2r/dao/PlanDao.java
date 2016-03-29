/*
 * @Author Kapil Kumar
 */
package com.o2r.dao;

import java.util.List;

import com.o2r.model.Plan;

public interface PlanDao {

	 public void addPlan(Plan plan);
	 public List<Plan> listPlans();
	 public Plan getPlan(int pid);	 
	 public void deletePlan(Plan plan);

}
