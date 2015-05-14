package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Budget;
import com.danielbchapman.production.entity.Department;
import com.danielbchapman.production.entity.Reimbursement;

/**
 * A simple access object to work with Reimbursements. It essentially uses
 * the BudgetEntryDao and keeps a log of itself.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Feb 4, 2011 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface ReimbursementDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;
  /**
   * Add a new reimbursement to the system
   * @param amount
   * @param emplyoee <Return Description>  
   * 
   */
  public void addReimbursement(double amount, String username, Department department, String description);
  
  /**
   * Report that reimbursements have been sent in.
   * @param reimbursement the reimbursement to confirm (finalize)
   * @param budget the budget to assign this entry to.
   */
  public void confirmReimbursement(Reimbursement reimbursement, Budget budget);
  
  /**
   * @return a list of all active reimbursements  
   * 
   */
  public ArrayList<Reimbursement> getAllReimbursements();
  
  /**
   * @return a list of all reimbursements for an employee  
   * 
   */
  public ArrayList<Reimbursement> getReimbursementsForUser(String username);
}
