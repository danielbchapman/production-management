package com.danielbchapman.production.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Entity implementation class for Entity: VenueLog
 *
 */
@Entity
public class VenueLog extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	
	Venue venue;
  @Temporal(TemporalType.TIMESTAMP)
  Date date;
  @Column(length=4096)
  String notes;
  public Venue getVenue()
  {
    return venue;
  }
  public void setVenue(Venue venue)
  {
    this.venue = venue;
  }
  public Date getDate()
  {
    return date;
  }
  public void setDate(Date date)
  {
    this.date = date;
  }
  public String getNotes()
  {
    return notes;
  }
  public void setNotes(String notes)
  {
    this.notes = notes;
  }
}
