package com.danielbchapman.production.beans;


import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Production;
import com.danielbchapman.production.entity.TodoList;
import com.danielbchapman.production.entity.TodoStatusUpdate;

@Remote
public interface TodoDaoRemote
{

  /**
   * Saves or updates an item.
   * @param listItem
   */
  public abstract void saveItem(TodoList listItem);

  public abstract void saveStatusUpdate(TodoList listItem, String notes);

  /**
   * @return a list of all items
   */
  public abstract ArrayList<TodoList> getItems(Production production);

  /**
   * @return a list of all items
   */
  public abstract ArrayList<TodoList> getActiveItems(Production production);

  /**
   * @return a list of all items
   */
  public abstract ArrayList<TodoList> getClosedItems(Production production);

  /**
   * @param list
   * @return a list of the history for an item...
   */
  public abstract ArrayList<TodoStatusUpdate> getHistoryForItem(TodoList list);

  public abstract TodoList getItem(Long id);

}