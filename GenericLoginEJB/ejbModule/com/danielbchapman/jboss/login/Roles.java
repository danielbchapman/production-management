package com.danielbchapman.jboss.login;

/**
 * The user roles
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 11, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public enum Roles
{
  ADMIN("admin_authentication", "Adminstrator"),
  USER("user_authentication", "User"),
  SCHEDULER("scheduler_authentication", "Scheduler"),
  GUEST("guest_authentication", "Guest"),
  INVENTORY_ADMIN("inventory_authentication", "Inventory Manager"),
  INVENTORY_WARDROBE("wardrobe", "Wardrobe Manager"),
  INVENTORY_LIGHTING("lighting", "Lighting Manager"),
  INVENTORY_SOUND("sound", "Sound Manager"),
  INVENTORY_PROPS("props", "Properties Manager"),
  INVENTORY_SCENIC("scenic", "Scenic Manager"),
  INVENTORY_STAGE_MANAGEMENT("stageManagement", "Stage Manager"),
  INVENTORY_GENERAL("general", "General Manager")
  ;
  String value;
  String name;

  Roles(String value, String name)
  {
    this.value = value;
  }

  /* (non-Javadoc)
   * @see java.lang.Enum#toString()
   */
  public String toString()
  {
    return name;
  }
  
  /**
   * A simple override of valueOf(object o)
   * @param s
   * @return the roles associated, or guest if unknown;
   * 
   */
  public static Roles fromString(String s)
  {
  	if(s == null)
  		return GUEST;
  	
  	for(Roles r : values())
  		if(s.equals(r.value) || s.equals(r.name))
  			return r;
  	
  	return GUEST;
  }
  
  /**
   * @return the value of the role e.g.<code>user_authentication</code>  
   * 
   */
  public String getRoleValue()
  {
  	return value;
  }
  
}
