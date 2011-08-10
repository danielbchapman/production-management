package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * The PerformanceAdvance is a wrapper object that contains information about a 
 * particular venue in a particular year. This is a collection of information 
 * regarding the tour directly and the advance. Not about the venue itself. 
 * 
 * There is a fair amount of redundant information here as it is designed to 
 * present a check list when talking to a venue so that no details are missed. 
 * Permanent features should be updated and kept on file.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 20, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Entity
public class PerformanceAdvance extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	private boolean complete;
	@Lob
	private String contactInformation;
	private Day day;
	@Column(length=96)
	private String gaffTape;

	@Column(length=96)
	private String groundPlan;
	@Lob
	private String hospitality;
	@Lob
	private String lightingNotes;
	@Column(length=96)
	private String lightPlot;
	@Lob
	private String loadingDock;
	@Lob
	private String maskingNotes;
	private Performance performance;
	@Column(length=96)
	private String rigging;
	
	@Lob
	private String scenicNotes;
	@Column(length=96)
	private String section;
	@Column(length=96)
	private String shorePower;
	@Lob
	private String soundNotes;
	private boolean wardrobeIroningBoard;
	private boolean wardrobeLaundry;
	
	@Lob
	private String wardrobeNotes;
	private boolean wardrobeSteamer;
	
	public String getContactInformation()
	{
		return contactInformation;
	}

	public Day getDay()
	{
		return day;
	}

	public String getGaffTape()
	{
		return gaffTape;
	}

	public String getGroundPlan()
	{
		return groundPlan;
	}

	public String getHospitality()
	{
		return hospitality;
	}
	
	public String getLightingNotes()
	{
		return lightingNotes;
	}
	
	public String getLightPlot()
	{
		return lightPlot;
	}
	
	public String getLoadingDock()
	{
		return loadingDock;
	}
	
	public String getMaskingNotes()
	{
		return maskingNotes;
	}
	
	public Performance getPerformance()
	{
		return performance;
	}
	
	public String getRigging()
	{
		return rigging;
	}
	
	public String getScenicNotes()
	{
		return scenicNotes;
	}
	
	public String getSection()
	{
		return section;
	}
	
	public String getShorePower()
	{
		return shorePower;
	}
	
	public String getSoundNotes()
	{
		return soundNotes;
	}
	
	public String getWardrobeNotes()
	{
		return wardrobeNotes;
	}
	
	public boolean isComplete()
	{
		return complete;
	}
	
	public boolean isWardrobeIroningBoard()
	{
		return wardrobeIroningBoard;
	}
	public boolean isWardrobeLaundry()
	{
		return wardrobeLaundry;
	}
	
	public boolean isWardrobeSteamer()
	{
		return wardrobeSteamer;
	}
	
	public void setComplete(boolean complete)
	{
		this.complete = complete;
	}
	
	public void setContactInformation(String contactInformation)
	{
		this.contactInformation = contactInformation;
	}
	
	public void setDay(Day day)
	{
		this.day = day;
	}
	
	public void setGaffTape(String gaffTape)
	{
		this.gaffTape = gaffTape;
	} 
	public void setGroundPlan(String groundPlan)
	{
		this.groundPlan = groundPlan;
	}
	
	public void setHospitality(String hospitality)
	{
		this.hospitality = hospitality;
	}
	
	public void setLightingNotes(String lightingNotes)
	{
		this.lightingNotes = lightingNotes;
	}
	
	public void setLightPlot(String lightPlot)
	{
		this.lightPlot = lightPlot;
	}
	
	public void setLoadingDock(String loadingDock)
	{
		this.loadingDock = loadingDock;
	}
	
	public void setMaskingNotes(String maskingNotes)
	{
		this.maskingNotes = maskingNotes;
	}
	
	public void setPerformance(Performance performance)
	{
		this.performance = performance;
	}
	
	public void setRigging(String rigging)
	{
		this.rigging = rigging;
	}
	
	public void setScenicNotes(String scenicNotes)
	{
		this.scenicNotes = scenicNotes;
	}
	
	public void setSection(String section)
	{
		this.section = section;
	}
	
	
	public void setShorePower(String shorePower)
	{
		this.shorePower = shorePower;
	}
	
	public void setSoundNotes(String soundNotes)
	{
		this.soundNotes = soundNotes;
	}
	
	public void setWardrobeIroningBoard(boolean wardrobeIroningBoard)
	{
		this.wardrobeIroningBoard = wardrobeIroningBoard;
	}
	
	public void setWardrobeLaundry(boolean wardrobeLaundry)
	{
		this.wardrobeLaundry = wardrobeLaundry;
	}

	public void setWardrobeNotes(String wardrobeNotes)
	{
		this.wardrobeNotes = wardrobeNotes;
	}

	public void setWardrobeSteamer(boolean wardrobeSteamer)
	{
		this.wardrobeSteamer = wardrobeSteamer;
	}
}
