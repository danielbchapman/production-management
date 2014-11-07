package com.danielbchapman.production.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * <p>
 * The TaskStatusUpdate is basically a log to be attached to a Task showing the current status of
 * the task. It is essentially a piece of markup that allows a use to leave useful information.
 * </p>
 * <p>
 * And example of this would be to leave something like: <i>"I took a phone call and am waiting for
 * a response from the Scenic Designer".</i>
 * </p>
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since May 12, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Entity
public class TaskStatusUpdate extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	@Lob
	private String notes = "";
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdated;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = Task.class)
	private Task task;

	public TaskStatusUpdate()
	{
		super();
	}

	public Date getLastUpdated()
	{
		return lastUpdated;
	}

	public String getNotes()
	{
		return notes;
	}

	public Task getTask()
	{
		return task;
	}

	public void setLastUpdated(Date lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public void setTask(Task task)
	{
		this.task = task;
	}

}
