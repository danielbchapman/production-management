package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;


/**
 * A simple todo list that stores completed and
 * incomplete items in a format that is easily viewed by 
 * the end user. This allows for simple tracking locally
 * and should be usable as a web service.
 * 
 */
@Entity
public class TodoList implements Serializable
{

	private static final long serialVersionUID = 1L;

	public TodoList()
	{
		super();
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(length=75)
	private String label;
	@Column(length=2048)
	private String description;
	private Boolean isComplete;
	@Column(length=50)
	private String department;
	private Priority priority;
  @ManyToOne(targetEntity=Production.class, optional=false, fetch=FetchType.LAZY)
  private Production production;
  @Temporal(value = TemporalType.DATE)
  private Date targetDate;
  @Temporal(value = TemporalType.TIMESTAMP)
  private Date lastUpdated;
  @OneToMany(fetch=FetchType.EAGER, targetEntity=TodoStatusUpdate.class, mappedBy="todoList")
  private Collection<TodoStatusUpdate> updates;

	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Boolean getIsComplete()
	{
		return isComplete;
	}
	public void setIsComplete(Boolean isComplete)
	{
		this.isComplete = isComplete;
	}
	public Priority getPriority()
	{
		return priority;
	}
	public void setPriority(Priority priority)
	{
		this.priority = priority;
	}
	public String getLabel()
	{
		return label;
	}
	public void setLabel(String label)
	{
		this.label = label;
	}
	public String getDepartment()
	{
		return department;
	}
	public void setDepartment(String grouping)
	{
		this.department = grouping;
	}
	public Production getProduction()
	{
		return production;
	}
	public void setProduction(Production production)
	{
		this.production = production;
	}
	public Date getLastUpdated()
	{
		return lastUpdated;
	}
	public void setLastUpdated(Date lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}
	public Date getTargetDate()
	{
		return targetDate;
	}
	public void setTargetDate(Date targetDate)
	{
		this.targetDate = targetDate;
	}
	public Collection<TodoStatusUpdate> getUpdates()
	{
		if(updates == null)
			return new ArrayList<TodoStatusUpdate>();
		return updates;
	}
	public void setUpdates(Collection<TodoStatusUpdate> updates)
	{
		this.updates = updates;
	}

	public String getHtmlUpdates()
	{
		Collection<TodoStatusUpdate> list = getUpdates();
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy hh:mm Z");
		builder.append("<p>");
		if(list != null && list.size() > 0)
			for(TodoStatusUpdate update : list)
			{
				builder.append("<span class=\"information\">");
				builder.append(format.format(update.getLastUpdated()));
				builder.append("</span><br/>");
				builder.append(update.getNotes());
				builder.append("<br/>");
			}
		builder.append("</p>");
		return builder.toString();
			
	}
}
