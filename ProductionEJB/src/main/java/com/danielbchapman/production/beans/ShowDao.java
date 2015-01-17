package com.danielbchapman.production.beans;

import java.util.ArrayList;

import javax.ejb.Stateless;

import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Show;

@Stateless
public class ShowDao implements ShowDaoRemote
{
  private static final long serialVersionUID = 1L;

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ShowDaoRemote#save(com.danielbchapman.production.entity.Show)
   */
  @Override
  public Show save(Show show)
  {
    return EntityInstance.saveObject(show);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ShowDaoRemote#delete(com.danielbchapman.production.entity.Show)
   */
  @Override
  public void delete(Show show)
  {
    //FIXME Implement this to cascade and update the values
    throw new RuntimeException("This is a complicated operation and needs implementation");
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ShowDaoRemote#getShows(com.danielbchapman.production.entity.Season)
   */
  @Override
  public ArrayList<Show> getShows(Season season)
  {
    if(season == null)
      return new ArrayList<>();
    
    return EntityInstance.getResultList("SELECT s FROM Show s WHERE s.season = ?1 ORDER BY s.name", Show.class);
  }
}
