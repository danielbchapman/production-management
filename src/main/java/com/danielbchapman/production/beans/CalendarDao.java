package com.danielbchapman.production.beans;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import org.theactingcompany.persistence.Indentifiable;

import com.danielbchapman.production.entity.Day;
import com.danielbchapman.production.entity.EntityInstance;
import com.danielbchapman.production.entity.Event;
import com.danielbchapman.production.entity.EventMapping;
import com.danielbchapman.production.entity.Performance;
import com.danielbchapman.production.entity.PerformanceAdvance;
import com.danielbchapman.production.entity.PerformanceSchedule;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Venue;
import com.danielbchapman.production.entity.VenueLog;
import com.danielbchapman.production.entity.Week;

/**
 * A simple Data Access Object that interfaces with the weekly/daily/event schedule an modifies and
 * saves events. This is then translated into the report which creates weekly schedules.
 * 
 * This isn't running under an EJB container so its a bean. This means there are initializations for
 * the manager.
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

	public static Date findMonday(Date date)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		for(; cal.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY;)
			cal.add(Calendar.DATE, -1);

		return cal.getTime();
	}

	Logger log = Logger.getLogger(CalendarDao.class);

	// @PersistenceContext
	EntityManager em = EntityInstance.getEm();

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#archiveAdvance(com.danielbchapman.production
	 * .entity.PerformanceAdvance, com.danielbchapman.production.entity.Venue)
	 */
	@Override
	public void archiveAdvance(PerformanceAdvance advance, Venue venue)
	{
		//@formatter:off		
		StringBuilder builder = new StringBuilder();
		SimpleDateFormat date = new SimpleDateFormat("MM-dd-yyyy");
		builder.append("<div>");
		builder.append("<h3>");
		builder.append("Archive of Advance (ID:");
		builder.append(advance.getId());
		builder.append(")</h3>");
		builder.append("<h4>Date ");
		builder.append(advance.getPerformance() == null 
				? "unknown" : date.format(advance.getPerformance().getDay().getDate()));
		builder.append(" | ");
		builder.append(advance.getPerformance() == null 
				? "Unkown Venue" 
						: advance.getPerformance().getVenue() == null 
						? "Null Venue" : advance.getPerformance().getVenue().getName());
		builder.append("</h4>");
		builder.append("<ul>");
		createListItem("Contact Information", advance.getContactInformation(), builder);
		createListItem("Gaff Tape", advance.getGaffTape(), builder);
		createListItem("Ground Plan", advance.getGroundPlan(), builder);
		createListItem("Hospitality", advance.getHospitality(), builder);
		createListItem("Lighting Notes", advance.getLightingNotes(), builder);
		createListItem("Light Plot", advance.getLightPlot(), builder);
		createListItem("Loading Dock", advance.getLoadingDock(), builder);
		createListItem("Masking Notes", advance.getMaskingNotes(), builder);
		createListItem("Rigging", advance.getRigging(), builder);
		createListItem("Scenic Notes", advance.getScenicNotes(), builder);
		createListItem("Section", advance.getSection(), builder);
		createListItem("Shore Power", advance.getShorePower(), builder);
		createListItem("Sound Notes", advance.getSoundNotes(), builder);
		createListItem("Ironing Board", advance.getWardrobeIroningBoard(), builder);
		createListItem("Laundry", advance.getWardrobeLaundry(), builder);
		createListItem("Steamer", advance.getWardrobeSteamer(), builder);
		createListItem("Wardrobe Notes", advance.getWardrobeNotes(), builder);
		builder.append("</ul>");
		builder.append("</div>");
		//@formatter:on		
		VenueLog log = new VenueLog();
		log.setDate(new Date());
		log.setNotes(builder.toString());
		log.setVenue(venue);
		EntityInstance.saveObject(log);

		removeItem(advance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#createPerformanceAdvance(com.danielbchapman
	 * .production.entity.Performance)
	 */
	@Override
	public PerformanceAdvance createPerformanceAdvance(Performance performance)
	{
		PerformanceAdvance advance = new PerformanceAdvance();
		advance.setComplete(false);
		advance.setDay(performance.getDay());
		advance.setPerformance(performance);
		advance.setVenue(performance.getVenue());
		performance.setAdvance(advance);
		savePerformance(performance);
		return advance;
	}

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
			day = null;// debug hook
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

		List<Day> results = (List<Day>) q.getResultList();
		if(results != null)
			for(Day d : results)
				days.add(d);

		return days;
	}

	@Override
	public ArrayList<PerformanceSchedule> getAllPerformanceSchedules()
	{
		return EntityInstance.getResultList("SELECT p FROM PerformanceSchedule p ORDER BY p.name",
				PerformanceSchedule.class);
	}

	@Override
	public ArrayList<Week> getAllWeeks()
	{
		return EntityInstance.getResultList("SELECT w FROM Week w ORDER BY w.date", Week.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#getAllWeeks(com.danielbchapman.production
	 * .entity.Production)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ArrayList<Week> getAllWeeks(Season season)
	{
		ArrayList<Week> ret = new ArrayList<Week>();

		if(season == null)
			return ret;

		Query q = em.createQuery("SELECT w FROM Week w WHERE w.season = ?1 ORDER BY w.date");
		q.setParameter(1, season);

		List<Week> weeks = (List<Week>) q.getResultList();

		if(weeks != null)
			for(Week w : weeks)
				ret.add(w);

		return ret;
	}

	@Override
	public ArrayList<Performance> getAlternativePerformances(PerformanceAdvance advance)
	{
		if(advance == null)
			throw new IllegalArgumentException("Advances must be non-null.");
		//@formatter:off
		String query = "SELECT p FROM Performance p WHERE p.advance IS NULL ORDER BY p.day.date";
		//@formatter:on

		return EntityInstance.getResultList(query, Performance.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#getEvents(com.danielbchapman.production
	 * .entity.Day)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public ArrayList<Event> getEvents(Day day)
	{
		Query q = em.createQuery("SELECT e FROM Event e WHERE e.day = ?1 ORDER BY e.start");
		q.setParameter(1, day);

		ArrayList<Event> ret = new ArrayList<Event>();
		List<Event> results = (List<Event>) q.getResultList();
		if(results != null)
			for(Event e : results)
				ret.add(e);

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getEventsAndPerformancesForDay(com.
	 * danielbchapman.production.entity.Day)
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

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<Event> getEventsForDay(Day day)
	{
		Query q = em.createQuery("SELECT e FROM Event e WHERE e.day = ?1");
		q.setParameter(1, day);
		ArrayList<Event> events = new ArrayList<Event>();

		List<Event> results = (List<Event>) q.getResultList();
		if(results != null)
			for(Event e : results)
				events.add(e);

		return events;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getOrCreateDay(java.util.Date,
	 * com.danielbchapman.production.entity.Production)
	 */
	@Override
	public Day getOrCreateDay(Date date, Season season)
	{
		Week weekRef = getOrCreateWeek(date, season);// Haha week reference? Get it?!
		Day day = getOrCreateDay(date, weekRef);
		return day;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getOrCreateDay(java.util.Date,
	 * com.danielbchapman.production.entity.Week)
	 */
	@Override
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
			List<Day> days = (List<Day>) q.getResultList();
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
			{// A previous version could cause two days to appear--this will automatically clean the
				// model.
				System.out.println("DENORMALIZED DATABASE--CLEANING");
				for(Day d : toRemove)
					removeItem(d);
			}
		}
		catch(NoResultException e)
		{
			day = null;// debug hook
		}

		if(day == null)
		{
			day = new Day();
			day.setDate(date);
			day.setWeek(week);
			saveDay(day);

			day = getOrCreateDay(day.getDate(), day.getWeek()); // refresh
		}

		return day;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getOrCreateWeek(java.util.Date,
	 * com.danielbchapman.production.entity.Production)
	 */
	@Override
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
			ret = (Week) q.getSingleResult();
		}
		catch(NoResultException e)
		{
			ret = null; // debug hook
		}

		if(ret == null)
		{
			ret = new Week();
			ret.setSeason(season);
			ret.setDate(dayInWeek);

			saveWeek(ret);
			ret = getOrCreateWeek(ret.getDate(), ret.getSeason());
		}

		return ret;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getPerformance(java.lang.Long)
	 */
	@Override
	public Performance getPerformance(Long id)
	{
		return em.find(Performance.class, id);
	}

	@Override
	public PerformanceAdvance getPerformanceAdvance(Day d)
	{
		ArrayList<PerformanceAdvance> results = EntityInstance.getResultList(
				"SELECT p FROM PerformanceAdvance p WHERE p.day = ?1", PerformanceAdvance.class, d);

		if(results.size() == 0)
			return null;
		else
			return results.get(0);
	}

	@Override
	public PerformanceAdvance getPerformanceAdvance(Long id)
	{
		return em.find(PerformanceAdvance.class, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#getPerformanceAdvances(com.danielbchapman
	 * .production.entity.Venue)
	 */
	@Override
	public ArrayList<PerformanceAdvance> getPerformanceAdvances(Venue venue)
	{
		//@formatter:off
		/* Removed by de-normalizing the database and storing venues in the day/performanceAdvance
		
		String query = 
						"SELECT  \n" +
						"  a.id \n" +
						"FROM  \n" +
						"  PerformanceAdvance a  \n" +
						"  INNER JOIN  \n" +
						"    Venue v  \n" +
						"    INNER JOIN Performance p \n" +
						"      ON p.venue_id = v.id \n" +
						"    ON \n" +
						"      a.performance_id = p.id \n" +
						"WHERE \n" +
						"  p.venue_id = ?1; \n" ;
		//@formatter:on
		 Query q = EntityInstance.getEm().createNativeQuery(query);
		 q.setParameter(1, venue.getId());
		 List<Long> ids = (List<Long>) q.getResultList();
		
		 ArrayList<PerformanceAdvance> advances = new ArrayList<PerformanceAdvance>();
		 for(Long id : ids)
		 advances.add(getPerformanceAdvance(id));
		
		 return advances;
		 */
		// @formatter:on
		return EntityInstance.getResultList("SELECT p FROM PerformanceAdvance p WHERE p.venue = ?1",
				PerformanceAdvance.class, venue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#getPerformances(com.danielbchapman.production
	 * .entity.Day)
	 */
	@Override
	public ArrayList<Performance> getPerformances(Day day)
	{
		return (ArrayList<Performance>) EntityInstance.getResultList(
				"SELECT p FROM Performance p WHERE p.day = ?1 ORDER BY p.start", new Object[] { day },
				Performance.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#getPerformances(com.danielbchapman.production
	 * .entity.Production)
	 */
	@Override
	public ArrayList<Performance> getPerformances(Season s)
	{
		return (ArrayList<Performance>) EntityInstance.getResultList(
				"SELECT p FROM Performance p WHERE p.season = ?1 ORDER BY p.start", new Object[] { s },
				Performance.class);
	}

	@Override
	public PerformanceSchedule getPerformanceSchedule(Long id)
	{
		return em.find(PerformanceSchedule.class, id);
	}

	@Override
	public ArrayList<PerformanceSchedule> getPerformanceSchedulesForSeason(Season season)
	{
		return EntityInstance.getResultList("SELECT p FROM PerformanceSchedule p ORDER BY p.name",
				PerformanceSchedule.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getWeek(java.lang.Long)
	 */
	@Override
	public Week getWeek(Long id)
	{
		return em.find(Week.class, id);
	}

	@Override
	public ArrayList<Week> getWeeksInRange(Date start, Date end, Season season)
	{
		Date startMonday = findMonday(start);
		Date endMonday = findMonday(end);
		//@formatter:off
		String query = 
						"SELECT  \n" +
						"  w  \n" +
						"FROM  \n" +
						"  Week w  \n" +
						"WHERE  \n" +
						"      w.date >= ?1 \n" +
						"  AND w.date <= ?2 \n" +
						"  AND w.season = ?3  \n" +
						"ORDER BY  \n" +
						"  w.date \n" ;
		//@formatter:on		
		return EntityInstance.getResultList(query, Week.class, startMonday, endMonday, season);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#removeItem(java.lang.Object)
	 */
	@Override
	public void removeItem(Indentifiable obj)
	{
		if(obj instanceof Day)
		{
			Day day = (Day) obj;
			Week week = day.getWeek();
			week.getDays().remove(day);
			ArrayList<Performance> performances = getPerformances(day);

			for(Performance p : performances)
				removeItem(p);

			ArrayList<PerformanceAdvance> advances = EntityInstance.getResultList(
					"SELECT p FROM PerformanceAdvance p WHERE p.day = ?1", PerformanceAdvance.class, day);
			for(PerformanceAdvance adv : advances)
			{
				adv.setDay(null);
				adv.setPerformance(null);// probably redundant, but worth setting
				EntityInstance.saveObject(adv);
			}

			EntityInstance.deleteObject(day);
			EntityInstance.saveObject(week);
			return;
		}

		if(obj instanceof Event)
		{
			Event event = (Event) obj;
			Day day = event.getDay();
			day.getEvents().remove(event);
			EntityInstance.deleteObject(event);
			EntityInstance.saveObject(day);
			return;
		}

		if(obj instanceof Performance)
		{
			Performance perf = (Performance) obj;
			Day day = perf.getDay();
			day.getPerformances().remove(perf);
			PerformanceAdvance advance = perf.getAdvance();
			/* Create an Orphan so we can find it */
			if(advance != null)
			{
				advance.setPerformance(null);
				perf.setAdvance(null);
				EntityInstance.saveObject(advance);
				EntityInstance.saveObject(perf);
			}

			EntityInstance.deleteObject(perf);
			EntityInstance.saveObject(day);
			return;
		}

		if(obj instanceof PerformanceAdvance)
		{
			PerformanceAdvance advance = (PerformanceAdvance) obj;

			Performance p = advance.getPerformance();
			if(p != null)
			{
				advance.getPerformance().setAdvance(null);
				advance.setPerformance(null);
				savePerformance(p);
			}

			EntityInstance.deleteObject(advance);
			return;
		}
		throw new IllegalArgumentException("The object " + obj + " was not removed.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#saveDay(com.danielbchapman.production
	 * .entity.Day)
	 */
	@Override
	public void saveDay(Day source)
	{
		if(source.getWeek() == null)
			throw new IllegalArgumentException("Days require a week to persist" + source);

		if(source.getId() == null)
		{
			if(source.getWeek().getDays() == null)
				source.getWeek().setDays(new Vector<Day>());

			source.getWeek().getDays().add(source);
			saveWeek(source.getWeek());
		}
		else
		{
			EntityInstance.saveObject(source);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#saveEvent(com.danielbchapman.production
	 * .entity.Event)
	 */
	@Override
	public void saveEvent(EventMapping source)
	{
		if(source instanceof Event)
		{
			if(source.getId() == null)
			{
				if(source.getDay().getEvents() == null)
					source.getDay().setEvents(new Vector<Event>());

				source.getDay().getEvents().add((Event) source);
				saveDay(source.getDay());
			}
			else
				EntityInstance.saveObject(source);
			return;
		}

		if(source instanceof Performance)
		{
			if(source.getId() == null)
			{
				if(source.getDay().getPerformances() == null)
					source.getDay().setPerformances(new Vector<Performance>());

				source.getDay().getPerformances().add((Performance) source);
				saveDay(source.getDay());
			}
			else
				EntityInstance.saveObject(source);
			return;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#savePerformance(com.danielbchapman.production
	 * .entity.Performance)
	 */
	@Override
	public void savePerformance(Performance performance)
	{
		saveEvent(performance);
	}

	@Override
	public void savePerformanceAdvance(PerformanceAdvance advance)
	{
		EntityInstance.saveObject(advance);
	}

	@Override
	public void savePerformanceSchedule(PerformanceSchedule schedule)
	{
		EntityInstance.saveObject(schedule);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.danielbchapman.production.beans.CalendarDaoRemote#saveWeek(com.danielbchapman.production
	 * .entity.Week)
	 */
	@Override
	public void saveWeek(Week source)
	{
		EntityInstance.saveObject(source);
	}

	private final void createListItem(String header, Object value, StringBuilder builder)
	{
		builder.append("<li>");
		wrapElement(header, "h4", builder);
		wrapElement(value, "span", builder);
		builder.append("</li>");
	}

	/**
	 * @param value
	 *          the information
	 * @param tag
	 *          the tag
	 * @param builder
	 *          the stringbuilder
	 */
	private final void wrapElement(Object value, String tag, StringBuilder builder)
	{
		builder.append('<');
		builder.append(tag);
		builder.append('>');
		builder.append(value);
		builder.append('<');
		builder.append('/');
		builder.append(tag);
		builder.append('>');
	}
}
