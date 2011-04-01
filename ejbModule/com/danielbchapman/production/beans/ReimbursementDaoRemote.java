package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Department;
import com.danielbchapman.production.entity.Employee;
import com.danielbchapman.production.entity.Reimbursement;

/**
 * A simple access object to work with Reimbursements. It essentially uses
 * the BudgetEntryDao and keeps a log of itself.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Feb 4, 2011 2011
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
@Remote
public interface ReimbursementDaoRemote
{
  /**
   * Add a new reimbursement to the system
   * @param amount
   * @param emplyoee <Return Description>  
   * 
   */
  public void addReimbursement(double amount, Employee emplyoee, Department department, String description);
  
  /**
   * Report that reimbursements have been sent in.
   * @param reimbursement <Return Description>  
   * 
   */
  public void reportReimbursement(Reimbursement reimbursement);
  
  /**
   * @return a list of all active reimbursements  
   * 
   */
  public ArrayList<Reimbursement> getAllReimbursements();
  
  /**
   * @return a list of all reimbursements for an employee  
   * 
   */
  public ArrayList<Reimbursement> getReimbursementsForEmployee(Employee employee);
}
