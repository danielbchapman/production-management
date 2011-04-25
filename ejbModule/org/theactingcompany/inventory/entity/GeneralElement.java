package org.theactingcompany.inventory.entity;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Entity implementation class for Entity: Wardrobe
 */
@Entity
@Table(name="GeneralElement")
public class GeneralElement extends InventoryElement
{
  private static final long serialVersionUID = 1L;

}
