package com.danielbchapman.production.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * An entry that ties to an initial budget entry and posts adjusting entries. This allows for a
 * dynamic calculated field.
 * 
 */
@Entity
public class BudgetAdjustingEntry extends BaseEntity
{

	private static final long serialVersionUID = 1L;

	@ManyToOne(targetEntity = BudgetEntry.class, fetch = FetchType.EAGER, cascade = {
			CascadeType.REFRESH, CascadeType.MERGE })
	private BudgetEntry budgetEntry;
	private Double amount;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date date;
	@Column(length = 200)
	private String note;
	private EntryType entryType;

	public BudgetAdjustingEntry()
	{
		super();
	}

	public Double getAmount()
	{
		return amount;
	}

	public BudgetEntry getBudgetEntry()
	{
		return budgetEntry;
	}

	public Date getDate()
	{
		return date;
	}

	public EntryType getEntryType()
	{
		return entryType;
	}

	public String getNote()
	{
		return note;
	}

	/**
	 * @return <b>true</b> if this is confirms and <b>false</b> if not
	 */
	public boolean isConfirmed()
	{
		if(EntryType.CONFIRMED.equals(entryType))
			return true;
		return false;
	}

	public void setAmount(Double amount)
	{
		this.amount = amount;
	}

	public void setBudgetEntry(BudgetEntry budget)
	{
		this.budgetEntry = budget;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public void setEntryType(EntryType entryType)
	{
		this.entryType = entryType;
	}

	public void setNote(String note)
	{
		this.note = note;
	}
}
