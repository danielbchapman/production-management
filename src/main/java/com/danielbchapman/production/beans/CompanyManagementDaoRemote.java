package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.Hospital;
import com.danielbchapman.production.entity.Hotel;
import com.danielbchapman.production.entity.Venue;

/**
 * The CompanyManagementBean provides methods for managing the Hospitals and cities
 * and hotels. This is used in the city sheets and primarily relates to company
 * management. However, these objects are joined by venues and can be used to relate
 * to production properties as well.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 23, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface CompanyManagementDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;
	/**
	 * Save a hotel to the database
	 * @param hotel the hotel to persists  
	 * 
	 */
	public abstract void saveHotel(Hotel hotel);
	/**
	 * Saves a city to the database
	 * @param city the city in question  
	 * 
	 */
	public abstract void saveCity(City city);
	
	/**
	 * Saves a hospital to the database.
	 * @param hospital saves a particular hospital  
	 * 
	 */
	public abstract void saveHospital(Hospital hospital);
	
	/**
	 * @return a list of all cataloged cities 
	 * 
	 */
	public abstract ArrayList<City> getCities();
	
	/**
	 * @param city the city to query
	 * @return a list of all the hotels in this city 
	 * 
	 */
	public abstract ArrayList<Hotel> getHotelsForCity(City city);
	
	/**
	 * @param city the city to query by
	 * @return a list of the hospitals in the city  
	 * 
	 */
	public abstract ArrayList<Hospital> getHospitalsForCity(City city);
	
	/**
	 * @param city the city to query by
	 * @return a list of all the venues in the city
	 * 
	 */
	public abstract ArrayList<Venue> getVenuesForCity(City city);
	
	/**
	 * 
	 * @param id the ID to use
	 * @return  a Hotel by ID  
	 * 
	 */
	public abstract Hotel getHotel(Long id);
	/**
	 * 
	 * @param id the ID to use
	 * @return  a City by ID  
	 * 
	 */
	public abstract City getCity(Long id);
	/**
	 * 
	 * @param id the ID to use
	 * @return  a Hospital by ID  
	 * 
	 */
	public abstract Hospital getHospital(Long id);

}
