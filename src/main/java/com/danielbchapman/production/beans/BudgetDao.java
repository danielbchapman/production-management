package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Budget;
import com.danielbchapman.production.entity.BudgetAdjustingEntry;
import com.danielbchapman.production.entity.BudgetEntry;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.EntryType;
import com.danielbchapman.production.entity.Season;

@Stateless
public class BudgetDao implements BudgetDaoRemote
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// @PersistenceContext
	EntityManager em = EntityInstance.getEm();

	@Override
	public void cancelEntry(BudgetAdjustingEntry entry)
	{
		if(entry.getBudgetEntry().isEstimated())
		{
			BudgetEntry parent = entry.getBudgetEntry();
			parent.getAdjustments().remove(entry);
			saveEntry(parent); // explicit cascade
			return;
		}

		entry.setEntryType(EntryType.CONFIRMED);
		saveAdjustingEntry(-entry.getAmount(), entry.getBudgetEntry(), "[CANCEL] " + entry.getNote(),
				EntryType.PENDING);
		saveAdjustingEntry(entry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.BudgetDaoRemote#cancelEntry(com.danielbchapman.production
	 * .entity.BudgetEntry)
	 */
	@Override
	public void cancelEntry(BudgetEntry entry)
	{
		if(entry.isEstimated())
		{
			for(BudgetAdjustingEntry adj : getAdjustingEntries(entry))
				EntityInstance.deleteObject(adj);

			EntityInstance.deleteObject(entry);
			return;
		}

		for(BudgetAdjustingEntry adj : getAdjustingEntries(entry))
			if(!adj.isConfirmed())
			{
				adj.setEntryType(EntryType.CONFIRMED);
				saveAdjustingEntry(adj);
			}

		saveAdjustingEntry(-entry.getCalculatedAmount(), entry, "[CANCEL] " + entry.getNote(),
				EntryType.PENDING);
		entry.setConfirmed(true);
		saveEntry(entry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.BudgetDaoRemote#deleteBudget(com.danielbchapman.production
	 * .entity.Budget)
	 */
	@Override
	public void deleteBudget(Budget b)
	{
		throw new RuntimeException("Deletion not implemented...do we need it?");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.BudgetDaoRemote#getAdjustingEntries(com.danielbchapman.
	 * production.entity.BudgetEntry)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ArrayList<BudgetAdjustingEntry> getAdjustingEntries(BudgetEntry entry)
	{
		ArrayList<BudgetAdjustingEntry> entries = new ArrayList<BudgetAdjustingEntry>();
		Query q = em.createQuery("SELECT e FROM BudgetAdjustingEntry e WHERE e.budgetEntry = ?1");
		q.setParameter(1, entry);

		List<BudgetAdjustingEntry> results = q.getResultList();

		if(results != null)
			for(BudgetAdjustingEntry e : results)
				entries.add(e);

		return entries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.BudgetDaoRemote#getAllBudgets(com.danielbchapman.production
	 * .entity.Production)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ArrayList<Budget> getAllBudgets(Season season)
	{
		ArrayList<Budget> budgets = new ArrayList<Budget>();
		Query q = em.createQuery("SELECT b FROM Budget b WHERE b.season = ?1 ORDER BY b.name");
		q.setParameter(1, season);

		List<Budget> results = q.getResultList();

		if(results != null)
			for(Budget e : results)
				budgets.add(e);

		return budgets;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.BudgetDaoRemote#getBudget(java.lang.Long)
	 */
	@Override
	public Budget getBudget(Long id)
	{
		return em.find(Budget.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.BudgetDaoRemote#getBudgetEntries(com.danielbchapman.production
	 * .entity.Budget)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ArrayList<BudgetEntry> getBudgetEntries(Budget budget)
	{
		ArrayList<BudgetEntry> entries = new ArrayList<BudgetEntry>();
		Query q = em.createQuery("SELECT e FROM BudgetEntry e WHERE e.budget = ?1 ORDER BY e.date ");
		q.setParameter(1, budget);

		List<BudgetEntry> results = q.getResultList();

		if(results != null)
			for(BudgetEntry e : results)
				entries.add(e);

		return entries;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.BudgetDaoRemote#saveAdjustingEntry(com.danielbchapman.
	 * production.entity.BudgetAdjustingEntry)
	 */
	@Override
	public void saveAdjustingEntry(BudgetAdjustingEntry adjust)
	{
		if(adjust.getId() != null)
			EntityInstance.saveObject(adjust);
		else
		{
			adjust.getBudgetEntry().getAdjustments().add(adjust);
			EntityInstance.saveObject(adjust.getBudgetEntry());
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.BudgetDaoRemote#saveAdjustingEntry(java.lang.Double,
	 * com.danielbchapman.production.entity.BudgetEntry, java.lang.String,
	 * com.danielbchapman.production.entity.EntryType)
	 */
	@Override
	public void saveAdjustingEntry(Double amount, BudgetEntry entry, String note, EntryType type)
	{
		BudgetAdjustingEntry adjust = new BudgetAdjustingEntry();
		adjust.setAmount(amount);
		adjust.setNote(note);
		adjust.setBudgetEntry(entry);
		adjust.setDate(new Date());
		adjust.setEntryType(type);
		if(entry.getAdjustments() == null)
			entry.setAdjustments(new ArrayList<BudgetAdjustingEntry>());
		entry.getAdjustments().add(adjust);

		saveEntry(entry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.BudgetDaoRemote#saveBudget(com.danielbchapman.production
	 * .entity.Budget)
	 */
	@Override
	public void saveBudget(Budget budget)
	{
		budget.setDate(new Date());
		EntityInstance.saveObject(budget);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.BudgetDaoRemote#saveEntry(com.danielbchapman.production
	 * .entity.BudgetEntry)
	 */
	@Override
	public void saveEntry(BudgetEntry entry)
	{
		EntityInstance.saveObject(entry);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.BudgetDaoRemote#saveEntry(java.lang.Double,
	 * com.danielbchapman.production.entity.Budget, java.lang.String,
	 * com.danielbchapman.production.entity.EntryType)
	 */
	@Override
	public void saveEntry(Double amount, Budget budget, String note, boolean estimated)
	{
		BudgetEntry entry = new BudgetEntry();
		entry.setAmountInitial(amount);
		entry.setDate(new Date());
		entry.setNote(note);
		entry.setBudget(budget);
		entry.setEstimated(estimated);

		saveEntry(entry);
	}
}
