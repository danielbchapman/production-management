package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.ejb.Stateless;

import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.Hospital;
import com.danielbchapman.production.entity.Hotel;
import com.danielbchapman.production.entity.Venue;

/**
 * Session Bean implementation class CompanyManagementBean
 */
@Stateless
public class CompanyManagementDao implements CompanyManagementDaoRemote
{

	public CompanyManagementDao()
	{
	}

	@Override
	public void saveHotel(Hotel hotel)
	{
		EntityInstance.saveObject(hotel);
	}

	@Override
	public void saveCity(City city)
	{
		EntityInstance.saveObject(city);
	}

	@Override
	public void saveHospital(Hospital hospital)
	{
		EntityInstance.saveObject(hospital);
	}

	@Override
	public ArrayList<City> getCities()
	{
		return EntityInstance.getResultList("SELECT c FROM City c ORDER BY c.stateOrTerritory, c.name", City.class);
	}

	@Override
	public ArrayList<Hotel> getHotelsForCity(City city)
	{
		return EntityInstance.getResultList("SELECT h FROM Hotel h WHERE h.city = ?1 ORDER BY h.name", Hotel.class, city);
	}

	@Override
	public ArrayList<Hospital> getHospitalsForCity(City city)
	{
		//TODO Auto Generated Sub
		return null;
	}

	@Override
	public ArrayList<Venue> getVenuesForCity(City city)
	{
		//TODO Auto Generated Sub
		return null;
	}

	@Override
	public Hotel getHotel(Long id)
	{
		//TODO Auto Generated Sub
		return null;
	}

	@Override
	public City getCity(Long id)
	{
		return EntityInstance.getEm().find(City.class, id);
	}

	@Override
	public Hospital getHospital(Long id)
	{
		//TODO Auto Generated Sub
		return null;
	}

}
