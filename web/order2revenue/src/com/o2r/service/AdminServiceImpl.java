package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.AdminDao;
import com.o2r.helper.CustomException;
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
 public void addEmployee(Employee employee)throws CustomException {
  adminDao.addEmployee(employee);
 }

 @Override
public List<Employee> listEmployeess()throws CustomException {
  return adminDao.listEmployeess();
 }

 @Override
public List<Employee> listEmployeess(int pageno)throws CustomException{
	 return adminDao.listEmployeess(pageno);
 }

 @Override
public Employee getEmployee(int empid)throws CustomException {
  return adminDao.getEmployee(empid);
 }

 @Override
public void deleteEmployee(Employee employee)throws CustomException {
  adminDao.deleteEmployee(employee);
 }

@Override
public void addQuery(GenericQuery query)throws CustomException {
	adminDao.addQuery(query);

}

@Override
public List<GenericQuery> listQueries()throws CustomException {

	return adminDao.listQueries();
}

}