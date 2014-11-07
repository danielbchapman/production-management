package org.theactingcompany.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Lob;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

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

  @OneToOne
  private BarCode barCode;

  @Column(length=200)
  private String colors;

  @Column(length=32)
  private String condition;
  private Integer count;
  @Lob
  private String description;
  private Long image;
  private Boolean inactive;
  @Transient
  private EmbeddableImage localImage;
  @Column(length=64)
  private String location;
	@Lob
  private String notes;
	@Column(length=128)
  private String production;
  @Column(length=200)
  private String serialNumberOrId;
  @Column(length=64)
  private String type;
  @Lob
  private String userNotes;
  
  private Double weight;
	public BarCode getBarCode()
  {
    return barCode;
  }

	public String getColors()
  {
    return colors;
  } 
  
  public String getCondition()
  {
    return condition;
  }
	/**
	 * @return the number of units, one if null  
	 */
	public Integer getCount()
	{
		if(count == null)
			count = 1;
		return count;
	}
	public String getDescription()
  {
    return description;
  }
  public Long getImage()
	{
		return image;
	}
  public Boolean getInactive()
  {
    return inactive;
  }
  public EmbeddableImage getLocalImage()
	{
		return localImage;
	}

  public String getLocation()
  {
    return location;
  }

  public String getNotes()
  {
    return notes;
  }

  public String getProduction()
  {
    return production;
  }

  public String getSerialNumberOrId()
  {
    return serialNumberOrId;
  }

  public String getType()
  {
    return type;
  }

  public String getUserNotes()
  {
    return userNotes;
  }

  public Double getWeight()
  {
    return weight;
  }

  public void setBarCode(BarCode barCode)
  {
    this.barCode = barCode;
  }

  public void setColors(String colors)
  {
    this.colors = colors;
  }

  public void setCondition(String condition)
  {
    if(condition != null)
      condition = condition.replaceAll("'", "");
    this.condition = condition;
  }

  public void setCount(Integer count)
	{
		this.count = count;
	}

  public void setDescription(String description)
  {
    this.description = description;
  }

  public void setImage(Long image)
	{
		this.image = image;
	}

  public void setInactive(Boolean inactive)
  {
    this.inactive = inactive;
  }

  public void setLocalImage(EmbeddableImage localImage)
	{
		this.localImage = localImage;
	}

  public void setLocation(String location)
  {
    if(location != null)
      location = location.replaceAll("'", "");
    this.location = location;
  }

  public void setNotes(String notes)
  {
    this.notes = notes;
  }

  public void setProduction(String production)
  {
    if(production != null)
      production = production.replaceAll("'", "");
    this.production = production;
  }

  public void setSerialNumberOrId(String serialNumberOrId)
  {
    this.serialNumberOrId = serialNumberOrId;
  }

  public void setType(String type)
  {
    if(type != null)
      type = type.replaceAll("'", "");
    this.type = type;
  }
  
  public void setUserNotes(String userNotes)
  {    
    this.userNotes = userNotes;
  }
  
  public void setWeight(Double weight)
  {
    this.weight = weight;
  }

  public String toString()
  {
    StringBuilder buf = new StringBuilder();

    buf.append(super.toString());
    buf.append("\nPhoto ? ");
    buf.append(image);
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
