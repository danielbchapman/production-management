package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Stateless;

import com.danielbchapman.production.dto.CitySheetDto;
import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.Day;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Hospital;
import com.danielbchapman.production.entity.Hotel;
import com.danielbchapman.production.entity.Performance;
import com.danielbchapman.production.entity.PointOfInterest;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Utility;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getCities()
	 */
	@Override
	public ArrayList<City> getCities()
	{
		return EntityInstance.getResultList("SELECT c FROM City c ORDER BY c.stateOrTerritory, c.name",
				City.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getCity(java.lang.Long)
	 */
	@Override
	public City getCity(Long id)
	{
		return EntityInstance.find(City.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CompanyManagementDaoRemote#getCitySheetDto(com.danielbchapman
	 * .production.entity.City)
	 */
	@Override
	public ArrayList<CitySheetDto> getCitySheetDto(City city, Season season)
	{
		ArrayList<City> cities = new ArrayList<City>();
		cities.add(city);
		return getCitySheetDto(cities, season);
	}

	@Override
	public ArrayList<CitySheetDto> getCitySheetDto(Collection<City> cities, Season season)
	{
		ArrayList<CitySheetDto> dtos = new ArrayList<CitySheetDto>();

		for(City city : cities)
		{
			CitySheetDto tmp = new CitySheetDto();
			tmp.setCity(city);
			tmp.setHospital(city.getSelectedHospital());
			tmp.setHotels(getHotelsForCity(city));
			//@formatter:off
			String queryPerformance = 
							"SELECT  \n" +
							"  p  \n" +
							"FROM  \n" +
							"  Performance p  \n" +
							"WHERE  \n" +
							"      p.day.castLocation = ?1  \n" +
							"  AND p.day.week.season = ?2  \n" +
							"ORDER BY  \n" +
							"  p.day.date \n" ;
			
			String queryDayCast = 
							"SELECT  \n" +
							"  d  \n" +
							"FROM  \n" +
							"  Day d  \n" +
							"WHERE  \n" +
							"      d.castLocation = ?1  \n" +
							"  AND d.week.season = ?2  \n" +
							"ORDER BY  \n" +
							"  d.date \n" ;
			
			String queryDayCrew = 
							"SELECT  \n" +
							"  d  \n" +
							"FROM  \n" +
							"  Day d  \n" +
							"WHERE  \n" +
							"      d.crewLocation = ?1  \n" +
							"  AND d.week.season = ?2  \n" +
							"ORDER BY  \n" +
							"  d.date \n" ;

			ArrayList<Performance> performances = EntityInstance.getResultList(queryPerformance, Performance.class, city, season);
			ArrayList<Day> castDaysInSeason = EntityInstance.getResultList(queryDayCast, Day.class, city, season);
			ArrayList<Day> crewDaysInSeason = EntityInstance.getResultList(queryDayCrew, Day.class, city, season);
			//@formatter:on
			if(performances.size() > 0)
				tmp.setVenue(performances.get(0).getVenue());

			if(castDaysInSeason.size() > 1)
			{
				tmp.setCastArrive(castDaysInSeason.get(0).getDate());
				tmp.setCastLeave(castDaysInSeason.get(castDaysInSeason.size() - 1).getDate());
			}

			if(crewDaysInSeason.size() > 1)
			{
				tmp.setCrewArrive(crewDaysInSeason.get(0).getDate());
				tmp.setCrewLeave(crewDaysInSeason.get(crewDaysInSeason.size() - 1).getDate());
			}

			tmp.setEarliest(Utility.compareReturnSmaller(tmp.getCastArrive(), tmp.getCrewArrive()));
			tmp.setLatest(Utility.compareReturnSmaller(tmp.getCastLeave(), tmp.getCrewLeave()));
			tmp.setPointsOfInterest(getPointsOfInterest(city));
			tmp.setSeason(season);
			tmp.setPerformances(performances);
			dtos.add(tmp);
		}

		return dtos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getHospital(java.lang.Long)
	 */
	@Override
	public Hospital getHospital(Long id)
	{
		return EntityInstance.find(Hospital.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getHospitalsForCity(com.
	 * danielbchapman.production.entity.City)
	 */
	@Override
	public ArrayList<Hospital> getHospitalsForCity(City city)
	{
		return EntityInstance.getResultList(
				"SELECT h FROM Hospital h WHERE h.city = ?1 ORDER BY h.name", Hospital.class, city);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getHotel(java.lang.Long)
	 */
	@Override
	public Hotel getHotel(Long id)
	{
		return EntityInstance.find(Hotel.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CompanyManagementDaoRemote#getHotelsForCity(com.danielbchapman
	 * .production.entity.City)
	 */
	@Override
	public ArrayList<Hotel> getHotelsForCity(City city)
	{
		return EntityInstance.getResultList("SELECT h FROM Hotel h WHERE h.city = ?1 ORDER BY h.name",
				Hotel.class, city);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CompanyManagementDaoRemote#getPointOfInterest(java.lang
	 * .Long)
	 */
	@Override
	public PointOfInterest getPointOfInterest(Long id)
	{
		return EntityInstance.find(PointOfInterest.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#getPointsOfInterest(com.
	 * danielbchapman.production.entity.City)
	 */
	@Override
	public ArrayList<PointOfInterest> getPointsOfInterest(City city)
	{
		//@formatter:off
		return EntityInstance.getResultList(
				"SELECT p FROM PointOfInterest p WHERE p.city = ?1", 
				PointOfInterest.class, 
				city);
		//@formatter:on
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CompanyManagementDaoRemote#getVenuesForCity(com.danielbchapman
	 * .production.entity.City)
	 */
	@Override
	public ArrayList<Venue> getVenuesForCity(City city)
	{
		return EntityInstance.getResultList("SELECT v FROM Venue v WHERE v.city = ?1 ORDER BY v.name",
				Venue.class, city);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#removePointOfInterest(com.
	 * danielbchapman.production.entity.PointOfInterest)
	 */
	@Override
	public void removePointOfInterest(PointOfInterest pointOfInterest)
	{
		if(pointOfInterest == null || pointOfInterest.getId() == null)
			return;
		else
			EntityInstance.deleteObject(pointOfInterest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CompanyManagementDaoRemote#saveCity(com.danielbchapman.
	 * production.entity.City)
	 */
	@Override
	public void saveCity(City city)
	{
		EntityInstance.saveObject(city);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CompanyManagementDaoRemote#saveHospital(com.danielbchapman
	 * .production.entity.Hospital)
	 */
	@Override
	public void saveHospital(Hospital hospital)
	{
		EntityInstance.saveObject(hospital);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CompanyManagementDaoRemote#saveHotel(com.danielbchapman
	 * .production.entity.Hotel)
	 */
	@Override
	public void saveHotel(Hotel hotel)
	{
		EntityInstance.saveObject(hotel);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CompanyManagementDaoRemote#savePointOfInterest(com.
	 * danielbchapman.production.entity.PointOfInterest)
	 */
	@Override
	public void savePointOfInterest(PointOfInterest pointOfInterest)
	{
		EntityInstance.saveObject(pointOfInterest);
	}

}
