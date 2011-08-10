package org.theactingcompany.help.beans;

import java.util.ArrayList;
import java.util.Date;

import javax.ejb.Stateless;

import org.theactingcompany.help.entity.EntityInstance;
import org.theactingcompany.help.entity.HelpPage;

/**
 * Session Bean implementation class HelpDao
 */
@Stateless
public class HelpDao implements HelpDaoRemote
{

	/**
	 * Default constructor.
	 */
	public HelpDao()
	{
	}

	/* (non-Javadoc)
	 * @see org.theactingcompany.help.beans.HelpDaoRemote#getOrCreateHelpPage(java.lang.String)
	 */
	@Override
	public HelpPage getOrCreateHelpPage(String lookupString)
	{
		ArrayList<HelpPage> results = getHelpPageHistory(lookupString);

		HelpPage ret;
		if(results.size() == 0)
		{
			ret = new HelpPage();
			ret.setLookupString(lookupString);
			ret.setLastUpdated(new Date());
			ret.setLastUpdatedBy("System");
			EntityInstance.saveObject(ret);
		}
		else
			ret = results.get(0); //select the top (most recent) help page
		
		return ret;
	}

	/* (non-Javadoc)
	 * @see org.theactingcompany.help.beans.HelpDaoRemote#getHelpPage(java.lang.Long)
	 */
	@Override
	public HelpPage getHelpPage(Long id)
	{
		return EntityInstance.getEm().find(HelpPage.class, id);
	}

	/* (non-Javadoc)
	 * @see org.theactingcompany.help.beans.HelpDaoRemote#saveHelpPage(org.theactingcompany.help.entity.HelpPage, java.lang.String)
	 */
	@Override
	public void saveHelpPage(HelpPage page, String userPrinciple)
	{
		HelpPage newPage = new HelpPage();
		newPage.setHelpPage(page.getHelpPage());
		newPage.setLastUpdated(new Date());
		newPage.setLastUpdatedBy(userPrinciple);
		newPage.setLookupString(page.getLookupString());
		EntityInstance.saveObject(newPage);
	}

	@Override
	public ArrayList<HelpPage> getHelpPageHistory(String lookupString)
	{
		return EntityInstance.getResultList(
				"SELECT h FROM HelpPage h WHERE h.lookupString = ?1 ORDER BY h.lastUpdated DESC", 
				HelpPage.class, 
				lookupString);

	}
}
