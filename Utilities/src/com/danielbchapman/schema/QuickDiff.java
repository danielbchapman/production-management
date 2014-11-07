package com.danielbchapman.schema;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class QuickDiff
{
	public static String compareFiles(File one, File two) throws IOException
	{
		String line;

		BufferedReader reader = new BufferedReader(new FileReader(one));
		ArrayList<String> hashOne = new ArrayList<String>();
		ArrayList<String> hashTwo = new ArrayList<String>();

		ArrayList<String> adds = new ArrayList<String>();
		ArrayList<String> deletes = new ArrayList<String>();

		for(; (line = reader.readLine()) != null;)
		{
			line = line.replaceAll("\t", ".").trim();
			hashOne.add(line);
		}

		reader.close();
		reader = new BufferedReader(new FileReader(two));

		for(; (line = reader.readLine()) != null;)
		{
			line = line.replaceAll("\t", ".").trim();
			hashTwo.add(line);
		}

		for(String key : hashOne)
			if(!hashTwo.contains(key))
				deletes.add(key);

		for(String key : hashTwo)
			if(!hashOne.contains(key))
				adds.add(key);

		StringBuffer buf = new StringBuffer();

		Collections.sort(adds);
		Collections.sort(deletes);

		buf.append("Adds---------------------\r\n");
		for(String key : adds)
		{
			buf.append(key);
			buf.append("\r\n");
		}
		buf.append("\r\nDeletes---------------------\r\n");
		for(String key : deletes)
		{
			buf.append(key);
			buf.append("\r\n");
		}

		return buf.toString();
	}

	public static void main(String[] args) throws IOException
	{
		System.out.println(compareFiles(new File("compare/inventory.txt"), new File(
				"compare/inventoryLive.txt")));
	}
}
