package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Production;
import com.danielbchapman.production.entity.TodoList;
import com.danielbchapman.production.entity.TodoStatusUpdate;

/**
 * A managed bean that handles interaction with TodoBeans and provides
 * a mapping of information.
 */
@Stateless
public class TodoDao implements TodoDaoRemote
{
//  @PersistenceContext
	EntityManager em = EntityInstance.getEm();
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#saveItem(com.danielbchapman.production.entity.TodoList)
   */
	public void saveItem(TodoList listItem)
	{
	  if(!Config.CONTAINER_MANAGED)
	    em.getTransaction().begin();
		
		if(listItem.getId() == null)
			em.persist(listItem);
		else
			em.merge(listItem);
		
		if(!Config.CONTAINER_MANAGED)
		  em.getTransaction().commit();
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#saveStatusUpdate(com.danielbchapman.production.entity.TodoList, java.lang.String)
   */
	public void saveStatusUpdate(TodoList listItem, String notes)
	{
		TodoStatusUpdate update = new TodoStatusUpdate();
		update.setLastUpdated(new Date());
		update.setNotes(notes);
		update.setTodoList(listItem);
		
		if(!Config.CONTAINER_MANAGED)
		  em.getTransaction().begin();
		
		em.persist(update);
		
		if(!Config.CONTAINER_MANAGED)
		  em.getTransaction().commit();
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getItems(com.danielbchapman.production.entity.Production)
   */
	@SuppressWarnings("unchecked")
	public ArrayList<TodoList> getItems(Production production)
	{
		ArrayList<TodoList> items = new ArrayList<TodoList>();
		
		if(production == null)
			return items;
		
		Query q = em.createQuery("SELECT t FROM TodoList t WHERE t.production = ?1 ORDER BY t.priority");
		q.setParameter(1, production);
		
		List<TodoList> results = (List<TodoList>)q.getResultList();
		
		if(results != null)
			for(TodoList list : results)
				items.add(list);

		return items;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getActiveItems(com.danielbchapman.production.entity.Production)
   */
	@SuppressWarnings("unchecked")
	public ArrayList<TodoList> getActiveItems(Production production)
	{
		ArrayList<TodoList> items = new ArrayList<TodoList>();
		
		if(production == null)
			return items;
		
		Query q = em.createQuery("SELECT t FROM TodoList t WHERE t.production = ?1 AND t.isComplete = ?2 ORDER BY t.priority");
		q.setParameter(1, production);
		q.setParameter(2, false);
		List<TodoList> results = (List<TodoList>)q.getResultList();
		
		if(results != null)
			for(TodoList list : results)
				items.add(list);

		return items;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getClosedItems(com.danielbchapman.production.entity.Production)
   */
	@SuppressWarnings("unchecked")
	public ArrayList<TodoList> getClosedItems(Production production)
	{
		ArrayList<TodoList> items = new ArrayList<TodoList>();
		
		if(production == null)
			return items;
		
		Query q = em.createQuery("SELECT t FROM TodoList t WHERE t.production = ?1 AND t.isComplete = ?2 ORDER BY t.priority");
		q.setParameter(1, production);
		q.setParameter(2, true);
		
		List<TodoList> results = (List<TodoList>)q.getResultList();
		
		if(results != null)
			for(TodoList list : results)
				items.add(list);

		return items;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getHistoryForItem(com.danielbchapman.production.entity.TodoList)
   */
	@SuppressWarnings("unchecked")
	public ArrayList<TodoStatusUpdate> getHistoryForItem(TodoList list)
	{
		 ArrayList<TodoStatusUpdate> ret =  new ArrayList<TodoStatusUpdate>();
		 
		 Query q = em.createQuery("SELECT t FROM TodoStatusUpdate t WHERE t.todoList = ?1 ORDER BY t.lastUpdated");
		 q.setParameter(1, list);
		 
		 List<TodoStatusUpdate> results = (List<TodoStatusUpdate>) q.getResultList();
		 if(results != null)
			 for(TodoStatusUpdate u : results)
				 ret.add(u);
		 
		 return ret;
	}
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getItem(java.lang.Long)
   */
	public TodoList getItem(Long id)
	{
		return em.find(TodoList.class, id);
	}
}
