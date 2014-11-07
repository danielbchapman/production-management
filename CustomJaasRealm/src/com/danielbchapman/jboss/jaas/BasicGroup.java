package com.danielbchapman.jboss.jaas;

import java.io.Serializable;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * A basic group that stores the principles in different sets.
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Aug 2, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
public class BasicGroup implements Group, Serializable
{
	private static final long serialVersionUID = 1L;
	private final String name;
	private final Set<Principal> users = new HashSet<Principal>();

	public BasicGroup(String name)
	{
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.acl.Group#addMember(java.security.Principal)
	 */
	public boolean addMember(Principal user)
	{
		return users.add(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.acl.Group#removeMember(java.security.Principal)
	 */
	public boolean removeMember(Principal user)
	{
		return users.remove(user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.acl.Group#isMember(java.security.Principal)
	 */
	public boolean isMember(Principal member)
	{
		return users.contains(member);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.acl.Group#members()
	 */
	public Enumeration<? extends Principal> members()
	{
		return Collections.enumeration(users);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.security.Principal#getName()
	 */
	public String getName()
	{
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o)
	{
		if (o == null)
			return false;

		if (!(o instanceof BasicGroup))
			return false;

		return name.equals(((BasicGroup) o).name);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode()
	{
		return name.hashCode();
	}
}