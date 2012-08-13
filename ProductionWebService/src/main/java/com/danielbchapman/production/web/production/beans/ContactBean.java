package com.danielbchapman.production.web.production.beans;

import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.danielbchapman.converter.AbstractConverter;
import com.danielbchapman.converter.Pair;
import com.danielbchapman.production.JasperUtility;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.ContactDaoRemote;
import com.danielbchapman.production.entity.Contact;
import com.danielbchapman.production.entity.ContactAddress;
import com.danielbchapman.production.entity.ContactGroup;
import com.danielbchapman.production.entity.ContactReportStructure;
import com.danielbchapman.production.entity.IContact;
import com.danielbchapman.production.entity.LinkedContact;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.SeasonContact;

/**
 * The ContactBean is the request bean for handling contacts. It makes a concentrated effort not to
 * cache information.
 * 
 * @author dchapman
 * @since Aug 23, 2011
 * @copyright The Acting Company Aug 23, 2011 @link www.theactingcompany.org
 */
@ManagedBean(name = "contactBean")
@ViewScoped
public class ContactBean implements Serializable
{
	private static final long serialVersionUID = 3L;
	private static String reportingDirectory = "contacts";
	private ArrayList<ContactWrapper> allContacts;
	private ContactDaoRemote contactDao;
	private ArrayList<SelectItem> contactGroupItems;
	private ArrayList<ContactGroupWrapper> contactGroups;
	private EditContact editContact = new EditContact(null);
	private NewContact newContact = new NewContact();
	private NewContactGroup newContactGroup = new NewContactGroup();
	private HashSet<SeasonContact> seasonContacts;
	private SeasonListStructure seasonListStructure;
	private SeasonPrintingController printingController;

	public ArrayList<String> contactSubGroupComplete(String query)
	{
		if(query == null || query.length() == 0)
			return getContactDao().getContactSubGroups();

		ArrayList<String> ret = new ArrayList<String>();

		for(String s : getContactDao().getContactSubGroups())
			if(s.contains(query))
				ret.add(s);

		return new ArrayList<String>();
	}

	public ArrayList<ContactWrapper> getAllContacts()
	{
		if(allContacts == null)
		{
			allContacts = new ArrayList<ContactWrapper>();
			ArrayList<Contact> tmp = getContactDao().getAllContacts();
			for(Contact c : tmp)
				allContacts.add(new ContactWrapper(c));
		}

		return allContacts;
	}

	/**
	 * @return the contactGroups
	 */
	public ArrayList<SelectItem> getContactGroupItems()
	{
		if(contactGroupItems == null)
		{
			contactGroupItems = new ArrayList<SelectItem>();
			for(ContactGroupWrapper g : getContactGroups())
				contactGroupItems.add(new SelectItem(g.getGroup().getId(), g.getGroup().getName()));
		}
		return contactGroupItems;
	}

	public ArrayList<ContactGroupWrapper> getContactGroups()
	{
		if(contactGroups == null)
		{
			ArrayList<ContactGroup> tmp = getContactDao().getGroups();
			contactGroups = new ArrayList<ContactGroupWrapper>();

			for(ContactGroup g : tmp)
				contactGroups.add(new ContactGroupWrapper(g));
		}

		return contactGroups;
	}

	/**
	 * @return the editContact
	 */
	public EditContact getEditContact()
	{
		return editContact;
	}

	/**
	 * @return the newContact
	 */
	public NewContact getNewContact()
	{
		return newContact;
	}

	public NewContactGroup getNewContactGroup()
	{
		return newContactGroup;
	}

	/**
	 * @return the printingController
	 */
	public SeasonPrintingController getPrintingController()
	{
		if(printingController == null)
		{
			String baseDir = Utility.getBean(AdministrationBean.class).getReportingDocumentRoot();
			File reportingDir = new File(baseDir + File.separator + reportingDirectory + File.separator);
			printingController = new SeasonPrintingController(reportingDir);
		}

		return printingController;
	}

