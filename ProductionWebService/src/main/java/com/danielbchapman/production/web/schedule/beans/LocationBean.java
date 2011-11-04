package com.danielbchapman.production.web.schedule.beans;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.danielbchapman.production.JasperUtility;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.CompanyManagementDaoRemote;
import com.danielbchapman.production.dto.CitySheetDto;
import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.Hospital;
import com.danielbchapman.production.entity.Hotel;
import com.danielbchapman.production.entity.PointOfInterest;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.web.production.beans.AdministrationBean;
import com.danielbchapman.production.web.production.beans.SeasonBean;

@SessionScoped
public class LocationBean
{
	private ArrayList<SelectItem> cities;
	private CityPrintingController cityPrinting;
	private static String reportingDirectory = "location";

	private CompanyManagementDaoRemote companyManagmentDao;
	private City editCity;
	private Long editCityId;
	private Hospital editHospital;
	private ArrayList<HospitalWrapper> editHospitals;
	private Hotel editHotel;
	private ArrayList<HotelWrapper> editHotels;
	private City newCity = new City();
	private Hospital newHospital;
	private PointOfInterest newPointOfInterest;
	private Hotel newHotel;
	private ArrayList<PointOfInterestWrapper> pointsOfInterest;

	public void doAddHospital(ActionEvent evt)
	{
		if(newHospital.getName() == null || newHospital.getName().length() < 3)
		{
			Utility.raiseInfo("Error Saving",
					"The hospital could not be saved, please enter a meaningful name.");
			return;
		}
		newHospital.setCity(editCity);
		getCompanyManagmentDao().saveHospital(newHospital);
		Utility.raiseInfo("Hospital Saved", "The hospital " + newHospital.getName() + " was added.");
		doEditSelection(evt);
	}

	public void doAddHotel(ActionEvent evt)
	{
		if(newHotel.getName() == null || newHotel.getName().length() < 3)
		{
			Utility.raiseInfo("Error Saving",
					"The hotel could not be saved, please enter a meaningful name.");
			return;
		}

		newHotel.setCity(editCity);
		getCompanyManagmentDao().saveHotel(newHotel);
		Utility.raiseInfo("Hotel Saved", "The hotel " + newHotel.getName() + " was added.");
		doEditSelection(evt);
	}

	public void doAddPointOfInterest(ActionEvent evt)
	{
		if(!Utility.validateLength(newPointOfInterest.getName(), 3, "Please enter a useful name."))
			return;

		newPointOfInterest.setCity(editCity);
		getCompanyManagmentDao().savePointOfInterest(newPointOfInterest);
		Utility.raiseInfo("Saved", "Point of interest has been added to the database.");
		newPointOfInterest = new PointOfInterest();
		doEditSelection(evt);
	}

	public void doEditSelection(ActionEvent evt)
	{
		if(editCityId == null)
			Utility.raiseError("Failed to Load", "The city failed to load and could not be edited");

		editCity = getCompanyManagmentDao().getCity(editCityId);
		newHospital = new Hospital();
		newHotel = new Hotel();
		newPointOfInterest = new PointOfInterest();
		pointsOfInterest = null;
		editHospitals = null;
		editHotels = null;
	}

	public SelectItem[] getAllStates()
	{
		return Utility.ALL_STATES;
	}

	public SelectItem[] getAllStatesAbberviationsOnly()
	{
		return Utility.ALL_STATES_SHORT;
	}

	public ArrayList<SelectItem> getCities()
	{
		if(cities == null)
		{
			cities = new ArrayList<SelectItem>();
			for(City c : getCompanyManagmentDao().getCities())
				cities.add(new SelectItem(c.getId(), "[" + c.getStateOrTerritory() + "] " + c.getName()));
		}

		return cities;
	}

	/**
	 * @param id
	 *          the ID of the city
	 * @return the city bound to this ID
	 * 
	 */
	public City getCity(Long id)
	{
		return getCompanyManagmentDao().getCity(id);
	}

	/**
	 * @return the cityPrinting
	 */
	public CityPrintingController getCityPrinting()
	{
		if(cityPrinting == null)
		{
			File root = new File(Utility.getBean(AdministrationBean.class).getReportingDocumentRoot()
					+ File.separator + reportingDirectory);
			cityPrinting = new CityPrintingController(root);
		}
		return cityPrinting;
	}

	public City getEditCity()
	{
		return editCity;
	}

	public Long getEditCityId()
	{
		return editCityId;
	}

	public Hospital getEditHospital()
	{
		return editHospital;
	}

