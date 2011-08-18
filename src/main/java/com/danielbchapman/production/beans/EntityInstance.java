package com.danielbchapman.production.beans;

import java.sql.Connection;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.theactingcompany.persistence.AbstractEntityInstance;

public class EntityInstance
{
	private static DelegateInstance DELEGATE;

	/**
	 * @param obj
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#deleteObject(org.theactingcompany.persistence.Indentifiable)
	 */
	public static void deleteObject(org.theactingcompany.persistence.Indentifiable obj)
	{
		getDelegate().deleteObject(obj);
	}

	/**
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getConnection()
	 */
	public static Connection getConnection()
	{
		return getDelegate().getConnection();
	}

	/**
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getEm()
	 */
	public static EntityManager getEm()
	{
		return getDelegate().getEm();
	}

	/**
	 * @param query
	 * @param clazz
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getResultList(java.lang.String, java.lang.Class)
	 */
	public static <T> ArrayList<T> getResultList(String query, Class<T> clazz)
	{
		return getDelegate().getResultList(query, clazz);
	}

	/**
	 * @param query
	 * @param clazz
	 * @param parameters
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getResultList(java.lang.String, java.lang.Class, java.lang.Object[])
	 */
	public static  <T> ArrayList<T> getResultList(String query, Class<T> clazz, Object... parameters)
	{
		return getDelegate().getResultList(query, clazz, parameters);
	}

	/**
	 * @param query
	 * @param parameters
	 * @param clazz
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getResultList(java.lang.String, java.lang.Object[], java.lang.Class)
	 */
	public static <T> ArrayList<T> getResultList(String query, Object[] parameters, Class<T> clazz)
	{
		return getDelegate().getResultList(query, parameters, clazz);
	}

	/**
	 * @param obj
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#saveObject(org.theactingcompany.persistence.Indentifiable)
	 */
	public static void saveObject(org.theactingcompany.persistence.Indentifiable obj)
	{
		getDelegate().saveObject(obj);
	}

	public static DelegateInstance getDelegate()
	{
		if(DELEGATE == null)
			DELEGATE = new DelegateInstance();
		return DELEGATE;
	}
}
