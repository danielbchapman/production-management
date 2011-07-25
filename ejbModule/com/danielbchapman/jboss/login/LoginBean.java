package com.danielbchapman.jboss.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

/**
 * Session Bean implementation class LoginBean
 */
@Stateless
public class LoginBean implements LoginBeanRemote
{

  /**
   * Default constructor.
   */
  public LoginBean()
  {
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.jboss.login.LoginBeanRemote#validateLogin(java.lang.String, java.lang.String)
   */
  @Override
  public User validateLogin(String username, String password)
  {
    Query q = EntityInstance.getEm().createQuery("SELECT u FROM User WHERE u.user = ?1 AND u.password = ?2");
    q.setParameter(1, username);
    q.setParameter(2, password);
    
    try
    {
      return (User)q.getSingleResult();
    }
    catch(NoResultException e)
    {
      return null;
    }
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.jboss.login.LoginBeanRemote#getRolesForUser(java.lang.String)
   */
  @Override
  public ArrayList<Role> getRolesForUser(String userId)
  {
    ArrayList<Role> roles = new ArrayList<Role>();
    User u = EntityInstance.getEm().find(User.class, userId);
    
    if(u != null && u.getRoles() != null)
      for(Role r : u.getRoles())
        roles.add(r);
    
    return roles;
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.jboss.login.LoginBeanRemote#addUser(java.lang.String, java.lang.String, java.util.Collection)
   */
  @Override
  public void addUser(String username, String password, Collection<Roles> roles)
  {
    User u = new User();
    u.setUser(username);
    u.setPassword(password);
    
    EntityInstance.getEm().getTransaction().begin();
    
    EntityInstance.getEm().persist(u);
    
    for(Roles r : roles)
    {
      Role tmp = new Role();
      tmp.setRole(r.getRoleValue());
      tmp.setUser(u);
      EntityInstance.getEm().persist(tmp);
    }
    
    EntityInstance.getEm().getTransaction().commit();
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.jboss.login.LoginBeanRemote#getUsers()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<String> getUsers()
  {
    Query q = EntityInstance.getEm().createQuery("SELECT u.user FROM User u ORDER BY u.user");
    ArrayList<String> users = new ArrayList<String>();
    
    List<String> results = q.getResultList();
    
    if(results != null)
      for(String s : results)
        users.add(s);
    
    return users;
    
  }

	@Override
	public User getUser(String userName)
	{
    Query q = EntityInstance.getEm().createQuery("SELECT u FROM User u WHERE user.u = ?1");
    q.setParameter(1, userName);
    
    try
    {
    	return (User) q.getSingleResult();
    }
    catch(NonUniqueResultException e)
    {
    	return null;
    }

	}

}
