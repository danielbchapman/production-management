package com.danielbchapman.production.models;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

public class IndexedScheduleModel implements ScheduleModel
{
	LinkedHashMap<String, ScheduleEvent> data = new LinkedHashMap<String, ScheduleEvent>();
	private BigInteger current = BigInteger.ONE;
	
	private String next()
	{
		synchronized(current)
		{
			current = current.add(BigInteger.ONE);
			return current.toString(16);
		}
	}
	@Override
	public void addEvent(ScheduleEvent event)
	{
		String id = next();
		synchronized(data)
		{
			event.setId(id);
			data.put(id, event);	
		}
	}

	@Override
	public boolean deleteEvent(ScheduleEvent event)
	{
		synchronized(data)
		{
			
			if(!deleteEvent(event.getId()))
			{
				System.out.println("Did not remove event : '" + event + "' with id '" + event.getId() + "'");
				return false;
			}
			else
				return true;
		}
	}
	
	/**
	 * @param id the id to remove 
	 * @return true if removed, false if not
	 */
	public boolean deleteEvent(String id)
	{
		synchronized(data)
		{
			return data.remove(id) == null ? false : true;
		}
	}

	@Override
	public List<ScheduleEvent> getEvents()
	{
		synchronized(data)
		{
			ArrayList<ScheduleEvent> events = new ArrayList<ScheduleEvent>();
			for(ScheduleEvent evt : data.values())
				events.add(evt);

			return events;
		}
	}

	@Override
	public ScheduleEvent getEvent(String id)
	{
		return data.get(id);
	}

	@Override
	public void updateEvent(ScheduleEvent event)
	{
		synchronized(data)
		{
			data.put(event.getId(), event);	
		}
	}

	@Override
	public int getEventCount()
	{
		return data.size();
	}

	@Override
	public void clear()
	{
		synchronized(data)
		{
			data.clear();	
		}
	}
}
