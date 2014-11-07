package com.danielbchapman.production;

import javax.naming.NamingException;

/**
 * Read the name
 *
 *********************************************** 
 * @author Daniel B. Chapman 
 * <br /><i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Dec 13, 2010 2010
 * @version 2 Development
 * @link http://www.lightassistant.com
 */
public class RuntimeNamingException extends RuntimeException
{
  private static final long serialVersionUID = 1L;
  
  public RuntimeNamingException(NamingException e)
  {
    super(e);
  }

}
