package com.danielbchapman.production.entity;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A Performance is a particular instance of a Production in a location. The
 * performance is created in the Scheduling and can be modified via a Venu.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 16, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class Performance extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date performanceTimeUTC;
	public Date getPerformanceTimeUTC()
	{
		return performanceTimeUTC;
	}
	public void setPerformanceTimeUTC(Date performanceTimeUTC)
	{
		this.performanceTimeUTC = performanceTimeUTC;
	}
	public Production getProduction()
	{
		return production;
	}
	public void setProduction(Production production)
	{
		this.production = production;
	}
	private Production production;
}
