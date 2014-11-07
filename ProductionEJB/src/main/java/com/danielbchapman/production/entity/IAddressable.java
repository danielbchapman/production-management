package com.danielbchapman.production.entity;

/**
 * An interface to provide types to an address.
 * 
 * @author dchapman
 * @since Sep 9, 2011
 * @copyright The Acting Company Sep 9, 2011 @link www.theactingcompany.org
 */
public interface IAddressable
{
	public String getAddressCity();

	public String getAddressLineOne();

	public String getAddressLineTwo();

	public String getAddressState();

	public String getAddressZip();

	public String getContact();

	public void setAddressCity(String city);

	public void setAddressLineOne(String lineOne);

	public void setAddressLineTwo(String lineTwo);

	public void setAddressState(String state);

	public void setAddressZip(String zip);

	public void setContact(String contact);
}
