package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Budget;
import com.danielbchapman.production.entity.BudgetEntry;
import com.danielbchapman.production.entity.EntryType;
import com.danielbchapman.production.entity.PettyCash;
import com.danielbchapman.production.entity.PettyCashEntry;

/**
 * A simple set of methods to track and reconcile petty cash.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Oct 1, 2009
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Stateless
public class PettyCashDao implements PettyCashDaoRemote
{
//  @PersistenceContext
  EntityManager em = EntityInstance.getEm();
  
  public PettyCashDao()
  {
    
  }
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.PettyCashDaoRemote#addPettyCash(java.lang.String, java.lang.Double)
   */
  public void addPettyCash(String name, Double amount)
  {
    PettyCash cash = new PettyCash();
    cash.setName(name);
    cash.setAmount(amount == null ? 0.00 : amount);
    
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    
    em.persist(cash);
    
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().commit();
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.PettyCashDaoRemote#addPettyCashEntry(com.danielbchapman.production.entity.Budget, com.danielbchapman.production.entity.PettyCash, java.lang.Double, java.lang.String)
   */
  public void addPettyCashEntry(Budget budget, PettyCash pettyCash, Double amount, String memo)
  {
    String adjustedMemo = "Petty Cash - " + memo;
    
    BudgetEntry entry = new BudgetEntry();
    entry.setBudget(budget);
    entry.setAmountInitial(amount);
    entry.setDate(new Date());
    entry.setEstimated(false);
    entry.setNote(adjustedMemo);
    
    PettyCashEntry petty = new PettyCashEntry();
    petty.setBudgetEntry(entry);
    petty.setPettyCash(pettyCash);
    
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    
    em.persist(entry);
    em.persist(petty);
    
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().commit();
  }
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.PettyCashDaoRemote#canHasPettyCash(java.lang.Long)
   */
  public PettyCash canHasPettyCash(Long id)
  {
    return em.find(PettyCash.class, id);
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.PettyCashDaoRemote#getPettyCashEntries(com.danielbchapman.production.entity.PettyCash)
   */
  @SuppressWarnings("unchecked")
  public ArrayList<PettyCashEntry> getPettyCashEntries(PettyCash petty)
  {
    Query q = em.createQuery("SELECT p FROM PettyCashEntry p WHERE p.pettyCash = ?1");
    q.setParameter(1, petty);
    List<PettyCashEntry> results = (List<PettyCashEntry>)q.getResultList();
    ArrayList<PettyCashEntry> ret = new ArrayList<PettyCashEntry>();
    
    if(results != null)
      for(PettyCashEntry p : results)
        ret.add(p);
    
    return ret;    
  }

  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.PettyCashDaoRemote#getPettyCashIssues()
   */
  @SuppressWarnings("unchecked")
  public ArrayList<PettyCash> getPettyCashIssues()
  {
    Query q = em.createQuery("SELECT p FROM PettyCash p WHERE p.reconciled = false ORDER BY p.name");
    List<PettyCash> results = (List<PettyCash>)q.getResultList();
    ArrayList<PettyCash> ret = new ArrayList<PettyCash>();
    
    if(results != null)
      for(PettyCash p : results)
        ret.add(p);
    
    return ret;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.remote.PettyCashDaoRemote#getPettyCashIssuesClosed()
   */
  @SuppressWarnings("unchecked")
  public ArrayList<PettyCash> getPettyCashIssuesClosed()
  {
    Query q = em.createQuery("SELECT p FROM PettyCash p WHERE p.reconciled = true ORDER BY p.name");
    List<PettyCash> results = (List<PettyCash>)q.getResultList();
    ArrayList<PettyCash> ret = new ArrayList<PettyCash>();
    
    if(results != null)
      for(PettyCash p : results)
        ret.add(p);
    
    return ret;
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.PettyCashDaoRemote#reconcilePettyCash(com.danielbchapman.production.entity.PettyCash)
   */
  public void reconcilePettyCash(PettyCash pettyCash)
  {
    //TODO this could feasibly confirm all entries and mark them as "unmodifiable"
    pettyCash.setReconciled(true);
    
    if(!Config.CONTAINER_MANAGED)  
      em.getTransaction().begin();
    
    em.merge(pettyCash);
    
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().commit();
  }
 
}
