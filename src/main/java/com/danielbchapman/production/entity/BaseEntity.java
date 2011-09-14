package com.danielbchapman.production.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.theactingcompany.persistence.Indentifiable;

/**
 * A simple base object to be extended that handles the ID etc...
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman <br />
 *         <i><b>Light Assistant</b></i> copyright Daniel B. Chapman
 * @since Dec 28, 2010 2010
 * @version 2 Development
 * @link http://www.lightassistant.com
 *************************************************************************** 
 */
@MappedSuperclass
public abstract class BaseEntity implements Indentifiable, Serializable
{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Clone this entity via serialization ignoring the ID.
	 * 
	 * @param T
	 *          the entity to clone
	 * @return
	 * @return a clone of this entity
	 */
	@Override
	public BaseEntity clone()
	{
		ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		try
		{
			ObjectOutputStream out = new ObjectOutputStream(bytes);
			out.writeObject(this);
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes.toByteArray()));
			BaseEntity clone = (BaseEntity) in.readObject();
			/* This is key, do not remove */
			clone.setId(null);
			return clone;
		}
		catch(IOException e)
		{
			throw new RuntimeException("[Fatal Error] cloning entity\n\t" + this + ": " + e.getMessage(),
					e);
		}
		catch(ClassNotFoundException e)
		{
			throw new RuntimeException(
					"[Fatal Error] cloning entity\n\r, (we can't find 'this.getClass()'\n): " + this + ": "
							+ e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if(obj == null)
			return false;

		if(!getClass().equals(obj.getClass()))
			return false;

		if(getId().equals(((BaseEntity) obj).getId()))
			return true;

		return false;
	}

	@Override
	public final Long getId()
	{
		return id;
	}

	@Override
	public final void setId(Long id)
	{
		this.id = id;
	}

	@Override
	public String toString()
	{
		return this.getClass() + " ID:'" + id + "' " + super.toString();
	}
}
