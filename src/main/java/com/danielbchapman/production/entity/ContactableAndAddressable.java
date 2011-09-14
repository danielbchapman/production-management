package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * This is a mutant base entity that provides embedded contact and address information. Java does
 * not support multiple inheritance and this provides consistency across the platform.
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
public abstract class ContactableAndAddressable extends BaseEntity implements IContactable,
		IAddressable
{
	private static final long serialVersionUID = 1L;

	@Column(length = 40)
	private String addressCity;
	@Column(length = 128)
	private String addressLineOne;
	@Column(length = 128)
	private String addressLineTwo;
	@Column(length = 60)
	private String addressState;
	@Column(length = 20)
	private String addressZip;
	@Column(length = 128)
	private String cell;
	@Column(length = 128)
	private String contact;
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

	/**
	 * @return the addressCity
	 */
	@Override
	public String getAddressCity()
	{
		return addressCity;
	}

	/**
	 * @return the addressLineOne
	 */
	@Override
	public String getAddressLineOne()
	{
		return addressLineOne;
	}

	/**
	 * @return the addressLineTwo
	 */
	@Override
	public String getAddressLineTwo()
	{
		return addressLineTwo;
	}

	/**
	 * @return the addressState
	 */
	@Override
	public String getAddressState()
	{
		return addressState;
	}

	/**
	 * @return the addressZip
	 */
	@Override
	public String getAddressZip()
	{
		return addressZip;
	}

	@Transient
	public String getAsHtml()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("<div>");
		builder.append("Not implemented");
		builder.append("</div>");
		return builder.toString();
	}

	/**
	 * @return the cell
	 */
	@Override
	public String getCell()
	{
		return cell;
	}

	/**
	 * @return the contact
	 */
	@Override
	public String getContact()
	{
		return contact;
	}

	/**
	 * @return the contactName
	 */
	@Override
	public String getContactName()
	{
		return contactName;
	}

	/**
	 * @return the contactNotes
	 */
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

	/**
	 * @return the email
	 */
	@Override
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return the fax
	 */
	@Override
	public String getFax()
	{
		return fax;
	}

	@Transient
	public String getFullAddressNewLines()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(addressLineOne);
		builder.append('\n');

		if(addressLineTwo != null && addressLineTwo.length() > 0)
		{
			builder.append(addressLineTwo);
			builder.append('\n');
		}

		builder.append(addressCity);
		builder.append(' ');
		builder.append(addressState);
		builder.append(' ');
		builder.append(addressZip);

		return builder.toString();
	}

	@Transient
	public String getFullAddressSpaced()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(addressLineOne);
		builder.append(' ');

		if(addressLineTwo != null && addressLineTwo.length() > 0)
		{
			builder.append(addressLineTwo);
			builder.append(' ');
		}

		builder.append(addressCity);
		builder.append(' ');
		builder.append(addressState);
		builder.append(' ');
		builder.append(addressZip);

		return builder.toString();
	}

	/**
	 * @return the phone
	 */
	@Override
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @param addressCity
	 *          the addressCity to set
	 */
	@Override
	public void setAddressCity(String addressCity)
	{
		this.addressCity = addressCity;
	}

	/**
	 * @param addressLineOne
	 *          the addressLineOne to set
	 */
	@Override
	public void setAddressLineOne(String addressLineOne)
	{
		this.addressLineOne = addressLineOne;
	}

	/**
	 * @param addressLineTwo
	 *          the addressLineTwo to set
	 */
	@Override
	public void setAddressLineTwo(String addressLineTwo)
	{
		this.addressLineTwo = addressLineTwo;
	}

	/**
	 * @param addressState
	 *          the addressState to set
	 */
	@Override
	public void setAddressState(String addressState)
	{
		this.addressState = addressState;
	}

	/**
	 * @param addressZip
	 *          the addressZip to set
	 */
	@Override
	public void setAddressZip(String addressZip)
	{
		this.addressZip = addressZip;
	}

	/**
	 * @param cell
	 *          the cell to set
	 */
	@Override
	public void setCell(String cell)
	{
		this.cell = cell;
	}

	/**
	 * @param contact
	 *          the contact to set
	 */
	@Override
	public void setContact(String contact)
	{
		this.contact = contact;
	}

	/**
	 * @param contactName
	 *          the contactName to set
	 */
	@Override
	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

	/**
	 * @param contactNotes
	 *          the contactNotes to set
	 */
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

	/**
	 * @param email
	 *          the email to set
	 */
	@Override
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @param fax
	 *          the fax to set
	 */
	@Override
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	/**
	 * @param phone
	 *          the phone to set
	 */
	@Override
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

}
