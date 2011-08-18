package com.danielbchapman.production.beans;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Options;

/**
 * Session Bean implementation class OptionsDao
 */
@Stateless
public class OptionsDao implements OptionsDaoRemote
{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//@PersistenceContext
  EntityManager em = EntityInstance.getEm();
  /**
   * Default constructor.
   */
  public OptionsDao()
  {
    // TODO Auto-generated constructor stub
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.OptionsDaoRemote#getOptions()
   */
  @Override
  public Options getOptions()
  { 
    Options opt = em.find(Options.class, Options.PRODUCTION_ID);
    if(opt == null)
    {
      opt = new Options();
      opt.setId(Options.PRODUCTION_ID);
      opt.setConnectionString("null");
      opt.setVenueDocumentRoot("./documents/null/");
      
      EntityInstance.saveObject(opt);
    }
    return opt; 
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.OptionsDaoRemote#getEntityManager()
   */
  @Override
  public EntityManager getEntityManager()
  {
    return em;
  }

  @Override
  public void setConnectionString(String connectionString)
  {
    if(connectionString == null)
      throw new NullPointerException("Connection string can not be null, this will break all reporting");
    Options opt = getOptions();
    opt.setConnectionString(connectionString);
    EntityInstance.saveObject(opt);
  }

  @Override
  public void setVenueDocumentRoot(String venueDocumentRoot)
  {
    if(venueDocumentRoot == null)
      throw new NullPointerException("Document root can not be null, this will break all reporting");
    Options opt = getOptions();
    opt.setVenueDocumentRoot(venueDocumentRoot);
    EntityInstance.saveObject(opt);
    
  }

  @Override
  public void setReportingDocumentRoot(String reportingDocumentRoot)
  {
    if(reportingDocumentRoot == null)
      throw new NullPointerException("Reporting document root can not be null, this will break all reporting");
    Options opt = getOptions();
    opt.setReportingRoot(reportingDocumentRoot);
    EntityInstance.saveObject(opt);
    
  }

}
