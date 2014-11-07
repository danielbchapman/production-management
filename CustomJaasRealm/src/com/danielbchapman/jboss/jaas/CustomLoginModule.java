package com.danielbchapman.jboss.jaas;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

public class CustomLoginModule implements LoginModule
{
	private Subject subject;
	private CallbackHandler callbackHandler;
	private Map<String, ?> sharedState;
	private Map<String, ?> options;

	private boolean commitSucceeded = false;
	private boolean loginSucceeded = false;

	private String username;
	private BasicPrincipal principal;
	private BasicPrincipal[] roles;

	/* (non-Javadoc)
	 * @see javax.security.auth.spi.LoginModule#initialize(javax.security.auth.Subject, javax.security.auth.callback.CallbackHandler, java.util.Map, java.util.Map)
	 */
	public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState,
			Map<String, ?> options)
	{
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		this.sharedState = sharedState;
		this.options = options;
	}

	/* (non-Javadoc)
	 * @see javax.security.auth.spi.LoginModule#login()
	 */
	public boolean login() throws LoginException
	{
		NameCallback nameCallback = new NameCallback("Username");
		PasswordCallback passwordCallback = new PasswordCallback("Password", false);

		Callback[] callbacks = new Callback[] { nameCallback, passwordCallback };
		try
		{
			callbackHandler.handle(callbacks);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			throw new LoginException(e.getLocalizedMessage());
		}
		catch (UnsupportedCallbackException e)
		{
			e.printStackTrace();
			throw new LoginException(e.getLocalizedMessage());
		}

		username = nameCallback.getName();
		char[] password = passwordCallback.getPassword();
		passwordCallback.clearPassword();

		principal = new BasicPrincipal(username);
		
		roles = new BasicPrincipal[] { new BasicPrincipal("admin") // for example
		// ...fill in all of the roles from your database
		};

		// trust me - just do a "find usages" for classes implementing LoginModule
		return true;
	}

	public boolean commit() throws LoginException
	{
		// this is the important part to work with JBoss:
		subject.getPrincipals().add(principal);
		// jboss requires the name 'Roles'
		BasicGroup group = new BasicGroup("Roles");
		for (BasicPrincipal role : roles)
			group.addMember(role);
				
		subject.getPrincipals().add(group);

		return true;
	}

	@Override
	public boolean abort() throws LoginException
	{
		return false;
	}

	@Override
	public boolean logout() throws LoginException
	{
		subject.getPrincipals().remove(principal);
		return true;
	}
}
