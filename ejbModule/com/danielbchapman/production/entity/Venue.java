package com.danielbchapman.production.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * A simple representation of a Venue
 * 
 */
@Entity
public class Venue extends BaseEntity
{
	private VenueStatistics venueStatistics;
	@ManyToOne(targetEntity=Company.class)
	private Company company;
	@Column(length = 40)
	private String venueUsername;
	@Column(length = 40)
	private String venuePassword;
	@Column(length = 40)
	private String venueRole;
	
	private Address address;
	private EmbeddableContactInformation contact;
	
	private String production;
	@Temporal(value = TemporalType.DATE)
	private Date productionDate;
	@Temporal(value = TemporalType.TIME)
	private Date productionTime;
	private Boolean advanceComplete;
  @Column(length=256)
  private String hospitality;
	
	@Column(length = 40)
	private String milageFromPrevious;
	
	@Column(length = 128)
	private String name;
	public Venue()
	{
		super();
	}

	public VenueStatistics getVenueStatistics()
	{
	  if(venueStatistics == null)
	    venueStatistics = new VenueStatistics();
		return venueStatistics;
	}

	public void setVenueStatistics(VenueStatistics venueStatistics)
	{
		this.venueStatistics = venueStatistics;
	}

  public Company getCompany()
  {
    return company;
  }

  public void setCompany(Company company)
  {
    this.company = company;
  }

  public String getVenueUsername()
  {
    return venueUsername;
  }

  public void setVenueUsername(String venueUsername)
  {
    this.venueUsername = venueUsername;
  }

  public String getVenuePassword()
  {
    return venuePassword;
  }

  public void setVenuePassword(String venuePassword)
  {
    this.venuePassword = venuePassword;
  }

  public String getVenueRole()
  {
    return venueRole;
  }

  public void setVenueRole(String venueRole)
  {
    this.venueRole = venueRole;
  }

  public Address getAddress()
  {
    if(address == null)
      address = new Address();
    return address;
  }

  public void setAddress(Address address)
  {
    if(address == null)
      address = new Address();
    this.address = address;
  }

  public EmbeddableContactInformation getContact()
  {
    if(contact == null)
      contact = new EmbeddableContactInformation();
    
    return contact;
  }

  public void setContact(EmbeddableContactInformation contact)
  {
    if(contact == null)
      contact = new EmbeddableContactInformation();
    
    this.contact = contact;
  }

  public String getProduction()
  {
    return production;
  }

  public void setProduction(String production)
  {
    this.production = production;
  }

  public Date getProductionDate()
  {
    return productionDate;
  }

  public void setProductionDate(Date productionDate)
  {
    this.productionDate = productionDate;
  }

  public Date getProductionTime()
  {
    return productionTime;
  }

  public void setProductionTime(Date productionTime)
  {
    this.productionTime = productionTime;
  }

  public Boolean getAdvanceComplete()
  {
    if(advanceComplete == null)
      advanceComplete = Boolean.FALSE;
    return advanceComplete;
  }

  public void setAdvanceComplete(Boolean advanceComplete)
  {
    this.advanceComplete = advanceComplete;
  }

  public String getMilageFromPrevious()
  {
    return milageFromPrevious;
  }

  public void setMilageFromPrevious(String milageFromPrevious)
  {
    this.milageFromPrevious = milageFromPrevious;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getHospitality()
  {
    return hospitality;
  }

  public void setHospitality(String hospitality)
  {
    this.hospitality = hospitality;
  }
}
