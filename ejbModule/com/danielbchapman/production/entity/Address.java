package com.danielbchapman.production.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * A simple embeddable unit that is an address. Be sure to return a new address on 
 * the getter or you can run into UI issues with JSF/Bean frameworks.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Jan 22, 2011 2011
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
@Embeddable
public class Address implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Column(length = 128)
  private String lineOne;
  @Column(length = 128)
  private String lineTwo;
  @Column(length = 128)
  private String contact;
  @Column(length = 20)
  private String zip;
  @Column(length = 40)
  private String city;
  @Column(length = 60)
  private String state;
  public String getLineOne()
  {
    return lineOne;
  }
  public void setLineOne(String lineOne)
  {
    this.lineOne = lineOne;
  }
  public String getLineTwo()
  {
    return lineTwo;
  }
  public void setLineTwo(String lineTwo)
  {
    this.lineTwo = lineTwo;
  }
  public String getContact()
  {
    return contact;
  }
  public void setContact(String contact)
  {
    this.contact = contact;
  }
  public String getZip()
  {
    return zip;
  }
  public void setZip(String zip)
  {
    this.zip = zip;
  }
  public String getCity()
  {
    return city;
  }
  public void setCity(String city)
  {
    this.city = city;
  }
  public String getState()
  {
    return state;
  }
  public void setState(String state)
  {
    this.state = state;
  }
}
