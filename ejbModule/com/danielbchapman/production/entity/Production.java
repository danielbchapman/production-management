package com.danielbchapman.production.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A simple entity to describe a production. This 
 * acts as a super reference for any objects.
 * 
 */
@Entity
public class Production implements Serializable
{

	private static final long serialVersionUID = 1L;

	public Production()
	{
		super();
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(length=256,unique=true)
	private String name;
	@Column(length=2048)
	private String desriptionMarkup;
	
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
	public String getDesriptionMarkup()
	{
		return desriptionMarkup;
	}
	public void setDesriptionMarkup(String desriptionMarkup)
	{
		this.desriptionMarkup = desriptionMarkup;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return name == null ? "none" : name.trim();
	}
	
	/**
	 * A more descriptive toString() if needed;
	 * @return a complex description.
	 * 
	 */
	public String debugToString()
	{
	  StringBuilder out = new StringBuilder();
	  out.append(super.toString());
	  out.append(" ");
	  out.append("Id='");
	  out.append(id);
	  out.append("' Name='");
	  out.append(name);
	  out.append("' Description='");
	  out.append(desriptionMarkup);
	  out.append("'");
	  return out.toString();		
	}
}

