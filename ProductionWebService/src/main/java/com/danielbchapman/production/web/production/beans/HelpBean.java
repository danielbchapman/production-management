package com.danielbchapman.production.web.production.beans;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.theactingcompany.help.beans.HelpDaoRemote;
import org.theactingcompany.help.entity.HelpPage;

import com.danielbchapman.production.Utility;

@ViewScoped
public class HelpBean implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private HelpPage helpPage;
	private String lookupString;
	private HelpDaoRemote helpDao;
	private ArrayList<HelpPage> history;
	
	public ArrayList<HelpPage> getHistory()
	{
		if(history == null)
			history = getHelpDao().getHelpPageHistory(lookupString);
		return history;
	}

	public HelpPage getHelpPage()
	{
		if(helpPage == null)
			helpPage = getHelpDao().getOrCreateHelpPage(lookupString);
		
		return helpPage;
	}
	
	public String getLookupString()
	{
		return lookupString;
	}
	
	public void setLookupString(String lookupString)
	{
		this.lookupString = lookupString;
	}
	
	public HelpDaoRemote getHelpDao()
	{
		if(helpDao == null)
			helpDao = Utility.getObjectFromContext(HelpDaoRemote.class, Utility.Namespace.HELP);
		
		return helpDao;
	}
	
	public void refreshHelp(ActionEvent evt)
	{
		helpPage = null;
	}
	
	public void saveHelpPage(ActionEvent evt)
	{
		getHelpDao().saveHelpPage(helpPage, Utility.getUserPrinciple());
		helpPage = null;
		history = null;
	}
	
	public void replacePage(ActionEvent evt)
	{
		Long id = (Long) evt.getComponent().getAttributes().get("helpPageId");
		getHelpDao().saveHelpPage(getHelpDao().getHelpPage(id), Utility.getUserPrinciple());
		helpPage = null;
		history = null;
	}
	
}
