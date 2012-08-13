package com.danielbchapman.production.web.services;

import java.util.ArrayList;
import java.util.Map;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.CompanyManagementDaoRemote;
import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.Hotel;
import com.google.gson.Gson;

/**
 * The hotel service expects a mapping of the following
 * <pre>
 * <tt>
 * [
 * </tt>
 * </pre>
 * @author dchapman
 * @since Aug 8, 2012
 * @copyright The Acting Company Aug 8, 2012
 */
public class HotelService extends AbstractJsonService
{
	/**
	 * The long identifiers of the city you are searching
	 */
	public static final String PARAM_CITY_ID = "city_id";
	
	/**
	 * a list of hotel objects rendered as JSON
	 * @see com.danielbchapman.production.entity.Hotel
	 */
	public static final String VALUE_HOTELS = "hotels";
	
	private static final long serialVersionUID = 1L;

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.web.services.AbstractJsonService#process(java.util.Map)
	 */
	@Override
	protected String process(Map<String, String> parameters) throws InvalidRequestException
	{
		try
		{
			CompanyManagementDaoRemote company = Utility.getObjectFromContext(CompanyManagementDaoRemote.class, Utility.Namespace.PRODUCTION);
			City city = company.getCity(Long.valueOf(parameters.get(PARAM_CITY_ID)));
			if(city == null)
				throw new InvalidRequestException("City was null", getClass(), parameters);
			
			ArrayList<Hotel> hotels = company.getHotelsForCity(city);
			Hotel[] hotelArray = Utility.array(hotels, Hotel.class);
			return new Gson().toJson(hotelArray); 
		}
		catch(Exception e)
		{
			if(e instanceof InvalidRequestException)
				throw (InvalidRequestException)e;
			
			throw new InvalidRequestException("Unable to process request [lazy]", getClass(), parameters);
		}
	}
}
