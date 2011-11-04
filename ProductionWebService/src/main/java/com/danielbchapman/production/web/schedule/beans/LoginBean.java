package com.danielbchapman.production.web.schedule.beans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.ToggleEvent;

import com.danielbchapman.jboss.login.Roles;
import com.danielbchapman.production.Utility;

@SessionScoped
public class LoginBean
{
	private String value;
	private TimeZone zone;
	private HtmlOutputText timeZoneField;
	private String timeSelection = "unknown";
	private String timeZoneCalculated = "unknown";
	private String timeZoneDaylightOffset = "0";
	private String timeZoneDaylightActive = "0";
	private RoleManager roles = new RoleManager();

	private TimeZone[] validZones = new TimeZone[] { TimeZone.getTimeZone("US/Alaska"),
			TimeZone.getTimeZone("US/Aleutian"), TimeZone.getTimeZone("US/Arizona"),
			TimeZone.getTimeZone("US/Central"), TimeZone.getTimeZone("US/East-Indiana"),
			TimeZone.getTimeZone("US/Eastern"), TimeZone.getTimeZone("US/Hawaii"),
			TimeZone.getTimeZone("US/Indiana-Starke"), TimeZone.getTimeZone("US/Michigan"),
			TimeZone.getTimeZone("US/Mountain"),
			// TimeZone.getTimeZone("US/Pacific"),
			TimeZone.getTimeZone("US/Pacific-New"), TimeZone.getTimeZone("US/Samoa") };

	private TimeZone[] searchableZones = new TimeZone[] { TimeZone.getTimeZone("US/Eastern"),
			TimeZone.getTimeZone("US/Central"), TimeZone.getTimeZone("US/Mountain"),
			TimeZone.getTimeZone("US/Arizona"), TimeZone.getTimeZone("US/Pacific-New"),
			TimeZone.getTimeZone("US/Pacific"), TimeZone.getTimeZone("US/Alaska") };

	private ArrayList<SelectItem> timeZones;

	public void confirmChangeZone(ActionEvent evt)
	{
		if(evt != null)
		{
			System.out.println("SELECTING TIME ZONE -> " + timeSelection);
			if(timeSelection == null)
			{
				zone = TimeZone.getDefault(); // Null selection
				return;
			}
			Utility.getBean(ScheduleBean.class).getAllSeasons();
			System.out.println("SECONDARY OUTPUT -> " + timeSelection);
			zone = TimeZone.getTimeZone(timeSelection); // list created from call--should not fail
		}
	}

	public void confirmTimeZone(ActionEvent evt)
	{
		if(evt != null)
		{
			Integer offset = Integer.valueOf(timeZoneCalculated);
			if(offset == null)
			{
				System.out.println("null offset");
				zone = TimeZone.getDefault();
				return;
			}

			String[] raw = TimeZone.getAvailableIDs(offset * 60 * 1000);// offset in minutes -> milli

			if(raw == null || raw.length == 0)
			{
				System.out.println("raw offest failed " + raw);
				zone = TimeZone.getDefault();
				return;
			}

			TimeZone tmpZone = null;
			int daylightSavingsTime = -Integer.valueOf(timeZoneDaylightOffset) * 60 * 1000;
			int dstActive = Integer.valueOf(timeZoneDaylightActive);

			for(int i = searchableZones.length - 1; i > -1; i--)// get system times, bottom of locale
																													// usually
			{
				tmpZone = searchableZones[i];
				if(dstActive != 0)
					if(tmpZone.useDaylightTime() && tmpZone.getDSTSavings() == daylightSavingsTime
							&& tmpZone.getOffset(System.currentTimeMillis()) == offset * 60 * 1000)
					{
						System.out.println("Confirmed: " + tmpZone);
						break;
					}

				tmpZone = null;
			}

			if(tmpZone == null)
			{
				tmpZone = TimeZone.getTimeZone(raw[0]);// should not fail <<--Failboat, doesn't detect DST,
																								// look above (comment left for comedy value, it was
																								// in reference ot the array)
				System.out.println("CONFIRMING TIME ZONE -> " + timeZoneCalculated
						+ " Failed to detect DST");
			}

			zone = tmpZone;
			Utility.getBean(ScheduleBean.class).getAllSeasons();
		}
	}

	public String getReadableOffset()
	{
		if(zone == null)
			return "No Time Zone";

		return "GMT " + zone.getRawOffset() / 60 / 60 / 1000;
	}

	public String getTimeSelection()
	{
		return timeSelection;
	}

	public TimeZone getTimeZone()
	{
		return zone;
	}

	public String getTimeZoneCalculated()
	{
		return timeZoneCalculated;
	}

	public String getTimeZoneDaylightActive()
	{
		return timeZoneDaylightActive;
	}

	public String getTimeZoneDaylightOffset()
	{
		return timeZoneDaylightOffset;
	}

	public HtmlOutputText getTimeZoneField()
	{
		return timeZoneField;
	}

	public ArrayList<SelectItem> getTimeZones()
	{
		if(timeZones == null)
		{
			timeZones = new ArrayList<SelectItem>();

			for(TimeZone id : validZones)
				timeZones.add(new SelectItem(id.getID()));
		}

		return timeZones;
	}

	public String getUserPrinciple()
	{
		return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() == null ? null
				: FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal().getName();
	}

	public String getValue()
	{
		return value;
	}

