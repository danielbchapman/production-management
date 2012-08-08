package org.theactingcompany.persistence;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.FlushModeType;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;

import com.sun.corba.se.spi.ior.Identifiable;

public abstract class AbstractEntityInstance
{
	private AbstractEntityInstance instance;
	private EntityManagerFactory factory;

	/**
	 * A simple wrapper to deal with container vs. local management for the system. This only works
	 * with the BaseEntity object.
	 * 
	 * @param obj
	 *          removes this object from the ORM.
	 */
	public void deleteObject(Indentifiable obj)
	{
		if(obj.getId() == null)
			throw new IllegalArgumentException(
					"Can not remove an object without ID, it doesn't exist in the Object Relational Mapping");

		EntityManager em = getEm();
		if(!isContainerManaged())
			em.getTransaction().begin();

		em.remove(getEm().find(obj.getClass(), obj.getId())); // This makes sure the entity is managed

		if(!isContainerManaged())
			em.getTransaction().commit();

		em.close();
	}

	public Connection getConnection()
	{
		return ((EntityManager) getEm()).unwrap(java.sql.Connection.class);
	}

	public <T> T detatch(T t)
	{
		EntityManager em = getEm();
		em.detach(t);
		em.close();
		return t;
	}

	public <T> ArrayList<T> detatch(ArrayList<T> list)
	{
		EntityManager em = getEm();
		for(T t : list)
			em.detach(t);

		em.close();
		return list;
	}

	public <T> T[] detatch(T... objects)
	{
		if(objects.length > 0)
		{
			EntityManager em = getEm();
			for(int i = 0; i < objects.length; i++)
				if(objects[i] != null)
					em.detach(objects[i]);

			em.close();
		}
		return objects;
	}

	public <T> T find(Class<T> clazz, Long id)
	{
		EntityManager em = getEm();
		T t = em.find(clazz, id);
		em.close();

		return t;
	}

	/**
	 * @return a link to the current instance of this EntityInstance subclass
	 */
	protected AbstractEntityInstance getCurrentInstance()
	{
		if(instance == null)
		{
			try
			{
				instance = getClass().newInstance();
			}
			catch(InstantiationException e)
			{
				e.printStackTrace();
			}
			catch(IllegalAccessException e)
			{
				e.printStackTrace();
			}
		}
		return instance;
	}

	/**
	 * @return an instance of the entity manager if it isn't there.
	 */
	public EntityManager getEm()
	{
		if(factory == null || !factory.isOpen())
			factory = Persistence.createEntityManagerFactory(getPersistenceUnitId());

		EntityManager em = factory.createEntityManager();
		em.setFlushMode(FlushModeType.AUTO);
		return em;
	}

	/**
	 * @return the name of the persistence unit e.g. "production"
	 */
	protected abstract String getPersistenceUnitId();

	/**
	 * @param query
	 *          the query to use
	 * @param clazz
	 *          the class to query for
	 * @return a single result if it exists, null if it does not
	 */
	public <T> T getSingleResult(String query, Class<T> clazz)
	{
		return getSingleResult(query, clazz, (Object[]) null);
	}

	@SuppressWarnings("unchecked")
	public <T> T getSingleResult(String query, Class<T> clazz, Object... parameters)
	{
		EntityManager em = getEm();

		Query q = em.createQuery(query);
		if(parameters != null && parameters.length > 0)
			for(int i = 0; i < parameters.length; i++)
				q.setParameter(i + 1, parameters[i]);

		T t = null;

		try
		{
			t = (T) q.getSingleResult();
		}
		catch(NoResultException e)
		{// Die silently
		}

		em.close();

		return t;
	}

	/**
	 * @param transaction
	 *          the objects to save in order of a transaction
	 * @return void
	 */
	public <T extends Indentifiable> void saveObjects(T... transaction)
	{
		EntityManager em = getEm();

		if(!isContainerManaged())
			em.getTransaction().begin();

		for(T t : transaction)
			if(t.getId() == null)
				em.persist(t);
			else
				em.merge(t);

		if(!isContainerManaged())
			em.getTransaction().commit();

		em.close();
	}

	/**
	 * @param query
	 * @param clazz
	 *          the class to query for
	 * @return a list of objects after the parameters are applied
	 * 
	 */
	@SuppressWarnings("unchecked")
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz)
	{
		EntityManager em = getEm();
		ArrayList<T> ret = new ArrayList<T>();
		Query q = em.createQuery(query);

		List<T> results = (List<T>) q.getResultList();
		if(results != null)
			for(T o : results)
				ret.add(o);

		em.close();
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
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz, Object... parameters)
	{
		return getResultList(query, parameters, clazz);
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
	public <T> ArrayList<T> getResultList(String query, Object[] parameters, Class<T> clazz)
	{
		ArrayList<T> ret = new ArrayList<T>();
		EntityManager em = getEm();

		Query q = em.createQuery(query);
		if(parameters != null && parameters.length > 0)
			for(int i = 0; i < parameters.length; i++)
				q.setParameter(i + 1, parameters[i]);

		List<T> results = (List<T>) q.getResultList();
		if(results != null)
			for(T o : results)
				ret.add(o);

		em.close();
		return ret;
	}

	/**
	 * @return <b>true</b> if container managed
	 */
	protected abstract boolean isContainerManaged();

	/**
	 * A simple wrapper to deal with container vs. local management for the system. This only works
	 * with the BaseEntity object.
	 */
	public <T extends Indentifiable> T saveObject(T obj)
	{
		EntityManager em = getEm();
		if(!isContainerManaged())
			em.getTransaction().begin();

		if(obj.getId() == null)
			em.persist(obj);
		else
			em.merge(obj);

		if(!isContainerManaged())
			em.getTransaction().commit();

		em.close();
		return obj;
	}

}