	public HashSet<SeasonContact> getSeasonContacts()
	{
		if(seasonContacts == null)
		{
			Season current = Utility.getBean(SeasonBean.class).getSeason();
			seasonContacts = new HashSet<SeasonContact>();
			for(SeasonContact s : getContactDao().getSeasonContacts(current))
				seasonContacts.add(s);
		}

		return seasonContacts;
	}

	/**
	 * @return the seasonListModel
	 */
	public SeasonListStructure getSeasonListStructure()
	{
		if(seasonListStructure == null)
		{
			Season season = Utility.getBean(SeasonBean.class).getSeason();
			ArrayList<IContact> seasonContacts = getContactDao().getContactsForSeason(season);
			ArrayList<IContact> notSeasonContacts = getContactDao().getContactsNotInSeason(season);

			seasonListStructure = new SeasonListStructure(notSeasonContacts, seasonContacts);
		}
		return seasonListStructure;
	}

	public void refresh()
	{
		allContacts = null;
		contactGroups = null;
		contactGroupItems = null;
		seasonContacts = null;
		seasonListStructure = null;
		printingController = null;
	}

	public void refresh(ActionEvent evt)
	{
		refresh();
	}

	private ContactDaoRemote getContactDao()
	{
		if(contactDao == null)
			contactDao = Utility.getObjectFromContext(ContactDaoRemote.class,
					Utility.Namespace.PRODUCTION);

		return contactDao;
	}

	public class ContactAddressWrapper implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private ContactAddress address;
		private Contact contact;

		public ContactAddressWrapper(Contact contact)
		{
			address = new ContactAddress();
			this.contact = contact;
		}

		public ContactAddressWrapper(Contact contact, ContactAddress address)
		{
			this.contact = contact;
			this.address = address;
		}

		public void delete(ActionEvent evt)
		{
			if(address.getId() == null)
				return;

			String ref = address.getLabel();
			getContactDao().deleteContactAddress(address);
			Utility.raiseInfo("Address Removed", ref);
			refresh();
		}

		/**
		 * @return the address
		 */
		public ContactAddress getAddress()
		{
			return address;
		}

		/**
		 * @return the contact
		 */
		public Contact getContact()
		{
			return contact;
		}

		public ContactAddress getContactAddress()
		{
			return address;
		}

