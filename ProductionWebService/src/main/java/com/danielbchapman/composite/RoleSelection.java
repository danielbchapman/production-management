package com.danielbchapman.composite;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;

import lombok.Data;

import com.danielbchapman.jboss.login.Role;
import com.danielbchapman.jboss.login.Roles;
import com.danielbchapman.production.Utility;

/**
 * A simpe backing class for the RoleSelection component.
 * 
 * @author dchapman
 * @since Aug 2, 2012
 * @copyright The Acting Company Aug 2, 2012
 */
/**
	 * 
	 */

@Data
public class RoleSelection implements Serializable
{
	private static final long serialVersionUID = 3L;
	private ArrayList<RoleItem> items = new ArrayList<RoleItem>();
	boolean sortApplied;
	public ArrayList<RoleItem> getItems()
	{
		if(!sortApplied)
			Collections.sort(items);
		
		return items;
	}
	public RoleSelection()
	{
		this(new Roles[] {});
	}

	public RoleSelection(ArrayList<Role> roleFromDb)
	{
		Roles[] roles = roleFromDb == null ? new Roles[]{Roles.GUEST} : new Roles[roleFromDb.size()];
				
		if(roleFromDb != null)
			for(int i = 0; i < roleFromDb.size(); i++)
				roles[i] = Roles.fromString(roleFromDb.get(i).getRole());
		
		HashSet<Roles> selected = new HashSet<Roles>(Utility.asCollection(roles));
		HashSet<Roles> all = new HashSet<Roles>(Utility.asCollection(Roles.values()));

		HashSet<Roles> notSelected = new HashSet<Roles>(all);
		notSelected.removeAll(selected);

		if(roles != null)
			for(Roles r : roles)
				items.add(new RoleItem(r, true));

		for(Roles r : notSelected)
			items.add(new RoleItem(r, false));				
	}
	
	public RoleSelection(Collection<Roles> roles)
	{
		this(roles == null ? new Roles[] {} : ((Roles[]) roles.toArray()));
	}

	public RoleSelection(Roles[] roles)
	{
		HashSet<Roles> selected = new HashSet<Roles>(Utility.asCollection(roles));
		HashSet<Roles> all = new HashSet<Roles>(Utility.asCollection(Roles.values()));

		HashSet<Roles> notSelected = new HashSet<Roles>(all);
		notSelected.removeAll(selected);

		if(roles != null)
			for(Roles r : roles)
				items.add(new RoleItem(r, true));

		for(Roles r : notSelected)
			items.add(new RoleItem(r, false));
	}

	public ArrayList<Roles> getSelectedRoles()
	{
		ArrayList<Roles> active = new ArrayList<Roles>();

		for(RoleItem i : items)
			if(i.selected)
				active.add(Roles.fromString(i.label));

		return active;
	}


	@Data
	public class RoleItem implements Comparable<RoleItem>, Serializable
	{
		private static final long serialVersionUID = 3L;
		
		private String label;
		private boolean selected;

		public RoleItem(Roles r, boolean selected)
		{
			this.selected = selected;
			this.label = r.getLabel();
		}

		/*
		 * (non-Javadoc) Use a hashcode based entirely on the string label. THIS IS INTENTIONAL
		 * 
		 * @see java.lang.Object#hashCode()
		 */
		public int hashCode()
		{
			return label.hashCode();
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Comparable#compareTo(java.lang.Object)
		 */
		@Override
		public int compareTo(RoleItem r)
		{
				return Utility.compareTo(label,  r.label);
		}
	}

}
