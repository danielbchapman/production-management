package com.danielbchapman.production.entity;

import java.sql.Connection;
import java.util.ArrayList;

import javax.persistence.EntityManager;

import org.theactingcompany.persistence.Indentifiable;
import com.danielbchapman.production.entity.DelegateInstance;

/**
 * @author dchapman
 * @since Aug 8, 2012
 * @copyright The Acting Company Aug 8, 2012
 */
public class EntityInstance
{
	private static DelegateInstance DELEGATE;

	/**
	 * @param clazz
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getAll(java.lang.Class)
	 */
	public static <T extends Indentifiable> ArrayList<T> getAll(Class<T> clazz)
	{
		return getDelegate().getAll(clazz);
	}

	/**
	 * @param obj
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#deleteObject(org.theactingcompany.persistence.Indentifiable)
	 */
	public static void deleteObject(org.theactingcompany.persistence.Indentifiable obj)
	{
		getDelegate().deleteObject(obj);
	}

	/**
	 * @param clazz
	 * @param id
	 * @return
	 */
	public static <T> T find(Class<T> clazz, Long id)
	{
		return getDelegate().find(clazz, id);
	}

	/**
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getConnection()
	 */
	public static Connection getConnection()
	{
		return getDelegate().getConnection();
	}

	public static DelegateInstance getDelegate()
	{
		if(DELEGATE == null)
			DELEGATE = new DelegateInstance();
		return DELEGATE;
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
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getResultList(java.lang.String,
	 *      java.lang.Class)
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
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getResultList(java.lang.String,
	 *      java.lang.Class, java.lang.Object[])
	 */
	public static <T> ArrayList<T> getResultList(String query, Class<T> clazz, Object... parameters)
	{
		return getDelegate().getResultList(query, clazz, parameters);
	}

	/**
	 * @param query
	 * @param parameters
	 * @param clazz
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#getResultList(java.lang.String,
	 *      java.lang.Object[], java.lang.Class)
	 */
	public static <T> ArrayList<T> getResultList(String query, Object[] parameters, Class<T> clazz)
	{
		return getDelegate().getResultList(query, parameters, clazz);
	}

	/**
	 * @param query
	 * @param clazz
	 * @return
	 */
	public static <T> T getSingleResult(String query, Class<T> clazz)
	{
		return getDelegate().getSingleResult(query, clazz);
	}

	/**
	 * @param query
	 * @param clazz
	 * @param parameters
	 * @return
	 */
	public static <T> T getSingleResult(String query, Class<T> clazz, Object... parameters)
	{
		return getDelegate().getSingleResult(query, clazz, parameters);
	}

	/**
	 * @param obj
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#saveObject(org.theactingcompany.persistence.Indentifiable)
	 */
	public static <T extends Indentifiable> T saveObject(T obj)
	{
		return getDelegate().saveObject(obj);
	}

	/**
	 * @param transaction
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#saveObjects(T[])
	 */
	public static <T extends Indentifiable> void saveObjects(T... transaction)
	{
		DELEGATE.saveObjects(transaction);
	}

	/**
	 * @param list
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#detatch(java.util.ArrayList)
	 */
	public static <T> ArrayList<T> detatch(ArrayList<T> list)
	{
		return getDelegate().detatch(list);
	}

	/**
	 * @param t
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#detatch(java.lang.Object)
	 */
	public static <T> T detatch(T t)
	{
		return getDelegate().detatch(t);
	}

	/**
	 * @param objects
	 * @return
	 * @see org.theactingcompany.persistence.AbstractEntityInstance#detatch(T[])
	 */
	public static <T> T[] detatch(T... objects)
	{
		return getDelegate().detatch(objects);
	}
}
