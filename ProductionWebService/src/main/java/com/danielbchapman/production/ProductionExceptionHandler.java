package com.danielbchapman.production;

import java.io.IOException;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 * An extension of the exception handler. This will force redirects on silent errors instead of just
 * spewing to the command line.
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Apr 26, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
public class ProductionExceptionHandler extends ExceptionHandlerWrapper
{
	private ExceptionHandler wrapper;

	public ProductionExceptionHandler(ExceptionHandler wrapper)
	{
		this.wrapper = wrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.context.ExceptionHandlerWrapper#getWrapped()
	 */
	@Override
	public ExceptionHandler getWrapped()
	{
		return wrapper;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.context.ExceptionHandlerWrapper#handle()
	 */
	@Override
	public void handle() throws FacesException
	{
		Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
		ExceptionBean bean = (ExceptionBean) Utility.getBean("exceptionBean");

		if(i.hasNext())
			bean.clearExceptionArray(null);

		while(i.hasNext())
		{
			ExceptionQueuedEvent event = i.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

			Throwable t = context.getException();

			try
			{
				if(ViewExpiredException.class.isAssignableFrom(t.getClass())
						|| IllegalStateException.class.isAssignableFrom(t.getClass()))
				{
					try
					{
						if(ViewExpiredException.class.isAssignableFrom(t.getClass()))
							System.out.println("view expired -> Redirecting");
						else
							System.out.println("Illegal State -> Redirecting");
						FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_WARN, "Session Expired",
										"Your session has timed out, you have been redirected to the home page."));
					}
					catch(IOException e)
					{
						e.printStackTrace();
						throw new FacesException(
								"FATAL ERROR: Application Menu Unstable [MenueBean$MenueItem:81], please log out and try again.",
								e);
					}
				}
				else
					stackExceptions(t, bean);
			}
			finally
			{
				i.remove();
			}

			try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("errorPage.xhtml");
			}
			catch(IOException e)
			{
				throw new FacesException(e.getMessage(), e);
			}
		}
	}

	/**
	 * I &lt;3 recursion. <br/>
	 * Its a primative "getRootCause" but stacks to the exception bean.
	 * 
	 * @param t
	 *          the exception to trace
	 * @param bean
	 *          A reference ot the exception bean
	 * 
	 */
	private void stackExceptions(Throwable t, ExceptionBean bean)
	{
		bean.addException(t);

		if(t.getCause() == null)
			return;

		stackExceptions(t.getCause(), bean);
	}
}