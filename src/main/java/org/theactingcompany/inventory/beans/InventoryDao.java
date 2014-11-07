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
import org.theactingcompany.inventory.entity.InventoryImage;
import org.theactingcompany.inventory.entity.InventoryProblem;
import org.theactingcompany.inventory.entity.WardrobeElement;

import com.danielbchapman.production.entity.EntityInstance;

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
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#deactivate(org.theactingcompany.inventory.entity.InventoryElement)
   */
  @Override
  public void deactivate(InventoryElement element)
  {
    element.setInactive(true);
    EntityInstance.saveObject(element);
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#deleteElement(org.theactingcompany.inventory.entity.InventoryElement)
   */
  @Override
  public void deleteElement(InventoryElement element)
  {
    EntityInstance.deleteObject(element);
  }

  @Override
	public void deleteInventoryImage(InventoryImage id)
	{
		EntityInstance.deleteObject(id);
		
	}

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryBeanRemote#getAllElements(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<InventoryElement> getAllElements(Class<? extends InventoryElement> clazz)
  {
    ArrayList<InventoryElement> ret = new ArrayList<InventoryElement>();

    Query q = em.createQuery("SELECT i FROM " + clazz.getSimpleName() + " i WHERE (i.inactive IS NULL OR i.inactive = ?1) ORDER BY i.production, i.type, i.description");
    q.setParameter(1, Boolean.FALSE);
    List<InventoryElement> results = (List<InventoryElement>)q.getResultList();

    if(results != null)
      for(InventoryElement e : results)
        ret.add(e);

    return ret;
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#getAllInactiveElements(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<? extends InventoryElement> getAllInactiveElements(Class<? extends InventoryElement> clazz)
  {
    ArrayList<InventoryElement> ret = new ArrayList<InventoryElement>();

    Query q = em.createQuery("SELECT i FROM " + clazz.getSimpleName() + " i WHERE i.inactive = true ORDER BY i.production, i.type, i.description");
    List<InventoryElement> results = (List<InventoryElement>)q.getResultList();

    if(results != null)
      for(InventoryElement e : results)
        ret.add(e);

    return ret;
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#getAllProblems()
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<InventoryProblem> getAllProblems()
  {
    Query q = em.createQuery("SELECT p FROM InventoryProblem p WHERE p.resolved = ?1");
    q.setParameter(1, false);
    
    List<InventoryProblem> problems = (List<InventoryProblem>)q.getResultList();
    ArrayList<InventoryProblem> ret = new ArrayList<InventoryProblem>();
    if(problems != null)
      for(InventoryProblem p : problems)
        ret.add(p);
    
    return ret;
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryBeanRemote#getElement(java.lang.Long, java.lang.Class)
   */
  @Override
  public InventoryElement getElement(Long id, Class<? extends InventoryElement> clazz)
  {
    return em.find(clazz, id);
  }

  @Override
	public InventoryImage getInventoryImage(Long id)
	{
		return em.find(InventoryImage.class, id);
		
	}

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#getProblem(java.lang.Long)
   */
  @Override
  public InventoryProblem getProblem(Long id)
  {
    return em.find(InventoryProblem.class, id);
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#getProblemsForClass(java.lang.Class)
   */
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<InventoryProblem> getProblemsForClass(Class<? extends InventoryProblem> clazz)
  {
    Query q = em.createQuery("SELECT p FROM InventoryProblem p WHERE p.resolved = ?1 AND p.entityClass = ?2");
    q.setParameter(1, false);
    q.setParameter(2, clazz.getName());
    
    List<InventoryProblem> problems = (List<InventoryProblem>)q.getResultList();
    ArrayList<InventoryProblem> ret = new ArrayList<InventoryProblem>();
    
    if(problems != null)
      for(InventoryProblem p : problems)
        ret.add(p);
    
    return ret;
  }

  @Override
  public void initializeFullTextSearches()
  {
    em.getTransaction().begin();
    try
    {//FIXME -> This never works: fix it
    	/*
CREATE ALIAS IF NOT EXISTS FT_INIT FOR "org.h2.fulltext.FullText.init";
CALL FT_DROP_ALL();
CALL FT_INIT();
CALL FT_CREATE_INDEX('PUBLIC', 'WARDROBEELEMENT', NULL);
CALL FT_CREATE_INDEX('PUBLIC', 'STAGEMANAGEMENTELEMENT', NULL);
CALL FT_CREATE_INDEX('PUBLIC', 'LIGHTINGELEMENT', NULL);
CALL FT_CREATE_INDEX('PUBLIC', 'GENERALELEMENT', NULL);
CALL FT_CREATE_INDEX('PUBLIC', 'SOUNDELEMENT', NULL);
CALL FT_CREATE_INDEX('PUBLIC', 'PROPSELEMENT', NULL);
CALL FT_CREATE_INDEX('PUBLIC', 'SCENICELEMENT', NULL);
    	 */
    	//This is by column
//      String createIndex = 
//        "//CALL FT_DROP_ALL();" + "\r\n" +
//        "CREATE ALIAS IF NOT EXISTS FT_INIT FOR \"org.h2.fulltext.FullText.init\";" + "\r\n" +
//        "CALL FT_INIT();" + "\r\n" +
//        "CALL FT_CREATE_INDEX('PUBLIC', 'WARDROBEELEMENT', 'ID, BARCODE_ID, SEX, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, PERIOD, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');"+ "\r\n" +
//        "CALL FT_CREATE_INDEX('PUBLIC', 'STAGEMANAGEMENTELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');"+ "\r\n" +
//        "CALL FT_CREATE_INDEX('PUBLIC', 'LIGHTINGELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n" +
//        "CALL FT_CREATE_INDEX('PUBLIC', 'GENERALELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n" +
//        "CALL FT_CREATE_INDEX('PUBLIC', 'SOUNDELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n" +
//        "CALL FT_CREATE_INDEX('PUBLIC', 'PROPSELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n" +
//        "CALL FT_CREATE_INDEX('PUBLIC', 'SCENICELEMENT', 'ID, BARCODE_ID, WEIGHT, CONDITION, LOCATION, COLORS, USERNOTES, TYPE, PRODUCTION, SERIALNUMBERORID, DESCRIPTION, NOTES, FILENAME, MIMETYPE');" + "\r\n"
//        ;
    	//These tables have no Blobs so they can be "Blanket" indexed
      String createIndex = 
      	"CREATE ALIAS IF NOT EXISTS FT_INIT FOR \"org.h2.fulltext.FullText.init\";" + "\r\n" +
      	"CALL FT_INIT();" + "\r\n" +
      	"CALL FT_DROP_ALL();" + "\r\n" +
      	"CALL FT_INIT();" + "\r\n" +
      	"CALL FT_CREATE_INDEX('PUBLIC', 'WARDROBEELEMENT', NULL);" + "\r\n" +
      	"CALL FT_CREATE_INDEX('PUBLIC', 'STAGEMANAGEMENTELEMENT', NULL);" + "\r\n" +
      	"CALL FT_CREATE_INDEX('PUBLIC', 'LIGHTINGELEMENT', NULL);" + "\r\n" +
      	"CALL FT_CREATE_INDEX('PUBLIC', 'GENERALELEMENT', NULL);" + "\r\n" +
      	"CALL FT_CREATE_INDEX('PUBLIC', 'SOUNDELEMENT', NULL);" + "\r\n" +
      	"CALL FT_CREATE_INDEX('PUBLIC', 'PROPSELEMENT', NULL);" + "\r\n" +
      	"CALL FT_CREATE_INDEX('PUBLIC', 'SCENICELEMENT', NULL);" + "\r\n";

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

  @Override
  public void reactivate(InventoryElement element)
  {
    element.setInactive(false);
    EntityInstance.saveObject(element);
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#reportElementProblem(org.theactingcompany.inventory.entity.InventoryElement, org.theactingcompany.inventory.entity.InventoryProblem)
   */
  @Override
  public void reportElementProblem(InventoryElement element, InventoryProblem problem)
  {
    problem.setEntityClass(element.getClass().getName());
    problem.setEntityId(element.getId());
    problem.setResolved(false);
    
    EntityInstance.saveObject(problem);
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#resolveProblem(org.theactingcompany.inventory.entity.InventoryProblem)
   */
  @Override
  public void resolveProblem(InventoryProblem problem)
  {
    problem.setResolved(true);
    EntityInstance.saveObject(problem);
  }

  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#saveElement(org.theactingcompany.inventory.entity.InventoryElement)
   */
  @Override
  public void saveElement(InventoryElement element)
  {
  	if(element.getBarCode() == null)
  	{
      BarCode code = new BarCode();
      element.setBarCode(code);
      EntityInstance.saveObject(code);  		
  	}

    if(element.getLocalImage() != null && element.getImage() == null)
    {
    	InventoryImage image = new InventoryImage();
    	image.setImage(element.getLocalImage());
    	EntityInstance.saveObject(image);
    	element.setImage(image.getId());
    }
    EntityInstance.saveObject(element);
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
    {
      InventoryElement el = getElement(rank.element, clazz);
      if(el != null && !(el.getInactive() == null? false : el.getInactive()));
        ret.add(el);
    }
      
    return ret;
  }
  
  /* (non-Javadoc)
   * @see org.theactingcompany.inventory.beans.InventoryBeanRemote#removeElement(org.theactingcompany.inventory.entity.InventoryElement)
   */
  @Override
  public void setInactive(InventoryElement element)
  {
    element.setInactive(true);
    EntityInstance.saveObject(element);
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

	@SuppressWarnings("rawtypes")
  public class RankedElement implements Comparable
  {
    Integer count;
    Long element;
    
    RankedElement(Long element, Integer count)
    {
      this.element = element;
      this.count = count;
    }
    
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
    
  }

}
