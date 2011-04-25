package org.theactingcompany.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Wardrobe
 */
@Entity
@Table(name="WardrobeElement")
public class WardrobeElement extends InventoryElement
{
  private static final long serialVersionUID = 1L;

  private String period;
  private String sex;

  public String getPeriod()
  {
    return period;
  }
  public String getSex()
  {
    return sex;
  }
  public void setPeriod(String period)
  {
    if(period != null)
      period = period.replaceAll("'", "");
    this.period = period;
  }
  public void setSex(String sex)
  {
    this.sex = sex;
  }
  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.entity.BaseEntity#toString()
   */
  public String toString()
  {
    StringBuilder buf = new StringBuilder();

    buf.append(super.toString());
    buf.append("\nPeriod: ");
    buf.append(period);
    buf.append("\nSex: ");
    buf.append(sex);

    return buf.toString();

  }
}
