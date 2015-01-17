package com.danielbchapman.production.web.schedule.beans;

import java.net.SocketException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import net.fortuna.ical4j.util.UidGenerator;

import com.danielbchapman.utility.calendars.CalendarUtil;
import com.danielbchapman.utility.calendars.ICalEvent;

public class CalendarServices
{

  public String queryCalendar()
  {
    // FIXME plug into database!

    ArrayList<ICalEvent> events = new ArrayList<ICalEvent>();// /demo data

    for (int i = 0; i < 10; i++)
    {
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_YEAR, i);
      String title = "[4] UPDATED Test Event " + i;
      boolean allDay = i % 2 == 0;
      String description = "[4] UPDATED Event Description... " + i;

      if (allDay)
        description += " ALL DAY EVENT";

      cal.set(Calendar.HOUR_OF_DAY, 12);
      Date start = cal.getTime();
      cal.add(Calendar.HOUR_OF_DAY, 2);
      Date end = cal.getTime();

      events.add(new ICalEvent(start, end, allDay, title, description));
    }

    return createICal(events, TimeZone.getDefault());
  }

  public <T extends ICalEvent> String createICal(ArrayList<T> events, TimeZone tz)
  {
    net.fortuna.ical4j.model.Calendar iCal = new net.fortuna.ical4j.model.Calendar();
    iCal.getProperties().add(new ProdId("-//Daniel B. Chapman Production Database//iCal4j 1.0//EN"));
    iCal.getProperties().add(Version.VERSION_2_0);
    iCal.getProperties().add(CalScale.GREGORIAN);

    Calendar cal = Calendar.getInstance(tz);
    // net.fortuna.ical4j.model.TimeZone tz = new net.fortuna.ical4j.model.TimeZone("UTC");
    for (T t : events)
    {
      if (t == null)
      {
        System.out.println("[WARNING] [" + CalendarServices.class.getName() + "] Event sent to iCal was null, this event will be skipped!");
        continue;
      }

      // All Day
      if (t.isAllDay())
      {
        cal.setTime(t.getDate());
        long start = CalendarUtil.cleanDate(cal).getTimeInMillis();
        VEvent event = new VEvent(new DateTime(start), t.getTitle());
        event.getProperties().add(new Uid(t.getUid()));
        iCal.getComponents().add(event);
      }
      else
      {
        cal.setTime(t.getDate());
        long start = CalendarUtil.cleanDate(cal).getTimeInMillis();
        VEvent event = new VEvent(new DateTime(start), t.getTitle());
        event.getProperties().add(new Uid(t.getUid()));
        iCal.getComponents().add(event);
      }
    }

    return iCal.toString();
  }
}
