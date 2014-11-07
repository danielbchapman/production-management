package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * A set of options for this application. This is things like directories etc...
 */
@Entity
public class Options extends BaseEntity
{

  private static final long serialVersionUID = 1L;
  public final static Long PRODUCTION_ID = 1L;
  
  @Column(length=512)
  private String venueDocumentRoot;
  @Column(length=512)
  private String connectionString;
  @Column(length=512)
  private String reportingRoot;
  
  public String getConnectionString()
  {
    return connectionString;
  }

  public void setConnectionString(String connectionString)
  {
    this.connectionString = connectionString;
  }

  public Options()
  {
    super();
  }

  public String getVenueDocumentRoot()
  {
    return venueDocumentRoot;
  }

  public void setVenueDocumentRoot(String venueDocumentRoot)
  {
    this.venueDocumentRoot = venueDocumentRoot;
  }

  public String getReportingRoot()
  {
    return reportingRoot;
  }

  public void setReportingRoot(String reportingRoot)
  {
    this.reportingRoot = reportingRoot;
  }
  

}
