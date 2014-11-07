package org.theactingcompany.core.bugs;

import java.util.ArrayList;

import javax.ejb.Remote;

@Remote
public interface BugsDaoRemote
{
	/**
	 * Save a bug into the database.
	 * @param b the bug to save  
	 * 
	 */
	public void saveBug(Bug b);
	
	/**
	 * Resolve the bug, providing a reason why.
	 * @param b the bug
	 * @param reason the reason.  
	 * 
	 */
	public void resolveBug(Bug b, String reason);
	
	/**
	 * Return the bug associated with this ID
	 * @param id the id to use
	 * @return A list of   
	 * 
	 */
	public Bug getBug(Long id);
	/**
	 * Get a list of everything in the system  
	 */
	public ArrayList<Bug> getAllBugs();
	
	/**
	 * Get a list of all the active bugs (unresolved)  
	 * 
	 */
	public ArrayList<Bug> getActiveBugs();
	
	/**
	 * Get a list of all the resolved bugs (inactive/fixed);  
	 */
	public ArrayList<Bug> getResolvedBugs();
	
	public void submitToJira(Bug b);
}
