package com.danielbchapman.production.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * A point of interest is simply something such as a store, restaurant or something
 * special about a city that someone may be interest in. This is intended as a 
 * way for the cast and crew to have an easier time upon entering a place. 
 * 
 * @author dchapman
 * @since Oct 17, 2011
 * @copyright The Acting Company Oct 17, 2011 @link www.theactingcompany.org
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"name", "city"}))
@EqualsAndHashCode(callSuper=true)
public class PointOfInterest extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	private City city;
	private String distance = "";
	@Lob
	private String name = "";
	@Lob
	private String notes = "";
	@Column(length=20)
	private String phone = "";
}
