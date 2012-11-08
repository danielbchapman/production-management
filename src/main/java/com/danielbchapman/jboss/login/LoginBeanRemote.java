package com.danielbchapman.jboss.login;

import java.io.Serializable;
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
public interface LoginBeanRemote extends Serializable
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
  public void addUser(String username, String password, Collection<Roles> roles);
  
  /**
   * @param user the user to change
   * @param newPass the new password to use
   */
  public void changePassword(String username, String newPass);
  
  /**
   * @param user the user to change
   * @param role the role to add
   */
  public void addRoll(User user, Roles role);
  
  /**
   * @param user the user to change
   * @param role the role to remove
   */
  public void removeRoll(User user, Roles role);
  /**
   *
   * @return a list of all the users in the system.  
   * 
   */
  public ArrayList<String> getUsers();
  
  public User getUser(String userName);
  
  /**
   * @param user the user to delete
   */
  public void deleteUser(User user);
}
