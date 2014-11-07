package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

/**
 * A simple entity that allows notes as to vendors ec... the notes field probably should be
 * implemented as a rich text or something useful. Right now it is basically contact information for
 * the company with some notes and a department.
 * 
 * Useful, but just that, a list.
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Apr 18, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Entity
public class Vendor extends Contactable
{
	private static final long serialVersionUID = 1L;

	@Column(length = 128)
	private String companyName = "";
	@Column(length = 128)
	private String department = "";
	@Lob
	private String notes = "";

	public String getCompanyName()
	{
		return companyName;
	}

	public String getDepartment()
	{
		return department;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setCompanyName(String companyName)
	{
		this.companyName = companyName;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}
}
