package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Show;

@Remote
public interface ShowDaoRemote extends Serializable
{
  
  /**
   * Save or update a show in the database.
   * @param show the show to save
   * @return A copy of the entity with the updated ID.  
   * 
   */
  public Show save(Show show);
  
  /**
   * Remove a show from the database. This will cascade and destroy all events, budgets, etc...
   * @param show the show to remove.  
   * 
   */
  public void delete(Show show);
  
  /**
   * Returns a list of all shows for a particular season.
   * @param season the season to search
   * @return A list of the shows  
   * 
   */
  public ArrayList<Show> getShows(Season season);
}
