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
	private static final long serialVersionUID = 1L;
	//  @PersistenceContext
	EntityManager em = EntityInstance.getEm();
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.DepartmentDaoRemote#saveDepartment(com.danielbchapman.production.entity.Department)
   */
	public void saveDepartment(Department department)
	{
		EntityInstance.saveObject(department);
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.DepartmentDaoRemote#removeDepartment(com.danielbchapman.production.entity.Department)
   */
	public void removeDepartment(Department department)
	{
		EntityInstance.deleteObject(department);
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
