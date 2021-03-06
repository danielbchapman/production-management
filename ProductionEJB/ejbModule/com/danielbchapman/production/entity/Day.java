package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

/**
 * A simple object representing a day of the week for use in the Weekly
 * Schedules for a tour
 * 
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "date", "week" }))
public class Day implements Serializable
{
	private static final long serialVersionUID = 1L;

	private Hotel castHotel;
	private City castLocation;
	@Column(length = 20)
	private String castTravel;
	private Hotel crewHotel;
	private City crewLocation;
	@Column(length = 20)
	private String crewTravel;
	@Temporal(value = TemporalType.DATE)
	private Date date;
	@OneToMany(mappedBy = "day", targetEntity = Event.class, fetch=FetchType.EAGER)
	private Collection<Event> events;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 75)
	private String label;
	@Column(length = 120)
	private String milageInformation;
	@Lob
	private String notes;
	@OneToMany(mappedBy = "day", targetEntity = Performance.class, fetch=FetchType.EAGER)
	private Collection<Performance> performances;
	@Column(length = 120)
	private String theaterInformation;
	@ManyToOne
	private Week week;
	
	public Day()
	{
		super();
	}
	public Hotel getCastHotel()
	{
		return castHotel;
	}

	public City getCastLocation()
	{
		return castLocation;
	}
	public String getCastTravel()
	{
		return castTravel;
	}
	public Hotel getCrewHotel()
	{
		return crewHotel;
	}
	public City getCrewLocation()
	{
		return crewLocation;
	}
	
	public String getCrewTravel()
	{
		return crewTravel;
	}

	public Date getDate()
	{
		return date;
	}

	/**
	 * @return the events
	 */
	public Collection<Event> getEvents()
	{
		return events;
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}

	public String getMilageInformation()
	{
		return milageInformation;
	}
	/**
	 * @return the notes
	 */
	public String getNotes()
	{
		return notes;
	}

	public Collection<Performance> getPerformances()
	{
		return performances;
	}

	public String getTheaterInformation()
	{
		return theaterInformation;
	}

	/**
	 * A java based hack for JasperReports. The time lines are easier
	 * to display as collections.
	 * 
	 * @return a list of EventMappings (Events/PerformanceEvents) for this day ordered by time.
	 * @see Performance#getEventSequence()
	 * 
	 */
	@Transient
	public ArrayList<EventMapping> getTimeline()
	{
		ArrayList<EventMapping> ret = new ArrayList<EventMapping>();
		ret.addAll(getEvents());
		
		for(Performance p : getPerformances())
			ret.addAll(p.getEventSequence());
		
		Collections.sort(ret);
		return ret;
	}

	/**
	 * @return the week
	 */
	public Week getWeek()
	{
		return week;
	}

	public void setCastHotel(Hotel castHotel)
	{
		this.castHotel = castHotel;
	}

	public void setCastLocation(City castLocation)
	{
		this.castLocation = castLocation;
	}

	public void setCastTravel(String castTravel)
	{
		this.castTravel = castTravel;
	}

	public void setCrewHotel(Hotel crewHotel)
	{
		this.crewHotel = crewHotel;
	}

	public void setCrewLocation(City crewLocation)
	{
		this.crewLocation = crewLocation;
	}

	public void setCrewTravel(String crewTravel)
	{
		this.crewTravel = crewTravel;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * @param events
	 *          the events to set
	 */
	public void setEvents(Collection<Event> events)
	{
		this.events = events;
	}

	/**
	 * @param id
	 *          the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @param label
	 *          the label to set
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	public void setMilageInformation(String milageInformation)
	{
		this.milageInformation = milageInformation;
	}

	/**
	 * @param notes
	 *          the notes to set
	 */
	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public void setPerformances(Collection<Performance> performances)
	{
		this.performances = performances;
	}

	public void setTheaterInformation(String theaterInformation)
	{
		this.theaterInformation = theaterInformation;
	}

	/**
	 * @param week
	 *          the week to set
	 */
	public void setWeek(Week week)
	{
		this.week = week;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		
		buf.append(super.toString());
		buf.append("Date ");
		buf.append(date);
		
		return buf.toString();
	}
}
