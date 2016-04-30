package com.o2r.dao;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.Partner;
import com.o2r.model.Seller;

/**
 * @author Deep Mehrotra
 *
 */
@Repository("partnerDao")
public class PartnerDaoImpl implements PartnerDao {

	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(PartnerDaoImpl.class.getName());
	private static final String chargesDeleteQuery = "delete from NRnReturnCharges where config_configId=:configId";


	@Override
	public void addPartner(Partner partner, int sellerId)
			throws CustomException {
		// sellerId=4;
		// sessionFactory.getCurrentSession().saveOrUpdate(partner);
		System.out.println(" Inside PartnerDaoIMpl partner id :"
				+ partner.getPcId());
		long id=partner.getPcId();
		//Partner exisitingObj=null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			if (id != 0) {
				Query gettingTaxId = session
						.createSQLQuery(chargesDeleteQuery)
						.setParameter("configId", partner.getNrnReturnConfig().getConfigId());
				session.saveOrUpdate(partner);
			} else {
				Seller seller = (Seller) session.get(Seller.class, sellerId);
				seller.getPartners().add(partner);
				session.saveOrUpdate(seller);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.addPartnerError,
					new Date(), 1, GlobalConstant.addPartnerErrorCode, e);

			// System.out.println(" pcId DAO IMPL :" + e.getLocalizedMessage());
		}
	}

	@Override
	public List<Partner> listPartner(int sellerId) throws CustomException {

		// sellerId=4;
		List<Partner> returnlist = null;
		System.out.println(" Inside list partner");
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			System.out.println(" getting seller " + seller);
			if (seller.getPartners() != null
					&& seller.getPartners().size() != 0)
				returnlist = seller.getPartners();
			/*
			 * System.out.println(" Getting list of partners size"+seller.
			 * getPartners().size());
			 * System.out.println(" Getting list of partners size"+returnlist);
			 */
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.listPartnerError,
					new Date(), 3, GlobalConstant.listPartnerErrorCode, e);
			// System.out.println(" Exception in getting partner list :"+
			// e.getLocalizedMessage());
		}
		return returnlist;
	}

	@Override
	public Partner getPartner(long partnerid) throws CustomException {
		try {
			
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Partner partner =(Partner) session.get(Partner.class,
					partnerid);
			Hibernate.initialize(partner.getNrnReturnConfig().getCharges());
			session.getTransaction().commit();
			session.close();
			return partner;
			/*return (Partner) sessionFactory.getCurrentSession().get(
					Partner.class, partnerid);*/
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.getPartnerError,
					new Date(), 3, GlobalConstant.getPartnerErrorCode, e);
		}
	}

	@Override
	public Partner getPartner(String partnername, int sellerId)
			throws CustomException {
		// sellerId=4;
		Partner returnpartner = null;
		Seller seller = null;

		System.out.println(" ***Insid get partner ***" + partnername);

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Criteria criteria = session.createCriteria(Seller.class).add(
					Restrictions.eq("id", sellerId));
			criteria.createAlias("partners", "partner",
					CriteriaSpecification.LEFT_JOIN)
					.add(Restrictions.eq("partner.pcName", partnername))
					.setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			if (criteria.list() != null && criteria.list().size() != 0) {
				seller = (Seller) criteria.list().get(0);
				returnpartner = seller.getPartners().get(0);
				if(returnpartner.getNrnReturnConfig()!=null)
				Hibernate.initialize(returnpartner.getNrnReturnConfig().getCharges());
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
e.printStackTrace();
			log.error(e);
			throw new CustomException(GlobalConstant.getPartnerError,
					new Date(), 3, GlobalConstant.getPartnerErrorCode, e);
			// System.out.println(" Partner  DAO IMPL :" +
			// e.getLocalizedMessage());
		}

		return returnpartner;

	}

	@Override
	public void deletePartner(Partner partner, int sellerId)
			throws CustomException {
		System.out.println(" In partner delete pid " + partner.getPcId()
				+ "   pcname " + partner.getPcName());
		// sellerId=4;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query deleteQuery = session
					.createSQLQuery("delete from Seller_Partner where partners_pcId=? and Seller_id=?");

			deleteQuery.setLong(0, partner.getPcId());
			deleteQuery.setInteger(1, sellerId);
			int updated = deleteQuery.executeUpdate();
			int sellerdelete = session.createQuery(
					"DELETE FROM Partner WHERE pcId = " + partner.getPcId())
					.executeUpdate();
			/*
			 * Criteria
			 * criteria=session.createCriteria(Seller.class).add(Restrictions
			 * .eq("id", sellerId)); criteria.createAlias("partners", "partner",
			 * CriteriaSpecification.LEFT_JOIN)
			 * .add(Restrictions.eq("partner.pcId", partner.getPcId()))
			 * .setResultTransformer
			 * (CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			 * seller=(Seller)criteria.list().get(0); Partner
			 * partneris=seller.getPartners().get(0);
			 * seller.getPartners().remove(partneris);
			 * session.delete(partneris);
			 * System.out.println(" List size after deletion :"
			 * +seller.getPartners().size()); session.saveOrUpdate(seller);
			 */
			System.out.println(" update : " + updated + " sellerdelete "
					+ sellerdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.deletePartnerError,
					new Date(), 3, GlobalConstant.deletePartnerErrorCode, e);
			// System.out.println(" Inside delleting partner"+
			// e.getLocalizedMessage());

		}

	}
	
	/*@Override
	public void deleteNrConfigFromPartner(Partner partner, int sellerId)
			throws CustomException {
		System.out.println(" In deleteNrConfigFromPartner pid " + partner.getPcId()
				+ "   pcname " + partner.getPcName());
		// sellerId=4;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query deleteQuery = session
					.createSQLQuery("delete from Seller_Partner where partners_pcId=? and Seller_id=?");

			deleteQuery.setInteger(0, partner.getPcId());
			deleteQuery.setInteger(1, sellerId);
			int updated = deleteQuery.executeUpdate();
			int sellerdelete = session.createQuery(
					"DELETE FROM Partner WHERE pcId = " + partner.getPcId())
					.executeUpdate();
			
			 * Criteria
			 * criteria=session.createCriteria(Seller.class).add(Restrictions
			 * .eq("id", sellerId)); criteria.createAlias("partners", "partner",
			 * CriteriaSpecification.LEFT_JOIN)
			 * .add(Restrictions.eq("partner.pcId", partner.getPcId()))
			 * .setResultTransformer
			 * (CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			 * seller=(Seller)criteria.list().get(0); Partner
			 * partneris=seller.getPartners().get(0);
			 * seller.getPartners().remove(partneris);
			 * session.delete(partneris);
			 * System.out.println(" List size after deletion :"
			 * +seller.getPartners().size()); session.saveOrUpdate(seller);
			 
			System.out.println(" update : " + updated + " sellerdelete "
					+ sellerdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {

			log.error(e);
			throw new CustomException(GlobalConstant.deletePartnerError,
					new Date(), 3, GlobalConstant.deletePartnerErrorCode, e);
			// System.out.println(" Inside delleting partner"+
			// e.getLocalizedMessage());

		}

	}*/

}