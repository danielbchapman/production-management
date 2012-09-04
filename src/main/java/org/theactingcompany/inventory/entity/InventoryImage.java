package org.theactingcompany.inventory.entity;

import javax.persistence.Entity;

import com.danielbchapman.production.entity.BaseEntity;

/**
 * A linked reference for all the images. 
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 27, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Entity
public class InventoryImage extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	private EmbeddableImage image;

	public EmbeddableImage getImage()
	{
    if(image == null)
    	image = new EmbeddableImage();

    return image;
	}
	public void setImage(EmbeddableImage image)
	{
    if(image == null)
    {
      getImage(); //Create a nulled photo
      return;
    }

    this.image = image;
	}
}
