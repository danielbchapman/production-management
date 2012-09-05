package com.danielbchapman.production.web.schedule.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import com.danielbchapman.jboss.login.Role;
import com.danielbchapman.jboss.login.Roles;

public class RoleManager implements Serializable
{
	private static final long serialVersionUID = 3L;
	private ArrayList<String> roles = new ArrayList<String>();
	
	public RoleManager(Role ... args)
	{
		if(args != null)
			for(Role s : args)
				roles.add(s.getRole());
	}
	
	public RoleManager(Collection<Role> arg)
	{
		if(arg != null)
			for(Role r : arg)
				roles.add(r.getRole());
	}
	
	public RoleManager(Roles ... args)
	{
		if(args != null)
			for(Roles r : args)
				roles.add(r.getRoleValue());
	}
  /**
   * @return true if user is 'admin'  
   * 
   */
  public boolean isAdmin()
  {
    return isUserInRole(Roles.ADMIN);
  }
  
  /**
   * 
   * @return If a user has a role, return true  
   */
  public boolean isGuest()
  {    
    return true;
  }  
  

  /**
   * The user has access to the scheduling component
   * 
   */
  public boolean isScheduler()
  {
    if(isUser())
      return true;
    
    return isUserInRole(Roles.SCHEDULER);
  }
  

  /**
   * @return true if 'venue' or 'admin'  
   * 
   */
  public boolean isUser()
  {
    if(isAdmin())
      return true;
    
    return isUserInRole(Roles.USER);
    
  }  
  
  public boolean isCompanyMember()
  {
  	if(isScheduler())
  		return true;
  	
  	return isUserInRole(Roles.COMPANY_MEMBER);
  }
  
  public boolean isInventoryAdmin()
  {
    if(isUser())
      return true;
    
    return isUserInRole(Roles.INVENTORY_ADMIN);
  }
  
  public boolean isInventoryGeneral()
  {
    if(isInventoryAdmin())
      return true;
    
    return isUserInRole(Roles.INVENTORY_GENERAL);
  }
  
  public boolean isInventoryWardrobe()
  {
    if(isInventoryAdmin())
      return true;
    
    return isUserInRole(Roles.INVENTORY_WARDROBE);
  }
  
  public boolean isInventoryLighting()
  {
    if(isInventoryAdmin())
      return true;
    
    return isUserInRole(Roles.INVENTORY_LIGHTING);
  }   
  
  public boolean isInventorySound()
  {
    if(isInventoryAdmin())
      return true;
    
    return isUserInRole(Roles.INVENTORY_SOUND);
  }   
  
  public boolean isInventoryProps()
  {
    if(isInventoryAdmin())
      return true;
    
    return isUserInRole(Roles.INVENTORY_PROPS);
  }   
  
  public boolean isInventoryScenic()
  {
    if(isInventoryAdmin())
      return true;
    
    return isUserInRole(Roles.INVENTORY_SCENIC);
  }   
  
  public boolean isInventoryStageManagement()
  {
    if(isInventoryAdmin())
      return true;
    
    return isUserInRole(Roles.INVENTORY_STAGE_MANAGEMENT); 
  }    
  
  public boolean isUserInRole(Roles role)
  {
  	if(role.getRoleValue().equals(Roles.GUEST.getRoleValue()))
  		return true;
  	
  	return roles.contains(role.getRoleValue());
//    return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role.getRoleValue()); 
  }
}
