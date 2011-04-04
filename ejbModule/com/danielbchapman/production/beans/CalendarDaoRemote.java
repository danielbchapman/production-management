package com.danielbchapman.production.beans;


import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Day;
import com.danielbchapman.production.entity.Event;
import com.danielbchapman.production.entity.Production;
import com.danielbchapman.production.entity.Week;

/**
 * A simple remote interface defining the CalendarDao
 */
@Remote
public interface CalendarDaoRemote
{

  /**
   * @param id
   * @return a week by the ID
   */
  public abstract Week getWeek(Long id);

  /**
   * @param day the day to query
   * @return a list of events for this day (zero count for none)
   */
  public abstract ArrayList<Event> getEvents(Day day);

  /**
   * @param dayInWeek
   * @param production
   * @return a week by the date
   */
  public abstract Week getOrCreateWeek(Date dayInWeek, Production production);

  /**
   * Creates a Day object for a date and a week
   * @param date the Date of the Day
   * @param week the Week to connect to (production based)
   * @return a new Day object for the Date/Week
   * 
   */
  public abstract Day getOrCreateDay(Date date, Week week);
  /**
   * This call creates a Week if necessary and then creates a Day for the production
   * @param date the Date of the Day
   * @param production the Production to assign this Week/Day to
   * @return a Day created for the Production.   
   * 
   */
  public abstract Day getOrCreateDay(Date date, Production production);
  /**
   * @return a list of all weeks, ordered
   */
  public abstract ArrayList<Week> getAllWeeks(Production production);

  /**
   *  saves a week
   * @param source
   */
  public abstract void saveWeek(Week source);

  /**
   *  Saves a day
   * @param source
   */
  public abstract void saveDay(Day source);

  /**
   * Saves an event
   * @param source
   */
  public abstract void saveEvent(Event source);

  /**
   * Removes an entity from the persistence layer
   * @param obj any object
   */
  public abstract void removeItem(Object obj);
  
  /**
   * Return a list of all Days that have been created.
   */
  public abstract ArrayList<Day> getActiveDaysForWeek(Week week);
  
  /**
   * @param day the day to search
   * @return all the events for a specific day
   * 
   */
  public abstract ArrayList<Event> getEventsForDay(Day day);
  
  /**
   * 
   * @param date the date to search
   * @return true if it is in the database, false otherwise.
   * 
   */
  public boolean dayExists(Date date, Production production);
  
  /**
   * Patch the database replacing all null end times with a 2 hour difference 
   * for compatibility with the Primefaces schedule (better functionality)
   */
  public abstract void patchEventEndTimeTwoHours();

}