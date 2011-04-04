package com.danielbchapman.jboss.login;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

public class EntityInstance
{
	private static EntityManager manager;
	
  /**
   * @return an instance of the entity manager if it isn't there.
   */
  public static EntityManager getEm()
  {
    if(manager == null || !manager.isOpen())
    {
      manager = Persistence.createEntityManagerFactory("authentication").createEntityManager();
      manager.setFlushMode(FlushModeType.COMMIT);
    }    
    return manager;
  }
}
