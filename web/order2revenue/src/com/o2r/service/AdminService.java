package com.o2r.service;

import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.Employee;
import com.o2r.model.GenericQuery;

/**
 * @author Deep Mehrotra
 *
 */
public interface AdminService {

 public void addEmployee(Employee employee)throws CustomException;

 public List<Employee> listEmployeess()throws CustomException;

 public List<Employee> listEmployeess(int pageno)throws CustomException;

 public Employee getEmployee(int empid)throws CustomException;

 public void deleteEmployee(Employee employee)throws CustomException;

 public void addQuery(GenericQuery query)throws CustomException;

 public List<GenericQuery> listQueries()throws CustomException;
}