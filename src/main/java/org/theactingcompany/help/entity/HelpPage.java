package org.theactingcompany.help.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.danielbchapman.production.entity.BaseEntity;

@Entity
public class HelpPage extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	@Lob
	private String helpPage;
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastUpdated;
	@Column(length=256)
	private String lastUpdatedBy;
	@Column(length=256)
	private String lookupString;
	public String getHelpPage()
	{
		if(helpPage == null)
		{
			StringBuilder builder = new StringBuilder();
			builder.append("<h3>No help yet</h3>\n");
			builder.append("<p>\n");
			builder.append("\tThis page has no help markup created, please create some (user or administrator access required)\n");
			builder.append("</p>\n");
			
			helpPage = builder.toString();
		}
		return helpPage;
	}
	public Date getLastUpdated()
	{
		return lastUpdated;
	}
	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}

	public String getLookupString()
	{
		return lookupString;
	}
	public void setHelpPage(String helpPage)
	{
		this.helpPage = helpPage;
	}
	public void setLastUpdated(Date lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public void setLookupString(String lookupString)
	{
		this.lookupString = lookupString;
	}
}
