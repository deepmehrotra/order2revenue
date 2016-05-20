package com.o2r.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;
import com.o2r.model.Seller;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("expenseDao")
public class ExpenseDaoImpl implements ExpenseDao {

	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(ExpenseDaoImpl.class.getName());

	@Override
	public void addExpense(Expenses expense, int sellerId)throws CustomException {
		log.info("*** addExpense start ***");
		// sellerId=4;
		Seller seller = null;
		ExpenseCategory parentcategory = null;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			/*
			 * if(expense.getExpenseId()!=0) { session.saveOrUpdate(expense); }
			 * else {
			 */

			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			expense.setCreatedOn(new Date());
			expense.setSellerId(sellerId);
			System.out.println(" Inside expese add");
			List<ExpenseCategory> expcat = seller.getExpensecategories();
			if (expcat != null) {
				for (ExpenseCategory expCat : expcat) {
					if (expCat.getExpcatName().equalsIgnoreCase(
							expense.getExpenseCatName())) {
						parentcategory = expCat;
					}
				}
			}
			System.out.println(" getting  parent category ");

			if (parentcategory != null && expense.getExpenseId() == 0) {
				expense.setExpenseCategory(parentcategory);
				parentcategory.getExpenses().add(expense);
			}
			System.out.println(" Added parent category to seller");
			session.saveOrUpdate(parentcategory);
			// session.saveOrUpdate(expense);

			// }
		} catch (Exception e) {
			/*
			 * System.out.println("Inside exception  "+e.getLocalizedMessage());
			 * e.printStackTrace();
			 */
			session.getTransaction().rollback();
			log.error(e);
			throw new CustomException(GlobalConstant.addExpenseError,new Date(), 1, GlobalConstant.addExpenseErrorCode, e);
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		log.info("*** addExpense exit ***");
	}

