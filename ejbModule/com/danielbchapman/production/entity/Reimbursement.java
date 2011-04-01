package com.danielbchapman.production.entity;

/**
 * A reimbursement links to an BudgetEntry so that it can be tied to a department
 * but at the same time easily reported based on who needs Reimbursement.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Feb 4, 2011 2011
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
public class Reimbursement extends BaseEntity
{
  private static final long serialVersionUID = 1L;
  private BudgetEntry entry;
  private Boolean reported;
  public Employee getEmployee()
  {
    return employee;
  }
  public void setEmployee(Employee employee)
  {
    this.employee = employee;
  }
  private Employee employee;
  public BudgetEntry getEntry()
  {
    return entry;
  }
  public Boolean getReported()
  {
    return reported;
  }
  public void setEntry(BudgetEntry entry)
  {
    this.entry = entry;
  }
  public void setReported(Boolean reported)
  {
    this.reported = reported;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.entity.BaseEntity#toString()
   */
  @Override
  public String toString()
  {
    return "[Employee] " + employee + " [Entry] " + entry;
  }
}
