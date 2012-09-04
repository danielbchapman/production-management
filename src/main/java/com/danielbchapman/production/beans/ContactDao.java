package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Contact;
import com.danielbchapman.production.entity.ContactAddress;
import com.danielbchapman.production.entity.ContactGroup;
import com.danielbchapman.production.entity.ContactReportStructure;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.IContact;
import com.danielbchapman.production.entity.LinkedContact;
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
			contact.setAddresses(new ArrayList<ContactAddress>());
			contact.getAddresses().add(address);
			EntityInstance.saveObject(contact);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#addLinkedContact(java.lang.String,
	 * com.danielbchapman.production.entity.ContactGroup,
	 * com.danielbchapman.production.entity.Contact)
	 */
	@Override
	public LinkedContact addLinkedContact(String position, ContactGroup group, String subGroup,
			Contact contact)
	{
		LinkedContact link = new LinkedContact();

		link.setPosition(position);
		link.setContactGroup(group);
		link.setSubGroup(subGroup);
		link.setContact(contact);
		link = EntityInstance.saveObject(link);

		return link;
	}

	@Override
	public LinkedContact addLinkedContact(String position, Long groupId, String subGroup,
			Contact contact)
	{
		return addLinkedContact(position, getContactGroup(groupId), subGroup, contact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#assignContactToSeason(com.danielbchapman
	 * .production.entity.Contact, com.danielbchapman.production.entity.Season)
	 */
	@Override
	public void assignContactToSeason(IContact contact, Season season)
	{
		SeasonContact sc = new SeasonContact();
		if(contact instanceof Contact)
			sc.setBaseContact((Contact) contact);
		else
			sc.setLinkedContact((LinkedContact) contact);
		// else classCast which would be re-thrown...so...

		sc.setSeason(season);
		EntityInstance.saveObject(sc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#clearLinks(com.danielbchapman.production
	 * .entity.Contact)
	 */
	@Override
	public void clearLinks(Contact contact)
	{
		ArrayList<LinkedContact> links = EntityInstance.getResultList(
				"SELECT l FROM LinkedContact l WHERE l.contact = ?1", LinkedContact.class, contact);

		for(LinkedContact l : links)
			removeLinkedContact(l);
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

		clearLinks(contact);
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
		return EntityInstance.getResultList("SELECT c FROM Contact c ORDER BY c.lastName, c.firstName",
				Contact.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getContact(java.lang.Long)
	 */
	@Override
	public Contact getContact(Long id)
	{
		return EntityInstance.find(Contact.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getContactGroup(java.lang.Long)
	 */
	@Override
	public ContactGroup getContactGroup(Long id)
	{
		return EntityInstance.find(ContactGroup.class, id);
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
				"SELECT c FROM Contact c WHERE c.contactGroup = ?1 ORDER BY  c.lastName, c.firstName",
				Contact.class, group);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#getContactsForSeason(com.danielbchapman
	 * .production.entity.Season)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<IContact> getContactsForSeason(Season season)
	{
		/* @formatter:off*/
		String sql = 
						"SELECT \"id\" FROM (SELECT " +
						"  sc.id AS \"id\", " +
						"  g.name AS \"name\", " +
						"  c.subGroup AS \"subGroup\", " +
						"  c.lastName AS \"lastName\", " +
						"  c.firstName AS \"firstName\" " +
						"FROM  " +
						"  SeasonContact sc  " +
						"  INNER JOIN Contact c  " +
						"    ON  " +
						"      sc.baseContact_id = c.id " +
						"  INNER JOIN ContactGroup g " +
						"    ON  " +
						"      c.contactGroup_id = g.id " +
						"  WHERE " +
						"        sc.baseContact_id IS NOT NULL " +
						"    AND sc.season_id = ?1 " +
						"     " +
						"UNION ALL " +
						" " +
						"SELECT " +
						"  sc.id AS \"id\", " +
						"  g.name AS \"name\", " +
						"  l.subGroup AS \"subGroup\", " +
						"  c.lastName AS \"lastName\", " +
						"  c.firstName AS \"firstName\" " +
						"FROM  " +
						"  SeasonContact sc  " +
						"  INNER JOIN LinkedContact l " +
						"    ON " +
						"      sc.linkedContact_id = l.id " +
						"  INNER JOIN Contact c " +
						"    ON  " +
						"      l.contact_id = c.id " +
						"  INNER JOIN ContactGroup g " +
						"    ON  " +
						"      l.contactGroup_id = g.id " +
						"  WHERE " +
						"        sc.linkedContact_id IS NOT NULL " +
						"    AND sc.season_id = ?1  " +
						"ORDER BY  " +
						"  \"name\", " +
						"  \"subGroup\", " +
						"  \"lastName\", " +
						"  \"firstName\" " +
						"  ) as DerivedForMySql " ;	
		/*@formatter:on*/

		List<Long> ids = null;

		EntityManager em = EntityInstance.getEm();
		Query q = em.createNativeQuery(sql);
		q.setParameter(1, season.getId());
		ids = (List<Long>) q.getResultList();

		ArrayList<IContact> seasonResults = new ArrayList<IContact>();

		for(Long id : ids)
		{
			SeasonContact sc = getSeasonContacts(id);
			if(sc != null)
				seasonResults.add(sc.getContact());
		}

		em.close();
		return seasonResults;
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
			ArrayList<IContact> contacts = EntityInstance.getResultList(
					"SELECT c FROM Contact c WHERE c.contactGroup = ?1 ORDER BY c.lastName, c.firstName",
					IContact.class, group);
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
	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<ContactReportStructure> getContactSheetSeason(Season season)
	{
		//@formatter:off
		String findGroups =
						"/* A Query to locate Groups inside the SeasonContacts for a season */ \n" +
						"SELECT DISTINCT id FROM  \n" +
						"( \n" +
						"SELECT \n" +
						"  g.id, \n" +
						"  g.name \n" +
						"FROM \n" +
						"  SeasonContact s  \n" +
						"  INNER JOIN \n" +
						"  Contact c \n" +
						"    ON \n" +
						"      s.baseContact_id = c.id \n" +
						"  INNER JOIN \n" +
						"  ContactGroup g \n" +
						"    ON  \n" +
						"      c.contactGroup_id = g.id \n" +
						"WHERE \n" +
						"  s.season_id = ?1 \n" +
						" \n" +
						"UNION ALL \n" +
						" \n" +
						"SELECT \n" +
						"  g.id, \n" +
						"  g.name \n" +
						"FROM \n" +
						"  SeasonContact s  \n" +
						"  INNER JOIN \n" +
						"  LinkedContact l \n" +
						"    ON \n" +
						"      s.linkedContact_id = l.id \n" +
						"  INNER JOIN \n" +
						"  ContactGroup g \n" +
						"    ON  \n" +
						"      l.contactGroup_id = g.id \n" +
						"WHERE \n" +
						"  s.season_id = ?1 \n" +
						"   \n" +
						"ORDER BY \n" +
						" 2 \n" +
						") as DerivedMySQL   \n" ;
		
		String linkedEjbql = 
						"SELECT  \n" +
						"  sc.linkedContact  \n" +
						"FROM  \n" +
						"  SeasonContact sc  \n" +
						"WHERE  \n" +
						"      sc.linkedContact.contactGroup = ?1  \n" +
						"  AND sc.season = ?2   \n" +
						"  AND sc.linkedContact IS NOT NULL  \n" +
						"ORDER BY  \n" +
						"  sc.linkedContact.contact.lastName,  \n" +
						"  sc.linkedContact.contact.firstName \n" ;
		
		String regularEjbql = 
						"SELECT  \n" +
						"  sc.baseContact  \n" +
						"FROM  \n" +
						"  SeasonContact sc  \n" +
						"WHERE  \n" +
						"      sc.baseContact.contactGroup = ?1  \n" +
						"  AND sc.season = ?2   \n" +
						"  AND sc.baseContact IS NOT NULL  \n" +
						"ORDER BY  \n" +
						"  sc.baseContact.lastName,  \n" +
						"  sc.baseContact.firstName   \n" ;		
		//@formatter:on
		EntityManager em = EntityInstance.getEm();
		List<Long> ids = (List<Long>) em.createNativeQuery(findGroups)
				.setParameter(1, season.getId()).getResultList();
		ArrayList<ContactGroup> groups = new ArrayList<ContactGroup>();

		for(Long id : ids)
			groups.add(getContactGroup(id));

		ArrayList<ContactReportStructure> ret = new ArrayList<ContactReportStructure>();

		Collections.sort(groups);
		for(ContactGroup group : groups)
		{
			ArrayList<LinkedContact> linked = EntityInstance.getResultList(linkedEjbql,
					LinkedContact.class, group, season);
			ArrayList<Contact> regular = EntityInstance.getResultList(regularEjbql, Contact.class, group,
					season);

			ArrayList<IContact> contacts = new ArrayList<IContact>(linked);
			contacts.addAll(regular);

			Collections.sort(contacts);

			if(contacts.size() > 0)
			{
				ContactReportStructure element = new ContactReportStructure(group, contacts);
				ret.add(element);
			}
		}

		em.close();
		return ret;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<IContact> getContactsNotInSeason(Season season)
	{
		ArrayList<IContact> ret = new ArrayList<IContact>();
		/* @formatter:off */
		String notInSeasonLinked =
						"/* Query to detrimine LinkedContact not in the season */ \n" +
						"SELECT id FROM \n" +
						"( \n" +
						"  SELECT  \n" +
						"    l.id, \n" +
						"    c.lastName, \n" +
						"    c.firstName \n" +
						"  FROM  \n" +
						"    LinkedContact l  \n" +
						"    INNER JOIN Contact c \n" +
						"      ON \n" +
						"        l.contact_id = c.id \n" +
						"  WHERE  \n" +
						"    ((SELECT  \n" +
						"        Count(s.id)  \n" +
						"      FROM  \n" +
						"        SeasonContact s  \n" +
						"      WHERE  \n" +
						"            s.linkedContact_id = l.id \n" +
						"        AND s.season_id = ?1 \n" +
						"      )) < 1 \n" +
						"  ORDER BY \n" +
						"    2, 3 \n" +
						") as DerivedSubTwo; \n" ;						
		String notInSeasonContacts = 
						"/* Query to detrimine Contact not in the season */ \n" +
						"SELECT id FROM \n" +
						"( \n" +
						"  SELECT  \n" +
						"    c.id, \n" +
						"    c.lastName, \n" +
						"    c.firstName \n" +
						"  FROM  \n" +
						"    Contact c \n" +
						"  WHERE \n" +
						"      ((SELECT  \n" +
						"        Count(s.id)  \n" +
						"      FROM  \n" +
						"        SeasonContact s  \n" +
						"      WHERE  \n" +
						"            s.baseContact_id = c.id \n" +
						"        AND s.season_id = ?1 \n" +
						"      )) < 1 \n" +
						"  ORDER BY \n" +
						"    2, 3 \n" +
						") as DerivedSubOne; \n" ;
		/* @formatter:on */
		EntityManager em = EntityInstance.getEm();
		Query q = em.createNativeQuery(notInSeasonLinked);
		q.setParameter(1, season.getId());
		List<Long> results = q.getResultList();

		for(Long id : results)
			ret.add(EntityInstance.find(LinkedContact.class, id));

		q = em.createNativeQuery(notInSeasonContacts);
		q.setParameter(1, season.getId());
		results = q.getResultList();

		for(Long id : results)
			ret.add(EntityInstance.find(Contact.class, id));

		em.close();
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

	@Override
	public LinkedContact getLinkedContact(Long id)
	{
		return EntityInstance.find(LinkedContact.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getLinkedContacts(com.danielbchapman.
	 * production.entity.Contact)
	 */
	@Override
	public ArrayList<LinkedContact> getLinkedContacts(Contact contact)
	{
		return EntityInstance.getResultList("SELECT l FROM LinkedContact l WHERE l.contact = ?1",
				LinkedContact.class, contact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getSeasonContacts(java.lang.Long)
	 */
	@Override
	public SeasonContact getSeasonContacts(Long id)
	{
		return EntityInstance.find(SeasonContact.class, id);
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
	public void removeContactFromSeason(IContact contact, Season season)
	{
		ArrayList<SeasonContact> contacts;
		if(contact instanceof Contact)
			contacts = EntityInstance.getResultList(
					"SELECT sc FROM SeasonContact sc WHERE sc.baseContact = ?1 and sc.season = ?2",
					SeasonContact.class, contact, season);
		else
			contacts = EntityInstance.getResultList(
					"SELECT sc FROM SeasonContact sc WHERE sc.linkedContact = ?1 and sc.season = ?2",
					SeasonContact.class, contact, season);

		for(SeasonContact sc : contacts)
			removeContactFromSeason(sc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#removeContactFromSeason(com.danielbchapman
	 * .production.entity.SeasonContact)
	 */
	@Override
	public void removeContactFromSeason(SeasonContact seasonContact)
	{
		EntityInstance.deleteObject(seasonContact);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.ContactDaoRemote#removeLinkedContact(com.danielbchapman
	 * .production.entity.LinkedContact)
	 */
	@Override
	public void removeLinkedContact(LinkedContact contact)
	{
		ArrayList<SeasonContact> seasonContacts = EntityInstance
				.getResultList("SELECT sc FROM SeasonContact sc WHERE sc.linkedContact = ?1",
						SeasonContact.class, contact);

		for(SeasonContact sc : seasonContacts)
			EntityInstance.deleteObject(sc);

		EntityInstance.deleteObject(contact);
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
