package org.theactingcompany.inventory.entity;

import javax.persistence.MappedSuperclass;

/**
 * A simple base type for elements that contains a general amount of information 
 * that can then be extended if needed.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 13, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@MappedSuperclass
public abstract class InventoryElement extends BaseEntity
{
  private static final long serialVersionUID = 1L;
  
  private String colors;
  private String condition;
  private String description;
  private String location;
  private String notes;
  private EmbeddableImage photo;
  private InventoryProduction production;
  private String serialNumberOrId;
  private String type;
  private String userNotes;
  private Double weight;
  
  public String getColors()
  {
    return colors;
  }

  public void setColors(String colors)
  {
    this.colors = colors;
  }

  public String getCondition()
  {
    return condition;
  }

  public void setCondition(String condition)
  {
    this.condition = condition;
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getLocation()
  {
    return location;
  }

  public void setLocation(String location)
  {
    this.location = location;
  }

  public String getNotes()
  {
    return notes;
  }

  public void setNotes(String notes)
  {
    this.notes = notes;
  }

  public InventoryProduction getProduction()
  {
    return production;
  }

  public void setProduction(InventoryProduction production)
  {
    this.production = production;
  }

  public String getSerialNumberOrId()
  {
    return serialNumberOrId;
  }

  public void setSerialNumberOrId(String serialNumberOrId)
  {
    this.serialNumberOrId = serialNumberOrId;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }

  public String getUserNotes()
  {
    return userNotes;
  }

  public void setUserNotes(String userNotes)
  {
    this.userNotes = userNotes;
  }

  public Double getWeight()
  {
    return weight;
  }

  public void setWeight(Double weight)
  {
    this.weight = weight;
  }

  public EmbeddableImage getPhoto()
  {
    if(photo == null)
      photo = new EmbeddableImage();

    return photo;
  }
  
  public final String getTableName()
  {
    return getClass().getSimpleName();
  }
  
  public void setPhoto(EmbeddableImage photo)
  {
    if(photo == null)
    {
      getPhoto(); //Create a nulled photo
      return;
    }

    this.photo = photo;
  }

  public String toString()
  {
    StringBuilder buf = new StringBuilder();

    buf.append(super.toString());
    buf.append("\nPhoto ? ");
    buf.append(photo == null ? "false" : photo.isImageAvailable());
    buf.append("\nType: ");
    buf.append(type);
    buf.append("\nCondition: ");
    buf.append(condition);
    buf.append("\nSerial/ID: ");
    buf.append(serialNumberOrId);
    buf.append("\nProduction: ");
    buf.append(production);
    buf.append("\nDescription: ");
    buf.append(description);
    buf.append("\nColors: ");
    buf.append(colors);
    buf.append("\nNotes: ");
    buf.append(notes);
    buf.append("\nUser Notes: ");
    buf.append(userNotes);

    return buf.toString();

  }
  
  /**
   * @return <b>true</b> if the required fields are present and <b>false</b> if the fields are not  
   * 
   */
  public boolean validate()
  {
    if(type == null || type.trim().length() == 0)
      return false;
    
    if(description == null || description.trim().length() == 0)
      return false;
    
    return true;
  }
}
