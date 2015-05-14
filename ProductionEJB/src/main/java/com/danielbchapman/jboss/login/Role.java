package com.danielbchapman.jboss.login;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * A user role in the general security strategy. This is linked by the user name but has entity
 * aspects which allow updates via a simple EJB layer without native SQL.
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Jan 7, 2011 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Entity
@Table(name = "role", uniqueConstraints = @UniqueConstraint(columnNames = { "user_user", "role" }))
public class Role implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(targetEntity = User.class)
	private User user;
	private String role;
	
	public Role()
	{
	}
	
	public Role(User user, String role)
	{
		this.user = user;
		this.role = role;
	}

	public Long getId()
	{
		return id;
	}

	public String getRole()
	{
		return role;
	}

	public User getUser()
	{
		return user;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public void setUser(User user)
	{
		this.user = user;
	}
}
