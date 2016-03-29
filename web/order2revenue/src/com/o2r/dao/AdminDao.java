package com.o2r.dao;

import java.util.List;

import com.o2r.model.Employee;
import com.o2r.model.GenericQuery;

/**
 * @author Deep Mehrotra
 *
 */
public interface AdminDao {

 public void addEmployee(Employee employee);

 public List<Employee> listEmployeess();

 public List<Employee> listEmployeess(int pageno);

 public Employee getEmployee(int empid);

 public void deleteEmployee(Employee employee);

public void addQuery(GenericQuery query);

public List<GenericQuery> listQueries();
}