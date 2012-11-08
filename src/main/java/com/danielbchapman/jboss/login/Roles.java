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
	//@formatter:off
	ADMIN("admin_authentication", "Adminstrator"), 
	USER("user_authentication", "User"), 
	SCHEDULER("scheduler_authentication", "Scheduler"),
	COMPANY_MEMBER("company_authentication", "Company"),
	CONTACT_MANAGER("contact_authentication", "Contact"),
	GUEST("guest_authentication", "Guest"),
	INVENTORY_ADMIN("inventory_authentication", "Inventory Manager"),
	INVENTORY_WARDROBE("wardrobe","Wardrobe Manager"),
	INVENTORY_LIGHTING("lighting", "Lighting Manager"),
	INVENTORY_SOUND("sound", "Sound Manager"),
	INVENTORY_PROPS("props", "Properties Manager"),
	INVENTORY_SCENIC("scenic", "Scenic Manager"),
	INVENTORY_STAGE_MANAGEMENT("stageManagement", "Stage Manager"),
	INVENTORY_GENERAL("general", "General Manager");
	//@formatter:on	
	/**
	 * A simple override of valueOf(object o)
	 * 
	 * @param s
	 * @return the roles associated, or guest if unknown;
	 * 
	 */
	public static Roles fromString(String s)
	{
		if(s == null)
			return GUEST;

		for(Roles r : values())
			if(s.equals(r.value) || s.equals(r.label) || s.equals(r.name()))
				return r;

		return GUEST;
	}

	String value;
	String label;

	Roles(String value, String label)
	{
		this.value = value;
		this.label = label;
	}

	public String getLabel()
	{
		return label;
	}

	/**
	 * @return the value of the role e.g.<code>user_authentication</code>
	 * 
	 */
	public String getRoleValue()
	{
		return value;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString()
	{
		return label;
	}
}
