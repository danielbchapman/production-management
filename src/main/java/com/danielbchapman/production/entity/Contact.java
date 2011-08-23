package com.danielbchapman.production.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * A simple prime entity that implements a set of contacts
 * 
 */
@Entity
public class Contact extends ContactableAndAddressable
{
	private static final long serialVersionUID = 1L;
	@ManyToOne(targetEntity = ContactGroup.class)
	private ContactGroup contactGroup;
	/**
	 * @return the contactGroup
	 */
	public ContactGroup getContactGroup()
	{
		return contactGroup;
	}
	/**
	 * @param contactGroup the contactGroup to set
	 */
	public void setContactGroup(ContactGroup contactGroup)
	{
		this.contactGroup = contactGroup;
	}

}
