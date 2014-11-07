package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Budget;
import com.danielbchapman.production.entity.BudgetAdjustingEntry;
import com.danielbchapman.production.entity.BudgetEntry;
import com.danielbchapman.production.entity.EntryType;
import com.danielbchapman.production.entity.Season;

/**
 * A simple local interface for the BudgetDaoRemote
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Oct 1, 2009 2009
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Remote
public interface BudgetDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

	/**
	 * Cancel this entry in the database by adding another entry that qualifies is. If this is part of
	 * an estimated entry, it simply deletes the entry.
	 * 
	 * @param entry
	 */
	public abstract void cancelEntry(BudgetAdjustingEntry entry);

	/**
	 * Cancel an entry in the database, if this is an estimate it is removed. If it is a real value an
	 * adjusting entry is posted.
	 * 
	 * @param entry
	 */
	public abstract void cancelEntry(BudgetEntry entry);

	/**
	 * Deletes the budget from the database--this is for error correct, it is highly recommended that
	 * this be avoided.
	 * 
	 * @param b
	 *          the budget to delete.
	 * 
	 */
	public abstract void deleteBudget(Budget b);

	/**
	 * @param entry
	 *          the BudgetEntry to analyze
	 * @return a list of AdjustingEntries that support this entry. This is an active work around to a
	 *         lazy fetch that is failing.
	 */
	public abstract ArrayList<BudgetAdjustingEntry> getAdjustingEntries(BudgetEntry entry);

	/**
	 * @return a list of all the budgets
	 */
	public abstract ArrayList<Budget> getAllBudgets(Season season);

	/**
	 * @param id
	 * @return the budget based on its ID
	 */
	public abstract Budget getBudget(Long id);

	/**
	 * @param budget
	 *          the budget to analyze
	 * @return an ordered list based on Date for a budget--this is essentially the same as using the
	 *         built in collection, however this is orders in the date order instead of natural order.
	 */
	public abstract ArrayList<BudgetEntry> getBudgetEntries(Budget budget);

	/**
	 * Add an adjusting entry to this BudgetEntry.
	 * 
	 * @param adjust
	 *          the adjustment to add
	 */
	public abstract void saveAdjustingEntry(BudgetAdjustingEntry adjust);

	/**
	 * Add an adjusting entry to a line item.
	 * 
	 * @param amount
	 *          the amount to add
	 * @param entry
	 *          the entry to adjust
	 */
	public abstract void saveAdjustingEntry(Double amount, BudgetEntry entry, String note,
			EntryType type);

	/**
	 * Saves a new budget to the system.
	 * 
	 * @param budget
	 */
	public abstract void saveBudget(Budget budget);

	/**
	 * Saves an entry to the database.
	 * 
	 * @param entry
	 *          the entry to save
	 */
	public abstract void saveEntry(BudgetEntry entry);

	/**
	 * Saves a new entry to the budget.
	 * 
	 * @param amount
	 *          the amount to add
	 * @param budget
	 *          the budget to add an entry to
	 * @param note
	 *          the notes for this entry (logs)
	 * @param estimated
	 *          <b>true</b> if this is an estimate, otherwise <b>false</b>
	 * 
	 */
	public abstract void saveEntry(Double amount, Budget budget, String note, boolean estimated);
}