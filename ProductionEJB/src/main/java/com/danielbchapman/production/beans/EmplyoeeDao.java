package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Employee;
import com.danielbchapman.production.entity.EntityInstance;

/**
 * Session Bean implementation class EmplyoeeDao
 */
@Stateless
public class EmplyoeeDao implements EmplyoeeDaoRemote
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	EntityManager em = EntityInstance.getEm();
  /**
   * Default constructor.
   */
  public EmplyoeeDao()
  {
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.EmplyoeeDaoRemote#addEmployee(java.lang.String)
   */
  @Override
  public void addEmployee(String name)
  {
    Employee emp = new Employee();
    emp.setName(name);
    EntityInstance.saveObject(emp);
  }
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.EmplyoeeDaoRemote#getEmployees()
   */
  @Override
  public ArrayList<Employee> getEmployees()
  {
  	return EntityInstance.getResultList("SELECT emp FROM Employee ORDER BY emp.name", Employee.class);
  }

}
