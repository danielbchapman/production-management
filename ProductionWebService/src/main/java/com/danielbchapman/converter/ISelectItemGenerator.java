package com.danielbchapman.converter;

import java.io.Serializable;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

public interface ISelectItemGenerator<Type> extends Serializable
{
	/**
	 * @return the current selection
	 */
	public Object getSelection();

	/**
	 * @return a string from getTypeLabel(Type) for the selected item.
	 */
	public String getSelectionLabel();

	/**
	 * @return a list of SelectItems that use this pair set as a reference
	 */
	public SelectItem[] getSelectItems();

	/**
	 * @param type
	 * @return the label for this type
	 */
	public String getTypeLabel(Type type);

	/**
	 * Process this ValueChangeEvent via the converter so that it returs a Pair(K,V)
	 * 
	 * @param evt
	 */
	public void processValueChangeEvent(ValueChangeEvent evt);

	/**
	 * @param obj
	 *          the object to set
	 */
	public void setSelection(Object obj);

	/**
	 * Update the entity via the selection passed.
	 */
	public void updateOnSelection();
}
