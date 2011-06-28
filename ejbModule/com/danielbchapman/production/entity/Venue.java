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
	
	private City city;
	@Column(length = 128)
	private String name;
	@Column(length=256)
	private String prosceniumToBackWall;
	@Column(length=256)
	private String prosceniumWidth;
	@Column(length=256)
	private String prosceniumHeight;
	@Column(length=256)
	private String prosceniumToApron;
	@Column(length=256)
	private String apronWidth;
	@Column(length=256)
	private String washerDryer;
  @Column(length=256)
  private String dressingRoom;
  @Column(length=256)
  private String loadingDock;
  @Lob
  private String notes;  
  @Column
  private String houseCapacity;
	
	public Venue()
	{
		super();
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
