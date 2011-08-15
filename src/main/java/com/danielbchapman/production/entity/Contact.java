package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

/**
 * A simple prime entity that implements a set of contacts
 * 
 */
@Entity
public class Contact extends BaseEntity
{

  private static final long serialVersionUID = 1L;
  private Long company;
  @OneToMany(mappedBy="contact",targetEntity=ContactInformation.class)
  private Collection<ContactInformation> contactInformation;
  @ManyToOne(targetEntity = ContactGroup.class)
  private ContactGroup group;
  
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date lastUpdated;
  
  private String name;
  
  @ManyToOne(targetEntity=Season.class, optional=false, fetch=FetchType.LAZY)
  private Season season;

  /**
   * @return the company
   */
  public Long getCompany()
  {
    return company;
  }

	/**
   * @return the contactInformation
   */
  public Collection<ContactInformation> getContactInformation()
  {
    return contactInformation;
  }

	/**
   * @return the group
   */
  public ContactGroup getGroup()
  {
    return group;
  }

  /**
   * @return the lastUpdated
   */
  public Date getLastUpdated()
  {
    return lastUpdated;
  }

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  public Season getSeason()
	{
		return season;
	}

  /**
   * @param company the company to set
   */
  public void setCompany(Long company)
  {
    this.company = company;
  }

  /**
   * @param contactInformation the contactInformation to set
   */
  public void setContactInformation(
      Collection<ContactInformation> contactInformation)
  {
    this.contactInformation = contactInformation;
  }

  /**
   * @param group the group to set
   */
  public void setGroup(ContactGroup group)
  {
    this.group = group;
  }

  /**
   * @param lastUpdated the lastUpdated to set
   */
  public void setLastUpdated(Date lastUpdated)
  {
    this.lastUpdated = lastUpdated;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }

  public void setSeason(Season season)
	{
		this.season = season;
	}

}
