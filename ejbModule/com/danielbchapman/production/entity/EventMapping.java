package com.danielbchapman.production.entity;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@MappedSuperclass
public class EventMapping extends BaseEntity implements Comparable<EventMapping>
{
	private static final long serialVersionUID = 2L;
  
  @Lob
  private String description;
  @Temporal(value = TemporalType.TIME)
  private Date start;
  @Temporal(TemporalType.TIME)
  private Date end;
  @ManyToOne
  private Day day;
  private boolean cast;
	private boolean crew;

  /**
   * @return the day
   */
  public Day getDay()
  {
    return day;
  }

	/**
   * @return the description
   */
  public String getDescription()
  {
    return description;
  }

  public Date getEnd()
  {
    return end;
  }

  /**
   * @return the time
   */
  public Date getStart()
  {
    return start;
  }

  /**
   * Return a composite of the Day and Event Times
   * @return java.util.Date composite of Day.date && Event.Time  
   */
  @Transient
  public Date getUtcEnd()
  { 
    if(day != null && day.getDate() != null && end != null)
      return composeUtcFromDay(day, end);
    else
      return end;
  }

  /**
   * Return a composite of the Day and Event Times
   * @return java.util.Date composite of Day.date && Event.Time  
   */
  @Transient
  public Date getUtcStart()
  { 
    if(day != null && day.getDate() != null && start != null)
      return composeUtcFromDay(day, start);
    else
      return start;
  }
  
  /**
   * @return <b>false</b> this is not a performance, the subclass will return true   
   */
  @Transient 
  public boolean isPerformance()
  {
  	return false;
  }
  
  /**
   * @return <b>true</b> if this entity is in the database<br /><b>false</b> if it has yet to be persisted  
   * 
   */
  @Transient 
  public boolean isPersisted()
  {
  	return getId() != null;
  }

  public boolean isCast()
	{
		return cast;
	}

  public boolean isCrew()
	{
		return crew;
	}

  public void setCast(boolean cast)
	{
		this.cast = cast;
	}
  
  public void setCrew(boolean crew)
	{
		this.crew = crew;
	}

	/**
   * @param day
   *          the day to set
   */
  public void setDay(Day day)
  {
    this.day = day;
  }

	/**
   * @param description
   *          the description to set
   */
  public void setDescription(String description)
  {
    this.description = description;
  }

	public void setEnd(Date end)
  {
    this.end = end;
  }

	/**
   * @param time
   *          the time to set
   */
  public void setStart(Date time)
  {
    start = time;
  }

	/*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
    StringBuilder buf = new StringBuilder();

    buf.append(super.toString());
    buf.append(" |");
    buf.append(" Id:'");
    buf.append(getId());
    buf.append("' Start:'");
    buf.append(start);
    buf.append("' End:'");
    buf.append(end);
    buf.append("' Day:'");
    buf.append(day);
    buf.append("' Description:'");
    buf.append(description);
    buf.append("' Cast:'");
    buf.append(cast);
    buf.append("' Crew:'");
    buf.append(crew);

    return buf.toString();

  }
  
  /**
   * Merge a data and a time together to create a valid "TIMESTAMP".
   * 
   * @param day
   * @param utcHour
   * @return a date indicating a real world value  
   * 
   */
  protected final Date composeUtcFromDay(Day day, Date utcHour)
  {
    Calendar composite = Calendar.getInstance();
    Calendar time = Calendar.getInstance();
    time.setTime(utcHour);
    
    //MERGE TIMES
    composite.setTime(day.getDate());
    composite.set(Calendar.HOUR_OF_DAY, time.get(Calendar.HOUR_OF_DAY));
    composite.set(Calendar.MINUTE, time.get(Calendar.MINUTE));
    composite.set(Calendar.SECOND, time.get(Calendar.SECOND));
    composite.set(Calendar.MILLISECOND, time.get(Calendar.MILLISECOND));
    
    return composite.getTime();
  }
  
	@Override
	public int compareTo(EventMapping o)
	{
		if(o == null)
			return 1;
		
		if(o.getStart() == null)
			return 1;
		
		if(getStart() == null)
			return -1;
		
		if(getStart().equals(o.getStart()))
			return 0;
		
		if(getStart().after(o.getStart()))
			return -1;
		
		return 1;
	}
}
