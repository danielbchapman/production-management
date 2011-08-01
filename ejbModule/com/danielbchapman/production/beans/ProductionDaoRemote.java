package com.danielbchapman.production.beans;


import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Production;
@Remote
public interface ProductionDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

  /**
   * @param source Saves a production ^^
   */
  public abstract void saveProduction(Production source);

  /**
   * @return a list of all the active productions
   */
  public abstract ArrayList<Production> getProductions();

  /**
   * @return a count of the productions
   */
  public abstract long getProductionCount();

  /**
   * @param id the unique ID to look for
   * @return a single production by id
   */
  public abstract Production getProduction(Long id);

  /**
   * @param name the production name
   * @return a single production by name
   */
  public abstract Production getProduction(String name);

  /**
   * Remove a production from the database
   * @param p
   */
  public abstract void removeProduction(Production p);

}