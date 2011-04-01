package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

/**
 * A simple class that represents a line item in the 
 * budget. These are further composed of AdjustingEntries
 * 
 */
@Entity
public class BudgetEntry implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(targetEntity = Budget.class)
	private Budget budget;
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "budgetEntry")
	private Collection<BudgetAdjustingEntry> adjustments;
	private Double amountInitial;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	@Column(length = 125)
	private String note;
	private EntryType entryType;

	public BudgetEntry()
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

	public Budget getBudget()
	{
		return budget;
	}

	public void setBudget(Budget budget)
	{
		this.budget = budget;
	}

	public Collection<BudgetAdjustingEntry> getAdjustments()
	{
		return adjustments;
	}

	public void setAdjustments(Collection<BudgetAdjustingEntry> adjustments)
	{
		this.adjustments = adjustments;
	}

	public Double getAmountInitial()
	{
		return amountInitial;
	}

	public void setAmountInitial(Double amountInitial)
	{
		this.amountInitial = amountInitial;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public String getNote()
	{
		return note;
	}

	public void setNote(String note)
	{
		this.note = note;
	}
	
	/**
	 * Return an amount of the budget including all adjusting entries
	 * related to this entry.
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

	public EntryType getEntryType()
	{
		return entryType;
	}

	public void setEntryType(EntryType entryType)
	{
		this.entryType = entryType;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
	  StringBuilder b = new StringBuilder();
	  
	  b.append("Id:'");
	  b.append(id);
	  b.append("' Amount Adjusted:'");
    b.append(getCalculatedAmount());
	  b.append("' Budget:'");
	  b.append(budget);
	  b.append("' Date:'");
	  b.append(date);
	  b.append("' Note:'");
	  b.append(note);
	  b.append("' EntryType:'");
	  b.append(entryType)
	  ;
	  return b.toString();
	}
}
