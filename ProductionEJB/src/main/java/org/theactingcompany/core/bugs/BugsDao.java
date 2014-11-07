package org.theactingcompany.core.bugs;

import java.util.ArrayList;

import javax.ejb.Stateless;

import com.danielbchapman.production.entity.EntityInstance;

/**
 * Session Bean implementation class BugsDao
 */
@Stateless
public class BugsDao implements BugsDaoRemote
{

	/**
	 * Default constructor.
	 */
	public BugsDao()
	{
	}

	/* (non-Javadoc)
	 * @see org.theactingcompany.core.bugs.BugsDaoRemote#saveBug(org.theactingcompany.core.bugs.Bug)
	 */
	@Override
	public void saveBug(Bug b)
	{
		EntityInstance.saveObject(b);
	}

	/* (non-Javadoc)
	 * @see org.theactingcompany.core.bugs.BugsDaoRemote#resolveBug(org.theactingcompany.core.bugs.Bug, java.lang.String)
	 */
	@Override
	public void resolveBug(Bug b, String reason)
	{
		b.setStatus("Resolved: " + reason);
		b.setResolved(true);
		EntityInstance.saveObject(b);
	}

	/* (non-Javadoc)
	 * @see org.theactingcompany.core.bugs.BugsDaoRemote#getAllBugs()
	 */
	@Override
	public ArrayList<Bug> getAllBugs()
	{
		return EntityInstance.getResultList("SELECT b FROM Bug b", Bug.class);
	}

	/* (non-Javadoc)
	 * @see org.theactingcompany.core.bugs.BugsDaoRemote#getActiveBugs()
	 */
	@Override
	public ArrayList<Bug> getActiveBugs()
	{
		return EntityInstance.getResultList("SELECT b FROM Bug b WHERE b.resolved = ?1", Bug.class, Boolean.FALSE);
	}

	/* (non-Javadoc)
	 * @see org.theactingcompany.core.bugs.BugsDaoRemote#getResolvedBugs()
	 */
	@Override
	public ArrayList<Bug> getResolvedBugs()
	{
		return EntityInstance.getResultList("SELECT b FROM Bug b WHERE b.resolved = ?1", Bug.class, Boolean.TRUE);
	}
	
	/* (non-Javadoc)
	 * @see org.theactingcompany.core.bugs.BugsDaoRemote#submitToJira(org.theactingcompany.core.bugs.Bug)
	 */
	public void submitToJira(Bug b)
	{
		throw new RuntimeException("Submission to JIRA Is proving harder than I thought, it will be implemented soon depleting the need for this.");
	}

	/* (non-Javadoc)
	 * @see org.theactingcompany.core.bugs.BugsDaoRemote#getBug(java.lang.Long)
	 */
	@Override
	public Bug getBug(Long id)
	{
		return EntityInstance.getEm().find(Bug.class, id);
	}

}
