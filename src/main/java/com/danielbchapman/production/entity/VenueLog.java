package com.danielbchapman.production.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * A simple implementation of a log.
 * @author dchapman
 * @since Oct 24, 2011
 * @copyright The Acting Company Oct 24, 2011 @link www.theactingcompany.org
 */
@Entity
@Data
@EqualsAndHashCode(callSuper=true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude={"venue"})
public class VenueLog extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	Venue venue;
	@Temporal(TemporalType.TIMESTAMP)
	Date date;
	@Lob
	String notes = "";
	@Column(length=64)
	String enteredBy = "";
}
