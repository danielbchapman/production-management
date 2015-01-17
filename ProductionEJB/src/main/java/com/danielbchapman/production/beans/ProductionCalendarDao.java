package com.danielbchapman.production.beans;

import javax.ejb.Stateless;

import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.calendar.CalendarEvent;
import com.danielbchapman.production.entity.calendar.Equipment;
import com.danielbchapman.production.entity.calendar.Facility;

@Stateless
public class ProductionCalendarDao implements ProductionCalendarDaoRemote
{
  private static final long serialVersionUID = 1L;

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionCalendarDaoRemote#delete(com.danielbchapman.production.entity.calendar.CalendarEvent)
   */
  @Override
  public void delete(CalendarEvent event)
  {
      EntityInstance.deleteObject(event);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionCalendarDaoRemote#delete(com.danielbchapman.production.entity.calendar.Facility)
   */
  @Override
  public void delete(Facility facility)
  {
    //FIXME Implement this to cascade and update the values
    throw new RuntimeException("This feature is not available as it is in progress and compilcated updates are required");
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionCalendarDaoRemote#delete(com.danielbchapman.production.entity.calendar.Equipment)
   */
  @Override
  public void delete(Equipment equipment)
  {
    //FIXME Implement this to cascade and update the values
    throw new RuntimeException("This feature is not available as it is in progress and compilcated updates are required");
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionCalendarDaoRemote#save(com.danielbchapman.production.entity.calendar.CalendarEvent)
   */
  @Override
  public CalendarEvent save(CalendarEvent event)
  {
    return EntityInstance.saveObject(event);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionCalendarDaoRemote#save(com.danielbchapman.production.entity.calendar.Facility)
   */
  @Override
  public Facility save(Facility facility)
  {
    return EntityInstance.saveObject(facility);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.ProductionCalendarDaoRemote#save(com.danielbchapman.production.entity.calendar.Equipment)
   */
  @Override
  public Equipment save(Equipment equipment)
  {
    return EntityInstance.saveObject(equipment);
  }

}
