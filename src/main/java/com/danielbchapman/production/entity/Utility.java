package com.danielbchapman.production.entity;

import java.awt.Color;

/**
 * A simple utility class
 * 
 * @author dchapman
 * @since Oct 18, 2011
 * @copyright The Acting Company Oct 18, 2011 @link www.theactingcompany.org
 */
public class Utility
{

	/**
	 * @param one
	 * @param two
	 * @return the standard implementation of Date.compareTo except this deals with null checks.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Comparable> int compare(T one, T two)
	{
		if(one == null && two == null)
			return 0;
		if(one == null)
			return -1;
		if(two == null)
			return 1;

		if(two.getClass().isAssignableFrom(one.getClass()))
			return one.compareTo(two);
		else
			throw new IllegalArgumentException(
					"Please use classes that should be compared, you provided " + one.getClass() + ", "
							+ two.getClass() + "(" + one + ", " + two + ")");
	}

	/**
	 * @param one
	 *          comparable one
	 * @param two
	 *          comparable two
	 * @return return the larger of the pair
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends Comparable> T compareReturnLarger(T one, T two)
	{
		if(compare(one, two) == 1)
			return one;
		else
			return two;
	}

	/**
	 * @param one
	 *          comparable one
	 * @param two
	 *          comparable two
	 * @return return the smaller of the pair
	 */
	@SuppressWarnings("rawtypes")
	public static <T extends Comparable> T compareReturnSmaller(T one, T two)
	{
		if(compare(one, two) != 1)
			return two;
		else
			return one;
	}
	
	public static String colorToStringHex(int red, int green, int blue)
	{
		//@formatter:off
		return 
				  Integer.toHexString(0xF & red >> 4)
				+ Integer.toHexString(0x0F & red)
				+ Integer.toHexString(0xF & green >> 4)
				+ Integer.toHexString(0x0F & green)
				+ Integer.toHexString(0xF & blue >> 4)
				+ Integer.toHexString(0x0F & blue);
		//@formatter:on				
	}

	public static Color stringHexToColor(String color)
	{
		if(color == null)
			return new Color(Color.RED.getRGB());
		
		char[] colors = color.toCharArray();
		if(colors.length < 6)
		{
			System.out.println("Color not set " + color + " was not of length 6");
			return new Color(Color.RED.getRGB());
		}
			
		
		int red = Integer.parseInt(new String(new char[] { colors[0], colors[1] }), 16);
		int green = Integer.parseInt(new String(new char[] { colors[2], colors[3] }), 16);
		int blue = Integer.parseInt(new String(new char[] { colors[4], colors[5] }), 16);
		
		//Intentional, do not optimize, this way the NFException will be thrown before setting values.
		return new Color(red, green, blue, 255);
	}	
}
