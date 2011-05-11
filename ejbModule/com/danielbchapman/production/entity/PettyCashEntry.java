package com.danielbchapman.production.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * A simple table to track a set of budget entries. These are assigned to a 
 * specific budget at entry, but are tracked on a single list until "reconciled" at
 * which point the budget entries are confirmed and the petty cash records are cleared.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Oct 1, 2009
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
//FIXME Java Doc Needed
@Entity
public class PettyCashEntry implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Long id;
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private PettyCash pettyCash;
  private BudgetEntry budgetEntry;
  
  public BudgetEntry getBudgetEntry() 
  {
    return budgetEntry;
  }
  public Long getId()
  {
    return id;
  }
  
  public PettyCash getPettyCash()
  {
    return pettyCash;
  }
  public void setBudgetEntry(BudgetEntry budgetEntry)
  {
    this.budgetEntry = budgetEntry;
  }
  public void setId(Long id)
  {
    this.id = id;
  }
  public void setPettyCash(PettyCash pettyCash)
  {
    this.pettyCash = pettyCash;
  }
}
