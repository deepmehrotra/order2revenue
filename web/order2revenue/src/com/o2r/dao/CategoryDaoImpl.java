package com.o2r.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
import com.o2r.model.Category;
import com.o2r.model.Seller;


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
public void addCategory(Category category,int sellerId) throws CustomException {

	log.info("***addCategory Start****");
	Seller seller=null;
	Category parentcategory=null;
	   try
	   {
		   Session session=sessionFactory.openSession();
		   session.beginTransaction();
		   Criteria criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));

		   if(category.isSubCategory())
		   {
			   List<Category> listparents=listParentCategories(sellerId);
			   for(Category cat:listparents)
			   {
				  if(cat.getCatName().equalsIgnoreCase(category.getParentCatName()))
				   {
					  parentcategory=cat;
				   }
			   }
			   if(parentcategory.getSubCategory()!=null)
			   parentcategory.getSubCategory().add(category);
			 category.setParent(parentcategory);
			 category.setCreatedOn(new Date());
			   }
		   category.setCreatedOn(new Date());
		   seller=(Seller)criteria.list().get(0);
		   if(seller.getCategories()!=null)
		   seller.getCategories().add(category);
		   session.saveOrUpdate(seller);

	   session.getTransaction().commit();
	   session.close();
	   }
	   catch (Exception e) {
		   log.equals("**Error Code : "+(sellerId+"-"+GlobalConstant.addCategoryErrorCode));
		   log.error(e);
	throw new CustomException(GlobalConstant.addCategoryError, new Date(),1,sellerId+"-"+GlobalConstant.addCategoryErrorCode, e);

	}
	   log.info("***addCategory Exit****");

}

@Override
public List<Category> listCategories(int sellerId) throws CustomException {
	//sellerId=4;
	log.info("***listCategories Start****");
	List<Category> returnlist=null;
	Seller seller=null;
	try
	{
	Session session=sessionFactory.openSession();
	   session.beginTransaction();
	   Criteria criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
	   criteria.createAlias("categories", "category",CriteriaSpecification.LEFT_JOIN)
	   .add(Restrictions.eq("category.isSubCategory",true))
	   .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	   if(criteria.list()!=null&&criteria.list().size()!=0)
	   {
	   seller=(Seller)criteria.list().get(0);
	   System.out.println(" Size of parent category list :"+seller.getCategories().size());
	   if(seller.getCategories()!=null&&seller.getCategories().size()!=0)
		   returnlist=seller.getCategories();
	   session.getTransaction().commit();
	   session.close();
	}
	}
	catch(Exception e)
	{
		   log.equals("**Error Code : "+(sellerId+"-"+GlobalConstant.listCategoryErrorCode));
		  log.error(e);
			throw new CustomException(GlobalConstant.listCategoryError, new Date(),3,(sellerId+"-"+GlobalConstant.listCategoryErrorCode), e);
	}
	log.info("***listCategories Exit****");
	return returnlist;
}

@Override
public List<Category> listParentCategories(int sellerId) throws CustomException {

	log.info("***listParentCategories Start****");
	List<Category> returnlist=null;
	Seller seller=null;
	List tempList=null;
	try
	{
	Session session=sessionFactory.openSession();
	   session.beginTransaction();
	   Criteria criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
	   criteria.createAlias("categories", "category",CriteriaSpecification.LEFT_JOIN)
	   .add(Restrictions.eq("category.isSubCategory",false))
	   .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	   tempList=criteria.list();
	   if(tempList!=null&&tempList.size()!=0)
	   {
	   seller=(Seller)tempList.get(0);
	   if(seller.getCategories()!=null&&seller.getCategories().size()!=0)
		   returnlist=seller.getCategories();
	   }
	   session.getTransaction().commit();
	   session.close();
	}
	catch(Exception e)
	{
		  log.equals("**Error Code : "+(sellerId+"-"+GlobalConstant.listInventoryGroupErrorCode));
		 log.error(e);
		throw new CustomException(GlobalConstant.listInventoryGroupError, new Date(),3, (sellerId+"-"+GlobalConstant.listInventoryGroupErrorCode),e);
	}
	log.info("***listParentCategories End****");
	return returnlist;
}

