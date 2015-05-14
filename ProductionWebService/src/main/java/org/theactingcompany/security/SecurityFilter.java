package org.theactingcompany.security;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter, Serializable
{
	private static final long serialVersionUID = 3L;
	public static String PRODUCTION_AUTH_TOKEN = "productionAuthToken";
	public static String PRODUCTION_AUTH_VALUE = "authenticated";
	private HashSet<String> ignoreList = new HashSet<String>();
	private String redirect = "/login.xhtml";

	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest sRequest, ServletResponse sResponse, FilterChain chain)
			throws IOException, ServletException
			{
		HttpServletResponse response = (HttpServletResponse) sResponse;
		HttpServletRequest request = (HttpServletRequest) sRequest;

		HttpSession session = ((HttpServletRequest) sRequest).getSession(true);

		boolean ignore = false;
		String uri = request.getRequestURI().toLowerCase();
		
		if(uri.equals("/"))
			ignore = true;
		
		for(String s : ignoreList)
			if(uri.contains(s))
				ignore = true;

		boolean authenticated = PRODUCTION_AUTH_VALUE.equals(session.getAttribute(PRODUCTION_AUTH_TOKEN));

		if(!authenticated && !ignore)
		{
			System.out.println("No filter not authentiated " + request.getRequestURL().toString());
			if(!ignore)
				if(!response.isCommitted())
				{
					response.sendRedirect(request.getContextPath() + redirect);
					return;
				}
		}

		chain.doFilter(sRequest, sResponse);
		return;
			}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
		String redirect = config.getInitParameter("redirect");
		if(redirect != null)
			this.redirect = redirect;
		
		String exclude = config.getInitParameter("exclude");
		if(exclude == null)
			return;
		
		String[] list = exclude.split("\\s+");
		for(String s : list)
			if(s != null)
				ignoreList.add(s);	
	}
}
