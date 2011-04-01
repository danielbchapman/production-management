package com.danielbchapman.production.entity;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Company
 *
 */
@Entity
public class Company implements Serializable {

	
	private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
  @Column(length=75)
  private String name;
  @Column(length=256)
  private String description;
  @OneToMany(mappedBy="company", targetEntity=Venue.class)
  private Collection<Venue> venues; 
	public Company() {
		super();
	}
	public Long getId()
	{
		return id;
	}
	public void setId(Long id)
	{
		this.id = id;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public Collection<Venue> getVenues()
	{
		return venues;
	}
	public void setVenues(Collection<Venue> venues)
	{
		this.venues = venues;
	}
   
}
