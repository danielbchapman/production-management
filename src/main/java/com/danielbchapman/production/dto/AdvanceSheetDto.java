package com.danielbchapman.production.dto;

import java.io.Serializable;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.danielbchapman.production.entity.City;
import com.danielbchapman.production.entity.Performance;
import com.danielbchapman.production.entity.PerformanceAdvance;
import com.danielbchapman.production.entity.Venue;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvanceSheetDto implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Venue venue = new Venue();
	private City city = new City();
	private PerformanceAdvance performanceAdvance = new PerformanceAdvance();
	private ArrayList<Performance> performances = new ArrayList<Performance>();
}
