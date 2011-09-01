package com.danielbchapman.production.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

/**
 * A simple prime entity that implements a set of contacts
 * 
 */
@Entity
public class Contact extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ContactAddress.class, mappedBy = "contact")
	private Collection<ContactAddress> addresses;
	@Transient
	private ContactAddress addressOne;
	@Transient
	private ContactAddress addressThree;
	@Transient
	private ContactAddress addressTwo;
	@Column(length = 128)
	private String cell;
	@ManyToOne(targetEntity = ContactGroup.class)
	private ContactGroup contactGroup;
	@Column(length = 128)
	private String email;
	@Column(length = 128)
	private String fax;
	@Column(length = 128)
	private String name;
	@Lob
	private String notes;

	@Column(length = 128)
	private String phone;

	@Column(length = 128)
	private String position;

	private String subGroup = "none";

	/**
	 * @return the addresses
	 */
	public Collection<ContactAddress> getAddresses()
	{
		return addresses;
	}

	/**
	 * Render the list of addresses as HTML.
	 * 
	 * @return a string in HTML
	 */
	@Transient
	public String getAddressesToHtmlDiv()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<div>");
		builder.append("<table>");

		if(addresses != null)
			for(ContactAddress a : addresses)
			{
				builder.append("<tr>");
				builder.append("<td>");
				builder.append("<i>");
				builder.append(a.getLabel());
				builder.append(": </i>");
				builder.append("</td>");
				builder.append("<td>");
				builder.append(a.getFormattedAddressTwoLineA());
				builder.append("</td>");
				builder.append("</tr>");

				builder.append("<tr>");
				builder.append("<td>");
				builder.append("</td>");
				builder.append("<td>");
				builder.append(a.getFormattedAddressTwoLineB());
				builder.append("</td>");
				builder.append("</tr>");
			}

		builder.append("</table>");
		builder.append("</div>");

		return builder.toString();
	}

	/**
	 * @return the addressOne
	 */
	public ContactAddress getAddressOne()
	{
		if(addressOne == null)
			processAddresses();
		return addressOne;
	}

	/**
	 * @return the addressThree
	 */
	public ContactAddress getAddressThree()
	{
		if(addressThree == null)
			processAddresses();
		return addressThree;
	}

	/**
	 * @return the addressTwo
	 */
	public ContactAddress getAddressTwo()
	{
		if(addressTwo == null)
			processAddresses();
		return addressTwo;
	}

	/**
	 * @return the cell
	 */
	public String getCell()
	{
		return cell;
	}

	/**
	 * @return the contactGroup
	 */
	public ContactGroup getContactGroup()
	{
		return contactGroup;
	}

	@Transient
	public String getContactInformationToHtmlDiv()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<div>");
		builder.append("<table>");

		if(!isEmptyOrNull(phone))
		{
			builder.append("<tr>");
			builder.append("<td>");
			builder.append("<i>");
			builder.append("Phone: ");
			builder.append("</i>");
			builder.append("</td>");
			builder.append("<td>");
			builder.append(phone);
			builder.append("</td>");
			builder.append("</tr>");
		}

		if(!isEmptyOrNull(cell))
		{
			builder.append("<tr>");
			builder.append("<td>");
			builder.append("<i>");
			builder.append("Cell: ");
			builder.append("</i>");
			builder.append("</td>");
			builder.append("<td>");
			builder.append(cell);
			builder.append("</td>");
			builder.append("</tr>");
		}

		if(!isEmptyOrNull(email))
		{
			builder.append("<tr>");
			builder.append("<td>");
			builder.append("<i>");
			builder.append("e-mail: ");
			builder.append("</i>");
			builder.append("</td>");
			builder.append("<td>");
			builder.append(email);
			builder.append("</td>");
			builder.append("</tr>");
		}

		if(!isEmptyOrNull(fax))
		{
			builder.append("<tr>");
			builder.append("<td>");
			builder.append("<i>");
			builder.append("Fax: ");
			builder.append("</i>");
			builder.append("</td>");
			builder.append("<td>");
			builder.append(fax);
			builder.append("</td>");
			builder.append("</tr>");
		}

		builder.append("</table>");
		builder.append("</div>");

		return builder.toString();
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return the fax
	 */
	public String getFax()
	{
		return fax;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the notes
	 */
	public String getNotes()
	{
		return notes;
	}

	/**
	 * @return the phone
	 */
	public String getPhone()
	{
		return phone;
	}

	/**
	 * @return the position
	 */
	public String getPosition()
	{
		return position;
	}

	/**
	 * @return the subGroup
	 */
	public String getSubGroup()
	{
		return subGroup;
	}

	/**
	 * @param addressOne
	 *          the addressOne to set
	 */
	public void setAddressOne(ContactAddress addressOne)
	{
		this.addressOne = addressOne;
	}

	/**
	 * @param addressThree
	 *          the addressThree to set
	 */
	public void setAddressThree(ContactAddress addressThree)
	{
		this.addressThree = addressThree;
	}

	/**
	 * @param addressTwo
	 *          the addressTwo to set
	 */
	public void setAddressTwo(ContactAddress addressTwo)
	{
		this.addressTwo = addressTwo;
	}

	/**
	 * @param cell
	 *          the cell to set
	 */
	public void setCell(String cell)
	{
		this.cell = cell;
	}

	/**
	 * @param contactGroup
	 *          the contactGroup to set
	 */
	public void setContactGroup(ContactGroup contactGroup)
	{
		this.contactGroup = contactGroup;
	}

	/**
	 * @param email
	 *          the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @param fax
	 *          the fax to set
	 */
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	/**
	 * @param name
	 *          the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param notes
	 *          the notes to set
	 */
	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	/**
	 * @param phone
	 *          the phone to set
	 */
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/**
	 * @param position
	 *          the position to set
	 */
	public void setPosition(String position)
	{
		this.position = position;
	}

	/**
	 * @param subGroup
	 *          the subGroup to set
	 */
	public void setSubGroup(String subGroup)
	{
		this.subGroup = subGroup;
	}

	/**
	 * @return an HTML representation of this object (for rendering to error messages etc...)
	 */
	public String toHtml()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("<div>");
		builder.append("<h3>");
		builder.append(Contact.class);
		builder.append(getId());
		builder.append("</h3>");

		builder.append("<ul>");
		builder.append("<li>");
		builder.append(name);
		builder.append("</li>");
		builder.append("<li>");
		builder.append(position);
		builder.append("</li>");
		builder.append("<li>");
		builder.append(notes);
		builder.append("</li>");
		builder.append("<li>");
		builder.append(contactGroup);
		builder.append("</li>");
		builder.append("<li>");
		builder.append(subGroup);
		builder.append("</li>");

		builder.append("</ul>");

		builder.append(getAddressesToHtmlDiv());
		builder.append(getContactInformationToHtmlDiv());

		builder.append("</div>");

		return builder.toString();
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

		builder.append(super.toString());
		builder.append(" Id:'");
		builder.append(getId());
		builder.append("' ");
		builder.append(" Name:'");
		builder.append(getName());
		builder.append("'");

		return builder.toString();
	}

	private boolean isEmptyOrNull(String s)
	{
		if(s == null)
			return true;

		if(s.isEmpty())
			return true;

		return false;
	}

	/**
	 * @param address
	 * @return a String that is an html representation of the address formatted as 2 lines.
	 */
	@Transient
	private void processAddresses()
	{
		int i = 0;
		if(addresses != null)
			for(ContactAddress add : addresses)
			{
				if(i == 0)
					addressOne = add;
				if(i == 1)
					addressTwo = add;
				if(i == 2)
					addressThree = add;

				if(i >= 3)
					break;
				++i;
			}
	}

	private String wrapViaTag(String value, String tag, StringBuilder builder)
	{
		builder.append("<");
		builder.append(tag);
		builder.append(">");

		builder.append(value);

		builder.append("</");
		builder.append(tag);
		builder.append(">");

		return builder.toString();
	}
}
