package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Stateless;

import com.danielbchapman.production.entity.Budget;
import com.danielbchapman.production.entity.BudgetEntry;
import com.danielbchapman.production.entity.Department;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Reimbursement;

/**
 * Session Bean implementation class ReimbursementDao
 */
@Stateless
public class ReimbursementDao implements ReimbursementDaoRemote
{
	private static final long serialVersionUID = 1L;
//	EntityManager em = EntityInstance.getEm();
  /**
   * Default constructor.
   */
  public ReimbursementDao()
  {
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ReimbursementDaoRemote#addReimbursement(double, com.danielbchapman.production.entity.Employee)
   */
  @Override
  public void addReimbursement(double amount, String username, Department department, String description)
  {
    if(username == null)
      throw new NullPointerException("Username is required");
    
    Reimbursement reimbursement = new Reimbursement();
    
    BudgetEntry entry = new BudgetEntry();
    entry.setDate(new Date());
    entry.setAmountInitial(amount);
    
    reimbursement.setUser(username);
    reimbursement.setReported(false);
    
    EntityInstance.saveObjects(entry, reimbursement);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ReimbursementDaoRemote#reportReimbursement(com.danielbchapman.production.entity.Reimbursement)
   */
  @Override
  public void confirmReimbursement(Reimbursement reimbursement, Budget budget)
  {
  	reimbursement.getEntry().setBudget(budget);
    reimbursement.setReported(true);
    EntityInstance.saveObject(reimbursement.getEntry());
    EntityInstance.saveObject(reimbursement);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ReimbursementDaoRemote#getAllReimbursements()
   */
  @Override
  public ArrayList<Reimbursement> getAllReimbursements()
  {
  	return EntityInstance.getResultList("SELECT r FROM Reimbursement r", Reimbursement.class);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ReimbursementDaoRemote#getReimbursementsForEmployee(com.danielbchapman.production.entity.Employee)
   */
  @Override
  public ArrayList<Reimbursement> getReimbursementsForUser(String username)
  {
  	return EntityInstance.getResultList("SELECT r FROM Reimbursement r WHERE r.user = ?1", Reimbursement.class, username);
  }

}
