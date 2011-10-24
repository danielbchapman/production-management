package com.danielbchapman.production.entity;

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
}
