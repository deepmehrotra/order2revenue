package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.bean.ExpensesDetails;
import com.o2r.helper.CustomException;
import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;

/**
 * @author Deep Mehrotra
 *
 */
public interface ExpenseService {

 public void addExpense(Expenses expense , int sellerId)throws CustomException;

 public void addExpenseCategory(ExpenseCategory category , int sellerId)throws CustomException;

 public List<Expenses> listExpenses(int sellerId)throws CustomException;

 public List<ExpenseCategory> listExpenseCategories(int sellerId)throws CustomException;

 public Expenses getExpense(int expenseId)throws CustomException;

 public ExpenseCategory getExpenseCategory(int expensecatId)throws CustomException;

 public int deleteExpense(Expenses expense,int sellerId)throws CustomException;

 public int deleteExpenseCategory(ExpenseCategory expenseCat,int sellerId)throws CustomException;

public double getMonthlyAmount(int catId, int sellerId)throws CustomException;

public List<Expenses> getExpenseByCategory(String expCatName ,int sellerId)throws CustomException;

public List<Expenses> getExpenseByDate(Date startDate,Date endDate ,int sellerId)throws CustomException;

public List<Expenses> getExpenseByName(String expname, int sellerId) throws CustomException;

public List<ExpensesDetails> getExpenseByYear(int sellerId, Date startDate, Date endDate) throws CustomException;

public List<ExpensesDetails> getExpenseByCatYear(int sellerId, Date startDate, Date endDate) throws CustomException;

}