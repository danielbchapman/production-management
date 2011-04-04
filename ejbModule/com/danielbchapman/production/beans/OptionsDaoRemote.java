package com.danielbchapman.production.beans;
import javax.ejb.Remote;
import javax.persistence.EntityManager;

import com.danielbchapman.production.entity.Options;

/**
 * A simple way to view the options.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jan 1, 2011 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface OptionsDaoRemote
{
  public Options getOptions();
  public EntityManager getEntityManager();
  public void setConnectionString(String connectionString);
  public void setVenueDocumentRoot(String venueDocumentRoot);
  public void setReportingDocumentRoot(String reportingDocumentRoot);
}
