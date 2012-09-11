package com.danielbchapman.production.web.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.event.ActionEvent;
import javax.faces.event.PhaseEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import lombok.Getter;
import lombok.Setter;

import org.eclipse.persistence.descriptors.SelectedFieldsLockingPolicy;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.SeasonDaoRemote;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.web.schedule.beans.LoginBean;

/**
 * Revamp of the season bean for managing seasons. This works off a phase event rather than some
 * crazy initialization.
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Apr 26, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
public class SeasonBean implements Serializable
{
	private static final long serialVersionUID = 3L;
	public final static String DEFAULT_SEAON_ID = "DefaultSeason";
	private Season newSeason = new Season();
	private Season season;
	private Long seasonCount;;
	private SeasonDaoRemote seasonDao;
	private ArrayList<SelectItem> seasons;
	@Getter
	@Setter
	private Long selectedSeason;
	@Getter
	private SeasonSelection selection = new SeasonSelection();

	public Season getDefaultSeason()
	{
		Long id = Utility.safeLong(
				Utility
					.getBean(AdministrationBean.class)
					.getSetting(DEFAULT_SEAON_ID));
		
		if(id != null)
			return getSeasonDao().getSeason(id);
		else
			return null;
	}
	public void confirmSeason(ActionEvent evt)
	{
		season = getSeasonDao().getSeason(selectedSeason);
		if(season != null)
		{
			getSelection().selectSummary(evt);
			Utility.redirect("season.xhtml");
		}
		else
			Utility.raiseWarning("No Season Selected",
					"Please select or create a season from the menu below");
	}

	public void createSeason(ActionEvent evt)
	{
		if(newSeason.getName() == null || newSeason.getName().trim().length() < 3)
		{
			Utility.raiseError("Can not create season", "Please enter a useful season name.");
			return;
		}

		getSeasonDao().saveSeason(newSeason);
		Utility.raiseInfo("Season Created", "The season " + newSeason.getName() + " was created.");
		refreshSeasonList();

	}

	public Season getNewSeason()
	{
		return newSeason;
	}

	public Season getSeason()
	{
		if(season == null)
			season = getDefaultSeason();
		
		return season;
	}

	public synchronized ArrayList<SelectItem> getSeasons()
	{
		if(seasons == null)
		{
			seasons = new ArrayList<SelectItem>();
			season = getDefaultSeason();
			ArrayList<Season> tmp = getSeasonDao().getSeasons();
			if(tmp != null)
				for(int i = 0; i < tmp.size(); i++)
					if(i == 0)
					{
						season = tmp.get(i);
							
						seasons.add(new SelectItem(tmp.get(i).getId(), tmp.get(i).getName()));
					}
					else
						seasons.add(new SelectItem(tmp.get(i).getId(), tmp.get(i).getName()));
			

		}
		return seasons;
	}

	/**
	 * @return <b>true</b> if no production is select else <b>false</b>
	 */
	public boolean isNoActiveSeason()
	{
		if(season == null)
			return true;
		return false;
	}

	public void doMakeDefault(ActionEvent evt)
	{
		if(Utility.getBean(LoginBean.class).isAdmin())
			if(season != null)
			{				
				Utility.getBean(AdministrationBean.class).setSetting(DEFAULT_SEAON_ID, season.getId().toString());
				Utility.raiseInfo("Setting System Property", "Key " + DEFAULT_SEAON_ID + " Value" + season.getId().toString());
			}
	}
	/**
	 * @return true if there is at least one active production, else false
	 */
	public boolean isSeasonCountMoreThanZero()
	{
		if(seasonCount == null || seasonCount < 1)
			seasonCount = getSeasonDao().getSeasonCount();

		if(seasonCount > 0)
			return true;

		return false;
	}

	public void nullifySeason()
	{
		season = null;
		Utility.redirect("setSeason.xhtml");
	}

	public void phaseInitializeSeasons(PhaseEvent evt)
	{
		if(seasonCount == null && evt != null)
			if(season == null || !isSeasonCountMoreThanZero())
			{
				season = getDefaultSeason();
				if(season == null)
					Utility.redirect("setSeason.xhtml");
			}
				
	}

	public String refreshSeasonList()
	{
		seasons = null;
		return "NOTHING";
	}

	public void selectSeason(ValueChangeEvent evt)
	{
		Long id = Long.valueOf((String) evt.getNewValue());
		season = seasonDao.getSeason(id);

		if(season != null)
			Utility.raiseInfo("Season Selected", "Season was changed to " + season.getName());
		else
			Utility.raiseError("Selection Failed", "The season could not be selected");
	}

	private SeasonDaoRemote getSeasonDao()
	{
		if(seasonDao == null)
			seasonDao = Utility.getObjectFromContext(SeasonDaoRemote.class, Utility.Namespace.PRODUCTION);
		return seasonDao;
	}

	public class SeasonSelection implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private Selection mode = Selection.SUMMARY;

		public Selection getMode()
		{
			return mode;
		}

		public boolean isBudget()
		{
			return mode == Selection.BUDGET;
		}

		public boolean isContacts()
		{
			return mode == Selection.CONTACTS;
		}

		public boolean isDepartments()
		{
			return mode == Selection.DEPARTMENTS;
		}

		public boolean isPettyCash()
		{
			return mode == Selection.PETTY_CASH;
		}

		public boolean isReports()
		{
			return mode == Selection.REPORTS;
		}

		public boolean isSummary()
		{
			return mode == Selection.SUMMARY;
		}

		public void selectBudget(ActionEvent evt)
		{
			mode = Selection.BUDGET;
		}

		public void selectContacts(ActionEvent evt)
		{
			mode = Selection.CONTACTS;
		}

		public void selectDepartment(ActionEvent evt)
		{
			mode = Selection.DEPARTMENTS;
			DepartmentBean bean = Utility.getBean(DepartmentBean.class);
			bean.refreshDepartments(evt);
		}

		public void selectPettyCash(ActionEvent evt)
		{
			mode = Selection.PETTY_CASH;
			// PettyCashBean bean = Utility.getBean(PettyCashBean.class);
			// bean.refreshDepartments(evt);
		}

		public void selectReports(ActionEvent evt)
		{
			mode = Selection.REPORTS;
		}

		public void selectSummary(ActionEvent evt)
		{
			mode = Selection.SUMMARY;
		}

	}

	public enum Selection
	{
		BUDGET("budget", "Budget"), DEPARTMENTS("departments", "Departments"), PETTY_CASH("pettyCash",
				"Petty Cash"), SUMMARY("summary", "Summary"), REPORTS("reports",
				"Reports"), CONTACTS("contacts", "Contacts");

		public static Selection parseValue(String sub)
		{
			if(sub == null)
				return Selection.SUMMARY;

			for(Selection s : Selection.values())
				if(s.toString().equals(sub.trim()))
					return s;

			return Selection.SUMMARY;
		}

		public String label;
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
}
