package com.danielbchapman.production.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * A simple entity that embeds the statistics for a particular venue
 *
 */
@Embeddable
public class VenueStatistics implements Serializable
{
	private static final long serialVersionUID = 1L;
	
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
	@Column(length=4096)
	private String statisticsNotes;
	@Column(length=256)
	private String washerDryer;
  @Column(length=256)
  private String dressingRoom;
  @Column(length=256)
  private String steamersIrons;
  @Column(length=256)
  private String loadingDock;
  @Column(length=256)
  private String showers;
  @Column(length=256)
  private String showNotes;
  
	public VenueStatistics()
	{
	}

	public String getProsceniumWidth()
	{
		return prosceniumWidth;
	}

	public void setProsceniumWidth(String prosceniumWidth)
	{
		this.prosceniumWidth = prosceniumWidth;
	}

	public String getProsceniumHeight()
	{
		return prosceniumHeight;
	}

	public void setProsceniumHeight(String prosceniumHeight)
	{
		this.prosceniumHeight = prosceniumHeight;
	}

	public String getApronWidth()
	{
		return apronWidth;
	}

	public void setApronWidth(String apronWidth)
	{
		this.apronWidth = apronWidth;
	}

  public String getStatisticsNotes()
  {
    return statisticsNotes;
  }

  public void setStatisticsNotes(String statisticsNotes)
  {
    this.statisticsNotes = statisticsNotes;
  }

  public String getWasherDryer()
  {
    return washerDryer;
  }

  public void setWasherDryer(String washerDryer)
  {
    this.washerDryer = washerDryer;
  }

  public String getDressingRoom()
  {
    return dressingRoom;
  }

  public void setDressingRoom(String dressingRoom)
  {
    this.dressingRoom = dressingRoom;
  }

  public String getSteamersIrons()
  {
    return steamersIrons;
  }

  public void setSteamersIrons(String steamersIrons)
  {
    this.steamersIrons = steamersIrons;
  }

  public String getLoadingDock()
  {
    return loadingDock;
  }

  public void setLoadingDock(String loadingDock)
  {
    this.loadingDock = loadingDock;
  }

  public String getProsceniumToApron()
  {
    return prosceniumToApron;
  }

  public void setProsceniumToApron(String prosceniumToApron)
  {
    this.prosceniumToApron = prosceniumToApron;
  }

  public String getProsceniumToBackWall()
  {
    return prosceniumToBackWall;
  }

  public void setProsceniumToBackWall(String prosceniumToBackWall)
  {
    this.prosceniumToBackWall = prosceniumToBackWall;
  }

  public String getShowers()
  {
    return showers;
  }

  public void setShowers(String showers)
  {
    this.showers = showers;
  }

  public String getShowNotes()
  {
    return showNotes;
  }

  public void setShowNotes(String showNotes)
  {
    this.showNotes = showNotes;
  }	
}
