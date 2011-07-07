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
}
