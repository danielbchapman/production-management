package com.danielbchapman.production.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = false)
public class Show extends BaseEntity
{
  private static final long serialVersionUID = 1L;
  
  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  @Column(nullable = false)
  private Season season;
  
  private int actorsEquityCall = 60;
  private int crewCall = 90;
  private int fightCall = 45;
  private int houseCall = 30;
  @Column(length = 512)
  private String name = "<Show Name>";
  @Lob
  private String description = "";
  private int performanceLength = 120;
  private int strikeLength = 240;
}
