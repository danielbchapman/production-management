package com.danielbchapman.production;

import java.io.Serializable;

import org.eclipse.persistence.internal.libraries.asm.tree.analysis.Value;

/**
 * A simple K,V pair interface to be used by components
 * @author dchapman
 * @since Sep 6, 2012
 * @copyright The Acting Company Sep 6, 2012
 */
public interface IKeyValue extends Serializable
{
	public String getKey();
	public String getValue();
	public void setKey(String key);
	public void setValue(String value);
	/**
	 * @return the {@link Value} for this key
	 */
	public String get(String key);
	
	/**
	 * @param key the key to set
	 * @param value the value for this key
	 */
	public void set(String key, String value);
}
