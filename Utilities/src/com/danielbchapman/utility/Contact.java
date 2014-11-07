package com.danielbchapman.utility;

public class Contact
{
	public static String getHeaders()
	{
		char tab = '\t';
		StringBuilder builder = new StringBuilder();
		builder.append("name");
		builder.append(tab);
		builder.append("home");
		builder.append(tab);
		builder.append("cell");
		builder.append(tab);
		builder.append("email");
		builder.append(tab);
		builder.append("position");
		builder.append(tab);
		builder.append("year");

		return builder.toString();
	}

	public static String stripIllegal(String string)
	{
		if(string == null)
			return string;

		String processed = string.replaceAll("\"", "'");
		processed = processed.replaceAll("\t", " ");
		return processed;
	}

	String name;

	String home;

	String cell;

	String email;

	String position;

	String year;

	public Contact(String name, String home, String cell, String email, String position, String year)
	{
		this.name = name;
		this.home = home;
		this.cell = cell;
		this.email = email;
		this.position = position;
		this.year = year;
	}

	/**
	 * @return the cell
	 */
	public String getCell()
	{
		return cell;
	}

	/**
	 * @return the email
	 */
	public String getEmail()
	{
		return email;
	}

	/**
	 * @return the home
	 */
	public String getHome()
	{
		return home;
	}

	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @return the position
	 */
	public String getPosition()
	{
		return position;
	}

	/**
	 * @return the year
	 */
	public String getYear()
	{
		return year;
	}

	/**
	 * @param cell
	 *          the cell to set
	 */
	public void setCell(String cell)
	{
		this.cell = cell;
	}

	/**
	 * @param email
	 *          the email to set
	 */
	public void setEmail(String email)
	{
		this.email = email;
	}

	/**
	 * @param home
	 *          the home to set
	 */
	public void setHome(String home)
	{
		this.home = home;
	}

	/**
	 * @param name
	 *          the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param position
	 *          the position to set
	 */
	public void setPosition(String position)
	{
		this.position = position;
	}

	/**
	 * @param year
	 *          the year to set
	 */
	public void setYear(String year)
	{
		this.year = year;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		char tab = '\t';
		StringBuilder builder = new StringBuilder();

		builder.append(stripIllegal(name));
		builder.append(tab);
		builder.append(stripIllegal(home));
		builder.append(tab);
		builder.append(stripIllegal(cell));
		builder.append(tab);
		builder.append(stripIllegal(email));
		builder.append(tab);
		builder.append(stripIllegal(position));
		builder.append(tab);
		builder.append(stripIllegal(year));

		return builder.toString();
	}
}