	@Override
	public double getMonthlyAmount(int catId, int sellerId)throws CustomException {

		log.info("*** getMonthlyAmount start ***");
		List<Long> returnlist = null;
		Session session = null;
		Date tommorrowDate = new Date();
		tommorrowDate.setDate(tommorrowDate.getDate() + 1);
		Date monthStartDate = new Date();
		monthStartDate.setDate(1);
		Double returnAmount = 0.0;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			returnlist = new ArrayList<Long>();
			/*
			 * Code for caluclating sku count
			 */
			Criteria criteriaforSkuCount = session
					.createCriteria(Expenses.class);
			criteriaforSkuCount
					.createAlias("expenseCategory", "expCat",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("expCat.expcategoryId", catId))
					.add(Restrictions.between("createdOn", monthStartDate,
							tommorrowDate));
			criteriaforSkuCount
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("amount"));
			criteriaforSkuCount.setProjection(projList);
			List<Object[]> expenseAmount = criteriaforSkuCount.list();
			Iterator catIterator = expenseAmount.iterator();
			if (expenseAmount != null) {
				while (catIterator.hasNext()) {
					// Object[] recordsRow = (Object[])catIterator.next();
					Double recordsRow = (Double) catIterator.next();
					// System.out.println(" Length of record row : "+recordsRow.length);
					/*
					 * if(recordsRow!=null&&recordsRow.length!=0&&recordsRow[0]!=
					 * null) {
					 * returnAmount=Double.parseDouble(recordsRow[0].toString
					 * ());
					 * 
					 * }
					 */
					returnAmount = recordsRow;
					System.out.println("recordsRow:  " + recordsRow);
				}
			}

		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.getMonthlyAmountError,
					new Date(), 3, GlobalConstant.getMonthlyAmountErrorCode, e);
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		log.info("*** getMonthlyAmount exit ***");
		if (returnAmount != null)
			return returnAmount;
		else
			return 0.0;
	}

	@Override
	public void addExpenseCategory(ExpenseCategory category, int sellerId)throws CustomException {
		log.info("*** addExpenseCategory start ***");
		// sellerId=4;
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			seller = (Seller) criteria.list().get(0);
			category.setCreatedOn(new Date());

			System.out.println(" Inside expense category  add");
			seller.getExpensecategories().add(category);
			session.saveOrUpdate(seller);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.addExpenseCategoryError,
					new Date(), 1, GlobalConstant.addExpenseCategoryErrorCode,
					e);
			/* System.out.println("Inside exception  "+e.getLocalizedMessage()); */
		}
		log.info("*** addExpenseCategory exit ***");
		/*
		 * System.out.println(" Saved Expense Group : "+category.getExpcategoryId
		 * ());
		 */
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Expenses> listExpenses(int sellerId)throws CustomException {
		log.info("*** listExpenses start ***");
		// sellerId=4;
		List<Expenses> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Expenses.class).add(
					Restrictions.eq("sellerId", sellerId));
			returnlist = criteria.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.listExpensesError,
					new Date(), 3, GlobalConstant.listExpensesErrorCode, e);
			/*
			 * System.out.println(" Exception in getting Expenses list :"+e.
			 * getLocalizedMessage());
			 */
		}
		log.info("*** listExpenses exit ***");
		return returnlist;
	}

	@Override
	public List<ExpenseCategory> listExpenseCategories(int sellerId)throws CustomException {
		log.info("*** listExpenseCategories ***");
		// sellerId=4;
		List<ExpenseCategory> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getExpensecategories() != null
					&& seller.getExpensecategories().size() != 0)
				returnlist = seller.getExpensecategories();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.listExpensesCategoriesError, new Date(), 1,GlobalConstant.listExpensesCategoriesErrorCode, e);
			/*
			 * System.out.println(" Exception in getting Expense cat list :"+e.
			 * getLocalizedMessage()); e.printStackTrace();
			 */
		}
		log.info("*** listExpenseCategory exit ***");
		return returnlist;
	}

	@Override
	public Expenses getExpense(int expenseId)throws CustomException {
		
		log.info("*** getExpense start ***");
		Expenses expenses=new Expenses();
		try{
			expenses=(Expenses) sessionFactory.getCurrentSession().get(Expenses.class, expenseId);
		}catch(Exception e){
			log.error(e);
			throw new CustomException(GlobalConstant.getExpenseError, new Date(), 3,GlobalConstant.getExpenseErrorCode, e);
		}
		log.info("*** getExpense exit ***");
		return expenses;
	}

	@Override
	public ExpenseCategory getExpenseCategory(int expensecatId)throws CustomException {
		
		log.info("*** getExpenseCategory start ***");
		ExpenseCategory expenseCategory=new ExpenseCategory();
		try{
		expenseCategory=(ExpenseCategory) sessionFactory.getCurrentSession().get(ExpenseCategory.class, expensecatId);
		}catch(Exception e){
			log.error(e);
			throw new CustomException(GlobalConstant.getExpenseCategoryError, new Date(), 3,GlobalConstant.getExpenseCategoryErrorCode, e);
		}
		log.info("*** getExpenseCategory exit");
		return expenseCategory;
	}

	public void saveExpense(Expenses expense) {
		
		sessionFactory.getCurrentSession().saveOrUpdate(expense);
	}

	@Override
	public int deleteExpense(Expenses expense, int sellerId)throws CustomException {
		
		log.info("*** deleteExpense start ***");
		/*System.out.println(" In Expense delete cid " + expense.getExpenseId());*/
		// sellerId=4;
		Session session = null;
		int catdelete = 0;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			/*
			 * Query deleteQuery = session.createSQLQuery(
			 * "delete from ExpenseCategory_Expenses where expcategoryId=? and expenses_expenseId=?"
			 * ); deleteQuery.setInteger(0,expense.getExpenseCategory().
			 * getExpcategoryId()); deleteQuery.setInteger(1,
			 * expense.getExpenseId());
			 * 
			 * int updated = deleteQuery.executeUpdate();
			 */
			catdelete = session.createQuery(
					"DELETE FROM Expenses WHERE expenseId = "
							+ expense.getExpenseId()).executeUpdate();

			System.out.println("  Deleteing category catdelete :" + catdelete);

		} catch (Exception e) {
			/*System.out.println(" Inside delleting order"+ e.getLocalizedMessage());
			e.printStackTrace();*/			
			session.getTransaction().rollback();
			log.error(e);
			throw new CustomException(GlobalConstant.deleteExpenseError, new Date(), 3,GlobalConstant.deleteExpenseErrorCode, e);
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		log.info("*** deleteExpense exit ***");
		return catdelete;
	}

	@Override
	public int deleteExpenseCategory(ExpenseCategory expenseCat, int sellerId)throws CustomException {
		
		log.info("*** deleteExpensecategory start ***");
		/*System.out.println(" In expense Category delete cid "+ expenseCat.getExpcategoryId());*/
		// sellerId=4;
		int updated = 0;
		int catdelete = 0;
		int expeCatid = expenseCat.getExpcategoryId();
		Session session = null;
		Seller seller = null;

		try {

			session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("expensecategories", "expCat",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("expCat.expcategoryId", expeCatid))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			if (criteria.list() != null && criteria.list().size() != 0) {
				System.out.println(" Getting seller ");
				seller = (Seller) criteria.list().get(0);
				expenseCat = seller.getExpensecategories().get(0);
				System.out.println(" Getting  expense category ");
			}

			// expenseCat=(ExpenseCategory)session.get(ExpenseCategory.class,
			// expenseCat.getExpcategoryId());
			System.out.println(" Expense sizein category : "
					+ expenseCat.getExpenses().size());
			if (expenseCat.getExpenses() == null
					|| expenseCat.getExpenses().size() == 0) {
				System.out.println(" Deleting expense category");
				seller.getExpensecategories().remove(expenseCat);
				System.out.println(" Deleted ec");
				session.delete(expenseCat);
				session.saveOrUpdate(seller);
				catdelete = session.createQuery(
						"DELETE FROM ExpCat  WHERE expcategoryId = "
								+ expenseCat.getExpcategoryId())
						.executeUpdate();

			}

			/*
			 * Query deleteQuery = session.createSQLQuery(
			 * "delete from Seller_ExpCat where Id="
			 * +sellerId+"and EXPCATID="+expenseCat.getExpcategoryId());
			 * deleteQuery.setInteger(0,sellerId); deleteQuery.setInteger(1,
			 * expenseCat.getExpcategoryId());
			 * 
			 * updated = deleteQuery.executeUpdate();
			 * catdelete=session.createQuery
			 * ("DELETE FROM ExpenseCategory WHERE expcategoryId = "
			 * +expenseCat.getExpcategoryId()).executeUpdate();
			 * 
			 * System.out.println("  Deleteing exp category updated:"+updated+
			 * " catdelete :"+catdelete);
			 */

		} catch (Exception e) {
			
			log.error(e);
			throw new CustomException(GlobalConstant.deleteExpenseCategoryError, new Date(), 3,GlobalConstant.deleteExpenseCategoryErrorCode, e);
			/*System.out.println(" Inside delleting expcat"+e.getLocalizedMessage());
			e.printStackTrace();
			return 0;*/
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		log.info("***deleteExpenseCategory exit***");
		if (catdelete != 0) {
			return updated;
		}
		return 0;
	}

	@Override
	public List<Expenses> getExpenseByDate(Date startDate, Date endDate,
			int sellerId)throws CustomException {
		log.info("*** getExpenseByDate start ***");
		List<Expenses> expenselist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session
					.createCriteria(Expenses.class)
					.add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions
							.between("expenseDate", startDate, endDate))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			expenselist = criteria.list();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.getExpenseByDateError, new Date(), 3,GlobalConstant.getExpenseByDateErrorCode, e);
			/*System.out.println(" Inside find  expenselist  by date  exception :"+ e.getMessage());
			e.printStackTrace();*/
		}
		log.info("*** getExpenseByDate exit ***");
		return expenselist;
	}
	@Override
	public List<Expenses> getExpenseByCategory(String expCatName, int sellerId)throws CustomException {
		
		log.info("*** getExpenseByCategory start ***");
		List returnlist = null;
		List<Expenses> expenselist = null;
		/*System.out.println(" ***Insid get pexpCatName ***" + expCatName);*/

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session
					.createCriteria(Expenses.class)
					.add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions.eq("expenseName", expCatName)
							.ignoreCase())
					.addOrder(org.hibernate.criterion.Order.desc("expenseDate"))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			returnlist = criteria.list();
			if (returnlist == null || returnlist.get(0) == null) {
				System.out.println(" Return no results");
			} else {
				expenselist = returnlist;
			}

			System.out.println(" Return object :#### " + returnlist);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.getExpenseByCategoryError, new Date(), 3,GlobalConstant.getExpenseByCategoryErrorCode, e);
			/*System.out.println(" Product   DAO IMPL :"+e.getLocalizedMessage());
			e.printStackTrace();*/
		}
		log.info("*** geetExpenseByCategory exit***");
		return expenselist;
	}

	@Override
	public List<Expenses> getExpenseByName(String expname,int sellerId) throws CustomException {
		
		log.info("*** getExpenseByCategory start ***");
		List returnlist = null;
		List<Expenses> expenselist = null;
		/*System.out.println(" ***Insid get pexpCatName ***" + expCatName);*/

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session
					.createCriteria(Expenses.class)
					.add(Restrictions.eq("sellerId", sellerId))
					.add(Restrictions.like("expenseName", expname+"%").ignoreCase())
					.addOrder(org.hibernate.criterion.Order.desc("expenseDate"))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			returnlist = criteria.list();
			if (returnlist == null || returnlist.size()==0 ||returnlist.get(0) == null) {
				System.out.println(" Return no results");
			} else {
				expenselist = returnlist;
			}

			System.out.println(" Return object :#### " + returnlist);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.error(e);
			throw new CustomException(GlobalConstant.getExpenseByCategoryError, new Date(), 3,GlobalConstant.getExpenseByCategoryErrorCode, e);
			/*System.out.println(" Product   DAO IMPL :"+e.getLocalizedMessage());
			e.printStackTrace();*/
		}
		log.info("*** geetExpenseByCategory exit***");
		return expenselist;

	}

	@Override
	public ExpenseCategory getExpenseByName(String expname) {
		// TODO Auto-generated method stub
		return null;
	}

}