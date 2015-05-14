package com.danielbchapman.production.beans;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import javax.ejb.Stateless;

import com.danielbchapman.production.entity.EntityInstance;

/**
 * This bean is a giant security hole. It will only be allowed
 * by those authenticated as administrator
 */
@Stateless
public class ReportingBean implements ReportingBeanRemote
{
	private static final long serialVersionUID = 1L;

	public ReportingBean()
	{
	}

	@Override	
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz, Object... params)
	{
		return EntityInstance.detatch(EntityInstance.getResultList(query, clazz, params)); 

	}

	@Override
	public <T> ArrayList<T> getResultList(String query, Class<T> clazz)
	{
		return getResultList(query, clazz, new Object[]{});
	}

	@Override
	public String echo()
	{
		return "I'm a string, I should be fine";
	}

	@Override
	public <T> byte[] printReportFromDatabase(File file, Map<String, Object> parameters, ArrayList<T> data)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.ReportingBeanRemote#uploadReport(java.io.File, byte[])
	 */
	@Override
	public <T> void uploadReport(File directory, byte[] pack, String[] filesNames)
	{
		directory.mkdirs();
		FileOutputStream stream = null;
		try
		{
			directory.mkdirs();
			for(int i = 0; i < pack.length; i++)
			{
				stream = new FileOutputStream(directory.getAbsolutePath() + File.separator + filesNames[i]);
				stream.write(pack[i]);
				stream.close();				
			}
		}
		catch(FileNotFoundException e)
		{
			if(stream != null)
				try
				{
					stream.close();
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}
			throw new RuntimeException(e.getMessage(), e);
		}
		catch(IOException e)
		{
			if(stream != null)
				try
				{
					stream.close();
				}
				catch(IOException e1)
				{
					e1.printStackTrace();
				}			
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.ReportingBeanRemote#initializeReportingDirectories(java.util.ArrayList)
	 */
	@Override
	public <T> void initializeReportingDirectories(ReportingType[] types)
	{
		String root = new OptionsDao().getOptions().getReportingRoot();
		for(ReportingType t : types)
			new File(root + File.separator + t.getName() + File.separator).mkdirs();
	}

	/* (non-Javadoc)
	 * @see com.danielbchapman.production.beans.ReportingBeanRemote#getReportsForModule(java.lang.Class)
	 */
	@Override
	public <T> ArrayList<File> getReports(ReportingType type)
	{
		String directoryName = new OptionsDao().getOptions().getReportingRoot() + File.separator + type.getName() + File.separator;
		File directory = new File(directoryName);
		ArrayList<File> files = new ArrayList<File>();
		if(directory.exists())
		{
			File[] fileSet = directory.listFiles();
			for(File f: fileSet)
				if(f.getName().endsWith(".jrxml"))
					files.add(f);
		}
		else
			directory.mkdirs();
		
		return files;
	}

}
