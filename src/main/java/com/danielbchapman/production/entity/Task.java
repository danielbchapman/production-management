package com.danielbchapman.production.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * A task is a collection of records that model something someone needs to do either on a daily,
 * event or one time basis. This can be a simple checklist or something more complex like a series
 * of long jobs. Each task has a log for comments history.
 * </p>
 * 
 * <p>
 * Tasks are recursive tree structures (many to one) in a reversed singly linked list and thus can
 * easily be attached to other tasks.
 * <p>
 * 
 * @see {@link TaskStatusUpdate}
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since May 12, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Entity
public class Task extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	/**
	 * Look to (GenericLoginEJB) this is a copy of that field.
	 * 
	 * @see {@link com.danielbchapman.jboss.login.User#user}
	 */
	@Column(length = 80)
	private String assignedTo;
	private boolean complete;
	@Column(length = 50)
	private String department = "";
	@Lob
	private String description = "";
	@Column(length = 75)
	private String label = "";
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdated;
	@Column(length = 50)
	private String lastUpdatedBy = "";
	private Task parentTask;
	private Priority priority;
	@ManyToOne(targetEntity = Season.class, optional = false, fetch = FetchType.LAZY)
	private Season season;
	@Temporal(value = TemporalType.DATE)
	private Date targetDate;
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, targetEntity = TaskStatusUpdate.class, mappedBy = "task")
	private Collection<TaskStatusUpdate> updates;

	public Task()
	{
		super();
	}

	public String getAssignedTo()
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

	public Season getSeason()
	{
		return season;
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

	public void setAssignedTo(String assignedTo)
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

	public void setSeason(Season season)
	{
		this.season = season;
	}

	public void setTargetDate(Date targetDate)
	{
		this.targetDate = targetDate;
	}

	public void setUpdates(Collection<TaskStatusUpdate> updates)
	{
		this.updates = updates;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.entity.BaseEntity#toString()
	 */
	@Override
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
		buf.append(season);
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
