package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * The main top level entity for a set of PettyCashEntry entity.
 *************************************************************************** 
 * @author Daniel B. Chapman <br />
 *         <i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Oct 1, 2009
 * @version 2 Development
 * @link http://www.lightassistant.com
 */
@Entity
public class PettyCash implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  @Column(length = 50)
  private String name;
  private Double amount;
  @OneToMany(fetch = FetchType.EAGER, mappedBy = "pettyCash")
  private Collection<PettyCashEntry> pettyCashEntries;
  private Boolean reconciled = false;

  public Double getAmount()
  {
    return amount;
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

  public Double getCalculatedAmount()
  {
    double ret = amount == null ? 0.00 : amount.doubleValue();
    if (pettyCashEntries == null)
      return amount == null ? 0.00 : amount.doubleValue();

    for (PettyCashEntry e : pettyCashEntries)
      ret += e.getBudgetEntry().getCalculatedAmount();// large calculation

    return ret;
  }

  public Boolean getReconciled()
  {
    return reconciled;
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
