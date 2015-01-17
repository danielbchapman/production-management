package com.danielbchapman.production.entity.calendar;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import com.danielbchapman.production.entity.BaseEntity;
import com.danielbchapman.production.entity.Season;
import com.danielbchapman.production.entity.Show;
import com.danielbchapman.utility.calendars.ICalEvent;

@Entity
@ToString(callSuper = false)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class CalendarEvent extends BaseEntity
{
  private static final long serialVersionUID = 1L;

  @Getter
  @Setter
  private boolean allDay = false;
  
  @Column(name="cast_event")
  private boolean castEvent;
  
  @Column(name="crew_event")
  private boolean crew;
  
  @Getter
  @Setter
  @Column(length=256)
  private String description = "";
  
  @Getter
  @Setter
  @Lob
  private String descriptionLong = "";
  
  @Temporal(TemporalType.TIMESTAMP)
  @Getter
  @Setter
  @Column(nullable = true)
  private Date end;
  
  @Column(nullable = true)
  private Equipment equipment;
  
  @Column(nullable = true)
  private Equipment facility;
  
  @Getter
  @Setter
  private boolean publicEvent;
  
  @Getter
  @Setter
  @Column(nullable = false)
  private Show show;
  
  @Temporal(TemporalType.TIMESTAMP)
  @Getter
  @Setter
  @Column(nullable = false)
  private Date start;
  
  @Getter
  @Setter
  private String uid;  
  
  public ICalEvent toICalEvent()
  {
    throw new RuntimeException("Not implemented");
  }
}
