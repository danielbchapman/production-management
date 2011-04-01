package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.BudgetEntry;
import com.danielbchapman.production.entity.Department;
import com.danielbchapman.production.entity.Employee;
import com.danielbchapman.production.entity.EntryType;
import com.danielbchapman.production.entity.Reimbursement;

/**
 * Session Bean implementation class ReimbursementDao
 */
@Stateless
public class ReimbursementDao implements ReimbursementDaoRemote
{
  EntityManager em = EntityInstance.getEm();
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
  public void addReimbursement(double amount, Employee employee, Department department, String description)
  {
    if(employee == null)
      throw new NullPointerException("Employee is required");
    
    Reimbursement reimbursement = new Reimbursement();
    
    BudgetEntry entry = new BudgetEntry();
    entry.setDate(new Date());
    entry.setEntryType(EntryType.PENDING);
    entry.setAmountInitial(amount);
    
    reimbursement.setEmployee(employee);
    reimbursement.setReported(false);
    
    if(!Config.CONTAINER_MANAGED)//FIXME -> This should be centralized, all entities should extend the base
      em.getTransaction().begin();
    
    em.persist(entry);
    em.persist(reimbursement);
    
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().commit();
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ReimbursementDaoRemote#reportReimbursement(com.danielbchapman.production.entity.Reimbursement)
   */
  @Override
  public void reportReimbursement(Reimbursement reimbursement)
  {
    reimbursement.setReported(true);
    EntityInstance.saveObject(reimbursement);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ReimbursementDaoRemote#getAllReimbursements()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Reimbursement> getAllReimbursements()
  {
    Query q = em.createQuery("SELECT r FROM Reimbursement r");
    List<Reimbursement> results = (List<Reimbursement>)q.getResultList();
    ArrayList<Reimbursement> ret = new ArrayList<Reimbursement>();
    if(results != null)
      for(Reimbursement r : results)
        ret.add(r);
    
    return ret;
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ReimbursementDaoRemote#getReimbursementsForEmployee(com.danielbchapman.production.entity.Employee)
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Reimbursement> getReimbursementsForEmployee(Employee employee)
  {
    Query q = em.createQuery("SELECT r FROM Reimbursement r WHERE r.employee = ?1");
    q.setParameter(1, employee);
    List<Reimbursement> results = (List<Reimbursement>)q.getResultList();
    ArrayList<Reimbursement> ret = new ArrayList<Reimbursement>();
    if(results != null)
      for(Reimbursement r : results)
        ret.add(r);
    
    return ret;
  }

}
