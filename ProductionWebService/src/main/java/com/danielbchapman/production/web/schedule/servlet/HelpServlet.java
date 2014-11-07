package com.danielbchapman.production.web.schedule.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.web.production.beans.HelpBean;

public class HelpServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
  /**
   * @see HttpServlet#HttpServlet()
   */
  public HelpServlet()
  {
    super();
  }
  
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Object obj = request.getParameter("lookup");
		HttpSession session = request.getSession();
		HelpBean helpBean = (HelpBean) session.getAttribute("helpBean");
		
		if(helpBean == null)
		{
			helpBean = new HelpBean();
			session.setAttribute("helpBean", helpBean);
		}	
		
		if(obj == null)
		{
			helpBean.setLookupString("404");
			helpBean.refreshHelp(null);
			Utility.redirect("." + request.getContextPath() + "/help.xhtml");
		}
		else
		{
			String lookupString = (String) request.getAttribute("lookup");
			helpBean.setLookupString(lookupString);
			helpBean.refreshHelp(null);
			Utility.redirect("." + request.getContextPath() + "/help.xhtml");
		}
	}

}
