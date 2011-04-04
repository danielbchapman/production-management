package com.danielbchapman.jboss.login;

import java.util.ArrayList;
import java.util.Collection;

import javax.ejb.Remote;


/**
 * A set of methods to allow the manipulation logins to the database. This 
 * is complete with static strings as suggested queries for SQL based access.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 4, 2011 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface LoginBeanRemote
{
  /**
   * Validate a login server side.
   * @param username the username
   * @param password the password
   * @return the User for the login, null if void.  
   * 
   */
  public User validateLogin(String username, String password);
  
  /**
   * @param userId
   * @return A list of the roles for the userId presented (User.name)  
   * 
   */
  public ArrayList<Role> getRolesForUser(String userId);
  
  /**
   * Add a user to the database.
   * @param username the username
   * @param password the password 
   * @param roles A collection of the roles this user should have.  
   * 
   */
  public void addUser(String username, String password, Collection<String> roles);
  
  /**
   *
   * @return a list of all the users in the system.  
   * 
   */
  public ArrayList<String> getUsers();
}
