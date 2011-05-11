package com.danielbchapman.production.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: TodoStatusUpdate
 * 
 */
@Entity
public class TaskStatusUpdate extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	@Lob
	private String notes;
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

	public void setReminder(Task reminder)
	{
		this.task = reminder;
	}

}
