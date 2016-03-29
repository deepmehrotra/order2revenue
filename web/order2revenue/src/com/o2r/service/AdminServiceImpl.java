package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.AdminDao;
import com.o2r.model.Employee;
import com.o2r.model.GenericQuery;

/**
 * @author Deep Mehrotra
 *
 */
@Service("adminService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AdminServiceImpl implements AdminService {

 @Autowired
 private AdminDao adminDao;

 @Override
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
 public void addEmployee(Employee employee) {
  adminDao.addEmployee(employee);
 }

 @Override
public List<Employee> listEmployeess() {
  return adminDao.listEmployeess();
 }

 @Override
public List<Employee> listEmployeess(int pageno){
	 return adminDao.listEmployeess(pageno);
 }

 @Override
public Employee getEmployee(int empid) {
  return adminDao.getEmployee(empid);
 }

 @Override
public void deleteEmployee(Employee employee) {
  adminDao.deleteEmployee(employee);
 }

@Override
public void addQuery(GenericQuery query) {
	adminDao.addQuery(query);

}

@Override
public List<GenericQuery> listQueries() {

	return adminDao.listQueries();
}

}