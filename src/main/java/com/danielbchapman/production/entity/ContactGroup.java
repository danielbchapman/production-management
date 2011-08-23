package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * A simple entity that represents a group of
 * people such as a company or subgroup.
 * 
 */
@Entity
public class ContactGroup extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	@Column(length = 40)
	private String name;
	@Column(length = 512)
	private String description;

	public ContactGroup()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
