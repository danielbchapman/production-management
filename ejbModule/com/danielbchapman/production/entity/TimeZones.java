package com.danielbchapman.production.entity;

import java.util.TimeZone;

public class TimeZones
{
	public static void main(String[] args)
	{
		for(String s : TimeZone.getAvailableIDs())
			System.out.println(s);
	}
}	
