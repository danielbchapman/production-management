package com.danielbchapman.tests;

import net.fortuna.ical4j.model.*;
import net.fortuna.ical4j.model.property.*;

import org.junit.Test;

import com.danielbchapman.production.web.schedule.beans.CalendarServices;

public class TestCalendars
{
  @Test
  public void TestICalSupport()
  {
    String output = new CalendarServices().queryCalendar();

    
    System.out.println(output);
  }
}
