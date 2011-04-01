package com.duastechnologies.jboss.login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
   * @see com.duastechnologies.jboss.login.LoginBeanRemote#validateLogin(java.lang.String, java.lang.String)
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
   * @see com.duastechnologies.jboss.login.LoginBeanRemote#getRolesForUser(java.lang.String)
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
   * @see com.duastechnologies.jboss.login.LoginBeanRemote#addUser(java.lang.String, java.lang.String, java.util.Collection)
   */
  @Override
  public void addUser(String username, String password, Collection<String> roles)
  {
    User u = new User();
    u.setUser(username);
    u.setPassword(password);
    
    EntityInstance.getEm().getTransaction().begin();
    EntityInstance.getEm().persist(u);
    for(String s : roles)
    {
      Role tmp = new Role();
      tmp.setRole(s);
      tmp.setUser(u);
      EntityInstance.getEm().persist(tmp);
    }
    EntityInstance.getEm().getTransaction().commit();
  }

  /* (non-Javadoc)
   * @see com.duastechnologies.jboss.login.LoginBeanRemote#getUsers()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<String> getUsers()
  {
    Query q = EntityInstance.getEm().createQuery("SELECT u FROM User ORDER BY u.name");
    ArrayList<String> users = new ArrayList<String>();
    
    List<String> results = q.getResultList();
    
    if(results != null)
      for(String s : results)
        users.add(s);
    
    return users;
    
  }

}
