package com.o2r.dao;

import java.util.List;

import com.o2r.model.StaticAreaTable;

/**
 * @author Deep Mehrotra
 *
 */
// GIT Test
public interface AreaConfigDao {

	public List<String> listCountryStates(String country);

	public String getStateFromZipCode(String zipcode);

	public String getCityFromZipCode(String zipcode);

	public String getStateFromCity(String city);

	boolean isZipCodeValid(String zipcode);

	public String getMetroFromZipCode(String zipcode);
	
	public List<String> listCities();
	
	public boolean addAreaZipcode(StaticAreaTable area, String city);
	
	public boolean checkZipcode(String zipcode);
}
