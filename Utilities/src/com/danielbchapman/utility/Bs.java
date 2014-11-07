package com.danielbchapman.utility;

import java.io.File;

public class Bs
{
	public static void main(String[] args)
	{
		System.out.println("FormatMe -> " + parseFile("Test Venue [1234] abcd"));
		File dir = new File("./test/");
		File tmp = new File("./test/testFile");
		String fileName = tmp.getName();
		File tmp2 = new File(tmp.getPath().replaceAll(fileName, "testFile2"));
		tmp.mkdirs();
		for(File f : dir.listFiles())
			System.out.println(f.getPath());

		for(File f : dir.listFiles())
			System.out.println(f.getPath());
		tmp.renameTo(tmp2);
		for(File f : dir.listFiles())
			System.out.println(f.getPath());

		System.out.println("Removing tmp " + tmp.delete());
		System.out.println("Removing tmp2 " + tmp2.delete());
		System.out.println("Removing dir " + dir.delete());
	}

	public static Long parseFile(String formatMe)
	{
		String name = formatMe;
		int start = name.indexOf('[');
		int end = name.indexOf(']');

		String parse = name.substring(start + 1, end);
		return Long.valueOf(parse);
	}
}
