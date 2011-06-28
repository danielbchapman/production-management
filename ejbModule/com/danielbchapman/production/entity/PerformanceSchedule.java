package com.danielbchapman.production.entity;

import javax.persistence.Entity;

/**
 * <p>
 * A performance schedule is a template that provides information to the 
 * calendar about a particular show. This show is then highlighted in uneditable 
 * events that appear on both the schedule and the printed calendars. This completely
 * minimizes the data that needs to be entered and provides information about
 * strike as well as the start of day etc...<br/><br/>
 * </p>
 * 
 * <p>
 * This complexity allows for very little data entry and allows for massive flexibility 
 * for the calls relating to performance times.
 * </p>
 * 
 * <em>ALL TIMES ARE IN ELAPSED MINUTES REPRESENTED BY INTEGERS</em>
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 23, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Entity
public class PerformanceSchedule extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	private int actorsEquityCall;
	private int crewCall;
	private int fightCall;
	private int houseCall;
	private String name;
	private int performanceLength;
	private Production production;
	private int strikeLength;

	/**
	 * Standard no argument constructor
	 */
	public PerformanceSchedule()
	{
	}
	
	/**
	 * Construct a performance schedule with a large
	 * amount of the information defaulted in based on 
	 * industry standards.
	 * @param defaults
	 */
	public PerformanceSchedule(boolean defaults)
	{
		actorsEquityCall = 30;
		crewCall = 90;
		fightCall = 45;
		houseCall = 30;
		name="[No Name]";
		performanceLength = 120;
		strikeLength = 120;
	}
	
	public int getActorsEquityCall()
	{
		return actorsEquityCall;
	}
	
	public int getCrewCall()
	{
		return crewCall;
	}
	
	public int getFightCall()
	{
		return fightCall;
	}

	public int getHouseCall()
	{
		return houseCall;
	}

	public String getName()
	{
		return name;
	}
	
	public int getPerformanceLength()
	{
		return performanceLength;
	}
	
	public Production getProduction()
	{
		return production;
	}
	
	public int getStrikeLength()
	{
		return strikeLength;
	}
	
	public void setActorsEquityCall(int actorsEquityCall)
	{
		this.actorsEquityCall = actorsEquityCall;
	}
	
	public void setCrewCall(int crewCall)
	{
		this.crewCall = crewCall;
	}
	
	public void setFightCall(int fightCall)
	{
		this.fightCall = fightCall;
	}
	
	public void setHouseCall(int houseCall)
	{
		this.houseCall = houseCall;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setPerformanceLength(int performanceLength)
	{
		this.performanceLength = performanceLength;
	}

	public void setProduction(Production production)
	{
		this.production = production;
	}

	public void setStrikeLength(int strikeLength)
	{
		this.strikeLength = strikeLength;
	} 
}
