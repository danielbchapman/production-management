package com.danielbchapman.production.beans;


import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Department;
import com.danielbchapman.production.entity.Production;
import com.danielbchapman.production.entity.Task;
import com.danielbchapman.production.entity.TaskStatusUpdate;
import com.danielbchapman.production.entity.TaskUser;

@Remote
public interface TaskDaoRemote
{

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
   * @return all the users (ordered by name) that can be assigned tasks  
   */
  public abstract ArrayList<TaskUser> getAllTaskUsers();
  
  /**
   * @param name the name of the individual
   * @param position the position of this individual 
   * 
   */
  public abstract void addTaskUser(String name, String position);
  
  /**
   * Removes a user from the database--this throws an exception if the user has 
   * @param user
   * @param user the user to remove  
   * @param reassign the user to reassign tasks to (null deletes the tasks). 
   */
  public abstract void removeTaskUser(TaskUser user, TaskUser reassign);

}