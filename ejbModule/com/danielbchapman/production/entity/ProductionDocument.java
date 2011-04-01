package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * A production document is specific to a Venue and represents something specific
 * on the file system. This allows the database to easily track uploads and downloads 
 * and to allow a confirmation.
 */
@Entity
public class ProductionDocument extends BaseEntity
{

  private static final long serialVersionUID = 1L;

  @Column(length=512)
  private String fileName;
  @Temporal(TemporalType.TIMESTAMP)
  private Date lastModified;
  @Column(length=50)
  private String lastModifiedBy;
  @Lob
  private byte[] value;
  private Venue venue;
  private Production production;
  private boolean isGlobalDocument;
  
  public ProductionDocument()
  {
    super();
  }

  public String getFileName()
  {
    return fileName;
  }

  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }

  public Date getLastModified()
  {
    return lastModified;
  }

  public void setLastModified(Date lastModified)
  {
    this.lastModified = lastModified;
  }

  public String getLastModifiedBy()
  {
    return lastModifiedBy;
  }

  public void setLastModifiedBy(String lastModifiedBy)
  {
    this.lastModifiedBy = lastModifiedBy;
  }

  public byte[] getValue()
  {
    return value;
  }

  public void setValue(byte[] value)
  {
    this.value = value;
  }

  public Venue getVenue()
  {
    return venue;
  }

  public void setVenue(Venue venue)
  {
    this.venue = venue;
  }

  public Production getProduction()
  {
    return production;
  }

  public void setProduction(Production production)
  {
    this.production = production;
  }

  public boolean isGlobalDocument()
  {
    return isGlobalDocument;
  }

  public void setGlobalDocument(boolean isGlobalDocument)
  {
    this.isGlobalDocument = isGlobalDocument;
  }

}
