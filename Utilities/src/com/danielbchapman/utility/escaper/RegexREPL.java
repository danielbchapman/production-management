package com.danielbchapman.utility.escaper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RegexREPL
{
	public static void main(String ... args) throws IOException
	{
		String replaceMe = "          \"SELECT \\\"id\\\" FROM (SELECT " +
				"  sc.id AS \"id\", \" +\\n" +
				"  g.name AS \"name\", \\n +\" a             ";
		System.out.println("Replacing -> \n" + replaceMe);
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String command = null;
		while(!"exit".equals((command = in.readLine())))
		{
			try
			{
				System.out.println("----------------BEFORE");
				System.out.println(replaceMe);
				System.out.println("----------------AFTER");
				System.out.println(replaceMe.replaceAll(command, "_"));	
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		System.out.println("Exiting...");
	}
}
