package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

/**
 * A simple supertype to group a set of budgets by a single department
 * 
 */
@Entity
public class Budget implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(targetEntity = Production.class)
	private Production production;
	@ManyToOne(targetEntity = Department.class)
	private Department department;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	private Double startingBudget;
	@OneToMany(targetEntity=BudgetEntry.class, mappedBy="budget")
	private Collection<BudgetEntry> entries;
	@Column(length=50)
	private String name;

	public Budget()
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

	public Production getProduction()
	{
		return production;
	}

	public void setProduction(Production production)
	{
		this.production = production;
	}

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Double getStartingBudget()
	{
		return startingBudget;
	}

	public void setStartingBudget(Double startingBudget)
	{
		this.startingBudget = startingBudget;
	}

	public Collection<BudgetEntry> getEntries()
	{
		return entries;
	}

	public void setEntries(Collection<BudgetEntry> entries)
	{
		this.entries = entries;
	}


	public String getName()
	{
		return name;
	}


	public void setName(String name)
	{
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return name;
	}
}
