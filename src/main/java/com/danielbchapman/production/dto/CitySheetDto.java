package com.danielbchapman.production.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import lombok.Data;

import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.Hospital;
import com.danielbchapman.production.entity.Hotel;
import com.danielbchapman.production.entity.Performance;
import com.danielbchapman.production.entity.PointOfInterest;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Venue;

@Data
public class CitySheetDto implements Comparable<CitySheetDto>, Serializable
{
	private static final long serialVersionUID = 1L;
	private City city;
	private Collection<PointOfInterest> pointsOfInterest;
	private Collection<Performance> performances;
	private Venue venue;
	private Collection<Hotel> hotels;
	private Hospital hospital;
	private Date earliest;
	private Date latest;
	private Date crewArrive;
	private Date castArrive;
	private Date crewLeave;
	private Date castLeave;
	private Season season;
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(CitySheetDto o)
	{
		if(o == null)
			return 1;

		if(o.getCity() == null)
			return 1;

		if(getCity() == null)
			return -1;

		return getCity().compareTo(o.getCity());
	}

	/**
	 * @return the city
	 */
	public City getCity()
	{
		if(city == null)
			city = new City();
		return city;
	}

	/**
	 * @return the hospital
	 */
	public Hospital getHospital()
	{
		if(hospital == null)
			hospital = new Hospital();
		return hospital;
	}

	/**
	 * @return the hotels
	 */
	public Collection<Hotel> getHotels()
	{
		if(hotels == null)
			hotels = new ArrayList<Hotel>();
		return hotels;
	}

	/**
	 * @return the pointsOfInterest
	 */
	public Collection<PointOfInterest> getPointsOfInterest()
	{
		if(pointsOfInterest == null)
			pointsOfInterest = new ArrayList<PointOfInterest>();
		return pointsOfInterest;
	}

	/**
	 * @return the venue
	 */
	public Venue getVenue()
	{
		if(venue == null)
			venue = new Venue();
		
		return venue;
	}
}
