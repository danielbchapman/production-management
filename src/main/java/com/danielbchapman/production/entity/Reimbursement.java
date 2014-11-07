package com.danielbchapman.production.entity;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * A reimbursement links to an BudgetEntry so that it can be tied to a department
 * but at the same time easily reported based on who needs Reimbursement.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Feb 4, 2011 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Entity
@Data
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true, doNotUseGetters=true)
public class Reimbursement extends BaseEntity
{
  private static final long serialVersionUID = 1L;
  private String user;
  private BudgetEntry entry;
  private Boolean reported;
}
