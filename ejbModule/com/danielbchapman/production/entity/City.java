package com.danielbchapman.production.entity;

import java.util.Collection;

/**
 * A city, like one that we live in.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 16, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class City extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	private String name;
	private String stateOrTerritory;
	private String country;
	private Collection<Hotel> hotels;
	private String taxiServiceName;
	private String taxiServicePhone;
	private String taxiServiceAddress;
	private Collection<Hospital> hospitals;
	private Hotel selectedHotel;
}
