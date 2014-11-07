package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * A simple representation of a department such as "Costumes" or "Video" etc...
 * 
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "name", "season" }))
public class Department extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	@Column(length = 50)
	private String name = "";
	private Season season;

	public Department()
	{
		super();
	}

	public String getName()
	{
		return name;
	}

	public Season getSeason()
	{
		return season;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSeason(Season season)
	{
		this.season = season;
	}

}
