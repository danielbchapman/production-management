package com.danielbchapman.production.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A simple entity that represents a group of
 * people such as a company or subgroup.
 * 
 */
@Entity
public class ContactGroup implements Serializable
{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 40)
	private String name;
	@Column(length = 512)
	private String description;

	public ContactGroup()
	{
		super();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
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
