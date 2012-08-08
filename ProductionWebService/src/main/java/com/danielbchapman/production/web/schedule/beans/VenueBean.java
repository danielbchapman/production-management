package com.danielbchapman.production.web.schedule.beans;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

import com.danielbchapman.production.AbstractPrintController;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.CalendarDaoRemote;
import com.danielbchapman.production.beans.OptionsDaoRemote;
import com.danielbchapman.production.beans.VenueDaoRemote;
import com.danielbchapman.production.dto.AdvanceSheetDto;
import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.Day;
import com.danielbchapman.production.entity.Performance;
import com.danielbchapman.production.entity.PerformanceAdvance;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Venue;
import com.danielbchapman.production.entity.VenueLog;

public class VenueBean implements Serializable
{
	private static final long serialVersionUID = 3L;

	private FileModel attachments;

	private Long cityId;

	private String department;

	private SelectItem[] departments = new SelectItem[] {
			new SelectItem(VenueDaoRemote.GENERAL_DOCUMENTS),
			new SelectItem(VenueDaoRemote.ELECTRICS_DOCUMENTS),
			new SelectItem(VenueDaoRemote.SCENIC_DOCUMENTS),
			new SelectItem(VenueDaoRemote.SOUND_DOCUMENTS),
			new SelectItem(VenueDaoRemote.WARDROBE_DOCUMENTS) };
	private SelectItem[] hours = new SelectItem[] { new SelectItem("01"), new SelectItem("02"),
			new SelectItem("03"), new SelectItem("04"), new SelectItem("05"), new SelectItem("06"),
			new SelectItem("07"), new SelectItem("08"), new SelectItem("09"), new SelectItem("10"),
			new SelectItem("11"), new SelectItem("12"), new SelectItem("13"), new SelectItem("14"),
			new SelectItem("15"), new SelectItem("16"), new SelectItem("17"), new SelectItem("18"),
			new SelectItem("19"), new SelectItem("20"), new SelectItem("21"), new SelectItem("22"),
			new SelectItem("23") };
	private String logEntry;
	private String logUser;
	private SelectItem[] minutes = new SelectItem[] { new SelectItem("00"), new SelectItem("15"),
			new SelectItem("30"), new SelectItem("45") };

	private NewVenue newVenue = new NewVenue();

	private OptionsDaoRemote optionsDao;
	private SelectItem[] productions = new SelectItem[] { new SelectItem("Romeo & Juliet"),
			new SelectItem("Comedy of Errors"), new SelectItem("Both") };
	private Long selectedId;
	private Venue venue = new Venue();
	private VenueDaoRemote venueDao;
	private CalendarDaoRemote calendarDao;
	private String venueHours;
	private ArrayList<SelectItem> venueItems;
	private ArrayList<VenueLog> venueLogs;
	private String venueMinutes;
	private ArrayList<SelectItem> venues;
	private String venueName;
	private boolean venueSelect = false;
	private Advance advance;

	public void addVenue(ActionEvent evt)
	{
		if(evt != null)
		{
			Venue venue = newVenue.getNewVenue();
			getVenueDao().saveVenue(venue);

			newVenue = new NewVenue();
			refreshVenues(evt);
		}
	}

	public String formatDirectoryName(Venue venue)
	{
		return venue.getName() + " [" + venue.getId() + "]";
	}

	/**
	 * @return the advance
	 */
	public Advance getAdvance()
	{
		if(advance == null)
		{
			if(venue == null)
				return new Advance(null);

			advance = new Advance(venue);
		}
		return advance;
	}

	public String getAmp()
	{
		return "&";
	}

	public FileModel getAttachments()
	{
		if(venue == null)
			attachments = new FileModel(null);// blank

		if(attachments == null && venue != null && venue.getId() != null)
			attachments = new FileModel(venue.getId());

		return attachments;
	}

	public Long getCityId()
	{
		return cityId;
	}

	public String getDepartment()
	{
		return department;
	}

	public SelectItem[] getDepartments()
	{
		return departments;
	}

	public SelectItem[] getHours()
	{
		return hours;
	}

	public String getLogEntry()
	{
		return logEntry;
	}

	public ArrayList<VenueLog> getLogs()
	{
		if(venue == null)
			return new ArrayList<VenueLog>();

		if(venue != null && venueLogs == null)
			venueLogs = getVenueDao().getLogEntries(venue);

		return venueLogs;
	}

