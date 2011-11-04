package com.danielbchapman.production;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

import com.danielbchapman.production.web.production.beans.AdministrationBean;

public abstract class AbstractPrintController
{
	private String reportingDirectoryName;
	private File baseDir;
	private String[] exclusions;
	private ArrayList<PrintElement> elements;

	public AbstractPrintController(String reportingDirectoryName, String... exclusions)
	{
		this.reportingDirectoryName = reportingDirectoryName;
		this.exclusions = exclusions;
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
		String root = Utility.getBean(AdministrationBean.class).getReportingDocumentRoot();
		baseDir = new File(root + File.separator + reportingDirectoryName + File.separator);
		ArrayList<File> jrxmls = JasperUtility.listPossibleReports(baseDir, exclusions);
		elements = new ArrayList<PrintElement>();

		for(File f : jrxmls)
			elements.add(new PrintElement(f));
	}

	protected abstract Collection<?> getData();

	// protected abstract Map<String, Object> getParameters();
	// protected abstract Map<String, Object> getParameters();
	/**
	 * This map will have the properties
	 * <ul>
	 * <li>FILE_PATH : set to the directory determined by the baseDirector in construction + the
	 * reporting root</li>
	 * <li>SUBREPORT_DIR : set to FILE_PATH</li>
	 * </ul>
	 * 
	 * @return a list of custom parameters for the report.
	 */
	protected abstract Map<String, Object> getParameters();

	protected abstract String getReportName();

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
			return "./" + reportingDirectoryName + "/" + file.getName();
		}

		public StreamedContent getPdf()
		{
			if(content == null)
			{
				byte[] bytes = getPrint();
				final ByteArrayInputStream input = new ByteArrayInputStream(bytes);
				String name = getReportName();
				String date = new SimpleDateFormat("MM-dd-yy").format(new Date());
				String fileName = name + "_" + date + ".pdf";
				content = new DefaultStreamedContent(input, "application/pdf",
						fileName.replaceAll(" ", "_"));
			}
			return content;
		}

		/**
		 * Destroy the content associated with the previous object so the stream can be reset and
		 * printed.
		 * 
		 * @param evt
		 */
		public void resetPrint(ActionEvent evt)
		{
			content = null;
		}

		private byte[] getPrint()
		{
			Collection<?> data = getData();

			Map<String, Object> params = getParameters();
			if(params == null)
				params = new HashMap<String, Object>();

			File root = new File(Utility.getBean(AdministrationBean.class).getReportingDocumentRoot());
			String path = new File(root.getAbsoluteFile() + File.separator + reportingDirectoryName
					+ File.separator).getAbsolutePath()
					+ File.separator;
			params.put("FILE_PATH", path);
			params.put("SUBREPORT_DIR", path);
			byte[] print = JasperUtility.printReportAsPDF(file, params, data);

			return print;
		}
	}
}
