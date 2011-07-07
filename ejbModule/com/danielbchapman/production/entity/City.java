package com.danielbchapman.production.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;


/**
 * A city, like one that we live in.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 16, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Entity
public class City extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	private String country;
	@OneToMany(cascade={CascadeType.ALL},targetEntity=Hospital.class, fetch=FetchType.EAGER)
	private Collection<Hospital> hospitals;
	@OneToMany(cascade={CascadeType.ALL},targetEntity=Hotel.class, fetch=FetchType.EAGER)
	private Collection<Hotel> hotels;
	private String name;
	private Hospital selectedHospital;
	private Hotel selectedHotel;
	private String stateOrTerritory;
	private String taxiServiceAddress;
	private String taxiServiceName;
	private String taxiServicePhone;
	
	public String getCountry()
	{
		return country;
	}
	public Collection<Hospital> getHospitals()
	{
		return hospitals;
	}

	public Collection<Hotel> getHotels()
	{
		return hotels;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Hospital getSelectedHospital()
	{
		return selectedHospital;
	}
	
	public Hotel getSelectedHotel()
	{
		return selectedHotel;
	}
	
	public String getStateOrTerritory()
	{
		return stateOrTerritory;
	}
	
	public String getTaxiServiceAddress()
	{
		return taxiServiceAddress;
	}
	
	public String getTaxiServiceName()
	{
		return taxiServiceName;
	}
	
	public String getTaxiServicePhone()
	{
		return taxiServicePhone;
	}
	
	public void setCountry(String country)
	{
		this.country = country;
	}
	
	public void setHospitals(Collection<Hospital> hospitals)
	{
		this.hospitals = hospitals;
	}
	
	public void setHotels(Collection<Hotel> hotels)
	{
		this.hotels = hotels;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setSelectedHospital(Hospital selectedHospital)
	{
		this.selectedHospital = selectedHospital;
	}
	
	public void setSelectedHotel(Hotel selectedHotel)
	{
		this.selectedHotel = selectedHotel;
	}
	
	public void setStateOrTerritory(String stateOrTerritory)
	{
		this.stateOrTerritory = stateOrTerritory;
	}
	
	public void setTaxiServiceAddress(String taxiServiceAddress)
	{
		this.taxiServiceAddress = taxiServiceAddress;
	}
	
	public void setTaxiServiceName(String taxiServiceName)
	{
		this.taxiServiceName = taxiServiceName;
	}
	
	public void setTaxiServicePhone(String taxiServicePhone)
	{
		this.taxiServicePhone = taxiServicePhone;
	}
	
	/* (non-Javadoc)
	 * @see com.danielbchapman.production.entity.BaseEntity#toString()
	 */
	public String toString()
	{
		return name;
	}
	
	/**
	 * @return a combination of City + State/Territory  
	 * 
	 */
	public String toStringDetailed()
	{
		return name + ", " + stateOrTerritory; 
	}
}
