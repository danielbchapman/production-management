package com.danielbchapman.production.beans;


import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Season;
@Remote
public interface SeasonDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

  /**
   * @param source Saves a season ^^
   */
  public abstract void saveSeason(Season source);

  /**
   * @return a list of all the active seasons
   */
  public abstract ArrayList<Season> getSeasons();

  /**
   * @return a count of the seasons
   */
  public abstract long getSeasonCount();

  /**
   * @param id the unique ID to look for
   * @return a single season by id
   */
  public abstract Season getSeason(Long id);

  /**
   * @param name the season name
   * @return a single season by name
   */
  public abstract Season getSeason(String name);

  /**
   * Remove a season from the database
   * @param p
   */
  public abstract void removeSeason(Season p);

}