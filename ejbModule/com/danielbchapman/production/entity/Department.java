package com.danielbchapman.production.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A simple representation of a department such
 * as "Costumes" or "Video" etc...
 * 
 */
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name"}))
public class Department extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	@Column(length=50)
	private String name;
	
	public Department()
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

	
}
