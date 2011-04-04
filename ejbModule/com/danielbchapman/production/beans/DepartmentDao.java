package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Department;

@Stateless
public class DepartmentDao implements DepartmentDaoRemote
{
//  @PersistenceContext
	EntityManager em = EntityInstance.getEm();
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.DepartmentDaoRemote#saveDepartment(com.danielbchapman.production.entity.Department)
   */
	public void saveDepartment(Department department)
	{
		try
		{
		  if(!Config.CONTAINER_MANAGED)
		    em.getTransaction().begin();
			
			if(department.getId() == null)
				em.persist(department);
			else
				em.merge(department);
			
			if(!Config.CONTAINER_MANAGED)
			  em.getTransaction().commit();			
		}
		catch(Exception e)
		{
			e.printStackTrace();//For debugging
			return;
		}
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.DepartmentDaoRemote#removeDepartment(com.danielbchapman.production.entity.Department)
   */
	public void removeDepartment(Department department)
	{
	  if(!Config.CONTAINER_MANAGED)
	    em.getTransaction().begin();
	  
		if(department != null)
			em.remove(department);
		
		if(!Config.CONTAINER_MANAGED)
		  em.getTransaction().commit();
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.DepartmentDaoRemote#getDepartment(java.lang.Long)
   */
	public Department getDepartment(Long id)
	{
		return em.find(Department.class, id);
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.DepartmentDaoRemote#getDepartments()
   */
	@SuppressWarnings("unchecked")
	public ArrayList<Department> getDepartments()
	{
		ArrayList<Department> ret = new ArrayList<Department>();
		
		Query q = em.createQuery("SELECT d FROM Department d ORDER BY d.name");
		List<Department> results = (List<Department>)q.getResultList();
		if(results != null)
			for(Department d : results)
				ret.add(d);
		
		return ret;
	}
}
