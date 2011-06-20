package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * This is a mutant base entity that provides embedded contact
 * Java does not support multiple inheritance and this provides consistency
 * across the platform.
 * 
 * Please forgive this mutant design, it does save time on the Web side. <em> significant 
 * time which warrants this sub par design, sub objects require significant maintenance and
 * cause null pointer issues with the current Primefaces and JSF2.0 models.</em>
 * 
 * @see Contactable
 * @see ContactableAndAddressable
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 20, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@MappedSuperclass
public abstract class Addressable extends BaseEntity
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
