package com.danielbchapman.production.web.schedule.beans;

import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.locks.ReentrantLock;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.primefaces.component.blockui.BlockUI;
import org.primefaces.component.schedule.Schedule;
import org.primefaces.event.DateSelectEvent;
import org.primefaces.event.ScheduleEntrySelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.danielbchapman.production.AbstractPrintController;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.CalendarDaoRemote;
import com.danielbchapman.production.beans.SeasonDaoRemote;
import com.danielbchapman.production.beans.VenueDaoRemote;
import com.danielbchapman.production.entity.Day;
import com.danielbchapman.production.entity.Event;
import com.danielbchapman.production.entity.EventMapping;
import com.danielbchapman.production.entity.Performance;
import com.danielbchapman.production.entity.PerformanceAdvance;
import com.danielbchapman.production.entity.PerformanceSchedule;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Venue;
import com.danielbchapman.production.entity.Week;
import com.danielbchapman.production.web.production.beans.AdministrationBean;
import com.danielbchapman.production.web.production.beans.SeasonBean;
import com.danielbchapman.production.web.schedule.beans.LocationBean.HotelWrapper;

/**
 * This is the backing logic for the schedule bean which is backed by the Primefaces Schedule
 * component. This is a separate application which allows for a different backing toolset from the
 * budgeting.
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Jan 2, 2011 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@ViewScoped
@ManagedBean(name="scheduleBean")
public class ScheduleBean implements Serializable
{
	private static final long serialVersionUID = 3L;
	private ArrayList<SelectItem> allSeasons;
	private CalendarDaoRemote calendarDao;
	private Selection currentSelection = Selection.CALENDAR;
	private Day day;
	private DayUI dayUi = new DayUI(new Day());
	private EventMapping eventEntity;

	private ReentrantLock modelLock = new ReentrantLock();
	//@formatter:off
	private SelectItem[] hourItems = 
			new SelectItem[] 
					{ 
			new SelectItem(0, "00"),
			new SelectItem(1, "1"),
			new SelectItem(2, "2"),
			new SelectItem(3, "3"),
			new SelectItem(4, "4"),
			new SelectItem(5, "5"),
			new SelectItem(6, "6"),
			new SelectItem(7, "7"),
			new SelectItem(8, "8"),
			new SelectItem(9, "9"),
			new SelectItem(10, "10"),
			new SelectItem(11, "11"),
			new SelectItem(12, "12"),
			new SelectItem(13, "13"),
			new SelectItem(14, "14"),
			new SelectItem(15, "15"),
			new SelectItem(16, "16"),
			new SelectItem(17, "17"),
			new SelectItem(18, "18"),
			new SelectItem(19, "19"),
			new SelectItem(20, "20"),
			new SelectItem(21, "21"),
			new SelectItem(22, "22"),
			new SelectItem(23, "23") 
					};
	//@formatter:on

	// private Logger log = Logger.getLogger(ScheduleBean.class);
	private Date loginDate = new Date();
	private SelectItem[] meridianItems = new SelectItem[] { new SelectItem("AM"),
			new SelectItem("PM"), };
	private SelectItem[] minuteItems = new SelectItem[] { new SelectItem(0, "00"),
			new SelectItem(15, "15"), new SelectItem(30, "30"), new SelectItem(45, "45") };
	private PerformanceSchedules performanceSchedules;

	private PerformanceUI performanceUi = new PerformanceUI(new Date(), null);

	private ScheduleModel scheduleModel;
	private ArrayList<Week> scheduleModelWeeks; // This is a hook for debuging
	private SeasonDaoRemote seasonDao;

	private ComplexEntityScheduleEvent selectedEvent;

	private Season selectedSeason;

	private TimeUI timeUi = new TimeUI(new Date(), new Date());
	private Long tmpSeason;
	//@formatter:off
	private SelectItem[] travelItems = 
			new SelectItem[] 
					{
			new SelectItem(TravelItems.NONE.toString()), 
			new SelectItem(TravelItems.HOTEL.toString()),
			new SelectItem(TravelItems.OVERNIGHT.toString()),
			new SelectItem(TravelItems.TRAVEL_DAY.toString()) 
					};
	//@formatter:on	

	private Boolean userInDaylightSavings;
	private VenueDaoRemote venueDao;
	@Getter
	@Setter
	private Schedule scheduleBinding;

	private boolean virtualEvent;
	@Getter
	@Setter
	private CalendarPrintController calendarPrintController = new CalendarPrintController();

	public TimeZone getServerTimezone()
	{
		//Hooked
		return TimeZone.getDefault();
	}

	public String getServerLocale()
	{
		return Locale.US.getDisplayName();
	}
	public void cancel(ActionEvent evt)
	{
		day = new Day();
		eventEntity = new Event();
	}

	public void confirmSeason(ActionEvent evt)
	{
		if(tmpSeason != null)
		{
			selectedSeason = getSeasonDao().getSeason(tmpSeason);
			scheduleModel = null;
			performanceSchedules = null;
		}
	}

	public void doSelectCalendar(ActionEvent evt)
	{
		currentSelection = Selection.CALENDAR;
	}

	public void doSelectCities(ActionEvent evt)
	{
		currentSelection = Selection.CITY;
	}

	public void doSelectPerformances(ActionEvent evt)
	{
		currentSelection = Selection.PERFORMANCES;
	}

	public ArrayList<SelectItem> getAllSeasons()
	{
		if(allSeasons == null)
		{
			ArrayList<Season> tmpSeasons = getSeasonDao().getSeasons();
			allSeasons = new ArrayList<SelectItem>();

			for(Season p : tmpSeasons)
				allSeasons.add(new SelectItem(p.getId(), p.getName()));

			if(tmpSeasons != null && tmpSeasons.size() > 0)
			{
				selectedSeason = Utility.getBean(SeasonBean.class).getSeason();
				tmpSeason = selectedSeason.getId();
			}

		}
		return allSeasons;
	}

	public Locale getBaseLocale()
	{
		return Locale.US;
	}

	public Day getDay()
	{
		return day;
	}

	public DayUI getDayUi()
	{
		return dayUi;
	}

	public EventMapping getEventEntity()
	{
		return eventEntity;
	}

	public SelectItem[] getHourItems()
	{
		return hourItems;
	}

	public SelectItem[] getMeridianItems()
	{
		return meridianItems;
	}

	public SelectItem[] getMinuteItems()
	{
		return minuteItems;
	}

	public TimeUI getNewTimeUiInstance(Date start)
	{
		Calendar plus2 = Calendar.getInstance();
		plus2.setTime(start);
		plus2.add(Calendar.HOUR, 2);
		TimeUI ret = new TimeUI(start, plus2.getTime());
		return ret;
	}

	public PerformanceSchedules getPerformanceSchedules()
	{
		if(performanceSchedules == null)
			performanceSchedules = new PerformanceSchedules();

		return performanceSchedules;
	}

	/**
	 * @param season
	 *          the season to search
	 * @return a list of performances for this season
	 */
	public ArrayList<Performance> getPerformancesForSeason(Season season)
	{
		return getCalendarDao().getPerformances(season);
	}

	/**
	 * @see com.danielbchapman.production.beans.CalendarDaoRemote#getPerformances(Season, Venue)
	 * @param seaon
	 *          the season
	 * @param venue
	 *          the venue
	 * @return
	 */
	public ArrayList<Performance> getPerformancesForSeasonAndVenue(Season season, Venue venue)
	{
		return getCalendarDao().getPerformances(season, venue);
	}

	public PerformanceUI getPerformanceUi()
	{
		if(performanceUi == null)
			performanceUi = new PerformanceUI(new Date(), null);
		return performanceUi;
	}

	public synchronized ScheduleModel getScheduleModel()
	{
		if(scheduleModel != null)
			return scheduleModel;
		
		System.out.println("Calling model " + Thread.currentThread().toString());
//		synchronized(ScheduleBean.this)
//		{
//			modelLock.lock();
			ScheduleModel prepare = new DefaultScheduleModel();	
			
			try
			{
				boolean companySecurity = Utility.getBean(LoginBean.class).isCompanyMember(); 
				scheduleModelWeeks = getCalendarDao().getAllWeeks(selectedSeason);

				for(Week w : scheduleModelWeeks)
				{
					ArrayList<Day> days = getCalendarDao().getActiveDaysForWeek(w);
					for(Day d : days)
					{
						//@formatter:off
						ComplexEntityScheduleEvent dayEvent =
								new ComplexEntityScheduleEvent(
										"CAST: " + d.getCastLocation() 
										+ " CREW: " + d.getCrewLocation(),
										d.getDate(), 
										d.getDate(),
										true
										);
						//@formatter:on					
						dayEvent.setBackingEntity(d);
						dayEvent.setStyleClass("eventDay");

						prepare.addEvent(dayEvent);
						ArrayList<EventMapping> events = getCalendarDao().getEventsAndPerformancesForDay(d);
						for(EventMapping e : events)
						{
							if(!companySecurity)
								if(!e.isPublicEvent() && !e.isPerformance())
									continue;

							ComplexEntityScheduleEvent se;
							if(e.getEnd() == null)
							{
								Calendar c = Calendar.getInstance();
								c.setTime(e.getStartDate());
								c.add(Calendar.HOUR, 2);
								se = new ComplexEntityScheduleEvent(e.getDescription(), e.getStartDate(), c.getTime(), false);							
							}
							else
								se = new ComplexEntityScheduleEvent(e.getDescription(), e.getStartDate(), e.getEndDate(), false);

							se.setBackingEntity(e);

							if(e instanceof Performance)
							{
								Performance p = (Performance) e;
								se.setStyleClass("eventPerformance");

								if(companySecurity)
								{
									ArrayList<Performance.PerformanceEvent> performanceEvents = p.getEventSequence();
									for(Performance.PerformanceEvent seq : performanceEvents)
									{
										ComplexEntityScheduleEvent sub = 
												new ComplexEntityScheduleEvent( seq.getDescription(), seq.getStartDate(), seq.getEndDate(), false);
										sub.setBackingEntity(seq);
										sub.setStyleClass("eventPerformanceLocked");
										prepare.addEvent(sub);
									}								
								}
//									else
//									{
//										ComplexEntityScheduleEvent perf = new ComplexEntityScheduleEvent(p.getDescription(), p.getStartDate(), p.getEndDate(), false);
//										perf.setStyleClass(styleClass)
//										scheduleModel.addEvent(;
//									}
							}
							else
							{
								if(e.isCast() && !e.isCrew())
									se.setStyleClass("eventScheduleCast");
								if(!e.isCast() && e.isCrew())
									se.setStyleClass("eventScheduleCrew");
								if(e.isCast() && e.isCrew())
									se.setStyleClass("eventSchedule");
								if(!e.isCast() && !e.isCrew())
									se.setStyleClass("eventSchedule");
							}

							prepare.addEvent(se);
						}
					}
				}
				scheduleModel = prepare;
			}
			catch (Exception e)
			{
//				modelLock.unlock();
				e.printStackTrace();
				throw new RuntimeException(e.getMessage(), e);
			}
//			finally
//			{
//				if(modelLock.isLocked())
//					modelLock.unlock();
//			}

			return getScheduleModel();
//		}
	}

	/**
	 * @return the scheduleModelWeeks
	 */
	public ArrayList<Week> getScheduleModelWeeks()
	{
		return scheduleModelWeeks;
	}

	public ScheduleEvent getSelectedEvent()
	{
		return selectedEvent;
	}

	public Season getSelectedSeason()
	{
		if(selectedSeason == null)
			selectedSeason = Utility.getBean(SeasonBean.class).getSeason();
		
		if(selectedSeason == null)
			selectedSeason = Utility.getBean(SeasonBean.class).getDefaultSeason();
		
		return selectedSeason;
	}

	public TimeUI getTimeUi()
	{
		return timeUi;
	}

	public Long getTmpSeason()
	{
		return tmpSeason;
	}

	public SelectItem[] getTravelItems()
	{
		return travelItems;
	}

	public boolean isDayEvent()
	{
		if(selectedEvent == null || selectedEvent.getBackingEntity() == null)
			return false;

		if(selectedEvent.isAllDay())
			return true;
		else
			return false;
	}

	public boolean isEvent()
	{
		if(selectedEvent == null || selectedEvent.getBackingEntity() == null)
			return false;

		if(selectedEvent.isAllDay())
			return false;
		else
			return true;
	}

	public boolean isExistingDay()
	{
		if(day == null || day.getId() == null)
			return false;
		return true;
	}

	public boolean isExistingEvent()
	{
		if(eventEntity == null || eventEntity.getId() == null)
			return false;
		return true;
	}

	public boolean isNewEvent()
	{
		if(selectedEvent == null)
			return false;

		if(selectedEvent.getBackingEntity() == null)
			return true;
		else
			return false;
	}

	public boolean isSelectedCalendar()
	{
		return Selection.CALENDAR == currentSelection;
	}

	public boolean isSelectedCities()
	{
		return Selection.CITY == currentSelection;
	}

	public boolean isSelectedPerformances()
	{
		return Selection.PERFORMANCES == currentSelection;
	}

	public boolean isVirtualEvent()
	{
		return virtualEvent;
	}

	public void onDateSelect(DateSelectEvent selectEvent)
	{
		virtualEvent = false;// Allow Selection
		Date selection = selectEvent.getDate();
		performanceUi = new PerformanceUI(selection, null);

		if(selectEvent != null)// Debugging what is passed as far as "date"
		{
			SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss Z");
			FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Information", "@Raw(" + df.format(selection) + ")");
			addMessage(error);
		}

		if(!getCalendarDao().dayExists(selection, selectedSeason))
		{
			selectedEvent = new ComplexEntityScheduleEvent("[NO DESCRIPTION]", selection, selection,
					true);
			Day unsavedDay = new Day();
			unsavedDay.setDate(selection);
			selectedEvent.setBackingEntity(unsavedDay);// This must be saved when confirmed.
			day = unsavedDay;
			dayUi = new DayUI(day);
		}
		else
		{
			selectedEvent = new ComplexEntityScheduleEvent("[NO DESCRIPTION]", selection, selection,
					false);
			Event unsavedEvent = new Event();

			// by definition this day exists
			Day backingDay = getCalendarDao().getOrCreateDay(selection, selectedSeason);
			unsavedEvent.setDay(backingDay);

			selectedEvent.setBackingEntity(unsavedEvent);
			eventEntity = unsavedEvent;

			Calendar tmp = Calendar.getInstance();
			tmp.setTime(selection);

			if(tmp.get(Calendar.HOUR_OF_DAY) == 0)
			{
				tmp.setTime(new Date());
				tmp.set(Calendar.MINUTE, 0);
			}

			timeUi = getNewTimeUiInstance(tmp.getTime());
			performanceUi.setCityId(backingDay.getCastLocation() == null ? null : backingDay
					.getCastLocation().getId());
		}
	}

	public void onEventSelect(ScheduleEntrySelectEvent selectEvent)
	{
		virtualEvent = false;
		selectedEvent = (ComplexEntityScheduleEvent) selectEvent.getScheduleEvent();

		if(selectedEvent == null)// Testing
		{
			FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to Load",
					"Unable to load the event, the model returned a null value. Please select your event again.");
			addMessage(error);
			return;
		}

		if(selectedEvent.getBackingEntity() instanceof Performance.PerformanceEvent)
		{
			virtualEvent = true;// Show the "virtualEvent dialog"
			return;
		}

		if(selectedEvent.getBackingEntity() instanceof Day)
		{
			day = (Day) selectedEvent.getBackingEntity();
			dayUi = new DayUI(day);
		}

		if(selectedEvent.getBackingEntity() instanceof Event)
		{
			eventEntity = (Event) selectedEvent.getBackingEntity();
			timeUi = new TimeUI(eventEntity.getStartDate(), eventEntity.getEndDate());
		}

		else
			if(selectedEvent.getBackingEntity() instanceof Performance)
			{
				Performance p = (Performance) selectedEvent.getBackingEntity();
				eventEntity = p;

				if(p.getId() == null)
					performanceUi = new PerformanceUI(p.getStartDate(), null);
				else
					performanceUi = new PerformanceUI(p.getStartDate(), p);

				performanceUi.setCrewCall(p.isCrewCall());
				performanceUi.setFightCall(p.isFightCall());
				performanceUi.setStrike(p.isStrike());
				performanceUi.setTalkback(p.isTalkback());
				/* Create a reference to the city */
				performanceUi.cityId = p.getDay().getCastLocation() == null ? null : p.getDay()
						.getCastLocation().getId();
				performanceUi.setVenueId(p.getVenue() == null ? null : p.getVenue().getId());
				performanceUi.getVenueItems();
			}
	}

	public void refreshModel(ActionEvent evt)
	{
		if(evt != null)
			scheduleModel = null;
	}

	public void removeDay(ActionEvent evt)
	{
		if(Utility.getBean(LoginBean.class).isScheduler())
		{
			getCalendarDao().removeItem(day);
			scheduleModel = null;
			day = new Day();
			eventEntity = new Event();				
		}
	}

	public void removeEvent(ActionEvent evt)
	{
		if(Utility.getBean(LoginBean.class).isScheduler())
		{
			getCalendarDao().removeItem(eventEntity);
			scheduleModel = null;
			day = new Day();
			eventEntity = new Event();				
		}
	}

	public void removePerformance(ActionEvent evt)
	{
		if(Utility.getBean(LoginBean.class).isScheduler())
		{
			getCalendarDao().removeItem(eventEntity);
			scheduleModel = null;
			day = new Day();
			eventEntity = new Event();			
		}
	}

	public void saveDay(ActionEvent evt)
	{
		if(evt != null && day != null && day.getDate() != null && Utility.getBean(LoginBean.class).isScheduler())
		{
			Date date = selectedEvent.getStartDate();
			Week week = getCalendarDao().getOrCreateWeek(date, selectedSeason);

			Day ref = getCalendarDao().getOrCreateDay(date, week);

			/*
			 * Duplicates were being created, we should use a DTO in this case, but since the entity is
			 * the DTO. . .
			 */
			LocationBean location = (LocationBean) Utility.getBean("locationBean");

			ref.setCastLocation(location.getCity(dayUi.castCity));
			ref.setCrewLocation(location.getCity(dayUi.crewCity));
			ref.setCastHotel(location.getHotel(dayUi.castHotel));
			ref.setCrewHotel(location.getHotel(dayUi.crewHotel));
			ref.setCastTravel(day.getCastTravel());
			ref.setCrewTravel(day.getCrewTravel());
			ref.setLabel(day.getLabel());
			ref.setMilageInformation(day.getMilageInformation());
			ref.setNotes(day.getNotes());
			ref.setTheaterInformation(day.getTheaterInformation());

			/* The spaghetti is strong here */
			if(!TravelItems.HOTEL.toString().equals(ref.getCastTravel()))
				ref.setCastHotel(null);

			if(!TravelItems.HOTEL.toString().equals(ref.getCrewTravel()))
				ref.setCrewHotel(null);

			getCalendarDao().saveDay(ref);

			scheduleModel = null;
			day = new Day();
			dayUi = new DayUI(day);
			eventEntity = new Event();
		}
	}

	public void saveEvent(ActionEvent evt)
	{
		if(eventEntity != null && Utility.getBean(LoginBean.class).isScheduler())
		{
			// Date date = selectedEvent.getStartDate();//BUG - > Use the hard reference
			EventMapping tmpEvent = (EventMapping) selectedEvent.getBackingEntity();
			Day assignedDay = tmpEvent.getDay();
			Date date = assignedDay.getDate();// == null? selectedEvent.getStartDate() :
			// assignedDay.getDate();

			if(date == null)// (!calendarDao.dayExists(date, selectedProduction))
			{
				FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to Save Event",
						"There is no day registered for '" + date
						+ "' please create the day, then save an event.");
				addMessage(error);
				return;
			}

			// Week week = calendarDao.getOrCreateWeek(date, selectedProduction);
			// Day d = calendarDao.getOrCreateDay(date, week);

			eventEntity.setDay(assignedDay);
			eventEntity.setStart(timeUi.getStartTime(assignedDay.getDate()));
			Date endTime = timeUi.getEndTime(assignedDay.getDate());

			if(endTime.before(eventEntity.getStartDate()))
			{
				Calendar tmpCal = Calendar.getInstance();
				tmpCal.setTime(eventEntity.getStartDate());

				if(tmpCal.get(Calendar.HOUR) > 22)
					tmpCal.add(Calendar.HOUR, 2);
				else
				{
					tmpCal.set(Calendar.HOUR_OF_DAY, 23);
					tmpCal.set(Calendar.MINUTE, 45);
				}

				endTime = tmpCal.getTime();
			}

			eventEntity.setEnd(endTime);
			selectedEvent.setEndDate(endTime);
			eventEntity.setDay(assignedDay);
			getCalendarDao().saveEvent(eventEntity);
			Utility.raiseInfo("Event Updated", eventEntity.getDescription());
		}

		scheduleModel = null;
		scheduleModel = getScheduleModel();
		day = new Day();
		eventEntity = new Event();
	}

	public void savePerformance(ActionEvent evt)
	{
		if(eventEntity != null && Utility.getBean(LoginBean.class).isScheduler())
		{
			Performance tmpPerf;
			PerformanceSchedule schedule = getCalendarDao().getPerformanceSchedule(
					getPerformanceSchedules().getEditableId());

			if(selectedEvent.getBackingEntity() instanceof Performance)
				tmpPerf = (Performance) selectedEvent.getBackingEntity();
			else
			{
				tmpPerf = new Performance();
				Event tmpEvent = (Event) selectedEvent.getBackingEntity();
				tmpPerf.setDay(tmpEvent.getDay());
				tmpPerf.setCast(true);
				tmpPerf.setCrew(true);
				tmpPerf.setDescription(schedule.getName());
				tmpPerf.setPerformanceSchedule(schedule);
			}

			Day assignedDay = tmpPerf.getDay();

			if(assignedDay.getDate() == null)// (!calendarDao.dayExists(date, selectedProduction))
			{
				FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Failed to Save Event",
						"There is no day registered for '" + assignedDay
						+ "' please create the day, then save an event.");
				addMessage(error);
				return;
			}
			Venue venue = performanceUi.getVenueId() == null ? null : getVenueDao().getVenue(
					performanceUi.getVenueId());

			tmpPerf.setDay(assignedDay);
			tmpPerf.setStart(performanceUi.getStartTime(assignedDay.getDate()));
			tmpPerf.setCrewCall(performanceUi.isCrewCall());
			tmpPerf.setFightCall(performanceUi.isFightCall());
			tmpPerf.setTalkback(performanceUi.isTalkback());
			tmpPerf.setStrike(performanceUi.isStrike());
			tmpPerf.setVenue(venue);

			Calendar endTimeCal = Calendar.getInstance();
			endTimeCal.setTime(tmpPerf.getStart());
			endTimeCal.add(Calendar.MINUTE, schedule.getPerformanceLength());
			tmpPerf.setEnd(endTimeCal.getTime());
			tmpPerf.setDay(assignedDay);

			getCalendarDao().savePerformance(tmpPerf);
			Utility.raiseInfo("Performance Saved", tmpPerf.getDescription());
		}

		scheduleModel = null;
		scheduleModel = getScheduleModel();
		day = new Day();
		eventEntity = new Event();
	}

	public void setDay(Day day)
	{
		this.day = day;
	}

	public void setEventEntity(Event event)
	{
		this.eventEntity = event;
	}

	public void setSelectedEvent(ScheduleEvent selectedEvent)
	{
		this.selectedEvent = (ComplexEntityScheduleEvent) selectedEvent;
	}

	public void setSelectedSeason(Season selectedProduction)
	{
		this.selectedSeason = selectedProduction;
	}

	public void setTmpSeason(Long tmpSeason)
	{
		this.tmpSeason = tmpSeason;
	}

	private void addMessage(FacesMessage message)
	{
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	private CalendarDaoRemote getCalendarDao()
	{
		if(calendarDao == null)
			calendarDao = Utility.getObjectFromContext(CalendarDaoRemote.class,
					Utility.Namespace.PRODUCTION);

		return calendarDao;
	}

	private TimeZone getClientTimeZone()
	{
		LoginBean login = Utility.getBean(LoginBean.class);
		TimeZone zone = login.getTimeZone();
		return zone; // client zone;
	}

	private SeasonDaoRemote getSeasonDao()
	{
		if(seasonDao == null)
			seasonDao = Utility.getObjectFromContext(SeasonDaoRemote.class, Utility.Namespace.PRODUCTION);

		return seasonDao;
	}

	private VenueDaoRemote getVenueDao()
	{
		if(venueDao == null)
			venueDao = Utility.getObjectFromContext(VenueDaoRemote.class, Utility.Namespace.PRODUCTION);

		return venueDao;
	}

	@Data
	@EqualsAndHashCode(callSuper = false)
	public class CalendarPrintController extends AbstractPrintController
	{
		private Date startDate;
		private Date endDate;
		private boolean printAll = true;
		private boolean cast = true;
		private boolean crew = true;
		private boolean details = true;
		
		public CalendarPrintController()
		{
			super("schedules", "sub");
		}

		private static final long serialVersionUID = 1L;

		@Override
		protected Collection<Week> getData()
		{
			ArrayList<Week> weeks = null;
			Season season = Utility.getBean(ScheduleBean.class).getSelectedSeason();

			if(printAll || startDate == null || endDate == null)
				weeks = getCalendarDao().getAllWeeks(season);
			else
				weeks = getCalendarDao().getWeeksInRange(startDate, endDate, season);
			
			return weeks;
		}

		@Override
		protected Map<String, Object> getParameters()
		{
			HashMap<String, Object> params = new HashMap<String, Object>();

			File root = new File(Utility.getBean(AdministrationBean.class).getReportingDocumentRoot());
			//@formatter:off
			String path = new File(
						root.getAbsoluteFile() 
						+ File.separator 
						+ "schedules"
						+ File.separator
					).getAbsolutePath() + File.separator;
			//@formatter:on
			params.put("FILE_PATH", path);
			params.put("SUBREPORT_DIR", path);
			params.put("PRINT_CAST", new Boolean(cast));
			params.put("PRINT_CREW", new Boolean(crew));
			params.put("PRINT_DETAILS", new Boolean(details));
			
			return params;
		}

		@Override
		protected String getReportName()
		{
			SimpleDateFormat df = new SimpleDateFormat("MM-dd-yy");
			return "Weekly-" + df.format(new Date()) ;
		}
		
	}
	public class ComplexEntityScheduleEvent extends DefaultScheduleEvent implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private Object backingEntity;

		public ComplexEntityScheduleEvent(String title, Date start, Date end, boolean allDay)
		{
			super(title, start, end, allDay);
		}

		public Object getBackingEntity()
		{
			return backingEntity;
		}

		public void setBackingEntity(Object backingEntity)
		{
			this.backingEntity = backingEntity;
		}
	}

	@Data
	public class DayUI implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private Long castCity;
		private Long castHotel;
		private ArrayList<SelectItem> castHotels;
		private Long crewCity;
		private Long crewHotel;
		private ArrayList<SelectItem> crewHotels;
		private boolean showCastHotel;
		private boolean showCrewHotel;

		private UIComponent bindingCrewHotels;
		private UIComponent bindingCastHotels;

		public DayUI(Day day)
		{
			if(day != null)
			{
				if(day.getCastLocation() != null)
					castCity = day.getCastLocation().getId();

				if(day.getCrewLocation() != null)
					crewCity = day.getCrewLocation().getId();

				if(TravelItems.HOTEL.toString().equals(day.getCastTravel()))
					showCastHotel = true;
				else
					showCastHotel = false;

				if(TravelItems.HOTEL.toString().equals(day.getCrewTravel()))
					showCrewHotel = true;
				else
					showCrewHotel = false;

				castHotel = day.getCastHotel() == null ? null : day.getCastHotel().getId();
				crewHotel = day.getCrewHotel() == null ? null : day.getCrewHotel().getId();
			}

		}

		public ArrayList<SelectItem> getCastHotels()
		{
			if(castHotels == null)
				if(castCity != null)
				{
					castHotels = new ArrayList<SelectItem>();
					ArrayList<HotelWrapper> tmpHotels = Utility.getBean(LocationBean.class).getHotelsForCity(
							castCity);
					for(HotelWrapper h : tmpHotels)
						castHotels.add(new SelectItem(h.getHotel().getId(), h.getHotel().getName()));
				}
				else
					castHotels = new ArrayList<SelectItem>();

			return castHotels;
		}

		public ArrayList<SelectItem> getCrewHotels()
		{
			if(crewHotels == null)
				if(crewCity != null)
				{
					crewHotels = new ArrayList<SelectItem>();
					ArrayList<HotelWrapper> tmpHotels = Utility.getBean(LocationBean.class).getHotelsForCity(
							crewCity);
					for(HotelWrapper h : tmpHotels)
						crewHotels.add(new SelectItem(h.getHotel().getId(), h.getHotel().getName()));
				}
				else
					crewHotels = new ArrayList<SelectItem>();

			return crewHotels;
		}

		public void onSelectShowCastHotels()
		{
			if(TravelItems.HOTEL.toString().equals(getDay().getCastTravel()))
				showCastHotel = true;
			else
				showCastHotel = false;

			castHotels = null;
		}

		public void onSelectShowCrewHotels()
		{
			if(TravelItems.HOTEL.toString().equals(getDay().getCrewTravel()))
				showCrewHotel = true;
			else
				showCrewHotel = false;

			crewHotels = null;
		}

		public void selectCastCity(ValueChangeEvent evt)
		{
			castCity = (Long) evt.getNewValue();
			castHotels = null;
		}

		public void selectCrewCity(ValueChangeEvent evt)
		{
			crewCity = (Long) evt.getNewValue();
			crewHotels = null;
		}
	}

	public class PerformanceSchedules implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private Long editableId;
		private ArrayList<SelectItem> performanceScheduleItems;
		private PerformanceSchedule wrapper;

		public void createPerformance(ActionEvent evt)
		{
			if(!Utility.getBean(LoginBean.class).isScheduler())
			{
				Utility.raiseWarning("Access Denied",
						"You lack the proper permissions");
				return;
			}

			getCalendarDao().savePerformanceSchedule(wrapper);
			Utility.raiseInfo("Performance Updated",
					"A performance schedule was created for " + wrapper.getName());
			wrapper = null;
			performanceScheduleItems = null;
		}

		public Long getEditableId()
		{
			return editableId;
		}

		public ArrayList<SelectItem> getPerforanceScheduleItems()
		{
			if(performanceScheduleItems == null)
			{
				ArrayList<PerformanceSchedule> tmpSchedules = getCalendarDao()
						.getPerformanceSchedulesForSeason(selectedSeason);
				performanceScheduleItems = new ArrayList<SelectItem>();

				for(PerformanceSchedule s : tmpSchedules)
					performanceScheduleItems.add(new SelectItem(s.getId(), s.getName()));
			}

			return performanceScheduleItems;
		}

		public PerformanceSchedule getWrapper()
		{
			if(wrapper == null)
				wrapper = new PerformanceSchedule(true);

			return wrapper;
		}

		public void setEditableId(Long editableId)
		{
			this.editableId = editableId;
		}

		public void setWrapper(PerformanceSchedule wrapper)
		{
			this.wrapper = wrapper;
		}
	}

	public class PerformanceUI extends TimeUI implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private Long cityId;
		private boolean crewCall;
		private boolean fightCall;
		private String productionName;
		private boolean strike;
		private boolean talkback;
		private Long venueId;
		private ArrayList<SelectItem> venueItems;
		private PerformanceAdvance advance;
		private Performance performance;

		public PerformanceUI(Date start, Performance performance)
		{
			super(start, start);
			strike = true;
			fightCall = true;
			crewCall = true;
			this.performance = performance;
			this.advance = performance == null ? null : performance.getAdvance() == null ? null
					: performance.getAdvance().getId() == null ? null : performance.getAdvance();
		}

		/**
		 * Create an advance object that represents a current set of information that has been sent to a
		 * venue.
		 * 
		 * @param evt
		 */
		public void doCreateAdvance(ActionEvent evt)
		{
			if(performance.getVenue() == null)
			{
				Utility.raiseWarning("Unable to Advance",
						"You must have a venue selected to create an advance.");
				return;
			}

			if(!Utility.getBean(LoginBean.class).isScheduler())
			{
				Utility.raiseWarning("Access Denied",
						"You lack the proper permissions");
				return;
			}

			getCalendarDao().createPerformanceAdvance(performance);
			Utility.raiseInfo("Advance Created", "Advances are updated via the Venue module");
			refreshModel(evt);
		}

		/**
		 * Save the advance to the system--this shouldn't really be needed.
		 * 
		 * @param evt
		 */
		public void doSaveAdvance(ActionEvent evt)
		{
			if(advance == null)
			{
				Utility.raiseError("Null Pointer", "The performance advance is null, can not save");
				return;
			}

			if(!Utility.getBean(LoginBean.class).isScheduler())
			{
				Utility.raiseWarning("Access Denied",
						"You lack the proper permissions");
				return;
			}

			getCalendarDao().savePerformanceAdvance(advance);
			Utility.raiseInfo("Advance Updated", "");
		}

		public Long getCityId()
		{
			return cityId;
		}

		public String getProductionName()
		{
			return productionName;
		}

		public Long getVenueId()
		{
			return venueId;
		}

		public ArrayList<SelectItem> getVenueItems()
		{
			if(venueItems == null)
			{

				if(cityId == null)
				{
					Utility.raiseWarning("No City Selected",
							"The cast has not been assigned to a city, venues can not be assigned");
					return new ArrayList<SelectItem>();
				}

				venueItems = new ArrayList<SelectItem>();
				ArrayList<Venue> tmpVenues = getVenueDao().getVenuesForCity(
						Utility.getBean(LocationBean.class).getCity(cityId));

				boolean found = false;

				for(Venue v : tmpVenues)
				{
					if(v.getId().equals(venueId))
						found = true;

					venueItems.add(new SelectItem(v.getId(), v.getName()));
				}

				if(!found && venueId != null)
				{
					Venue tmpVenue = getVenueDao().getVenue(venueId);
					venueItems.add(new SelectItem(venueId, tmpVenue.getName()));
				}

				if(venueItems.size() == 0)
				{
					venueItems = null;
					return new ArrayList<SelectItem>();
				}

			}
			return venueItems;
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
		 * @return the showAdvance
		 */
		public boolean isShowAdvance()
		{
			return advance == null && performance != null;
		}

		public boolean isStrike()
		{
			return strike;
		}

		public boolean isTalkback()
		{
			return talkback;
		}

		public void setCityId(Long cityId)
		{
			this.cityId = cityId;
		}

		public void setCrewCall(boolean crewCall)
		{
			this.crewCall = crewCall;
		}

		public void setFightCall(boolean fightCall)
		{
			this.fightCall = fightCall;
		}

		public void setProductionName(String productionName)
		{
			this.productionName = productionName;
		}

		public void setStrike(boolean strike)
		{
			this.strike = strike;
		}

		public void setTalkback(boolean talkback)
		{
			this.talkback = talkback;
		}

		public void setVenueId(Long venueId)
		{
			this.venueId = venueId;
		}
	}

	public enum Selection
	{
		CALENDAR("schedule", "Calendar"), CITY("cities", "City Management"), PERFORMANCES("advance",
				"Performance and Advance");

		public static Selection parseValue(String sub)
		{
			if(sub == null)
				return Selection.CITY;

			for(Selection s : Selection.values())
				if(s.toString().equals(sub.trim()))
					return s;

			return Selection.CITY;
		}

		String label;
		String value;

		Selection(String value, String label)
		{
			this.value = value;
			this.label = label;
		}

		public String getLabel()
		{
			return label;
		}

		@Override
		public String toString()
		{
			return value;
		}
	}

	public class TimeUI implements Serializable
	{
		private static final long serialVersionUID = 3L;
		final static String AM = "AM";
		final static String PM = "PM";
		private Date end;
		private int endHour;
		private String endMeridian;
		private int endMinute;
		private Date start;
		private int startHour;
		private String startMeridian;

		private int startMinute;

		public TimeUI(Date start, Date end)
		{
			this.start = start;
			if(end == null)
			{

				Calendar tmp = Calendar.getInstance();
				tmp.setTime(start);
				tmp.add(Calendar.HOUR_OF_DAY, 2);
				end = tmp.getTime();
			}

			this.end = end;
			Calendar startC = Calendar.getInstance();
			Calendar endC = Calendar.getInstance();
			startC.setTime(start);
			endC.setTime(end);
			startHour = startC.get(Calendar.HOUR_OF_DAY);
			startMinute = startC.get(Calendar.MINUTE);
			endHour = endC.get(Calendar.HOUR_OF_DAY);
			endMinute = endC.get(Calendar.MINUTE);
			startMeridian = startC.get(Calendar.AM_PM) == Calendar.AM ? AM : PM;
			endMeridian = endC.get(Calendar.AM_PM) == Calendar.AM ? AM : PM;

		}

		public int getEndHour()
		{
			return endHour;
		}

		public String getEndMeridian()
		{
			return endMeridian;
		}

		public int getEndMinute()
		{
			return endMinute;
		}

		public Date getEndTime(Date daysDate)
		{
			Calendar time = Calendar.getInstance();
			time.setTime(daysDate);
			time.set(Calendar.HOUR_OF_DAY, endHour);
			time.set(Calendar.MINUTE, endMinute);
			// time.set(Calendar.AM_PM, "PM".equals(endMeridian)? Calendar.PM : Calendar.AM);
			return time.getTime();
		}

		public int getStartHour()
		{
			return startHour;
		}

		public String getStartMeridian()
		{
			return startMeridian;
		}

		public int getStartMinute()
		{
			return startMinute;
		}

		public Date getStartTime(Date daysDate)
		{
			Calendar time = Calendar.getInstance();
			time.setTime(daysDate);
			time.set(Calendar.HOUR_OF_DAY, startHour);
			time.set(Calendar.MINUTE, startMinute);
			// time.set(Calendar.AM_PM, "PM".equals(startMeridian) ? Calendar.PM : Calendar.AM);
			return time.getTime();
		}

		public void setEndHour(int endHour)
		{
			this.endHour = endHour;
		}

		public void setEndMeridian(String endMeridian)
		{
			this.endMeridian = endMeridian;
		}

		public void setEndMinute(int endMinute)
		{
			this.endMinute = endMinute;
		}

		public void setStartHour(int startHour)
		{
			this.startHour = startHour;
		}

		public void setStartMeridian(String startMeridian)
		{
			this.startMeridian = startMeridian;
		}

		public void setStartMinute(int startMinute)
		{
			this.startMinute = startMinute;
		}

		@Override
		public String toString()
		{
			StringBuilder b = new StringBuilder();

			b.append("Start (");
			b.append(start);
			b.append(") @ ");
			b.append(startHour);
			b.append(":");
			b.append(startMinute);
			b.append(" ");
			b.append(startMeridian);

			b.append(" End (");
			b.append(end);
			b.append(") @ ");
			b.append(endHour);
			b.append(":");
			b.append(endMinute);
			b.append(" ");
			b.append(endMeridian);

			return b.toString();
		}

	}

	public enum TravelItems
	{
		HOTEL("Hotel"), NONE("N/A"), OVERNIGHT("Overnight"), TRAVEL_DAY("Travel Day");

		String s;

		TravelItems(String s)
		{
			this.s = s;
		}

		@Override
		public String toString()
		{
			return s;
		}
	}
}
