package com.danielbchapman.production.beans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Venue;
import com.danielbchapman.production.entity.VenueLog;

/**
 * A bean for accessing venue information. This includes methods for accessing files
 * from the disk as well as information about the venue. This allows tracking per production.
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jan 1, 2011 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface VenueDaoRemote extends Serializable
{
	static final long serialVersionUID = 1L;
  public final static String ELECTRICS_DOCUMENTS = "electrics";
  public final static String SCENIC_DOCUMENTS = "scenery";
  public final static String GENERAL_DOCUMENTS = "general";
  public final static String WARDROBE_DOCUMENTS = "wardrobe";
  public final static String SOUND_DOCUMENTS = "sound";
  
  /**
   * Save this venue information
   * @param venue the venue to save  
   */
  public void saveVenue(Venue venue);
  
  /**
   * @param user the username to search for a related. As a username is derived from a venue for
   * the role venue this is 1-1.
   * @return the venue to view.  
   * 
   */
  public Venue getVenue(String user);
  
  /**
   * @param production
   * @return a list of all venues for this production  
   * 
   */
  public ArrayList<Venue> getVenues(Season production);

  /**
   * @return a list of all venues ordered by performace date  
   * 
   */
  public ArrayList<Venue> getAllVenues();
  
  /**
   * @return the root node for the general department  
   */
  public File getRootGeneralFolder();
  
  /**
   * @param id the id to find
   * @return the venue for a specific producution  
   * 
   */
  public Venue getVenue(Long id);
  
  /**
   * Adds a log entry to the venue logs.
   * @param value the notes (plaintext) for the venue
   * @param venue the venue for which to assign these notes
   */
  public void addLogEntry(String value, Venue venue);
  
  /**
   * 
   * @param venue, the venue to look for
   * @return a list of the log entries in order of creation for historical purposes.
   * 
   */
  public ArrayList<VenueLog> getLogEntries(Venue venue);
 
}
