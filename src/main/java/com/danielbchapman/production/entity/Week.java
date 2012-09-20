package com.danielbchapman.production.entity;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.theactingcompany.persistence.Indentifiable;

/**
 * A simple wrapper for a week (primarily for a database grouping).
 * 
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "date", "season" }))
public class Week implements Indentifiable
{
	private static final long serialVersionUID = 1L;

	public static Date getDate(int day, Date start)
	{
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
		cal.setTime(start);
		cal.add(Calendar.DAY_OF_MONTH, day);
		return cal.getTime();
	}

	@Temporal(value = TemporalType.DATE)
	private Date date;

	@OneToMany(mappedBy = "week", targetEntity = Day.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<Day> days;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdated;

	@ManyToOne(targetEntity = Season.class, optional = false)
	private Season season;

	public Week()
	{
		super();
	}

	public Date getDate()
	{
		return date;
	}

	/**
	 * @return the days
	 */
	public Collection<Day> getDays()
	{
		return days;
	}

	/**
	 * @return the associated Friday of this week. Null if not found
	 * 
	 */
	@Transient
	public Day getFriday()
	{
		return getDay(Calendar.FRIDAY);
	}

	@Override
	public Long getId()
	{
		return id;
	}

	/**
	 * @return the lastUpdated
	 */
	public Date getLastUpdated()
	{
		return lastUpdated;
	}

	/**
	 * @return the associated Monday of this week. Null if not found
	 * 
	 */
	@Transient
	public Day getMonday()
	{
		return getDay(Calendar.MONDAY);
	}

	/**
	 * @return the associated Saturday of this week. Null if not found
	 * 
	 */
	@Transient
	public Day getSaturday()
	{
		return getDay(Calendar.SATURDAY);
	}

	public Season getSeason()
	{
		return season;
	}

	/**
	 * @return the associated Sunday of this week. Null if not found
	 * 
	 */
	@Transient
	public Day getSunday()
	{
		return getDay(Calendar.SUNDAY);
	}

	/**
	 * @return the associated Thursday of this week. Null if not found
	 * 
	 */
	@Transient
	public Day getThursday()
	{
		return getDay(Calendar.THURSDAY);
	}

	/**
	 * @return the associated Tuesday of this week. Null if not found
	 * 
	 */
	@Transient
	public Day getTuesday()
	{
		return getDay(Calendar.TUESDAY);
	}

	/**
	 * @return the associated Wednesday of this week. Null if not found
	 * 
	 */
	@Transient
	public Day getWednesday()
	{
		return getDay(Calendar.WEDNESDAY);
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	/**
	 * @param days
	 *          the days to set
	 */
	public void setDays(Collection<Day> days)
	{
		this.days = days;
	}

	@Override
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @param lastUpdated
	 *          the lastUpdated to set
	 */
	public void setLastUpdated(Date lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}

	public void setSeason(Season season)
	{
		this.season = season;
	}

	/**
	 * @param day
	 *          the amount of days to move forward/backward from this point.
	 * @return the week date plus the day value eg Monday + X;
	 */
	@Transient
	public Day getDay(int day)
	{
		if(days == null)
			return null;

		for(Day d : days)
			if(d == null || d.getDate() == null)
				return null;
			else
			{
				Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
				cal.setTime(d.getDate());
				if(cal.get(Calendar.DAY_OF_WEEK) == day)
					return d;
			}

		return null;
	}
	
	@Transient
	public Week getThis()
	{
		return this;
	}
}
