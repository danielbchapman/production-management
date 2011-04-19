package org.theactingcompany.inventory.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.theactingcompany.inventory.entity.InventoryElement;
import org.theactingcompany.inventory.entity.InventoryProblem;

/**
 * Session Bean implementation class InventoryBean
 */
@Stateless
public class InventoryDao implements InventoryDaoRemote
{
  EntityManager em = EntityInstance.getEm();
  /**
   * Default constructor.
   */
  public InventoryDao()
  {
  }
  
  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryBeanRemote#getElement(java.lang.Long, java.lang.Class)
   */
  @Override
  public InventoryElement getElement(Long id, Class<? extends InventoryElement> clazz)
  {
    return em.find(clazz, id);
  }
  
  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryBeanRemote#removeElement(org.theactingcompany.inventory.entity.InventoryElement)
   */
  @Override
  public void removeElement(InventoryElement element)
  {
    EntityInstance.deleteObject(element);
  }
  
  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryBeanRemote#getAllElements(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<InventoryElement> getAllElements(Class<? extends InventoryElement> clazz)
  {
    ArrayList<InventoryElement> ret = new ArrayList<InventoryElement>();
    InventoryElement instance = null;
    try
    {
      instance = clazz.newInstance();
    }
    catch (InstantiationException e)
    {
      throw new RuntimeException("Could not instantiate class: " + clazz, e);
    }
    catch (IllegalAccessException e)
    {
      throw new RuntimeException("Could not instantiate class: " + clazz, e);
    }
    
    Query q = em.createQuery("SELECT i FROM " + instance.getTableName() + " i ORDER BY i.type, i.description");
    List<InventoryElement> results = (List<InventoryElement>)q.getResultList();
    
    if(results != null)
      for(InventoryElement e : results)
        ret.add(e);
    
    return ret;
  }
  
  @Override
  public void saveElement(InventoryElement element)
  {
    EntityInstance.saveObject(element);
  }
  
  @Override
  public void reportElementProblem(InventoryElement element, InventoryProblem problem)
  {
    //FIXME Implement
    throw new RuntimeException("NOT IMPLEMENTED...");
    
  }
  
  @Override
  public void resolveProblem(InventoryProblem problem)
  {
    //FIXME Implement
    throw new RuntimeException("NOT IMPLEMENTED...");
    
  }
  
  @Override
  public ArrayList<InventoryProblem> getAllProblems()
  {
    //FIXME Implement
    throw new RuntimeException("NOT IMPLEMENTED...");
  }
  
  @Override
  public ArrayList<InventoryProblem> getProblemsForClass(Class<? extends InventoryProblem> clazz)
  {
    //FIXME Implement
    throw new RuntimeException("NOT IMPLEMENTED...");
  }
  
  @Override
  public ArrayList<InventoryElement> searchElement(String[] searchString, Class<? extends InventoryElement> clazz)
  {
    //FIXME Implement
    throw new RuntimeException("NOT IMPLEMENTED...");
  }
  
  @Override
  public ArrayList<InventoryElement> searchElement(String searchString, Class<? extends InventoryElement> clazz)
  {
    //FIXME Implement
    throw new RuntimeException("NOT IMPLEMENTED...");
  }

}
