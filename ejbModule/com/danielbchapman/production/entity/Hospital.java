package com.danielbchapman.production.entity;

public class Hospital extends BaseEntity
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
	public String getPhoneNumber()
	{
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}
	private String name;
	private Address address;
	private String phoneNumber;
}
