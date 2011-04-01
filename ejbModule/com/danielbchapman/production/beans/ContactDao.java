package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Company;
import com.danielbchapman.production.entity.Contact;
import com.danielbchapman.production.entity.ContactGroup;
import com.danielbchapman.production.entity.ContactInformation;

/**
 * A simple Data Access Object that provides simple methods
 * for creating and maintaining contacts as well as generating the reports
 * associated with them.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Oct 1, 2009 2009
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
@Stateless
public class ContactDao implements ContactDaoRemote
{
//  @PersistenceContext
  EntityManager em = EntityInstance.getEm();
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#getGroups()
   */
  @SuppressWarnings("unchecked")
	public ArrayList<ContactGroup> getGroups()
  {
  	Query q = em.createQuery("SELECT g FROM ContactGroup g ORDER BY g.name");
  	List<ContactGroup> results = q.getResultList();
  	ArrayList<ContactGroup> ret = new ArrayList<ContactGroup>();
  	if(results != null)
  		for(ContactGroup g : results)
  			ret.add(g);
  	
  	return ret;
  }
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#saveGroup(com.danielbchapman.production.entity.ContactGroup)
   */
  public void saveGroup(ContactGroup group)
  {
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
  	if(group.getId() == null)
  		em.persist(group);
  	else
  		em.merge(group);
  	
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().commit();
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#getContacts()
   */
  @SuppressWarnings("unchecked")
	public ArrayList<Contact> getContacts()
  {
  	Query q = em.createQuery("SELECT c FROM Contact ORDER BY c.name");
  	ArrayList<Contact> ret = new ArrayList<Contact>();
  	List<Contact> results = (List<Contact>)q.getResultList();
  	
  	if(results != null)
  		for(Contact c : results)
  			ret.add(c);
  	
  	return ret;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#saveContact(com.danielbchapman.production.entity.Contact)
   */
  public void saveContact(Contact contact)
  {
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    
  	if(contact.getId() == null)
  		em.persist(contact);
  	else
  		em.merge(contact);
  	
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().commit();
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#getContactInformation(com.danielbchapman.production.entity.Contact)
   */
  @SuppressWarnings("unchecked")
	public ArrayList<ContactInformation> getContactInformation(Contact contact)
  {
  	ArrayList<ContactInformation> ret = new ArrayList<ContactInformation>();
  	Query q = em.createQuery("SELECT i FROM ContactInformation i WHERE i.contact = ?1 ORDER BY i.id");
  	q.setParameter(1, contact);
  	
  	List<ContactInformation> results = (List<ContactInformation>)q.getResultList();
  	if(results != null)
  		for(ContactInformation i : results)
  			ret.add(i);
  	
  	return ret;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#saveContactInformation(com.danielbchapman.production.entity.ContactInformation)
   */
  public void saveContactInformation(ContactInformation info)
  {
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    
  	if(info.getId() == null)
  		em.persist(info);
  	else
  		em.merge(info);
  	
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().commit();
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#getCompanies()
   */
  @SuppressWarnings("unchecked")
	public ArrayList<Company> getCompanies()
  {
  	ArrayList<Company> ret = new ArrayList<Company>();
  	Query q = em.createQuery("SELECT c FROM Company c ORDER BY c.name");
  	List<Company> results = (List<Company>)q.getResultList();
  	
  	if(results != null)
  		for(Company c : results)
  			ret.add(c);
  	
  	return ret;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ContactDaoRemote#save(com.danielbchapman.production.entity.Company)
   */
  public void save(Company entity)
  {
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    
  	if(entity.getId() == null)
  		em.persist(entity);
  	else
  		em.merge(entity);
  	
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().commit();
  }
}
