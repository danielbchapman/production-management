package com.danielbchapman.production.beans;
import java.io.Serializable;

import javax.ejb.Remote;

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
public interface OptionsDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;
	
  public Options getOptions();
  public void setConnectionString(String connectionString);
  public void setVenueDocumentRoot(String venueDocumentRoot);
  public void setReportingDocumentRoot(String reportingDocumentRoot);
}
