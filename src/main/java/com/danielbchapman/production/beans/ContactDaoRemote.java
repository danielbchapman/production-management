package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Contact;
import com.danielbchapman.production.entity.ContactAddress;
import com.danielbchapman.production.entity.ContactGroup;
import com.danielbchapman.production.entity.ContactReportStructure;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.SeasonContact;

@Remote
public interface ContactDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

	/**
	 * Add an address to a particular contact
	 * 
	 * @param address
	 * @param contact
	 */
	public abstract void addContactAddress(ContactAddress address, Contact contact);

	/**
	 * Assign this contact to a particular season.
	 * 
	 * @param contcat
	 *          the contact to assign
	 * @param season
	 *          the season to be assigned to
	 */
	public abstract void assignContactToSeason(Contact contcat, Season season);

	/**
	 * Remove a contact from the database
	 * 
	 * @param contact
	 */
	public abstract void deleteContact(Contact contact);

	/**
	 * Remove a contact's address from the database
	 * 
	 * @param address
	 */
	public abstract void deleteContactAddress(ContactAddress address);

	/**
	 * Delete a contact group from the database and assign all the contacts to another group.
	 * 
	 * @param group
	 */
	public abstract void deleteContactGroup(ContactGroup group, ContactGroup reassign);

	/**
	 * @param contact
	 * @return a list of addresses for a particular contact
	 */
	public abstract ArrayList<ContactAddress> getAddressesForContact(Contact contact);

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
	 * @return the structure for a contact sheet
	 */
	public ArrayList<ContactReportStructure> getContactSheetAll();

	/**
	 * @param season
	 *          the season to sort by
	 * @return the structure for a contact sheet by the season
	 */
	public ArrayList<ContactReportStructure> getContactSheetSeason(Season season);

	/**
	 * @param season
	 *          the season to search
	 * @return the inverse of getContactsForSeason
	 */
	public abstract ArrayList<Contact> getContactsNotInSeason(Season season);

	/**
	 * @return a list of distinct subgroups
	 */
	public abstract ArrayList<String> getContactSubGroups();

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
	 * Remove a contact from a particular season.
	 * 
	 * @param contact
	 *          the contact to remove
	 * @param season
	 *          the season to remove from
	 */
	public abstract void removeContactFromSeason(Contact contact, Season season);

	/**
	 * Remove a contact from a particular season.
	 * 
	 * @param contact
	 *          the season contact to remove
	 */
	public abstract void removeContactFromSeason(SeasonContact seasonContact);

	/**
	 * Save a contact to the database
	 * 
	 * @param contact
	 */
	public abstract Contact saveContact(Contact contact);

	/**
	 * Saves a group
	 * 
	 * @param group
	 */
	public abstract void saveGroup(ContactGroup group);

}