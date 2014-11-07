package com.danielbchapman.utility.xmlsanity;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XmlSanity
{
	public static String[] EXTENSIONS = new String[] { ".xml", ".xhtml" };

	public static void main(String[] args)
	{
		ArrayList<Error> errors = new ArrayList<Error>();
		parseDirectories(new File("C:\\Users\\dchapman\\git\\ProductionSchedule\\src"), errors);
		parseDirectories(new File("C:\\Users\\dchapman\\git\\ProductionSchedule\\WebContent"), errors);
		System.out.println(errors.size() + " errors found...");
		for(Error e : errors)
			System.err.println(e.printError());
		
		System.out.println(errors.size() + " complete");
	}

	public static void parseDirectories(File f, ArrayList<Error> errors)
	{
		if (errors == null)
			errors = new ArrayList<Error>();

		if (f.isDirectory())
		{
			File[] files = f.listFiles();
			
			for(File sub : files)
				parseDirectories(sub, errors);
		}
		else
		{
			Error e = parseFile(f);
			if(e != null)
				errors.add(e);
		}
	}

	public static Error parseFile(File f)
	{
		try
		{
			if (f.exists() && validXmlExtension(f))
			{
				System.out.println("processing: " + f);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				builder.parse(f);
			} 
			else
				return null;
		}
		catch (SAXParseException e)
		{
			return new Error(f, e);
		}
		catch (SAXException e)
		{
			System.err.println(e);
			return null;
		}
		catch (ParserConfigurationException e)
		{
			System.err.println(e);
			return null;
		}
		catch (IOException e)
		{
			System.err.println(e);
			return null;
		}
		return null;
	}

	public static boolean validXmlExtension(File f)
	{
		if(!f.exists())
			return false;
		String parse = f.getName();
		for(String s : EXTENSIONS)
			if(parse.endsWith(s))
				return true;
				
		return false;
	}
}
