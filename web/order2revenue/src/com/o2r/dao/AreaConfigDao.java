package com.o2r.dao;

import java.util.List;

/**
 * @author Deep Mehrotra
 *
 */
//GIT Test
public interface AreaConfigDao {
 
 
 public List<String> listCountryStates(String country);
 
 public String getStateFromZipCode(String zipcode);
 
 public String getCityFromZipCode(String zipcode);
 
 public String getStateFromCity(String city);
}