	/**
	 * @return the logUser
	 */
	public String getLogUser()
	{
		if(logUser == null)
			logUser = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
		return logUser;
	}

	public SelectItem[] getMinutes()
	{
		return minutes;
	}

	public NewVenue getNewVenue()
	{
		return newVenue;
	}

	public File getOrCreateVenueDocumentRoot(Venue venue)
	{
		// READ ALL FILES
		String rootString = getOptionsDao().getOptions().getVenueDocumentRoot();
		if(rootString == null)
			throw new RuntimeException("[SETTINGS ARE INVALID, THE VENUE DOCUMENT IS NULL]");

		File root = new File(rootString);
		if(!root.exists())
			root.mkdirs();

		HashMap<Long, File> fileMap = new HashMap<Long, File>();

		File[] rootFiles = root.listFiles();
		for(File f : rootFiles)
			fileMap.put(parseFile(f), f);

		File modelRoot = fileMap.get(venue.getId());
		if(modelRoot == null)
		{
			modelRoot = new File(rootString + formatDirectoryName(venue) + File.separator);
			if(!modelRoot.exists())
				modelRoot.mkdirs();
		}

		return modelRoot;
	}

	public SelectItem[] getProductions()
	{
		return productions;
	}

	public Long getSelectedId()
	{
		return selectedId;
	}

	public Venue getVenue()
	{
		return venue;
	}

	public String getVenueHours()
	{
		return venueHours;
	}

	public ArrayList<SelectItem> getVenueItems()
	{
		if(venueItems == null)
		{
			venueItems = new ArrayList<SelectItem>();
			ArrayList<Venue> allVenues = getVenueDao().getAllVenues();
			for(Venue v : allVenues)
				venueItems.add(new SelectItem(v, v.getName()));
		}
		return venueItems;
	}

	public String getVenueMinutes()
	{
		return venueMinutes;
	}

	/**
	 * @return the venueName
	 */
	public String getVenueName()
	{
		return venueName;
	}

	public ArrayList<SelectItem> getVenues()
	{
		if(venues == null)
		{
			venues = new ArrayList<SelectItem>();
			ArrayList<Venue> allVenues = getVenueDao().getAllVenues();

			//@formatter:off
			Collections.sort(
					allVenues, 
					new Comparator<Venue>()
					{
						@Override
						public int compare(Venue o1, Venue o2)
						{
							return Venue.compareVenueByStateCityName(o1, o2);
						}
						
					}
			);
			
			for(Venue v : allVenues)
				venues.add(new SelectItem(v.getId(), v.getDetailedDescription()));
			//@formatter:on
		}
		return venues;
	}

