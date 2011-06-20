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
  private String name;
  @ManyToOne(targetEntity = ContactGroup.class)
  private ContactGroup group;
  private Long company;
  
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date lastUpdated;
  
  @OneToMany(mappedBy="contact",targetEntity=ContactInformation.class)
  private Collection<ContactInformation> contactInformation;
  
  @ManyToOne(targetEntity=Production.class, optional=false, fetch=FetchType.LAZY)
  private Production production;

  /**
   * @return the name
   */
  public String getName()
  {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name)
  {
    this.name = name;
  }

  /**
   * @return the group
   */
  public ContactGroup getGroup()
  {
    return group;
  }

  /**
   * @param group the group to set
   */
  public void setGroup(ContactGroup group)
  {
    this.group = group;
  }

  /**
   * @return the company
   */
  public Long getCompany()
  {
    return company;
  }

  /**
   * @param company the company to set
   */
  public void setCompany(Long company)
  {
    this.company = company;
  }

  /**
   * @return the lastUpdated
   */
  public Date getLastUpdated()
  {
    return lastUpdated;
  }

  /**
   * @param lastUpdated the lastUpdated to set
   */
  public void setLastUpdated(Date lastUpdated)
  {
    this.lastUpdated = lastUpdated;
  }

  /**
   * @return the contactInformation
   */
  public Collection<ContactInformation> getContactInformation()
  {
    return contactInformation;
  }

  /**
   * @param contactInformation the contactInformation to set
   */
  public void setContactInformation(
      Collection<ContactInformation> contactInformation)
  {
    this.contactInformation = contactInformation;
  }

	public Production getProduction()
	{
		return production;
	}

	public void setProduction(Production production)
	{
		this.production = production;
	}

}