	public ArrayList<HospitalWrapper> getEditHospitals()
	{
		if(editCity == null)
			return new ArrayList<HospitalWrapper>();

		if(editHospitals == null)
		{
			editHospitals = new ArrayList<HospitalWrapper>();
			ArrayList<Hospital> tmpHospitals = getCompanyManagmentDao().getHospitalsForCity(editCity);
			for(Hospital h : tmpHospitals)
				editHospitals.add(new HospitalWrapper(h));
		}

		return editHospitals;
	}

	public Hotel getEditHotel()
	{
		return editHotel;
	}

	public ArrayList<HotelWrapper> getEditHotels()
	{
		if(editCity == null)
			return new ArrayList<HotelWrapper>();

		if(editHotels == null)
		{
			editHotels = new ArrayList<HotelWrapper>();
			for(Hotel h : getCompanyManagmentDao().getHotelsForCity(editCity))
				editHotels.add(new HotelWrapper(h));
		}

		return editHotels;
	}

	public Hotel getHotel(Long id)
	{
		if(id == null)
			return null;

		return getCompanyManagmentDao().getHotel(id);
	}

	public ArrayList<HotelWrapper> getHotelsForCity(Long id)
	{
		ArrayList<Hotel> hotels = getCompanyManagmentDao().getHotelsForCity(
				getCompanyManagmentDao().getCity(id));
		ArrayList<HotelWrapper> wrapper = new ArrayList<HotelWrapper>();

		for(Hotel h : hotels)
			wrapper.add(new HotelWrapper(h));

		return wrapper;
	}

	public City getNewCity()
	{
		return newCity;
	}

	public Hospital getNewHospital()
	{
		return newHospital;
	}

	public Hotel getNewHotel()
	{
		return newHotel;
	}

	/**
	 * @return the newPointOfInterest
	 */
	public PointOfInterest getNewPointOfInterest()
	{
		return newPointOfInterest;
	}

	public ArrayList<PointOfInterestWrapper> getPointsOfInterest()
	{
		if(editCity == null)
			return new ArrayList<PointOfInterestWrapper>();

		if(pointsOfInterest == null)
		{
			pointsOfInterest = new ArrayList<PointOfInterestWrapper>();

			ArrayList<PointOfInterest> points = getCompanyManagmentDao().getPointsOfInterest(editCity);
			for(PointOfInterest p : points)
				pointsOfInterest.add(new PointOfInterestWrapper(p));
		}

		return pointsOfInterest;

	}

	public boolean isReadyForEdits()
	{
		return editCity != null;
	}

	public void saveCity(ActionEvent evt)
	{
		if(newCity == null)
		{
			Utility.raiseError("Unable to save",
					"The City was null, please contact support if the error persists.");
			return;
		}
		getCompanyManagmentDao().saveCity(newCity);
		Utility.raiseInfo("City Saved",
				"The City '" + newCity.getName() + " " + newCity.getStateOrTerritory() + " was saved");
		newCity = new City();
		cities = null;
	}

	public void saveEdits(ActionEvent evt)
	{
		if(editCity != null)
			getCompanyManagmentDao().saveCity(editCity);

		editCity = null;
	}

	public void setCities(ArrayList<SelectItem> cities)
	{
		this.cities = cities;
	}

	public void setEditCityId(Long editCityId)
	{
		this.editCityId = editCityId;
	}

	private CompanyManagementDaoRemote getCompanyManagmentDao()
	{

		if(companyManagmentDao == null)
			companyManagmentDao = Utility.getObjectFromContext(CompanyManagementDaoRemote.class,
					Utility.Namespace.PRODUCTION);

		return companyManagmentDao;
	}

	public class CityPrintingController
	{
		private boolean printAll = false;
		private File baseDir;
		private ArrayList<PrintElement> elements;
		private PrintElement selection;

		public CityPrintingController(File baseDir)
		{
			this.baseDir = baseDir;
			init();
		}

		/**
		 * @return the element
		 */
		public ArrayList<PrintElement> getElements()
		{
			return elements;
		}

		/**
		 * @return the selection
		 */
		public PrintElement getSelection()
		{
			return selection;
		}

		/**
		 * Initialize this structure.
		 */
		public void init()
		{
			ArrayList<File> jrxmls = JasperUtility.listPossibleReports(baseDir, "sub");
			elements = new ArrayList<PrintElement>();

			for(File f : jrxmls)
				elements.add(new PrintElement(f));
		}

		/**
		 * @return the printAll
		 */
		public boolean isPrintAll()
		{
			return printAll;
		}

		/**
		 * @param printAll
		 *          the printAll to set
		 */
		public void setPrintAll(boolean printAll)
		{
			this.printAll = printAll;
		}