	public void handleFileUpload(FileUploadEvent evt)
	{
		System.out.println("Handling Upload: " + evt.getFile());
		UploadedFile upload = evt.getFile();
		if(department == null || department.trim().length() == 0)
			department = VenueDaoRemote.GENERAL_DOCUMENTS;

		File toWrite = new File(getAttachments().getModelRoot() + File.separator + department
				+ File.separator + upload.getFileName());
		toWrite.mkdirs();// FIXME This should have some _heavy_ duty filtering.

		if(toWrite.exists())
			toWrite.delete();

		FileOutputStream out = null;
		try
		{
			toWrite.createNewFile();
			out = new FileOutputStream(toWrite);
			out.write(upload.getContents());
			out.close();
			attachments = null;
			FacesMessage notice = new FacesMessage(FacesMessage.SEVERITY_INFO, "File Uploaded",
					"The file " + upload.getFileName() + " has been uploaded.");
			FacesContext.getCurrentInstance().addMessage(null, notice);
		}
		catch(IOException e)
		{
			FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Unable to save file",
					e.getMessage());
			FacesContext.getCurrentInstance().addMessage(null, error);
			e.printStackTrace();
		}
		finally
		{
			Utility.close(out);
		}
	}

	public boolean isCanHasLogs()
	{
		if(getLogs().size() == 0)
			return false;

		return true;
	}

	public boolean isCanHasVenue()
	{
		return venueSelect;
	}

	public Long parseFile(File file)
	{
		String name = file.getName();
		int start = name.indexOf('[');
		int end = name.indexOf(']');

		try
		{
			String parse = name.substring(start + 1, end);
			return Long.valueOf(parse);
		}
		catch(Exception e)
		{
			return Long.valueOf(name); // try a simple long
		}
	}

	public void refreshVenues(ActionEvent evt)// callable via null
	{
		venue = new Venue();
		venueSelect = false;
		venues = null;
		venueItems = null;
		venueLogs = null;
		attachments = null;
		advance = null;
	}

	public void saveNotes(ActionEvent evt)
	{
		if(venue != null)
		{
			getVenueDao().addLogEntry(logEntry, venue, logUser);
			venueLogs = null;
			logEntry = null;
			logUser = null;
			Utility.raiseInfo("Log Saved", "The log has been saved to the database.");
		}
	}

	public void saveVenue(ActionEvent evt)
	{
		if(cityId != null)
			venue.setCity(Utility.getBean(LocationBean.class).getCity(cityId));
		else
			venue.setCity(null);

		/* Rename files accordingly */
		if(venue.getId() != null)
			if(!venue.getName().equals(venueName))
			{
				File venueFileRoot = getOrCreateVenueDocumentRoot(venue);
				venue.setName(venueName);
				String newPath = venueFileRoot.getParent() + File.separator + formatDirectoryName(venue);
				boolean rename = venueFileRoot.renameTo(new File(newPath));
				if(!rename)
					throw new RuntimeException(
							"IO Error, could not rename the venue files. Please contact the adminstrator");
			}

		getVenueDao().saveVenue(venue);
		Utility.raiseInfo("Venue Updated", venue.getName() + " changes have been saved.");
		refreshVenues(null);
	}

	public void selectVenue(ActionEvent evt)
	{
		if(evt != null && selectedId != null)
		{
			venue = getVenueDao().getVenue(selectedId);
			venueLogs = null;
			attachments = null;
			venueSelect = true;
			cityId = venue.getCity() == null ? null : venue.getCity().getId();
			advance = new Advance(venue);
			venueName = venue.getName();
		}
	}

	public void setCityId(Long cityId)
	{
		this.cityId = cityId;
	}

	public void setDepartment(String department)
	{
		this.department = department;
	}

	public void setLogEntry(String logEntry)
	{
		this.logEntry = logEntry;
	}

	/**
	 * @param logUser
	 *          the logUser to set
	 */
	public void setLogUser(String logUser)
	{
		this.logUser = logUser;
	}

	public void setSelectedId(Long selectedId)
	{
		this.selectedId = selectedId;
	}

	public void setVenueHours(String venueHours)
	{
		this.venueHours = venueHours;
	}

	public void setVenueMinutes(String venueMinutes)
	{
		this.venueMinutes = venueMinutes;
	}

	/**
	 * @param venueName
	 *          the venueName to set
	 */
	public void setVenueName(String venueName)
	{
		this.venueName = venueName;
	}

	public void setVenues(ArrayList<SelectItem> venues)
	{
		this.venues = venues;
	}

	public void updateFileDepartment()
	{
		// Void listener for debug hooks.
		System.out.println(department);
	}

	private CalendarDaoRemote getCalendarDao()
	{
		if(calendarDao == null)
			calendarDao = Utility.getObjectFromContext(CalendarDaoRemote.class,
					Utility.Namespace.PRODUCTION);
		return calendarDao;
	}

	private OptionsDaoRemote getOptionsDao()
	{
		if(optionsDao == null)
			optionsDao = Utility.getObjectFromContext(OptionsDaoRemote.class,
					Utility.Namespace.PRODUCTION);
		return optionsDao;
	}

	private VenueDaoRemote getVenueDao()
	{
		if(venueDao == null)
			venueDao = Utility.getObjectFromContext(VenueDaoRemote.class, Utility.Namespace.PRODUCTION);
		return venueDao;
	}

	private void mkDirs(File f)
	{
		if(!f.exists())
			f.mkdirs();
	}

	public class Advance implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private ArrayList<AdvanceWrapper> advances;

		public Advance(Venue venue)
		{
			ArrayList<PerformanceAdvance> tmpAdvances = null;
			if(venue == null)
				tmpAdvances = new ArrayList<PerformanceAdvance>();
			else
				tmpAdvances = getCalendarDao().getPerformanceAdvances(venue);

			advances = new ArrayList<AdvanceWrapper>();
			for(PerformanceAdvance adv : tmpAdvances)
				advances.add(new AdvanceWrapper(adv));
		}

		/**
		 * @return the advances
		 */
		public ArrayList<AdvanceWrapper> getAdvances()
		{
			return advances;
		}

		public class AdvanceWrapper
		{
			private PerformanceAdvance performanceAdvance;
			private Long performanceId;
			private AbstractPrintController printController;

			public AdvanceWrapper(PerformanceAdvance performanceAdvance)
			{
				this.performanceAdvance = performanceAdvance;
			}

			public void doArchiveAdvance(ActionEvent evt)
			{
				getCalendarDao().archiveAdvance(performanceAdvance, venue);
				Utility.raiseInfo("Advance Archived",
						"Please check the venue logs for this advance information.");
				refreshVenues(evt);
			}

			public void doMarkCompleted(ActionEvent evt)
			{
				performanceAdvance.setComplete(true);
				getCalendarDao().savePerformanceAdvance(performanceAdvance);
				Utility.raiseInfo("Advance Updated", "The advance is now marked as completed.");
				refreshVenues(evt);
			}

			public void doReassign(ActionEvent evt)
			{
				if(performanceId != null)
				{
					Performance p = getCalendarDao().getPerformance(performanceId);
					performanceAdvance.setVenue(p.getVenue());
					performanceAdvance.setPerformance(p);
					p.setAdvance(performanceAdvance);
					getCalendarDao().savePerformanceAdvance(performanceAdvance);
					getCalendarDao().savePerformance(p);
					Utility.raiseInfo(
							"Advance Reassigned",
							"The advance has been saved and reassigned to the performance of "
									+ p.getDescription());
					refreshVenues(evt);
					return;
				}
				Utility.raiseError("Invalid Selection", "You can only reassign to an existing performance");
			}

			public void doUpdateAdvance(ActionEvent evt)
			{
				getCalendarDao().savePerformanceAdvance(performanceAdvance);
				Utility.raiseInfo("Advance Updated", "");
				refreshVenues(evt);
			}

			/**
			 * @return the performanceAdvance
			 */
			public PerformanceAdvance getPerformanceAdvance()
			{
				return performanceAdvance;
			}

			public String getPerformanceDetails()
			{
				if(performanceAdvance == null || performanceAdvance.getPerformance() == null
						|| performanceAdvance.getDay() == null)
					return "Orphan";
				else
				{
					Day day = performanceAdvance.getDay();
					Performance perf = performanceAdvance.getPerformance();
					String city = "";
					String venue = "";
					if(day != null)
						city = day.getCastLocation() != null ? day.getCastLocation().getName() : "";
					venue = VenueBean.this.venue.getName();

					return city + " " + venue + " of " + perf == null ? "" : perf.getDescription();
				}
			}

			/**
			 * @return the performanceId
			 */
			public Long getPerformanceId()
			{
				return performanceId;
			}

			/**
			 * @return the performances
			 */
			public ArrayList<Performance> getPerformances()
			{
				if(performanceAdvance == null || !performanceAdvance.isOrphan())
					return new ArrayList<Performance>();
				ArrayList<Performance> ret = getCalendarDao()
						.getAlternativePerformances(performanceAdvance);
				return ret;
			}

			/**
			 * @return the printController
			 */
			@SuppressWarnings("serial")
			public AbstractPrintController getPrintController()
			{
				if(printController == null)
				{
					printController = new AbstractPrintController("venue", "sub")
					{
						@Override
						protected Collection<?> getData()
						{
							try
							{
								City city = venue.getCity() == null ? new City() : venue.getCity();
								ArrayList<Performance> performances = new ArrayList<Performance>();

								if(performanceAdvance.getPerformance() != null)
								{
									Season season = performanceAdvance.getPerformance().getDay().getWeek()
											.getSeason();
									performances = Utility.getBean(ScheduleBean.class)
											.getPerformancesForSeasonAndVenue(season, venue);
								}

								AdvanceSheetDto dto = new AdvanceSheetDto(venue, city, performanceAdvance,
										performances);
								ArrayList<AdvanceSheetDto> data = new ArrayList<AdvanceSheetDto>();

								data.add(dto);
								return data;
							}
							catch(Throwable t)// Elvis operator
							{
								throw new RuntimeException("Unable to print", t);
							}
						}

						@Override
						protected Map<String, Object> getParameters()
						{
							return null;
						}

						@Override
						protected String getReportName()
						{
							return "Venue Sheet " + venue.getName();
						}

					};
				}
				return printController;
			}

			/**
			 * @param performanceAdvance
			 *          the performanceAdvance to set
			 */
			public void setPerformanceAdvance(PerformanceAdvance performanceAdvance)
			{
				this.performanceAdvance = performanceAdvance;
			}

			/**
			 * @param performanceId
			 *          the performanceId to set
			 */
			public void setPerformanceId(Long performanceId)
			{
				this.performanceId = performanceId;
			}

		}
	}

	public class FileModel implements Serializable
	{
		private static final long serialVersionUID = 3L;
		ArrayList<FileWrapper> files;
		File modelRoot;

		public FileModel(Long venueId)
		{
			/*
			 * Null initialization for web beans, they will initialize and there is no logic to control
			 * it.
			 */
			if(venueId == null)
			{
				files = new ArrayList<FileWrapper>();
				return;
			}
			Venue venue = getVenueDao().getVenue(venueId);
			files = new ArrayList<FileWrapper>();

			modelRoot = getOrCreateVenueDocumentRoot(venue);

			File lights = new File(modelRoot.getAbsolutePath() + File.separator
					+ VenueDaoRemote.ELECTRICS_DOCUMENTS + File.separator);
			File general = new File(modelRoot.getAbsolutePath() + File.separator
					+ VenueDaoRemote.GENERAL_DOCUMENTS + File.separator);
			File scenery = new File(modelRoot.getAbsolutePath() + File.separator
					+ VenueDaoRemote.SCENIC_DOCUMENTS + File.separator);
			File sound = new File(modelRoot.getAbsolutePath() + File.separator
					+ VenueDaoRemote.SOUND_DOCUMENTS + File.separator);
			File wardrobe = new File(modelRoot.getAbsolutePath() + File.separator
					+ VenueDaoRemote.WARDROBE_DOCUMENTS + File.separator);

			mkDirs(lights);
			mkDirs(general);
			mkDirs(scenery);
			mkDirs(sound);
			mkDirs(wardrobe);

			File[] docs;

			docs = lights.listFiles();
			for(File f : docs)
				files.add(new FileWrapper(f, VenueDaoRemote.ELECTRICS_DOCUMENTS));

			docs = scenery.listFiles();
			for(File f : docs)
				files.add(new FileWrapper(f, VenueDaoRemote.SCENIC_DOCUMENTS));

			docs = sound.listFiles();
			for(File f : docs)
				files.add(new FileWrapper(f, VenueDaoRemote.SOUND_DOCUMENTS));

			docs = wardrobe.listFiles();
			for(File f : docs)
				files.add(new FileWrapper(f, VenueDaoRemote.WARDROBE_DOCUMENTS));

			docs = general.listFiles();
			for(File f : docs)
				files.add(new FileWrapper(f, VenueDaoRemote.GENERAL_DOCUMENTS));
		}

		public ArrayList<FileWrapper> getFiles()
		{
			return files;
		}

		public File getModelRoot()
		{
			return modelRoot;
		}
	}

	public class FileWrapper implements Serializable
	{
		private static final long serialVersionUID = 3L;
		String department;
		File file;
		DefaultStreamedContent stream;

		FileWrapper(File file, String department)
		{
			this.file = file;
			this.department = department;
		}

		public String getDepartment()
		{
			return department;
		}

		public File getFile()
		{
			return file;
		}

		public Date getLastModified()
		{
			return new Date(file.lastModified());
		}

		public DefaultStreamedContent getStream()
		{
			if(stream == null)
			{
				try
				{
					stream = new DefaultStreamedContent(new FileInputStream(file), "attachment",
							file.getName());
				}
				catch(FileNotFoundException e)
				{
					FacesMessage error = new FacesMessage(FacesMessage.SEVERITY_ERROR, "File not found",
							"If this message persists please contact the administrator");
					FacesContext.getCurrentInstance().addMessage(null, error);
					e.printStackTrace();
				}
			}
			return stream;
		}

		public void setDepartment(String department)
		{
			this.department = department;
		}

		public void setFile(File file)
		{
			this.file = file;
		}

		public void setStream(DefaultStreamedContent stream)
		{
			this.stream = stream;
		}

	}

	public class NewVenue implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private Long cityId;
		private String name;
		private String productionName;

		public Long getCityId()
		{
			return cityId;
		}

		public String getName()
		{
			return name;
		}

		public Venue getNewVenue()
		{
			Venue tmp = new Venue();
			tmp.setName(name);
			tmp.setCity(Utility.getBean(LocationBean.class).getCity(cityId));

			return tmp;
		}

		public String getProductionName()
		{
			return productionName;
		}

		public void setCityId(Long cityId)
		{
			this.cityId = cityId;
		}

		public void setName(String name)
		{
			this.name = name;
		}
	}
}
