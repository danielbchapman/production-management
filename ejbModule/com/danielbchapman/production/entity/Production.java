package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * A simple entity to describe a production. This 
 * acts as a super reference for any objects.
 * 
 */
@Entity
public class Production extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	public Production()
	{
		super();
	}

	@Column(length=256,unique=true)
	private String name;
	
	@Lob
	private String desriptionMarkup;

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
	  out.append(getId());
	  out.append("' Name='");
	  out.append(name);
	  out.append("' Description='");
	  out.append(desriptionMarkup);
	  out.append("'");
	  return out.toString();		
	}
}

