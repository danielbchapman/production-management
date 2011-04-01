package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Collection;

import javax.persistence.Column;
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
  
//  @OneToMany(mappedBy = "week", targetEntity = Day.class)
//  private Collection<Day> days;
  
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date lastUpdated;
  
  @ManyToOne(targetEntity=Production.class, optional=false)
  private Production production;

  public Week()
  {
    super();
  }
//  
//  /**
//   * @return the days
//   */
//  public Collection<Day> getDays()
//  {
//    return days;
//  }
//
//  /**
//   * @param days
//   *          the days to set
//   */
//  public void setDays(Collection<Day> days)
//  {
//    this.days = days;
//  }

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

	public Production getProduction()
	{
		return production;
	}

	public void setProduction(Production production)
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

}
