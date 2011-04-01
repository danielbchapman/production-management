package com.danielbchapman.production.entity;

/**
 * A placeholder class that clearly represents an employee.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Feb 4, 2011 2011
 * @version 2 Development
 * @link http://www.lightassistant.com
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
