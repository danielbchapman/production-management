package com.danielbchapman.production.beans;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import javax.ejb.Remote;

/**
 * <em>This bean is a giant security hole. It will only be allowed
 * by those authenticated as administrator.</em><br />
 * 
 * This bean basically allows EJBQL queries directly to the layer. As such it 
 * is protected via admin rights.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jul 6, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Remote
public interface ReportingBeanRemote extends Serializable
{
	public enum ReportingType
	{
		WEEKLY("Weekly", "week"),
		DAILY("Daily", "day"),
		CITY_SHEETS("City Sheets", "citySheet"),
		VENUES("venues", "venue"),
		BUDGET_MASTER("Budget Master", "budgetMaster"),
		BUDGET("Budget", "budget"),
		BUDGET_ESTIMATE_MASTER("Budget Estimates Master", "budgetEstimateMaster"),
		BUDGET_ESTIMATE("Budget Estimates", "budgetEstimate");
		
		ReportingType(String name, String id)
		{
			this.name = name;
			this.id = id;
		}
		String id;
		String name;
		
		public String getName()
		{
			return name;
		}
		
		public String getId()
		{
			return this.id;
		}
		
	}
	
	static final long serialVersionUID = 1L;
  /**
   * 
   * @param query
   * @param parameters the parameters indexed from 0 to X where 
   * <br/>
   * <code>
   * [0] = ?1<br />
   * [1] = ?2<br />
   *  . . .<br />
   * [length] = ?length+1<br />
   * </code>
   * @param clazz the class to query for
   * @return a list of objects after the parameters are applied  
   * 
   */
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz, Object... params);

  /**
   * 
   * @param query
   * @param clazz the class to query for
   * @return a list of objects based on th query  
   * 
   */
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz);
	
	/**
	 * @return a simple test string
	 */
	public String echo();

	/**
	 * Print a jasper report from a file that is local on the server.
	 * @param file
	 * @param parameters
	 * @param data
	 * @return
	 */
	public <T> byte[] printReportFromDatabase(File file, Map<String, Object> parameters, ArrayList<T> data);
	
	/**
	 * Upload a report to the directory
	 * @param directory the directory to save to
	 * @param pack the set of files to be written
	 * @param fileNames the names of the files to save
	 */
	public <T> void uploadReport(File directory, byte[] pack, String[] filesNames);
	
	/**
	 * Create a set of directories for each class 
	 * @param modules a list of all classes to initialize
	 */
	public <T> void initializeReportingDirectories(ReportingType[] types);
	
	/**
	 * @param module the module to search for
	 * @return a list of files for a particular module that can be used to print for it.
	 */
	public <T> ArrayList<File> getReports(ReportingType type); 
	
	

}
