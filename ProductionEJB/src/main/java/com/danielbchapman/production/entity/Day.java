package com.danielbchapman.production.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.CascadeType;
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

import org.theactingcompany.persistence.Indentifiable;

/**
 * A simple object representing a day of the week for use in the Weekly Schedules for a tour
 * 
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "date", "week" }))
public class Day implements Indentifiable, Comparable<Day>
{
	private static final long serialVersionUID = 1L;
	
	public static Day emptyDay(Date d)
	{
		Day day = new Day();
		day.setDate(d);
		day.empty = true;
		return day;
	}
	
	@Transient
	private boolean empty;
	
	private Hotel castHotel;
	private City castLocation;
	@Column(length = 20)
	private String castTravel = "";
	private Hotel crewHotel;
	private City crewLocation;
	@Column(length = 20)
	private String crewTravel = "";
	@Temporal(value = TemporalType.DATE)
	private Date date;
	@OneToMany(mappedBy = "day", targetEntity = Event.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Event> events;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 75)
	private String label = "";
	@Column(length = 120)
	private String milageInformation = "";
	@Lob
	private String notes = "";
	@OneToMany(mappedBy = "day", targetEntity = Performance.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Performance> performances;
	@Column(length = 120)
	private String theaterInformation;
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH })
	private Week week;

	public Day()
	{
		super();
	}

	@Transient
	public boolean isEmpty()
	{
		return empty;
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
	@Override
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

	@Transient
	public Performance getProbablePerformance()
	{
		if(getPerformances() == null || getPerformances().size() == 0)
			return null;
		else
		{
			Performance top = null;
			for(Performance p : getPerformances())
			{
				if(top == null)
				{
					top = p;
					continue;
				}

				if(p.getStart().compareTo(top.getStart()) > 0)
					top = p;
			}
			return top;
		}
	}

	@Transient
	public Venue getProbableVenue()
	{
		if(getPerformances() == null || getPerformances().size() == 0)
			return null;
		else
		{
			Performance top = null;
			for(Performance p : getPerformances())
			{
				if(top == null)
				{
					top = p;
					continue;
				}

				if(p.getStart().compareTo(top.getStart()) > 0)
					top = p;
			}
			return top.getVenue();
		}
	}

	public String getTheaterInformation()
	{
		return theaterInformation;
	}

	/**
	 * A java based hack for JasperReports. The time lines are easier to display as collections.
	 * 
	 * @return a list of EventMappings (Events/PerformanceEvents) for this day ordered by time.
	 * @see Performance#getEventSequence()
	 * 
	 */
	@Transient
	public ArrayList<EventMapping> getTimeline()
	{
		return getTimeline(true, true, true);
	}

	/**
	 * <p>
	 * <em>Java based helper for JasperReports</em>
	 * </p>
	 * Return a list of all events based on the filters.
	 * 
	 * @param cast
	 *          whether to show crew only events
	 * @param crew
	 *          whether to show cast only events
	 * @param details
	 *          whether to show the details surrounding a performance
	 * @return a list of Events matching the fiterset.
	 */
	public ArrayList<EventMapping> getTimeline(boolean cast, boolean crew, boolean details)
	{
		if(isEmpty())
			return new ArrayList<EventMapping>();
		
		ArrayList<EventMapping> ret = new ArrayList<EventMapping>();
		if(crew && cast)
			ret.addAll(getEvents());
		else
			for(Event e : getEvents())
			{
				if(e.isCast() || e.isCrew())
				{
					if(e.isCast() && e.isCrew())
						ret.add(e);
					else
						if(e.isCast() && cast)
							ret.add(e);
						else
							if(e.isCrew() && crew)
								ret.add(e);
				}
				else
					ret.add(e);
			}

		if(details)
		{
			for(Performance p : getPerformances())
			{
				ret.addAll(p.getEventSequence());
				ret.add(p);
			}
		}
		else
			ret.addAll(getPerformances());

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
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();

		buf.append("[Day] ");
		buf.append("Date ");
		buf.append(date);

		return buf.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Day target)
	{	
		if(target == null)
			return 1;
		
		if(date == null && target.getDate() == null)
			return 0;
		
		return date.compareTo(target.date);
	}
}
