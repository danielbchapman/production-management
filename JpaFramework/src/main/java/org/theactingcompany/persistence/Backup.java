package org.theactingcompany.persistence;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringBufferInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * A class that provides methods to backup the database into binary forms and then reflect that into
 * a new database via serialization.
 * 
 * Painful but do-able.
 * 
 * @author dchapman
 * @since Aug 9, 2012
 * @copyright The Acting Company Aug 9, 2012
 */
public class Backup
{
	public final static String DATABASE = "database";
	public final static String STRUCTURE = "structure";
	public final static String COLUMN = "column";
	public final static String DATA = "data";
	public final static String CLASS = "class";
	public final static String INDEX = "index";
	public final static String TYPE = "type";
	public final static String TABLE = "table";
	public final static String ROW = "row";
	public final static String ROWS = "rows";
	public final static String EMPTY = "empty";
	public final static String NAME = "name";

	/**
	 * Write a table that looks like:
	 * 
	 * <pre>
	 *  table
	 *    structure
	 *      ...
	 *      column class="x"
	 *      /column
	 *      ...
	 *    /structure  
	 *    
	 *    rows  
	 *      ...
	 *      row
	 *        data //column @ x
	 *          value
	 *        /data
	 *      /row
	 *      ...
	 *    /rows
	 *  /table
	 * </pre>
	 * 
	 * @param tableName
	 *          the table to write
	 * @param directory
	 *          the directory to save the xml
	 * @param instance
	 *          the instance to use for a connection
	 */
	public static String backupToXml(String database, AbstractEntityInstance instance, String ... tables)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;
			try
			{
				builder = factory.newDocumentBuilder();
			}
			catch(ParserConfigurationException e)
			{
				throw new RuntimeException(e.getMessage(), e);
			}
			
			Document doc = builder.newDocument();
			doc.setXmlStandalone(true);
			doc.setXmlVersion("1.0");
			Element root = doc.createElement(DATABASE);
			root.setAttribute(NAME, database);
			doc.appendChild(root);
			
			for(String name : tables)
			{
				EntityManager em = instance.getEm();

				em.getTransaction().begin();
				Connection con = em.unwrap(Connection.class);
				
				Statement statement = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);				
				ResultSet tableSet = statement.executeQuery("SELECT * FROM " + name);
				Element table = doc.createElement(TABLE);
				table.setAttribute(NAME, name);
				table.appendChild(writeStructure(tableSet, doc));
				table.appendChild(writeRows(tableSet, doc));
				root.appendChild(table);
				
				em.getTransaction().rollback();
			}
			
			try
			{
				TransformerFactory transformFactory = TransformerFactory.newInstance();
				Transformer transform = transformFactory.newTransformer();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				StreamResult result = new StreamResult(out);
				transform.transform(new DOMSource(doc), result);
				
				StringBuilder build = new StringBuilder(2048);
				BufferedReader reader = new BufferedReader( new InputStreamReader(new ByteArrayInputStream(out.toByteArray())));
				String line = null;
				
				while((line = reader.readLine()) != null)
				{
					build.append(line);
					build.append("\n\r");
				}
				
				return build.toString();
			}
			catch(TransformerException e)
			{
				throw new RuntimeException(e.getMessage(), e);
			}
			catch(IOException e)
			{
				//Will not fire--this is from the string buffer
				throw new RuntimeException(e.getMessage(), e);
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	private static Element writeStructure(ResultSet results, Document doc)
	{
		Element structure = doc.createElement(STRUCTURE);
		
		try
		{
			ResultSetMetaData meta = results.getMetaData();
			
			int count = meta.getColumnCount();
			for(int i = 1; i <= count; i++)
			{
				Element column = doc.createElement(COLUMN);
				column.setAttribute(CLASS, meta.getColumnClassName(i));
				column.setAttribute(TYPE, meta.getColumnTypeName(i));
				column.setAttribute(INDEX, Integer.toString(i));
				column.setAttribute(NAME, meta.getColumnName(i));
				structure.appendChild(column);
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return structure;
	}
	
	private static Element writeRows(ResultSet results, Document doc)
	{
		Element rows = doc.createElement(ROWS);
		
		try
		{
			ResultSetMetaData meta = results.getMetaData();
			int columns = meta.getColumnCount();
			if(results.first())
			{
				do{
					Element row = writeRow(results, doc, columns);
					
					System.out.println(rows);
					rows.appendChild(row);
				}
				while(results.next());				
			}
		}
		catch(SQLException e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return rows;
	}
	
	private static Element writeRow(ResultSet results, Document doc, int columnCount)
	{
		Element row = doc.createElement(ROW);

		for(int i = 1; i <= columnCount; i++)
			row.appendChild(data(safeReadCell(results, i), doc));

		return row;
	}

	private static Object safeReadCell(ResultSet results, int index)
	{
		Object value = null;
		try
		{
			value = results.getObject(index);
		}
		catch(SQLException e)
		{
			// Who cares?
		}

		return value;
	}

	private static Element data(Object element, Document doc)
	{
		Element node = doc.createElement("data");
		if(element != null && element.toString().length() == 0)
			node.setAttribute(EMPTY, Boolean.TRUE.toString());
		
		node.setTextContent(element == null ? "null" : element.toString());
		
		return node;
	}
	
	public void restore(InputStream xmlStream, AbstractEntityInstance entities)
	{
	}
}
