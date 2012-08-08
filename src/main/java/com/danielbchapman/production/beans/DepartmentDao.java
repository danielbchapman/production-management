package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Department;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Season;

@Stateless
public class DepartmentDao implements DepartmentDaoRemote
{
	private static final long serialVersionUID = 1L;
	//  @PersistenceContext
//	EntityManager em = EntityInstance.getEm();
	
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
		return EntityInstance.find(Department.class, id);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.DepartmentDaoRemote#saveDepartment(com.danielbchapman.production.entity.Department, com.danielbchapman.production.entity.Season)
	 */
	@Override
	public void saveDepartment(Department department, Season season)
	{
		department.setSeason(season);
		EntityInstance.saveObject(department);		
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.DepartmentDaoRemote#getDepartments(com.danielbchapman.production.entity.Season)
	 */
	@Override
	public ArrayList<Department> getDepartments(Season season)
	{
		return EntityInstance.getResultList(
				"SELECT d FROM Department d WHERE d.season = ?1 ORDER BY d.name", 
				Department.class, 
				season);
	}
}
