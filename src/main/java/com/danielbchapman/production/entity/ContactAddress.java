package com.danielbchapman.production.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * A linked entity that provides one of many addresses for a particular contact.
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Jan 22, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Entity
public class ContactAddress extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	@Column(length = 40)
	private String city = "";
	@ManyToOne(targetEntity = Contact.class, cascade = { CascadeType.REFRESH })
	private Contact contact;
	@Column(length = 128)
	private String label = "";
	@Column(length = 128)
	private String lineOne = "";
	@Column(length = 128)
	private String lineTwo = "";
	@Column(length = 60)
	private String state = "";
	@Column(length = 20)
	private String zip = "";

	public String getCity()
	{
		return city;
	}

	/**
	 * @return the contact
	 */
	public Contact getContact()
	{
		return contact;
	}

	/**
	 * @return the first line of an address in a two line format.
	 */
	@Transient
	public String getFormattedAddressTwoLineA()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(getLineOne());
		if(getLineTwo() != null && !getLineTwo().isEmpty())
		{
			builder.append(", ");
			builder.append(getLineTwo());
		}

		return builder.toString();
	}

	/**
	 * @return the second line of an address in a two line format.
	 */
	@Transient
	public String getFormattedAddressTwoLineB()
	{
		StringBuilder builder = new StringBuilder();

		builder.append(getCity());
		builder.append(", ");
		builder.append(getState());
		builder.append(" ");
		builder.append(getZip());

		return builder.toString();
	}

	/**
	 * @return the label
	 */
	public String getLabel()
	{
		return label;
	}

	public String getLineOne()
	{
		return lineOne;
	}

	public String getLineTwo()
	{
		return lineTwo;
	}

	public String getState()
	{
		return state;
	}

	public String getZip()
	{
		return zip;
	}

	public void setCity(String city)
	{
		this.city = city;
	}

	/**
	 * @param contact
	 *          the contact to set
	 */
	public void setContact(Contact contact)
	{
		this.contact = contact;
	}

	/**
	 * @param label
	 *          the label to set
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	public void setLineOne(String lineOne)
	{
		this.lineOne = lineOne;
	}

	public void setLineTwo(String lineTwo)
	{
		this.lineTwo = lineTwo;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public void setZip(String zip)
	{
		this.zip = zip;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.BaseEntity#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(label);
		builder.append("] ");
		builder.append(lineOne);
		if(lineTwo != null && !lineTwo.isEmpty())
		{
			builder.append(", ");
			builder.append(lineTwo);
		}
		builder.append(" ");
		builder.append(city);
		builder.append(" ");
		builder.append(state);
		builder.append(" ");
		builder.append(zip);
		return builder.toString();
	}
}
