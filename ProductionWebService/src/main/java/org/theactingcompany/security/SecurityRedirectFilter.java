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

public class SecurityRedirectFilter extends SecurityFilter
{
	private static final long serialVersionUID = 702795695770105328L;
	
	private HashSet<String> exclude = new HashSet<String>();
	@Override
	public void destroy()
	{
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException
	{
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		for(String s : exclude)
			if(request.getRequestURI().toLowerCase().contains(s))
			{
				response.sendRedirect(request.getContextPath() + "/not_authorized.xhtml");
				return;
			}
		
		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
	}

}
