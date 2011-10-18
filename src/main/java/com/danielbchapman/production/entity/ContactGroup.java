package com.danielbchapman.production.entity;

import java.awt.Color;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * A simple entity that represents a group of people such as a company or subgroup.
 * 
 */
@Entity
public class ContactGroup extends BaseEntity implements Comparable
{
	private static final long serialVersionUID = 1L;
	private int alpha = 255;
	private int blue = 255;
	@Column(length = 512)
	private String description = "";
	private int green = 255;
	@Column(length = 40)
	private String name = "";
	private int red = 255;
	private int rank = 99;

	public ContactGroup()
	{
		super();
	}

	/*
	 * @param o
	 * 
	 * @return
	 */
	@Override
	public int compareTo(Object o)
	{
		if(o == null)
			return 1;

		if(!(o instanceof ContactGroup))
			return 1;

		ContactGroup to = (ContactGroup) o;

		if(rank < to.rank)
			return -1;

		if(rank > to.rank)
			return 1;

		return name.compareTo(to.name);
	}

	/**
	 * @return the alpha
	 */
	public int getAlpha()
	{
		return alpha;
	}

	/**
	 * @return a new color based on the current values
	 */
	@Transient
	public Color getAwtColor()
	{
		return new Color(red, green, blue, alpha);
	}

	/**
	 * @return the blue
	 */
	public int getBlue()
	{
		return blue;
	}

	public String getDescription()
	{
		return description;
	}

	/**
	 * @return the green
	 */
	public int getGreen()
	{
		return green;
	}

	public String getName()
	{
		return name;
	}

	/**
	 * @return the rank
	 */
	public int getRank()
	{
		return rank;
	}

	/**
	 * @return the red
	 */
	public int getRed()
	{
		return red;
	}

	/**
	 * @param alpha
	 *          the alpha to set
	 */
	public void setAlpha(int alpha)
	{
		this.alpha = alpha;
	}

	/**
	 * This updates all the integer fields with the new color object.
	 * 
	 * @param c
	 *          the color to set.
	 */
	@Transient
	public void setAwtColor(Color c)
	{
		red = c.getRed();
		blue = c.getBlue();
		green = c.getGreen();
		alpha = c.getAlpha();
	}

	/**
	 * @param blue
	 *          the blue to set
	 */
	public void setBlue(int blue)
	{
		this.blue = blue;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @param green
	 *          the green to set
	 */
	public void setGreen(int green)
	{
		this.green = green;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @param rank
	 *          the rank to set
	 */
	public void setRank(int rank)
	{
		this.rank = rank;
	}

	/**
	 * @param red
	 *          the red to set
	 */
	public void setRed(int red)
	{
		this.red = red;
	}
}
