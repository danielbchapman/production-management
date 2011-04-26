package org.theactingcompany.inventory.beans;

import java.util.ArrayList;
import java.util.HashSet;

import javax.ejb.Remote;

import org.theactingcompany.inventory.entity.InventoryElement;
import org.theactingcompany.inventory.entity.InventoryProblem;
import org.theactingcompany.inventory.entity.WardrobeElement;

/**
 * A bean that uses a class pattern to determine information and 
 * allows retrieval and saving of elements. It is a completely general
 * bean that allows generic deployment.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Apr 13, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface InventoryDaoRemote
{
  /**
   * Return a specific element based on an ID and a class
   * @param id the element to search for
   * @param clazz the element class (i.e. PropsElement.class
   * @return the element with the specified ID, null if not found
   * 
   */
  public InventoryElement getElement(Long id, Class<? extends InventoryElement> clazz);
  
  /**
   * Removes an element from the database
   * @param element the element to remove
   * 
   */
  public void setInactive(InventoryElement element);
  
  /**
   * Set an item back to active status (undelete)
   * @param element the element to activate  
   * 
   */
  public void reactivate(InventoryElement element);

  /**
   * Deactivate an element (delete with option to undelete)
   * @param element the element to deactivate
   * 
   */
  public void deactivate(InventoryElement element);
  
  /**
   * Purge the object from the system (administrator only)
   * @param element The element to delete
   * 
   */
  public void deleteElement(InventoryElement element);
  /**
   * @param clazz the class to list
   * @return a sorted list (by type/description) for the entire inventory
   * section.  
   * 
   */
  public ArrayList<? extends InventoryElement> getAllElements(Class<? extends InventoryElement> clazz);
  
  /**
   * @param clazz the class to list
   * @return a sorted list (by type/description) for the entire inventory
   * section.  
   * 
   */
  public ArrayList<? extends InventoryElement> getAllInactiveElements(Class<? extends InventoryElement> clazz);
  /**
   * Save an element to the database
   * @param element the element to save  
   * 
   */
  public void saveElement(InventoryElement element);
  
  /**
   * Report a problem with a specific element
   * @param element
   * @param problem <Return Description>  
   * 
   */
  public void reportElementProblem(InventoryElement element, InventoryProblem problem);
  
  /**
   * Marks a problem as resolved, these can then be looked at and modified.
   * @param problem the problem to mark as resolved
   * 
   */
  public void resolveProblem(InventoryProblem problem);
  
  /**
   * @return a list of all inventory problems  
   * 
   */
  public ArrayList<InventoryProblem> getAllProblems();
  
  /**
   * Return the selected problem
   * @param id the id of the problem
   * @return The reference to the problem.
   * 
   */
  public InventoryProblem getProblem(Long id);
  /**
   * 
   * @param clazz the entity class to search for
   * @return a list of problems for the class (size 0 for none)
   * 
   */
  public ArrayList<? extends InventoryProblem> getProblemsForClass(Class<? extends InventoryProblem> clazz);
  
  /**
   * Searches the table for the keywords. 
   * @param searchString the key word/words to search for
   * @param clazz the InventoryElement to search for
   * @return a list of results ordered by relevance, type, description of unique results
   * 
   */
  public ArrayList<? extends InventoryElement> searchElement(String[] searchString, Class<? extends InventoryElement> clazz);
  
  /**
   * A convenience method for single strings
   * @see InventoryDaoRemote#searchElement(String[], Class)  
   * @param searchString the key word/words to search for
   * @param clazz the InventoryElement to search for
   * @return a list of results ordered by relevance, type, description of unique results  
   * 
   */
  public ArrayList<? extends InventoryElement> searchElement(String searchString, Class<? extends InventoryElement> clazz);
  
  /** 
   * Suggest possible matches for whatever the user types.
   * @param clazz the element to search for
   * @param value the string to search for
   * @return a list of productions that are stored in these elements  
   */
  public HashSet<String> suggestProduction(Class<? extends InventoryElement> clazz, String value);
  
  /**
   * Suggest possible matches for whatever the user types against period (Wardrobe only)
   * @param clazz the class of the element to search for (Valid only for Wardrobe right now, this is a type lock)
   * @param value the value to compare
   * @return a list of possible matches 
   * 
   */
  public HashSet<String> suggestPeriod(Class<WardrobeElement> clazz, String value);
  
  /**
   * Suggest possible places of storage for this item (localized to the class)
   * @param clazz the class of the element to search for 
   * @param value the value to compare
   * @return a list of possible matches  
   * 
   */
  
  public HashSet<String> suggestLocation(Class<? extends InventoryElement> clazz, String value);
  
  /**
   * Suggest possible condition descriptions for this class (helps with searches)
   * @param clazz the class of the element to search for 
   * @param value the value to compare
   * @return a list of possible matches 
   * 
   */
  
  public HashSet<String> suggestCondition(Class<? extends InventoryElement> clazz, String value);
  
  /**
   * Suggest Possible types for this class (helps with searches)
   * @param clazz the class of the element to search for 
   * @param value the value to compare
   * @return a list of possible matches 
   * 
   */
  public HashSet<String> suggestType(Class<? extends InventoryElement> clazz, String value);
  
  /**
   * Assigns a unique code to this element (across all departments)
   * @param element The InventoryElement to attach a bar code to  
   * 
   */
  public void assignBarCodeToEntity(InventoryElement element);
  
  /**
   * Initialize the tables on the native database to support searching.  
   */
  public void initializeFullTextSearches();
}
