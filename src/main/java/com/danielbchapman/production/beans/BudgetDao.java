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
import com.danielbchapman.production.entity.EntryType;
import com.danielbchapman.production.entity.Season;

@Stateless
public class BudgetDao implements BudgetDaoRemote
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//  @PersistenceContext
  EntityManager em = EntityInstance.getEm();
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.BudgetDaoRemote#saveBudget(com.danielbchapman.production.entity.Budget)
   */
  public void saveBudget(Budget budget)
  {
  	budget.setDate(new Date());
  	
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().begin();
  	
  	if(budget.getId() == null)
  		em.persist(budget);
  	else
  		em.merge(budget);
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().commit();
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.BudgetDaoRemote#saveAdjustingEntry(java.lang.Double, com.danielbchapman.production.entity.BudgetEntry, java.lang.String, com.danielbchapman.production.entity.EntryType)
   */
  public void saveAdjustingEntry(Double amount, BudgetEntry entry, String note, EntryType type)
  {
  	BudgetAdjustingEntry adjust = new BudgetAdjustingEntry();
  	adjust.setAmount(amount);
  	adjust.setNote(note);
  	adjust.setBudgetEntry(entry);
  	adjust.setDate(new Date());
  	adjust.setEntryType(type);
  	saveAdjustingEntry(adjust);
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.BudgetDaoRemote#saveAdjustingEntry(com.danielbchapman.production.entity.BudgetAdjustingEntry)
   */
  public void saveAdjustingEntry(BudgetAdjustingEntry adjust)
  {
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    
  	if(adjust.getId() == null)
  		em.persist(adjust);
  	else
  		em.merge(adjust);
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().commit();
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.BudgetDaoRemote#saveEntry(java.lang.Double, com.danielbchapman.production.entity.Budget, java.lang.String, com.danielbchapman.production.entity.EntryType)
   */
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
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.BudgetDaoRemote#saveEntry(com.danielbchapman.production.entity.BudgetEntry)
   */
  public void saveEntry(BudgetEntry entry)
  {
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
  	
  	if(entry.getId() != null)
  		em.merge(entry);
  	else
  		em.persist(entry);
  	
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().commit();
  }
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.BudgetDaoRemote#getBudget(java.lang.Long)
   */
  public Budget getBudget(Long id)
  {
  	return em.find(Budget.class, id);
  }
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.BudgetDaoRemote#getBudgetEntries(com.danielbchapman.production.entity.Budget)
   */
  @SuppressWarnings("unchecked")
	public ArrayList<BudgetEntry> getBudgetEntries(Budget budget)
  {
  	ArrayList<BudgetEntry> entries = new ArrayList<BudgetEntry>();
  	Query q = em.createQuery("SELECT e FROM BudgetEntry e WHERE e.budget = ?1 ORDER BY e.date ");
  	q.setParameter(1, budget);
  	
  	List<BudgetEntry> results = (List<BudgetEntry>)q.getResultList();
  	
  	if(results != null)
  		for(BudgetEntry e : results)
  			entries.add(e);
  	
  	return entries;
  }
  
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.BudgetDaoRemote#getAdjustingEntries(com.danielbchapman.production.entity.BudgetEntry)
   */
	@SuppressWarnings("unchecked")
	public ArrayList<BudgetAdjustingEntry> getAdjustingEntries(BudgetEntry entry)
	{
  	ArrayList<BudgetAdjustingEntry> entries = new ArrayList<BudgetAdjustingEntry>();
  	Query q = em.createQuery("SELECT e FROM BudgetAdjustingEntry e WHERE e.budgetEntry = ?1");
  	q.setParameter(1, entry);
  	
  	List<BudgetAdjustingEntry> results = (List<BudgetAdjustingEntry>)q.getResultList();
  	
  	if(results != null)
  		for(BudgetAdjustingEntry e : results)
  			entries.add(e);
  	
  	return entries;		
	}
	
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.BudgetDaoRemote#getAllBudgets(com.danielbchapman.production.entity.Production)
   */
  @SuppressWarnings("unchecked")
	public ArrayList<Budget> getAllBudgets(Season season)
  {
  	ArrayList<Budget> budgets = new ArrayList<Budget>();
  	Query q = em.createQuery("SELECT b FROM Budget b WHERE b.season = ?1 ORDER BY b.name");
  	q.setParameter(1, season);
  	
  	List<Budget> results = (List<Budget>)q.getResultList();
  	
  	if(results != null)
  		for(Budget e : results)
  			budgets.add(e);
  	
  	return budgets;  	
  }

	@Override
	public void deleteBudget(Budget b)
	{
		throw new RuntimeException("Deletion not implemented...do we need it?");
	}
}
