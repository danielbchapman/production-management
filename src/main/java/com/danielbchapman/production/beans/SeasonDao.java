package com.danielbchapman.production.beans;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Season;

/**
 * A simple class for dealing with IO for productions.
 *
 */
@Stateless
public class SeasonDao implements SeasonDaoRemote
{
	private static final long serialVersionUID = 1L;
	//  @PersistenceContext
	EntityManager em = EntityInstance.getEm();
	
  public SeasonDao()
  {
    
  }
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#saveProduction(com.danielbchapman.production.entity.Production)
   */
	public void saveSeason(Season source)
	{
		EntityInstance.saveObject(source);
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProductions()
   */
	@SuppressWarnings("unchecked")
	public ArrayList<Season> getSeasons()
	{
		Query q = em.createQuery("SELECT s FROM Season s ORDER BY s.name");
		ArrayList<Season> ret = new ArrayList<Season>();
		List<Season> results = (List<Season>)q.getResultList();
		
		if(results != null)
			for(Season p : results)
				ret.add(p);
		
		return ret;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProductionCount()
   */
	public long getSeasonCount()
	{
		Query q = em.createNativeQuery("SELECT Count(*) FROM Season");
		Long i = (Long) q.getSingleResult();
		if(i == null)
			return -1;
		
		return i;
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProduction(java.lang.Long)
   */
	public Season getSeason(Long id)
	{
		return em.find(Season.class, id);
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProduction(java.lang.String)
   */
	public Season getSeason(String name)
	{
		Query q = em.createQuery("SELECT s FROM Season s WHERE s.name = ?!");
		q.setParameter(1, name);
		return (Season) q.getSingleResult();
	}
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#removeProduction(com.danielbchapman.production.entity.Production)
   */
	public void removeSeason(Season p)
	{
		EntityInstance.deleteObject(p);
	}
}
