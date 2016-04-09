package com.o2r.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.ExpenseDao;
import com.o2r.helper.CustomException;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;

/**
 * @author Deep Mehortra
 *
 */
@Service("expenseService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ExpenseServiceImpl implements ExpenseService {

 @Autowired
 private ExpenseDao expenseDao;


@Override
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public void addExpense(Expenses expense ,int sellerId)throws CustomException {
	expenseDao.addExpense(expense,sellerId);

}

@Override
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public void addExpenseCategory(ExpenseCategory expenseCat ,int sellerId)throws CustomException {
	expenseDao.addExpenseCategory(expenseCat,sellerId);

}

@Override
public List<Expenses> listExpenses(int sellerId)throws CustomException {
	return expenseDao.listExpenses(sellerId);
}

@Override
public List<ExpenseCategory> listExpenseCategories(int sellerId)throws CustomException
{
	return expenseDao.listExpenseCategories(sellerId);
}
@Override
public Expenses getExpense(int expenseId)throws CustomException {
	return expenseDao.getExpense(expenseId);
}

@Override
public ExpenseCategory getExpenseCategory(int expenseCatId)throws CustomException {
	return expenseDao.getExpenseCategory(expenseCatId);
}


@Override
public int deleteExpenseCategory(ExpenseCategory expenseCat,int sellerId)throws CustomException {

	return expenseDao.deleteExpenseCategory(expenseCat,sellerId);
}

@Override
public int deleteExpense(Expenses expenses,int sellerId)throws CustomException {

	return expenseDao.deleteExpense(expenses,sellerId);
}

@Override
public double getMonthlyAmount(int catId, int sellerId)throws CustomException
{
	return expenseDao.getMonthlyAmount(catId, sellerId);
}

@Override
public List<Expenses> getExpenseByCategory(String expCatName, int sellerId)throws CustomException {
	return expenseDao.getExpenseByCategory(expCatName, sellerId);
}

@Override
public List<Expenses> getExpenseByDate(Date startDate, Date endDate,
		int sellerId)throws CustomException {
	return expenseDao.getExpenseByDate(startDate, endDate, sellerId);
}

@Override
public List<Expenses> getExpenseByName(String expname, int sellerId)
		throws CustomException {
	return expenseDao.getExpenseByName(expname, sellerId);
}
}