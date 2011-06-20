package com.danielbchapman.production.entity;

/**
 * A hotel is simply as it states. A hotel somewhere.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 16, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class Hotel extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public Address getAddress()
	{
		return address;
	}
	public void setAddress(Address address)
	{
		this.address = address;
	}
	public String getNotes()
	{
		return notes;
	}
	public void setNotes(String notes)
	{
		this.notes = notes;
	}
	public City getCity()
	{
		return city;
	}
	public void setCity(City city)
	{
		this.city = city;
	}
	private String name;
	private Address address;
	private String notes;
	private City city;
}
