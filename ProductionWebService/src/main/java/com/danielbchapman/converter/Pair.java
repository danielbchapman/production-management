package com.danielbchapman.converter;

import lombok.Data;

/**
 * A simple pair class that can be used as part of the PairConverter which is used 
 * to convert a pair to a SelectItem
 * @author dchapman
 * @since Sep 27, 2011
 * @copyright The Acting Company Sep 27, 2011 @link www.theactingcompany.org
 */
@Data
public class Pair<K,V>
{
	private K k;
	private V v;
	
	public Pair(K k, V v)
	{
		this.k = k;
		this.v = v;
	}
	
	/**
	 * Return the value of 'v' which is the value where k would be the key
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return v == null ? "null" : v.toString();
	}
}