		/**
		 * @param selection
		 *          the selection to set
		 */
		public void setSelection(PrintElement selection)
		{
			this.selection = selection;
		}

		public class PrintElement
		{
			private File file;
			private StreamedContent content;
			private boolean validSeason;

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
					String seasonName = Utility.getBean(SeasonBean.class).getSeason().getName();
					String date = new SimpleDateFormat("MM-dd-yy").format(new Date());
					String fileName = editCity.getName().replaceAll(" ", "_") + "_"
							+ seasonName.replaceAll(" ", "_") + "-" + date + ".pdf";
					content = new DefaultStreamedContent(input, "application/pdf", fileName);
				}
				return content;
			}

			/**
			 * @return the validSeason
			 */
			public boolean isValidSeason()
			{
				Season season = Utility.getBean(SeasonBean.class).getSeason();
				if(season == null || season.getId() == null)
					validSeason = false;
				else
					validSeason = true;

				return validSeason;
			}

			private byte[] getPrint()
			{
				/* Print via the options selected... */
				Season season = Utility.getBean(SeasonBean.class).getSeason();
				ArrayList<CitySheetDto> data = getCompanyManagmentDao().getCitySheetDto(editCity, season);

				HashMap<String, Object> params = new HashMap<String, Object>();
				File root = new File(Utility.getBean(AdministrationBean.class).getReportingDocumentRoot());
				String path = new File(root.getAbsoluteFile() + File.separator + reportingDirectory
						+ File.separator).getAbsolutePath()
						+ File.separator;
				params.put("FILE_PATH", path);
				params.put("SUBREPORT_DIR", path);
				byte[] print = JasperUtility.printReportAsPDF(file, params, data);

				return print;
			}
		}
	}

	public class HospitalWrapper
	{
		private Hospital hospital;

		public HospitalWrapper(Hospital hospital)
		{
			this.hospital = hospital;
		}

		public Hospital getHospital()
		{
			return hospital;
		}

		public boolean isSelectable()
		{
			if(editCity == null)
				return false;

			if(editCity.getSelectedHospital() == null)
				return true;
			else
				return !editCity.getSelectedHospital().equals(hospital);
		}

		public void onHospitalSelected(ActionEvent evt)
		{
			if(editCity != null)
			{
				editCity.setSelectedHospital(hospital);
				getCompanyManagmentDao().saveCity(editCity);
				doEditSelection(null);
			}
			else
				Utility.raiseError("No City Selected", "Unable to assign hospital.");

			Utility.raiseInfo("Hospital Selected", "The hospital " + hospital.getName()
					+ " was selected.");
		}

		public void setHospital(Hospital hospital)
		{
			this.hospital = hospital;
		}

		public void updateHospital(ActionEvent evt)
		{
			getCompanyManagmentDao().saveHospital(hospital);
			doEditSelection(evt);
			Utility.raiseInfo("Hospital Updated", "The hospital " + hospital.getName() + " was updated.");
		}
	}

	public class HotelWrapper
	{
		private Hotel hotel;

		public HotelWrapper(Hotel hotel)
		{
			this.hotel = hotel;
		}

		public Hotel getHotel()
		{
			return hotel;
		}

		public boolean isCastSelectable()
		{
			if(editCity == null)
				return false;

			if(editCity.getCastHotel() == null)
				return true;
			else
				return editCity.getCastHotel().equals(hotel);
		}

		public boolean isCrewSelectable()
		{
			if(editCity == null)
				return false;

			if(editCity.getCrewHotel() == null)
				return true;
			else
				return editCity.getCrewHotel().equals(hotel);
		}

		public void setHotel(Hotel hotel)
		{
			this.hotel = hotel;
		}

		public void updateHotel(ActionEvent evt)
		{
			getCompanyManagmentDao().saveHotel(hotel);
			doEditSelection(evt);
			Utility.raiseInfo("Hospital Updated", "The hospital " + hotel.getName() + " was updated.");
		}
	}

	public class PointOfInterestWrapper
	{
		PointOfInterest value;

		public PointOfInterestWrapper(PointOfInterest value)
		{
			this.value = value;
		}

		public void doUpdatePoint(ActionEvent evt)
		{
			if(!Utility.validateLength(value.getName(), 3, "Please enter a useful length for the name"))
				return;

			getCompanyManagmentDao().savePointOfInterest(value);
			Utility.raiseInfo("Point Saved", "The point of interest was saved to the database.");
			doEditSelection(evt);
		}

		/**
		 * @return the value
		 */
		public PointOfInterest getValue()
		{
			return value;
		}

		/**
		 * @param value
		 *          the value to set
		 */
		public void setValue(PointOfInterest value)
		{
			this.value = value;
		}
	}
}
