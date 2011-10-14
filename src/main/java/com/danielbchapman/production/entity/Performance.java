package com.danielbchapman.production.entity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

/**
 * A performance is an extension of an event that allows for some other linked objects to be
 * created. This adds considerable complexity to the calendar, but it does provide a way to interact
 * with other parts of the database.
 * 
 * Upon creating an event you can convert it to a performance.
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Jun 20, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Entity
public class Performance extends EventMapping
{
	private static final long serialVersionUID = 2L;

	@OneToOne(cascade = CascadeType.ALL, optional = true, targetEntity = PerformanceAdvance.class)
	private PerformanceAdvance advance;
	private PerformanceSchedule performanceSchedule;
	private boolean talkback;
	private boolean crewCall;
	private boolean strike;
	private boolean fightCall;
	private Venue venue;

	public Performance()
	{
		super();
	}

	public PerformanceAdvance getAdvance()
	{
		return advance;
	}

	/**
	 * The event sequence is a series of event related to a specific performance. This includes all
	 * the events defaulted in by the performance schedule. This method is per-entity and allows quick
	 * and easy generation of the schedule so that the logic can be in one place.
	 * 
	 * @return a list of all the events revolving around this performance
	 *         <em>excluding the performance itself.</em> If the performance schedule is null it
	 *         returns an empty list.
	 * 
	 */
	@Transient
	public ArrayList<PerformanceEvent> getEventSequence()
	{
		ArrayList<PerformanceEvent> events = new ArrayList<PerformanceEvent>();

		if(performanceSchedule == null)
			return events;
		if(crewCall)
			events.add(getEvent("Crew Call", performanceSchedule.getCrewCall(),
					performanceSchedule.getCrewCall(), true, true, true));

		events
				.add(getEvent("AEA Call", performanceSchedule.getActorsEquityCall(), 30, true, true, true));
		if(fightCall)
			events.add(getEvent("Fight Call", performanceSchedule.getFightCall(), 15, true, true, true));
		events.add(getEvent("House Open", performanceSchedule.getHouseCall(),
				performanceSchedule.getHouseCall(), true, true, true));

		events.add(getEvent(getDescription(), 0, performanceSchedule.getPerformanceLength(), true,
				true, true));

		if(talkback)
		{
			events.add(getEvent("Talkback", performanceSchedule.getPerformanceLength(), 15, false, true,
					true));
			if(strike)
				events.add(getEvent("Strike", performanceSchedule.getPerformanceLength() + 15,
						performanceSchedule.getStrikeLength(), false, true, true));
		}
		else
			if(strike)
				events.add(getEvent("Strike", performanceSchedule.getPerformanceLength(),
						performanceSchedule.getStrikeLength(), false, true, true));

		return events;
	}

	public PerformanceSchedule getPerformanceSchedule()
	{
		return performanceSchedule;
	}

	public Venue getVenue()
	{
		return venue;
	}

	public boolean isCrewCall()
	{
		return crewCall;
	}

	public boolean isFightCall()
	{
		return fightCall;
	}

	/**
	 * @return <b>true</b> this is a performance, it returns true
	 */
	@Transient
	@Override
	public boolean isPerformance()
	{
		return true;
	}

	public boolean isStrike()
	{
		return strike;
	}

	public boolean isTalkback()
	{
		return talkback;
	}

	public void setAdvance(PerformanceAdvance advance)
	{
		this.advance = advance;
	}

	public void setCrewCall(boolean crewCall)
	{
		this.crewCall = crewCall;
	}

	public void setFightCall(boolean fightCall)
	{
		this.fightCall = fightCall;
	}

	public void setPerformanceSchedule(PerformanceSchedule performanceSchedule)
	{
		this.performanceSchedule = performanceSchedule;
	}

	public void setStrike(boolean strike)
	{
		this.strike = strike;
	}

	public void setTalkback(boolean talkback)
	{
		this.talkback = talkback;
	}

	public void setVenue(Venue venue)
	{
		this.venue = venue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder buf = new StringBuilder();

		buf.append(super.toString());
		buf.append("' Performance:'");
		buf.append(performanceSchedule == null ? "null" : performanceSchedule.getName());
		buf.append("' Venue:'");
		buf.append(venue == null ? "null" : venue.getName());
		buf.append("' Advance:'");
		buf.append(advance.getId());

		return buf.toString();
	}

	private final PerformanceEvent getEvent(String description, int offset, int duration,
			boolean eventBeforePerformance, boolean cast, boolean crew)
	{
		Calendar startTime = Calendar.getInstance();
		Calendar endTime = Calendar.getInstance();
		startTime.setTime(composeUtcFromDay(getDay(), getStart()));
		endTime.setTime(composeUtcFromDay(getDay(), getStart()));

		if(eventBeforePerformance)
		{
			startTime.add(Calendar.MINUTE, offset * -1);
			endTime.add(Calendar.MINUTE, (offset * -1) + duration);
		}
		else
		{
			startTime.add(Calendar.MINUTE, offset);
			endTime.add(Calendar.MINUTE, offset + duration);
		}

		return new PerformanceEvent(getDay(), description, startTime.getTime(), endTime.getTime(),
				cast, crew);
	}

	public class PerformanceEvent extends EventMapping
	{
		private static final long serialVersionUID = 1L;

		public PerformanceEvent(Day day, String description, Date start, Date end, boolean cast,
				boolean crew)
		{
			setDay(day);
			setDescription(description);
			setStart(start);
			setEnd(end);
			setCast(cast);
			setCrew(crew);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.danielbchapman.production.entity.EventMapping#isPerformance()
		 */
		@Override
		public boolean isPerformance()
		{
			return true; // This is part of performance, not event
		}
	}
}
