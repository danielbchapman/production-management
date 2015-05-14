package com.danielbchapman.production.entity;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import lombok.ToString;

/**
 * The abstract contact is a class that provides implementation for
 * 
 * @author dchapman
 * @since Sep 13, 2011
 * @copyright The Acting Company Sep 13, 2011 @link www.theactingcompany.org
 */
@MappedSuperclass
@ToString(
		callSuper = false, 
		doNotUseGetters=true,
		includeFieldNames=false, 
		exclude={"contactGroup", "addresses", "addressOne", "addressTwo", "addressThree"})
public class AbstractContact extends BaseEntity implements IContact
{
	private static final long serialVersionUID = 1L;

	/**
	 * A simple null/empty check.
	 * 
	 * @param s
	 *          the string to check
	 * @return true if null or empty
	 */
	protected static boolean isEmptyOrNull(String s)
	{
		if(s == null)
			return true;

		if(s.isEmpty())
			return true;

		return false;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = ContactAddress.class, mappedBy = "contact")
	private Collection<ContactAddress> addresses;
	@Transient
	private ContactAddress addressOne;
	@Transient
	private ContactAddress addressThree;
	@Transient
	private ContactAddress addressTwo;
	@Column(length = 128)
	private String cell = "";
	@ManyToOne(targetEntity = ContactGroup.class)
	private ContactGroup contactGroup;
	@Column(length = 128)
	private String email = "";
	@Column(length = 128)
	private String fax = "";
	@Column(length = 128)
	private String firstName = "";
	@Column(length = 128)
	private String lastName = "";
	@Lob
	private String notes = "";
	@Column(length = 128)
	private String phone = "";
	@Column(length = 128)
	private String position = "";
	@Column(length = 128)
	private String subGroup = "none";

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(IContact o)
	{
		return IContact.Methods.compare(this, o);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getAddresses()
	 */
	@Override
	public Collection<ContactAddress> getAddresses()
	{
		return addresses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getAddressesToHtmlDiv()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getAddressOne()
	 */
	@Override
	public ContactAddress getAddressOne()
	{
		if(addressOne == null)
			processAddresses();
		return addressOne;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getAddressThree()
	 */
	@Override
	public ContactAddress getAddressThree()
	{
		if(addressThree == null)
			processAddresses();
		return addressThree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getAddressTwo()
	 */
	@Override
	public ContactAddress getAddressTwo()
	{
		if(addressTwo == null)
			processAddresses();
		return addressTwo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getCell()
	 */
	@Override
	public String getCell()
	{
		return cell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getContactGroup()
	 */
	@Override
	public ContactGroup getContactGroup()
	{
		return contactGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getContactInformationToHtmlDiv()
	 */
	@Override
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getEmail()
	 */
	@Override
	public String getEmail()
	{
		return email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getFax()
	 */
	@Override
	public String getFax()
	{
		return fax;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getFirstName()
	 */
	@Override
	public String getFirstName()
	{
		return firstName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getFullName()
	 */
	@Override
	@Transient
	public String getFullName()
	{
		return getLastName() + ", " + getFirstName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getLastName()
	 */
	@Override
	public String getLastName()
	{
		return lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getNotes()
	 */
	@Override
	public String getNotes()
	{
		return notes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getPhone()
	 */
	@Override
	public String getPhone()
	{
		return phone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getPosition()
	 */
	@Override
	public String getPosition()
	{
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#getSubGroup()
	 */
	@Override
	public String getSubGroup()
	{
		return subGroup;
	}

	/**
	 * @param addresses
	 *          the addresses to set
	 */
	public void setAddresses(Collection<ContactAddress> addresses)
	{
		this.addresses = addresses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.entity.IContact2#setAddressOne(com.danielbchapman.production.
	 * entity.ContactAddress)
	 */
	@Override
	public void setAddressOne(ContactAddress addressOne)
	{
		this.addressOne = addressOne;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.entity.IContact2#setAddressThree(com.danielbchapman.production
	 * .entity.ContactAddress)
	 */
	@Override
	public void setAddressThree(ContactAddress addressThree)
	{
		this.addressThree = addressThree;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.entity.IContact2#setAddressTwo(com.danielbchapman.production.
	 * entity.ContactAddress)
	 */
	@Override
	public void setAddressTwo(ContactAddress addressTwo)
	{
		this.addressTwo = addressTwo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#setCell(java.lang.String)
	 */
	@Override
	public void setCell(String cell)
	{
		this.cell = cell;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.entity.IContact2#setContactGroup(com.danielbchapman.production
	 * .entity.ContactGroup)
	 */
	@Override
	public void setContactGroup(ContactGroup contactGroup)
	{
		this.contactGroup = contactGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email)
	{
		this.email = email;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#setFax(java.lang.String)
	 */
	@Override
	public void setFax(String fax)
	{
		this.fax = fax;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#setLastName(java.lang.String)
	 */
	@Override
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#setNotes(java.lang.String)
	 */
	@Override
	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#setPhone(java.lang.String)
	 */
	@Override
	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#setPosition(java.lang.String)
	 */
	@Override
	public void setPosition(String position)
	{
		this.position = position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#setSubGroup(java.lang.String)
	 */
	@Override
	public void setSubGroup(String subGroup)
	{
		this.subGroup = subGroup;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.IContact2#toHtml()
	 */
	@Override
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
		builder.append(getFirstName());
		builder.append(" ");
		builder.append(getLastName());
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

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see com.danielbchapman.production.entity.BaseEntity#toString()
//	 */
//	@Override
//	public String toString()
//	{
//		StringBuilder builder = new StringBuilder();
//
//		builder.append(super.toString());
//		builder.append(" Id:'");
//		builder.append(getId());
//		builder.append("' ");
//		builder.append(" Name:'");
//		builder.append(getFirstName());
//		builder.append(" ");
//		builder.append(getLastName());
//		builder.append("'");
//
//		return builder.toString();
//	}

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

	@SuppressWarnings("unused")
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
