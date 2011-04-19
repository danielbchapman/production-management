package org.theactingcompany.inventory.beans;

import java.util.ArrayList;

import javax.ejb.Remote;

import org.theactingcompany.inventory.entity.InventoryElement;
import org.theactingcompany.inventory.entity.InventoryProblem;

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
  public void removeElement(InventoryElement element);
  
  /**
   * @param clazz the class to list
   * @return a sorted list (by type/description) for the entire inventory
   * section.  
   * 
   */
  public ArrayList<? extends InventoryElement> getAllElements(Class<? extends InventoryElement> clazz);
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
}
