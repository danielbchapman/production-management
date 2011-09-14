package com.danielbchapman.production.entity;

import javax.persistence.Entity;

/**
 * The SeasonContacts provides a link between a season and a contact. This simply marks a contact as
 * a member of the season so that when a sheet is printed it filters out unused contacts.
 * 
 * @author dchapman
 * @since Aug 23, 2011
 * @copyright The Acting Company Aug 23, 2011 @link www.theactingcompany.org
 */
@Entity
public class SeasonContact extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	private Contact baseContact;
	private LinkedContact linkedContact;

	private Season season;

	/**
	 * @return the baseContact
	 */
	public Contact getBaseContact()
	{
		return baseContact;
	}

	/**
	 * Return, first a base contact and else a Linked Contact
	 * 
	 * @return the contact
	 */
	public IContact getContact()
	{
		if(baseContact != null)
			return baseContact;

		if(linkedContact != null)
			return linkedContact;

		return null;
	}

	/**
	 * @return the season
	 */
	public Season getSeason()
	{
		return season;
	}

	/**
	 * @param baseContact
	 *          the baseContact to set
	 */
	public void setBaseContact(Contact baseContact)
	{
		this.baseContact = baseContact;
	}

	/**
	 * @param linkedContact
	 *          the linkedContact to set
	 */
	public void setLinkedContact(LinkedContact linkedContact)
	{
		this.linkedContact = linkedContact;
	}

	/**
	 * @param season
	 *          the season to set
	 */
	public void setSeason(Season season)
	{
		this.season = season;
	}
}
