package com.danielbchapman.production.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * A simple embedded piece of contact information as a workaround for the
 * unimplemented Contact system.
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
public class EmbeddableContactInformation implements Serializable
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
  @Column(length = 128)
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
