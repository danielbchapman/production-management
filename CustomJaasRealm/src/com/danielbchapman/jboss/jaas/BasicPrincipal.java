package com.danielbchapman.jboss.jaas;

import java.io.Serializable;
import java.security.Principal;

/**
 * A basic principle that overrides the JBoss internal security. The role
 * query is completely ludicrous so we're wrapping it. And while we're at it 
 * we can add a back-door for the administrator.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Aug 2, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class BasicPrincipal implements Principal, Serializable
{
	private static final long serialVersionUID = 1L;
	private final String name;
	
	public BasicPrincipal(String name)
	{
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.security.Principal#getName()
	 */
	@Override
	public String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o)
	{
		return name.equals(o);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return name.hashCode();
	}
}
