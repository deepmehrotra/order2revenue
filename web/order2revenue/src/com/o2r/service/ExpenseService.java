package com.o2r.service;

import java.util.Date;
import java.util.List;

import com.o2r.model.ExpenseCategory;
import com.o2r.model.Expenses;

/**
 * @author Deep Mehrotra
 *
 */
public interface ExpenseService {

 public void addExpense(Expenses expense , int sellerId);

 public void addExpenseCategory(ExpenseCategory category , int sellerId);
 
 public  List<Expenses>  getExpenseByName(String expenseCat ,int sellerId);
 
 public void addExpenseByName(ExpenseCategory expenseCat ,int sellerId);

 public List<Expenses> listExpenses(int sellerId);

 public List<ExpenseCategory> listExpenseCategories(int sellerId);

 public Expenses getExpense(int expenseId);

 public ExpenseCategory getExpenseCategory(int expensecatId);

 public int deleteExpense(Expenses expense,int sellerId);

 public int deleteExpenseCategory(ExpenseCategory expenseCat,int sellerId);

double getMonthlyAmount(int catId, int sellerId);

public List<Expenses> getExpenseByCategory(String expCatName ,int sellerId);

public List<Expenses> getExpenseByDate(Date startDate,Date endDate ,int sellerId);
}