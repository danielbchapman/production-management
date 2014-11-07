package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * This is a mutant base entity that provides embedded contact Java does not support multiple
 * inheritance and this provides consistency across the platform.
 * 
 * Please forgive this mutant design, it does save time on the Web side. <em> significant 
 * time which warrants this sub par design, sub objects require significant maintenance and
 * cause null pointer issues with the current Primefaces and JSF2.0 models.</em>
 * 
 * @see Contactable
 * @see ContactableAndAddressable
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Jun 20, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@MappedSuperclass
public abstract class Addressable extends BaseEntity implements IAddressable
{
	private static final long serialVersionUID = 1L;

	@Column(length = 128)
	private String addressLineOne = "";
	@Column(length = 128)
	private String addressLineTwo = "";
	@Column(length = 128)
	private String addressContact = "";
	@Column(length = 20)
	private String addressZip = "";
	@Column(length = 40)
	private String addressCity = "";
	@Column(length = 60)
	private String addressState = "";

	/**
	 * @return the addressCity
	 */
	@Override
	public String getAddressCity()
	{
		return addressCity;
	}

	/**
	 * @return the addressContact
	 */
	public String getAddressContact()
	{
		return addressContact;
	}

	/**
	 * @return the addressLineOne
	 */
	@Override
	public String getAddressLineOne()
	{
		return addressLineOne;
	}

	/**
	 * @return the addressLineTwo
	 */
	@Override
	public String getAddressLineTwo()
	{
		return addressLineTwo;
	}

	/**
	 * @return the addressState
	 */
	@Override
	public String getAddressState()
	{
		return addressState;
	}

	/**
	 * @return the addressZip
	 */
	@Override
	public String getAddressZip()
	{
		return addressZip;
	}

	/**
	 * @param addressCity
	 *          the addressCity to set
	 */
	@Override
	public void setAddressCity(String addressCity)
	{
		this.addressCity = addressCity;
	}

	/**
	 * @param addressContact
	 *          the addressContact to set
	 */
	public void setAddressContact(String addressContact)
	{
		this.addressContact = addressContact;
	}

	/**
	 * @param addressLineOne
	 *          the addressLineOne to set
	 */
	@Override
	public void setAddressLineOne(String addressLineOne)
	{
		this.addressLineOne = addressLineOne;
	}

	/**
	 * @param addressLineTwo
	 *          the addressLineTwo to set
	 */
	@Override
	public void setAddressLineTwo(String addressLineTwo)
	{
		this.addressLineTwo = addressLineTwo;
	}

	/**
	 * @param addressState
	 *          the addressState to set
	 */
	@Override
	public void setAddressState(String addressState)
	{
		this.addressState = addressState;
	}

	/**
	 * @param addressZip
	 *          the addressZip to set
	 */
	@Override
	public void setAddressZip(String addressZip)
	{
		this.addressZip = addressZip;
	}
}
