package com.danielbchapman.production.entity;


/**
 * The SeasonContacts provides a link between a season and a contact. This
 * simply marks a contact as a member of the season so that when a sheet is printed
 * it filters out unused contacts.
 * 
 * @author dchapman
 * @since Aug 23, 2011
 * @copyright The Acting Company Aug 23, 2011 @link www.theactingcompany.org
 */
public class SeasonContacts extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	private Contact contact;
	private Season season;

	/**
	 * @return the season
	 */
	public Season getSeason()
	{
		return season;
	}

	/**
	 * @param season the season to set
	 */
	public void setSeason(Season season)
	{
		this.season = season;
	}

	/**
	 * @return the contact
	 */
	public Contact getContact()
	{
		return contact;
	}

	/**
	 * @param contact the contact to set
	 */
	public void setContact(Contact contact)
	{
		this.contact = contact;
	}
}
