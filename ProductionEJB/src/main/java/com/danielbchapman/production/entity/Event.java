package com.danielbchapman.production.entity;

import javax.persistence.Entity;

/**
 * An event is the core item of the Calendar. This provides something that exists
 * in a real time on the schedule. Each field is relatively self explanatory.
 * 
 * <br /> An event that has a cast or crew boolean will be specific to those elements. 
 * If an event lacks a crew/cast specification it is considered general and will
 * be printed on the schedules.
 *
 ***************************************************************************
 * @author Daniel B. Chapman 
 * @since Jun 20, 2011
 * @link http://www.theactingcompany.org
 ***************************************************************************
 */
@Entity
public class Event extends EventMapping
{
	private static final long serialVersionUID = 1L;
}
