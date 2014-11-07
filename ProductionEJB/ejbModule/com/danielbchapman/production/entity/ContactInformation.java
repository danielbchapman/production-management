package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: ContactInformation
 * 
 */
@Entity
public class ContactInformation implements Serializable
{

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date lastUpdated;
  private String information;
  @ManyToOne
  private Contact contact;
  
  public ContactInformation()
  {
    super();
  }
  
  /**
   * @return the contact
   */
  public Contact getContact()
  {
    return contact;
  }
  /**
   * @return the id
   */
  public Long getId()
  {
    return id;
  }
  /**
   * @return the information
   */
  public String getInformation()
  {
    return information;
  }
  /**
   * @return the lastUpdated
   */
  public Date getLastUpdated()
  {
    return lastUpdated;
  }
  /**
   * @param contact the contact to set
   */
  public void setContact(Contact contact)
  {
    this.contact = contact;
  }
  /**
   * @param id the id to set
   */
  public void setId(Long id)
  {
    this.id = id;
  }
  /**
   * @param information the information to set
   */
  public void setInformation(String information)
  {
    this.information = information;
  }
  /**
   * @param lastUpdated the lastUpdated to set
   */
  public void setLastUpdated(Date lastUpdated)
  {
    this.lastUpdated = lastUpdated;
  }

}
