package com.o2r.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

	@Override
	public List<String> listCountryStates(String country) {

		// sessionFactory.getCurrentSession().saveOrUpdate(seller);
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
		}
		return stateNames;
	}

	public String getStateFromZipCode(String zipcode) {

		// sessionFactory.getCurrentSession().saveOrUpdate(seller);
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
			if (stateNames != null && stateNames.size() != 0)
				returnState = stateNames.get(0);
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return returnState;
	}

	public String getCityFromZipCode(String zipcode) {
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
		}
		return returnCity;
	}

	public String getStateFromCity(String city) {
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
		}
		return returnCity;
	}

	@Override
	public boolean isZipCodeValid(String zipcode) {

		// sessionFactory.getCurrentSession().saveOrUpdate(seller);
		List<String> stateNames = null;
		boolean returnObject = false;
		try {
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			Query gettingStates = session.createSQLQuery(zipcodeValidQuery)
					.setParameter("zipcode", zipcode);
			stateNames = gettingStates.list();
			if (stateNames != null && stateNames.size() != 0)
				returnObject = true;
			session.getTransaction().commit();
			session.close();
		} catch (Exception e) {

			e.printStackTrace();
		}
		return returnObject;
	}
}
