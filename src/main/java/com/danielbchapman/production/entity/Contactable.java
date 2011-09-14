package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;

/**
 * This is a mutant base entity that provides embedded contact Java does not support multiple
 * inheritance and this provides consistency across the platform.
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
public abstract class Contactable extends BaseEntity implements IContactable
{
	private static final long serialVersionUID = 1L;

	@Column(length = 128)
	private String cell;
	@Column(length = 128)
	private String contactName;
	@Lob
	private String contactNotes;
	@Column(length = 128)
	private String contactPosition;
	@Column(length = 128)
	private String email;
	@Column(length = 128)
	private String fax;
	@Column(length = 128)
	private String phone;

	@Override
	public String getCell()
	{
		return cell;
	}

	@Override
	public String getContactName()
	{
		return contactName;
	}

	@Override
	public String getContactNotes()
	{
		return contactNotes;
	}

	/**
	 * @return the contactPosition
	 */
	@Override
	public String getContactPosition()
	{
		return contactPosition;
	}

	@Override
	public String getEmail()
	{
		return email;
	}

	@Override
	public String getFax()
	{
		return fax;
	}

	@Override
	public String getPhone()
	{
		return phone;
	}

	@Override
	public void setCell(String cell)
	{
		this.cell = cell;
	}

	@Override
	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

	@Override
	public void setContactNotes(String contactNotes)
	{
		this.contactNotes = contactNotes;
	}

	/**
	 * @param contactPosition
	 *          the contactPosition to set
	 */
	@Override
	public void setContactPosition(String contactPosition)
	{
		this.contactPosition = contactPosition;
	}

	@Override
	public void setEmail(String email)
	{
		this.email = email;
	}

	@Override
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	@Override
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

}
