package com.o2r.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Category;
import com.o2r.model.PartnerCategoryMap;
import com.o2r.model.Product;
import com.o2r.model.ProductConfig;
import com.o2r.model.Seller;
import com.o2r.model.UploadReport;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("categoryDao")
public class CategoryDaoImpl implements CategoryDao {

	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(CategoryDaoImpl.class.getName());

	@Override
	public void addCategory(Category category, int sellerId)
			throws CustomException {

		log.info("***addCategory Start****");
		Seller seller = null;
		Category parentcategory = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));

			if (category.isSubCategory()) {
				List<Category> listparents = listParentCategories(sellerId);
				for (Category cat : listparents) {
					if (cat.getCatName().equalsIgnoreCase(
							category.getParentCatName())) {
						parentcategory = cat;
					}
				}
				if (parentcategory.getSubCategory() != null)
					parentcategory.getSubCategory().add(category);
				category.setParent(parentcategory);
				category.setCreatedOn(new Date());
			}
			category.setCreatedOn(new Date());
			seller = (Seller) criteria.list().get(0);
			if (seller.getCategories() != null)
				seller.getCategories().add(category);
			session.saveOrUpdate(seller);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addCategoryErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addCategoryError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addCategoryErrorCode, e);

		}
		log.info("***addCategory Exit****");

	}

	@Override
	public void addPartnerCatRef(Category category, int sellerId)
			throws CustomException {

		log.info("***addPartnerCatRef Start****");
		// Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			session.saveOrUpdate(category);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addCategoryErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addCategoryError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addCategoryErrorCode, e);

		}
		log.info("***addPartnerCatRef Exit****");

	}

	@Override
	public List<Category> listCategories(int sellerId) throws CustomException {

		log.info("***listCategories Start****");
		List<Category> returnlist = null;
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("categories", "category",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("category.isSubCategory", true))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list() != null && criteria.list().size() != 0) {
				seller = (Seller) criteria.list().get(0);
				System.out.println(" Size of parent category list :"
						+ seller.getCategories().size());
				if (seller.getCategories() != null
						&& seller.getCategories().size() != 0)
					returnlist = seller.getCategories();
				session.getTransaction().commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.listCategoryErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listCategoryError,
					new Date(), 3,
					(sellerId + "-" + GlobalConstant.listCategoryErrorCode), e);
		}
		log.info("***listCategories Exit****");
		return returnlist;
	}

	@Override
	public List<Category> listParentCategories(int sellerId)
			throws CustomException {

		log.info("***listParentCategories Start****");
		List<Category> returnlist = null;
		Seller seller = null;
		List tempList = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("categories", "category",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("category.isSubCategory", false))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			tempList = criteria.list();
			if (tempList != null && tempList.size() != 0) {
				seller = (Seller) tempList.get(0);
				if (seller.getCategories() != null
						&& seller.getCategories().size() != 0)
					returnlist = seller.getCategories();
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.listInventoryGroupErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(
					GlobalConstant.listInventoryGroupError,
					new Date(),
					3,
					(sellerId + "-" + GlobalConstant.listInventoryGroupErrorCode),
					e);
		}
		log.info("***listParentCategories End****");
		return returnlist;
	}

	@Override
	public List<Long> getSKuCount(String catname, int catId, int sellerId)
			throws CustomException {
		List<Long> returnlist = null;
		Session session = null;
		log.info("***getSKuCount Start****");
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			returnlist = new ArrayList<Long>();
			/*
			 * Code for caluclating sku count
			 */
			Criteria criteriaforSkuCount = session.createCriteria(Seller.class);
			criteriaforSkuCount
					.createAlias("categories", "category",
							CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("id", sellerId))
					.add(Restrictions.eq("category.isSubCategory", true))
					.add(Restrictions.eq("category.parentCatName", catname));
			criteriaforSkuCount
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			ProjectionList projList = Projections.projectionList();
			projList.add(Projections.sum("category.skuCount"));
			projList.add(Projections.sum("category.productCount"));
			projList.add(Projections.sum("category.openingStock"));
			criteriaforSkuCount.setProjection(projList);
			List<Object[]> catSKUCount = criteriaforSkuCount.list();
			Iterator catIterator = catSKUCount.iterator();
			if (catSKUCount != null) {
				while (catIterator.hasNext()) {
					Object[] recordsRow = (Object[]) catIterator.next();
					if (recordsRow != null && recordsRow.length != 0
							&& recordsRow[0] != null && recordsRow[1] != null) {
						returnlist
								.add(Long.parseLong(recordsRow[0].toString()));
						returnlist
								.add(Long.parseLong(recordsRow[1].toString()));
						returnlist
								.add(Long.parseLong(recordsRow[2].toString()));
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getSKUCountErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getSKUCountError,
					new Date(), 3,
					(sellerId + "-" + GlobalConstant.getSKUCountErrorCode), e);
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		log.info("***getSKuCount End****");
		return returnlist;
	}

	@Override
	public Category getCategory(int categoryId) {
		log.info("***getCategory Start****");
		return (Category) sessionFactory.getCurrentSession().get(
				Category.class, categoryId);
	}

	@Override
	public Category getCategory(String catname, int sellerId)
			throws CustomException {
		log.info("***getCategory Start****");
		Category returnObject = null;
		Seller seller = null;
		List tempList = null;
		log.debug("***Insid get category from catname ***" + catname);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("categories", "category",
					CriteriaSpecification.LEFT_JOIN).add(
					Restrictions.eq("category.catName", catname).ignoreCase());
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			tempList = criteria.list();
			if (tempList != null && tempList.size() != 0) {
				seller = (Seller) tempList.get(0);
				returnObject = seller.getCategories().get(0);
			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getCategorywithNameErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(
					GlobalConstant.getCategorywithNameError,
					new Date(),
					3,
					(sellerId + "-" + GlobalConstant.getCategorywithNameErrorCode),
					e);
		}
		log.info("***getCategory Exit****");
		return returnObject;
	}

	@Override
	public Category getSubCategory(String catname, int sellerId)
			throws CustomException {
		log.info("***getCategory Start****");
		Category returnObject = null;
		Seller seller = null;
		List tempList = null;
		log.debug("***Insid get sub category from catname ***" + catname);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			Criterion res1 = Restrictions.or(
					Restrictions.eq("category.catName", catname).ignoreCase(),
					Restrictions.like(
							"category.partnerCatRef",
							"%" + GlobalConstant.orderUniqueSymbol + catname
									+ GlobalConstant.orderUniqueSymbol + "%")
							.ignoreCase());
			criteria.createAlias("categories", "category",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("category.isSubCategory", true))
					/*
					 * .add(Restrictions.eq("category.catName", catname)
					 * .ignoreCase());
					 */
					.add(res1);

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			tempList = criteria.list();
			if (tempList != null && tempList.size() != 0) {
				seller = (Seller) tempList.get(0);
				returnObject = seller.getCategories().get(0);
			}

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.getCategorywithNameErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(
					GlobalConstant.getCategorywithNameError,
					new Date(),
					3,
					(sellerId + "-" + GlobalConstant.getCategorywithNameErrorCode),
					e);
		}
		log.info("***getCategory Exit****");
		return returnObject;

	}

	@Override
	public int deleteCategory(Category category, int sellerId)
			throws CustomException {

		log.info("***deleteCategory Start****");
		log.debug("In Category delete cid " + category.getCategoryId());
		int catId = category.getCategoryId();
		int catdelete = 0;
		int updated = 0;
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Object obj = session.get(Category.class, catId);
			if (obj != null) {
				Category cat = (Category) obj;
				if (cat.isSubCategory() || cat.getSubCategory().size() == 0) {
					if (cat.isSubCategory()) {
						cat.getProducts().clear();
						cat.getSubCategory().clear();
						session.saveOrUpdate(cat);
					}
					Query deleteQuery = session
							.createSQLQuery("delete from Seller_Category where seller_Id=? and CATEGORIES_CATEGORYID=?");
					deleteQuery.setInteger(0, sellerId);
					deleteQuery.setInteger(1, category.getCategoryId());

					updated = deleteQuery.executeUpdate();
					catdelete = session.createQuery(
							"DELETE FROM Category WHERE categoryId = "
									+ category.getCategoryId()).executeUpdate();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.deleteCategoryErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.deleteCategoryError,
					new Date(), 2,
					(sellerId + "-" + GlobalConstant.deleteCategoryErrorCode),
					e);
		} finally {
			session.getTransaction().commit();
			session.close();
		}
		log.info("***deleteCategory Exit****");
		if (updated != 0 && catdelete != 0)
			return 1;
		else
			return 0;

	}

	@Override
	public Map<String, String> getCategoryParentMap(int sellerId)
			throws CustomException {

		log.info("*** getSKUCategoryMap Starts : ProductDaoImpl ****");
		Map<String, String> returnMap = new HashMap<String, String>();
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("categories", "category",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("category.isSubCategory", true))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			List templist = criteria.list();
			if (templist != null && templist.size() != 0) {
				seller = (Seller) templist.get(0);

				if (seller.getCategories() != null
						&& seller.getCategories().size() != 0) {
					for (Category cat : seller.getCategories()) {
						returnMap.put(cat.getCatName(), cat.getParentCatName());
					}
				}
				session.getTransaction().commit();
				session.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(
					GlobalConstant.getProductwithCreatedDateError, new Date(),
					3, GlobalConstant.getProductwithCreatedDateErrorCode, e);
		}
		log.info("*** getSKUCategoryMap Ends : ProductDaoImpl ****");
		return returnMap;
	}

	@Override
	public List<String> listPartnerCategories() throws CustomException {

		log.info("***listPartnerCategories Start****");
		List<String> returnlist = new ArrayList<String>();
		Seller seller = null;
		try {

			List<PartnerCategoryMap> partnerCategoryMapList = new ArrayList<PartnerCategoryMap>();
			partnerCategoryMapList = (List<PartnerCategoryMap>) sessionFactory
					.getCurrentSession()
					.createCriteria(PartnerCategoryMap.class).list();

			if (partnerCategoryMapList != null
					&& partnerCategoryMapList.size() != 0) {
				for (PartnerCategoryMap cat : partnerCategoryMapList) {
					returnlist.add(cat.getPartnerName() + "_"
							+ cat.getPartnerCategoryRef());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (GlobalConstant.listCategoryErrorCode));
			log.error("Failed! by sellerId : " + e);
			throw new CustomException(GlobalConstant.listCategoryError,
					new Date(), 3, (GlobalConstant.listCategoryErrorCode), e);
		}
		log.info("***listCategories Exit****");
		return returnlist;
	}
	
	@Override
	public List<String> listPartnerCategories(String partnerName, int sellerId) throws CustomException {

		log.info("***listPartnerCategories by Name Start****");
		List<String> returnlist = new ArrayList<String>();
		Seller seller = null;
		try {

			List<PartnerCategoryMap> partnerCategoryMapList = new ArrayList<PartnerCategoryMap>();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session
					.createCriteria(PartnerCategoryMap.class);

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			partnerCategoryMapList = criteria.list();
			
			/*partnerCategoryMapList = (List<PartnerCategoryMap>) sessionFactory
					.getCurrentSession()
					.createCriteria(PartnerCategoryMap.class).list();*/

			if (partnerCategoryMapList != null
					&& partnerCategoryMapList.size() != 0) {
				for (PartnerCategoryMap cat : partnerCategoryMapList) {
					if (partnerName!= null && partnerName.equals(cat.getPartnerName())) {
						returnlist.add(cat.getPartnerCategoryRef());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.equals("**Error Code : "
					+ (GlobalConstant.listCategoryErrorCode));
			log.error("Failed! by sellerId : " + e);
			throw new CustomException(GlobalConstant.listCategoryError,
					new Date(), 3, (GlobalConstant.listCategoryErrorCode), e);
		}
		log.info("***listCategories Exit****");
		return returnlist;
	}

	@Override
	public PartnerCategoryMap getPartnerCategoryMap(String partnerName,
			String catName, int sellerId) throws CustomException {

		log.info("*** getPartnerCategoryMap Starts : CategoryDaoImpl ****");
		PartnerCategoryMap returnObject = null;
		List returnlist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session
					.createCriteria(PartnerCategoryMap.class);
			criteria.add(Restrictions.eq("partnerName", partnerName)
					.ignoreCase());

			if (catName != null)
				criteria.add(Restrictions.eq("partnerCategoryRef", catName)
						.ignoreCase());
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			returnlist = criteria.list();

			if (returnlist != null && returnlist.size() != 0) {

				returnObject = (PartnerCategoryMap) returnlist.get(0);
				Hibernate.initialize(returnObject.getProduct());
			} else {
				log.debug("PartnerCategoryMap " + catName + " not found");
			}
			log.debug(" Return object :#### " + returnObject);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getProductError,
					new Date(), 3, GlobalConstant.getProductErrorCode, e);

		}
		log.info("*** getPartnerCategoryMap Ends : CategoryDaoImpl ****");
		return returnObject;

	}

	@Override
	public PartnerCategoryMap getPartnerCategoryMap(long id)
			throws CustomException {
		log.info("*** getPartnerCategoryMap start : CategoryDaoImpl ****");

		List<PartnerCategoryMap> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session
					.createCriteria(PartnerCategoryMap.class);
			criteria.add(Restrictions.eq("id", id));
			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			if (criteria.list().size() != 0) {
				returnlist = criteria.list();
			}
			session.getTransaction().commit();
			session.close();
			log.info("*** getPartnerCategoryMap ends : CategoryDaoImpl ****");

			return returnlist.get(0);
		} catch (Exception e) {
			log.error("Failed! : " + e);
			throw new CustomException(GlobalConstant.listCategoryError,
					new Date(), 3, GlobalConstant.listCategoryErrorCode, e);
		}
	}

	@Override
	public void addPartnerCatCommission(PartnerCategoryMap partnerCatMap,
			int sellerId) throws CustomException {

		log.info("*** addPartnerCatCommission Starts : CategoryDaoImpl ****");

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			session.saveOrUpdate(partnerCatMap);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addCategoryError,
					new Date(), 1, GlobalConstant.addCategoryErrorCode, e);
		}
		log.info("*** addPartnerCatCommission Ends : CategoryDaoImpl ****");
	}

	@Override
	public List<PartnerCategoryMap> listPartnerCategoryMap(int sellerId,
			int pageNo) throws CustomException {

		log.info("*** listPartnerCategoryMap by SellerId Starts : CategoryDaoImpl ****");
		List<PartnerCategoryMap> returnlist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Seller seller = (Seller) session.get(Seller.class, sellerId);
			if (seller.getPartnerCategoryList() != null
					&& !seller.getPartnerCategoryList().isEmpty()) {

				returnlist = seller.getPartnerCategoryList();
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getProductError,
					new Date(), 3, GlobalConstant.getProductErrorCode, e);

		}
		log.info("*** listPartnerCategoryMap by SellerId Ends : CategoryDaoImpl ****");
		return returnlist;
	}
	
	@Override
	public List<PartnerCategoryMap> searchPartnerCategory(String searchCriteria, String value,
			int sellerId) throws CustomException {

		log.info("*** searchPartnerCategory by SellerId Starts : CategoryDaoImpl ****");
		List<PartnerCategoryMap> returnlist = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			Criteria criteria = session
					.createCriteria(PartnerCategoryMap.class);
			criteria.add(Restrictions.like(searchCriteria, "%" + value + "%"));

			criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			criteria.createAlias("seller", "seller",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("seller.id", sellerId))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			returnlist = criteria.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getProductError,
					new Date(), 3, GlobalConstant.getProductErrorCode, e);

		}
		log.info("*** searchPartnerCategory by SellerId Ends : CategoryDaoImpl ****");
		return returnlist;
	}

	@Override
	public void addParterCategory(PartnerCategoryMap partnerCategoryMap,
			int sellerId) throws CustomException {
		log.info("***addParterCategory Start for : " + sellerId + "****");
		Seller seller = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			if (partnerCategoryMap.getId() != 0) {
				PartnerCategoryMap newPartnerCat = getPartnerCategoryMap(partnerCategoryMap
						.getId());
				newPartnerCat.setCommission(partnerCategoryMap.getCommission());
				session.merge(newPartnerCat);
			} else {
				Criteria criteria = session.createCriteria(Seller.class).add(
						Restrictions.eq("id", sellerId));
				seller = (Seller) criteria.list().get(0);
				partnerCategoryMap.setSeller(seller);
				if (seller.getPartnerCategoryList() != null) {
					seller.getPartnerCategoryList().add(partnerCategoryMap);
				}
				session.saveOrUpdate(seller);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			log.equals("**Error Code : "
					+ (sellerId + "-" + GlobalConstant.addCategoryErrorCode));
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addCategoryError,
					new Date(), 1, sellerId + "-"
							+ GlobalConstant.addCategoryErrorCode, e);
		}
		log.info("***addParterCategory Exit****");
	}
}