package com.danielbchapman.production.web.schedule.beans;

import javax.faces.context.FacesContext;

import com.danielbchapman.jboss.login.Roles;

public class RoleManager
{
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
    for(Roles r : Roles.values())
      if(isUserInRole(r));
    
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
    return FacesContext.getCurrentInstance().getExternalContext().isUserInRole(role.getRoleValue()); 
  }
}
