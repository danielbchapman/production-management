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
}
