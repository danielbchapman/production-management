package com.danielbchapman.production.beans;


import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Department;
@Remote
public interface DepartmentDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

  /**
   * @param department saves a department, <b>exceptions suppressed.</b>
   */
  public abstract void saveDepartment(Department department);

  public abstract void removeDepartment(Department department);

  /**
   * @param id
   * @return the specified department
   */
  public abstract Department getDepartment(Long id);

  /**
   * @return a list of all departments
   */
  public abstract ArrayList<Department> getDepartments();

}