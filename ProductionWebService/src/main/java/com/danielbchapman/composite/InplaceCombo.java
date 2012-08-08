package com.danielbchapman.composite;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.danielbchapman.converter.AbstractConverter;
import com.danielbchapman.converter.Pair;

@RequestScoped
@ManagedBean(name = "inplaceCombo")
public class InplaceCombo
{

	@SuppressWarnings("rawtypes")
	public void onComboChange(ValueChangeEvent evt)
	{
		UIComponent parent = UIComponent.getCompositeComponentParent(evt.getComponent());
		parent.getAttributes().put("comboRendered", Boolean.FALSE);
		AbstractConverter converter = (AbstractConverter) parent.getAttributes().get("converter");
		converter.processValueChangeEvent(evt);
		
		if(evt.getNewValue() instanceof Pair)
		{
			Pair pair = (Pair) evt.getNewValue();
			parent.getAttributes().put("label", pair.getV());
			parent.getAttributes().put("value", pair.getK());
			System.out.println("Pair: " + pair);
		}
		else
			parent.getAttributes().put("label", "@[raw]" + evt.getNewValue());
	}

	public void onLinkSelect(ActionEvent evt)
	{
		UIComponent.getCompositeComponentParent(evt.getComponent()).getAttributes()
				.put("comboRendered", Boolean.TRUE);
	}

}
