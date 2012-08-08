package com.danielbchapman.production.web.production.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;

import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.DepartmentDaoRemote;
import com.danielbchapman.production.entity.Department;

/**
 * A simple web bean backing departments
 * 
 */
@SessionScoped
public class DepartmentBean implements Serializable
{
	private static final long serialVersionUID = 3L;
	private DepartmentDaoRemote departmentDaoObj;
	private ArrayList<SelectItem> departmentItems;
	private ArrayList<SelectItem> departmentItemsNameOnly;
	private HashSet<String> departmentNameHash;
	private ArrayList<DepartmentWrapper> departmentWrappers;
	private DepartmentWrapper edit = new DepartmentWrapper(new Department());

	public void addDepartment(ActionEvent evt)
	{
		if(!checkValidSeason())
			return;

		if(evt == null)
			return;

		String departmentName = (String) evt.getComponent().getAttributes().get("departmentName");
		if(departmentName != null && departmentName.trim().length() > 3)
		{
			Department dept = new Department();
			dept.setName(departmentName);
			getDepartmentDao().saveDepartment(dept, Utility.getBean(SeasonBean.class).getSeason());
			Utility.raiseInfo("Department Added", "The department " + dept.getName()
					+ " has been added to the database");
			refreshDepartments(evt);
		}
		else
			Utility.raiseError("Failed to Add", "Your department name must be at least 3 characters");
	}

	/**
	 * @param query
	 *          the string to query against
	 * @return a Collection of Strings that might be a match.
	 */
	public ArrayList<String> departmentNameComplete(String query)
	{
		ArrayList<String> ret = new ArrayList<String>();
		if(query == null || query.length() < 5)
		{
			for(String s : getDepartmentNamesHashed())
				ret.add(s);
			return ret;
		}

		for(String s : getDepartmentNamesHashed())
			if(s.contains(query))
				ret.add(s);

		return ret;
	}

	/**
	 * @param id
	 * @return the Department associated with the ID
	 */
	public Department getDepartment(Long id)
	{
		return getDepartmentDao().getDepartment(id);
	}

	public ArrayList<SelectItem> getDepartmentItems()
	{
		if(departmentItems == null)
			getDepartmentWrappers();

		return departmentItems;
	}

	public ArrayList<SelectItem> getDepartmentItemsNameOnly()
	{
		if(departmentItemsNameOnly == null)
			getDepartmentWrappers();

		return departmentItemsNameOnly;
	}

	public HashSet<String> getDepartmentNamesHashed()
	{
		if(departmentNameHash == null)
			getDepartmentWrappers();

		return departmentNameHash;
	}

	public ArrayList<DepartmentWrapper> getDepartmentWrappers()
	{
		if(departmentWrappers == null)
		{
			departmentItems = new ArrayList<SelectItem>();
			departmentItemsNameOnly = new ArrayList<SelectItem>();
			departmentWrappers = new ArrayList<DepartmentWrapper>();
			departmentNameHash = new HashSet<String>();
			SeasonBean seasonBean = Utility.getBean(SeasonBean.class);

			if(!checkValidSeason())
			{
				departmentWrappers = null;
				departmentItems = null;
				departmentItemsNameOnly = null;
				return new ArrayList<DepartmentWrapper>();
			}

			ArrayList<Department> tmp = getDepartmentDao().getDepartments(seasonBean.getSeason());
			if(departmentItems != null)
				for(Department d : tmp)
				{
					departmentItems.add(new SelectItem(d.getId(), d.getName()));
					departmentItemsNameOnly.add(new SelectItem(d.getName()));
					departmentWrappers.add(new DepartmentWrapper(d));
					departmentNameHash.add(d.getName());
				}
		}
		return departmentWrappers;
	}

	public DepartmentWrapper getEdit()
	{
		return edit;
	}

	public void refreshDepartments(ActionEvent evt)
	{
		departmentItems = null;
		departmentWrappers = null;
	}

	private boolean checkValidSeason()
	{
		SeasonBean season = Utility.getBean(SeasonBean.class);
		if(season.getSeason() != null)
			return true;

		Utility.redirect("season.xhtml");
		departmentWrappers = null;
		departmentItems = null;
		departmentItemsNameOnly = null;
		return false;
	}

	private DepartmentDaoRemote getDepartmentDao()
	{
		if(departmentDaoObj == null)
			departmentDaoObj = Utility.getObjectFromContext(DepartmentDaoRemote.class,
					Utility.Namespace.PRODUCTION);
		return departmentDaoObj;
	}

	public class DepartmentWrapper implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private Department department;

		public DepartmentWrapper()
		{
			this.department = new Department();
		}

		public DepartmentWrapper(Department department)
		{
			this.department = department;
		}

		public Department getDepartment()
		{
			return department;
		}

		public void removeDepartment(ActionEvent evt)
		{
			getDepartmentDao().removeDepartment(department);
			Utility.raiseInfo("Department Removed", "The department name removed.");
			refreshDepartments(evt);
		}

		public void saveDepartment(ActionEvent evt)
		{
			if(!checkValidSeason())
				return;

			if(department.getName() != null && department.getName().trim().length() > 3)
			{
				getDepartmentDao()
						.saveDepartment(department, Utility.getBean(SeasonBean.class).getSeason());
				Utility.raiseInfo("Department Saved",
						"The department name was updated to " + department.getName());
				refreshDepartments(evt);
			}
			else
			{
				department = getDepartmentDao().getDepartment(department.getId());// refresh
				Utility.raiseError("Unable to Save",
						"The department name must be longer than 3 characters.");
			}
		}

		public void setDepartment(Department department)
		{
			this.department = department;
		}
	}
}
