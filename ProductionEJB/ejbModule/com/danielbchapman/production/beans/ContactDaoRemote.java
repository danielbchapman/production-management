package com.danielbchapman.production.beans;


import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Contact;
import com.danielbchapman.production.entity.ContactGroup;
import com.danielbchapman.production.entity.ContactInformation;

@Remote
public interface ContactDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

  /**
   * @return a list of contact groups from the DB ordered by name
   */
  public abstract ArrayList<ContactGroup> getGroups();

  /**
   * Saves a group
   * @param group
   */
  public abstract void saveGroup(ContactGroup group);

  /**
   * @return a list of known contacts
   */
  public abstract ArrayList<Contact> getContacts();

  /**
   * Save a contact to the database
   * @param contact
   */
  public abstract void saveContact(Contact contact);

  /**
   * @param contact
   * @return a list of contact information for a particular contact
   */
  public abstract ArrayList<ContactInformation> getContactInformation(
      Contact contact);

  /**
   * Save a contact to the database.
   * @param info
   */
  public abstract void saveContactInformation(ContactInformation info);

}