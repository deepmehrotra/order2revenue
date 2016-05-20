package com.o2r.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.controller.SellerController;
import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
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

	static Logger log = Logger.getLogger(AdminDaoImpl.class.getName());

	@Override
	public void addEmployee(Employee employee)throws CustomException {

		log.info("*** addEmployee start***");		
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(employee);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addEmployeeError, new Date(), 1, GlobalConstant.addEmployeeErrorCode, e);
		}
		log.info("*** addEmployee exit***");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> listEmployeess(int pageno)throws CustomException {
		log.info("*** listEmployee Start ***");
		List<Employee> returnlist=new ArrayList<Employee>();
		try{
		returnlist = sessionFactory.getCurrentSession().createCriteria(Employee.class).setMaxResults(LIMITITEMSPERPAGE).setFirstResult(LIMITITEMSPERPAGE * (pageno - 1)).list();		
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.listEmployeeError, new Date(), 3, GlobalConstant.listEmployeeErrorCode, e);
		}
		log.info("*** listEmployee exit ***");
		return returnlist;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Employee> listEmployeess()throws CustomException {
		
		log.info("*** listEmployeess Start ***");
		List<Employee> employeess=new ArrayList<Employee>();
		try{
		employeess=sessionFactory.getCurrentSession().createCriteria(Employee.class).list();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.listEmployeeError, new Date(), 3, GlobalConstant.listEmployeeErrorCode, e);
		}
		log.info("*** listEmployeess exit ***");
		return employeess;
	}

	@Override
	public Employee getEmployee(int empid)throws CustomException {
		
		log.info("*** getEmployee start ***");
		Employee employee=new Employee();
		try{
		employee=(Employee) sessionFactory.getCurrentSession().get(Employee.class, empid);
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.getEmployeeError, new Date(), 3, GlobalConstant.getEmployeeErrorCode, e);
		}
		log.info("*** getEmployee exit ***");
		return employee;
	}

	@Override
	public void deleteEmployee(Employee employee)throws CustomException {
		log.info("*** deleteEmployee start ***");
		try{
		sessionFactory.getCurrentSession().createQuery("DELETE FROM Employee WHERE empid = "+ employee.getEmpId()).executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.deleteEmployeeError, new Date(), 3, GlobalConstant.deleteEmployeeErrorCode, e);
		}
		log.info("*** deleteEmployee exit ***");
	}

	@Override
	public void addQuery(GenericQuery query)throws CustomException {
		
		log.info("*** addQuery start ***");		
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(query);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addQueryError, new Date(), 3, GlobalConstant.addQueryErrorCode, e);
		}		
		log.info("*** addQuery exit ***");
	}

	@Override
	public List<GenericQuery> listQueries()throws CustomException {		
		
		log.info("*** listQueries start ***");
		List<GenericQuery> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(GenericQuery.class);
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.addOrder(org.hibernate.criterion.Order.desc("queryTime"));
			returnlist = criteria.list();			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.listQueriesError, new Date(), 3, GlobalConstant.listQueriesErrorCode, e);			
		}
		log.info("*** listQueries exit ***");
		return returnlist;
	}

}