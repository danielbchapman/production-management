package org.theactingcompany.persistence;

import java.io.Serializable;

/**
 * A simple interface used by the persistence shortcuts.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 20, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public interface Indentifiable extends Serializable
{
  public Long getId();
  public void setId(Long id);
}
