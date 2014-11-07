package com.danielbchapman.production.entity;

/**
 * An interface to provide types to the elements of contactable and addressable.
 * 
 * @author dchapman
 * @since Sep 9, 2011
 * @copyright The Acting Company Sep 9, 2011 @link www.theactingcompany.org
 */
public interface IContactable
{
	public String getCell();

	public String getContactName();

	public String getContactNotes();

	public String getContactPosition();

	public String getEmail();

	public String getFax();

	public String getPhone();

	public void setCell(String cell);

	public void setContactName(String contactName);

	public void setContactNotes(String contactNotes);

	public void setContactPosition(String contactPosition);

	public void setEmail(String email);

	public void setFax(String fax);

	public void setPhone(String phone);
}
