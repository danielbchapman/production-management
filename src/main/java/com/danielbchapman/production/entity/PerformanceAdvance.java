package com.danielbchapman.production.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The PerformanceAdvance is a wrapper object that contains information about a particular venue in
 * a particular year. This is a collection of information regarding the tour directly and the
 * advance. Not about the venue itself.
 * 
 * There is a fair amount of redundant information here as it is designed to present a check list
 * when talking to a venue so that no details are missed. Permanent features should be updated and
 * kept on file.
 * 
 *************************************************************************** 
 * @author Daniel B. Chapman
 * @since Jun 20, 2011
 * @link http://www.theactingcompany.org
 *************************************************************************** 
 */
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "day", "performance" }))
@EqualsAndHashCode(callSuper=false)
@Data
public class PerformanceAdvance extends BaseEntity
{
	private static final long serialVersionUID = 1L;
	private boolean complete;
	@Lob
	private String contactInformation = "";
	private Day day;
	@Column(length = 96)
	private String gaffTape = "";
	@Column(length = 96)
	private String groundPlan = "";
	@Lob
	private String hospitality = "";
	@Lob
	private String lightingNotes = "";
	@Column(length = 96)
	private String lightPlot = "";
	@Lob
	private String loadingDock = "";
	@Lob
	private String maskingNotes = "";
	@OneToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH }, optional = true)
	private Performance performance;
	@Column(length = 96)
	private String rigging = "";
	@Lob
	private String scenicNotes = "";
	@Column(length = 96)
	private String section = "";
	@Column(length = 96)
	private String shorePower = "";
	@Lob
	private String soundNotes = "";
	@Column(length = 128)
	private String wardrobeIroningBoard = "";
	@Column(length = 128)
	private String wardrobeLaundry = "";
	@Lob
	private String wardrobeNotes = "";
	@Column(length = 128)
	private String wardrobeSteamer = "";
	
	private Venue venue;

	/**
	 * @return true if this production is an orphan, else return false.
	 */
	public boolean isOrphan()
	{
		if(getPerformance() == null)
			return true;
		else
			return false;
	}
}
