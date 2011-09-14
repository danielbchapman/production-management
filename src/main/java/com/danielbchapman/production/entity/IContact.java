package com.danielbchapman.production.entity;

import java.util.Collection;

import javax.persistence.Transient;

public interface IContact extends Comparable<IContact>
{

	/**
	 * @return the addresses
	 */
	public abstract Collection<ContactAddress> getAddresses();

	/**
	 * Render the list of addresses as HTML.
	 * 
	 * @return a string in HTML
	 */
	@Transient
	public abstract String getAddressesToHtmlDiv();

	/**
	 * @return the addressOne
	 */
	public abstract ContactAddress getAddressOne();

	/**
	 * @return the addressThree
	 */
	public abstract ContactAddress getAddressThree();

	/**
	 * @return the addressTwo
	 */
	public abstract ContactAddress getAddressTwo();

	/**
	 * @return the cell
	 */
	public abstract String getCell();

	/**
	 * @return the contactGroup
	 */
	public abstract ContactGroup getContactGroup();

	@Transient
	public abstract String getContactInformationToHtmlDiv();

	/**
	 * @return the email
	 */
	public abstract String getEmail();

	/**
	 * @return the fax
	 */
	public abstract String getFax();

	/**
	 * @return the firstName
	 */
	public abstract String getFirstName();

	/**
	 * @return the proper full name (a shortcut for display).
	 */
	@Transient
	public abstract String getFullName();

	/**
	 * @return the lastName
	 */
	public abstract String getLastName();

	/**
	 * @return the notes
	 */
	public abstract String getNotes();

	/**
	 * @return the phone
	 */
	public abstract String getPhone();

	/**
	 * @return the position
	 */
	public abstract String getPosition();

	/**
	 * @return the subGroup
	 */
	public abstract String getSubGroup();

	/**
	 * @param addressOne
	 *          the addressOne to set
	 */
	public abstract void setAddressOne(ContactAddress addressOne);

	/**
	 * @param addressThree
	 *          the addressThree to set
	 */
	public abstract void setAddressThree(ContactAddress addressThree);

	/**
	 * @param addressTwo
	 *          the addressTwo to set
	 */
	public abstract void setAddressTwo(ContactAddress addressTwo);

	/**
	 * @param cell
	 *          the cell to set
	 */
	public abstract void setCell(String cell);

	/**
	 * @param contactGroup
	 *          the contactGroup to set
	 */
	public abstract void setContactGroup(ContactGroup contactGroup);

	/**
	 * @param email
	 *          the email to set
	 */
	public abstract void setEmail(String email);

	/**
	 * @param fax
	 *          the fax to set
	 */
	public abstract void setFax(String fax);

	/**
	 * @param firstName
	 *          the firstName to set
	 */
	public abstract void setFirstName(String firstName);

	/**
	 * @param lastName
	 *          the lastName to set
	 */
	public abstract void setLastName(String lastName);

	/**
	 * @param notes
	 *          the notes to set
	 */
	public abstract void setNotes(String notes);

	/**
	 * @param phone
	 *          the phone to set
	 */
	public abstract void setPhone(String phone);

	/**
	 * @param position
	 *          the position to set
	 */
	public abstract void setPosition(String position);

	/**
	 * @param subGroup
	 *          the subGroup to set
	 */
	public abstract void setSubGroup(String subGroup);

	/**
	 * @return an HTML representation of this object (for rendering to error messages etc...)
	 */
	public abstract String toHtml();

}