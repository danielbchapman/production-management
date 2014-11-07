package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * The main top level entity for a set of PettyCashEntry entity.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Oct 1, 2009
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Entity
public class PettyCash implements Serializable
{
  private static final long serialVersionUID = 1L;

  private Double amount;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(length = 50)
  private String name;
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "pettyCash")
  private Collection<PettyCashEntry> pettyCashEntries;
  private Boolean reconciled = false;

  public Double getAmount()
  {
    return amount;
  }

  public Double getCalculatedAmount()
  {
    double ret = amount == null ? 0.00 : amount.doubleValue();
    if (pettyCashEntries == null)
      return amount == null ? 0.00 : amount.doubleValue();

    for (PettyCashEntry e : pettyCashEntries)
      ret += e.getBudgetEntry().getCalculatedAmount();// large calculation

    return ret;
  }

  public Long getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public Collection<PettyCashEntry> getPettyCashEntries()
  {
    return pettyCashEntries;
  }

  public Boolean getReconciled()
  {
    return reconciled;
  }

  public void setAmount(Double amount)
  {
    this.amount = amount;
  }

  public void setId(Long id)
  {
    this.id = id;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public void setPettyCashEntries(Collection<PettyCashEntry> pettyCashEntries)
  {
    this.pettyCashEntries = pettyCashEntries;
  }

  public void setReconciled(Boolean reconciled)
  {
    this.reconciled = reconciled;
  }

  /*
   * (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return name == null ? "null" : name.replace(' ', '_');
  }
}
