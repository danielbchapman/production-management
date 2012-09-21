package com.danielbchapman.production.web.schedule.beans;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.primefaces.event.ToggleEvent;

import com.danielbchapman.jboss.login.LoginBeanRemote;
import com.danielbchapman.jboss.login.Role;
import com.danielbchapman.jboss.login.Roles;
import com.danielbchapman.jboss.login.User;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.web.production.beans.BudgetBean;
import com.danielbchapman.production.web.production.beans.ContactBean;
import com.danielbchapman.production.web.production.beans.DepartmentBean;
import com.danielbchapman.production.web.production.beans.SeasonBean;

@SessionScoped
public class LoginBean implements Serializable
{
	private static final long serialVersionUID = 3L;
	@Getter
	@Setter
	private String authUser = "Daniel";
	@Getter
	@Setter
	private String authPass = "acting_co";
	private String value;
	private TimeZone zone;
	private HtmlOutputText timeZoneField;
	private String timeSelection = "unknown";
	private String timeZoneCalculated = "unknown";
	private String timeZoneDaylightOffset = "0";
	private String timeZoneDaylightActive = "0";
	private RoleManager roles;

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
	
	private String username;
	@Getter
	@Setter
	private boolean loginError;

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

		return "GMT " + zone.getOffset(new Date().getTime()) / 60/ 60 / 1000;
//		return "GMT " + zone.getRawOffset() / 60 / 60 / 1000;
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
		return FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal() == null ? username
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
		return isUserInRole(Roles.ADMIN);
	}

	public boolean isGuest()
	{
		return isUserInRole(Roles.GUEST);
	}

	public boolean isInventoryAdmin()
	{
		return isUserInRole(Roles.INVENTORY_ADMIN);
	}

	public boolean isInventoryGeneral()
	{
		return isUserInRole(Roles.INVENTORY_GENERAL);
	}

	public boolean isInventoryLighting()
	{
		return isUserInRole(Roles.INVENTORY_LIGHTING);
	}
	
	public boolean isCompanyMember()
	{
		return isUserInRole(Roles.COMPANY_MEMBER);
	}

	public boolean isInventoryProps()
	{
		return isUserInRole(Roles.INVENTORY_PROPS);
	}

	public boolean isInventoryScenic()
	{
		return isUserInRole(Roles.INVENTORY_SCENIC);
	}

	public boolean isInventorySound()
	{
		return isUserInRole(Roles.INVENTORY_SOUND);
	}

	public boolean isInventoryStageManagement()
	{
		return isUserInRole(Roles.INVENTORY_STAGE_MANAGEMENT);
	}

	public boolean isInventoryWardrobe()
	{
		return isUserInRole(Roles.INVENTORY_WARDROBE);
	}

	public boolean isScheduler()
	{
		return isUserInRole(Roles.SCHEDULER);
	}

	public boolean isUser()
	{
		return isUserInRole(Roles.USER);
	}

	public boolean isUserInRole(Roles role)
	{
		if(roles == null)
			roles = new RoleManager(Roles.GUEST);
		
		boolean ret = roles.isUserInRole(role); /* Debug hook */
		return ret;
	}

	public boolean isLoggedIn()
	{
		return getUserPrinciple() != null || username != null;
	}
	
	/**
	 * Log into the database via a user session. This will do 
	 * a manual calculation of roles.
	 * 
	 * @param evt the action event (not used)
	 */
	public void doSessionLogin(ActionEvent evt)
	{
		String user = authUser;
		String pass = authPass;
		
		LoginBeanRemote login = Utility.getObjectFromContext(LoginBeanRemote.class, Utility.Namespace.LOGIN);
		User userObject = login.validateLogin(user, pass);
		if(userObject != null)
		{
			List<Role> db = login.getRolesForUser(user);
			this.roles = new RoleManager(db);
				
			Utility.login();
			username = authUser;
//			authUser = null; /*Leave this reference */
			authPass = null;
			cleanBeans();
			loginError = false;
			//Utility.redirect(Utility.getSession().getServletContext().getContextPath() + "/index.xhtml");
		}
		else
			loginError = true;
		
		authPass = null;
	}

	/**
	 * A simple check to make sure this page should be displayed.
	 */
	public void phaseSecurePage(PhaseEvent evt)
	{
		if(!isCompanyMember())
			Utility.redirect("not_authorized.xhtml");
	}
	
	/**
	 * A simple security check that then passes to Initialize seasons
	 * if failed.
	 */
	public void phaseSecurePageSeasonCheck(PhaseEvent evt)
	{
		if(!isCompanyMember())
		{
			Utility.redirect("not_authorized.xhtml");
			return;
		}
			
		Utility.getBean(SeasonBean.class).phaseInitializeSeasons(evt);
	}
	
	/**
	 * A method to destroy the current objects backing a view.
	 */
	protected synchronized void cleanBeans()
	{
		Class<?>[] classes = new Class<?>[]{
			ScheduleBean.class,
			VenueBean.class,
			SeasonBean.class,
			DepartmentBean.class,
			BudgetBean.class,
			ContactBean.class
		};
		
		for(Class<?> c : classes)
			Utility.clearBean(c);
	}
	public void doSessionLogout(ActionEvent evt)
	{
		Utility.logOut();
		//Utility.redirect(Utility.getSession().getServletContext().getContextPath() + "/index.xhtml");
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
