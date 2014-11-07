package com.danielbchapman.production.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

/**
 * A simple embedded piece of contact information as a workaround for the unimplemented Contact
 * system.
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Jan 22, 2011 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Embeddable
public class EmbeddableContactInformation implements Serializable
{
	private static final long serialVersionUID = 1L;
	@Column(length = 128)
	private String contactName = "";
	@Column(length = 64)
	private String phone = "";
	@Column(length = 64)
	private String fax = "";
	@Column(length = 64)
	private String cell = "";
	@Column(length = 128)
	private String email = "";
	@Lob
	private String contactNotes;

	public String getCell()
	{
		return cell;
	}

	public String getContactName()
	{
		return contactName;
	}

	public String getContactNotes()
	{
		return contactNotes;
	}

	public String getEmail()
	{
		return email;
	}

	public String getFax()
	{
		return fax;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setCell(String cell)
	{
		this.cell = cell;
	}

	public void setContactName(String contactName)
	{
		this.contactName = contactName;
	}

	public void setContactNotes(String contactNotes)
	{
		this.contactNotes = contactNotes;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public void setFax(String fax)
	{
		this.fax = fax;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

}
