package com.danielbchapman.production.entity;

import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * The linked contact provides a delegate to a contact, but overrides the Group, sub-group and
 * Position title. This allows a single contact to be Propagated across a contact-sheet in multiple
 * ways.
 * 
 * @author dchapman
 * @since Sep 13, 2011
 * @copyright The Acting Company Sep 13, 2011 @link www.theactingcompany.org
 */
@Entity
public class LinkedContact extends BaseEntity implements IContact
{
	private static final long serialVersionUID = 1L;
	private Contact contact = new Contact();
	private ContactGroup contactGroup;
	@Column(length = 128)
	private String subGroup = "none";
	@Column(length = 128)
	private String position;

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

	/**
	 * @param obj
	 * @return
	 * @see com.danielbchapman.production.entity.BaseEntity#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		return contact.equals(obj);
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getAddresses()
	 */
	@Override
	public Collection<ContactAddress> getAddresses()
	{
		return contact.getAddresses();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getAddressesToHtmlDiv()
	 */
	@Override
	public String getAddressesToHtmlDiv()
	{
		return contact.getAddressesToHtmlDiv();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getAddressOne()
	 */
	@Override
	public ContactAddress getAddressOne()
	{
		return contact.getAddressOne();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getAddressThree()
	 */
	@Override
	public ContactAddress getAddressThree()
	{
		return contact.getAddressThree();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getAddressTwo()
	 */
	@Override
	public ContactAddress getAddressTwo()
	{
		return contact.getAddressTwo();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getCell()
	 */
	@Override
	public String getCell()
	{
		return contact.getCell();
	}

	/**
	 * @return the contact
	 */
	public Contact getContact()
	{
		return contact;
	}

	/**
	 * @return the contactGroup
	 */
	@Override
	public ContactGroup getContactGroup()
	{
		return contactGroup;
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getContactInformationToHtmlDiv()
	 */
	@Override
	public String getContactInformationToHtmlDiv()
	{
		return contact.getContactInformationToHtmlDiv();
	}

	/**
	 * @return the displayname for this link indicating it is a link.
	 */
	@Transient
	public String getDisplayName()
	{
		StringBuilder builder = new StringBuilder();
		builder.append(getLastName());
		builder.append(", ");
		builder.append(getFirstName());
		builder.append(" [LINK");
		builder.append("] ");
		builder.append(getPosition());
		return builder.toString();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getEmail()
	 */
	@Override
	public String getEmail()
	{
		return contact.getEmail();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getFax()
	 */
	@Override
	public String getFax()
	{
		return contact.getFax();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getFirstName()
	 */
	@Override
	public String getFirstName()
	{
		return contact.getFirstName();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getFullName()
	 */
	@Override
	public String getFullName()
	{
		return getDisplayName();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getLastName()
	 */
	@Override
	public String getLastName()
	{
		return contact.getLastName();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getNotes()
	 */
	@Override
	public String getNotes()
	{
		return contact.getNotes();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#getPhone()
	 */
	@Override
	public String getPhone()
	{
		return contact.getPhone();
	}

	/**
	 * @return the position
	 */
	@Override
	public String getPosition()
	{
		return position;
	}

	/**
	 * @return the subGroup
	 */
	@Override
	public String getSubGroup()
	{
		return subGroup;
	}

	/**
	 * @return
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return contact.hashCode();
	}

	/**
	 * @param addressOne
	 * @see com.danielbchapman.production.entity.AbstractContact#setAddressOne(com.danielbchapman.production.entity.ContactAddress)
	 */
	@Override
	public void setAddressOne(ContactAddress addressOne)
	{
		contact.setAddressOne(addressOne);
	}

	/**
	 * @param addressThree
	 * @see com.danielbchapman.production.entity.AbstractContact#setAddressThree(com.danielbchapman.production.entity.ContactAddress)
	 */
	@Override
	public void setAddressThree(ContactAddress addressThree)
	{
		contact.setAddressThree(addressThree);
	}

	/**
	 * @param addressTwo
	 * @see com.danielbchapman.production.entity.AbstractContact#setAddressTwo(com.danielbchapman.production.entity.ContactAddress)
	 */
	@Override
	public void setAddressTwo(ContactAddress addressTwo)
	{
		contact.setAddressTwo(addressTwo);
	}

	/**
	 * @param cell
	 * @see com.danielbchapman.production.entity.AbstractContact#setCell(java.lang.String)
	 */
	@Override
	public void setCell(String cell)
	{
		contact.setCell(cell);
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
	 * @param contactGroup
	 *          the contactGroup to set
	 */
	@Override
	public void setContactGroup(ContactGroup contactGroup)
	{
		this.contactGroup = contactGroup;
	}

	/**
	 * @param email
	 * @see com.danielbchapman.production.entity.AbstractContact#setEmail(java.lang.String)
	 */
	@Override
	public void setEmail(String email)
	{
		contact.setEmail(email);
	}

	/**
	 * @param fax
	 * @see com.danielbchapman.production.entity.AbstractContact#setFax(java.lang.String)
	 */
	@Override
	public void setFax(String fax)
	{
		contact.setFax(fax);
	}

	/**
	 * @param firstName
	 * @see com.danielbchapman.production.entity.AbstractContact#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(String firstName)
	{
		contact.setFirstName(firstName);
	}

	/**
	 * @param lastName
	 * @see com.danielbchapman.production.entity.AbstractContact#setLastName(java.lang.String)
	 */
	@Override
	public void setLastName(String lastName)
	{
		contact.setLastName(lastName);
	}

	/**
	 * @param notes
	 * @see com.danielbchapman.production.entity.AbstractContact#setNotes(java.lang.String)
	 */
	@Override
	public void setNotes(String notes)
	{
		contact.setNotes(notes);
	}

	/**
	 * @param phone
	 * @see com.danielbchapman.production.entity.AbstractContact#setPhone(java.lang.String)
	 */
	@Override
	public void setPhone(String phone)
	{
		contact.setPhone(phone);
	}

	/**
	 * @param position
	 *          the position to set
	 */
	@Override
	public void setPosition(String position)
	{
		this.position = position;
	}

	/**
	 * @param subGroup
	 *          the subGroup to set
	 */
	@Override
	public void setSubGroup(String subGroup)
	{
		this.subGroup = subGroup;
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#toHtml()
	 */
	@Override
	public String toHtml()
	{
		return contact.toHtml();
	}

	/**
	 * @return
	 * @see com.danielbchapman.production.entity.AbstractContact#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("[Linked Contact]");
		builder.append(getPosition());
		builder.append(" | ");
		builder.append(getContactGroup());
		builder.append("\n");
		builder.append(contact.toString());

		return builder.toString();
	}
}
