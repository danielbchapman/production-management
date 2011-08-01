package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.TimeZone;

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

/**
 * A simple wrapper for a week (primarily for a database grouping).
 * 
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"date", "production"}))
public class Week implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  
  @Temporal(value = TemporalType.DATE)
  private Date date;
  
  @OneToMany(mappedBy = "week", targetEntity = Day.class, fetch=FetchType.EAGER)
  private Collection<Day> days;
  
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date lastUpdated;
  
  @ManyToOne(targetEntity=Season.class, optional=false)
  private Season production;

  public Week()
  {
    super();
  }
  
  /**
   * @return the days
   */
  public Collection<Day> getDays()
  {
    return days;
  }

  /**
   * @param days
   *          the days to set
   */
  public void setDays(Collection<Day> days)
  {
    this.days = days;
  }

  /**
   * @return the lastUpdated
   */
  public Date getLastUpdated()
  {
    return lastUpdated;
  }

  /**
   * @param lastUpdated
   *          the lastUpdated to set
   */
  public void setLastUpdated(Date lastUpdated)
  {
    this.lastUpdated = lastUpdated;
  }

	public Season getProduction()
	{
		return production;
	}

	public void setProduction(Season production)
	{
		this.production = production;
	}

	public Long getId()
	{
		return id;
	}

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
	 * @return the associated Monday of this week. Null if not found  
	 * 
	 */
	@Transient
	public Day getMonday()
	{
		return getDay(Calendar.MONDAY);
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
	 * @return the associated Friday of this week. Null if not found  
	 * 
	 */
	@Transient
	public Day getFriday()
	{
		return getDay(Calendar.FRIDAY);
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
	/**
	 * @return the associated Sunday of this week. Null if not found  
	 * 
	 */
	@Transient
	public Day getSunday()
	{
		return getDay(Calendar.SUNDAY);
	}
	
	@Transient
	private Day getDay(int day)
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
}
