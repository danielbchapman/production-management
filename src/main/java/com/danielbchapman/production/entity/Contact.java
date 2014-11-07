package com.danielbchapman.production.entity;

import javax.persistence.Entity;

/**
 * A simple prime entity that implements a set of contacts
 * 
 */
@Entity
public class Contact extends AbstractContact implements IContact
{
	private static final long serialVersionUID = 1L;

}
