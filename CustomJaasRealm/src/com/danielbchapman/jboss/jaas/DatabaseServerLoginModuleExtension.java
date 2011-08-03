package com.danielbchapman.jboss.jaas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.sql.DataSource;
import javax.transaction.SystemException;
import javax.transaction.Transaction;

import org.jboss.security.auth.spi.DatabaseServerLoginModule;

/**
 * This is an override of the database login module so that we can actually
 * support some hashing. Crazy, I know.
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Aug 2, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
public class DatabaseServerLoginModuleExtension extends DatabaseServerLoginModule
{
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jboss.security.auth.spi.DatabaseServerLoginModule#convertRawPassword
	 * (java.lang.String)
	 */
	@Override
	protected String convertRawPassword(String rawPassword)
	{
		if (rawPassword == null)
			return null;

		String decryptedPass = null;
		Transaction tx = null;
		Connection conn = null;
		;
		PreparedStatement ps = null;
		ResultSet rs = null;

		if (suspendResume)
		{
			try
			{
				if (tm == null)
					throw new IllegalStateException("Transaction Manager is null");
				tx = tm.suspend();
			}
			catch (SystemException e)
			{
				throw new RuntimeException(e);
			}

			try
			{
				InitialContext ctx = new InitialContext();
				DataSource ds = (DataSource) ctx.lookup(dsJndiName);
				conn = ds.getConnection();
				
				// Get the password
				ps = conn.prepareStatement(
						"CALL Trim(TRAILING Char(0) FROM " +
								"Utf8ToString(" +
									"Decrypt('AES', StringToUtf8(?1), (SELECT password FROM User WHERE user = ?2))" +
								")" +
						");");
				ps.setString(1, getUsername());
				ps.setString(2, getUsername());
				rs = ps.executeQuery();
				
				if (rs.next() == false)
				{
					if(log.isTraceEnabled())
						log.debug("No match");
					throw new RuntimeException(new FailedLoginException("No matching username found in Principals"));
				}

				decryptedPass = rs.getString(1);
			}
			catch (NamingException ex)
			{
				if(log.isTraceEnabled())
					log.debug(ex.getMessage());
				LoginException le = new LoginException("Error looking up DataSource from: " + dsJndiName);
				le.initCause(ex);
				throw new RuntimeException(new FailedLoginException("No matching username found in Principals"));
			}
			catch (SQLException ex)
			{
				if(log.isTraceEnabled())
					log.debug(ex.getMessage());
				LoginException le = new LoginException("Query failed");
				le.initCause(ex);
				throw new RuntimeException(new FailedLoginException("No matching username found in Principals"));
			}
			finally
			{
				if (rs != null)
				{
					try
					{
						rs.close();
					}
					catch (SQLException e)
					{
					}
				}
				if (ps != null)
				{
					try
					{
						ps.close();
					}
					catch (SQLException e)
					{
					}
				}
				if (conn != null)
				{
					try
					{
						conn.close();
					}
					catch (SQLException ex)
					{
					}
				}
				if (suspendResume)
				{
					try
					{
						tm.resume(tx);
					}
					catch (Exception e)
					{
						throw new RuntimeException(e);
					}
					if (log.isTraceEnabled())
						log.trace("resumeAnyTransaction");
				}
			}
		}

		if(log.isTraceEnabled())
		{
			log.debug("Login Attempt:");
			log.debug("\tu " + getUsername());
			log.debug("\tp " + rawPassword);
			log.debug("\td " + decryptedPass);
			log.debug("\tq " + rolesQuery);			
		}

		return decryptedPass;
	}
}
