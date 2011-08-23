package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.danielbchapman.production.entity.Contact;
import com.danielbchapman.production.entity.ContactGroup;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.SeasonContacts;

/**
 * A simple Data Access Object that provides simple methods
 * for creating and maintaining contacts as well as generating the reports
 * associated with them.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Oct 1, 2009 2009
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Stateless
public class ContactDao implements ContactDaoRemote
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//  @PersistenceContext
  EntityManager em = EntityInstance.getEm();
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#getContacts()
   */
  @SuppressWarnings("unchecked")
	public ArrayList<Contact> getAllContacts()
  {
  	return EntityInstance.getResultList("SELECT c FROM Contact c ORDER BY c.contactName", Contact.class);
  }
  /* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getContactsForGroup(com.danielbchapman.production.entity.ContactGroup)
	 */
	@Override
	public ArrayList<Contact> getContactsForGroup(ContactGroup group)
	{
		return EntityInstance.getResultList("SELECT c FROM Contact c WHERE c.contactGroup = ?1 ORDER BY c.contactName", Contact.class, group);
	}
  
  /* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getContactsForSeason(com.danielbchapman.production.entity.Season)
	 */
	@Override
	public ArrayList<Contact> getContactsForSeason(Season season)
	{
		return
				EntityInstance.getResultList(
						"SELECT s.contact FROM SeasonContacts s " + 
						"WHERE s.season = ?1 AND s.contact IS NOT NULL " + 
						" ORDER BY s.contact.contactGroup.name, s.contact.contactName",
						Contact.class,
						season);
	}
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#getGroups()
   */
  @SuppressWarnings("unchecked")
	public ArrayList<ContactGroup> getGroups()
  {
  	return EntityInstance.getResultList("SELECT g FROM ContactGroup g ORDER BY g.name", ContactGroup.class);
  }
  
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#saveContact(com.danielbchapman.production.entity.Contact)
   */
  public void saveContact(Contact contact)
  {
  	EntityInstance.saveObject(contact);
  }
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#saveGroup(com.danielbchapman.production.entity.ContactGroup)
   */
  public void saveGroup(ContactGroup group)
  {
  	EntityInstance.saveObject(group);
  }
  
	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.ContactDaoRemote#getSeasonContacts(com.danielbchapman.production.entity.Season)
	 */
	@Override
	public ArrayList<SeasonContacts> getSeasonContacts(Season season)
	{
		return EntityInstance.getResultList("SELECT s FROM SeasonContacts s WHERE s.seaosn = ?1 ORDER BY s.name", SeasonContacts.class, season);
	}
}
