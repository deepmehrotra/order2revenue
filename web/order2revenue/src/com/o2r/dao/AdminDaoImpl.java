package com.o2r.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.Employee;
import com.o2r.model.GenericQuery;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("adminDao")
public class AdminDaoImpl implements AdminDao {

 @Autowired
 private SessionFactory sessionFactory;
 private static final int LIMITITEMSPERPAGE = 10;

 @Override
public void addEmployee(Employee employee) {
	 System.out.println(" Employee age "+employee.getEmpAge());
	 System.out.println("Employee name :"+employee.getEmpName());
	 try
	 {
   sessionFactory.getCurrentSession().saveOrUpdate(employee);
	 }
	 catch(Exception e)
	 {
		 System.out.println(" Employee exception :"+e.getLocalizedMessage());
	 }
 }

 @Override
@SuppressWarnings("unchecked")
 public List<Employee> listEmployeess(int pageno) {
	 List<Employee> returnlist=sessionFactory.getCurrentSession().createCriteria(Employee.class).setMaxResults(LIMITITEMSPERPAGE).setFirstResult(LIMITITEMSPERPAGE*(pageno-1)).list();
	 System.out.println(" Getting employee records"+returnlist.size());

  return returnlist; }

 @Override
@SuppressWarnings("unchecked")
 public List<Employee> listEmployeess() {
  return sessionFactory.getCurrentSession().createCriteria(Employee.class).list();
 }


 @Override
public Employee getEmployee(int empid) {
  return (Employee) sessionFactory.getCurrentSession().get(Employee.class, empid);
 }

 @Override
public void deleteEmployee(Employee employee) {
  sessionFactory.getCurrentSession().createQuery("DELETE FROM Employee WHERE empid = "+employee.getEmpId()).executeUpdate();
 }

 @Override
 public void addQuery(GenericQuery query) {
 	 System.out.println("Query  "+query.getEmail());
 	 try
 	 {
    sessionFactory.getCurrentSession().saveOrUpdate(query);
 	 }
 	 catch(Exception e)
 	 {
 		 System.out.println(" addQuery :"+e.getLocalizedMessage());
 	 }
  }

 @Override
 public List<GenericQuery> listQueries() {
 	//sellerId=4;
	 System.out.println(" Inside query geting list");
 	List<GenericQuery> returnlist=null;
 	try
 	{
 	Session session=sessionFactory.openSession();
 	   session.beginTransaction();
 	   Criteria criteria=session.createCriteria(GenericQuery.class);
  	  criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
  	 criteria.addOrder(org.hibernate.criterion.Order.desc("queryTime"));
  	  returnlist=criteria.list();
  	  System.out.println(" Queries : "+returnlist);
 	   session.getTransaction().commit();
 	   session.close();
 	}
 	catch(Exception e)
 	{
 		System.out.println(" Exception in getting order list :"+e.getLocalizedMessage());
 	}
 	return returnlist;
 }

}