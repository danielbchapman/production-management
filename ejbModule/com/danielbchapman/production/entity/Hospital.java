package com.danielbchapman.production.entity;

import javax.persistence.Entity;

@Entity
public class Hospital extends ContactableAndAddressable
{
	private static final long serialVersionUID = 1L;
	private String name;
	private City city;
	
	public City getCity()
	{
		return city;
	}
	public void setCity(City city)
	{
		this.city = city;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}
