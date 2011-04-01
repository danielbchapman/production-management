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
public class Department implements Serializable
{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(length=50)
	private String name;
	
	public Department()
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

	
}
