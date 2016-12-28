package com.o2r.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.o2r.model.StaticAreaTable;

/**
 * @author Deep Mehrotra
 *
 */
// GIT Test
@Repository("areaConfigDao")
public class AreaConfigDaoImpl implements AreaConfigDao {

	@Autowired
	private SessionFactory sessionFactory;

	static Logger log = Logger.getLogger(AreaConfigDaoImpl.class.getName());
	
	private static final String stateRetriveQuery = "select ts.state_name from tbl_states ts , tbl_countries tc where tc.id=ts.country_id and tc.name=:country;";
	private static final String stateRetriveFromZipCodeQuery = "select ts.state_name from tbl_states ts , tbl_city tc,tbl_area "
			+ "ta where  ta.city_id=tc.id and tc.state_id=ts.id and ta.zipcode=:zipcode";
	private static final String cityRetriveFromZipCodeQuery = "select tc.city_name from  tbl_city tc,tbl_area "
			+ "ta where  ta.city_id=tc.id and  ta.zipcode=:zipcode";
	private static final String stateRetriveFromCityQuery = "select ts.state_name from tbl_states ts , tbl_city t"
			+ " where  tc.state_id=ts.id and tc.city_name=:cityName";
	private static final String zipcodeValidQuery = "select * from tbl_area where zipcode=:zipcode";
	
	private static final String listCitiesQuery = "SELECT city_name FROM o2rschema.tbl_city order by city_name asc";
	
	private static final String getCityQuery = "select id from tbl_city where city_name =:city";
	
	private static final String addZipcode = "insert into tbl_area (`id`, `area_name`, `zipcode`, `city_id`, `status`, `is_deleted`)"+
			"values (:id , :area, :zip, :cityid, 1, 0)";

	@Override
	public List<String> listCountryStates(String country) {

		log.info("*** listCountryStates starts***");
		List<String> stateNames = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query gettingStates = session.createSQLQuery(stateRetriveQuery)
					.setParameter("country", country);
			stateNames = gettingStates.list();
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("*** listCountryStates ends***");
		return stateNames;
	}

	public String getStateFromZipCode(String zipcode) {
		
		log.info("*** getStateFromZipCode starts***");
		List<String> stateNames = null;
		String returnState = null;
		System.out.println(" Fetching state against zipcode : " + zipcode);
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query gettingStates = session.createSQLQuery(
					stateRetriveFromZipCodeQuery).setParameter("zipcode",
					zipcode);
			stateNames = gettingStates.list();
			System.out.println(" stateNames.size() " + stateNames.size());
			System.out.println(" States  : "+stateNames.toString());
			if (stateNames != null && stateNames.size() != 0)
				returnState = stateNames.get(0);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("*** getStateFromZipCode ends***");
		return returnState;
	}

	public String getCityFromZipCode(String zipcode) {
		
		log.info("*** getCityFromZipCode starts***");
		List<String> cityNames = null;
		String returnCity = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query gettingStates = session.createSQLQuery(
					cityRetriveFromZipCodeQuery).setParameter("zipcode",
					zipcode);
			cityNames = gettingStates.list();
			if (cityNames != null)
				returnCity = cityNames.get(0);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("*** getCityFromZipCode starts***");
		return returnCity;
	}

	public String getStateFromCity(String city) {
		log.info("***getStateFromCity starts *** ");
		List<String> cityNames = null;
		String returnCity = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query gettingStates = session.createSQLQuery(
					stateRetriveFromCityQuery).setParameter("cityName", city);
			cityNames = gettingStates.list();
			if (cityNames != null)
				returnCity = cityNames.get(0);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!",e);
		}
		log.info("***getStateFromCity ends*** ");
		return returnCity;
	}

	@Override
	public boolean isZipCodeValid(String zipcode) {

		log.info("***isZipCodeValid starts*** ");
		List<String> stateNames = null;
		boolean returnObject = false;	
		Session session = null;
		try {			
			
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query gettingStates = session.createSQLQuery(zipcodeValidQuery)
					.setParameter("zipcode", zipcode);
			stateNames = gettingStates.list();
			if (stateNames != null && stateNames.size() != 0)
				returnObject = true;
			session.getTransaction().commit();	
			if(returnObject != true)
				throw new NullPointerException();
		} catch (NullPointerException ne) {			
			log.error("Invalid Zipcode ! : "+zipcode, ne);			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed ! ", e);
			log.equals(e);
		} finally {
			if(session != null)
				session.close();
		}
		log.info("***isZipCodeValid ends*** ");
		return returnObject;
	}
	
	@Override
	public String getMetroFromZipCode(String zipcode) {
		log.info("*** getCityFromZipCode starts***");
		List<String> cityNames = null;
		String returnCity = null;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query gettingStates = session.createSQLQuery(
					cityRetriveFromZipCodeQuery).setParameter("zipcode",
					zipcode);
			cityNames = gettingStates.list();
			if (cityNames != null)
				returnCity = cityNames.get(0);
			if (returnCity != null && returnCity.contains("Delhi")) {
				returnCity = "Delhi";
			}
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Failed!", e);
		}
		log.info("*** getCityFromZipCode starts***");
		return returnCity;
	}
	
	@Override
	public List<String> listCities() {
		List<String> listCities = new ArrayList<String>();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			/*Criteria listCitiesCriteria = session.createCriteria(StaticCityTable.class)
					.setProjection(Projections.property("city_name"))
					.addOrder(org.hibernate.criterion.Order.asc("city_name"));*/
			Query listCitiesCriteria = session.createSQLQuery(listCitiesQuery);	
			listCities = listCitiesCriteria.list();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in getting City List....", e);
		} finally {
			if(session != null){
				session.close();
			}
		}		
		return listCities;
	}
	
	@Override
	public boolean addAreaZipcode(StaticAreaTable areaObj, String city) {
		Session session = null;	
		BigInteger cityObj = null;
		try {
			if(areaObj != null){
				session = sessionFactory.openSession();
				session.beginTransaction();
				Query getCity = session.createSQLQuery(getCityQuery)
						.setParameter("city", city);
				if(getCity.list().size() != 0){
					cityObj = (BigInteger) getCity.list().get(0);				
				}
				
				if(cityObj != null && areaObj != null){
					if (checkZipcode(areaObj.getZipcode()) != true && StringUtils.isNumeric(areaObj.getZipcode())
							&& areaObj.getZipcode().length() ==  6) {
						areaObj.setCity_id(cityObj.longValue());
						String getMaxID = "select max(id) from tbl_area";
						BigInteger maxId = (BigInteger) session.createSQLQuery(getMaxID).list().get(0);
						session.createSQLQuery(addZipcode)
							.setParameter("id", maxId.longValue()+1)
							.setParameter("area", areaObj.getArea_name())
							.setParameter("zip", areaObj.getZipcode())
							.setParameter("cityid", areaObj.getCity_id())
							.executeUpdate();
						session.getTransaction().commit();
						return true;
					}					
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error in getting City List....", e);
			return false;
		} finally {
			if(session != null){
				session.close();
			}
		}
		return false;
	}	
	
	@Override
	public boolean checkZipcode(String zipcode) {
		List<String> returnList = null;
		Session session = null;
		boolean result = false;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			Query listCitiesCriteria = session.createSQLQuery(zipcodeValidQuery)
					.setParameter("zipcode", zipcode);	
			returnList = listCitiesCriteria.list();
			if(returnList != null && returnList.size() !=0){
				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Check Zipcode By Admin....", e);
			return false;
		} finally {
			if(session != null){
				session.close();
			}
		}		
		return result;
	}
	
}
