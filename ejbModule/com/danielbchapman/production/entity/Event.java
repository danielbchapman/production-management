package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Event
 */
@Entity
public class Event implements Serializable
{

  private static final long serialVersionUID = 2L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(length = 1024)
  private String description;
  @Temporal(value = TemporalType.TIME)
  private Date start;
  @Temporal(TemporalType.TIME)
  private Date end;
  @JoinColumn
  private Department department;
  @ManyToOne
  private Day day;
  //Transient Variables that are Calculated and not persisted 
  @Transient
  private Date startUtc;
  @Transient
  private Date endUtc;

  public Event()
  {
    super();
  }

  /**
   * @return the day
   */
  public Day getDay()
  {
    return day;
  }

  public Department getDepartment()
  {
    return department;
  }

  /**
   * @return the description
   */
  public String getDescription()
  {
    return description;
  }

  /**
   * @return the id
   */
  public Long getId()
  {
    return id;
  }

  /**
   * @return the time
   */
  public Date getStart()
  {
    return start;
  }

  /**
   * @param day
   *          the day to set
   */
  public void setDay(Day day)
  {
    this.day = day;
  }

  public void setDepartment(Department department)
  {
    this.department = department;
  }

  /**
   * @param description
   *          the description to set
   */
  public void setDescription(String description)
  {
    this.description = description;
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
   * @param time
   *          the time to set
   */
  public void setStart(Date time)
  {
    start = time;
    startUtc = null;
  }

  public Date getEnd()
  {
    return end;
  }

  public void setEnd(Date end)
  {
    this.end = end;
    endUtc = null;
  }

  /**
   * Return a composite of the Day and Event Times
   * @return java.util.Date composite of Day.date && Event.Time  
   */
  @Transient
  public Date getUtcStart()
  {
    if(startUtc != null)
      return startUtc;
    
    if(day != null && day.getDate() != null && start != null)
      startUtc = composeUtcFromDay(day, start);
    else
      return start;
    
    return getUtcStart();
  }
  
  /**
   * Return a composite of the Day and Event Times
   * @return java.util.Date composite of Day.date && Event.Time  
   */
  @Transient
  public Date getUtcEnd()
  {
    if(endUtc != null)
      return endUtc;
    
    if(day != null && day.getDate() != null && end != null)
      endUtc = composeUtcFromDay(day, end);
    else
      return end;
    
    return getUtcEnd();    
  }
  
  private Date composeUtcFromDay(Day day, Date utcHour)
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
    buf.append(id);
    buf.append("' Start:'");
    buf.append(start);
    buf.append("' End:'");
    buf.append(end);
    buf.append("' Day:'");
    buf.append(day);
    buf.append("' Description:'");
    buf.append(description);

    return buf.toString();

  }
}
