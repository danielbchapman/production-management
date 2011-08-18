package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.ejb.Stateless;

import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Hospital;
import com.danielbchapman.production.entity.Hotel;
import com.danielbchapman.production.entity.Venue;

/**
 * Session Bean implementation class CompanyManagementBean
 */
@Stateless
public class CompanyManagementDao implements CompanyManagementDaoRemote
{
	private static final long serialVersionUID = 1L;

	public CompanyManagementDao()
	{
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#saveHotel(com.danielbchapman.production.entity.Hotel)
	 */
	@Override
	public void saveHotel(Hotel hotel)
	{
		EntityInstance.saveObject(hotel);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#saveCity(com.danielbchapman.production.entity.City)
	 */
	@Override
	public void saveCity(City city)
	{
		EntityInstance.saveObject(city);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#saveHospital(com.danielbchapman.production.entity.Hospital)
	 */
	@Override
	public void saveHospital(Hospital hospital)
	{
		EntityInstance.saveObject(hospital);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getCities()
	 */
	@Override
	public ArrayList<City> getCities()
	{
		return EntityInstance.getResultList("SELECT c FROM City c ORDER BY c.stateOrTerritory, c.name", City.class);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getHotelsForCity(com.danielbchapman.production.entity.City)
	 */
	@Override
	public ArrayList<Hotel> getHotelsForCity(City city)
	{
		return EntityInstance.getResultList("SELECT h FROM Hotel h WHERE h.city = ?1 ORDER BY h.name", Hotel.class, city);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getHospitalsForCity(com.danielbchapman.production.entity.City)
	 */
	@Override
	public ArrayList<Hospital> getHospitalsForCity(City city)
	{
		return EntityInstance.getResultList("SELECT h FROM Hospital h WHERE h.city = ?1 ORDER BY h.name", Hospital.class, city);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getVenuesForCity(com.danielbchapman.production.entity.City)
	 */
	@Override
	public ArrayList<Venue> getVenuesForCity(City city)
	{
		return EntityInstance.getResultList("SELECT v FROM Venue v WHERE v.city = ?1 ORDER BY v.name", Venue.class, city);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getHotel(java.lang.Long)
	 */
	@Override
	public Hotel getHotel(Long id)
	{
		return EntityInstance.getEm().find(Hotel.class, id);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getCity(java.lang.Long)
	 */
	@Override
	public City getCity(Long id)
	{
		return EntityInstance.getEm().find(City.class, id);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getHospital(java.lang.Long)
	 */
	@Override
	public Hospital getHospital(Long id)
	{
		return EntityInstance.getEm().find(Hospital.class, id);
	}

}
