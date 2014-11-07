package com.danielbchapman.production.beans;

import javax.ejb.Remote;

@Remote
public interface EntityFinderRemote
{
	/**
	 * Find the entity of class T with the ID of value.
	 * @param id
	 * @return T if found, else null.
	 * 
	 */
	public <T> T findEntity(Long id, Class<T> clazz);
}
