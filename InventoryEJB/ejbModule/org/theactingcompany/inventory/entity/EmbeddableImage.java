package org.theactingcompany.inventory.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;

/**
 * A simple embeddable image that can easily be created and set.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 13, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Embeddable
public class EmbeddableImage implements Serializable
{
  private static final long serialVersionUID = 1L;
  @Column(length=128)
  private String mimeType;
  @Lob
  @Basic(fetch=FetchType.LAZY)
  private byte[] image;
  @Column (length=256)
  private String fileName;

  public String getMimeType()
  {
    return mimeType;
  }
  
  public void setMimeType(String mimeType)
  {
    this.mimeType = mimeType;
  }
  
  public byte[] getImage()
  {
    return image;
  }
  public void setImage(byte[] image)
  {
    this.image = image;
  }

  public String getFileName()
  {
    return fileName;
  }
  public void setFileName(String fileName)
  {
    this.fileName = fileName;
  }
  /**
   * Checks to see if the image is null
   * @return <b>true</b> if the image exists <br/> <b>false</b> if the image does not exist
   * 
   */
  public boolean isImageAvailable()
  {
    if(image == null || image.length == 0)
      return false;
    else
      return true;
  }
}
