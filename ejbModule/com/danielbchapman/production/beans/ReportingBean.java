package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 * This bean is a giant security hole. It will only be allowed
 * by those authenticated as administrator
 */
@Stateless
public class ReportingBean implements ReportingBeanRemote
{
	private static final long serialVersionUID = 1L;

	public ReportingBean()
	{
	}

	@Override	
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz, Object... params)
	{
		ArrayList<T> results = EntityInstance.getResultList(query, clazz, params);
		for(T t : results)
			EntityInstance.getEm().detach(t);
		return results;
	}

	@Override
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz)
	{
		return getResultList(query, clazz, new Object[]{});
	}

	@Override
	public String echo()
	{
		return "I'm a string, I should be fine";
	}


}
