package com.o2r.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.CategoryDao;
import com.o2r.helper.CustomException;
import com.o2r.model.Category;

/**
 * @author Deep Mehortra
 *
 */
@Service("categoryService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class CategoryServiceImpl implements CategoryService {

 @Autowired
 private CategoryDao categoryDao;


@Override
@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
public void addCategory(Category category ,int sellerId) throws CustomException {
		categoryDao.addCategory(category,sellerId);

}

@Override
public List<Category> listCategories(int sellerId) throws CustomException {
	return categoryDao.listCategories(sellerId);
}
@Override
public List<Category> listParentCategories(int sellerId) throws CustomException
{
	return categoryDao.listParentCategories(sellerId);
}
@Override
public Category getCategory(int CategoryId) {
	return categoryDao.getCategory(CategoryId);
}

@Override
public int deleteCategory(Category category,int sellerId) throws CustomException {

	return categoryDao.deleteCategory(category,sellerId);
}

@Override
public List<Long> getSKuCount(String catname,int catId, int sellerId) throws CustomException
{
	return categoryDao.getSKuCount(catname,catId , sellerId);
}

@Override
public Category getCategory(String catname ,int sellerId) throws CustomException
{
	return categoryDao.getCategory(catname ,sellerId);
}
}