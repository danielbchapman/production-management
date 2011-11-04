package com.danielbchapman.production.web.schedule.beans;

import com.danielbchapman.production.SmartConvertCurrency;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.EntityFinderRemote;
import com.danielbchapman.production.web.production.converters.CityConverter;

/**
 * A simple session bean to help the converters.
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Aug 10, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
public class ConverterBean
{
	private CityConverter cityConverter = new CityConverter();
	private EntityFinderRemote entityFinder;

	public CityConverter getCityConverter()
	{
		return cityConverter;
	}

	/**
	 * @see EntityFinderRemote#findEntity(Long, Class);
	 * 
	 */
	public <T> T getEntity(Class<T> clazz, Long id)
	{
		return getEntityFinder().findEntity(id, clazz);
	}

	/**
	 * @return a reference to a smart converter that ignores '$' chars.
	 */
	public SmartConvertCurrency getSmartConvertCurrency()
	{
		return new SmartConvertCurrency();
	}

	private EntityFinderRemote getEntityFinder()
	{
		if(entityFinder == null)
			entityFinder = Utility.getObjectFromContext(EntityFinderRemote.class,
					Utility.Namespace.PRODUCTION);
		return entityFinder;
	}
}
