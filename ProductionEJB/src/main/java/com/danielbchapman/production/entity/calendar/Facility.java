package com.danielbchapman.production.entity.calendar;

import javax.persistence.Entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.danielbchapman.production.entity.BaseEntity;

@Entity
@Data()
@EqualsAndHashCode(callSuper = true)
public class Facility extends BaseEntity
{
  private static final long serialVersionUID = 1L;
  
  private String name;
  private String description;

}
