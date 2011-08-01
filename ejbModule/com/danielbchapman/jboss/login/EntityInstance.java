package com.danielbchapman.jboss.login;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class EntityInstance
{
	private static EntityManager manager;

	/**
	 * @return an instance of the entity manager if it isn't there.
	 */
	public static EntityManager getEm()
	{
		if (manager == null || !manager.isOpen())
		{
			manager = Persistence.createEntityManagerFactory("authentication").createEntityManager();
			manager.setFlushMode(FlushModeType.COMMIT);
		}

		return manager;
	}

	public static Connection getConnection()
	{
		return ((EntityManager) getEm()).unwrap(java.sql.Connection.class);
		// return DriverManager.getConnection(CONNECTION_STRING, "sa", "***REMOVED***");
	}

	/**
	 * A simple wrapper to deal with container vs. local management for the
	 * system. This only works with the BaseEntity object.
	 */
	public static void saveObject(Indentifiable obj)
	{
		if (!Config.CONTAINER_MANAGED)
			getEm().getTransaction().begin();

		if (obj.getId() == null)
			getEm().persist(obj);
		else
			getEm().merge(obj);

		if (!Config.CONTAINER_MANAGED)
			getEm().getTransaction().commit();
	}

	/**
	 * A simple wrapper to deal with container vs. local management for the
	 * system. This only works with the BaseEntity object.
	 * 
	 * @param obj
	 *          removes this object from the ORM.
	 */
	public static void deleteObject(Indentifiable obj)
	{
		if (obj.getId() == null)
			throw new IllegalArgumentException(
					"Can not remove an object without ID, it doesn't exist in the Object Relational Mapping");

		if (!Config.CONTAINER_MANAGED)
			getEm().getTransaction().begin();

		getEm().remove(getEm().find(obj.getClass(), obj.getId())); // This makes
																																// sure the
																																// entity is
																																// managed

		if (!Config.CONTAINER_MANAGED)
			getEm().getTransaction().commit();
	}

	/**
	 * 
	 * @param query
	 * @param parameters
	 *          the parameters indexed from 0 to X where <br/>
	 *          <code>
	 * [0] = ?1<br />
	 * [1] = ?2<br />
	 *  . . .<br />
	 * [length] = ?length+1<br />
	 * </code>
	 * @param clazz
	 *          the class to query for
	 * @return a list of objects after the parameters are applied
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> getResultList(String query, Object[] parameters, Class<T> clazz)
	{
		ArrayList<T> ret = new ArrayList<T>();
		Query q = getEm().createQuery(query);
		if (parameters != null && parameters.length > 0)
			for (int i = 0; i < parameters.length; i++)
				q.setParameter(i + 1, parameters[i]);

		List<T> results = (List<T>) q.getResultList();
		if (results != null)
			for (T o : results)
				ret.add(o);

		return ret;
	}

	/**
	 * @param query
	 *          the query
	 * @param clazz
	 *          the class to query for
	 * @param parameters
	 *          a list of the parameters (starting at 1)
	 * @return <Return Description>
	 * 
	 */
	public static <T> ArrayList<T> getResultList(String query, Class<T> clazz, Object... parameters)
	{
		return getResultList(query, parameters, clazz);
	}

	/**
	 * @param query
	 * @param clazz
	 *          the class to query for
	 * @return a list of objects after the parameters are applied
	 * 
	 */
	@SuppressWarnings("unchecked")
	public static <T> ArrayList<T> getResultList(String query, Class<T> clazz)
	{
		ArrayList<T> ret = new ArrayList<T>();
		Query q = getEm().createQuery(query);

		List<T> results = (List<T>) q.getResultList();
		if (results != null)
			for (T o : results)
				ret.add(o);

		return ret;
	}
}
