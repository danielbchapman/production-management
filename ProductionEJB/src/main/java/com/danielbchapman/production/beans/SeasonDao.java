package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.ejb.Stateless;

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
//	EntityManager em = EntityInstance.getEm();
	
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
	public ArrayList<Season> getSeasons()
	{
		return EntityInstance.getResultList("SELECT s FROM Season s ORDER BY s.name", Season.class);		
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProductionCount()
   */
	public long getSeasonCount()
	{
		return getSeasons().size();
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProduction(java.lang.Long)
   */
	public Season getSeason(Long id)
	{
		return EntityInstance.find(Season.class, id);
	}
	
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#getProduction(java.lang.String)
   */
	public Season getSeason(String name)
	{
		return EntityInstance.getSingleResult("SELECT s FROM Season s WHERE s.name = ?1", Season.class, name);
	}
	/* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionDaoRemote#removeProduction(com.danielbchapman.production.entity.Production)
   */
	public void removeSeason(Season p)
	{
		EntityInstance.deleteObject(p);
	}
}
