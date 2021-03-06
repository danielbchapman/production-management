package org.theactingcompany.help.entity;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.theactingcompany.persistence.Indentifiable;

/**
 * A simple base object to be extended that handles the ID etc...
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Dec 28, 2010 2010
 * @version 2 Development
 * @link http://www.lightassistant.com
 ***************************************************************************
 */
@MappedSuperclass
public abstract class BaseEntity implements Indentifiable, Serializable
{
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  public final Long getId()
  {
    return id;
  }

  public final void setId(Long id)
  {
    this.id = id;
  }
  
  public String toString()
  {
    return this.getClass() + " ID:'" + id + "' " + super.toString(); 
  }
}
