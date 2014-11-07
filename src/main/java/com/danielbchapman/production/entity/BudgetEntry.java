package com.danielbchapman.production.entity;

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

/**
 * A simple class that represents a line item in the budget. These are further composed of
 * AdjustingEntries
 * 
 */
@Entity
public class BudgetEntry extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	@ManyToOne(targetEntity = Budget.class)
	private Budget budget;
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "budgetEntry", cascade = CascadeType.ALL, orphanRemoval = true)
	private Collection<BudgetAdjustingEntry> adjustments;
	private Double amountInitial = 0.00;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	@Column(length = 125)
	private String note;
	private boolean estimated;
	private boolean confirmed;

	public BudgetEntry()
	{
		super();
	}

	public Collection<BudgetAdjustingEntry> getAdjustments()
	{
		return adjustments;
	}

	public Double getAmountInitial()
	{
		return amountInitial;
	}

	public Budget getBudget()
	{
		return budget;
	}

	/**
	 * Return an amount of the budget including all adjusting entries related to this entry.
	 * 
	 * @return The new value accounting for all adjusting entries
	 */
	public Double getCalculatedAmount()
	{
		if(adjustments != null && adjustments.size() > 0)
		{
			Double newValue = amountInitial;

			for(BudgetAdjustingEntry entry : adjustments)
				newValue += entry.getAmount() == null ? 0.00 : entry.getAmount();

			return newValue;
		}
		return amountInitial;
	}

	public Date getDate()
	{
		return date;
	}

	public String getNote()
	{
		return note;
	}

	public boolean isConfirmed()
	{
		return confirmed;
	}

	public boolean isEstimated()
	{
		return estimated;
	}

	public void setAdjustments(Collection<BudgetAdjustingEntry> adjustments)
	{
		this.adjustments = adjustments;
	}

	public void setAmountInitial(Double amountInitial)
	{
		this.amountInitial = amountInitial;
	}

	public void setBudget(Budget budget)
	{
		this.budget = budget;
	}

	public void setConfirmed(boolean confirmed)
	{
		this.confirmed = confirmed;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public void setEstimated(boolean estimated)
	{
		this.estimated = estimated;
	}

	public void setNote(String note)
	{
		this.note = note;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder b = new StringBuilder();

		b.append("Id:'");
		b.append(getId());
		b.append("' Amount Adjusted:'");
		b.append(getCalculatedAmount());
		b.append("' Budget:'");
		b.append(budget);
		b.append("' Date:'");
		b.append(date);
		b.append("' Note:'");
		b.append(note);
		b.append("' Estimate:'");
		b.append(estimated);
		return b.toString();
	}
}
