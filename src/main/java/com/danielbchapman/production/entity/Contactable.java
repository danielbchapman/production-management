package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Lob;
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
 * @see Addressable
 * @see ContactableAndAddressable
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 20, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@MappedSuperclass
public abstract class Contactable extends BaseEntity
{
  private static final long serialVersionUID = 1L;
  
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
