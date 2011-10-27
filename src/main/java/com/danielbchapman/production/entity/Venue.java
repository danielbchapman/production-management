package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A simple representation of a Venue
 * 
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@ToString(doNotUseGetters=true, callSuper=true)
public class Venue extends ContactableAndAddressable implements Comparable<Venue>
{
	private static final long serialVersionUID = 1L;

	@Column(length = 256)
	private String apronWidth = "";
	@Column(length = 256)
	private String boxOffice = "";
	@Column(length = 256)
	private String boxOfficeContact = "";
	private City city;
	@Column(length = 256)
	private String dressingRoom = "";
	@Column
	private String houseCapacity = "";
	@Column(length = 256)
	private String loadingDock = "";
	@Column(length = 128)
	private String name = "";
	@Lob
	private String notes = "";
	@Lob
	private String presenter = "";
	@Lob
	private String techicalOrProduction = "";
	@Lob
	private String education = "";
	@Column(length = 256)
	private String prosceniumHeight = "";
	@Column(length = 256)
	private String prosceniumToApron = "";
	@Column(length = 256)
	private String prosceniumToBackWall = "";
	@Column(length = 256)
	private String prosceniumWidth = "";
	private String showersForCrew = "";
	@Column(length = 256)
	private String steamerIron = "";
	@Column(length = 256)
	private String washerDryer = "";

	public Venue()
	{
		super();
	}
	
	/**
	 * @param a
	 * @param b
	 * @return a standard call of <code>(Comparable)a.compareTo(b);</code>
	 */
	public static int compareVenues(Venue a, Venue b)
	{
		if(a == null && b == null)
			return 0;
		
		if(a == null)
			return -1;
		
		if(b == null)
			return 1;
		
		if(a.getName() == null && b.getName() == null)
			return 0;
		
		if(a.getName() == null)
			return -1;
		
		if(b.getName() == null)
			return 1;
		
		int compare = a.getName().compareTo(b.getName()); 
		return  compare == 0 ? a.getId().compareTo(b.getId()) : compare;
	}
	
	/**
	 * @param a
	 * @param b
	 * @return a standard call of <code>(Comparable)a.compareTo(b);</code>
	 * with a sorting of State, City, Name
	 */
	public static int compareVenueByStateCityName(Venue a, Venue b)
	{
		if(a == null && b == null)
			return 0;
		
		if(a == null)
			return -1;
		
		if(b == null)
			return 1;
		
		
		if(a.getAddressState() == null && b.getAddressState() == null)
			return Venue.compareVenues(a,  b);
		
		if(a.getAddressCity() == null && b.getAddressCity() == null)
			return Venue.compareVenues(a, b);
		
		if(a.getAddressCity() == null)
			return -1;
		
		if(a.getAddressState() == null)
			return -1;
		
		int states = a.getAddressState().compareTo(b.getAddressState());
		
		if(states != 0)
			return states;
		
		int city = a.getAddressCity().compareTo(b.getAddressCity());
		
		if(city != 0)
			return city;
		
		return Venue.compareVenues(a, b);
	}

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(Venue o)
	{
		return compareVenues(this, o);
	}
	
	public String getDetailedDescription()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(getAddressState() == null ? "n/a" : getAddressState());
		builder.append("]");
		builder.append(" ");
		builder.append(getAddressCity() == null ? "n/a" : getAddressCity());
		builder.append(" | ");
		builder.append(getName());
		
		return builder.toString();
	}
}
