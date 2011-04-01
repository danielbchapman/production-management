package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Employee;

/**
 * A simple accessor interface for Employee
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
public interface EmplyoeeDaoRemote
{
  public void addEmployee(String name);
  public ArrayList<Employee> getEmployees();
}
