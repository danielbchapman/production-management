package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

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
@MappedSuperclass
public abstract class ContactableAndAddressable extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	@Column(length = 128)
  private String cell;
  @Column(length = 40)
  private String addressCity;
  @Column(length = 128)
  private String contact;

	@Column(length = 128)
  private String contactName;

	@Lob
  private String contactNotes;
  @Column(length = 128)
  private String email;
  @Column(length = 128)
  private String fax;
  @Column(length = 128)
  private String lineOne;
  @Column(length = 128)
  private String lineTwo;
  @Column(length = 128)
  private String phone;
  @Column(length = 60)
  private String state;
  @Column(length = 20)
  private String zip;
  public String getAddressCity()
	{
		return addressCity;
	}
  public String getCell()
  {
    return cell;
  }
  
  public String getContact()
  {
    return contact;
  }
  
  public String getContactName()
  {
    return contactName;
  }
  
  public String getContactNotes()
  {
    return contactNotes;
  }
  
  public String getEmail()
  {
    return email;
  }
  
  public String getFax()
  {
    return fax;
  }
  
  public String getLineOne()
  {
    return lineOne;
  }
  
  public String getLineTwo()
  {
    return lineTwo;
  }
  
  public String getPhone()
  {
    return phone;
  }
  
  public String getState()
  {
    return state;
  }
  
  public String getZip()
  {
    return zip;
  }
  
  public void setAddressCity(String addressCity)
	{
		this.addressCity = addressCity;
	}
  
  public void setCell(String cell)
  {
    this.cell = cell;
  }
  
  public void setContact(String contact)
  {
    this.contact = contact;
  }
  
  public void setContactName(String contactName)
  {
    this.contactName = contactName;
  }
  
  public void setContactNotes(String contactNotes)
  {
    this.contactNotes = contactNotes;
  }
  
  public void setEmail(String email)
  {
    this.email = email;
  }
  
  public void setFax(String fax)
  {
    this.fax = fax;
  }
  
  public void setLineOne(String lineOne)
  {
    this.lineOne = lineOne;
  }
  
  public void setLineTwo(String lineTwo)
  {
    this.lineTwo = lineTwo;
  }
  
  public void setPhone(String phone)
  {
    this.phone = phone;
  }
  
  public void setState(String state)
  {
    this.state = state;
  }
  
  public void setZip(String zip)
  {
    this.zip = zip;
  }
  
  @Transient
  public String getFullAddressNewLines()
  {
  	StringBuilder builder = new StringBuilder();
  	builder.append(lineOne);
  	builder.append('\n');
  	
  	if(lineTwo != null && lineTwo.length() > 0)
  	{
    	builder.append(lineTwo);
    	builder.append('\n');  		
  	}

  	builder.append(addressCity);
  	builder.append(' ');
  	builder.append(state);
  	builder.append(' ' );
  	builder.append(zip);
  	
  	return builder.toString();
  }
  
  @Transient
  public String getFullAddressSpaced()
  {
  	StringBuilder builder = new StringBuilder();
  	builder.append(lineOne);
  	builder.append(' ');
  	
  	if(lineTwo != null && lineTwo.length() > 0)
  	{
    	builder.append(lineTwo);
    	builder.append(' ');  		
  	}

  	builder.append(addressCity);
  	builder.append(' ');
  	builder.append(state);
  	builder.append(' ' );
  	builder.append(zip);
  	
  	return builder.toString();
  }
}
