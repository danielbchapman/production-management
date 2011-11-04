package com.danielbchapman.production.web.production.converters;

import com.danielbchapman.production.entity.City;

/**
 * A simple City converter
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Aug 10, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class CityConverter extends EntityConverter
{

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.web.production.converters.EntityConverter#getEntityType()
	 */
	@Override
	protected Class<City> getEntityType()
	{
		return City.class;
	}

}
