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
  ADMIN("admin_authentication"),
  USER("user_authentication"),
  SCHEDULER("scheduler_authentication"),
  GUEST("guest_authentication"),
  INVENTORY_ADMIN("inventory_authentication"),
  INVENTORY_WARDROBE("wardrobe"),
  INVENTORY_LIGHTING("lighting"),
  INVENTORY_SOUND("sound"),
  INVENTORY_PROPS("props"),
  INVENTORY_SCENIC("scenic"),
  INVENTORY_STAGE_MANAGEMENT("stageManagement"),
  INVENTORY_GENERAL("general")
  ;
  String value;

  Roles(String value)
  {
    this.value = value;
  }

  public String toString()
  {
    return value;
  }
}
