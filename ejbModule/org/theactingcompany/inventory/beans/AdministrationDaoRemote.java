package org.theactingcompany.inventory.beans;

import javax.ejb.Remote;

@Remote
public interface AdministrationDaoRemote
{
  /**
   * Initialize the full text searches of the tables.  
   * 
   */
  public void initializeFullTextSearches();
  
}
