package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: TodoStatusUpdate
 * 
 */
@Entity
public class TodoStatusUpdate implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(length = 512)
	private String notes;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date lastUpdated;
	@ManyToOne(fetch = FetchType.EAGER, targetEntity = TodoList.class)
	private TodoList todoList;

	public TodoStatusUpdate()
	{
		super();
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getNotes()
	{
		return notes;
	}

	public void setNotes(String notes)
	{
		this.notes = notes;
	}

	public Date getLastUpdated()
	{
		return lastUpdated;
	}

	public void setLastUpdated(Date lastUpdated)
	{
		this.lastUpdated = lastUpdated;
	}

	public TodoList getTodoList()
	{
		return todoList;
	}

	public void setTodoList(TodoList todoList)
	{
		this.todoList = todoList;
	}

}
