package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;


/**
 * Entity implementation class for Entity: TaskUsers
 * 
 */
@Entity
public class TaskUser extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	@Column(length=75)
	private String name;
	@Column(length=75)
	private String position;
	public String getName()
	{
		return name;
	}
	public String getPosition()
	{
		return position;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setPosition(String position)
	{
		this.position = position;
	}

}
