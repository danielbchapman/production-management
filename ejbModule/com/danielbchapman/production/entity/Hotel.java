package com.danielbchapman.production.entity;

import javax.persistence.Entity;

/**
 * A hotel is simply as it states. A hotel somewhere.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 16, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Entity
public class Hotel extends ContactableAndAddressable
{
	private static final long serialVersionUID = 1L;

	private City city;
	private String name;
	private String notes;
	private Double rateDouble;
	private Double rateSingle;
	private Double taxAdditional;
	private Double taxRate;
	private String website;
	
	public City getCity()
	{
		return city;
	}
	public String getName()
	{
		return name;
	}
	public String getNotes()
	{
		return notes;
	}
	public Double getRateDouble()
	{
		if(rateDouble == null)
			rateDouble = 0D;
		
		return rateDouble;
	}
	public Double getRateSingle()
	{
		if(rateSingle == null)
			rateSingle = 0D;
		return rateSingle;
	}
	public Double getTaxAdditional()
	{
		if(taxAdditional == null)
			taxAdditional = 0D;
		return taxAdditional;
	}
	public Double getTaxRate()
	{
		if(taxRate == null)
			taxRate = 0D;
		return taxRate;
	}
	public String getWebsite()
	{
		return website;
	}
	public void setCity(City city)
	{
		this.city = city;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	public void setRateDouble(Double rateDouble)
	{
		this.rateDouble = rateDouble;
	}
	
	public void setRateSingle(Double rateSingle)
	{
		this.rateSingle = rateSingle;
	}
	public void setTaxAdditional(Double taxAdditional)
	{
		this.taxAdditional = taxAdditional;
	}
	public void setTaxRate(Double taxRate)
	{
		this.taxRate = taxRate;
	}
	public void setWebsite(String website)
	{
		this.website = website;
	}
}
