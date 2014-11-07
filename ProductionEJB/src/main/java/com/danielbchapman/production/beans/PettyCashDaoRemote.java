package com.danielbchapman.production.beans;


import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Budget;
import com.danielbchapman.production.entity.PettyCash;
import com.danielbchapman.production.entity.PettyCashEntry;
@Remote
public interface PettyCashDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

  /**
   * Reconcile a petty cash issuance by closing it so it can not be further modified 
   * and by printing the associated form. The entries can be changed by modifying the 
   * actual budgets, but the line items can not be removed. Entries should be corrected
   * prior to reconciliation.
   * @param pettyCash The PettyCash to reconcile.  
   * 
   */
  public abstract void reconcilePettyCash(PettyCash pettyCash);

  public abstract void addPettyCash(String name, Double amount);

  /**
   * Add an entry for the current petty cash supply and assign it 
   * to a specific budget of choice. Once reconciled these will all
   * be confirmed and the supply will be emptied.
   * 
   * @param budget The budget to assign to
   * @param pettyCash The petty cash supply
   * @param amount The amount of the transaction
   * @param memo A note describing the amount.
   */
  public abstract void addPettyCashEntry(Budget budget, PettyCash pettyCash,
      Double amount, String memo);

  /**
   * @return a list of all petty cash issues by name
   */
  public abstract ArrayList<PettyCash> getPettyCashIssues();
  
  /**
   * @return a list of all petty cash issues by name that are closed (historical)
   */
  public abstract ArrayList<PettyCash> getPettyCashIssuesClosed();

  /**
   * LOLCATS WANT UR MONIES
   * @param id
   * @return the PettyCash for the id
   */
  public abstract PettyCash canHasPettyCash(Long id);

  /**
   * @param petty the petty cash to issue
   * @return a list of entries for this PettyCash issue
   */
  public abstract ArrayList<PettyCashEntry> getPettyCashEntries(PettyCash petty);

}