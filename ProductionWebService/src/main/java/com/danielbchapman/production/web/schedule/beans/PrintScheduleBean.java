package com.danielbchapman.production.web.schedule.beans;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.danielbchapman.production.JasperUtility;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.CalendarDaoRemote;
import com.danielbchapman.production.beans.OptionsDaoRemote;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Week;
import com.danielbchapman.production.web.production.beans.AdministrationBean;

public class PrintScheduleBean
{
	private static String reportingDirectory = "schedules";

	private ArrayList<PrintElement> elements;

	private StreamedContent report;

	private boolean renderOutput;
	private Date startDate;
	private Date endDate;
	private boolean cast = true;
	private boolean crew = true;
	private boolean details = true;
	private Color colorOne;
	private Color colorTwo;

	private StreamedContent print;
	//@formatter:off
	//private Color[] colors = new Color[]{}
	//@formatter:on
	private boolean printAll;

	private String message;

	private String title;

	private OptionsDaoRemote optionsDao;

	private CalendarDaoRemote calendarDao;

	public void clearAndCancel(ActionEvent evt)
	{
		if(evt != null)
		{
			report = null;
			message = "unknown";
			title = "Print Status...";
		}
	}

	/**
	 * @return the colorOne
	 */
	public Color getColorOne()
	{
		return colorOne;
	}

	/**
	 * @return the colorTwo
	 */
	public Color getColorTwo()
	{
		return colorTwo;
	}

	/**
	 * @return the elements
	 */
	public ArrayList<PrintElement> getElements()
	{
		if(elements == null)
		{
			File root = new File(Utility.getBean(AdministrationBean.class).getReportingDocumentRoot());
			File base = new File(root.getAbsoluteFile() + File.separator + reportingDirectory);
			elements = new ArrayList<PrintElement>();
			ArrayList<File> reports = JasperUtility.listPossibleReports(base, "_");
			for(File f : reports)
				elements.add(new PrintElement(f));
		}
		return elements;
	}

	public Date getEndDate()
	{
		return endDate;
	}

	public String getMessage()
	{
		return message;
	}

	/**
	 * @return a reference to the streamed content for this report.
	 */
	public StreamedContent getReport()
	{
		return report;
	}

	public Date getStartDate()
	{
		return startDate;
	}

	public String getTitle()
	{
		return title;
	}

	/**
	 * @return the cast
	 */
	public boolean isCast()
	{
		return cast;
	}

	/**
	 * @return the crew
	 */
	public boolean isCrew()
	{
		return crew;
	}

	/**
	 * @return the details
	 */
	public boolean isDetails()
	{
		return details;
	}

	public boolean isPrintAll()
	{
		return printAll;
	}

	public synchronized boolean isRenderOutput()
	{
		return renderOutput;
	}

	/**
	 * @param cast
	 *          the cast to set
	 */
	public void setCast(boolean cast)
	{
		this.cast = cast;
	}

	/**
	 * @param colorOne
	 *          the colorOne to set
	 */
	public void setColorOne(Color colorOne)
	{
		this.colorOne = colorOne;
	}

	/**
	 * @param colorTwo
	 *          the colorTwo to set
	 */
	public void setColorTwo(Color colorTwo)
	{
		this.colorTwo = colorTwo;
	}

	/**
	 * @param crew
	 *          the crew to set
	 */
	public void setCrew(boolean crew)
	{
		this.crew = crew;
	}

	/**
	 * @param details
	 *          the details to set
	 */
	public void setDetails(boolean details)
	{
		this.details = details;
	}

	public void setEndDate(Date endDate)
	{
		this.endDate = endDate;
	}

	public void setPrintAll(boolean printAll)
	{
		this.printAll = printAll;
	}

	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

	private CalendarDaoRemote getCalendarDao()
	{
		if(calendarDao == null)
			calendarDao = Utility.getObjectFromContext(CalendarDaoRemote.class,
					Utility.Namespace.PRODUCTION);

		return calendarDao;
	}

	public class PrintElement
	{
		private File file;
		private StreamedContent content;

		public PrintElement(File file)
		{
			this.file = file;
		}

		public String getFileName()
		{
			return "./" + reportingDirectory + "/" + file.getName();
		}

		public StreamedContent getPdf()
		{
			if(content == null)
			{
				byte[] bytes = getPrint();
				final ByteArrayInputStream input = new ByteArrayInputStream(bytes);
				String seasonName = Utility.getBean(ScheduleBean.class).getSelectedSeason().getName();
				String date = new SimpleDateFormat("MM-dd-yy").format(new Date());
				String fileName = seasonName.replaceAll(" ", "_") + "_Weekly-" + date + ".pdf";
				content = new DefaultStreamedContent(input, "application/pdf", fileName);
			}
			return content;
		}

		public void printSchedule(ActionEvent evt) throws Exception
		{
			report = getPdf();
			content = null;
		}

		private byte[] getPrint()
		{
			ArrayList<Week> weeks = null;
			Season season = Utility.getBean(ScheduleBean.class).getSelectedSeason();

			if(printAll || startDate == null || endDate == null)
				weeks = getCalendarDao().getAllWeeks(season);
			else
				weeks = getCalendarDao().getWeeksInRange(startDate, endDate, season);

			HashMap<String, Object> params = new HashMap<String, Object>();

			File root = new File(Utility.getBean(AdministrationBean.class).getReportingDocumentRoot());
			//@formatter:off
			String path = new File(
						root.getAbsoluteFile() 
						+ File.separator 
						+ reportingDirectory
						+ File.separator
					).getAbsolutePath() + File.separator;
			//@formatter:on
			params.put("FILE_PATH", path);
			params.put("SUBREPORT_DIR", path);
			params.put("PRINT_CAST", new Boolean(cast));
			params.put("PRINT_CREW", new Boolean(crew));
			params.put("PRINT_DETAILS", new Boolean(details));

			byte[] print = JasperUtility.printReportAsPDF(file, params, weeks);

			return print;
		}
	}

}
