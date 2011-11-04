package com.danielbchapman.converter;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

/**
 * 
 * @author dchapman
 * @since Sep 27, 2011
 * @copyright The Acting Company Sep 27, 2011 @link www.theactingcompany.org
 */
public abstract class AbstractConverter<Type> implements ISelectItemGenerator<Type>
{
	private static final long serialVersionUID = 1L;
	private int val = 0;
	private final LinkedHashMap<Integer, Type> map;
	private final SelectItem[] items;
	private Type selection;

	public AbstractConverter(final Collection<Type> values, final Type selection)
	{
		if(values == null)
			throw new IllegalArgumentException("Null values passed.");

		map = new LinkedHashMap<Integer, Type>();
		items = new SelectItem[values.size()];
		Iterator<Type> iterator = values.iterator();
		for(int i = 0; i < values.size(); i++)
		{
			Type t = iterator.next();
			map.put(new Integer(i), t);
			items[i] = new SelectItem(i, getTypeLabel(t));
		}

		this.selection = selection;
	}

	/**
	 * @return the selection
	 */
	@Override
	public Type getSelection()
	{
		return selection;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.danielbchapman.converter.ISelectItemGenerator#getSelectionLabel()
	 */
	@Override
	public String getSelectionLabel()
	{
		return selection == null ? "null" : getTypeLabel(selection);
	}

	/**
	 * @return a list of SelectItems that use this pair set as a reference
	 */
	@Override
	public SelectItem[] getSelectItems()
	{
		return items;
	}

	@Override
	public void processValueChangeEvent(ValueChangeEvent evt)
	{
		Integer key = Integer.valueOf((String) evt.getNewValue());
		selection = map.get(key);
		updateOnSelection();// Forces the user to deal with the selection
	}

	/**
	 * @param selection
	 *          the selection to set
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setSelection(Object selection)
	{
		this.selection = (Type) selection;
	}
}
