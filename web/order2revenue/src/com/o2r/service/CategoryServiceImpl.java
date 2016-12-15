package com.o2r.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.o2r.dao.CategoryDao;
import com.o2r.helper.CustomException;
import com.o2r.model.Category;
import com.o2r.model.PartnerCategoryMap;

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
	public void addCategory(Category category, int sellerId)
			throws CustomException {
		categoryDao.addCategory(category, sellerId);

	}

	@Override
	public List<Category> listCategories(int sellerId) throws CustomException {
		return categoryDao.listCategories(sellerId);
	}

	@Override
	public List<Category> listParentCategories(int sellerId)
			throws CustomException {
		return categoryDao.listParentCategories(sellerId);
	}

	@Override
	public Category getCategory(int CategoryId) {
		return categoryDao.getCategory(CategoryId);
	}

	@Override
	public int deleteCategory(Category category, int sellerId)
			throws CustomException {

		return categoryDao.deleteCategory(category, sellerId);
	}

	@Override
	public List<Long> getSKuCount(String catname, int catId, int sellerId)
			throws CustomException {
		return categoryDao.getSKuCount(catname, catId, sellerId);
	}

	@Override
	public Category getCategory(String catname, int sellerId)
			throws CustomException {
		return categoryDao.getCategory(catname, sellerId);
	}

	@Override
	public Category getSubCategory(String catname, int sellerId)
			throws CustomException {
		return categoryDao.getSubCategory(catname, sellerId);
	}

	@Override
	public Map<String, String> getCategoryParentMap(int sellerId)
			throws CustomException {
		return categoryDao.getCategoryParentMap(sellerId);
	}

	@Override
	public void addPartnerCatRef(Category prodcat, int sellerId)
			throws CustomException {
		categoryDao.addPartnerCatRef(prodcat, sellerId);

	}

	@Override
	public List<String> listPartnerCategories() throws CustomException {
		return categoryDao.listPartnerCategories();
	}

	@Override
	public List<String> listPartnerCategories(String partnerName, int sellerId)
			throws CustomException {
		return categoryDao.listPartnerCategories(partnerName, sellerId);
	}

	@Override
	public PartnerCategoryMap getPartnerCategoryMap(String partnerName,
			String catName, int sellerId) throws CustomException {
		return categoryDao
				.getPartnerCategoryMap(partnerName, catName, sellerId);
	}

	@Override
	public void addPartnerCatCommission(PartnerCategoryMap partnerCatMap,
			int sellerId) throws CustomException {
		categoryDao.addPartnerCatCommission(partnerCatMap, sellerId);
	}

	@Override
	public List<PartnerCategoryMap> listPartnerCategoryMap(int sellerId,
			int pageNo) throws CustomException {
		return categoryDao.listPartnerCategoryMap(sellerId, pageNo);
	}

	@Override
	public void addParterCategory(PartnerCategoryMap partnerCategoryMap,
			int sellerId) throws CustomException {
		categoryDao.addParterCategory(partnerCategoryMap, sellerId);
	}

	@Override
	public List<PartnerCategoryMap> searchPartnerCategory(
			String searchCriteria, String value, int sellerId)
			throws CustomException {
		return categoryDao.searchPartnerCategory(searchCriteria, value,
				sellerId);
	}
}