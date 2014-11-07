package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

/**
 * <em>This bean is a giant security hole. It will only be allowed
 * by those authenticated as administrator.</em><br />
 * 
 * This bean basically allows EJBQL queries directly to the layer. As such it 
 * is protected via admin rights.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jul 6, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface ReportingBeanRemote extends Serializable
{
	static final long serialVersionUID = 1L;
  /**
   * 
   * @param query
   * @param parameters the parameters indexed from 0 to X where 
   * <br/>
   * <code>
   * [0] = ?1<br />
   * [1] = ?2<br />
   *  . . .<br />
   * [length] = ?length+1<br />
   * </code>
   * @param clazz the class to query for
   * @return a list of objects after the parameters are applied  
   * 
   */
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz, Object... params);

  /**
   * 
   * @param query
   * @param clazz the class to query for
   * @return a list of objects based on th query  
   * 
   */
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz);
	
	public String echo();

}
