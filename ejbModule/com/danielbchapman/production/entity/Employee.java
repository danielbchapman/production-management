package com.danielbchapman.production.entity;

/**
 * A placeholder class that clearly represents an employee.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Feb 4, 2011 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class Employee extends BaseEntity
{
  private static final long serialVersionUID = 1L;
  private String name;
  public String getName()
  {
    return name;
  }
  public void setName(String name)
  {
    this.name = name;
  }
}
