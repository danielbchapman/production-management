package org.theactingcompany.security;

import java.io.IOException;
import java.io.Serializable;
import java.security.Policy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityFilter implements Filter, Serializable
{
	private static final long serialVersionUID = 3L;
	public static String PRODUCTION_AUTH_TOKEN = "productionAuthToken";
	public static String PRODUCTION_AUTH_VALUE = "authenticated";
	public static String[] IGNORE_LIST = {"errorPage", "login", "console"}; 

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
		for(String s : IGNORE_LIST)
			if(uri.contains(s))
				ignore = true;
		
		boolean authenticated = PRODUCTION_AUTH_VALUE.equals(session.getAttribute(PRODUCTION_AUTH_TOKEN));
		
		if(!authenticated)
		{
			System.out.println("No filter not authentiated " + request.getRequestURL().toString());
			if(!ignore)
				if(!response.isCommitted())
				{
					response.sendRedirect(request.getContextPath() + "/login.xhtml");
					return;
				}
		}
		
		chain.doFilter(sRequest, sResponse);
		return;
	}

	@Override
	public void init(FilterConfig config) throws ServletException
	{
//		Policy currentPolicy = Policy.getPolicy();
//		if(!(currentPolicy instanceof ProductionPolicy))
//			Policy.setPolicy(new ProductionPolicy(currentPolicy));
	}

}
