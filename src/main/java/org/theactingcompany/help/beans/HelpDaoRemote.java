package org.theactingcompany.help.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.ejb.Remote;

import org.theactingcompany.help.entity.HelpPage;

/**
 * <p>
 * This is the central help system for the database. It provides a single entity
 * that is edited via the "help page". The idea is to create extremely basic wiki 
 * knowledge for each page so that users can add information for each other.
 * </p> 
 * <p>
 * Obviously this has drawbacks, but it is simple
 * </p>
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Aug 4, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface HelpDaoRemote extends Serializable
{
	/**
	 * @param lookupString the "id" for the page that is probably the URL of the subview or view
	 * @return a HelpPage for this string, if none is available it is created.
	 * 
	 */
	public HelpPage getOrCreateHelpPage(String lookupString);
	
	/**
	 * @param id the BaseEntity#getId()
	 * @return the specific entity with this name
	 * 
	 */
	public HelpPage getHelpPage(Long id);
	
	/**
	 * Save a specific page to the database.  
	 * @param page the page to save
	 * @param userPrinciple The user that saved the page
	 */
	public void saveHelpPage(HelpPage page, String userPrinciple);
	
	/**
	 * @param lookupString
	 * @return <Return Description>  
	 * 
	 */
	public ArrayList<HelpPage> getHelpPageHistory(String lookupString);
}
