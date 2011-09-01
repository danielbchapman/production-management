package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Contact;
import com.danielbchapman.production.entity.ContactAddress;
import com.danielbchapman.production.entity.ContactGroup;
import com.danielbchapman.production.entity.ContactReportStructure;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.SeasonContact;

/**
 * A simple Data Access Object that provides simple methods for creating and maintaining contacts as
 * well as generating the reports associated with them.
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Oct 1, 2009 2009
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Stateless
public class ContactDao implements ContactDaoRemote
{

	private static final long serialVersionUID = 1L;
	EntityManager em = EntityInstance.getEm();

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#addContactAddress(com.danielbchapman.
	 * production.entity.ContactAddress, com.danielbchapman.production.entity.Contact)
	 */
	@Override
	public void addContactAddress(ContactAddress address, Contact contact)
	{
		address.setContact(contact);

		if(contact.getAddresses() != null)
		{
			contact.getAddresses().add(address);
			EntityInstance.saveObject(contact);
		}
		else
		{
			EntityInstance.saveObject(address);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#assignContactToSeason(com.danielbchapman
	 * .production.entity.Contact, com.danielbchapman.production.entity.Season)
	 */
	@Override
	public void assignContactToSeason(Contact contact, Season season)
	{
		SeasonContact sc = new SeasonContact();
		sc.setContact(contact);
		sc.setSeason(season);
		EntityInstance.saveObject(sc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#deleteContact(com.danielbchapman.production
	 * .entity.Contact)
	 */
	@Override
	public void deleteContact(Contact contact)
	{
		ArrayList<ContactAddress> addresses = getAddressesForContact(contact);
		for(int i = 0; i < addresses.size(); i++)
			deleteContactAddress(addresses.get(i));

		EntityInstance.deleteObject(contact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#deleteContactAddress(com.danielbchapman
	 * .production.entity.ContactAddress)
	 */
	@Override
	public void deleteContactAddress(ContactAddress address)
	{
		Contact c = getContact(address.getContact().getId());
		if(c.getAddresses() != null)
			c.getAddresses().remove(address);
		EntityInstance.deleteObject(address);
		EntityInstance.saveObject(c);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#deleteContactGroup(com.danielbchapman.
	 * production.entity.ContactGroup, com.danielbchapman.production.entity.ContactGroup)
	 */
	@Override
	public void deleteContactGroup(ContactGroup group, ContactGroup reassign)
	{
		ArrayList<Contact> contacts = getContactsForGroup(group);
		for(Contact c : contacts)
		{
			c.setContactGroup(reassign);
			saveContact(c);
		}

		EntityInstance.deleteObject(group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#getAddressesForContact(com.danielbchapman
	 * .production.entity.Contact)
	 */
	@Override
	public ArrayList<ContactAddress> getAddressesForContact(Contact contact)
	{
		return EntityInstance.getResultList("SELECT a FROM ContactAddress a WHERE a.contact = ?1",
				ContactAddress.class, contact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getContacts()
	 */
	@Override
	public ArrayList<Contact> getAllContacts()
	{
		return EntityInstance.getResultList("SELECT c FROM Contact c ORDER BY c.name", Contact.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getContact(java.lang.Long)
	 */
	@Override
	public Contact getContact(Long id)
	{
		return EntityInstance.getEm().find(Contact.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getContactGroup(java.lang.Long)
	 */
	@Override
	public ContactGroup getContactGroup(Long id)
	{
		return EntityInstance.getEm().find(ContactGroup.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#getContactsForGroup(com.danielbchapman
	 * .production.entity.ContactGroup)
	 */
	@Override
	public ArrayList<Contact> getContactsForGroup(ContactGroup group)
	{
		return EntityInstance.getResultList(
				"SELECT c FROM Contact c WHERE c.contactGroup = ?1 ORDER BY c.name", Contact.class, group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#getContactsForSeason(com.danielbchapman
	 * .production.entity.Season)
	 */
	@Override
	public ArrayList<Contact> getContactsForSeason(Season season)
	{
		return EntityInstance.getResultList("SELECT s.contact FROM SeasonContact s "
				+ "WHERE s.season = ?1 AND s.contact IS NOT NULL "
				+ " ORDER BY s.contact.contactGroup.name, s.contact.name", Contact.class, season);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getContactSheetAll()
	 */
	@Override
	public ArrayList<ContactReportStructure> getContactSheetAll()
	{
		ArrayList<ContactGroup> groups = EntityInstance.getResultList(
				"SELECT c FROM ContactGroup c ORDER BY c.name", ContactGroup.class);

		ArrayList<ContactReportStructure> ret = new ArrayList<ContactReportStructure>();
		for(ContactGroup group : groups)
		{
			ArrayList<Contact> contacts = EntityInstance
					.getResultList("SELECT c FROM Contact c WHERE c.contactGroup = ?1 ORDER BY c.name",
							Contact.class, group);
			if(contacts.size() > 0)
			{
				ContactReportStructure element = new ContactReportStructure(group, contacts);
				ret.add(element);
			}
		}

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#getContactSheetSeason(com.danielbchapman
	 * .production.entity.Season)
	 */
	@Override
	public ArrayList<ContactReportStructure> getContactSheetSeason(Season season)
	{
		ArrayList<ContactGroup> groups = EntityInstance.getResultList(

		"SELECT DISTINCT sc.contact.contactGroup FROM SeasonContact sc ORDER BY sc.contact.name",
				ContactGroup.class);

		ArrayList<ContactReportStructure> ret = new ArrayList<ContactReportStructure>();
		for(ContactGroup group : groups)
		{
			ArrayList<Contact> contacts = EntityInstance
					.getResultList(
							"SELECT sc.contact FROM SeasonContact sc WHERE sc.contact.contactGroup = ?1 ORDER BY sc.contact.name",
							Contact.class, group);

			if(contacts.size() > 0)
			{
				ContactReportStructure element = new ContactReportStructure(group, contacts);
				ret.add(element);
			}
		}

		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Contact> getContactsNotInSeason(Season season)
	{
		ArrayList<Contact> ret = new ArrayList<Contact>();
		Query q = EntityInstance
				.getEm()
				.createNativeQuery(
						"SELECT c.id FROM Contact c WHERE c.id NOT IN (SELECT s.contact_id FROM SeasonContact s WHERE s.season_id = ?1) ORDER BY c.name");
		q.setParameter(1, season.getId());

		List<Long> results = q.getResultList();

		for(Long id : results)
			ret.add(EntityInstance.getEm().find(Contact.class, id));

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getContactSubGroups()
	 */
	@Override
	public ArrayList<String> getContactSubGroups()
	{
		return EntityInstance.getResultList(
				"SELECT DISTINCT c.subGroup FROM Contact c WHERE c.subGroup IS NOT NULL", String.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getGroups()
	 */
	@Override
	public ArrayList<ContactGroup> getGroups()
	{
		return EntityInstance.getResultList("SELECT g FROM ContactGroup g ORDER BY g.name",
				ContactGroup.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getSeasonContacts(java.lang.Long)
	 */
	@Override
	public SeasonContact getSeasonContacts(Long id)
	{
		return EntityInstance.getEm().find(SeasonContact.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getSeasonContacts(com.danielbchapman.
	 * production.entity.Season)
	 */
	@Override
	public ArrayList<SeasonContact> getSeasonContacts(Season season)
	{
		return EntityInstance.getResultList(
				"SELECT s FROM SeasonContact s WHERE s.seaosn = ?1 ORDER BY s.name", SeasonContact.class,
				season);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#removeContactFromSeason(com.danielbchapman
	 * .production.entity.Contact, com.danielbchapman.production.entity.Season)
	 */
	@Override
	public void removeContactFromSeason(Contact contact, Season season)
	{
		ArrayList<SeasonContact> contacts = EntityInstance.getResultList(
				"SELECT sc FROM SeasonContact sc WHERE sc.contact = ?1 and sc.season = ?2",
				SeasonContact.class, contact, season);

		for(SeasonContact sc : contacts)
			removeContactFromSeason(sc);
	}

	@Override
	public void removeContactFromSeason(SeasonContact seasonContact)
	{
		EntityInstance.deleteObject(seasonContact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#saveContact(com.danielbchapman.production
	 * .entity.Contact)
	 */
	@Override
	public Contact saveContact(Contact contact)
	{
		return EntityInstance.saveObject(contact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#saveGroup(com.danielbchapman.production
	 * .entity.ContactGroup)
	 */
	@Override
	public void saveGroup(ContactGroup group)
	{
		EntityInstance.saveObject(group);
	}

}
