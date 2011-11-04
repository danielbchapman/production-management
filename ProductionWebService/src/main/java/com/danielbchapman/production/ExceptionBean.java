package com.danielbchapman.production;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Stack;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.theactingcompany.core.bugs.Bug;
import org.theactingcompany.core.bugs.BugsDaoRemote;

/**
 * A simple bean that has a log of all exceptions and can be used to report/log them etc...
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Apr 26, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@SessionScoped
public class ExceptionBean
{
	private Bug bug = new Bug();
	private BugsDaoRemote bugDao;
	private Stack<Throwable> exceptions = new Stack<Throwable>();

	public ExceptionBean()
	{
	}

	public void addException(Throwable t)
	{
		exceptions.push(t);
	}

	public void clearExceptionArray(ActionEvent evt)
	{
		exceptions.clear();
	}

	public Stack<Throwable> getAllExceptions()
	{
		return exceptions;
	}

	public Bug getBug()
	{
		return bug;
	}

	public Throwable getTopException()
	{
		if(exceptions.size() == 0)
			return new PlaceholderException("No errors at this time");

		Throwable check = exceptions.peek();

		if(check == null)
			check = new PlaceholderException("No errors at this time");

		return check;
	}

	public void logOut(ActionEvent evt)
	{
		try
		{
			FacesContext.getCurrentInstance().getExternalContext().redirect("logoutbasic.jsp");
		}
		catch(IOException e)
		{
			e.printStackTrace();
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void reportError(ActionEvent evt)
	{
		StringWriter writer = new StringWriter();
		PrintWriter printWriter = new PrintWriter(writer);
		for(Throwable t : exceptions)
			t.printStackTrace(printWriter);

		bug.setStackTrace(writer.toString());
		bug.setStatus("REPORTED");

		getBugsDao().saveBug(bug);

		bug = new Bug();

		Utility.redirect("index.xhtml");
	}

	public void throwException(ActionEvent evt)
	{
		throw new RuntimeException("Test Wrapper A Cause", new RuntimeException(
				"Test exception Wrapper B", new RuntimeException(
						"This is a test exception to make sure error reporting is up and working.")));
	}

	private BugsDaoRemote getBugsDao()
	{
		if(bugDao == null)
			bugDao = Utility.getObjectFromContext(BugsDaoRemote.class, Utility.Namespace.BUGS);

		return bugDao;
	}
}
