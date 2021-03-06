package com.o2r.service;

import java.util.List;

import com.o2r.helper.CustomException;
import com.o2r.model.Category;

/**
 * @author Deep Mehrotra
 *
 */
public interface CategoryService {

 public void addCategory(Category category , int sellerId) throws CustomException;

 public List<Category> listCategories(int sellerId)  throws CustomException;

 public List<Category> listParentCategories(int sellerId)  throws CustomException;

 public Category getCategory(int catId);

 public Category getCategory(String catname ,int sellerId)  throws CustomException;


 public int deleteCategory(Category category,int sellerId)  throws CustomException;

public List<Long> getSKuCount(String catname, int catId, int sellerId)  throws CustomException;
}