package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;

import javax.persistence.*;

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
//	@OneToMany(mappedBy = "day", targetEntity = Event.class)
//	private Collection<Event> events;
	@Column(length = 75)
	private String label;
	@Column(length = 256)
	private String notes;
	@Column(length = 20)
	private String castTravel;
	@Column(length = 20)
	private String crewTravel;
	@Column(length = 120)
	private String castLocation;
	@Column(length = 120)
	private String crewLocation;
	@Column(length = 120)
	private String theaterInformation;
	@Column(length = 120)
	private String milageInformation;
	@ManyToOne
	private Week week;

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

//	/**
//	 * @return the events
//	 */
//	public Collection<Event> getEvents()
//	{
//		return events;
//	}
//
//	/**
//	 * @param events
//	 *          the events to set
//	 */
//	public void setEvents(Collection<Event> events)
//	{
//		this.events = events;
//	}

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

	public String getCastLocation()
	{
		return castLocation;
	}

	public void setCastLocation(String castLocation)
	{
		this.castLocation = castLocation;
	}

	public String getCrewLocation()
	{
		return crewLocation;
	}

	public void setCrewLocation(String crewLocation)
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

}
