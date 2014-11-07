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
 * An entry that ties to an initial budget entry and posts 
 * adjusting entries. This allows for a dynamic calculated field.
 * 
 */
@Entity
public class BudgetAdjustingEntry implements Serializable
{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(targetEntity = BudgetEntry.class, fetch = FetchType.EAGER)
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

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public BudgetEntry getBudgetEntry()
	{
		return budgetEntry;
	}

	public void setBudgetEntry(BudgetEntry budget)
	{
		this.budgetEntry = budget;
	}

	public Double getAmount()
	{
		return amount;
	}

	public void setAmount(Double amount)
	{
		this.amount = amount;
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

	public EntryType getEntryType()
	{
		return entryType;
	}

	public void setEntryType(EntryType entryType)
	{
		this.entryType = entryType;
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
}
