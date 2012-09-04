package org.theactingcompany.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.danielbchapman.production.entity.BaseEntity;

/**
 * Entity implementation class for Entity: InventoryProblem
 * 
 * This class is a simple tracking device for problems in the inventory. This
 * allows a user to report a problem for any piece of inventory so that it can be 
 * tracked and resolved. Some form of notification would help this immensely but
 * at a minimum it gives a central place for things.
 * 
 */
@Entity
@Table(name="InventoryProblem")
public class InventoryProblem extends BaseEntity
{

  private static final long serialVersionUID = 1L;

  private Long entityId;
  @Column(length=256)
  private String entityClass;
  @Column(length=512)
  private String description;
  @Column(length=80)
  private String emailContact;
  @Column(length=40)
  private String phoneContact;
  @Column(length=50)
  private String name;
  private boolean contactMe;
  private boolean resolved;
  public String getDescription()
  {
    return description;
  }
  public String getEmailContact()
  {
    return emailContact;
  }

  public String getEntityClass()
  {
    return entityClass;
  }
  public Long getEntityId()
  {
    return entityId;
  }
  public String getName()
  {
    return name;
  }
  public String getPhoneContact()
  {
    return phoneContact;
  }
  public boolean isContactMe()
  {
    return contactMe;
  }
  public boolean isResolved()
  {
    return resolved;
  }
  public void setContactMe(boolean contactMe)
  {
    this.contactMe = contactMe;
  }
  public void setDescription(String description)
  {
    this.description = description;
  }
  public void setEmailContact(String emailContact)
  {
    this.emailContact = emailContact;
  }
  public void setEntityClass(String entityClass)
  {
    this.entityClass = entityClass;
  }
  public void setEntityId(Long entityId)
  {
    this.entityId = entityId;
  }
  public void setName(String name)
  {
    this.name = name;
  }
  public void setPhoneContact(String phoneContact)
  {
    this.phoneContact = phoneContact;
  }
  public void setResolved(boolean resolved)
  {
    this.resolved = resolved;
  }

}
