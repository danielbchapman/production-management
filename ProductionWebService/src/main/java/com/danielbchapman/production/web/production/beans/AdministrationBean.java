package com.danielbchapman.production.web.production.beans;

import java.util.ArrayList;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.danielbchapman.jboss.login.LoginBeanRemote;
import com.danielbchapman.jboss.login.Roles;
import com.danielbchapman.jboss.login.User;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.OptionsDaoRemote;

public class AdministrationBean
{
	private String connectionString;
	private User editableUser;
	private String editableUserName;
	private LoginBeanRemote login;
	private OptionsDaoRemote options;
	private String password;
	private String reportingDocumentRoot;
	private String role;
	private SelectItem[] roles;

	private String user;

	private UserAddModify userAddModify = new UserAddModify();

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
		if(reportingDocumentRoot == null)
			reportingDocumentRoot = getOptionsBean().getOptions() != null ? getOptionsBean().getOptions()
					.getReportingRoot() : "none";
		return reportingDocumentRoot;
	}

	public String getRole()
	{
		return role;
	}

	/**
	 * 
	 * @return a list of all the Roles available (bad object name...)
	 * 
	 */
	public SelectItem[] getRoles()
	{
		if(roles == null)
		{
			Roles[] statics = Roles.values();
			roles = new SelectItem[statics.length];

			for(int i = 0; i < roles.length; i++)
				roles[i] = new SelectItem(statics[i].getLabel());
		}

		return roles;
	}

	public String getUser()
	{
		return user;
	}

	public UserAddModify getUserAddModify()
	{
		return userAddModify;
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
			String username = userAddModify.getUsername();
			String password = userAddModify.getPassword();
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

			Roles role = Roles.fromString(userAddModify.getRole());

			ArrayList<Roles> roleTmp = new ArrayList<Roles>();
			roleTmp.add(role);

			try
			{
				getLoginBean().addUser(username, password, roleTmp);
				Utility.raiseInfo("User added", "The user " + username + " was added to the database.");
				userAddModify = new UserAddModify();
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

	public class UserAddModify
	{
		private String password;
		private String role;
		private String username;

		public String getPassword()
		{
			return password;
		}

		public String getRole()
		{
			return role;
		}

		public String getUsername()
		{
			return username;
		}

		public void setPassword(String password)
		{
			this.password = password;
		}

		public void setRole(String role)
		{
			this.role = role;
		}

		public void setUsername(String username)
		{
			this.username = username;
		}
	}

}
