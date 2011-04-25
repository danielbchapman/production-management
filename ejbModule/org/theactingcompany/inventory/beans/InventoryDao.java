package org.theactingcompany.inventory.beans;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.theactingcompany.inventory.entity.BarCode;
import org.theactingcompany.inventory.entity.InventoryElement;
import org.theactingcompany.inventory.entity.InventoryProblem;
import org.theactingcompany.inventory.entity.WardrobeElement;

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

    Query q = em.createQuery("SELECT i FROM " + clazz.getSimpleName() + " i ORDER BY i.production, i.type, i.description");
    List<InventoryElement> results = (List<InventoryElement>)q.getResultList();

    if(results != null)
      for(InventoryElement e : results)
        ret.add(e);

    return ret;
  }

  @Override
  public void saveElement(InventoryElement element)
  {
    if(element.getBarCode() == null)
      assignBarCodeToEntity(element);
    else
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

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#searchElement(java.lang.String[], java.lang.Class)
   */
  //FIXME -> this should be done via the query. It might be gross, but it will be faster than this COMPLEXITY!!!  
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<InventoryElement> searchElement(String[] searchString, Class<? extends InventoryElement> clazz)
  {
    if(searchString.length == 0)
      return new ArrayList<InventoryElement>();

    StringBuilder query = new StringBuilder();
    
    String tableName = clazz.getSimpleName().toUpperCase();
    for(int i = 0; i < searchString.length; i++)
    {
      query.append("SELECT\n\t element.id\n FROM\n\t FT_SEARCH_DATA(?");
      query.append(i + 1);
      query.append(", 0, 0) ftsearch \n\tINNER JOIN ");
      query.append(tableName);
      query.append(" element\n\t\t ON \n\t\t ftsearch.KEYS[0] = element.ID");
      if(i+1 < searchString.length)
        query.append("\nUNION ALL\n");
    }
    
    Query q = em.createNativeQuery(query.toString());
    
    for(int i = 0; i < searchString.length; i++)
      q.setParameter(i + 1, searchString[i]);
    
    List<Long> results = (List<Long>) q.getResultList();
    HashMap<Long, Integer> ranks = new HashMap<Long, Integer>();
      
    if(results != null)
      for(Long element : results)
      { 
        if(ranks.containsKey(element))
        {
          ranks.put(element, ranks.get(element) -1);
        }
        else
          ranks.put(element, 0);
      }
    
    ArrayList<RankedElement> sort = new ArrayList<RankedElement>();
    ArrayList<InventoryElement> ret = new ArrayList<InventoryElement>();
    
    for(Long element : ranks.keySet())
      sort.add(new RankedElement(element, ranks.get(element)));
    
    Collections.sort(sort);
    
    for(RankedElement rank : sort)
      ret.add(getElement(rank.element, clazz));
    
    return ret;
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#searchElement(java.lang.String, java.lang.Class)
   * 
   */
  @Override
  public ArrayList<InventoryElement> searchElement(String searchString, Class<? extends InventoryElement> clazz)
  {
    return searchElement(new String[]{searchString}, clazz); 
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestProduction(java.lang.Class, java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public HashSet<String> suggestProduction(Class<? extends InventoryElement> clazz, String value)
  {
    value = escape(value);
    Query q = em.createNativeQuery("SELECT production FROM " + clazz.getSimpleName() + " WHERE production LIKE '%" + value + "%'");
    List<String> values = (List<String>)q.getResultList();
    HashSet<String> ret = new HashSet<String>();

    if(values != null)
      for(String s : values)
        ret.add(s);

    return ret;
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestPeriod(java.lang.Class, java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public HashSet<String> suggestPeriod(Class<WardrobeElement> clazz, String value)
  {
    value = value.replaceAll("'", "");
    Query q = em.createNativeQuery("SELECT period FROM " + clazz.getSimpleName() + " WHERE period LIKE '%" + value + "%'");
    List<String> values = (List<String>)q.getResultList();
    HashSet<String> ret = new HashSet<String>();

    if(values != null)
      for(String s : values)
        ret.add(s);

    return ret;
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestLocation(java.lang.Class, java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public HashSet<String> suggestLocation(Class<? extends InventoryElement> clazz, String value)
  {
    value = escape(value);
    Query q = em.createNativeQuery("SELECT location FROM " + clazz.getSimpleName() + " WHERE location LIKE '%" + value + "%'");
    List<String> values = (List<String>)q.getResultList();
    HashSet<String> ret = new HashSet<String>();

    if(values != null)
      for(String s : values)
        ret.add(s);

    return ret;
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestCondition(java.lang.Class, java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public HashSet<String> suggestCondition(Class<? extends InventoryElement> clazz, String value)
  {
    value = escape(value);
    Query q = em.createNativeQuery("SELECT condition FROM " + clazz.getSimpleName() + " WHERE condition LIKE '%" + value + "%'");
    List<String> values = (List<String>)q.getResultList();
    HashSet<String> ret = new HashSet<String>();

    if(values != null)
      for(String s : values)
        ret.add(s);

    return ret;
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestType(java.lang.Class, java.lang.String)
   */
  @SuppressWarnings("unchecked")
  @Override
  public HashSet<String> suggestType(Class<? extends InventoryElement> clazz, String value)
  {
    value = escape(value);
    Query q = em.createNativeQuery("SELECT type FROM " + clazz.getSimpleName() + " WHERE type LIKE '%" + value + "%'");
    List<String> values = (List<String>)q.getResultList();
    HashSet<String> ret = new HashSet<String>();

    if(values != null)
      for(String s : values)
        ret.add(s);

    return ret;
  }

  private String escape(String input)
  {
    if(input == null)
      return null;
    return input
    .replaceAll(" ", "%")
    .replaceAll("'", "")
    .replaceAll(",", "")
    .replaceAll(";", "")
    .replaceAll(".", "")
    .replaceAll("\"", "");
  }

  @Override
  public void assignBarCodeToEntity(InventoryElement element)
  {
    BarCode code = new BarCode();
    element.setBarCode(code);
    EntityInstance.saveObject(code);
    EntityInstance.saveObject(element);
  }

  @Override
  public void initializeFullTextSearches()
  {
    em.getTransaction().begin();
    try
    {
      String createIndex = 
        "CALL FT_DROP_ALL();" + "\r\n" +
        "CREATE ALIAS IF NOT EXISTS FT_INIT FOR \"org.h2.fulltext.FullText.init\";" + "\r\n" +
        "CALL FT_INIT();" + "\r\n" +
        "CALL FT_CREATE_INDEX('PUBLIC', 'WARDROBEELEMENT', 'ID, BARCODE_ID, SEX, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, PERIOD, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');"+ "\r\n" +
        "CALL FT_CREATE_INDEX('PUBLIC', 'STAGEMANAGEMENTELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');"+ "\r\n" +
        "CALL FT_CREATE_INDEX('PUBLIC', 'LIGHTINGELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n" +
        "CALL FT_CREATE_INDEX('PUBLIC', 'GENERALELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n" +
        "CALL FT_CREATE_INDEX('PUBLIC', 'SOUNDELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n" +
        "CALL FT_CREATE_INDEX('PUBLIC', 'PROPSELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n" +
        "CALL FT_CREATE_INDEX('PUBLIC', 'SCENICELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n"
        ;

      em.createNativeQuery(createIndex).executeUpdate();
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }
    finally
    {
      em.getTransaction().commit();
    }
  }
  
  @SuppressWarnings("rawtypes")
  public class RankedElement implements Comparable
  {
    Long element;
    Integer count;
    
    @Override
    public int compareTo(Object obj)
    {
      if(obj == null)
        return 1;
      
      RankedElement rank = (RankedElement)obj;
      
      if(rank.count == count)
        return 0;
      
      if(rank.count > count)
        return -1;
      
      return 1;
    }
    
    RankedElement(Long element, Integer count)
    {
      this.element = element;
      this.count = count;
    }
    
  }
}
