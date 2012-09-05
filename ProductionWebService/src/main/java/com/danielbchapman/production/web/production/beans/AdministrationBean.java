package com.danielbchapman.production.web.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import lombok.Data;
import lombok.Getter;

import com.danielbchapman.composite.RoleSelection;
import com.danielbchapman.jboss.login.LoginBeanRemote;
import com.danielbchapman.jboss.login.Roles;
import com.danielbchapman.jboss.login.User;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.OptionsDaoRemote;

public class AdministrationBean implements Serializable
{
	private static final long serialVersionUID = 3L;
	private String connectionString;
	private User editableUser;
	private String editableUserName;
	private LoginBeanRemote login;
	private OptionsDaoRemote options;
	private String password;
	private String reportingDocumentRoot;
	private String role;
	private String user;
	
	private UserAdd userAdd = new UserAdd();
	@Getter
	private UserAdd editable = new UserAdd();

	private ArrayList<SelectItem> users;

	private String venueDocumentRoot;

	public void clearFields(ActionEvent evt)
	{
		if(evt != null)
		{
			user = null;
			password = null;
			role = null;
			users = null;
		}
	}

	public void doSelectUser(ActionEvent evt)
	{
		if(evt != null)
		{
			editableUser = getLoginBean().getUser(editableUserName);
			editable = new UserAdd(getLoginBean().getUser(editableUserName));
		}
	}

	public String getConnectionString()
	{
		if(connectionString == null)
		{
			connectionString = getOptionsBean().getOptions() != null ? getOptionsBean().getOptions()
					.getConnectionString() : "none";
		}
		return connectionString;
	}

	public User getEditableUser()
	{
		return editableUser;
	}

	public String getEditableUserName()
	{
		return editableUserName;
	}

	public String getPassword()
	{
		return password;
	}

	public String getReportingDocumentRoot()
	{
		return Utility.getSession().getServletContext().getRealPath("/reports/");
//		if(reportingDocumentRoot == null)
//			reportingDocumentRoot = getOptionsBean().getOptions() != null ? getOptionsBean().getOptions()
//					.getReportingRoot() : "none";
//		return reportingDocumentRoot;
	}

	public String getRole()
	{
		return role;
	}
	
	public String getUser()
	{
		return user;
	}

	public UserAdd getUserAdd()
	{
		return userAdd;
	}

	public ArrayList<SelectItem> getUsers()
	{
		if(users == null)
		{
			users = new ArrayList<SelectItem>();
			for(String u : getLoginBean().getUsers())
				users.add(new SelectItem(u));
		}

		return users;
	}

	public String getVenueDocumentRoot()
	{
		if(venueDocumentRoot == null)
			venueDocumentRoot = getOptionsBean().getOptions() != null ? getOptionsBean().getOptions()
					.getVenueDocumentRoot() : "none";
		return venueDocumentRoot;
	}

	public boolean isCanHasEditableUser()
	{
		return editableUser != null;
	}

	public void saveUser(ActionEvent evt)
	{
		if(evt != null)
		{
			String username = userAdd.getUsername();
			String password = userAdd.getPassword();
			if(username == null || username.length() < 4)
			{
				Utility.raiseError("Error", "The user " + username
						+ " must be at least (4) characters and unique.");
				return;
			}

			if(password == null || password.length() < 8)
			{
				Utility.raiseError("Error", "The password " + password
						+ " must be at least (8) characters.");
				return;
			}

			ArrayList<Roles> roles = userAdd.getRoleSelection().getSelectedRoles();

			try
			{
				getLoginBean().addUser(username, password, roles);
				Utility.raiseInfo("User added", "The user " + username + " was added to the database.");
				userAdd = new UserAdd();
			}
			catch(Exception e)
			{
				Utility.raiseError("Error!", e.getMessage());
				e.printStackTrace();
			}

		}
	}

	public void setConnectionString(String connectionString)
	{
		this.connectionString = connectionString;
	}

	public void setEditableUser(User editableUser)
	{
		this.editableUser = editableUser;
	}

	public void setEditableUserName(String editableUserName)
	{
		this.editableUserName = editableUserName;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public void setReportingDocumentRoot(String reportingDocumentRoot)
	{
		this.reportingDocumentRoot = reportingDocumentRoot;
	}

	public void setRole(String role)
	{
		this.role = role;
	}

	public void setUser(String user)
	{
		this.user = user;
	}

	public void setVenueDocumentRoot(String venueDocumentRoot)
	{
		this.venueDocumentRoot = venueDocumentRoot;
	}

	public void updateConnectionString(ActionEvent evt)
	{
		if(evt != null)
			getOptionsBean().setConnectionString(connectionString);
	}

	public void updateReportingDocumentRoot(ActionEvent evt)
	{
		if(evt != null)
			getOptionsBean().setReportingDocumentRoot(reportingDocumentRoot);
	}

	public void updateVenueDocumentRoot(ActionEvent evt)
	{
		if(evt != null)
			getOptionsBean().setVenueDocumentRoot(venueDocumentRoot);
	}

	private LoginBeanRemote getLoginBean()
	{
		if(login == null)
			login = Utility.getObjectFromContext(LoginBeanRemote.class, Utility.Namespace.LOGIN);
		return login;
	}

	private OptionsDaoRemote getOptionsBean()
	{
		if(options == null)
			options = Utility.getObjectFromContext(OptionsDaoRemote.class, Utility.Namespace.PRODUCTION);
		return options;
	}
	
	@Data
	public class UserAdd implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private String password;
		private String password2;
		private String username;
		private RoleSelection roleSelection;
		
		public UserAdd(User u)
		{
			this.username = u.getUser();
			this.roleSelection = new RoleSelection(getLoginBean().getRolesForUser(username));
		}
		
		public UserAdd()
		{
			roleSelection = new RoleSelection();
		}
	}

}
