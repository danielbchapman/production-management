package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Remote;

import com.danielbchapman.production.dto.CitySheetDto;
import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.Hospital;
import com.danielbchapman.production.entity.Hotel;
import com.danielbchapman.production.entity.PointOfInterest;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Venue;

/**
 * The CompanyManagementBean provides methods for managing the Hospitals and cities and hotels. This
 * is used in the city sheets and primarily relates to company management. However, these objects
 * are joined by venues and can be used to relate to production properties as well.
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
	 * @return a list of all cataloged cities
	 * 
	 */
	public abstract ArrayList<City> getCities();

	/**
	 * 
	 * @param id
	 *          the ID to use
	 * @return a City by ID
	 * 
	 */
	public abstract City getCity(Long id);

	/**
	 * @param cities
	 *          the cities to return a list of
	 * 
	 * @param season
	 *          the season to search in (for dates)
	 * @return a list of CitySheetDto element for this collection of cities
	 */
	public ArrayList<CitySheetDto> getCitySheetDto(City city, Season season);

	/**
	 * @param cities
	 *          the cities to return a list of
	 * 
	 * @param season
	 *          the season to search in (for dates)
	 * @return a list of CitySheetDto element for this collection of cities
	 */
	public ArrayList<CitySheetDto> getCitySheetDto(Collection<City> cities, Season season);

	/**
	 * 
	 * @param id
	 *          the ID to use
	 * @return a Hospital by ID
	 * 
	 */
	public abstract Hospital getHospital(Long id);

	/**
	 * @param city
	 *          the city to query by
	 * @return a list of the hospitals in the city
	 * 
	 */
	public abstract ArrayList<Hospital> getHospitalsForCity(City city);

	/**
	 * 
	 * @param id
	 *          the ID to use
	 * @return a Hotel by ID
	 * 
	 */
	public abstract Hotel getHotel(Long id);

	/**
	 * @param city
	 *          the city to query
	 * @return a list of all the hotels in this city
	 * 
	 */
	public abstract ArrayList<Hotel> getHotelsForCity(City city);

	/**
	 * @param id
	 *          the DB ID
	 * @return return the point of intrest associated with this ID
	 */
	public abstract PointOfInterest getPointOfInterest(Long id);

	/**
	 * @param city
	 *          the city to search
	 * @return all points of interest for this city.
	 */
	public abstract ArrayList<PointOfInterest> getPointsOfInterest(City city);

	/**
	 * @param city
	 *          the city to query by
	 * @return a list of all the venues in the city
	 * 
	 */
	public abstract ArrayList<Venue> getVenuesForCity(City city);

	/**
	 * Remove this point of interest from the database.
	 * 
	 * @param pointOfInterest
	 *          the point of interest to remove
	 */
	public abstract void removePointOfInterest(PointOfInterest pointOfInterest);

	/**
	 * Saves a city to the database
	 * 
	 * @param city
	 *          the city in question
	 * 
	 */
	public abstract void saveCity(City city);

	/**
	 * Saves a hospital to the database.
	 * 
	 * @param hospital
	 *          saves a particular hospital
	 * 
	 */
	public abstract void saveHospital(Hospital hospital);

	/**
	 * Save a hotel to the database
	 * 
	 * @param hotel
	 *          the hotel to persists
	 * 
	 */
	public abstract void saveHotel(Hotel hotel);

	/**
	 * Save this point of interest and any changes to it.
	 * 
	 * @param pointOfInterest
	 *          the point of intrest to persist or merge
	 */
	public abstract void savePointOfInterest(PointOfInterest pointOfInterest);
}
