package com.danielbchapman.production.beans;

import java.io.Serializable;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.calendar.CalendarEvent;
import com.danielbchapman.production.entity.calendar.Equipment;
import com.danielbchapman.production.entity.calendar.Facility;

@Remote
public interface ProductionCalendarDaoRemote extends Serializable
{
  /**
   * Removes an event from the database.
   * @param event the event to remove
   */
  public abstract void delete(CalendarEvent event);
  /**
   * Removes a Facility from the database.
   * @param facility the facility to remove
   */
  public abstract void delete(Facility facility);
  /**
   * Removes a piece of equipment from the database.
   * @param equipment the equipment to remove
   */
  public abstract void delete(Equipment equipment);
  
  /**
   * Adds an event to the calendar
   * @param event
   * @return a reference to that event with the ID updated 
   */
  public abstract CalendarEvent save(CalendarEvent event);
  
  /**
   * Saves a facility to the database
   * @param facility
   * @return a reference to the facility
   */
  public abstract Facility save(Facility facility);
  
  /**
   * Saves a piece of equipment to the database.
   * @param equipment
   * @return a reference to the event  
   * 
   */
  public abstract Equipment save(Equipment equipment);
  
}
