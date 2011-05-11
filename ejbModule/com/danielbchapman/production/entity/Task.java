package com.danielbchapman.production.entity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.CascadeType;


/**
 * A simple todo list that stores completed and
 * incomplete items in a format that is easily viewed by 
 * the end user. This allows for simple tracking locally
 * and should be usable as a web service.
 * 
 */
@Entity
public class Task extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	private TaskUser assignedTo;
	private Boolean complete;
	@Column(length=50)
	private String department;
	@Lob
	private String description;
	@Column(length=75)
	private String label;
	@Temporal(value = TemporalType.TIMESTAMP)
  private Date lastUpdated;
	@Column(length=50)
  private String lastUpdatedBy;
	private Priority priority;
	@ManyToOne(targetEntity=Production.class, optional=false, fetch=FetchType.LAZY)
  private Production production;
	@Temporal(value = TemporalType.DATE)
  private Date targetDate;
	@OneToMany(cascade=CascadeType.REMOVE,fetch=FetchType.EAGER, targetEntity=TaskStatusUpdate.class, mappedBy="task")
  private Collection<TaskStatusUpdate> updates;
	private Task parentTask;
	
  public Task()
	{
		super();
	}
  
	public TaskUser getAssignedTo()
	{
		return assignedTo;
	}
	
	public Boolean getComplete()
	{
		return complete;
	}
	
  public String getDepartment()
	{
		return department;
	}
  
  public String getDescription()
	{
		return description;
	}
  
  public String getLabel()
	{
		return label;
	}
  
  public Date getLastUpdated()
	{
		return lastUpdated;
	}

	public String getLastUpdatedBy()
	{
		return lastUpdatedBy;
	}
	
	public Task getParentTask()
	{
		return parentTask;
	}
	
	public Priority getPriority()
	{
		return priority;
	}
	
	public Production getProduction()
	{
		return production;
	}
	
	public Date getTargetDate()
	{
		return targetDate;
	}
	
	public Collection<TaskStatusUpdate> getUpdates()
	{
		if(updates == null)
			return new ArrayList<TaskStatusUpdate>();
		return updates;
	}
	
	public void setAssignedTo(TaskUser assignedTo)
	{
		this.assignedTo = assignedTo;
	}
	
	public void setComplete(Boolean complete)
	{
		this.complete = complete;
	}
	
	public void setDepartment(String grouping)
	{
		this.department = grouping;
	}
	
	public void setDescription(String description)
	{
		this.description = description;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public void setLastUpdated(Date lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}
	
	public void setLastUpdatedBy(String lastUpdatedBy)
	{
		this.lastUpdatedBy = lastUpdatedBy;
	}
	
	public void setParentTask(Task parentTask)
	{
		this.parentTask = parentTask;
	}
	
	public void setPriority(Priority priority)
	{
		this.priority = priority;
	}
	
	public void setProduction(Production production)
	{
		this.production = production;
	}
	
	public void setTargetDate(Date targetDate)
	{
		this.targetDate = targetDate;
	}
	
	public void setUpdates(Collection<TaskStatusUpdate> updates)
	{
		this.updates = updates;
	}
	
	/* (non-Javadoc)
	 * @see com.danielbchapman.production.entity.BaseEntity#toString()
	 */
	public String toString()
	{
		StringBuilder buf = new StringBuilder();
		buf.append("Label [");
		buf.append(label);
		buf.append("] ");
		
		buf.append("Complete [");
		buf.append(complete);
		buf.append("] ");
		
		buf.append("Department [");
		buf.append(department);
		buf.append("] ");
		
		buf.append("Priority [");
		buf.append(priority == null ? "NONE" : priority.toString());
		buf.append("] ");
		
		buf.append("Production [");
		buf.append(production);
		buf.append("] ");
		
		buf.append("Target [");
		buf.append(targetDate);
		buf.append("] ");
		
		buf.append("Last Updated [");
		buf.append(lastUpdated);
		
		buf.append(" (");
		buf.append(lastUpdatedBy);
		buf.append(")] ");
		
		buf.append("Description [");
		buf.append(description);
		buf.append("]");
		
		return buf.toString();
	}
}
