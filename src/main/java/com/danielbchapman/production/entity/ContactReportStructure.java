package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A simple datastructure to represent a contact sheet so that Jasper Reports can actually do some
 * elegant printing.
 * 
 * @author dchapman
 * @since Aug 24, 2011
 * @copyright The Acting Company Aug 24, 2011 @link www.theactingcompany.org
 */
public class ContactReportStructure implements Serializable, Comparable<ContactReportStructure>
{

	private static final long serialVersionUID = 1L;

	private ArrayList<IContact> contacts;
	private ContactGroup group;

	/**
	 * Default Constructor
	 */
	public ContactReportStructure()
	{
		this.group = null;
		this.contacts = new ArrayList<IContact>();
	}

	public ContactReportStructure(ContactGroup group, ArrayList<IContact> contacts)
	{
		this.group = group;
		this.contacts = contacts;
	}

	public void addContact(IContact contact)
	{
		contacts.add(contact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ContactReportStructure obj)
	{
		if(obj == null)
			return 1;

		return group.getName().compareTo(obj.getGroup().getName());
	}

	/**
	 * @return the contacts
	 */
	public ArrayList<IContact> getContacts()
	{
		return contacts;
	}

	/**
	 * @return the group
	 */
	public ContactGroup getGroup()
	{
		return group;
	}

	/**
	 * @param contacts
	 *          the contacts to set
	 */
	public void setContacts(ArrayList<IContact> contacts)
	{
		this.contacts = contacts;
	}

	/**
	 * @param group
	 *          the group to set
	 */
	public void setGroup(ContactGroup group)
	{
		this.group = group;
	}
}
