package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Contact;
import com.danielbchapman.production.entity.ContactGroup;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.SeasonContact;

@Remote
public interface ContactDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

	/**
	 * @return a list of known contacts
	 */
	public abstract ArrayList<Contact> getAllContacts();

	/**
	 * @param id
	 * @return the entity for this ID
	 */
	public Contact getContact(Long id);

	/**
	 * @param id
	 * @return the entity for this ID
	 */
	public ContactGroup getContactGroup(Long id);

	/**
	 * @param group
	 *          the group to search by.
	 * @return a list of contacts for this group.
	 */
	public abstract ArrayList<Contact> getContactsForGroup(ContactGroup group);

	/**
	 * @param season
	 *          the season to search.
	 * @return a list of all the contacts assigned to this season.
	 */
	public abstract ArrayList<Contact> getContactsForSeason(Season season);

	/**
	 * @return a list of contact groups from the DB ordered by name
	 */
	public abstract ArrayList<ContactGroup> getGroups();

	/**
	 * @param id
	 *          the id
	 * @return return the entity for this ID
	 */
	public SeasonContact getSeasonContacts(Long id);

	/**
	 * Return a reference to the linking object for the SeasonContacts
	 * 
	 * @param season
	 * @return a list of all SeasonContacts currently assigned to this season
	 */
	public abstract ArrayList<SeasonContact> getSeasonContacts(Season season);

	/**
	 * Save a contact to the database
	 * 
	 * @param contact
	 */
	public abstract void saveContact(Contact contact);

	/**
	 * Saves a group
	 * 
	 * @param group
	 */
	public abstract void saveGroup(ContactGroup group);

}