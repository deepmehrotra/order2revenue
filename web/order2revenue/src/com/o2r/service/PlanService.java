package com.o2r.service;
/**
 * @author Kapil Kumar
 *
 */
import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.Plan;

public interface PlanService {

	 public void addPlan(Plan plan)throws CustomException;
	 public List<Plan> listPlans()throws CustomException;
	 public Plan getPlan(int pid)throws CustomException;	 
	 public void deletePlan(Plan plan)throws CustomException;
}
