package com.danielbchapman.utility;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ContactProcessing
{
	public static void main(String[] args)
	{
		File[] files = new File("input/").listFiles();
		ArrayList<LinkedHashMap<String, Contact>> contactSets = new ArrayList<LinkedHashMap<String, Contact>>();

		for(int i = 0; i < files.length; i++)
			try
			{
				contactSets.add(processFile(files[i]));
			}
			catch(IOException e)
			{
				System.err.println("Unable to process file: " + files[i].getName());
				e.printStackTrace();
			}

		for(int i = 0; i < contactSets.size(); i++)
			for(String key : contactSets.get(i).keySet())
				System.out.println(contactSets.get(i).get(key));

		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter("contactOut.txt"));
			writer.write(Contact.getHeaders());
			writer.write("\r\n");

			for(int i = 0; i < contactSets.size(); i++)
				for(String key : contactSets.get(i).keySet())
				{
					writer.write(contactSets.get(i).get(key).toString());
					writer.write("\r\n");// windows
				}
			writer.flush();
			close(writer);

			writer = new BufferedWriter(new FileWriter("contactOutEmail.txt"));
			writer.write(Contact.getHeaders());
			writer.write("\r\n");

			for(int i = 0; i < contactSets.size(); i++)
				for(String key : contactSets.get(i).keySet())
				{
					Contact c = contactSets.get(i).get(key);
					if(c.getEmail().trim().length() > 0)
					{
						writer.write(c.toString());
						writer.write("\r\n");// windows
					}
				}

			writer.flush();
			close(writer);

			writer = new BufferedWriter(new FileWriter("contactOutEmailList.txt"));
			HashMap<String, String> emailName = new HashMap<String, String>();
			for(int i = 0; i < contactSets.size(); i++)
				for(String key : contactSets.get(i).keySet())
				{
					Contact c = contactSets.get(i).get(key);
					emailName.put(c.getEmail().trim().toLowerCase(), c.getName().trim());
				}

			// Quick attempt to block duplicates
			for(String email : emailName.keySet())
			{
				String name = emailName.get(email);
				writer.write('<');
				writer.write(name);
				writer.write('>');
				writer.write(' ');
				writer.write(email);
				writer.write(";\r\n");// windows
			}
			writer.flush();
			close(writer);
		}
		catch(IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static LinkedHashMap<String, Contact> processFile(File file) throws IOException
	{
		LinkedHashMap<String, Contact> contacts = new LinkedHashMap<String, Contact>();
		/*
		 * [0] = name [1] = home [2] = cell [3] = position [4] = email [5] = year
		 */
		int[] indecies = null;

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line;
		for(; (line = reader.readLine()) != null;)
		{
			String[] tokens = line.split("\t"); // bad TSV split

			if(indecies == null)
			{
				indecies = new int[6];
				for(int i = 0; i < tokens.length; i++)
				{
					if("name".equalsIgnoreCase(tokens[i] == null ? "fail" : tokens[i].trim()))
						indecies[0] = i;
					if("home".equalsIgnoreCase(tokens[i] == null ? "fail" : tokens[i].trim()))
						indecies[1] = i;
					if("cell".equalsIgnoreCase(tokens[i] == null ? "fail" : tokens[i].trim()))
						indecies[2] = i;
					if("position".equalsIgnoreCase(tokens[i] == null ? "fail" : tokens[i].trim()))
						indecies[3] = i;
					if("email".equalsIgnoreCase(tokens[i] == null ? "fail" : tokens[i].trim()))
						indecies[4] = i;
					if("year".equalsIgnoreCase(tokens[i] == null ? "fail" : tokens[i].trim()))
						indecies[5] = i;
				}
				continue;
			}

			String name = "";
			String home = "";
			String cell = "";
			String position = "";
			String email = "";
			String year = "";

			try
			{
				name = tokens[indecies[0]];
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				// Do nothing--slows this down a bit
			}
			try
			{
				home = tokens[indecies[1]];
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				// Do nothing--slows this down a bit
			}
			try
			{
				cell = tokens[indecies[2]];
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				// Do nothing--slows this down a bit
			}
			try
			{
				position = tokens[indecies[3]];
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				// Do nothing--slows this down a bit
			}
			try
			{
				email = tokens[indecies[4]];
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				// Do nothing--slows this down a bit
			}
			try
			{
				year = tokens[indecies[5]];
			}
			catch(ArrayIndexOutOfBoundsException ex)
			{
				// Do nothing--slows this down a bit
			}

			Contact tmpContact = new Contact(name, home, cell, email, position, year);
			contacts.put(tmpContact.getName(), tmpContact);
		}

		close(reader);
		return contacts;
	}

	private final static void close(Closeable closeable)
	{
		try
		{
			closeable.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
