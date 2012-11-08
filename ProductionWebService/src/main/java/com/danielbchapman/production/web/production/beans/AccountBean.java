package com.danielbchapman.production.web.production.beans;

import java.io.Serializable;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import lombok.Getter;
import lombok.Setter;

import com.danielbchapman.jboss.login.LoginBeanRemote;
import com.danielbchapman.jboss.login.User;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.web.schedule.beans.LoginBean;

@ViewScoped
public class AccountBean implements Serializable
{
	private LoginBeanRemote loginDao;
	
	private static final long serialVersionUID = 1L;
	@Getter
	@Setter
	private String oldPass;
	@Getter
	@Setter
	private String password;
	@Getter
	@Setter
	private String passwordConfirm;
	
	@Getter
	@Setter
	private String errorMessage;
	@Getter
	@Setter
	private boolean success;
	public void changePass(ActionEvent evt)
	{
		User user = getLoginDao().validateLogin(Utility.getBean(LoginBean.class).getUserPrinciple(), oldPass);
		success = false;
		if(user == null)
		{
			errorMessage = "Inavlid password";
			return;
		}
		
		if(Utility.compareTo(password, passwordConfirm) != 0)
		{
			errorMessage = "Passwords do not match";
			return;
		}

		getLoginDao().changePassword(user.getUser(), password);
		password = null;
		oldPass = null;
		passwordConfirm = null;
		errorMessage = "";
		success = true;
		
		Utility.raiseInfo("Success", "Your password was successfully changed");
	}
	
	private LoginBeanRemote getLoginDao()
	{
		if(loginDao == null)
			loginDao = Utility.getObjectFromContext(LoginBeanRemote.class, Utility.Namespace.PRODUCTION);
		return loginDao;
	}
}
