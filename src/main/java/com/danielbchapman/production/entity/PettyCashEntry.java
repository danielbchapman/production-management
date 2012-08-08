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
public class PettyCashEntry extends BaseEntity
{
  private static final long serialVersionUID = 1L;
  private BudgetEntry budgetEntry;
  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  private PettyCash pettyCash;
  
  public BudgetEntry getBudgetEntry() 
  {
    return budgetEntry;
  }

  public PettyCash getPettyCash()
  {
    return pettyCash;
  }
  public void setBudgetEntry(BudgetEntry budgetEntry)
  {
    this.budgetEntry = budgetEntry;
  }

  public void setPettyCash(PettyCash pettyCash)
  {
    this.pettyCash = pettyCash;
  }
}
