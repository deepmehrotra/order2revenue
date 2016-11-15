package com.o2r.dao;

import java.util.List;
import java.util.Map;

import com.o2r.helper.CustomException;
import com.o2r.model.Category;
import com.o2r.model.PartnerCategoryMap;

/**
 * @author Deep Mehrotra
 *
 */
public interface CategoryDao {

	public void addCategory(Category category, int sellerId)
			throws CustomException;

	public List<Category> listCategories(int sellerId) throws CustomException;

	public List<Category> listParentCategories(int sellerId)
			throws CustomException;

	public Category getCategory(int categoryId);

	public int deleteCategory(Category category, int sellerId)
			throws CustomException;

	public List<Long> getSKuCount(String catname, int catId, int sellerId)
			throws CustomException;

	public Category getCategory(String catname, int sellerId)
			throws CustomException;

	public Category getSubCategory(String catname, int sellerId)
			throws CustomException;

	public Map<String, String> getCategoryParentMap(int sellerId)
			throws CustomException;

	public void addPartnerCatRef(Category category, int sellerId)
			throws CustomException;

	public List<String> listPartnerCategories() throws CustomException;

	public PartnerCategoryMap getPartnerCategoryMap(String partnerName,
			String catName, int sellerId) throws CustomException;

	public void addPartnerCatCommission(PartnerCategoryMap partnerCatMap,
			int sellerId) throws CustomException;

	public List<PartnerCategoryMap> listPartnerCategoryMap(int sellerId,
			int pageNo) throws CustomException;

	public void addParterCategory(PartnerCategoryMap partnerCategoryMap,
			int sellerId) throws CustomException;

	public PartnerCategoryMap getPartnerCategoryMap(long id)
			throws CustomException;

	public List<PartnerCategoryMap> searchPartnerCategory(
			String searchCriteria, String value, int sellerId)
			throws CustomException;
}