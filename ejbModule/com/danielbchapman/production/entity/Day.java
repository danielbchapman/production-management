package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
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

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Temporal(value = TemporalType.DATE)
	private Date date;
	@OneToMany(mappedBy = "day", targetEntity = Event.class)
	private Collection<Event> events;
	@Column(length = 75)
	private String label;
	@Lob
	private String notes;
	@Column(length = 20)
	private String castTravel;
	@Column(length = 20)
	private String crewTravel;
	@Column(length = 120)
	private String theaterInformation;
	@Column(length = 120)
	private String milageInformation;
	@ManyToOne
	private Week week;
	private City castLocation;
	private City crewLocation;
	
	public Day()
	{
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id
	 *          the id to set
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * @return the events
	 */
	public Collection<Event> getEvents()
	{
		return events;
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
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * @param label
	 *          the label to set
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * @return the notes
	 */
	public String getNotes()
	{
		return notes;
	}

	/**
	 * @param notes
	 *          the notes to set
	 */
	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	/**
	 * @return the week
	 */
	public Week getWeek()
	{
		return week;
	}

	/**
	 * @param week
	 *          the week to set
	 */
	public void setWeek(Week week)
	{
		this.week = week;
	}

	public String getCastTravel()
	{
		return castTravel;
	}

	public void setCastTravel(String castTravel)
	{
		this.castTravel = castTravel;
	}

	public City getCastLocation()
	{
		return castLocation;
	}

	public void setCastLocation(City castLocation)
	{
		this.castLocation = castLocation;
	}

	public City getCrewLocation()
	{
		return crewLocation;
	}

	public void setCrewLocation(City crewLocation)
	{
		this.crewLocation = crewLocation;
	}

	public String getCrewTravel()
	{
		return crewTravel;
	}

	public void setCrewTravel(String crewTravel)
	{
		this.crewTravel = crewTravel;
	}

	public String getTheaterInformation()
	{
		return theaterInformation;
	}

	public void setTheaterInformation(String theaterInformation)
	{
		this.theaterInformation = theaterInformation;
	}

	public String getMilageInformation()
	{
		return milageInformation;
	}

	public void setMilageInformation(String milageInformation)
	{
		this.milageInformation = milageInformation;
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
