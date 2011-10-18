package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * A simple representation of a Venue
 * 
 */
@Entity
public class Venue extends ContactableAndAddressable
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

	public String getApronWidth()
	{
		return apronWidth;
	}

	public String getBoxOffice()
	{
		return boxOffice;
	}

	public String getBoxOfficeContact()
	{
		return boxOfficeContact;
	}

	public City getCity()
	{
		return city;
	}

	public String getDressingRoom()
	{
		return dressingRoom;
	}

	public String getHouseCapacity()
	{
		return houseCapacity;
	}

	public String getLoadingDock()
	{
		return loadingDock;
	}

	public String getName()
	{
		return name;
	}

	public String getNotes()
	{
		return notes;
	}

	public String getProsceniumHeight()
	{
		return prosceniumHeight;
	}

	public String getProsceniumToApron()
	{
		return prosceniumToApron;
	}

	public String getProsceniumToBackWall()
	{
		return prosceniumToBackWall;
	}

	public String getProsceniumWidth()
	{
		return prosceniumWidth;
	}

	public String getShowersForCrew()
	{
		return showersForCrew;
	}

	public String getSteamerIron()
	{
		return steamerIron;
	}

	public String getWasherDryer()
	{
		return washerDryer;
	}

	public void setApronWidth(String apronWidth)
	{
		this.apronWidth = apronWidth;
	}

	public void setBoxOffice(String boxOffice)
	{
		this.boxOffice = boxOffice;
	}

	public void setBoxOfficeContact(String boxOfficeContact)
	{
		this.boxOfficeContact = boxOfficeContact;
	}

	public void setCity(City city)
	{
		this.city = city;
	}

	public void setDressingRoom(String dressingRoom)
	{
		this.dressingRoom = dressingRoom;
	}

	public void setHouseCapacity(String houseCapacity)
	{
		this.houseCapacity = houseCapacity;
	}

	public void setLoadingDock(String loadingDock)
	{
		this.loadingDock = loadingDock;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public void setProsceniumHeight(String prosceniumHeight)
	{
		this.prosceniumHeight = prosceniumHeight;
	}

	public void setProsceniumToApron(String prosceniumToApron)
	{
		this.prosceniumToApron = prosceniumToApron;
	}

	public void setProsceniumToBackWall(String prosceniumToBackWall)
	{
		this.prosceniumToBackWall = prosceniumToBackWall;
	}

	public void setProsceniumWidth(String prosceniumWidth)
	{
		this.prosceniumWidth = prosceniumWidth;
	}

	public void setShowersForCrew(String showersForCrew)
	{
		this.showersForCrew = showersForCrew;
	}

	public void setSteamerIron(String steamerIron)
	{
		this.steamerIron = steamerIron;
	}

	public void setWasherDryer(String washerDryer)
	{
		this.washerDryer = washerDryer;
	}
}