	public void handleToggle(ToggleEvent evt)
	{
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggle Event", "ToggleEvent -> "
						+ evt.getComponent()));
		System.out.println("Current Instance");
	}

	// /**
	// * @return true if user is 'admin'
	// *
	// */
	// public boolean isAdmin()
	// {
	// return
	// FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin_authentication");
	// }
	//
	// public boolean isCanHasTimeZone()
	// {
	// if(zone != null)
	// return true;
	//
	// return false;
	// }
	// public boolean isGuest()
	// {
	// return
	// FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin_authentication")
	// || FacesContext.getCurrentInstance().getExternalContext().isUserInRole("user_authentication")
	// ||
	// FacesContext.getCurrentInstance().getExternalContext().isUserInRole("scheduler_authentication")
	// || FacesContext.getCurrentInstance().getExternalContext().isUserInRole("guest_authentication")
	// ;
	// }
	//
	// public boolean isScheduler()
	// {
	// return
	// FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin_authentication")
	// || FacesContext.getCurrentInstance().getExternalContext().isUserInRole("user_authentication")
	// ||
	// FacesContext.getCurrentInstance().getExternalContext().isUserInRole("scheduler_authentication")
	// ;
	// }
	//
	// /**
	// * @return true if 'venue' or 'admin'
	// *
	// */
	// public boolean isUser()
	// {
	// return
	// FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin_authentication")
	// || FacesContext.getCurrentInstance().getExternalContext().isUserInRole("user_authentication");
	// }
	//
	// /**
	// * @return true if 'venue' or 'admin'
	// *
	// */
	// public boolean isInventoryManager()
	// {
	// return
	// FacesContext.getCurrentInstance().getExternalContext().isUserInRole("admin_authentication")
	// || FacesContext.getCurrentInstance().getExternalContext().isUserInRole("user_authentication");
	// }

	public boolean isAdmin()
	{
		return roles.isAdmin();
	}

	public boolean isGuest()
	{
		return roles.isGuest();
	}

	public boolean isInventoryAdmin()
	{
		return roles.isInventoryAdmin();
	}

	public boolean isInventoryGeneral()
	{
		return roles.isInventoryGeneral();
	}

	public boolean isInventoryLighting()
	{
		return roles.isInventoryLighting();
	}

	public boolean isInventoryProps()
	{
		return roles.isInventoryProps();
	}

	public boolean isInventoryScenic()
	{
		return roles.isInventoryScenic();
	}

	public boolean isInventorySound()
	{
		return roles.isInventorySound();
	}

	public boolean isInventoryStageManagement()
	{
		return roles.isInventoryStageManagement();
	}

	public boolean isInventoryWardrobe()
	{
		return roles.isInventoryWardrobe();
	}

	public boolean isLoggedIn()
	{
		return getUserPrinciple() != null;
	}

	public boolean isScheduler()
	{
		return roles.isScheduler();
	}

	public boolean isUser()
	{
		return roles.isUser();
	}

	public boolean isUserInRole(Roles role)
	{
		return roles.isUserInRole(role);
	}

	public void selectZone(ActionEvent evt)
	{
		if(evt != null)
		{
			TimeZone z = TimeZone.getTimeZone(timeSelection);
			this.zone = z;
		}
	}

	public void sendTestMessage(ActionEvent evt)
	{
		FacesContext.getCurrentInstance().addMessage(
				null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Toggle Event",
						"This is a test message to check for error handling."));
	}

	public void setTimeSelection(String timeSelection)
	{
		this.timeSelection = timeSelection;
	}

	public void setTimeZone(TimeZone zone)
	{
		this.zone = zone;
	}

	public void setTimeZoneCalculated(String timeZoneCalculated)
	{
		this.timeZoneCalculated = timeZoneCalculated;
	}

	public void setTimeZoneDaylightActive(String timeZoneDaylightActive)
	{
		this.timeZoneDaylightActive = timeZoneDaylightActive;
	}

	public void setTimeZoneDaylightOffset(String timeZoneDaylightOffset)
	{
		this.timeZoneDaylightOffset = timeZoneDaylightOffset;
	}

	public void setTimeZoneField(HtmlOutputText timeZoneField)
	{
		this.timeZoneField = timeZoneField;
	}

	public void setValue(String v)
	{
		value = v;
	}

	public void timeZoneNullPhaseCheck(PhaseEvent evt)
	{
		if(evt != null && zone == null)
		{
			try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("setTimeZone.xhtml");
			}
			catch(IOException e)
			{
				e.printStackTrace();
				try
				{
					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.responseSendError(500,
									"FATAL ERROR: timeZoneNullPhaseCheck(), please log out and try again.");
					FacesContext.getCurrentInstance().responseComplete();
				}
				catch(IOException e1)
				{
					FacesContext.getCurrentInstance().getExternalContext().setResponseStatus(500);
					FacesContext.getCurrentInstance().responseComplete();
					e1.printStackTrace();
				}
			}
		}
	}

	/**
	 * Force the page to redirect to the schedule view if the values are set.
	 * 
	 */
	public void timeZonePhaseCheck(PhaseEvent evt)
	{

		if(evt != null && zone != null)
		{
			try
			{
				FacesContext.getCurrentInstance().getExternalContext().redirect("schedule.xhtml");
			}
			catch(IOException e)
			{
				e.printStackTrace();
				try
				{
					FacesContext
							.getCurrentInstance()
							.getExternalContext()
							.responseSendError(500,
									"FATAL ERROR: timeZonePhaseCheck(), please log out and try again.");
					FacesContext.getCurrentInstance().responseComplete();
				}
				catch(IOException e1)
				{
					FacesContext.getCurrentInstance().getExternalContext().setResponseStatus(500);
					FacesContext.getCurrentInstance().responseComplete();
					e1.printStackTrace();
				}
			}
		}
	}
}
