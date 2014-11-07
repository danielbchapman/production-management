package com.danielbchapman.production.beans;

import javax.ejb.Stateless;

/**
 * A simple bean that tries to locate an entity by the 
 * Long id and its class.
 */
@Stateless
public class EntityFinder implements EntityFinderRemote
{

	/**
	 * Default constructor.
	 */
	public EntityFinder()
	{
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.EntityFinderRemote#findEntity(java.lang.Long, java.lang.Class)
	 */
	@Override
	public <T> T findEntity(Long id, Class<T> clazz)
	{
		if(id == null)
			return null;
		
		return EntityInstance.getEm().find(clazz, id);
	}

}