@Override
public List<Long> getSKuCount(String catname ,int catId ,int sellerId) throws CustomException {
	List<Long> returnlist=null;
	Session session=null;
	log.info("***getSKuCount Start****");
	try
	{
		session=sessionFactory.openSession();
	   session.beginTransaction();
	   returnlist=new ArrayList<Long>();
		 /*
		  * Code for caluclating sku count
		  */
		 Criteria criteriaforSkuCount=session.createCriteria(Seller.class);
		 criteriaforSkuCount.createAlias("categories", "category", CriteriaSpecification.LEFT_JOIN)
		 .add(Restrictions.eq("id", sellerId))
		  .add(Restrictions.eq("category.isSubCategory",true))
		 .add(Restrictions.eq("category.parentCatName", catname));
		 criteriaforSkuCount.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		 ProjectionList projList = Projections.projectionList();
		 projList.add(Projections.sum("category.skuCount"));
		 projList.add(Projections.sum("category.productCount"));
		 projList.add(Projections.sum("category.openingStock"));
		 criteriaforSkuCount.setProjection(projList);
		List<Object[]> catSKUCount = criteriaforSkuCount.list();
		 Iterator catIterator = catSKUCount.iterator();
		 if(catSKUCount != null){
			 while(catIterator.hasNext()){
				 Object[] recordsRow = (Object[])catIterator.next();
				 System.out.println(" Length of record row : "+recordsRow.length);
				 if(recordsRow!=null&&recordsRow.length!=0&&recordsRow[0]!=null&&recordsRow[1]!=null)
				 {
				 System.out.println(" Getting category sku count and product count : "+recordsRow[0] +" : "+recordsRow[1]);
				 returnlist.add(Long.parseLong(recordsRow[0].toString()));
				 returnlist.add(Long.parseLong(recordsRow[1].toString()));
				 returnlist.add(Long.parseLong(recordsRow[2].toString()));
				 }
			 }
		 }


	}
	catch(Exception e)
	{
		  log.equals("**Error Code : "+(sellerId+"-"+GlobalConstant.getSKUCountErrorCode));
		 log.error(e);
			throw new CustomException(GlobalConstant.getSKUCountError, new Date(),3,(sellerId+"-"+GlobalConstant.getSKUCountErrorCode), e);
	}
	finally
	{
		session.getTransaction().commit();
		   session.close();
	}
	log.info("***getSKuCount End****");
	return returnlist;
}

@Override
public Category getCategory(int categoryId) {
	log.info("***getCategory Start****");

	return (Category) sessionFactory.getCurrentSession().get(Category.class, categoryId);
}


@Override
public Category getCategory(String catname ,int sellerId) throws CustomException
{
	log.info("***getCategory Start****");
	Category returnObject=null;
	 Seller seller=null;
	 List tempList=null;
	 log.debug("***Insid get category from catname ***"+catname);
	try
	 {
  Session session=sessionFactory.openSession();
  session.beginTransaction();
  Criteria criteria=session.createCriteria(Seller.class).add(Restrictions.eq("id", sellerId));
  criteria.createAlias("categories", "category", CriteriaSpecification.LEFT_JOIN)
 .add(Restrictions.eq("category.catName", catname).ignoreCase());
  criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
  tempList=criteria.list();
		  if(tempList!=null&&tempList.size()!=0)
		  {
		  seller=(Seller)tempList.get(0);
		  returnObject=seller.getCategories().get(0);
		  }

		  session.getTransaction().commit();
		  session.close();
	 }
	 catch (Exception e) {
		  log.equals("**Error Code : "+(sellerId+"-"+GlobalConstant.getCategorywithNameErrorCode));
		 log.error(e);
			throw new CustomException(GlobalConstant.getCategorywithNameError, new Date(),3, (sellerId+"-"+GlobalConstant.getCategorywithNameErrorCode),e);
	}
	log.info("***getCategory Exit****");
	return returnObject;

}


@Override
public int deleteCategory(Category category,int sellerId) throws CustomException {

	 log.info("***deleteCategory Start****");
	 log.debug("In Category delete cid "+category.getCategoryId());
	 int catId=category.getCategoryId();
	 int catdelete=0;
	 int updated=0;
	 Session session=null;
	 try
	 {
		 session=sessionFactory.openSession();
		  session.beginTransaction();
		  Object obj=session.get(Category.class, catId);
		  if(obj!=null)
		  {
		  Category cat=(Category)obj;
		  if(cat.isSubCategory()||cat.getSubCategory().size()==0)
		  {
			  if(cat.isSubCategory())
			  {
				  cat.getProducts().clear();
				  cat.getSubCategory().clear();
				  session.saveOrUpdate(cat);
				  }
		  Query deleteQuery = session.createSQLQuery(
				    "delete from Seller_Category where seller_Id=? and CATEGORIES_CATEGORYID=?");
		  		deleteQuery.setInteger(0,sellerId);
				deleteQuery.setInteger(1, category.getCategoryId());

				updated = deleteQuery.executeUpdate();
				catdelete=session.createQuery("DELETE FROM Category WHERE categoryId = "+category.getCategoryId()).executeUpdate();
	  }

	 }
	 }
	 catch(Exception e)
	 {
		 log.equals("**Error Code : "+(sellerId+"-"+GlobalConstant.deleteCategoryErrorCode));
		 log.error(e);
			throw new CustomException(GlobalConstant.deleteCategoryError, new Date(),2,(sellerId+"-"+GlobalConstant.deleteCategoryErrorCode), e);
	 }
	 finally
	 {
		 session.getTransaction().commit();
		  session.close();
	 }
	 log.info("***deleteCategory Start****");
	 if(updated!=0&&catdelete!=0)
	return 1;
	 else
		 return 0;

}


}