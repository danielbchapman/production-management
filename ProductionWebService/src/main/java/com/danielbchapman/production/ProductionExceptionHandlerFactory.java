package com.danielbchapman.production;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Can I just say how much I hate factory implementations? Anyhow...
 * @see ExceptionHandlerFactory
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 26, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public class ProductionExceptionHandlerFactory extends ExceptionHandlerFactory
{

  private ExceptionHandlerFactory parent;

  /**
   * Default Constructor.
   * @param parent
   */
  public ProductionExceptionHandlerFactory(ExceptionHandlerFactory parent)
  {
    this.parent = parent;
  }

  /* (non-Javadoc)
   * @see javax.faces.context.ExceptionHandlerFactory#getExceptionHandler()
   */
  @Override
  public ExceptionHandler getExceptionHandler()
  {
    return new ProductionExceptionHandler(parent.getExceptionHandler());
  }
}