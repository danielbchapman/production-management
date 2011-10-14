package com.danielbchapman.production.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Remote;

import org.theactingcompany.persistence.Indentifiable;

import com.danielbchapman.production.entity.Day;
import com.danielbchapman.production.entity.Event;
import com.danielbchapman.production.entity.EventMapping;
import com.danielbchapman.production.entity.Performance;
import com.danielbchapman.production.entity.PerformanceAdvance;
import com.danielbchapman.production.entity.PerformanceSchedule;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Venue;
import com.danielbchapman.production.entity.Week;

/**
 * A simple remote interface defining the CalendarDao
 */
@Remote
public interface CalendarDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;

	/**
	 * This method takes an advance, makes it a simple HTML form and logs it in the venue log. This is
	 * intended to be used when we come back to a venue year after year. This also allows a broken
	 * advance information to be stored in the logs and found.
	 * 
	 * @param advance
	 *          the advance to archive
	 * @param venue
	 *          the venue for this advance
	 */
	public abstract void archiveAdvance(PerformanceAdvance advance, Venue venue);

	/**
	 * Create an advance for this performance.
	 * 
	 * @param performance
	 * @return a reference to this advance
	 */
	public abstract PerformanceAdvance createPerformanceAdvance(Performance performance);

	/**
	 * 
	 * @param date
	 *          the date to search
	 * @return true if it is in the database, false otherwise.
	 * 
	 */
	public boolean dayExists(Date date, Season production);

	/**
	 * Return a list of all Days that have been created.
	 */
	public abstract ArrayList<Day> getActiveDaysForWeek(Week week);

	/**
	 * @return a list of known performance schedules
	 * 
	 */
	public abstract ArrayList<PerformanceSchedule> getAllPerformanceSchedules();

	/**
	 * @return all weeks in the system.
	 */
	public abstract ArrayList<Week> getAllWeeks();

	/**
	 * @return a list of all weeks, ordered
	 */
	public abstract ArrayList<Week> getAllWeeks(Season production);

	/**
	 * @param advance
	 *          the advance to search
	 * @return a list of possible performances to assign this advance to.
	 */
	public abstract ArrayList<Performance> getAlternativePerformances(PerformanceAdvance advance);

	/**
	 * @param day
	 *          the day to query
	 * @return a list of events for this day (zero count for none)
	 */
	public abstract ArrayList<Event> getEvents(Day day);

	/**
	 * @param day
	 *          the day to search
	 * @return all the events and performances for a specific day
	 * 
	 */
	public abstract ArrayList<EventMapping> getEventsAndPerformancesForDay(Day day);

	/**
	 * @param day
	 *          the day to search
	 * @return all the events for a specific day
	 * 
	 */
	public abstract ArrayList<Event> getEventsForDay(Day day);

	/**
	 * This call creates a Week if necessary and then creates a Day for the production
	 * 
	 * @param date
	 *          the Date of the Day
	 * @param production
	 *          the Production to assign this Week/Day to
	 * @return a Day created for the Production.
	 * 
	 */
	public abstract Day getOrCreateDay(Date date, Season production);

	/**
	 * Creates a Day object for a date and a week
	 * 
	 * @param date
	 *          the Date of the Day
	 * @param week
	 *          the Week to connect to (production based)
	 * @return a new Day object for the Date/Week
	 * 
	 */
	public abstract Day getOrCreateDay(Date date, Week week);

	/**
	 * @param dayInWeek
	 * @param production
	 * @return a week by the date
	 */
	public abstract Week getOrCreateWeek(Date dayInWeek, Season production);

	/**
	 * @param id
	 *          the id of the performance
	 * @return a performance for this id
	 */
	public abstract Performance getPerformance(Long id);

	/**
	 * 
	 * @param d
	 *          the day to look for
	 * @return a list of all PerformanceAdvance objects bound to this day
	 * 
	 */
	public abstract PerformanceAdvance getPerformanceAdvance(Day d);

	/**
	 * @param id
	 *          the id of the entity
	 * @return the PerformanceAdvance specified by this ID
	 * 
	 */
	public abstract PerformanceAdvance getPerformanceAdvance(Long id);

	/**
	 * @param venue
	 * @return a list of advances that are tied to a venue. These may or may not be orphans.
	 */
	public abstract ArrayList<PerformanceAdvance> getPerformanceAdvances(Venue venue);

	/**
	 * @param day
	 *          the day to query
	 * @return a list of all the performances for that day
	 * 
	 */
	public abstract ArrayList<Performance> getPerformances(Day day);

	/**
	 * @param p
	 *          the production to look for
	 * @return a list of all performances for this production
	 */
	public abstract ArrayList<Performance> getPerformances(Season p);

	/**
	 * @param id
	 *          the ID to look for
	 * @return the performance schedule associated with this ID
	 * 
	 */
	public abstract PerformanceSchedule getPerformanceSchedule(Long id);

	/**
	 * @param production
	 *          the production to filter by
	 * @return a list of known performance schedules for the selected production
	 * 
	 */
	public abstract ArrayList<PerformanceSchedule> getPerformanceSchedulesForSeason(Season production);

	/**
	 * @param id
	 * @return a week by the ID
	 */
	public abstract Week getWeek(Long id);

	/**
	 * Get all the weeks in the range start/end. This will search back to a previous Monday in the
	 * selected week
	 * 
	 * @param start
	 *          the start week
	 * @param end
	 *          the end week
	 * @param season
	 *          the season to print
	 * @return a list of the weeks in this range.
	 */
	public abstract ArrayList<Week> getWeeksInRange(Date start, Date end, Season season);

	/**
	 * Removes an entity from the persistence layer
	 * 
	 * @param Indentifiable
	 *          any object
	 */
	public abstract void removeItem(Indentifiable obj);

	/**
	 * Saves a day
	 * 
	 * @param source
	 */
	public abstract void saveDay(Day source);

	/**
	 * Saves an event
	 * 
	 * @param source
	 */
	public abstract void saveEvent(EventMapping source);

	/**
	 * Saves a performance
	 * 
	 * @param source
	 * 
	 */
	public abstract void savePerformance(Performance source);

	/**
	 * Saves or updates a PerformanceAdvance.
	 * 
	 * @param advance
	 *          the advance to save or update
	 * 
	 */
	public abstract void savePerformanceAdvance(PerformanceAdvance advance);

	/**
	 * Save or update a performance schedule in the database.
	 * 
	 * @param schedule
	 *          the schedule to save or update
	 * 
	 */
	public abstract void savePerformanceSchedule(PerformanceSchedule schedule);

	/**
	 * saves a week
	 * 
	 * @param source
	 */
	public abstract void saveWeek(Week source);

}