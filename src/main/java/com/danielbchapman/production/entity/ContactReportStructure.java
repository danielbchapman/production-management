package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

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
	
	private ArrayList<Contact> contacts;
	private ContactGroup group;

	/**
	 * Default Constructor 
	 */
	public ContactReportStructure()
	{
		this.group = null;
		this.contacts = new ArrayList<Contact>();
	}
	
	public ContactReportStructure(ContactGroup group, ArrayList<Contact> contacts)
	{
		this.group = group;
		this.contacts = contacts;
	}

	public void addContact(Contact contact)
	{
		contacts.add(contact);
	}
	/**
	 * @return the contacts
	 */
	public ArrayList<Contact> getContacts()
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
	public void setContacts(ArrayList<Contact> contacts)
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

	/* (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public int compareTo(ContactReportStructure obj)
	{
		if(obj == null)
			return 1;
		
		return group.getName().compareTo(obj.getGroup().getName());
	}
}
