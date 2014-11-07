package com.danielbchapman.production;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

public class JasperUtility
{
	/**
	 * This method attempts to print a report from a pre-compiled .jasper file. If the file is older
	 * than the XML on file it will be removed and recompiled.
	 * 
	 * @param file
	 *          the file to update
	 */
	public static JasperReport getReportUpdateCompilation(File file)
	{
		JasperReport report = null;

		if(file.getName().endsWith(".jasper"))
		{
			String baseName = file.getName().replaceAll(".jasper", ".jrxml");
			File test = new File(baseName);

			if(test.exists() && test.lastModified() > file.lastModified())
			{
				file.delete();
				try
				{
					report = JasperCompileManager.compileReport(new FileInputStream(test));
				}
				catch(FileNotFoundException e)
				{
					throw new RuntimeException(e.getMessage(), e);
				}
				catch(JRException e)
				{
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			else
				try
				{
					report = (JasperReport) JRLoader.loadObject(file);
				}
				catch(JRException e)
				{
					throw new RuntimeException(e.getMessage(), e);
				}
		}
		else
			if(file.getName().endsWith(".jrxml"))
			{
				try
				{
					report = JasperCompileManager.compileReport(file.getAbsolutePath());
					//report = JasperCompileManager.compileReportTo(new FileInputStream(file));
				}
//				catch(FileNotFoundException e)
//				{
//					throw new RuntimeException(e.getMessage(), e);
//				}
				catch(JRException e)
				{
					throw new RuntimeException(e.getMessage(), e);
				}
				catch(Throwable e)
				{
					throw new RuntimeException(e.getMessage(), e);
				}
			}
			else
				throw new RuntimeException("The file " + file
						+ " is neither a .jasper or a .jrxml file. This indicates a poor target.");

		return report;
	}

	/**
	 * @param parent
	 *          the file to search
	 * @return a set of unique base files (.jrxml has preference to .jasper) for a single directory.
	 *         This allows a listing of alternative reporting. If the report has "sub or SubReport" in
	 *         it it will be ignored.
	 */
	public static ArrayList<File> listPossibleReports(File parent, String... exclusions)
	{
		HashMap<String, File> files = new HashMap<String, File>();
		File[] children = parent.listFiles();

		if(children == null)
			return new ArrayList<File>();
		
		for(File f : children)
		{
			boolean include = true;
			if(exclusions != null)
				for(String exclude : exclusions)
					if(f.getName().toLowerCase().contains(exclude.toLowerCase()))
						include = false;

			if(f.getName().endsWith(".jasper") && include)
			{
				String baseName = f.getName().replaceAll(".jasper", "");
				if(!files.containsKey(baseName))
					files.put(baseName, f);
			}

			if(f.getName().endsWith(".jrxml") && include)
			{
				String baseName = f.getName().replaceAll(".jrxml", "");
				files.put(baseName, f); // stomp .jasper
			}
		}

		ArrayList<File> cleaned = new ArrayList<File>();
		for(File f : files.values())
			cleaned.add(f);

		Collections.sort(cleaned);
		return cleaned;
	}

	/**
	 * @param file
	 *          the file to base this on (.jasper or .jrxml)
	 * @param parameters
	 *          the parameters for the report
	 * @param datasource
	 *          the collection of data to use
	 * @return the report as a PDF in bytes
	 */
	public static byte[] printReportAsPDF(File file, Map<String, Object> parameters,
			Collection<?> datasource)
	{
		System.out.println("Printing: " + file.getAbsolutePath());
		for(String s : parameters.keySet())
			System.out.println(s + ", " + parameters.get(s));

		JasperReport report = getReportUpdateCompilation(file);
		if(report == null)
			return null;

		JasperPrint print;
		if(datasource != null)
			try
			{
				print = JasperFillManager.fillReport(report, parameters, new JRBeanCollectionDataSource(
						datasource));
			}
			catch(JRException e)
			{
				throw new RuntimeException(e.getMessage(), e);
			}
		else
			try
			{
				print = JasperFillManager.fillReport(report, parameters, new JREmptyDataSource());
			}
			catch(JRException e)
			{
				throw new RuntimeException(e.getMessage(), e);
			}
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try
		{
			JasperExportManager.exportReportToPdfStream(print, out);
		}
		catch(JRException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}

		return out.toByteArray();
	}

}
