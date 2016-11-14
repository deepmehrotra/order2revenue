package com.o2r.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.helper.CustomException;
import com.o2r.helper.GlobalConstant;
import com.o2r.model.MetaPartner;
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

		log.info("*** AddPArtner Starts : partnerDaoImpl****");
		log.debug("******* Inside PartnerDaoIMpl partner id :"
				+ partner.getPcId() + "******* ConfigId : "
				+ partner.getNrnReturnConfig().getConfigId());
		long id = partner.getPcId();

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			if (id != 0) {
				Query gettingTaxId = session.createSQLQuery(chargesDeleteQuery)
						.setParameter("configId",
								partner.getNrnReturnConfig().getConfigId());
				gettingTaxId.executeUpdate();
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
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addPartnerError,
					new Date(), 1, GlobalConstant.addPartnerErrorCode, e);
		}
		log.info("*** AddPArtner Ends : partnerDaoImpl****");
	}

	@Override
	public void editPartner(Partner partner, int sellerId)
			throws CustomException {

		log.info("*** editPartner Starts : partnerDaoImpl****");
		log.debug("******* Inside PartnerDaoIMpl partner id :"
				+ partner.getPcId() + "******* ConfigId : "
				+ partner.getNrnReturnConfig().getConfigId());
		long id = partner.getPcId();

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			if (id != 0) {

				session.merge(partner);
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.addPartnerError,
					new Date(), 1, GlobalConstant.addPartnerErrorCode, e);
		}
		log.info("*** editPartner Ends : partnerDaoImpl****");
	}

	@Override
	public List<Partner> listPartner(int sellerId) throws CustomException {

		log.info("*** listPartner Starts : partnerDaoImpl****");
		List<Partner> returnlist = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			log.debug(" getting seller " + seller);
			if (seller.getPartners() != null
					&& seller.getPartners().size() != 0)
				returnlist = seller.getPartners();

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listPartnerError,
					new Date(), 3, GlobalConstant.listPartnerErrorCode, e);

		}
		log.info("*** listPartner Ends : partnerDaoImpl****");
		return returnlist;
	}
	
	@Override
	public Map<String, Boolean> getPartnerMap(int sellerId) throws CustomException {

		log.info("*** getPartnerMap Starts : partnerDaoImpl****");
		List<Partner> returnlist = null;
		Partner returnObject = null;
		Map<String, Boolean> partnerNameList = new HashMap<String, Boolean>();
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Seller seller = (Seller) session.get(Seller.class, sellerId);
			log.debug(" getting seller " + seller);
			if (seller.getPartners() != null
					&& seller.getPartners().size() != 0)
				returnlist = seller.getPartners();

			if (returnlist != null && returnlist.size() != 0) {

				returnObject = (Partner) returnlist.get(0);
				if (returnObject != null && returnObject.getNrnReturnConfig() != null) {
					partnerNameList.put(returnObject.getPcName().trim(), returnObject.getNrnReturnConfig()
							.isNrCalculator());
				} else{
					log.debug("Partner is null " + sellerId);
				}
			} else {
				log.debug("No Partners available for Seller ID: " + sellerId);
			}
			
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.listPartnerError,
					new Date(), 3, GlobalConstant.listPartnerErrorCode, e);

		}
		log.info("*** getPartnerMap Ends : partnerDaoImpl****");
		return partnerNameList;
	}

	@Override
	public Partner getPartner(long partnerid) throws CustomException {

		log.info("*** getPartner Starts : partnerDaoImpl****");
		Partner partner = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			partner = (Partner) session.get(Partner.class, partnerid);
			Hibernate.initialize(partner.getNrnReturnConfig().getCharges());
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.getPartnerError,
					new Date(), 3, GlobalConstant.getPartnerErrorCode, e);
		}
		log.info("*** getPartner Ends : partnerDaoImpl****");
		return partner;
	}

	@Override
	public MetaPartner getMetaPartner(String partnerName)
			throws CustomException {

		log.info("*** getPartner Starts : partnerDaoImpl****");
		MetaPartner partner = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			// partner =(MetaPartner)
			// session.get(MetaPartner.class,partnerName);
			Criteria criteria = session.createCriteria(MetaPartner.class).add(
					Restrictions.eq("pcName", partnerName));
			if (criteria.list() != null && criteria.list().size() != 0) {
				partner = (MetaPartner) criteria.list().get(0);
				Hibernate.initialize(partner.getNrnReturnConfig().getCharges());
			}
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.getPartnerError,
					new Date(), 3, GlobalConstant.getPartnerErrorCode, e);
		}
		log.info("*** getPartner Ends : partnerDaoImpl****");
		return partner;
	}

	@Override
	public Partner getPartner(String partnername, int sellerId)
			throws CustomException {

		log.info("*** getPartner by PartnerName Starts : partnerDaoImpl****");
		Partner returnpartner = null;
		Seller seller = null;

		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			seller = (Seller) session.get(Seller.class, sellerId);
			/*
			 * Criteria criteria = session.createCriteria(Seller.class).add(
			 * Restrictions.eq("id", sellerId));
			 * criteria.createAlias("partners",
			 * "partner",CriteriaSpecification.LEFT_JOIN)
			 * .add(Restrictions.like("partner.pcName", partnername,
			 * MatchMode.EXACT)) // .add(Restrictions.eq("partner.pcName",
			 * partnername).ignoreCase())
			 * .setResultTransformer(CriteriaSpecification
			 * .DISTINCT_ROOT_ENTITY);
			 */
			List<Partner> partnerList = seller.getPartners();
			if (partnerList != null && partnerList.size() != 0) {
				for (Partner partner : partnerList) {
					if (partner != null && partner.getPcName().equals(partnername))
						returnpartner = partner;
				}
				if (returnpartner != null
						&& returnpartner.getNrnReturnConfig() != null) {
					Hibernate.initialize(returnpartner.getNrnReturnConfig()
							.getCharges());
					Hibernate.initialize(returnpartner.getOrders());
				}
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.getPartnerError,
					new Date(), 3, GlobalConstant.getPartnerErrorCode, e);

		}
		log.info("*** getPartner by partnerName Starts : partnerDaoImpl****");
		return returnpartner;

	}

	@Override
	public void deletePartner(Partner partner, int sellerId)
			throws CustomException {

		log.info("*** DeletePartner Starts : partnerDaoImpl****");
		log.debug(" In partner delete pid " + partner.getPcId() + "   pcname "
				+ partner.getPcName());

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

			log.debug(" update : " + updated + " sellerdelete " + sellerdelete);
			session.getTransaction().commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed! by sellerId : " + sellerId, e);
			throw new CustomException(GlobalConstant.deletePartnerError,
					new Date(), 3, GlobalConstant.deletePartnerErrorCode, e);

		}
		log.info("*** DeletePartner Ends : partnerDaoImpl****");
	}

	@Override
	public void addMetaPartner(MetaPartner partner) throws CustomException {

		log.info("*** AddPArtner Starts : partnerDaoImpl****");
		log.debug("******* Inside PartnerDaoIMpl partner id :"
				+ partner.getPcId() + "******* ConfigId : "
				+ partner.getNrnReturnConfig().getConfigId());
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();

			session.saveOrUpdate(partner);

			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
			throw new CustomException(GlobalConstant.addPartnerError,
					new Date(), 1, GlobalConstant.addPartnerErrorCode, e);
		}
		log.info("*** AddPArtner Ends : partnerDaoImpl****");
	}

	/*
	 * @Override public void deleteNrConfigFromPartner(Partner partner, int
	 * sellerId) throws CustomException {
	 * System.out.println(" In deleteNrConfigFromPartner pid " +
	 * partner.getPcId() + "   pcname " + partner.getPcName()); // sellerId=4;
	 * try { Session session = sessionFactory.openSession();
	 * session.beginTransaction(); Query deleteQuery = session .createSQLQuery(
	 * "delete from Seller_Partner where partners_pcId=? and Seller_id=?");
	 * 
	 * deleteQuery.setInteger(0, partner.getPcId()); deleteQuery.setInteger(1,
	 * sellerId); int updated = deleteQuery.executeUpdate(); int sellerdelete =
	 * session.createQuery( "DELETE FROM Partner WHERE pcId = " +
	 * partner.getPcId()) .executeUpdate();
	 * 
	 * Criteria criteria=session.createCriteria(Seller.class).add(Restrictions
	 * .eq("id", sellerId)); criteria.createAlias("partners", "partner",
	 * CriteriaSpecification.LEFT_JOIN) .add(Restrictions.eq("partner.pcId",
	 * partner.getPcId())) .setResultTransformer
	 * (CriteriaSpecification.DISTINCT_ROOT_ENTITY);
	 * seller=(Seller)criteria.list().get(0); Partner
	 * partneris=seller.getPartners().get(0);
	 * seller.getPartners().remove(partneris); session.delete(partneris);
	 * System.out.println(" List size after deletion :"
	 * +seller.getPartners().size()); session.saveOrUpdate(seller);
	 * 
	 * System.out.println(" update : " + updated + " sellerdelete " +
	 * sellerdelete); session.getTransaction().commit(); session.close();
	 * 
	 * } catch (Exception e) {
	 * 
	 * log.error("Failed!",e); throw new
	 * CustomException(GlobalConstant.deletePartnerError, new Date(), 3,
	 * GlobalConstant.deletePartnerErrorCode, e); //
	 * System.out.println(" Inside delleting partner"+ //
	 * e.getLocalizedMessage());
	 * 
	 * }
	 * 
	 * }
	 */

}
