package org.theactingcompany.inventory.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;

@Entity
public class BarCode implements Serializable, Indentifiable
{
  private static final long serialVersionUID = 1L;
  @Id
  @TableGenerator
  (
    allocationSize=1,
    initialValue=00110000,//Start Bar codes here (2011--00) (just so we don't have crap numbers)
    name="bar_code",
    table="GENERATED_VALUES" 
  )
  @GeneratedValue(strategy=GenerationType.TABLE, generator="bar_code")
  private Long id;

  public Long getId()
  {
    return id;
  }

  public void setId(Long id)
  {
    this.id = id;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  public String toString()
  {
    return "" + id;
  }
}
