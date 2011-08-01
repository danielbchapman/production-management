package com.danielbchapman.production.beans;


import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Production;
import com.danielbchapman.production.entity.Task;
import com.danielbchapman.production.entity.TaskStatusUpdate;

/**
 * <p>
 * This is the primary interface for the TaskDao. All methods here relate
 * to managing tasks and history of tasks.
 * </p>
 * @see {@link com.danielbchapman.production.entity.Task}
 * @see EJB3
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since May 12, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface TaskDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

  /**
   * Saves or updates an item.
   * @param listItem
   */
  public abstract void saveItem(Task listItem, String user);

  /**
   * Save an update related to this status for the log.
   * @param listItem the item to update
   * @param notes the notes to save
   * @param user the user principle for the log  
   * 
   */
  public abstract void saveStatusUpdate(Task listItem, String notes, String user);

  /**
   * @return a list of all items
   */
  public abstract ArrayList<Task> getItems(Production production);

  /**
   * @return a list of all items that are not children
   */
  public abstract ArrayList<Task> getActiveTopLevelItems(Production production);

  /**
   * @return a list of all items that are not children
   */
  public abstract ArrayList<Task> getClosedTopLevelItems(Production production);

  /**
   * @param list
   * @return a list of the history for an item...
   */
  public abstract ArrayList<TaskStatusUpdate> getHistoryForItem(Task list);

  /**
   * @param id the id of the task
   * @return the associated task  
   * 
   */
  public abstract Task getItem(Long id);
  
  /**
   * @return all items with no reminder 
   */
  public abstract ArrayList<Task> getTaskReminderNone();
  
  /**
   * @return all items with an email reminder
   */
  public abstract ArrayList<Task> getTaskReminderEmail();
  
  /**
   * @return all items with urgent reminder
   */
  public abstract ArrayList<Task> getTaskReminderUrget();  
  
  /**
   * Get all items that have a pointer to this task in the database
   * @param task the task to search
   * @return a list of tasks ordered by priority and then creation date
   */
  public abstract ArrayList<Task> getSubTasks(Task task);
  /**
   * @return all the users (ordered by name) that can be assigned tasks  
   */
  public abstract ArrayList<String> getAllUsers();
  
  /**
   * Removes a user from the database 
   * @param user the user to remove  
   * @param reassign the user to reassign tasks to (null deletes the tasks). 
   */
  public abstract void reassignTask(String user, String reassign);

}