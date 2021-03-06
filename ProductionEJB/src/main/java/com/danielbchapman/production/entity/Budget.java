package com.danielbchapman.production.entity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * A simple supertype to group a set of budgets by a single department
 * 
 */
@Entity
public class Budget extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	@ManyToOne(targetEntity = Department.class)
	private Department department;
	@OneToMany(targetEntity = BudgetEntry.class, mappedBy = "budget", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Collection<BudgetEntry> entries;
	@Column(length = 64, unique = true)
	private String name = "";
	@ManyToOne(targetEntity = Season.class)
	private Season season;
	private Double startingBudget = 0.00;

	public Budget()
	{
		super();
	}

	/**
	 * @return a calculation of the total amount of all entries assigned to this budget This does not
	 */
	@Transient
	public double getCurrentTotals()
	{
		double ret = 0.00;

		for(BudgetEntry e : getEntries())
			ret += e.getCalculatedAmount();

		return ret;
	}

	public Date getDate()
	{
		return date;
	}

	public Department getDepartment()
	{
		return department;
	}

	public Collection<BudgetEntry> getEntries()
	{
		return entries;
	}

	public String getName()
	{
		return name;
	}

	public Season getSeason()
	{
		return season;
	}

	public Double getStartingBudget()
	{
		return startingBudget;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
	}

	public void setEntries(Collection<BudgetEntry> entries)
	{
		this.entries = entries;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public void setSeason(Season season)
	{
		this.season = season;
	}

	public void setStartingBudget(Double startingBudget)
	{
		this.startingBudget = startingBudget;
	}

	@Transient
	public BigDecimal getEstimatedTotals()
	{
		BigDecimal total = BigDecimal.ZERO;
		
		for(BudgetEntry e : getEntries())
			if(e.isEstimated())
			total = total.add(BigDecimal.valueOf(e.getCalculatedAmount()));
		
		return total;
	}
	
	@Transient
	public BigDecimal getTotalsNotEsimated()
	{
		BigDecimal total = BigDecimal.ZERO;
		
		for(BudgetEntry e : getEntries())
			if(!e.isEstimated())
				total = total.add(BigDecimal.valueOf(e.getCalculatedAmount()));
		
		return total;		
	}
	
	@Transient
	public BigDecimal getForecast()
	{
		return BigDecimal.valueOf(getCurrentTotals());
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return name;
	}
}
