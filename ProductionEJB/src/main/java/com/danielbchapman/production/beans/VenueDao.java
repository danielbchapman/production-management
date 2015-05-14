package com.danielbchapman.production.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Stateless;

import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Venue;
import com.danielbchapman.production.entity.VenueLog;

/**
 * A bean for accessing venue information.
 */
@Stateless
public class VenueDao implements VenueDaoRemote
{
	private static final long serialVersionUID = 1L;
	// @PersistenceContext
//	EntityManager em = EntityInstance.getEm();

	/**
	 * Default constructor.
	 */
	public VenueDao()
	{
	}

	@Override
	public void addLogEntry(String value, Venue venue, String userPrincipal)
	{
		VenueLog log = new VenueLog(venue, new Date(), value, userPrincipal);
		EntityInstance.saveObject(log);
	}

	@Override
	public ArrayList<Venue> getAllVenues()
	{
		return EntityInstance.getResultList("SELECT v FROM Venue v ORDER BY v.name", Venue.class);
	}

	@Override
	public ArrayList<VenueLog> getLogEntries(Venue venue)
	{
		return EntityInstance.getResultList("SELECT log FROM VenueLog log WHERE log.venue = ?1 ORDER BY log.date", VenueLog.class, venue);
	}

	@Override
	public File getRootGeneralFolder()
	{
		return null;
	}

	@Override
	public Venue getVenue(Long id)
	{
		return EntityInstance.find(Venue.class, id);
	}

	@Override
	public Venue getVenue(String user)
	{
		return null;
	}

	@Override
	public ArrayList<Venue> getVenues(Season production)
	{
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.VenueDaoRemote#getVenuesForCity(com.danielbchapman.production
	 * .entity.City)
	 */
	@Override
	public ArrayList<Venue> getVenuesForCity(City city)
	{
		return EntityInstance.getResultList("SELECT v FROM Venue v WHERE v.city = ?1", Venue.class,
				city);
	}

	@Override
	public void saveVenue(Venue venue)
	{
		EntityInstance.saveObject(venue);

	}

}
