package com.danielbchapman.production.beans;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Wrapper;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

import com.danielbchapman.production.entity.BaseEntity;

public class EntityInstance
{
  private final static String CONNECTION_STRING = "jdbc:h2:tcp://localhost/C:\\Database\\Data\\live;";
	private static EntityManager manager;
	
  /**
   * @return an instance of the entity manager if it isn't there.
   */
  public static EntityManager getEm()
  {
    if(manager == null || !manager.isOpen())
    {
      manager = Persistence.createEntityManagerFactory("production").createEntityManager();
      manager.setFlushMode(FlushModeType.COMMIT);
    }

    
    return manager;
  }
  
  public static Connection getConnection()
  {
    return ((EntityManager)getEm()).unwrap(java.sql.Connection.class);
   // return DriverManager.getConnection(CONNECTION_STRING, "sa", "***REMOVED***");
  }
  
  /**
   * A simple wrapper to deal with container vs. local management
   * for the system. This only works with the BaseEntity object.
   */
  public static void saveObject(BaseEntity obj)
  {      
      if(!Config.CONTAINER_MANAGED)
        getEm().getTransaction().begin();
      
      if(obj.getId() == null)
        getEm().persist(obj);
      else
        getEm().merge(obj);
      if(!Config.CONTAINER_MANAGED)
        getEm().getTransaction().commit();
  }
  
  /**
   * A simple wrapper to deal with container vs. local management
   * for the system. This only works with the BaseEntity object.
   * @param obj removes this object from the ORM.
   */
  public static void deleteObject(BaseEntity obj)
  {
    if(obj.getId() == null)
      throw new IllegalArgumentException("Can not remove an object without ID, it doesn't exist in the Object Relational Mapping");
      
    if(!Config.CONTAINER_MANAGED)
      getEm().getTransaction().begin();
    
    getEm().remove(obj);
    
    if(!Config.CONTAINER_MANAGED)
      getEm().getTransaction().commit();    
  }
}
