package com.danielbchapman.production;

/**
 * A placeholder for "null" exceptions in the reporting.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 26, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class PlaceholderException extends Exception
{
  private static final long serialVersionUID = 1L;
  
  public PlaceholderException(String message)
  {
    super(message);
  }
}
