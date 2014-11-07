package com.danielbchapman.composite;

import java.io.Serializable;
import java.util.ArrayList;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import com.danielbchapman.converter.AbstractConverter;
import com.danielbchapman.production.Utility;
import com.danielbchapman.production.entity.PointOfInterest;

@ViewScoped
@ManagedBean(name = "testBean")
public class TestBean implements Serializable
{
	private static final long serialVersionUID = 3L;

	public String setMe = "Unset";

	private AbstractConverter<String> testConverter;

	private Double currency = null;
	private PointOfInterest pointOfInterest = new PointOfInterest(null, "Distance", "name", "notes",
			"");

	public TestBean()
	{
	}

	public void doNothing(ActionEvent evt)
	{
		Utility.raiseInfo("Does nothing", "A do nothing fire of AJAX goodness");
	}

	public void doSomething()
	{
		System.out.println(setMe);
	}

	public AbstractConverter<String> getConverter()
	{
		if(testConverter == null)
		{
			ArrayList<String> strings = new ArrayList<String>();

			for(int i = 0; i < 5; i++)
				strings.add("Test " + i);

			String selection = strings.get(3);
			testConverter = new AbstractConverter<String>(strings, selection)
			{
				private static final long serialVersionUID = 1L;

				@Override
				public String getTypeLabel(String type)
				{
					return type + " <String>";
				}

				@Override
				public void updateOnSelection()
				{
					System.out.println(getConverter().getSelection());
				}

			};
		}

		return testConverter;

	}

	/**
	 * @return the currency
	 */
	public Double getCurrency()
	{
		return currency;
	}

	/**
	 * @return the pointOfInterest
	 */
	public PointOfInterest getPointOfInterest()
	{
		return pointOfInterest;
	}

	/**
	 * @return the selection
	 */
	public String getSelection()
	{
		return getConverter().getSelection();
	}

	/**
	 * @return the setMe
	 */
	public String getSetMe()
	{
		return setMe;
	}

	/**
	 * @param currency
	 *          the currency to set
	 */
	public void setCurrency(Double currency)
	{
		this.currency = currency;
	}

	/**
	 * @param pointOfInterest
	 *          the pointOfInterest to set
	 */
	public void setPointOfInterest(PointOfInterest pointOfInterest)
	{
		this.pointOfInterest = pointOfInterest;
	}

	/**
	 * @param selection
	 *          the selection to set
	 */
	public void setSelection(String selection)
	{
		getConverter().setSelection(selection);
	}

	/**
	 * @param setMe
	 *          the setMe to set
	 */
	public void setSetMe(String setMe)
	{
		this.setMe = setMe;
	}
}
