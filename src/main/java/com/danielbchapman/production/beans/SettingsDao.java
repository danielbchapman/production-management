package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.ejb.Stateless;

import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Settings;

/**
 * Session Bean implementation class SettingsBean
 */
@Stateless
public class SettingsDao implements SettingsDaoRemote
{
	public SettingsDao()
	{
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.SettingsBeanRemote#get(java.lang.String)
	 */
	@Override
	public String get(String key)
	{
		Settings entity = getEntity(key);
		if(entity == null)
			return null;
		else
			return entity.getValue();
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.SettingsBeanRemote#put(java.lang.String, java.lang.String)
	 */
	@Override
	public void put(String key, String value)
	{
		Settings entity = getEntity(key);
		if(entity == null)
			entity = new Settings(key, value);
		else
			entity.setValue(value);
		
		EntityInstance.saveObject(entity);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.SettingsDaoRemote#getSettings()
	 */
	@Override
	public ArrayList<Settings> getSettings()
	{
		return EntityInstance.getResultList("SELECT s FROM Settings s ORDER BY s.key", Settings.class);
	}

	@Override
	public void delete(String key)
	{
		Settings s = getEntity(key);
		if(s != null)
			EntityInstance.deleteObject(s);
	}

	private Settings getEntity(String key)
	{
		return EntityInstance.getSingleResult("SELECT s FROM Settings s WHERE s.key = ?1", Settings.class, key);
	}
}
