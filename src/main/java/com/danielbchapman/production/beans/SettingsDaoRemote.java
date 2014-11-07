package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Settings;

/**
 * <p>
 * The settings bean is simply a key/value map in the database for settings. It is used to add
 * functionality without having to rebuild the database for a single field.
 * </p>
 * <p>
 * Example:
 * <code>
 * <pre>
 *   settingsBean.get("GOOGLE_DOCS_URL"); //return https://....
 *   settingsBean.put("GOOGLE_DOCS_URL", "https://...");
 * </pre>
 * </code>
 * </p>
 * @author dchapman
 * @since Sep 4, 2012
 * @copyright The Acting Company Sep 4, 2012
 */
@Remote
public interface SettingsDaoRemote extends Serializable
{
	
	/**
	 * @return all Settings objects from the database.
	 */
	public ArrayList<Settings> getSettings();
	/**
	 * @param key the key to get
	 * @return the value for this key, null if undefined
	 */
	public String get(String key);
	
	/**
	 * @param key the key to assign
	 * @param value the value to assign to this key
	 */
	public void put(String key, String value);
	
	/**
	 * @param key the key to remove from the database
	 */
	public void delete(String key);
}
