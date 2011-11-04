package com.danielbchapman.production.web.schedule.beans;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

import javax.faces.application.FacesMessage;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;
import org.theactingcompany.inventory.beans.InventoryDaoRemote;
import org.theactingcompany.inventory.entity.EmbeddableImage;
import org.theactingcompany.inventory.entity.GeneralElement;
import org.theactingcompany.inventory.entity.InventoryElement;
import org.theactingcompany.inventory.entity.InventoryImage;
import org.theactingcompany.inventory.entity.InventoryProblem;
import org.theactingcompany.inventory.entity.LightingElement;
import org.theactingcompany.inventory.entity.PropsElement;
import org.theactingcompany.inventory.entity.ScenicElement;
import org.theactingcompany.inventory.entity.SoundElement;
import org.theactingcompany.inventory.entity.StageManagementElement;
import org.theactingcompany.inventory.entity.WardrobeElement;

import com.danielbchapman.production.Utility;

/**
 * A session bean for handling inventory.
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Apr 13, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@SessionScoped
public class InventoryBean
{
	private InventoryDaoRemote inventoryDao;
	private Selection selection = Selection.parseValue(null);

	private InventoryElementSet<GeneralElement> general = new InventoryElementSet<GeneralElement>(
			GeneralElement.class);
	private InventoryElementSet<LightingElement> lighting = new InventoryElementSet<LightingElement>(
			LightingElement.class);
	private InventoryProblemSet problems = new InventoryProblemSet();
	private InventoryElementSet<PropsElement> props = new InventoryElementSet<PropsElement>(
			PropsElement.class);
	private InventoryElementSet<ScenicElement> scenic = new InventoryElementSet<ScenicElement>(
			ScenicElement.class);
	private InventoryElementSet<SoundElement> sound = new InventoryElementSet<SoundElement>(
			SoundElement.class);
	private InventoryElementSet<StageManagementElement> stageManagement = new InventoryElementSet<StageManagementElement>(
			StageManagementElement.class);
	private InventoryElementSet<WardrobeElement> wardrobe = new InventoryElementSet<WardrobeElement>(
			WardrobeElement.class);

	/**
	 * Navigates the user to the correct page and executes a search for the id in question
	 * 
	 * @param className
	 *          the entity class
	 * @param id
	 *          the identifier of the class
	 * 
	 */
	public void executeProblemSelect(ActionEvent evt, String className, Long id,
			InventoryProblem problem)
	{
		Class<?> clazz;
		try
		{
			clazz = Class.forName(className);
		}
		catch(ClassNotFoundException e)
		{
			raiseError("Fatal Error", e.getMessage());
			return;
		}

		if(GeneralElement.class.equals(clazz))
		{
			getGeneral().setProblemSearchResult(id, problem);
			getGeneral().doModeResults(evt);
			getGeneral().keyWords = "Problem # " + problem.getId();
			selectGeneral(evt);
		}

		if(LightingElement.class.equals(clazz))
		{
			getLighting().setProblemSearchResult(id, problem);
			getLighting().doModeResults(evt);
			getLighting().keyWords = "Problem # " + problem.getId();
			selectLighting(evt);
		}
		if(PropsElement.class.equals(clazz))
		{
			getProps().setProblemSearchResult(id, problem);
			getProps().doModeResults(evt);
			getProps().keyWords = "Problem # " + problem.getId();
			selectProps(evt);
		}
		if(ScenicElement.class.equals(clazz))
		{
			getScenic().setProblemSearchResult(id, problem);
			getScenic().doModeResults(evt);
			getScenic().keyWords = "Problem # " + problem.getId();
			selectScenic(evt);
		}
		if(SoundElement.class.equals(clazz))
		{
			getSound().setProblemSearchResult(id, problem);
			getSound().doModeResults(evt);
			getSound().keyWords = "Problem # " + problem.getId();
			selectSound(evt);
		}
		if(StageManagementElement.class.equals(clazz))
		{
			getStageManagement().setProblemSearchResult(id, problem);
			getStageManagement().doModeResults(evt);
			getStageManagement().keyWords = "Problem # " + problem.getId();
			selectStageManagement(evt);
		}
		if(WardrobeElement.class.equals(clazz))
		{
			getWardrobe().setProblemSearchResult(id, problem);
			getWardrobe().doModeResults(evt);
			getWardrobe().keyWords = "Problem # " + problem.getId();
			selectWardrobe(evt);
		}

	}

	public InventoryElementSet<GeneralElement> getGeneral()
	{
		return general;
	}

	/**
	 * Returns an image for a particular object (null if null);
	 * 
	 * @param id
	 *          the unique id
	 * @param clazz
	 *          the class of the element to find
	 * @return the EmbeddableImage for this class, null if not found
	 * 
	 */
	public EmbeddableImage getImageForClass(Long id)
	{
		if(id == null)
			return null;

		InventoryImage image = getInventoryDao().getInventoryImage(id);
		if(image == null || !image.getImage().isImageAvailable())
			return null;

		return image.getImage();
	}

	public InventoryElementSet<LightingElement> getLighting()
	{
		return lighting;
	}

	public InventoryProblemSet getProblems()
	{
		return problems;
	}

	public InventoryElementSet<PropsElement> getProps()
	{
		return props;
	}

	public InventoryElementSet<ScenicElement> getScenic()
	{
		return scenic;
	}

	public Selection getSelection()
	{
		return selection;
	}

	public InventoryElementSet<SoundElement> getSound()
	{
		return sound;
	}

	public InventoryElementSet<StageManagementElement> getStageManagement()
	{
		return stageManagement;
	}

	public InventoryElementSet<WardrobeElement> getWardrobe()
	{
		return wardrobe;
	}

	public void initializeFullTextSearches(ActionEvent evt)
	{
		if(evt != null)
		{
			getInventoryDao().initializeFullTextSearches();
		}
	}

	public boolean isActiveGeneral()
	{
		return selection == Selection.GENERAL;
	}

	public boolean isActiveLighting()
	{
		return selection == Selection.LIGHTING;
	}

	public boolean isActiveProblems()
	{
		return selection == Selection.PROBLEMS;
	}

	public boolean isActiveProps()
	{
		return selection == Selection.PROPS;
	}

	public boolean isActiveScenic()
	{
		return selection == Selection.SCENIC;
	}

	public boolean isActiveSound()
	{
		return selection == Selection.SOUND;
	}

	public boolean isActiveStageManagement()
	{
		return selection == Selection.STAGE_MANAGEMENT;
	}

	public boolean isActiveWardrobe()
	{
		return selection == Selection.WARDROBE;
	}

	public void selectGeneral(ActionEvent evt)
	{
		if(evt != null)
			selection = Selection.GENERAL;
	}

	public void selectLighting(ActionEvent evt)
	{
		if(evt != null)
			selection = Selection.LIGHTING;
	}

	public void selectProblems(ActionEvent evt)
	{
		if(evt != null)
			selection = Selection.PROBLEMS;
	}

	public void selectProps(ActionEvent evt)
	{
		if(evt != null)
			selection = Selection.PROPS;
	}

	public void selectScenic(ActionEvent evt)
	{
		if(evt != null)
			selection = Selection.SCENIC;
	}

	public void selectSound(ActionEvent evt)
	{
		if(evt != null)
			selection = Selection.SOUND;
	}

	public void selectStageManagement(ActionEvent evt)
	{
		if(evt != null)
			selection = Selection.STAGE_MANAGEMENT;
	}

	public void selectWardrobe(ActionEvent evt)
	{
		if(evt != null)
			selection = Selection.WARDROBE;
	}

	/**
	 * Blow up the wardrobe system and see if we can do 20K records
	 * 
	 * Yes, this will take a long time
	 */
	public void stressTestWardrobe(ActionEvent evt)
	{
		InputStream stream = getClass().getResourceAsStream("inventory.png");
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int i;
		try
		{
			while((i = stream.read()) > -1)
				out.write(i & 0x000000FF);
		}
		catch(IOException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}

		byte[] data = out.toByteArray();

		EmbeddableImage image = new EmbeddableImage();
		image.setFileName("test.png");
		image.setImage(data);
		image.setMimeType("image/png");

		for(int j = 0; j < 20000; j++)
		{
			WardrobeElement tmp = new WardrobeElement();
			tmp.setProduction("Stress Test");
			tmp.setType("Test");
			tmp.setDescription("Value of stress test " + j);
			tmp.setLocalImage(image);
			getInventoryDao().saveElement(tmp);
		}

	}

	private InventoryDaoRemote getInventoryDao()
	{
		if(inventoryDao == null)
			inventoryDao = Utility.getObjectFromContext(InventoryDaoRemote.class,
					Utility.Namespace.INVENTORY);

		return inventoryDao;
	}

	private void raiseError(String error, String details)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, error, details);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	private void raiseInfo(String error, String details)
	{
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, error, details);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	/**
	 * A generic wrapper for all types of InventoryElements so that they can be rapidly deployed to
	 * the frame.
	 * 
	 *************************************************************************** 
	 * @author Daniel B. Chapman
	 * @since Apr 13, 2011
	 * @link http://www.theactingcompany.org
	 *************************************************************************** 
	 */
	public class InventoryElementSet<T extends InventoryElement>
	{
		protected String bulkUploadProduction;
		protected Class<T> clazz;
		protected ArrayList<InventoryElementWrapper<T>> inactiveResults;
		protected String keyWords;
		protected InventoryElementWrapper<T> newElement;
		protected Mode searchMode = Mode.SEARCH;
		protected ArrayList<InventoryElementWrapper<T>> searchResults;
		protected InventoryElementWrapper<T> selected;

		public InventoryElementSet(Class<T> clazz)
		{
			init(clazz, null);
		}

		public InventoryElementSet(Class<T> clazz, T selected)
		{
			init(clazz, selected);
		}

		public void clearNewElement(ActionEvent evt)
		{
			if(evt != null)
			{
				newElement = new InventoryElementWrapper<T>(instantiateClass(clazz));
			}
		}

		public void clearSelectedElement()
		{
			selected = new InventoryElementWrapper<T>(instantiateClass(clazz));
		}

		@SuppressWarnings("unchecked")
		public void deactivate(ActionEvent evt)
		{
			if(evt == null)
				return;

			Long id = (Long) evt.getComponent().getAttributes().get("elementId");
			T element = (T) getInventoryDao().getElement(id, clazz);
			getInventoryDao().deactivate(element);
			raiseInfo("Item Deactivated", "The element " + element.getId() + " had been deactivated");
			inactiveResults = null;
			searchResults = null;
		}

		@SuppressWarnings("unchecked")
		public void delete(ActionEvent evt)
		{
			if(evt == null)
				return;

			Long id = (Long) evt.getComponent().getAttributes().get("elementId");
			T element = (T) getInventoryDao().getElement(id, clazz);
			getInventoryDao().deleteElement(element);
			raiseInfo("Item Deleted", "The element " + element.getId() + " had been deleted");
			// FIXME searchResults.remove(new InventoryElementWrapper<T>(element));
			// Not Deleting--this is probably a .equals(); problem...it would be good to fix
			getSearchResults();
		}

		public void doModeDetail(ActionEvent evt)
		{
			searchMode = Mode.DETAIL;
		}

		public void doModeEdit(ActionEvent evt)
		{
			searchMode = Mode.EDIT;
		}

		public void doModeResults(ActionEvent evt)
		{
			searchMode = Mode.RESULTS;
		}

		public void doModeSearch(ActionEvent evt)
		{
			searchMode = Mode.SEARCH;
		}

		public void doSetProductionForBulkUpload(ActionEvent evt)
		{
			if(bulkUploadProduction == null)
			{
				raiseError("Validation Error", "You must enter a production.");
				return;
			}

			if(bulkUploadProduction.trim().length() < 5)
			{
				bulkUploadProduction = null;
				raiseError("Validation Error", "A production must have a minimum of (5).");
				return;
			}

		}

		@SuppressWarnings("unchecked")
		public void editElement(ActionEvent evt)
		{
			if(evt == null)
				return;

			Long id = (Long) evt.getComponent().getAttributes().get("elementId");
			T element = (T) inventoryDao.getElement(id, clazz);
			if(element == null)
			{
				raiseError("Error", "Unable to open element with id " + id
						+ " please contact the administrator if the error persists");
				return;
			}

			selected = new InventoryElementWrapper<T>(element);
			EmbeddableImage image = getImageForClass(selected.getElement().getId());
			doModeEdit(evt);
		}

		public String getBulkUploadProduction()
		{
			return bulkUploadProduction;
		}

		public String getClassT()
		{
			return clazz.getName();
		}

		@SuppressWarnings({ "unchecked" })
		public ArrayList<InventoryElementWrapper<T>> getInactiveResults()
		{
			if(inactiveResults == null)
			{
				inactiveResults = new ArrayList<InventoryElementWrapper<T>>();
				ArrayList<T> elements = (ArrayList<T>) getInventoryDao().getAllInactiveElements(clazz);

				if(elements != null)
					for(T element : elements)
						inactiveResults.add(new InventoryElementWrapper<T>(element));
			}
			return inactiveResults;
		}

		public String getKeyWords()
		{
			return keyWords;
		}

		public SelectItem[] getKnownProduction()
		{
			HashSet<String> values = getInventoryDao().suggestProduction(clazz, " ");
			SelectItem[] ret = new SelectItem[values.size()];
			int i = 0;
			for(String s : values)
				ret[i++] = new SelectItem(s);
			return ret;
		}

		public SelectItem[] getKnownTypes()
		{
			HashSet<String> values = getInventoryDao().suggestType(clazz, " ");
			SelectItem[] ret = new SelectItem[values.size()];
			int i = 0;
			for(String s : values)
				ret[i++] = new SelectItem(s);
			return ret;
		}

		public InventoryElementWrapper<T> getNewElement()
		{
			return newElement;
		}

		public ArrayList<InventoryElementWrapper<T>> getSearchResults()
		{
			if(searchResults == null)
				searchAll(null);
			else
				searchViaKeywords(null);

			return searchResults;
		}

		public String getSearchTitle()
		{
			if(keyWords == null)
				return "Displaying All Elements";
			else
				return "Search Result for '" + keyWords + "'";
		}

		public InventoryElementWrapper<T> getSelected()
		{
			return selected;
		}

		/**
		 * Total hack, basically this gives an image location with the parameters in the request... JSF
		 * doesn't play well with this behavior--it is a little post happy.
		 * 
		 * @return URL for InventoryImageServlet
		 * 
		 */
		public String getUrlParameters()
		{
			if(selected != null && clazz != null)
				if(selected.element.getImage() != null)
				{
					InventoryImage image = getInventoryDao().getInventoryImage(selected.element.getImage());
					if(image != null && image.getImage().isImageAvailable())
					{
						return "/InventoryImageServlet/" + image.getImage().getFileName() + "?id="
								+ image.getId();
					}
					else
					{
						return "/InventoryImageServlet/noImage.png";
					}
				}
				else
					return "/InventoryImageServlet/noImage.png";
			else
				return "/InventoryImageServlet/errorImage.png";
		}

		public String getUrlParametersForUpload()
		{
			if(clazz != null)
				return "/InventoryImageUploadServlet/upload?class=" + clazz.getName(); // Ghetto use of
																																								// "generic"
			else
				return "/InventoryImageUploadServlet/error.png";
		}

		public void handleBulkImageUpload(FileUploadEvent evt)
		{
			UploadedFile upload = evt.getFile();
			EmbeddableImage embed = new EmbeddableImage();

			embed.setFileName(upload.getFileName());
			embed.setMimeType(FacesContext.getCurrentInstance().getExternalContext()
					.getMimeType(upload.getFileName()));
			embed.setImage(upload.getContents());

			InventoryElement element;
			try
			{
				element = clazz.newInstance();
			}
			catch(InstantiationException e)
			{
				raiseError("Application Failure", "The image " + embed.getFileName()
						+ " failed upload, please log out and try again");
				throw new RuntimeException(e.getMessage(), e);
			}
			catch(IllegalAccessException e)
			{
				raiseError("Application Failure", "The image " + embed.getFileName()
						+ " failed upload, please log out and try again");
				throw new RuntimeException(e.getMessage(), e);
			}

			element.setLocalImage(embed);
			element.setProduction(bulkUploadProduction);
			element.setType("Unknown");

			getInventoryDao().saveElement(element);
			raiseInfo("Image Added", "The image " + embed.getFileName()
					+ " was uploaded and a new item was added to the inventory as \"unknown\"");

		}

		public void handleImageUpload(FileUploadEvent evt)
		{
			UploadedFile upload = evt.getFile();

			EmbeddableImage image = new EmbeddableImage();
			image.setFileName(upload.getFileName());
			image.setMimeType(FacesContext.getCurrentInstance().getExternalContext()
					.getMimeType(upload.getFileName()));
			image.setImage(upload.getContents());

			// FIXME scale and compress images here.
			System.out.println("\t\t\tnewElement:\n" + newElement);
			raiseInfo("Image Uploaded", "The image " + image.getFileName() + " was uploaded.");

			newElement.element.setLocalImage(image);
		}

		public T instantiateClass(Class<T> clazz)
		{
			try
			{
				return clazz.newInstance();
			}
			catch(InstantiationException e)
			{
				throw new RuntimeException(e.getMessage(), e);
			}
			catch(IllegalAccessException e)
			{
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		public boolean isDetail()
		{
			if(Mode.DETAIL == searchMode)
				return true;

			return false;
		}

		public boolean isEdit()
		{
			if(Mode.EDIT == searchMode)
				return true;

			return false;
		}

		public boolean isReadyForBulkUpload()
		{
			return bulkUploadProduction != null;
		}

		public boolean isResults()
		{
			if(Mode.RESULTS == searchMode)
				return true;

			return false;
		}

		public boolean isSearch()
		{
			if(Mode.SEARCH == searchMode)
				return true;

			return false;
		}

		/**
		 * @return true if this is the wardrobe class, otherwise false <br />
		 *         (wardrobe has special properties)
		 */
		public boolean isWardrobe()
		{
			return clazz != null && clazz.equals(WardrobeElement.class);
		}

		public void refreshInactive(ActionEvent evt)
		{
			inactiveResults = null;
		}

		public void saveNewElement(ActionEvent evt)
		{
			if(evt != null)
			{
				if(!newElement.element.validate())// Required Fields
				{
					raiseError("Unable to Save", "You are missing required information (type/description)");
					return;
				}
				getInventoryDao().saveElement(newElement.element);
				raiseInfo("Inventory Saved", "The information has been saved to the database.");
				clearNewElement(evt);
			}
		}

		public void saveSelectedElement(ActionEvent evt)
		{
			if(evt != null)
			{
				getInventoryDao().saveElement(selected.element);
				clearSelectedElement();
			}
		}

		@SuppressWarnings("unchecked")
		public void searchAll(ActionEvent evt)
		{

			ArrayList<T> results = (ArrayList<T>) getInventoryDao().getAllElements(clazz);
			searchResults = new ArrayList<InventoryElementWrapper<T>>();

			for(T t : results)
				searchResults.add(new InventoryElementWrapper<T>(t));

			keyWords = null;

			doModeResults(evt);
		}

		@SuppressWarnings("unchecked")
		public void searchViaKeywords(ActionEvent evt)
		{
			if(evt == null)
				return;

			if(keyWords == null)
			{
				raiseError("Search string required",
						"The system requires a set of words to search for, to view the entire invenotry select 'View All'");
				return;
			}

			String tmpKey = keyWords.trim();

			if(tmpKey.length() < 1)
			{
				raiseError("Invalid Search", "Please enter a valid search");
				return;
			}

			ArrayList<String> strings = new ArrayList<String>();
			String[] cuts = tmpKey.split(" ");
			for(String s : cuts)
				if(s != null && s.trim().length() > 0)
					strings.add(s);

			String[] values = new String[strings.size()];

			for(int i = 0; i < values.length; i++)
				values[i] = strings.get(i);

			ArrayList<T> results = (ArrayList<T>) getInventoryDao().searchElement(values, clazz);
			searchResults = new ArrayList<InventoryElementWrapper<T>>();

			for(T t : results)
				searchResults.add(new InventoryElementWrapper<T>(t));

			doModeResults(evt);
		}

		public void searchViaParameters(ActionEvent evt)
		{
			if(evt == null)
				return;

			String keys = (String) evt.getComponent().getAttributes().get("searchString");
			keyWords = keys;
			searchViaKeywords(evt);
		}

		@SuppressWarnings("unchecked")
		public void selectElement(ActionEvent evt)
		{
			if(evt == null)
				return;

			Long id = (Long) evt.getComponent().getAttributes().get("elementId");
			T element = (T) inventoryDao.getElement(id, clazz);
			if(element == null)
			{
				raiseError("Error", "Unable to open element with id " + id
						+ " please contact the administrator if the error persists");
				return;
			}

			selected = new InventoryElementWrapper<T>(element);
			selected.image = null;
			doModeDetail(evt);
		}

		public void setBulkUploadProduction(String bulkUploadProduction)
		{
			this.bulkUploadProduction = bulkUploadProduction;
		}

		public void setKeyWords(String keyWords)
		{
			this.keyWords = keyWords;
		}

		public void setNewElement(InventoryElementWrapper<T> newElement)
		{
			this.newElement = newElement;
		}

		@SuppressWarnings("unchecked")
		public void setProblemSearchResult(Long id, InventoryProblem problem)
		{
			searchResults = new ArrayList<InventoryElementWrapper<T>>();
			InventoryElementWrapper<T> element = new InventoryElementWrapper<T>((T) getInventoryDao()
					.getElement(id, clazz));
			element.setActiveProblemDescription(problem.getDescription());
			element.setActiveProblemId(problem.getId());
			searchResults.add(element);
		}

		public void setSelected(InventoryElementWrapper<T> selected)
		{
			this.selected = selected;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestProduction
		 */
		public ArrayList<String> suggestCondition(String search)
		{
			return convertHashSet(getInventoryDao().suggestCondition(clazz, search.trim()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestProduction
		 */
		public ArrayList<String> suggestLocation(String search)
		{
			return convertHashSet(getInventoryDao().suggestLocation(clazz, search.trim()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestProduction
		 */
		public ArrayList<String> suggestPeriod(String search)
		{
			if(!clazz.equals(WardrobeElement.class))
				return new ArrayList<String>();

			return convertHashSet(getInventoryDao().suggestPeriod(WardrobeElement.class, search.trim()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestProduction
		 */
		public ArrayList<String> suggestProduction(String search)
		{
			return convertHashSet(getInventoryDao().suggestProduction(clazz, search.trim()));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.theactingcompany.inventory.beans.InventoryDaoRemote#suggestProduction
		 */
		public ArrayList<String> suggestType(String search)
		{
			return convertHashSet(getInventoryDao().suggestType(clazz, search.trim()));
		}

		protected void init(Class<T> clazz, T selected)
		{

			if(clazz == null)
				throw new NullPointerException("Follow the contract, class is null.");

			this.clazz = clazz;
			this.selected = new InventoryElementWrapper<T>(selected);

			if(selected == null)
				selected = instantiateClass(clazz);

			newElement = new InventoryElementWrapper<T>(instantiateClass(clazz));
		}

		private ArrayList<String> convertHashSet(HashSet<String> hash)
		{
			ArrayList<String> ret = new ArrayList<String>();
			for(String s : hash)
				ret.add(s);
			return ret;
		}

		/**
		 * A Wrapper that handles internal methods to a single request (exposing methods on inner
		 * objects such as images etc...
		 * 
		 * @retarded Q extends T so that the JSF validation can actually function properly. Otherwise it
		 *           defaults to Object and warns for each field making the tool borderline useless. The
		 *           implementation is absolutely &lt;T extends InventoryElement&gt;
		 * 
		 *************************************************************************** 
		 * @author Daniel B. Chapman
		 * @since Apr 20, 2011
		 * @link http://www.theactingcompany.org
		 *************************************************************************** 
		 */
		public class InventoryElementWrapper<Q extends T>
		{
			protected String activeProblemDescription;
			protected Long activeProblemId;
			protected Q element;
			protected DefaultStreamedContent image;
			protected InventoryProblem problem = new InventoryProblem();
			protected String userNotes;

			public InventoryElementWrapper(Q element)
			{
				this.element = element;
			}

			public void activateElement(ActionEvent evt)
			{
				if(evt == null)
					return;

				getInventoryDao().reactivate(element);
				inactiveResults = null;
				searchResults = null;
				doModeSearch(evt);
			}

			public void appendUserNotes(ActionEvent evt)
			{
				if(evt != null)
				{
					if(userNotes != null && userNotes.length() > 3)
					{
						String newNotes = element.getUserNotes() == null ? userNotes : element.getUserNotes()
								.trim() + " " + userNotes;
						element.setUserNotes(newNotes.trim());
						getInventoryDao().saveElement(element);
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Inventory Updated", "The item "
										+ element.getId() + " was updated."));
					}
					else
					{
						FacesContext.getCurrentInstance().addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_ERROR, "Update Failed",
										"Please enter a useful amount of data (3) or more characters"));
					}
				}
			}

			public String getActiveProblemDescription()
			{
				return activeProblemDescription;
			}

			public Long getActiveProblemId()
			{
				return activeProblemId;
			}

			public Q getElement()
			{
				return element;
			}

			public DefaultStreamedContent getImage()
			{
				if(image == null)
				{
					InputStream input = null;
					if(element.getImage() != null)
					{
						InventoryImage dbImage = getInventoryDao().getInventoryImage(element.getImage());
						if(dbImage != null && dbImage.getImage().isImageAvailable())
						{
							input = new ByteArrayInputStream(dbImage.getImage().getImage());
							image = new DefaultStreamedContent(input, FacesContext.getCurrentInstance()
									.getExternalContext().getMimeType(dbImage.getImage().getMimeType()));
						}
						else
						{
							input = getClass().getResourceAsStream("noImage.png");
							image = new DefaultStreamedContent(input, FacesContext.getCurrentInstance()
									.getExternalContext().getMimeType("noImage.png"));
						}
					}
					else
					{
						input = getClass().getResourceAsStream("noImage.png");
						image = new DefaultStreamedContent(input, FacesContext.getCurrentInstance()
								.getExternalContext().getMimeType("noImage.png"));
					}
				}

				return image;
			}

			public InventoryProblem getProblem()
			{
				return problem;
			}

			/**
			 * Total hack, basically this gives an image location with the parameters in the request...
			 * JSF doesn't play well with this behavior--it is a little post happy.
			 * 
			 * @return URL for InventoryImageServlet
			 * 
			 */
			public String getUrlParameters()
			{
				if(element != null)
					if(element.getImage() != null)
					{
						InventoryImage image = getInventoryDao().getInventoryImage(element.getImage());
						if(image != null && image.getImage().isImageAvailable())
							return "/InventoryImageServlet/" + image.getImage().getFileName() + "?id="
									+ image.getId();
						else
							return "/InventoryImageServlet/noImage.png";
					}
					else
						return "/InventoryImageServlet/noImage.png";
				else
					return "/InventoryImageServlet/errorImage.png";
			}

			public String getUserNotes()
			{
				return userNotes;
			}

			public void handleImageUpdateUpload(FileUploadEvent evt)
			{
				UploadedFile upload = evt.getFile();
				EmbeddableImage embed = new EmbeddableImage();

				embed.setFileName(upload.getFileName());
				embed.setMimeType(FacesContext.getCurrentInstance().getExternalContext()
						.getMimeType(upload.getFileName()));
				embed.setImage(upload.getContents());

				element.setLocalImage(embed);
				element.setImage(null);

				this.image = null;
				raiseInfo("Image Uploaded", "The image " + embed.getFileName() + " was uploaded.");

			}

			public boolean isCanHasSpecificProblem()
			{
				return activeProblemId != null;
			}

			public void resolveActiveProblem(ActionEvent evt)
			{
				if(evt == null)
					return;

				if(activeProblemId != null)
				{
					InventoryProblem problem = getInventoryDao().getProblem(activeProblemId);
					getInventoryDao().resolveProblem(problem);
					getProblems().refreshProblems(evt);

					activeProblemDescription = null;
					activeProblemId = null;

					selectProblems(evt);
				}
			}

			public void saveProblem(ActionEvent evt)
			{
				if(problem.getDescription() == null || problem.getDescription().length() < 4)
				{
					raiseInfo("Validation Error",
							"Please enter a useful description of the problem [(5) characters].");
					return;
				}

				getInventoryDao().reportElementProblem(element, problem);
				problem = new InventoryProblem();
				raiseInfo(
						"Inventory Problem Reported",
						"Your problem has been reported, thank you for the informaiton. If your contact information is avaiable we will contact you with questions.");
			}

			public void saveUpdates(ActionEvent evt)
			{
				if(evt != null)
				{
					if(!element.validate())// Required Fields
					{
						raiseError("Unable to Save", "You are missing required information (type/description)");
						return;
					}

					getInventoryDao().saveElement(element);

					raiseInfo("Inventory Saved", "Element #" + element.getId() + " has been updated");
					doModeSearch(evt);
				}
			}

			public void setActiveProblemDescription(String activeProblemDescription)
			{
				this.activeProblemDescription = activeProblemDescription;
			}

			public void setActiveProblemId(Long activeProblemId)
			{
				this.activeProblemId = activeProblemId;
			}

			public void setElement(Q element)
			{
				this.element = element;
			}

			public void setProblem(InventoryProblem problem)
			{
				this.problem = problem;
			}

			public void setUserNotes(String userNotes)
			{
				this.userNotes = userNotes;
			}
		}
	}

	public class InventoryProblemSet
	{
		protected ArrayList<InventoryProblem> active;

		public void findProblem(ActionEvent evt)
		{
			Long id = (Long) evt.getComponent().getAttributes().get("entityId");
			String clazz = (String) evt.getComponent().getAttributes().get("entityClass");
			Long problemId = (Long) evt.getComponent().getAttributes().get("problemId");
			InventoryProblem problem = getInventoryDao().getProblem(problemId);

			if(id == null)
			{
				raiseError("Fata Error", "Unable to locate null class/id pair");
				return;
			}

			executeProblemSelect(evt, clazz, id, problem);
		}

		public ArrayList<InventoryProblem> getActive()
		{
			if(active == null)
				active = getInventoryDao().getAllProblems();

			return active;
		}

		public void refreshProblems(ActionEvent evt)
		{
			if(evt != null)
				active = null;
		}

		public void resolveProblem(ActionEvent evt)
		{
			if(evt == null)
				return;

			Long id = (Long) evt.getComponent().getAttributes().get("problemId");
			InventoryProblem problem = getInventoryDao().getProblem(id);
			getInventoryDao().resolveProblem(problem);
			raiseInfo("Problem Resolved",
					"Problem for " + problem.getEntityClass() + " " + problem.getId() + " is resolved.");

			active = null; // refresh;
		}
	}

	public enum Mode
	{
		DETAIL, EDIT, RESULTS, SEARCH;
	}

	public enum Selection
	{
		GENERAL("general", "General"), LIGHTING("lighting", "Lighting"), PROBLEMS("problems",
				"Inventory Problems"), PROPS("props", "Properties"), SCENIC("scenic", "Scenic"), SOUND(
				"sound", "Sound"), STAGE_MANAGEMENT("stageManagement", "Stage Management"), WARDROBE(
				"wardrobe", "Wardrobe");

		public static Selection parseValue(String sub)
		{
			if(sub == null)
				return Selection.GENERAL;

			for(Selection s : Selection.values())
				if(s.toString().equals(sub.trim()))
					return s;

			return Selection.GENERAL;
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
}
