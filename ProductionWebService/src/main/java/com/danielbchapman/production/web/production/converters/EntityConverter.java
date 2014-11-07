package com.danielbchapman.production.web.production.converters;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.danielbchapman.production.Utility;
import com.danielbchapman.production.beans.EntityFinderRemote;
import com.danielbchapman.production.entity.BaseEntity;

/**
 * A simple converter that takes an Entity class and uses the long ID to
 * query the production database and map to that entity (if it exists) otherwise
 * null will be used.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Aug 10, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
public abstract class EntityConverter implements Converter, Serializable
{
	private static final long serialVersionUID = 3L;
	protected abstract Class<? extends BaseEntity> getEntityType();
	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	@Override
	public Object getAsObject(FacesContext ctx, UIComponent component, String value)
	{
		if(value == null || value.length() == 0)
			return null;
		try
		{
			Long id = Long.valueOf(value);
			return Utility.getObjectFromContext(EntityFinderRemote.class, Utility.Namespace.PRODUCTION).findEntity(id, getEntityType());
		}
		catch(Exception e)
		{
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/* (non-Javadoc)
	 * @see javax.faces.convert.Converter#getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2)
	{
		if(arg2 == null)
			return "null";
		
		if(arg2 instanceof BaseEntity)
			return ((BaseEntity) arg2).getId().toString();
		
		return arg2.toString();
	}

}
