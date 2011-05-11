package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Department;
import com.danielbchapman.production.entity.Production;
import com.danielbchapman.production.entity.Task;
import com.danielbchapman.production.entity.TaskStatusUpdate;
import com.danielbchapman.production.entity.TaskUser;

/**
 * A managed bean that handles interaction with TodoBeans and provides
 * a mapping of information.
 */
@Stateless
public class TaskDao implements TaskDaoRemote
{
//  @PersistenceContext
	EntityManager em = EntityInstance.getEm();
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#saveItem(com.danielbchapman.production.entity.TodoList)
   */
	public void saveItem(Task listItem, String user)
	{
		listItem.setLastUpdatedBy(user);
		listItem.setLastUpdated(new Date());
		EntityInstance.saveObject(listItem);
		
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#saveStatusUpdate(com.danielbchapman.production.entity.TodoList, java.lang.String)
   */
	public void saveStatusUpdate(Task listItem, String notes, String user)
	{
		TaskStatusUpdate update = new TaskStatusUpdate();
		update.setLastUpdated(new Date());
		update.setNotes(notes);
		update.setReminder(listItem);
		
		EntityInstance.saveObject(update);
		listItem.setLastUpdatedBy(user);
		listItem.setLastUpdated(new Date());
		EntityInstance.saveObject(listItem);
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getItems(com.danielbchapman.production.entity.Production)
   */
	@SuppressWarnings("unchecked")
	public ArrayList<Task> getItems(Production production)
	{
		ArrayList<Task> items = new ArrayList<Task>();
		
		if(production == null)
			return items;
		
		Query q = em.createQuery("SELECT t FROM Reminder t WHERE t.production = ?1 ORDER BY t.priority");
		q.setParameter(1, production);
		
		List<Task> results = (List<Task>)q.getResultList();
		
		if(results != null)
			for(Task list : results)
				items.add(list);

		return items;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getActiveItems(com.danielbchapman.production.entity.Production)
   */
	@SuppressWarnings("unchecked")
	public ArrayList<Task> getActiveTopLevelItems(Production production)
	{
		ArrayList<Task> items = new ArrayList<Task>();
		
		if(production == null)
			return items;
		
		Query q = em.createQuery("SELECT t FROM Task t WHERE t.production = ?1 AND AND t.parentTask = ?1 t.isComplete = ?3 ORDER BY t.priority");
		q.setParameter(1, production);
		q.setParameter(2, null);
		q.setParameter(3, false);
		
		List<Task> results = (List<Task>)q.getResultList();
		
		if(results != null)
			for(Task list : results)
				items.add(list);

		return items;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getClosedItems(com.danielbchapman.production.entity.Production)
   */
	@SuppressWarnings("unchecked")
	public ArrayList<Task> getClosedTopLevelItems(Production production)
	{
		ArrayList<Task> items = new ArrayList<Task>();
		
		if(production == null)
			return items;
		
		Query q = em.createQuery("SELECT t FROM Task t WHERE t.production = ?1 AND AND t.parentTask = ?1 t.isComplete = ?3 ORDER BY t.priority");
		q.setParameter(1, production);
		q.setParameter(2, null);
		q.setParameter(3, true);
		
		List<Task> results = (List<Task>)q.getResultList();
		
		if(results != null)
			for(Task list : results)
				items.add(list);

		return items;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getHistoryForItem(com.danielbchapman.production.entity.TodoList)
   */
	@SuppressWarnings("unchecked")
	public ArrayList<TaskStatusUpdate> getHistoryForItem(Task list)
	{
		 ArrayList<TaskStatusUpdate> ret =  new ArrayList<TaskStatusUpdate>();
		 
		 Query q = em.createQuery("SELECT t FROM TaskStatusUpdate t WHERE t.reminder = ?1 ORDER BY t.lastUpdated");
		 q.setParameter(1, list);
		 
		 List<TaskStatusUpdate> results = (List<TaskStatusUpdate>) q.getResultList();
		 if(results != null)
			 for(TaskStatusUpdate u : results)
				 ret.add(u);
		 
		 return ret;
	}
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.TodoDaoRemote#getItem(java.lang.Long)
   */
	public Task getItem(Long id)
	{
		return em.find(Task.class, id);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.ReminderDaoRemote#getReminderNone()
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ArrayList<Task> getTaskReminderNone()
	{
		ArrayList<Task> items = new ArrayList<Task>();
		
		Query q = em.createQuery("SELECT t FROM Task t WHERE t.isComplete = ?1 AND t.priority = ?2 ORDER BY t.priority");
		q.setParameter(1, false);
		q.setParameter(2, com.danielbchapman.production.entity.Priority.NONE);
		
		List<Task> results = (List<Task>)q.getResultList();
		
		if(results != null)
			for(Task list : results)
				items.add(list);

		return items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Task> getTaskReminderEmail()
	{
		ArrayList<Task> items = new ArrayList<Task>();
		
		Query q = em.createQuery("SELECT t FROM Task t WHERE t.isComplete = ?1 AND t.priority = ?2 ORDER BY t.priority");
		q.setParameter(1, false);
		q.setParameter(2, com.danielbchapman.production.entity.Priority.E_MAIL);
		
		List<Task> results = (List<Task>)q.getResultList();
		
		if(results != null)
			for(Task list : results)
				items.add(list);

		return items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Task> getTaskReminderUrget()
	{
		ArrayList<Task> items = new ArrayList<Task>();
		
		Query q = em.createQuery("SELECT t FROM Task t WHERE t.isComplete = ?1 AND t.priority = ?2 ORDER BY t.priority");
		q.setParameter(1, false);
		q.setParameter(2, com.danielbchapman.production.entity.Priority.URGENT);
		
		List<Task> results = (List<Task>)q.getResultList();
		
		if(results != null)
			for(Task list : results)
				items.add(list);

		return items;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<TaskUser> getAllTaskUsers()
	{
		ArrayList<TaskUser> items = new ArrayList<TaskUser>();
		
		Query q = em.createQuery("SELECT t FROM TaskUser ORDER BY t.name");
		q.setParameter(1, false);
		q.setParameter(2, com.danielbchapman.production.entity.Priority.URGENT);
		
		List<TaskUser> results = (List<TaskUser>)q.getResultList();
		
		if(results != null)
			for(TaskUser list : results)
				items.add(list);

		return items;
	}

	@Override
	public void addTaskUser(String name, String position)
	{
		TaskUser user = new TaskUser();
		user.setName(name);
		user.setPosition(position);
		EntityInstance.saveObject(user);
	}

	@Override
	public void removeTaskUser(TaskUser user, TaskUser reassign)
	{
		if(reassign == null)
		{
			Query q = em.createQuery("DELETE Task u WHERE u.assignedTo = ?1");
			q.setParameter(1, user);
			q.executeUpdate();
		}
		else
		{
			Query q = em.createQuery("UPDATE TaskUser u SET u.assignedTo = ?1 WHERE assignedTo = ?2");
			q.setParameter(1, reassign);
			q.setParameter(2, user);
		}
		
		EntityInstance.deleteObject(user);
	}
}
