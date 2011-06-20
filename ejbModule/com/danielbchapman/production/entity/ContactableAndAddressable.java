package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Lob;

/**
 * This is a mutant base entity that provides embedded contact and address information.
 * Java does not support multiple inheritance and this provides consistency
 * across the platform.
 * 
 * Please forgive this mutant design, it does save time on the Web side. <em> significant 
 * time which warrants this sub par design, sub objects require significant maintenance and
 * cause null pointer issues with the current Primefaces and JSF2.0 models.</em>
 *
 * @see Contactable
 * @see Addressable
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 20, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class ContactableAndAddressable
{
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
  
  @Column(length = 128)
  private String contactName;
  @Column(length = 128)
  private String phone;
  @Column(length = 128)
  private String fax;
  @Column(length = 128)
  private String cell;
  @Column(length = 128)
  private String email;
  @Lob
  private String contactNotes;
  public String getContactName()
  {
    return contactName;
  }
  public void setContactName(String contactName)
  {
    this.contactName = contactName;
  }
  public String getPhone()
  {
    return phone;
  }
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  public String getFax()
  {
    return fax;
  }
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  public String getCell()
  {
    return cell;
  }
  public void setCell(String cell)
  {
    this.cell = cell;
  }
  public String getEmail()
  {
    return email;
  }
  public void setEmail(String email)
  {
    this.email = email;
  }
  public String getContactNotes()
  {
    return contactNotes;
  }
  public void setContactNotes(String contactNotes)
  {
    this.contactNotes = contactNotes;
  }
}
