package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.Production;

/**
 * A simple class for dealing with IO for productions.
 *
 */
@Stateless
public class ProductionDao implements ProductionDaoRemote
{
//  @PersistenceContext
	EntityManager em = EntityInstance.getEm();
	
  public ProductionDao()
  {
    
  }
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#saveProduction(com.danielbchapman.production.entity.Production)
   */
	public void saveProduction(Production source)
	{
		if(source.getId() != null)
			em.merge(source);
		else
			em.persist(source);
		
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProductions()
   */
	@SuppressWarnings("unchecked")
	public ArrayList<Production> getProductions()
	{
		Query q = em.createQuery("SELECT p FROM Production p ORDER BY p.name");
		ArrayList<Production> ret = new ArrayList<Production>();
		List<Production> results = (List<Production>)q.getResultList();
		
		if(results != null)
			for(Production p : results)
				ret.add(p);
		
		return ret;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProductionCount()
   */
	public long getProductionCount()
	{
		Query q = em.createNativeQuery("SELECT Count(*) FROM Production");
		Long i = (Long) q.getSingleResult();
		if(i == null)
			return -1;
		
		return i;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProduction(java.lang.Long)
   */
	public Production getProduction(Long id)
	{
		return em.find(Production.class, id);
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProduction(java.lang.String)
   */
	public Production getProduction(String name)
	{
		Query q = em.createQuery("SELECT p FROM Production p WHERE p.name = ?!");
		q.setParameter(1, name);
		return (Production) q.getSingleResult();
	}
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#removeProduction(com.danielbchapman.production.entity.Production)
   */
	public void removeProduction(Production p)
	{
	  if(!Config.CONTAINER_MANAGED)
	    em.getTransaction().begin();
	  
		if(p != null)
			em.remove(p);
		
		if(!Config.CONTAINER_MANAGED)
		  em.getTransaction().commit();
	}
}
