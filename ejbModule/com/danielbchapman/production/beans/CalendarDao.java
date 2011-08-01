package com.danielbchapman.production.beans;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.danielbchapman.production.entity.Day;
import com.danielbchapman.production.entity.Event;
import com.danielbchapman.production.entity.EventMapping;
import com.danielbchapman.production.entity.Performance;
import com.danielbchapman.production.entity.PerformanceAdvance;
import com.danielbchapman.production.entity.PerformanceSchedule;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Week;

/**
 * A simple Data Access Object that interfaces with the weekly/daily/event
 * schedule an modifies and saves events. This is then translated into the 
 * report which creates weekly schedules.
 * 
 * This isn't running under an EJB container so its a bean. This means there 
 * are initializations for the manager.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Oct 1, 2009 2009
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Stateless
public class CalendarDao implements CalendarDaoRemote
{
	private static final long serialVersionUID = 1L;
	Logger log = Logger.getLogger(CalendarDao.class);
	public static Date findMonday(Date date)
  {
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    
    for(;cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY;)
      cal.add(Calendar.DATE, -1);

    return cal.getTime();
  }
  
  //  @PersistenceContext
  EntityManager em = EntityInstance.getEm();
  
  @Override
  public boolean dayExists(Date date, Season season)
  {
    if(date == null)
      return false;
    
    Query q = em.createQuery("SELECT d FROM Day d WHERE d.date = ?1 AND d.week.season = ?2");
    q.setParameter(1, date);
    q.setParameter(2, season);
    
    Day day = null;
    try
    {
      day = (Day) q.getSingleResult();
      if(day != null)
        return true;
    }
    catch(NoResultException e)
    {
      day = null;//debug hook
    }
    catch(NonUniqueResultException e)
    {
      day = null;// it must exist...
      return true;
    }
    return false;
  }
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Day> getActiveDaysForWeek(Week week)
  {
    Query q = em.createQuery("SELECT d FROM Day d WHERE d.week = ?1");
    q.setParameter(1, week);
    ArrayList<Day> days = new ArrayList<Day>();
    
    List<Day> results = (List<Day>)q.getResultList();
    if(results != null)
      for(Day d : results)
        days.add(d);
    
    return days;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#getAllWeeks(com.danielbchapman.production.entity.Production)
   */
  @SuppressWarnings("unchecked")
  public ArrayList<Week> getAllWeeks(Season season)
  {
  	ArrayList<Week> ret = new ArrayList<Week>();
  	
  	if(season == null)
  		return ret;
  	
    Query q = em.createQuery("SELECT w FROM Week w WHERE w.season = ?1 ORDER BY w.id");
    q.setParameter(1, season);
    
    List<Week> weeks = (List<Week>)q.getResultList();
    
    if(weeks != null)
      for(Week w : weeks)
        ret.add(w);
    
    return ret;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#getEvents(com.danielbchapman.production.entity.Day)
   */
  @SuppressWarnings("unchecked")
	public ArrayList<Event> getEvents(Day day)
  {
  	Query q = em.createQuery("SELECT e FROM Event e WHERE e.day = ?1 ORDER BY e.start");
  	q.setParameter(1, day);
  	
  	ArrayList<Event> ret = new ArrayList<Event>();
  	List<Event> results = (List<Event>)q.getResultList();
  	if(results != null)
  		for(Event e : results)
  			ret.add(e);
  	
  	return ret;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public ArrayList<Event> getEventsForDay(Day day)
  {
    Query q = em.createQuery("SELECT e FROM Event e WHERE e.day = ?1");
    q.setParameter(1, day);
    ArrayList<Event> events = new ArrayList<Event>();
    
    List<Event> results = (List<Event>)q.getResultList();
    if(results != null)
      for(Event e : results)
        events.add(e);
    
    return events;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#getOrCreateDay(java.util.Date, com.danielbchapman.production.entity.Production)
   */
  @Override
  public Day getOrCreateDay(Date date, Season production)
  {
    Week weekRef= getOrCreateWeek(date, production);//Haha week reference? Get it?!
    Day day = getOrCreateDay(date, weekRef);
    return day;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#getOrCreateDay(java.util.Date, com.danielbchapman.production.entity.Week)
   */
  @SuppressWarnings("unchecked")
  public Day getOrCreateDay(Date date, Week week)
  {
  	Query q = em.createQuery("SELECT d FROM Day d WHERE d.date = ?1 AND d.week = ?2 ORDER BY d.id");
  	q.setParameter(1, date);
  	q.setParameter(2, week);
  	ArrayList<Day> toRemove = new ArrayList<Day>();
  	Day day = null;
  	try
  	{
  	  List<Day> days = (List<Day>)q.getResultList();
  	  if(days.size() > 0)
  	  {
  	    for(int i = 0; i < days.size(); i++)
  	    {
  	      if(i == 0)
  	      {
  	        day = days.get(i);
  	        break;
  	      }
  	      toRemove.add(days.get(i));
  	    }
  	  }

  	  if(toRemove.size() > 0)
  	  {//A previous version could cause two days to appear--this will automatically clean the model.
  	    System.out.println("DENORMALIZED DATABASE--CLEANING");
  	    for(Day d : toRemove)
  	      removeItem(d);
  	  }
  	}
  	catch(NoResultException e)
  	{
  		day = null;//debug hook
  	}
  	
  	if(day == null)
  	{
  		day = new Day();
  		day.setDate(date);
  		day.setWeek(week);
  		saveDay(day);
  		
  		day = getOrCreateDay(day.getDate(), day.getWeek()); //refresh
  	}
  	
  	return day;
  }
 
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#getOrCreateWeek(java.util.Date, com.danielbchapman.production.entity.Production)
   */
  public Week getOrCreateWeek(Date dayInWeek, Season season)
  {
    if(dayInWeek == null)
      return null;
    
    dayInWeek = findMonday(dayInWeek);
    
    Query q = em.createQuery("SELECT w FROM Week w WHERE w.date = ?1 AND w.season = ?2");
    q.setParameter(1, dayInWeek);
    q.setParameter(2, season);
    Week ret = null;
    try
    {
    	ret = (Week)q.getSingleResult();	
    }
    catch(NoResultException e)
    {
    	ret = null; //debug hook
    }
    
    if(ret == null)
    {
    	ret = new Week();
    	ret.setProduction(season);
    	ret.setDate(dayInWeek);
    	
    	saveWeek(ret);
    	ret = getOrCreateWeek(ret.getDate(), ret.getProduction());
    }
    
    return ret;
  }
  
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#getWeek(java.lang.Long)
   */
  public Week getWeek(Long id)
  {
  	return em.find(Week.class, id);
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#removeItem(java.lang.Object)
   */
  public void removeItem(Object obj)
  {
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    if(obj instanceof Week)
    {
      Week week = (Week) obj;
      Query q = em.createQuery("DELETE FROM Week w WHERE w.id = ?1");
      q.setParameter(1, week.getId());
      q.executeUpdate();      
    }
    else if(obj instanceof Day)
    {
      Day day = (Day) obj;
      Query q; 
      
      q = em.createQuery("DELETE FROM Performance p WHERE p.day.id = ?1");
      q.setParameter(1, day.getId());
      q.executeUpdate();
      
      q = em.createQuery("DELETE FROM Event e WHERE e.day.id = ?1");
      q.setParameter(1, day.getId());
      q.executeUpdate();
      
      q = em.createQuery("DELETE FROM Day d WHERE d.id = ?1");
      q.setParameter(1, day.getId());
      q.executeUpdate();      
    }
    else if(obj instanceof Event)
    {
      Event event = (Event) obj;
      Query q = em.createQuery("DELETE FROM Event e WHERE e.id = ?1");
      q.setParameter(1, event.getId());
      q.executeUpdate();
    }
    else //Will likely throw an Illegal Argument Exception--beware of detached values
      em.remove(obj);
  	
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().commit();
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#saveDay(com.danielbchapman.production.entity.Day)
   */
  public void saveDay(Day source)
  {
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    
  	em.merge(source);
  	
  	if(!Config.CONTAINER_MANAGED)
  	  em.getTransaction().commit();
  }
  

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#saveEvent(com.danielbchapman.production.entity.Event)
   */
  public void saveEvent(EventMapping source)
  {
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    
    if(source.getId() != null)
    	em.merge(source);
    else
    	em.persist(source);
    
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().commit();
  }

  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#savePerformance(com.danielbchapman.production.entity.Performance)
   */
  public void savePerformance(Performance performance)
  {
  	saveEvent(performance);
  }
  /* (non-Javadoc)
   * @see com.danielbchapman.production.beans.CalendarDaoRemote#saveWeek(com.danielbchapman.production.entity.Week)
   */
  public void saveWeek(Week source)
  { 
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().begin();
    
    source.setLastUpdated(new Timestamp(System.currentTimeMillis()));
    em.merge(source);
    
    if(!Config.CONTAINER_MANAGED)
      em.getTransaction().commit();
  }
  
	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getPerformances(com.danielbchapman.production.entity.Day)
	 */
	@Override
	public ArrayList<Performance> getPerformances(Day day)
	{
		return (ArrayList<Performance>) EntityInstance.getResultList("SELECT p FROM Performance p WHERE p.day = ?1 ORDER BY p.start", new Object[]{day}, Performance.class);
	}
	
	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getPerformances(com.danielbchapman.production.entity.Production)
	 */
	@Override
	public ArrayList<Performance> getPerformances(Season s)
	{
		return (ArrayList<Performance>) EntityInstance.getResultList("SELECT p FROM Performance p WHERE p.season = ?1 ORDER BY p.start", new Object[]{s}, Performance.class);
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getEventsAndPerformancesForDay(com.danielbchapman.production.entity.Day)
	 */
	@Override
	public ArrayList<EventMapping> getEventsAndPerformancesForDay(Day day)
	{
		log.debug("getEventsAndPerformancesForDay(); " + day);
		ArrayList<Event> events = getEvents(day);
		ArrayList<Performance> performances = getPerformances(day);
		
		ArrayList<EventMapping> ret = new ArrayList<EventMapping>();
		for(Event e : events)
			ret.add(e);
		
		log.debug("Event Count" + events.size());
		log.debug("Performance Count" + performances.size());
		
		for(Performance p : performances)
			ret.add(p);
		
		Collections.sort(events);
		
		return ret; 
	}
	
	@Override
	public void savePerformanceSchedule(PerformanceSchedule schedule)
	{
		EntityInstance.saveObject(schedule);
	}
	
	@Override
	public PerformanceSchedule getPerformanceSchedule(Long id)
	{
		return em.find(PerformanceSchedule.class, id);
	}
	
	@Override
	public ArrayList<PerformanceSchedule> getAllPerformanceSchedules()
	{
		return EntityInstance.getResultList("SELECT p FROM PerformanceSchedule p ORDER BY p.season, p.name", PerformanceSchedule.class);
	}
	
	@Override
	public ArrayList<PerformanceSchedule> getPerformanceSchedulesForSeason(Season season)
	{
		return EntityInstance.getResultList("SELECT p FROM PerformanceSchedule p WHERE p.season = ?1 ORDER BY p.name", PerformanceSchedule.class, season);
	}
	
	@Override
	public void savePerformanceAdvance(PerformanceAdvance advance)
	{
		EntityInstance.saveObject(advance);
	}
	
	@Override
	public PerformanceAdvance getPerformanceAdvance(Long id)
	{
		return em.find(PerformanceAdvance.class, id);
	}
	
	@Override
	public ArrayList<PerformanceAdvance> getPerformanceAdvance(Day d)
	{
		return EntityInstance.getResultList("SELECT p FROM PerformanceAdvance p WHERE p.day = ?1", PerformanceAdvance.class, d);
	}
}
