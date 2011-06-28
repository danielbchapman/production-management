package com.danielbchapman.production.beans;


import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Day;
import com.danielbchapman.production.entity.Event;
import com.danielbchapman.production.entity.EventMapping;
import com.danielbchapman.production.entity.Performance;
import com.danielbchapman.production.entity.PerformanceAdvance;
import com.danielbchapman.production.entity.PerformanceSchedule;
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
   * @param day the day to query
   * @return a list of all the performances for that day  
   * 
   */
  public abstract ArrayList<Performance> getPerformances(Day day);
  
  /**
   * @param p the production to look for
   * @return a list of all performances for this production
   */
  public abstract ArrayList<Performance> getPerformances(Production p);

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
  public abstract void saveEvent(EventMapping source);

  /**
   * Saves a performance
   * @param source  
   * 
   */
  public abstract void savePerformance(Performance source);
  
  
  /**
   * Save or update a performance schedule in the database.
   * @param schedule the schedule to save or update 
   * 
   */
  public abstract void savePerformanceSchedule(PerformanceSchedule schedule);
  /**
   * @param id the ID to look for
   * @return the performance schedule associated with this ID  
   * 
   */
  public abstract PerformanceSchedule getPerformanceSchedule(Long id);
  /**
   * @return a list of known performance schedules  
   * 
   */
  public abstract ArrayList<PerformanceSchedule> getAllPerformanceSchedules();
  
  /**
   * @param production the production to filter by
   * @return a list of known performance schedules for the selected production  
   * 
   */
  public abstract ArrayList<PerformanceSchedule> getPerformanceSchedulesForProduction(Production production);
  
  
  /**
   * Saves or updates a PerformanceAdvance.
   * @param advance the advance to save or update  
   * 
   */
  public abstract void savePerformanceAdvance(PerformanceAdvance advance);
  /**
   * @param id the id of the entity
   * @return the PerformanceAdvance specified by this ID
   * 
   */
  public abstract PerformanceAdvance getPerformanceAdvance(Long id);
  /**
   * 
   * @param d the day to look for
   * @return a list of all PerformanceAdvance objects bound to this day  
   * 
   */
  public abstract ArrayList<PerformanceAdvance> getPerformanceAdvance(Day d);
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
   * @param day the day to search
   * @return all the events and performances for a specific day
   * 
   */
  public abstract ArrayList<EventMapping> getEventsAndPerformancesForDay(Day day);  
  
  /**
   * 
   * @param date the date to search
   * @return true if it is in the database, false otherwise.
   * 
   */
  public boolean dayExists(Date date, Production production);

}