		public void save(ActionEvent evt)
		{
			getContactDao().addContactAddress(address, contact);
			Utility.raiseInfo("Address Saved", address.getLabel());
			refresh();
		}
	}

	/**
	 * A simple wrapper for the group providing a save method.
	 * 
	 * @author dchapman
	 * @since Aug 24, 2011
	 * @copyright The Acting Company Aug 24, 2011 @link www.theactingcompany.org
	 */
	public class ContactGroupWrapper implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private ContactGroup group;

		public ContactGroupWrapper(ContactGroup group)
		{
			this.group = group;
		}

		/**
		 * @return the group
		 */
		public ContactGroup getGroup()
		{
			return group;
		}

		public void save(ActionEvent evt)
		{
			getContactDao().saveGroup(group);
			refresh();
			Utility.raiseInfo("Contact Saved", group.getName());
		}

		/**
		 * @param group
		 *          the group to set
		 */
		public void setGroup(ContactGroup group)
		{
			this.group = group;
		}
	}

	/**
	 * A simple wrapper for a contact providing a save method
	 * 
	 * @author dchapman
	 * @since Aug 24, 2011
	 * @copyright The Acting Company Aug 24, 2011 @link www.theactingcompany.org
	 */
	public class ContactWrapper implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private AbstractConverter<ContactGroup> converter;
		private ArrayList<ContactAddressWrapper> addresses;

		private ArrayList<LinkedContactWrapper> links;

		private Contact contact;
		private Long newLinkedGroupId;

		private String newLinkSubGroup;

		private String newLinkPosition;
		private Long contactGroupId;
		private ContactAddressWrapper newAddress;

		public ContactWrapper(Contact contact)
		{
			init(contact);
		}

		public void delete(ActionEvent evt)
		{
			String contactString = contact.getFullName();
			String log = "[CONTACT_DELETED]\n" + contact.toString();
			getContactDao().deleteContact(contact);
			refresh();
			Utility
					.raiseWarning("Contact Removed", "The contact " + contactString + " has been removed.");
			Utility.logCritical(log);

		}

		public void doSaveNewLink(ActionEvent evt)
		{
			LinkedContact save = getContactDao().addLinkedContact(newLinkPosition, newLinkedGroupId,
					newLinkSubGroup, contact);
			Utility.raiseInfo("Link Created", "A link for " + save.getDisplayName() + " created");

			newLinkSubGroup = null;
			newLinkedGroupId = null;
			newLinkPosition = null;

			refresh();
		}

		/**
		 * @return the addresses
		 */
		public ArrayList<ContactAddressWrapper> getAddresses()
		{
			return addresses;
		}

		/**
		 * @return the contact
		 */
		public Contact getContact()
		{
			return contact;
		}

		/**
		 * @return the contactGroupId
		 */
		public Long getContactGroupId()
		{
			return contactGroupId;
		}

		/**
		 * @return the converter
		 */
		public AbstractConverter<ContactGroup> getConverter()
		{
			if(converter == null)
			{
				ArrayList<Pair<Long, ContactWrapper>> wrappers = new ArrayList<Pair<Long, ContactWrapper>>();
				for(ContactWrapper item : getAllContacts())
					wrappers.add(new Pair<Long, ContactWrapper>((Long) item.getContact().getId(), item));
				ArrayList<ContactGroup> groups = getContactDao().getGroups();

				converter = new AbstractConverter<ContactGroup>(groups, getContact().getContactGroup())
				{
					private static final long serialVersionUID = 1L;

					@Override
					public String getTypeLabel(ContactGroup type)
					{
						return type.getName();
					}

					@Override
					public void updateOnSelection()
					{
						setContactGroupId(this.getSelection().getId());
					}

				};

			}
			return converter;
		}

		/**
		 * @return the links
		 */
		public ArrayList<LinkedContactWrapper> getLinks()
		{
			return links;
		}

		/**
		 * @return the newAddress
		 */
		public ContactAddressWrapper getNewAddress()
		{
			return newAddress;
		}

		/**
		 * @return the newLinkedGroupId
		 */
		public Long getNewLinkedGroupId()
		{
			return newLinkedGroupId;
		}

		/**
		 * @return the newLinkPosition
		 */
		public String getNewLinkPosition()
		{
			return newLinkPosition;
		}

		/**
		 * @return the newLinkSubGroup
		 */
		public String getNewLinkSubGroup()
		{
			return newLinkSubGroup;
		}

		public void init(Contact contact)
		{
			this.contact = contact;
			contactGroupId = contact.getContactGroup().getId();
			ArrayList<ContactAddress> tmp = getContactDao().getAddressesForContact(contact);
			addresses = new ArrayList<ContactAddressWrapper>();

			for(ContactAddress a : tmp)
				addresses.add(new ContactAddressWrapper(contact, a));

			links = new ArrayList<LinkedContactWrapper>();
			ArrayList<LinkedContact> data = getContactDao().getLinkedContacts(contact);

			for(LinkedContact link : data)
				links.add(new LinkedContactWrapper(link));

			newAddress = new ContactAddressWrapper(contact, new ContactAddress());
			newLinkSubGroup = "none";// per entity
		}

		public void save(ActionEvent evt)
		{
			if(contactGroupId != null)
				contact.setContactGroup(getContactDao().getContactGroup(contactGroupId));

			getContactDao().saveContact(contact);
			refresh();
			Utility.raiseInfo("Contact Saved", contact.getFirstName() + " " + contact.getLastName());
		}

		/**
		 * @param contact
		 *          the contact to set
		 */
		public void setContact(Contact contact)
		{
			this.contact = contact;
		}

		/**
		 * @param contactGroupId
		 *          the contactGroupId to set
		 */
		public void setContactGroupId(Long contactGroupId)
		{
			this.contactGroupId = contactGroupId;
		}

		/**
		 * @param newLinkedGroupId
		 *          the newLinkedGroupId to set
		 */
		public void setNewLinkedGroupId(Long newLinkedGroupId)
		{
			this.newLinkedGroupId = newLinkedGroupId;
		}

		/**
		 * @param newLinkPosition
		 *          the newLinkPosition to set
		 */
		public void setNewLinkPosition(String newLinkPosition)
		{
			this.newLinkPosition = newLinkPosition;
		}

		/**
		 * @param newLinkSubGroup
		 *          the newLinkSubGroup to set
		 */
		public void setNewLinkSubGroup(String newLinkSubGroup)
		{
			this.newLinkSubGroup = newLinkSubGroup;
		}

		public class LinkedContactWrapper
		{
			private LinkedContact linkedContact;

			public LinkedContactWrapper(LinkedContact linkedContact)
			{
				this.linkedContact = linkedContact;
			}

			public void doDelete(ActionEvent evt)
			{
				LinkedContact tmp = linkedContact;
				getContactDao().removeLinkedContact(linkedContact);
				Utility.raiseInfo("Link Removed", tmp.getDisplayName() + " Removed");

				refresh();
			}

			/**
			 * @return the linkedContact
			 */
			public LinkedContact getLinkedContact()
			{
				return linkedContact;
			}

			/**
			 * @param linkedContact
			 *          the linkedContact to set
			 */
			public void setLinkedContact(LinkedContact linkedContact)
			{
				this.linkedContact = linkedContact;
			}
		}
	}

	/**
	 * A simple extension of the new contact class that allows editing
	 * 
	 * @author dchapman
	 * @since Aug 23, 2011
	 * @copyright The Acting Company Aug 23, 2011 @link www.theactingcompany.org
	 */
	public class EditContact extends NewContact
	{
		private static final long serialVersionUID = 1L;

		public EditContact(Contact c)
		{
			if(c == null)
			{
				c = new Contact();
				contactGroup = null;
			}
			else
			{
				this.contact = c;
				this.contactGroup = c.getContactGroup().getId();
			}
		}
	}

	/**
	 * A simple override of the NewContactGroup to provide editing
	 * 
	 * @author dchapman
	 * @since Aug 24, 2011
	 * @copyright The Acting Company Aug 24, 2011 @link www.theactingcompany.org
	 */
	public class EditContactGroup extends NewContactGroup
	{
		private static final long serialVersionUID = 1L;
		private ContactGroup group;

		public EditContactGroup(ContactGroup group)
		{
			this.group = group;
			this.color = new Color(group.getRed(), group.getBlue(), group.getGreen(), group.getAlpha());
			this.name = group.getName();
			this.description = group.getDescription();
			this.rank = group.getRank();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.danielbchapman.production.web.production.beans.ContactBean.NewContactGroup#saveGroup()
		 */
		@Override
		public void saveGroup()
		{
			if(name == null || name.length() < 3)
			{
				Utility.raiseError("Invalid Input", "A group name must be at least 3 characters.");
				return;
			}

			group.setDescription(description);
			group.setName(name);
			group.setRed(color.getRed());
			group.setGreen(color.getGreen());
			group.setBlue(color.getBlue());
			group.setAlpha(color.getAlpha());
			group.setRank(rank);

			getContactDao().saveGroup(group);

			Utility.raiseInfo("Group Added", name);
			name = "";
			description = "";
			refresh();
		}
	}

	public class NewContact implements Serializable
	{
		private static final long serialVersionUID = 3L;
		protected ArrayList<ContactAddressWrapper> addresses = new ArrayList<ContactAddressWrapper>();
		protected Contact contact = new Contact();
		protected Long contactGroup;
		protected ContactAddress newAddress = new ContactAddress();

		/**
		 * @return the addresses
		 */
		public ArrayList<ContactAddressWrapper> getAddresses()
		{
			return addresses;
		}

		public Contact getContact()
		{
			return contact;
		}

		/**
		 * @return the contactGroup
		 */
		public Long getContactGroup()
		{
			return contactGroup;
		}

		/**
		 * @return the newAddress
		 */
		public ContactAddress getNewAddress()
		{
			return newAddress;
		}

		/**
		 * Saves a new contact to the database.
		 * 
		 * @param e
		 *          JSF Event
		 */
		public void saveContact(ActionEvent e)
		{
			if(contact.getFirstName() == null || contact.getFirstName().length() < 3)
			{
				Utility.raiseError("Invalid Input", "The contact name must be at least 3 characters.");
				return;
			}

			ContactGroup g = getContactDao().getContactGroup(contactGroup);
			contact.setContactGroup(g);
			contact = getContactDao().saveContact(contact);
			System.out.println("LOGGING->" + contact.getFirstName() + " " + contact.getLastName() + " "
					+ contact.getId());
			if(!Utility.isEmpty(newAddress.getCity()) || !Utility.isEmpty(newAddress.getState())
					|| !Utility.isEmpty(newAddress.getZip()) || !Utility.isEmpty(newAddress.getLabel())
					|| !Utility.isEmpty(newAddress.getLineOne()) || !Utility.isEmpty(newAddress.getLineTwo()))
				getContactDao().addContactAddress(newAddress, contact);
			Utility.raiseInfo("Contact Added", contact.getFirstName() + " " + contact.getLastName());

			contact = new Contact();
			contactGroup = null;
			newAddress = new ContactAddress();
			refresh();
		}

		/**
		 * @param contactGroup
		 *          the contactGroup to set
		 */
		public void setContactGroup(Long contactGroup)
		{
			this.contactGroup = contactGroup;
		}
	}

	/**
	 * A UI Wrapper to handle creating groups
	 * 
	 * @author dchapman
	 * @since Aug 23, 2011
	 * @copyright The Acting Company Aug 23, 2011 @link www.theactingcompany.org
	 */
	public class NewContactGroup implements Serializable
	{
		private static final long serialVersionUID = 3L;
		protected Color color = Color.BLACK;
		protected String description;
		protected String name;
		protected int rank = 99;

		public void setStringColor(String color)
		{
			setColor(Utility.stringHexToColor(color));
		}
		
		public String getStringColor()
		{
			return Utility.colorToStringHex(color);
		}
		/**
		 * @return the color
		 */
		public Color getColor()
		{
			return color;
		}

		/**
		 * @return the description
		 */
		public String getDescription()
		{
			return description;
		}

		/**
		 * @return the name
		 */
		public String getName()
		{
			return name;
		}

		/**
		 * @return the rank
		 */
		public int getRank()
		{
			return rank;
		}

		public void saveGroup()
		{
			if(name == null || name.length() < 3)
			{
				Utility.raiseError("Invalid Input", "A group name must be at least 3 characters.");
				return;
			}

			ContactGroup group = new ContactGroup();
			group.setDescription(description);
			group.setName(name);
			group.setRed(color.getRed());
			group.setGreen(color.getGreen());
			group.setBlue(color.getBlue());
			group.setAlpha(color.getAlpha());
			group.setRank(rank);

			getContactDao().saveGroup(group);

			Utility.raiseInfo("Group Added", name);
			name = "";
			description = "";
			refresh();
		}

		/**
		 * @param color
		 *          the color to set
		 */
		public void setColor(Color color)
		{
			this.color = color;
		}

		/**
		 * @param description
		 *          the description to set
		 */
		public void setDescription(String description)
		{
			this.description = description;
		}

		/**
		 * @param name
		 *          the name to set
		 */
		public void setName(String name)
		{
			this.name = name;
		}

		/**
		 * @param rank
		 *          the rank to set
		 */
		public void setRank(int rank)
		{
			this.rank = rank;
		}
	}

	/**
	 * A simple structure that wraps the DualListModel and provides basic methods for dealing with the
	 * objects.
	 * 
	 * @author dchapman
	 * @since Aug 30, 2011
	 * @copyright The Acting Company Aug 30, 2011 @link www.theactingcompany.org
	 */
	public class SeasonListStructure implements Serializable
	{
		private static final long serialVersionUID = 1L;

		private ArrayList<IContact> source;
		private ArrayList<IContact> target;

		public SeasonListStructure(ArrayList<IContact> source, ArrayList<IContact> target)
		{
			this.source = source;
			this.target = target;
		}

		public void abortChanges(ActionEvent evt)
		{
			Utility.raiseInfo("Aborting Changes", "Resetting Model");
			seasonListStructure = null;
		}

		public void commitChanged(ActionEvent evt)
		{
			Season season = Utility.getBean(SeasonBean.class).getSeason();
			for(IContact c : source)
				getContactDao().removeContactFromSeason(c, season);

			for(IContact c : target)
				getContactDao().assignContactToSeason(c, season);

			Utility.raiseInfo("Committed", "The season contacts have been updated.");
			seasonListStructure = null;
		}

		/**
		 * Assign a contact from the season
		 * 
		 * @param evt
		 *          the action with an attribute of 'contactId'
		 */
		public void doAddContact(ActionEvent evt)
		{
			if(evt != null)
			{
				Long id = (Long) evt.getComponent().getAttributes().get("contactId");
				IContact unknown = getContactDao().getContact(id);
				if(unknown == null)
					unknown = getContactDao().getLinkedContact(id);

				if(unknown == null)
				{
					Utility.raiseError("Failed to Assign", "Unable to locate UUID for unknown entity " + id);
					return;
				}

				Season s = Utility.getBean(SeasonBean.class).getSeason();
				getContactDao().assignContactToSeason(unknown, s);
				Utility.raiseInfo("Contact Assigned",
						unknown.getFullName() + " has been added to " + s.getName());

				refresh();
			}
		}

		/**
		 * Remove a contact from the season
		 * 
		 * @param evt
		 *          the action with an attribute of 'contactId'
		 */
		public void doRemoveContact(ActionEvent evt)
		{
			if(evt != null)
			{
				Long id = (Long) evt.getComponent().getAttributes().get("contactId");
				IContact unknown = getContactDao().getContact(id);
				if(unknown == null)
					unknown = getContactDao().getLinkedContact(id);

				if(unknown == null)
				{
					Utility.raiseError("Failed to Remove", "Unable to locate UUID for unknown entity " + id);
					return;
				}

				Season s = Utility.getBean(SeasonBean.class).getSeason();
				getContactDao().removeContactFromSeason(unknown, s);
				Utility.raiseInfo("Contact Removed",
						unknown.getFullName() + " has been removed from " + s.getName());
			}
			refresh();
		}

		/**
		 * @return the source
		 */
		public ArrayList<IContact> getSource()
		{
			return source;
		}

		/**
		 * @return the target
		 */
		public ArrayList<IContact> getTarget()
		{
			return target;
		}

		/**
		 * @param source
		 *          the source to set
		 */
		public void setSource(ArrayList<IContact> source)
		{
			this.source = source;
		}

		/**
		 * @param target
		 *          the target to set
		 */
		public void setTarget(ArrayList<IContact> target)
		{
			this.target = target;
		}
	}

	public class SeasonPrintingController implements Serializable
	{
		private static final long serialVersionUID = 3L;
		private File baseDir;
		private ArrayList<PrintElement> elements;

		public SeasonPrintingController(File baseDir)
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
		 * Initialize this structure.
		 */
		public void init()
		{
			ArrayList<File> jrxmls = JasperUtility.listPossibleReports(baseDir, "sub");
			elements = new ArrayList<PrintElement>();

			for(File f : jrxmls)
				elements.add(new PrintElement(f));
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
					String seasonName = Utility.getBean(SeasonBean.class).getSeason().getName();
					String date = new SimpleDateFormat("MM-dd-yy").format(new Date());
					String fileName = seasonName.replaceAll(" ", "_") + "_contact_sheet_" + date + ".pdf";
					content = new DefaultStreamedContent(input, "application/pdf", fileName);
				}
				return content;
			}

			private byte[] getPrint()
			{
				ArrayList<ContactReportStructure> data = getContactDao().getContactSheetSeason(
						Utility.getBean(SeasonBean.class).getSeason());

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
}
